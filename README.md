**WinterSupplementCalculator**

  This project is a Spring Boot application that implements a rules engine for calculating winter supplement payments. It uses MQTT for communication and provides a REST API for managing subscriptions.

**Setting Up Development Environment**

1. **Prerequisites**
   - Java JDK 21 
   - Maven
   - Git
   - Postman
   - Mosquitto client
 
 The mosquitto service need to be running to ensure that mosquitto client is active,it can be checked in services.msc (Windows).

2. **Clone the Repository**

git clone https://github.com/your-username/winter-supplement-rules-engine.git
cd winter-supplement-rules-engine

3. **Build the Project**

In Eclipse right click on project Run As->Maven clean

4. The server will start by default at localhost:8080

5. Use the REST API to subscribe to a specific topic using POSTMAN:

POST http://localhost:8080/api/mqtt/subscribe?topicId=<mqtt topic id>.
The mqtt topic id can be received from the Winter Supplement WebApp(https://winter-supplement-app-d690e5-tools.apps.silver.devops.gov.bc.ca/)

6. Use the command
mosquitto_pub -d -h test.mosquitto.org -t "BRE/calculateWinterSupplementInput/5138f6da-f9dc-4865-9c80-ae506c52f891" -m "{"id":"5138f6da-f9dc-4865-9c80-ae506c52f891","numberOfChildren":2,"familyComposition":"single","familyUnitInPayForDecember":true}" 
in the command line to publish values for winter supplement rules engine.

7. **Subscribing to Results**

    To see the results, subscribe to the output topic:
    mosquitto_sub -h localhost -t "BRE/calculateWinterSupplementOutput/<mqtt topic id>"
    

**Project Details**

## API Endpoints

- `POST /api/mqtt/subscribe`: Subscribe to a specific MQTT topic
- `POST /api/rules/process`: Process winter supplement input directly via REST API

## Configuration

The main configuration file is `src/main/resources/application.properties`. Key settings include:

- MQTT broker URL
- MQTT client ID
- Input and output topic prefixes

## Architecture

- `RulesengineApplication`: Main Spring Boot application class
- `MqttConfig`: Configures MQTT connection and channels
- `MqttService`: Handles MQTT message processing
- `RulesEngineService`: Implements the business logic for winter supplement calculation
- `MqttController`: REST API for managing MQTT subscriptions
- `RulesEngineController`: REST API for direct input processing
