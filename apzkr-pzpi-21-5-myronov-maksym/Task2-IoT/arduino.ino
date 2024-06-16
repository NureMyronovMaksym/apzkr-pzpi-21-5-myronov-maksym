const int trigPin = 9;
const int echoPin = 10;
const int tempPin = A0;

long measureDistance() {
  digitalWrite(trigPin, LOW);
  delayMicroseconds(2);

  digitalWrite(trigPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin, LOW);

  long duration = pulseIn(echoPin, HIGH);

  long distance = duration * 0.034 / 2;

  return distance;
}

float readTemperature() {
  int tempReading = analogRead(tempPin);
  float voltage = tempReading * 5.0 / 1024.0;
  float temperatureC = (voltage - 0.5) * 100.0;
  return temperatureC;
}

void setup() {
  Serial.begin(9600);

  pinMode(trigPin, OUTPUT);

  pinMode(echoPin, INPUT);

  Serial.println("Position the ultrasonic sensor to measure length, width, and height.");
}

void loop() {
  Serial.println("Measuring length...");
  delay(2000);
  long length1 = measureDistance();
  Serial.println("Length (near)");

  delay(2000);
  long length2 = measureDistance();
  Serial.println("Length (far)");

  long length = abs(length2 - length1);
  Serial.print("Length: ");
  Serial.print(length);
  Serial.println(" cm");

  Serial.println("Measuring width...");
  delay(2000);
  long width1 = measureDistance();
  Serial.println("Width (near)");

  delay(2000);
  long width2 = measureDistance();
  Serial.println("Width (far)");

  long width = abs(width2 - width1);
  Serial.print("Width: ");
  Serial.print(width);
  Serial.println(" cm");

  Serial.println("Measuring height...");
  delay(2000);
  long height1 = measureDistance();
  Serial.println("Height (top)");

  delay(2000);
  long height2 = measureDistance();
  Serial.println("Height (bottom)");

  long height = abs(height2 - height1);
  Serial.print("Height: ");
  Serial.print(height);
  Serial.println(" cm");

  float temperatureC = readTemperature();
  Serial.print("Temperature: ");
  Serial.print(temperatureC);
  Serial.println(" °C");

  delay(5000);
}
