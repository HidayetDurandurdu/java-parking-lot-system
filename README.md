# Parking Lot System (Java)

## Overview
This project is a Java OOP implementation of a multi-floor parking lot with a gate-based registration system. Cars can enter the parking lot through a `Gate`, receive a ticket ID, occupy a parking `Space`, and later be deregistered to free the space.

The project also includes JUnit tests (functional + structural) to validate correctness and design.

## Core Concepts
- **ParkingLot**: Represents the parking facility as a 2D floor plan (`Space[][]`).
- **Space**: Represents an individual parking space (floor index + space index) that may be occupied by a car.
- **Car**: Stores car attributes such as license plate, required parking size, preferred floor, and ticket ID.
- **Gate**: Handles car registration and deregistration, assigning spaces based on availability and car size.

## Key Features
- Multi-floor parking layout using a 2D array
- Support for different vehicle sizes:
  - `SMALL` vehicles occupy **1 space**
  - `LARGE` vehicles occupy **2 adjacent spaces**
- Preferred-floor parking:
  - The gate attempts to park a car on its preferred floor first, then searches nearby floors
- Ticket generation:
  - Ticket IDs follow the pattern: `<LICENSE_PLATE>-T`
- Includes test suite for validating structure and functionality (JUnit)

## Project Structure
- `src/main/java/`
  - `parking/` and `parking/facility/` contain the parking lot system logic
  - `vehicle/` contains the vehicle models (`Car`, `Size`)
- `src/test/java/`
  - JUnit tests and a test suite runner

## How It Works
1. Create a `ParkingLot` with a given number of floors and spaces per floor.
2. Create a `Gate` connected to that parking lot.
3. Register cars through the gate:
   - The system finds a suitable space (and two spaces for large cars).
   - The car receives a ticket ID.
4. Deregister cars using the ticket ID to free the occupied space(s).

## Example Usage (Concept)
- Create lot: `new ParkingLot(floors, spacesPerFloor)`
- Create gate: `new Gate(parkingLot)`
- Register car: `gate.registerCar(car)`
- Remove car: `gate.deRegisterCar(ticketId)`

## Possible Improvements
- Add a real ticket object and entry/exit timestamps
- Prevent duplicate registrations by license plate
- Improve large-car placement strategy (e.g., first-fit vs best-fit)
- Add a CLI demo runner / simulation
- Add Maven/Gradle build config for easier test execution

## Author
Hidayet Melih Durandurdu
