import axios from "axios";

const REST_API_AUTHENTICATE_URL = "http://localhost:8080/api/v1/auth/register"
const REST_API_REGISTRATE_URL = "http://localhost:8080/api/v1/auth/authenticate"

export const registerUser = (registerRequest) => axios.post(REST_API_BASE_URL, registerRequest);

export const authenticateUser = (authenticateRequest) => axios.post(REST_API_BASE_URL, authenticateRequest);