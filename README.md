## **msusers** 
A microservice for user management.

## Table of Contents

- [Introduction](#introduction)
- [Technologies](#technologies)
- [Setup](#setup)
- [Usage](#usage)
- [Endpoints](#endpoints)

## Introduction

**msusers** is a microservice designed to handle user management functionalities such as user sign-up and login.

## Technologies

- Java
- Spring Boot
- Spring Security(BCrypt)
- JJWT (Java JWT)
- Gradle

## Setup

### Prerequisites

- Java 8 or higher
- Gradle

### Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/juanmapaniego/msusers.git
    cd msusers
    ```

2. Build the project:
    ```sh
    ./gradlew build
    ```

3. Run the application:
    ```sh
    ./gradlew bootRun
    ```

## Usage

### Running the Application

To start the application, use the following command:
```sh
./gradlew bootRun
```
### Connect to H2
While the app is running, you can connect to the H2 console accesing to 
```
localhost:8080/h2-console
```
with
```
JDBC URL: jdbc:h2:mem:usersdb
username: sa
password: password
```
### Testing

To run tests, use the following command:
```sh
./gradlew test
```

## Endpoints

### User Registration

- **URL:** `/sign-up`
- **Method:** `POST`
- **Request Body:**
    ```json
    {
        "name": "John Doe",
        "email": "john.doe@example.com",
        "password": "4Password1",
        "phones": [
            {
                "number": "123456789",
                "cityCode": "1",
                "countryCode": "57"
            }
        ]
    }
    ```
- **Response:**
    ```json
    {
        "id": "1",
        "name": "John Doe",
        "email": "john.doe@example.com",
        "token": "jwt_token",
        "phones": [
            {
                "number": "123456789",
                "cityCode": "1",
                "countryCode": "57"
            }
        ],
        "created": "2023-10-01T12:00:00",
        "isActive": "true",
        "lastLogin": "2023-10-01T12:00:00"
    }
    ```

### User Login

- **URL:** `/login`
- **Method:** `GET`
- **Headers:**
    ```sh
    Authorization: Bearer jwt_token
    ```
- **Response:**
    ```json
    {
        "id": "1",
        "name": "John Doe",
        "email": "john.doe@example.com",
        "isActive": "true",
        "phones": [
            {
                "number": "123456789",
                "cityCode": "1",
                "countryCode": "57"
            }
        ],
        "created": "2023-10-01T12:00:00",
        "lastLogin": "2023-10-01T12:00:00",
        "token": "new_jwt_token"
    }
    ```

