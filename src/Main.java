import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<Flight> flights = new ArrayList<>();

        flights.add(new Flight("AI101", "Air India", "Chennai", "Delhi", "10:30 AM", "1:20 PM", 50, 10, 5500, 12000));
        flights.add(new Flight("6E205", "IndiGo", "Chennai", "Mumbai", "3:00 PM", "4:55 PM", 80, 8, 4800, 10000));
        flights.add(new Flight("SG302", "SpiceJet", "Chennai", "Bangalore", "6:00 PM", "7:05 PM", 60, 5, 3500, 8000));
        flights.add(new Flight("UK405", "Vistara", "Chennai", "Hyderabad", "8:30 AM", "10:00 AM", 45, 7, 4200, 9500));

        ArrayList<Booking> bookings = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            displayMenu();

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    viewFlights(flights);
                    break;

                case "2":
                    bookFlight(flights, bookings, scanner);
                    break;

                case "3":
                    searchBookingByPnr(bookings, scanner);
                    break;

                case "4":
                    cancelBooking(bookings, scanner);
                    break;

                case "5":
                    System.out.println("Thank you for using the Airport Reservation System. Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }

        scanner.close();
    }

    public static void displayMenu() {
        System.out.println("\n========================================");
        System.out.println("     AIRPORT RESERVATION SYSTEM");
        System.out.println("========================================");
        System.out.println("1. View Available Flights");
        System.out.println("2. Book a Flight");
        System.out.println("3. Search Booking by PNR");
        System.out.println("4. Cancel Booking");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    public static void viewFlights(ArrayList<Flight> flights) {
        for (Flight flight : flights) {
            flight.displayFlightDetails();
        }
    }

    public static void bookFlight(ArrayList<Flight> flights, ArrayList<Booking> bookings, Scanner scanner) {
        System.out.println("----------------------------------------");
        System.out.println("           AVAILABLE FLIGHTS");
        System.out.println("----------------------------------------");
        for (int i = 0; i < flights.size(); i++) {
            Flight flight = flights.get(i);
            System.out.println((i + 1) + ". " + flight.getFlightNumber() + " - " + flight.getAirline() + " - " + flight.getSource() + " to " + flight.getDestination());
        }

        System.out.print("\nEnter flight choice: ");
        String flightChoice = scanner.nextLine().trim();
        Flight selectedFlight = null;

        switch (flightChoice) {
            case "1":
                selectedFlight = flights.get(0);
                break;
            case "2":
                selectedFlight = flights.get(1);
                break;
            case "3":
                selectedFlight = flights.get(2);
                break;
            case "4":
                selectedFlight = flights.get(3);
                break;
            default:
                System.out.println("Invalid flight choice. Please select 1, 2, 3, or 4.");
                break;
        }

        if (selectedFlight == null) {
            return;
        }

        System.out.println("\nFlight selected successfully!\n");
        selectedFlight.displayFlightDetails();

        String travelClass = "";
        double pricePerTicket = 0.0;

        while (travelClass.isEmpty()) {
            System.out.println("\n----------------------------------------");
            System.out.println("           SELECT TRAVEL CLASS");
            System.out.println("----------------------------------------");
            System.out.println("1. Economy  - ₹" + selectedFlight.getEconomyPrice() + "  - " + selectedFlight.getEconomySeats() + " seats available");
            System.out.println("2. Business - ₹" + selectedFlight.getBusinessPrice() + " - " + selectedFlight.getBusinessSeats() + " seats available");
            System.out.print("\nEnter your choice: ");

            String classChoice = scanner.nextLine().trim();

            switch (classChoice) {
                case "1":
                    travelClass = "Economy";
                    pricePerTicket = selectedFlight.getEconomyPrice();
                    break;
                case "2":
                    travelClass = "Business";
                    pricePerTicket = selectedFlight.getBusinessPrice();
                    break;
                default:
                    System.out.println("Invalid class choice. Please select 1 or 2.");
                    break;
            }
        }

        System.out.println("\nTravel class selected: " + travelClass);
        System.out.println("Price per ticket: ₹" + pricePerTicket);

        int numberOfTickets = 0;
        while (true) {
            System.out.print("\nHow many tickets do you want to book? ");
            String ticketInput = scanner.nextLine().trim();

            try {
                numberOfTickets = Integer.parseInt(ticketInput);
                if (numberOfTickets <= 0) {
                    System.out.println("Invalid number of tickets. Please enter a positive number.");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number of tickets. Please enter a positive number.");
                continue;
            }

            int availableSeats = travelClass.equals("Economy")
                    ? selectedFlight.getEconomySeats()
                    : selectedFlight.getBusinessSeats();

            if (numberOfTickets > availableSeats) {
                System.out.println("Sorry, only " + availableSeats + " seats are available in " + travelClass + ".");
                continue;
            }

            break;
        }

        double totalPrice = pricePerTicket * numberOfTickets;

        System.out.println("\n----------------------------------------");
        System.out.println("           BOOKING SUMMARY");
        System.out.println("----------------------------------------");
        System.out.println("Travel Class      : " + travelClass);
        System.out.println("Price Per Ticket  : ₹" + pricePerTicket);
        System.out.println("Number of Tickets : " + numberOfTickets);
        System.out.println("Total Price       : ₹" + totalPrice);
        System.out.println("----------------------------------------");

        ArrayList<Passenger> passengers = new ArrayList<>();

        for (int i = 1; i <= numberOfTickets; i++) {
            System.out.println("\nPassenger " + i);
            System.out.print("Enter passenger name: ");
            String name = scanner.nextLine();

            System.out.print("Enter passenger age: ");
            int age;
            while (true) {
                try {
                    age = Integer.parseInt(scanner.nextLine().trim());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid age format. Please enter a valid number.");
                    System.out.print("Enter passenger age: ");
                }
            }

            String gender = null;
            while (gender == null) {
                System.out.println("\nEnter passenger gender:\n");
                System.out.println("1. Female");
                System.out.println("2. Male");
                System.out.println("3. Prefer not to say\n");
                System.out.print("Enter your choice: ");

                String genderChoice = scanner.nextLine().trim();

                switch (genderChoice) {
                    case "1":
                        gender = "Female";
                        break;
                    case "2":
                        gender = "Male";
                        break;
                    case "3":
                        gender = "Prefer not to say";
                        break;
                    default:
                        System.out.println("Invalid gender choice. Please select 1, 2, or 3.");
                        break;
                }
            }

            Passenger passenger = new Passenger(name, age, gender);
            passengers.add(passenger);
        }

        System.out.println("\n========================================");
        System.out.println("           BOOKING REVIEW");
        System.out.println("========================================");
        System.out.println("Flight Number     : " + selectedFlight.getFlightNumber());
        System.out.println("Airline           : " + selectedFlight.getAirline());
        System.out.println("Route             : " + selectedFlight.getSource() + " -> " + selectedFlight.getDestination());
        System.out.println("Departure Time    : " + selectedFlight.getDepartureTime());
        System.out.println("Arrival Time      : " + selectedFlight.getArrivalTime());
        System.out.println("Travel Class      : " + travelClass);
        System.out.println("Price Per Ticket  : ₹" + pricePerTicket);
        System.out.println("Number of Tickets : " + numberOfTickets);
        System.out.println("Total Price       : ₹" + totalPrice);
        System.out.println("\n------------ PASSENGERS ----------------");

        for (int i = 0; i < passengers.size(); i++) {
            Passenger p = passengers.get(i);
            System.out.println("\nPassenger " + (i + 1));
            System.out.println("Name   : " + p.getName());
            System.out.println("Age    : " + p.getAge());
            System.out.println("Gender : " + p.getGender());
        }

        while (true) {
            System.out.println("\n========================================");
            System.out.println("1. Confirm Booking");
            System.out.println("2. Cancel Booking");
            System.out.println("========================================");
            System.out.print("Enter your choice: ");

            String confirmChoice = scanner.nextLine().trim();

            if (confirmChoice.equals("1")) {
                String pnr = "PNR" + System.currentTimeMillis();

                if (travelClass.equals("Economy")) {
                    selectedFlight.setEconomySeats(
                        selectedFlight.getEconomySeats() - numberOfTickets
                    );
                } else if (travelClass.equals("Business")) {
                    selectedFlight.setBusinessSeats(
                        selectedFlight.getBusinessSeats() - numberOfTickets
                    );
                }

                Booking booking = new Booking(
                    pnr,
                    selectedFlight,
                    passengers,
                    travelClass,
                    numberOfTickets,
                    pricePerTicket,
                    totalPrice,
                    "CONFIRMED"
                );

                bookings.add(booking);

                // ── PDF ticket generation ──────────────────────────────────
                TicketPDF.generate(booking);
                // ──────────────────────────────────────────────────────────

                System.out.println("\n========================================");
                System.out.println("        BOOKING CONFIRMED");
                System.out.println("========================================");
                System.out.println("PNR               : " + pnr);
                System.out.println("Flight Number     : " + selectedFlight.getFlightNumber());
                System.out.println("Airline           : " + selectedFlight.getAirline());
                System.out.println("Route             : " + selectedFlight.getSource() + " -> " + selectedFlight.getDestination());
                System.out.println("Travel Class      : " + travelClass);
                System.out.println("Number of Tickets : " + numberOfTickets);
                System.out.println("Price Per Ticket  : ₹" + pricePerTicket);
                System.out.println("Total Price       : ₹" + totalPrice);
                System.out.println("Booking Status    : CONFIRMED");
                System.out.println("========================================");
                System.out.println("Please save your PNR for future booking enquiries.");
                break;
            } else if (confirmChoice.equals("2")) {
                System.out.println("\nBooking cancelled.");
                break;
            } else {
                System.out.println("Invalid choice. Please select 1 or 2.");
            }
        }
    }

    public static void searchBookingByPnr(ArrayList<Booking> bookings, Scanner scanner) {
        System.out.println("----------------------------------------");
        System.out.println("        SEARCH BOOKING BY PNR");
        System.out.println("----------------------------------------");
        System.out.print("\nEnter PNR: ");
        String searchPnr = scanner.nextLine().trim();

        boolean found = false;
        for (Booking booking : bookings) {
            if (booking.getPnr().equalsIgnoreCase(searchPnr)) {
                found = true;
                System.out.println("\n========================================");
                System.out.println("          BOOKING DETAILS");
                System.out.println("========================================");
                System.out.println("PNR               : " + booking.getPnr());
                System.out.println("Flight Number     : " + booking.getFlight().getFlightNumber());
                System.out.println("Airline           : " + booking.getFlight().getAirline());
                System.out.println("Route              : " + booking.getFlight().getSource() + " -> " + booking.getFlight().getDestination());
                System.out.println("Travel Class      : " + booking.getTravelClass());
                System.out.println("Number of Tickets : " + booking.getNumberOfTickets());
                System.out.println("Price Per Ticket  : ₹" + booking.getPricePerTicket());
                System.out.println("Total Price       : ₹" + booking.getTotalPrice());
                System.out.println("Booking Status    : " + booking.getBookingStatus());
                System.out.println("\n------------ PASSENGERS ----------------");

                int passengerIndex = 1;
                for (Passenger passenger : booking.getPassengers()) {
                    System.out.println("\nPassenger " + passengerIndex);
                    System.out.println("Name   : " + passenger.getName());
                    System.out.println("Age    : " + passenger.getAge());
                    System.out.println("Gender : " + passenger.getGender());
                    passengerIndex++;
                }
                break;
            }
        }

        if (!found) {
            System.out.println("Booking not found. Please check the PNR and try again.");
        }
    }

    public static void cancelBooking(ArrayList<Booking> bookings, Scanner scanner) {
        System.out.println("----------------------------------------");
        System.out.println("           CANCEL BOOKING");
        System.out.println("----------------------------------------");
        System.out.print("\nEnter PNR: ");
        String cancelPnr = scanner.nextLine().trim();

        boolean cancelFound = false;
        for (Booking booking : bookings) {
            if (booking.getPnr().equalsIgnoreCase(cancelPnr)) {
                cancelFound = true;
                if (booking.getBookingStatus().equals("CANCELLED")) {
                    System.out.println("Booking is already cancelled.");
                    break;
                }

                System.out.println("\n========================================");
                System.out.println("          CANCEL BOOKING");
                System.out.println("========================================");
                System.out.println("PNR               : " + booking.getPnr());
                System.out.println("Flight Number     : " + booking.getFlight().getFlightNumber());
                System.out.println("Airline           : " + booking.getFlight().getAirline());
                System.out.println("Travel Class      : " + booking.getTravelClass());
                System.out.println("Number of Tickets : " + booking.getNumberOfTickets());
                System.out.println("Total Price       : ₹" + booking.getTotalPrice());
                System.out.println("Booking Status    : " + booking.getBookingStatus());
                System.out.println("========================================");

                while (true) {
                    System.out.println("\nAre you sure you want to cancel this booking?\n");
                    System.out.println("1. Yes, Cancel Booking");
                    System.out.println("2. No, Keep Booking");
                    System.out.print("\nEnter your choice: ");

                    String cancelChoice = scanner.nextLine().trim();

                    if (cancelChoice.equals("1")) {
                        Flight flight = booking.getFlight();
                        if (booking.getTravelClass().equals("Economy")) {
                            flight.setEconomySeats(
                                flight.getEconomySeats() + booking.getNumberOfTickets()
                            );
                        } else if (booking.getTravelClass().equals("Business")) {
                            flight.setBusinessSeats(
                                flight.getBusinessSeats() + booking.getNumberOfTickets()
                            );
                        }

                        booking.setBookingStatus("CANCELLED");

                        System.out.println("\n========================================");
                        System.out.println("        BOOKING CANCELLED");
                        System.out.println("========================================");
                        System.out.println("PNR               : " + booking.getPnr());
                        System.out.println("Booking Status    : CANCELLED");
                        System.out.println("Seats have been restored successfully.");
                        System.out.println("========================================");
                        break;
                    } else if (cancelChoice.equals("2")) {
                        System.out.println("Booking cancellation aborted.");
                        break;
                    } else {
                        System.out.println("Invalid choice. Please select 1 or 2.");
                    }
                }
                break;
            }
        }

        if (!cancelFound) {
            System.out.println("Booking not found. Please check the PNR and try again.");
        }
    }
}
