public class Flight {
    // Fields
    private String flightNumber;
    private String airline;
    private String source;
    private String destination;
    private String departureTime;
    private String arrivalTime;
    private int economySeats;
    private int businessSeats;
    private double economyPrice;
    private double businessPrice;

    // Constructor to initialize all 10 fields
    public Flight(String flightNumber, String airline, String source, String destination,
            String departureTime, String arrivalTime, int economySeats, int businessSeats,
            double economyPrice, double businessPrice) {
        this.flightNumber = flightNumber;
        this.airline = airline;
        this.source = source;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.economySeats = economySeats;
        this.businessSeats = businessSeats;
        this.economyPrice = economyPrice;
        this.businessPrice = businessPrice;
    }

    // Getters and Setters
    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getEconomySeats() {
        return economySeats;
    }

    public void setEconomySeats(int economySeats) {
        this.economySeats = economySeats;
    }

    public int getBusinessSeats() {
        return businessSeats;
    }

    public void setBusinessSeats(int businessSeats) {
        this.businessSeats = businessSeats;
    }

    public double getEconomyPrice() {
        return economyPrice;
    }

    public void setEconomyPrice(double economyPrice) {
        this.economyPrice = economyPrice;
    }

    public double getBusinessPrice() {
        return businessPrice;
    }

    public void setBusinessPrice(double businessPrice) {
        this.businessPrice = businessPrice;
    }

    // Method to display flight details
    public void displayFlightDetails() {
        System.out.println("----------------------------------------");
        System.out.println("Flight Number          : " + flightNumber);
        System.out.println("Airline                : " + airline);
        System.out.println("Source                 : " + source);
        System.out.println("Destination            : " + destination);
        System.out.println("Departure Time         : " + departureTime);
        System.out.println("Arrival Time           : " + arrivalTime);
        System.out.println("Available Economy Seats: " + economySeats);
        System.out.println("Available Business Seats: " + businessSeats);
        System.out.println("Economy Price          : ₹" + economyPrice);
        System.out.println("Business Price         : ₹" + businessPrice);
        System.out.println("----------------------------------------");
    }
}
