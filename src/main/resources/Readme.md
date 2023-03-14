# Drone Delivery System

This repository contains Java code for a drone delivery system API.

The API provides endpoints for registering a new drone, loading medications onto a drone, retrieving loaded medications 
from a drone, finding available drones for loading, and checking a drone's battery level.

## Tech Stack
The application is built using the following technologies:

- Java 17
- Spring Boot
- H2 database
- Log4j2
- Hibernate
- Lombok

## Requirements
- JDK 11 or later
- Any relational database of choice

# Classes

## DroneModel Class
This class represents the drone entity in the system. It has the following properties:

- **id:** Long - unique identifier of the drone
- **serialNumber:** String - unique serial number of the drone
- **model:** DroneModels - type of the drone, Lightweight, Middleweight, Cruiserweight, or Heavyweight
- **weightLimit:** double - maximum weight the drone can carry, 500 grams at most
- **batteryCapacity:** double - battery percentage of the drone
- **state:** DroneState - current state of the drone, IDLE, LOADING, LOADED, DELIVERING, DELIVERED, or RETURNING
- **medications:** List<MedicationModel> - medications currently loaded on the drone

### MedicationModel Class
This class represents the medication entity in the system. It has the following properties:

- **id:** Long - unique identifier of the medication
- **name:** String - name of the medication
- **weight:** double - weight of the medication, in grams
- **code:** String - code of the medication, consisting of upper case letters, numbers, and underscore
- **image:** byte[] - picture of the medication case

### DroneDto Class
This class represents the DTO (Data Transfer Object) for the drone entity. It has the following properties:

- **id:** Long - unique identifier of the drone
- **serialNumber:** String - unique serial number of the drone, between 3 and 100 characters long
- **model:** String - type of the drone, LIGHTWEIGHT, MIDDLEWEIGHT, CRUISERWEIGHT, or HEAVYWEIGHT
- **weightLimit:** double - maximum weight the drone can carry, between 1 and 500 grams
- **batteryCapacity:** double - battery percentage of the drone
- **state:** String - current state of the drone, IDLE, LOADING, LOADED, DELIVERING, DELIVERED, or RETURNING
- **medications:** List<MedicationModel> - medications currently loaded on the drone

### MedicationDto Class
This class represents the DTO (Data Transfer Object) for the medication entity. It has the following properties:

- **name:** String - name of the medication, consisting of letters, numbers, hyphen, and underscore
- **weight:** double - weight of the medication, in grams
- **code:** String - code of the medication, consisting of upper case letters, numbers, and underscore
- **image:** byte[] - picture of the medication case

### DroneMapperClass
This class is responsible for mapping the DroneModel entity to its DTO representation and vice versa.

### MedicationMapperClass
This class is responsible for mapping the MedicationModel entity to its DTO representation and vice versa.

### DroneService 
This is an interface that defines the methods for registering and loading drones.

### DroneServiceImpl
Implements the DroneService interface and provides methods for registering a drone, loading medications onto a drone, 
retrieving loaded medications from a drone, and finding available drones for loading.

### DroneBatteryCheckServiceImpl
This class implements the DroneBatteryCheckService interface and provides the functionality for checking the battery 
level of all drones and logging the data into a database.

### BatteryLogModel
This class represents a battery log entry in the database. It includes the drone ID, battery capacity, and timestamp.

### BatteryLogMapper
This class provides methods for mapping BatteryLogModel objects to BatteryLogDto objects and vice versa.

### DroneRepository
This is an interface that extends JpaRepository and provides methods for accessing the DroneModel entity in the database.

### DroneDispatchController 
Provides the API endpoints for registering a new drone, loading medications onto a drone, retrieving loaded medications 
from a drone, finding available drones for loading, and checking a drone's battery level.


## How to Use the Project

To use the project, you need to follow these steps:

- Clone the project from the GitHub repository.
- Open the project in your preferred IDE, such as IntelliJ IDEA, Eclipse, or NetBeans.
- Make sure you have the required dependencies, such as Spring Web, Spring Data JPA, and MySQL Driver, in your pom.xml file.
- Create a MySQL database named drone_service and configure the application.properties file with the appropriate username and password.
- Run the project as a Spring Boot application and check the console for any errors or warnings.
- Test the project using a tool like Postman, by sending HTTP requests to the following endpoints:

The API will be available at localhost:8080/droneApi/v1/.

## API Endpoints
***POST /registerDrone***
Registers a new drone with the given name, serialNumber, weightLimit, state, batteryCapacity.

Request Body
*json*
{
   ` "name" : "DroneA",
    "serialNumber" : "ABC123",
    "model" : "LIGHTWEIGHT",
    "weightLimit" : 500,
    "batteryCapacity" : 70,
    "state" : IDLE `
}

Response
Returns a 201 Created response with the message "Drone registered successfully".

***PUT /loadDrone/{id}***
Loads medications onto a drone.

***Request Parameters***
***id:*** The ID of the drone to load medications onto.

Request Body
*json*
[
    {
      `  "name": "Cough_Syrup",
        "weight": 200.0,
        "code": "CSY",
        "image": null
        },
        {
        "name": "ZOBO",
        "weight": 300.0,
        "code": "ZOBO",
        "image": null `
    }
]

Response
Returns the loaded drone.

***GET /availableDrones***
Finds available drones for loading.

Response
Returns a list of available drones.

***GET /medsFromDrone/{id}***
Retrieves loaded medications from a drone.

Request Parameters
id: The ID of the drone to retrieve loaded medications from.
Response
Returns a list of loaded medications.

***GET /batteryLevel/{id}***
Checks a drone's battery level.

Request Parameters
id: The ID of the drone to check the battery level of.
Response
Returns a string containing the drone's battery level.

# Test
Testing was not carried out on this project