import java.util.ArrayList;

public class Booking {
    // Private fields
    private String pnr;
    private Flight flight;
    private ArrayList<Passenger> passengers;
    private String travelClass;
    private int numberOfTickets;
    private double pricePerTicket;
    private double totalPrice;
    private String bookingStatus;

    // Constructor to initialize all fields
    public Booking(String pnr, Flight flight, ArrayList<Passenger> passengers,
                   String travelClass, int numberOfTickets, double pricePerTicket,
                   double totalPrice, String bookingStatus) {
        this.pnr = pnr;
        this.flight = flight;
        this.passengers = passengers;
        this.travelClass = travelClass;
        this.numberOfTickets = numberOfTickets;
        this.pricePerTicket = pricePerTicket;
        this.totalPrice = totalPrice;
        this.bookingStatus = bookingStatus;
    }

    // Getters and Setters
    public String getPnr() {
        return pnr;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(ArrayList<Passenger> passengers) {
        this.passengers = passengers;
    }

    public String getTravelClass() {
        return travelClass;
    }

    public void setTravelClass(String travelClass) {
        this.travelClass = travelClass;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public double getPricePerTicket() {
        return pricePerTicket;
    }

    public void setPricePerTicket(double pricePerTicket) {
        this.pricePerTicket = pricePerTicket;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    // Method to display booking details
    public void displayBookingDetails() {
        System.out.println("========================================");
        System.out.println("            BOOKING DETAILS             ");
        System.out.println("========================================");
        System.out.println("PNR               : " + pnr);
        if (flight != null) {
            System.out.println("Flight Number     : " + flight.getFlightNumber());
            System.out.println("Airline           : " + flight.getAirline());
            System.out.println("Source            : " + flight.getSource());
            System.out.println("Destination       : " + flight.getDestination());
        }
        System.out.println("Travel Class      : " + travelClass);
        System.out.println("Number of Tickets : " + numberOfTickets);
        System.out.println("Price Per Ticket  : ₹" + pricePerTicket);
        System.out.println("Total Price       : ₹" + totalPrice);
        System.out.println("Booking Status    : " + bookingStatus);
        System.out.println("\n--- Passenger Details ---");
        if (passengers != null) {
            for (Passenger passenger : passengers) {
                passenger.displayPassengerDetails();
            }
        }
        System.out.println("========================================");
    }
}
