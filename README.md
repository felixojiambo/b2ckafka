# b2ckafka

## Overview

`b2ckafka` is a Spring Boot application that integrates with Safaricom's Daraja API for handling B2C (Business to Customer) transactions. The application uses Apache Kafka for messaging to handle B2C requests and responses, and MongoDB for storing the data. It provides endpoints for receiving B2C requests, fetching payment statuses, updating payment statuses, and obtaining access tokens.

## Project Structure

- **`/src/main/java`**: Contains the Java source code.
  - **`/com/b2c/tandapay/config`**: Configuration classes for application properties.
  - **`/com/b2c/tandapay/controller`**: REST controllers handling HTTP requests.
  - **`/com/b2c/tandapay/service`**: Services for interacting with Mpesa and Kafka.
  - **`/com/b2c/tandapay/repository`**: MongoDB repositories for data persistence.
  - **`/com/b2c/tandapay/model`**: Model classes for request and response data.
  - **`/com/b2c/tandapay/dto`**: Data Transfer Objects (DTOs) for communication.

- **`/src/main/resources`**: Contains application properties and other resources.
  - **`application.properties`**: Configuration file for application settings.

## Prerequisites

- Java 17 or later
- Apache Kafka
- MongoDB
- Maven or Gradle (for dependency management)

## Setup and Installation

1. **Clone the Repository**

   ```bash
   git clone https://github.com/felixojiambo/b2ckafka.git
   cd b2ckafka
   ```

2. **Update Configuration**

   Edit the `src/main/resources/application.properties` file with the provided configuration values.

3. **Build the Project**

   Using Maven:
   ```bash
   mvn clean install
   ```

   Using Gradle:
   ```bash
   ./gradlew build
   ```

4. **Run the Application**

   Using Maven:
   ```bash
   mvn spring-boot:run
   ```

   Using Gradle:
   ```bash
   ./gradlew bootRun
   ```

## API Endpoints

### 1. Receive B2C Request

- **URL:** `/api/b2c`
- **Method:** `POST`
- **Request Body:**
  ```json
  {
    "amount": "1000",
    "partyA": "123456",
    "partyB": "234553",
    "ocassion": "Payment for Order",
    "initiatorName": "testapi",
    "securityCredential": "********"
  }
  ```
- **Description:** Receives a B2C request, saves it to MongoDB, and sends it to a Kafka topic for processing.

### 2. Fetch Payment Status

- **URL:** `/api/b2c/{requestId}`
- **Method:** `GET`
- **Path Variable:**
  - `requestId`: The ID of the request to fetch.
- **Response Body:**
  ```json
  {
    "status": "Success",
    "amount": "1000",
    "transactionId": "abc123",
    "errorCode": null
  }
  ```
- **Description:** Retrieves the payment status based on the request ID from MongoDB.

### 3. Update Payment Status

- **URL:** `/api/b2c/{requestId}`
- **Method:** `PUT`
- **Path Variable:**
  - `requestId`: The ID of the request to update.
- **Request Body:**
  ```json
  {
    "status": "Completed",
    "amount": "1000",
    "transactionId": "abc123",
    "errorCode": null
  }
  ```
- **Description:** Updates the payment status for a specific request ID in MongoDB.

### 4. Get Access Token

- **URL:** `/api/b2c/token`
- **Method:** `GET`
- **Description:** Retrieves an access token from Safaricom's OAuth endpoint.

## Kafka Topics

- **`b2c_requests`**: Topic where B2C requests are published.
- **`b2c_responses`**: Topic where B2C responses are consumed.

## Configuration

### `application.properties`

```properties
server.port=8080

# Mpesa Configuration
mpesa.consumer-key=JiQPZnHrn0TkftZAvWZrj2ibuFsuxCRfZxA8eUX0T6r9YTud
mpesa.consumer-secret=sp0Bao3jdWpd5Z3kAuAFKzOQxYTK8LuYRT3Igg8CbiA42OOvvAQiRLzry5YAnN40
mpesa.access-token-url=https://sandbox.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials
mpesa.b2c-url=https://sandbox.safaricom.co.ke/mpesa/b2c/v1/paymentrequest
mpesa.b2c-initiator-password=Safaricom999!*!
mpesa.b2c-result-url=https://mydomain.com/b2c/result
mpesa.b2c-queue-timeout-url=https://mydomain.com/b2c/queue
mpesa.b2c-initiator-name=testapi
mpesa.shortcode=234553

# Kafka Configuration
spring.kafka.bootstrap-servers=localhost:39092

# MongoDB Configuration
spring.data.mongodb.uri=mongodb://localhost:37017/mpesa
```

## Documentation and Testing

- **Documentation**: Detailed documentation is provided within the codebase and API endpoints.
- **Testing**: Unit tests and integration tests are included to ensure the functionality of the application.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
