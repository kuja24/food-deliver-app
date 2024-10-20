# Food Delivery

## Overview
This project is a Spring Boot application that implements JWT-based authentication and role-based access control (RBAC). It exposes various RESTful APIs, which can be tested using Swagger UI.

## Swagger UI
The Swagger UI provides a user-friendly interface for testing the REST APIs. 
When running locally, You can access the Swagger UI by navigating to the following URL in your browser:
http://localhost:8081/swagger-ui.html

## Public Endpoints
The below endpoints are public and do not require JWT:
   - Swagger UI
   - /generate-token
   - /register

## Obtaining a JWT Token
To access secured endpoints, you need to obtain a JWT token. You can get the token by calling the login endpoint:

### Login Endpoint
- **URL:** `/api/v1/auth/generate-token`
- **Method:** `POST`
- **Request Body:**
  ```json
  {
    "email": "your_email",
    "password": "your_password"
  }
  
## Using the token in Swagger UI
Click on the "Authorize" Button located in the top right corner of the Swagger UI.
In the modal that appears, enter the token obtained from the /generate-token endpoint.
Subsequent requests will use this token in the Authorization Headers

To change the token "logout" in the "Authorize" section and repeat the above process with a new token
