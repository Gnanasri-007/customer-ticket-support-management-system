import java.util.Random;
    import java.util.ArrayList;
    import java.util.Scanner;
   
    class Node {
        Ticket ticket;
        Node next;
    
        public Node(Ticket ticket) {
            this.ticket = ticket;
            this.next = null;
        }
    }
    
    // Ticket class
    class Ticket {
        private static final String[] BERTH_TYPES = {
            "Lower Berth", "Middle Berth", "Upper Berth", 
            "Side Lower Berth", "Side Upper Berth"
        };
        
        private int ticketId;
        private String destination;
        private boolean isAvailable;
        private String passengerName;
        private Integer passengerAge;
        private String berth;
    
        public Ticket(int ticketId, String destination) {
            this.ticketId = ticketId;
            this.destination = destination;
            this.isAvailable = true;
            this.passengerName = null;
            this.passengerAge = null;
            this.berth = null;
        }
    
        public boolean book(String passengerName, int passengerAge) {
            if (this.isAvailable) {
                this.isAvailable = false;
                this.passengerName = passengerName;
                this.passengerAge = passengerAge;
                Random random = new Random();
                this.berth = BERTH_TYPES[random.nextInt(BERTH_TYPES.length)];
                return true;
            }
            return false;
        }
    
        public void cancel() {
            this.isAvailable = true;
            this.passengerName = null;
            this.passengerAge = null;
            this.berth = null;
        }
    
        // Getters
        public int getTicketId() { return ticketId; }
        public String getDestination() { return destination; }
        public boolean isAvailable() { return isAvailable; }
        public String getPassengerName() { return passengerName; }
        public Integer getPassengerAge() { return passengerAge; }
        public String getBerth() { return berth; }
    }
    
    // Linked List for Ticket Management
    class TicketList {
        private Node head;
    
        public TicketList() {
            this.head = null;
        }
    
        public void addTicket(Ticket ticket) {
            Node newNode = new Node(ticket);
            if (head == null) {
                head = newNode;
            } else {
                Node current = head;
                while (current.next != null) {
                    current = current.next;
                }
                current.next = newNode;
            }
        }
    
        public void displayAvailableTickets() {
            Node current = head;
            ArrayList<String> availableTickets = new ArrayList<>();
            while (current != null) {
                if (current.ticket.isAvailable()) {
                    availableTickets.add("🔹 Ticket " + current.ticket.getTicketId() + 
                                      ": " + current.ticket.getDestination());
                }
                current = current.next;
            }
            
            if (!availableTickets.isEmpty()) {
                System.out.println("\n Available Tickets:");
                for (String ticket : availableTickets) {
                    System.out.println(ticket);
                }
            } else {
                System.out.println(" No tickets available.");
            }
        }
    
        public void bookTicket(int ticketId, String passengerName, int passengerAge) {
            Node current = head;
            while (current != null) {
                if (current.ticket.getTicketId() == ticketId) {
                    if (current.ticket.book(passengerName, passengerAge)) {
                        System.out.println(" Ticket " + ticketId + " to " + 
                                         current.ticket.getDestination() + 
                                         " has been successfully booked for " + 
                                         passengerName + ", Age: " + passengerAge + "!");
                        System.out.println(" Berth Allocated: " + current.ticket.getBerth());
                    } else {
                        System.out.println(" Ticket " + ticketId + " is already booked.");
                    }
                    return;
                }
                current = current.next;
            }
            System.out.println(" Invalid Ticket ID. Please try again.");
        }
    
        public void cancelTicket(int ticketId, Scanner scanner) {
            Node current = head;
            while (current != null) {
                if (current.ticket.getTicketId() == ticketId) {
                    if (!current.ticket.isAvailable()) {
                        System.out.println(" Ticket was booked by " + 
                                         current.ticket.getPassengerName() + 
                                         ", Age: " + current.ticket.getPassengerAge() + 
                                         ", Berth: " + current.ticket.getBerth() + ".");
                        System.out.print("Do you want to cancel it? (yes/no): ");
                        String confirm = scanner.nextLine().trim().toLowerCase();
                        if (confirm.equals("yes")) {
                            current.ticket.cancel();
                            System.out.println(" Ticket " + ticketId + 
                                            " has been canceled and is now available again.");
                        } else {
                            System.out.println(" Ticket cancellation aborted.");
                        }
                    } else {
                        System.out.println(" Ticket " + ticketId + " is already available.");
                    }
                    return;
                }
                current = current.next;
            }
            System.out.println(" Invalid Ticket ID. Please try again.");
        }
    }
    
    public class TicketBookingSystem {
        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            TicketList ticketList = new TicketList();
    
            // Add sample tickets
            Ticket[] sampleTickets = {
                new Ticket(101, "New York"),
                new Ticket(102, "Los Angeles"),
                new Ticket(103, "Chicago"),
                new Ticket(104, "Houston"),
                new Ticket(105, "San Francisco")
            };
    
            for (Ticket ticket : sampleTickets) {
                ticketList.addTicket(ticket);
            }
    
            // User interaction loop
            while (true) {
                System.out.println("\n Welcome to the Ticket Booking System ");
                System.out.println(" 1 View Available Tickets");
                System.out.println(" 2 Book a Ticket");
                System.out.println(" 3 Cancel a Ticket");
                System.out.println(" 4 Exit");
    
                System.out.print("Enter your choice: ");
                String choice = scanner.nextLine();
    
                switch (choice) {
                    case "1":
                        ticketList.displayAvailableTickets();
                        break;
    
                    case "2":
                        ticketList.displayAvailableTickets();
                        System.out.print("Enter the Ticket ID you want to book: ");
                        int ticketId = Integer.parseInt(scanner.nextLine());
                        System.out.print("Enter your Name: ");
                        String passengerName = scanner.nextLine().trim();
                        System.out.print("Enter your Age: ");
                        int passengerAge = Integer.parseInt(scanner.nextLine());
                        ticketList.bookTicket(ticketId, passengerName, passengerAge);
                        break;
    
                    case "3":
                        System.out.print("Enter the Ticket ID you want to cancel: ");
                        ticketId = Integer.parseInt(scanner.nextLine());
                        ticketList.cancelTicket(ticketId, scanner);
                        break;
    
                    case "4":
                        System.out.println(" Thank you for using the Ticket Booking System. Have a great day!");
                        scanner.close();
                        return;
    
                    default:
                        System.out.println(" Invalid choice. Please try again.");
                }
            }
        }
    }
    

