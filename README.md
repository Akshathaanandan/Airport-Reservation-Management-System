# Airport Reservation Management System

## Project Overview
The Airport Reservation Management System is a console-based application developed using Core Java. It allows users to view available flights, book tickets for multiple passengers, manage reservations, and generate PDF tickets.

## Features
- Display available flights and flight details.
- Book flight tickets.
- Support multiple passengers in a single booking.
- Generate a unique PNR for each booking.
- Search bookings using PNR.
- Cancel existing bookings.
- Automatically update seat availability after booking and cancellation.
- Generate PDF tickets containing booking details.

## Technologies Used
- Java (Core Java)
- Apache PDFBox 3.0.8 – PDF ticket generation
- MySQL Connector/J 9.2.0 – JDBC dependency for planned database integration

## Project Structure
```text
AirportReservationSystem/
├── src/       - Java source files
├── bin/       - Compiled class files
├── lib/       - External JAR dependencies
├── tickets/   - Generated PDF tickets
├── .gitignore
└── README.md
```

## Current Project Status
The console application features have been implemented and tested. MySQL database integration and website development are planned for a later phase.

## Future Enhancements
- Integrate MySQL for persistent data storage.
- Develop a web-based interface.
- Deploy the application.

## Note
This project is currently developed as a Core Java console application.