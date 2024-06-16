from flask import Flask, request, jsonify
import jwt
import serial
import time
import requests
import os

app = Flask(__name__)

spring_boot_url = 'http://localhost:8080/api/iot-service/package'
user_id = 0
token = ''
serial_port = ''
ser = None
SECRET_KEY = 'eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0'

# Global variables for package dimensions and temperature
length = width = height = temperature = None
data_file_path = None

class Package:
    def __init__(self, height, length, width, temperature):
        self.height = height
        self.length = length
        self.width = width
        self.temperature = temperature

    def to_dict(self):
        return {
            'height': self.height,
            'length': self.length,
            'width': self.width,
            'temperature': self.temperature
        }

    def volume(self):
        return self.height * self.length * self.width

    def to_volume_temp_dict(self):
        return {
            'volume': self.volume(),
            'temperature': self.temperature
        }

class Container:
    def __init__(self, number_of_packages, fulfillment_percentage):
        self.number_of_packages = number_of_packages
        self.fulfillment_percentage = fulfillment_percentage

    def to_dict(self):
        return {
            "number_of_packages": self.number_of_packages,
            "fulfillment_percentage": self.fulfillment_percentage
        }

def send_data_to_spring_boot(package):
    headers = {
        'Authorization': f'Bearer {token}',
        'Content-Type': 'application/json'
    }
    payload = package.to_volume_temp_dict()
    response = requests.post(spring_boot_url, headers=headers, json=payload)
    return response

def get_user_id(token):
    global user_id
    try:
        decoded_token = jwt.decode(token, options={"verify_signature": False})
        user_id = decoded_token.get('id')
    except jwt.ExpiredSignatureError:
        print("Token has expired")
    except jwt.InvalidTokenError:
        print("Invalid token")

def decode_token(token):
    try:
        return jwt.decode(token, options={"verify_signature": False})
    except jwt.ExpiredSignatureError:
        return None
    except jwt.InvalidTokenError:
        return None

def analyze_data(package):
    if package.temperature > 60.0:
        raise ValueError("High temperature detected")
    if package.volume() > 8000:
        raise ValueError("Large package detected")

@app.route('/connect/package', methods=['POST'])
def connect_arduino():
    global token, serial_port, ser, data_file_path

    token = request.headers.get('Authorization')
    if not token:
        return jsonify({"error": "Missing Authorization header"}), 400

    token = token.replace("Bearer ", "")

    decoded_token = decode_token(token)
    if not decoded_token:
        return jsonify({"error": "Invalid or expired token"}), 401
    roles = decoded_token.get('roles', [])

    if 'ROLE_ADMIN' not in roles:
        return jsonify({"error": "Insufficient permissions"}), 403

    data = request.json
    serial_port = data.get('serial_port')
    data_file_path = data.get('data_file_path')
    get_user_id(token)

    try:
        if not serial_port:
            return jsonify({"message": "Simulated connection"}), 200
        else:
            if ser is None:
                ser = serial.Serial(serial_port, 9600)
            time.sleep(2)
            return jsonify({"message": "Connected"}), 200
    except Exception as e:
        return jsonify({"error": str(e)}), 500

@app.route('/send/packageData', methods=['POST'])
def send_data():
    global ser, length, width, height, temperature, data_file_path

    if data_file_path is None:
        return jsonify({"error": "Arduino not connected"}), 500

    try:
        if os.path.exists(data_file_path):
            with open(data_file_path, 'r') as file:
                lines = file.readlines()
                for line in lines:
                    if 'Length:' in line:
                        length = int(line.split(':')[1].strip().split()[0])
                    elif 'Width:' in line:
                        width = int(line.split(':')[1].strip().split()[0])
                    elif 'Height:' in line:
                        height = int(line.split(':')[1].strip().split()[0])
                    elif 'Temperature:' in line:
                        temperature = int(float(line.split(':')[1].strip().split()[0]))


            package = Package(height, length, width, temperature)
        else:
            if ser is None:
                return jsonify({"error": "Arduino not connected"}), 500

            ser.write(b'GET_PACKAGE_DATA\n')
            package_response = ser.readline().decode('utf-8').strip()
            params = dict(item.split('=') for item in package_response.split('&'))
            length = float(params.get('length'))
            width = float(params.get('width'))
            height = float(params.get('height'))
            temperature = float(params.get('temperature'))
            package_volume = length * width * height
            analyze_data(package_volume)

            package = Package(height, length, width, temperature)

        send_data_to_spring_boot(package)
        return jsonify({"message": "Data sent successfully"}), 200
    except Exception as e:
        return jsonify({"error": str(e)}), 500

@app.route('/container/capacity', methods=['POST'])
def calculate_capacity():
    global length, width, height
    if not all([length, width, height]):
        return jsonify({"error": "Package dimensions not set. Please send package data first."}), 400

    data = request.json
    container_volume = data.get('container_volume')
    if not container_volume:
        return jsonify({"error": "Missing container_volume in request"}), 400

    try:
        package_volume = length * width * height
        num_packages = int(container_volume / package_volume)
        used_volume = num_packages * package_volume
        fulfillment_percentage = (used_volume / container_volume) * 100

        container = Container(number_of_packages=num_packages, fulfillment_percentage=fulfillment_percentage)
        return jsonify(container.to_dict()), 200
    except Exception as e:
        return jsonify({"error": str(e)}), 500


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)