## **msusers** 
Microservicio para la gestion de usuarios.

## Tabla de contenidos

- [Introducción](#introducción)
- [Tecnologías](#tecnologías)
- [Setup](#setup)
- [Uso](#usage)
- [Endpoints](#endpoints)

## Introducción

**msusers** is a microservice designed to handle user management functionalities such as user sign-up and login.

## Tecnologías

- Java
- Spring Boot
- Spring Security(BCrypt)
- JJWT (Java JWT)
- Gradle

## Setup

### Prerequisitos

- Java 8 o superior
- Gradle

### Instalación

1. Clonar el repositorio:
    ```sh
    git clone https://github.com/juanmapaniego/msusers.git
    cd msusers
    ```

2. Buildear el proyecto:
    ```sh
    ./gradlew build
    ```

3. Ejecutar la aplicación:
    ```sh
    ./gradlew bootRun
    ```

## Uso

### Ejecutando la aplicación

Para ejecutar la aplicacion, usar el siguiente comando;
```sh
./gradlew bootRun
```
### Conectarse a H2
Mientras la app se este ejecutando, se puede conectar a la consola de H2 accediendo a: 
```
localhost:8080/h2-console
```
con los siguientes valores:
```
JDBC URL: jdbc:h2:mem:usersdb
username: sa
password: password
```
### Testing

Para ejecutar los tests, ejecutar el siguiente comando:
```sh
./gradlew test
```
#### Coverage
Para generar reportes de coverage debe ejectutarse el siguiente comando:
```sh
./gradlew :jacocoTestReport
```
Los mismos se generan en html y xml en el directorio
```
./build/reports/jacoco/test
```

El proyecto se encuentra configurado con 80% de cobertura, se puede ejecutar el comando
```sh
./gradlew :jacocoTestCoverageVerification
```
y verificar el resultado exitoso.

## Endpoints

### Registro de usuario

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

### Login de usuario

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

