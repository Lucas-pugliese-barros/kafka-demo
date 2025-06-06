# Kafka Messaging Project

## Overview

This project demonstrates implementation of **Apache Kafka** using **Java** and **Spring Boot**. It consists of two separate applications:

- **Producer**: Accept JSON messages via an API endpoint and publishes them to a Kafka topic called `topic-messages`.
- **Consumer**: An application that read data from the `topic-messages` topic.

OBS: The idea of that project is also doesn't use Zookeeper since in the future versions of Kafka it will replaced for the KRaft
---

## Prerequisites

- **Java 21** or later
- **Maven**
- **Apache Kafka** (via Docker)
- **Docker**

---

## Setup Instructions

### 1. Start Kafka

Run Kafka using Docker:
```bash
docker run -d -p 9092:9092 --name kafka confluentinc/cp-kafka:latest
```

### 2. Build and Run the Producer:
```bash
cd kafka-producer
mvn clean install
mvn spring-boot:run
```

### 3. Build and Run the Consumer:
```bash
cd kafka-consumer
mvn clean install
mvn spring-boot:run
```

### 4. Test the Application
```bash
curl -X POST http://localhost:8081/api/messages \
     -H "Content-Type: application/json" \
     -d '{"id":"msg1","content":"Hello Kafka!"}'
```

--- 

### That project is an improvement of the [simple-kafka-demo](https://github.com/Lucas-pugliese-barros/simple-kafka-demo) with the following feature below.

```
Multiple partitions for parallelism.
More than one broker
A Dead Letter Queue (DLQ) for error handling.
Complex processing logic (e.g., aggregation, filtering).
Error handling
Store consumed messages in a database.
```

---

