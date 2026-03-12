 // FlightManagementSystem.java - Complete Version with Hash-based Data Structures (CO 04)
// Updated: 06-03-2026

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

// ==================== USER CLASS ====================
class User {
    private String fullName;
    private String email;
    private String phoneNumber;
    private String password;
    private String userId;
    
    public User(String fullName, String email, String phoneNumber, String password) {
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.userId = generateUserId();
    }
    
    private String generateUserId() {
        return "USR" + System.currentTimeMillis();
    }
    
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getUserId() { return userId; }
    
    public boolean validatePassword(String password) {
        return this.password != null && this.password.equals(password);
    }
    
    public void display() {
        System.out.println("User ID: " + userId);
        System.out.println("Name: " + fullName);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phoneNumber);
    }
}

// ==================== FLIGHT CLASS ====================
class Flight {
    private String flightNumber;
    private String from;
    private String to;
    private String departureTime;
    private String arrivalTime;
    private String status;
    private double basePrice;
    private int availableSeats;
    private int totalSeats;
    
    public Flight(String flightNumber, String from, String to, String departureTime, 
                  String arrivalTime, double basePrice, int totalSeats) {
        this.flightNumber = flightNumber;
        this.from = from;
        this.to = to;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.basePrice = basePrice;
        this.status = "On Time";
        this.availableSeats = totalSeats;
        this.totalSeats = totalSeats;
    }
    
    public String getFlightNumber() { return flightNumber; }
    public String getFrom() { return from; }
    public String getTo() { return to; }
    public String getDepartureTime() { return departureTime; }
    public String getArrivalTime() { return arrivalTime; }
    public String getStatus() { return status; }
    public double getBasePrice() { return basePrice; }
    public int getAvailableSeats() { return availableSeats; }
    public int getTotalSeats() { return totalSeats; }
    
    public void setStatus(String status) { this.status = status; }
    
    public void bookSeat() {
        if (availableSeats > 0) {
            availableSeats--;
        }
    }
    
    public void cancelSeat() {
        if (availableSeats < totalSeats) {
            availableSeats++;
        }
    }
    
    public String getRoute() {
        return from + " → " + to;
    }
    
    public void display() {
        System.out.println(flightNumber + " | " + getRoute() + " | " + departureTime + " - " + 
                          arrivalTime + " | " + status + " | Available: " + availableSeats + "/" + totalSeats + 
                          " | Price: $" + basePrice);
    }
}

// ==================== BOOKING CLASS ====================
class Booking {
    private String bookingId;
    private String userId;
    private String flightNumber;
    private String bookingDate;
    private String flightDate;
    private String[] seats;
    private int seatCount;
    private double totalPrice;
    private String status; // UPCOMING, COMPLETED, CANCELLED
    private String refundStatus;
    
    public Booking(String userId, String flightNumber, String flightDate, int maxSeats) {
        this.bookingId = generateBookingId();
        this.userId = userId;
        this.flightNumber = flightNumber;
        this.bookingDate = getCurrentDate();
        this.flightDate = flightDate;
        this.seats = new String[maxSeats];
        this.seatCount = 0;
        this.totalPrice = 0.0;
        this.status = "UPCOMING";
        this.refundStatus = "N/A";
    }
    
    private String generateBookingId() {
        return "BK" + (int)(Math.random() * 9000 + 1000);
    }
    
    private String getCurrentDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return now.format(formatter);
    }
    
    public void addSeat(String seatNumber) {
        if (seatCount < seats.length) {
            seats[seatCount] = seatNumber;
            seatCount++;
        }
    }
    
    public void setTotalPrice(double price) {
        this.totalPrice = price;
    }
    
    public void setStatus(String status) { this.status = status; }
    public void setRefundStatus(String refundStatus) { this.refundStatus = refundStatus; }
    
    public String getBookingId() { return bookingId; }
    public String getUserId() { return userId; }
    public String getFlightNumber() { return flightNumber; }
    public String getFlightDate() { return flightDate; }
    public String[] getSeats() { return seats; }
    public int getSeatCount() { return seatCount; }
    public double getTotalPrice() { return totalPrice; }
    public String getStatus() { return status; }
    public String getRefundStatus() { return refundStatus; }
    
    public String getSeatsDisplay() {
        if (seatCount == 0) return "None";
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < seatCount; i++) {
            if (i > 0) result.append(", ");
            result.append(seats[i]);
        }
        return result.toString();
    }
    
    public void display() {
        System.out.println("Booking ID: " + bookingId);
        System.out.println("Flight: " + flightNumber);
        System.out.println("Booking Date: " + bookingDate);
        System.out.println("Flight Date: " + flightDate);
        System.out.println("Seats: " + getSeatsDisplay());
        System.out.println("Total Price: $" + String.format("%.2f", totalPrice));
        System.out.println("Status: " + status);
        if (status.equals("CANCELLED")) {
            System.out.println("Refund Status: " + refundStatus);
        }
    }
}

// ==================== SEAT CLASS ====================
class Seat {
    private String seatNumber;
    private boolean isOccupied;
    private boolean isEmergencyExit;
    private String bookedBy;
    
    public Seat(String seatNumber, boolean isEmergencyExit) {
        this.seatNumber = seatNumber;
        this.isOccupied = false;
        this.isEmergencyExit = isEmergencyExit;
        this.bookedBy = null;
    }
    
    public String getSeatNumber() { return seatNumber; }
    public boolean isOccupied() { return isOccupied; }
    public boolean isEmergencyExit() { return isEmergencyExit; }
    public String getBookedBy() { return bookedBy; }
    
    public void book(String userId) {
        this.isOccupied = true;
        this.bookedBy = userId;
    }
    
    public void cancel() {
        this.isOccupied = false;
        this.bookedBy = null;
    }
    
    public void display() {
        System.out.print(seatNumber + " ");
        if (isOccupied) System.out.print("(Occupied)");
        else if (isEmergencyExit) System.out.print("(Emergency)");
        else System.out.print("(Available)");
    }
}

// ==================== STACK IMPLEMENTATION ====================
class Stack {
    private String[] elements;
    private int top;
    private int capacity;
    
    public Stack(int capacity) {
        this.capacity = capacity;
        elements = new String[capacity];
        top = -1;
    }
    
    public void push(String item) {
        if (top == capacity - 1) {
            System.out.println("Stack is full!");
            return;
        }
        top++;
        elements[top] = item;
    }
    
    public String pop() {
        if (isEmpty()) {
            return null;
        }
        String item = elements[top];
        elements[top] = null;
        top--;
        return item;
    }
    
    public String peek() {
        if (isEmpty()) {
            return null;
        }
        return elements[top];
    }
    
    public boolean isEmpty() {
        return top == -1;
    }
    
    public int size() {
        return top + 1;
    }
    
    public void display() {
        if (isEmpty()) {
            System.out.println("Stack is empty");
            return;
        }
        System.out.println("Stack contents (top to bottom):");
        for (int i = top; i >= 0; i--) {
            System.out.println("  " + elements[i]);
        }
    }
}

// ==================== QUEUE IMPLEMENTATION ====================
class Queue {
    private String[] elements;
    private int front;
    private int rear;
    private int size;
    private int capacity;
    
    public Queue(int capacity) {
        this.capacity = capacity;
        elements = new String[capacity];
        front = 0;
        rear = -1;
        size = 0;
    }
    
    public void enqueue(String item) {
        if (size == capacity) {
            System.out.println("Queue is full!");
            return;
        }
        rear = (rear + 1) % capacity;
        elements[rear] = item;
        size++;
    }
    
    public String dequeue() {
        if (isEmpty()) {
            return null;
        }
        String item = elements[front];
        elements[front] = null;
        front = (front + 1) % capacity;
        size--;
        return item;
    }
    
    public String peek() {
        if (isEmpty()) {
            return null;
        }
        return elements[front];
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    public int size() {
        return size;
    }
    
    public void display() {
        if (isEmpty()) {
            System.out.println("Queue is empty");
            return;
        }
        System.out.println("Queue contents (front to rear):");
        int count = 0;
        int index = front;
        while (count < size) {
            System.out.println("  " + elements[index]);
            index = (index + 1) % capacity;
            count++;
        }
    }
}

// ==================== SEARCHING ALGORITHMS ====================
class SearchAlgorithms {
    
    // Linear Search for User by email
    public static int linearSearchUserByEmail(User[] users, int size, String email) {
        for (int i = 0; i < size; i++) {
            if (users[i] != null && users[i].getEmail() != null && users[i].getEmail().equals(email)) {
                return i;
            }
        }
        return -1;
    }
    
    // Linear Search for User by userId
    public static int linearSearchUserById(User[] users, int size, String userId) {
        for (int i = 0; i < size; i++) {
            if (users[i] != null && users[i].getUserId() != null && users[i].getUserId().equals(userId)) {
                return i;
            }
        }
        return -1;
    }
    
    // Linear Search for Flight by flight number
    public static int linearSearchFlightByNumber(Flight[] flights, int size, String flightNumber) {
        for (int i = 0; i < size; i++) {
            if (flights[i] != null && flights[i].getFlightNumber() != null && 
                flights[i].getFlightNumber().equals(flightNumber)) {
                return i;
            }
        }
        return -1;
    }
    
    // Binary Search for Flight by flight number (requires sorted array)
    public static int binarySearchFlightByNumber(Flight[] flights, int size, String flightNumber) {
        int left = 0;
        int right = size - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (flights[mid] == null) break;
            
            int comparison = flights[mid].getFlightNumber().compareTo(flightNumber);
            
            if (comparison == 0) {
                return mid;
            } else if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }
    
    // Linear Search for Booking by booking ID
    public static int linearSearchBookingById(Booking[] bookings, int size, String bookingId) {
        for (int i = 0; i < size; i++) {
            if (bookings[i] != null && bookings[i].getBookingId() != null && 
                bookings[i].getBookingId().equals(bookingId)) {
                return i;
            }
        }
        return -1;
    }
    
    // Search bookings by user ID (returns count and populates result array)
    public static int searchBookingsByUser(Booking[] bookings, int size, String userId, 
                                           int[] resultIndices, int maxResults) {
        int count = 0;
        for (int i = 0; i < size && count < maxResults; i++) {
            if (bookings[i] != null && bookings[i].getUserId() != null && 
                bookings[i].getUserId().equals(userId)) {
                resultIndices[count] = i;
                count++;
            }
        }
        return count;
    }
    
    // Search flights by route
    public static int searchFlightsByRoute(Flight[] flights, int size, String from, String to, 
                                           int[] resultIndices, int maxResults) {
        int count = 0;
        for (int i = 0; i < size && count < maxResults; i++) {
            if (flights[i] != null && 
                flights[i].getFrom() != null && flights[i].getTo() != null &&
                flights[i].getFrom().equalsIgnoreCase(from) && 
                flights[i].getTo().equalsIgnoreCase(to)) {
                resultIndices[count] = i;
                count++;
            }
        }
        return count;
    }
}

// ==================== SORTING ALGORITHMS ====================
class SortAlgorithms {
    
    // Bubble Sort for Flights by flight number
    public static void bubbleSortFlightsByNumber(Flight[] flights, int size) {
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - i - 1; j++) {
                if (flights[j] != null && flights[j + 1] != null &&
                    flights[j].getFlightNumber().compareTo(flights[j + 1].getFlightNumber()) > 0) {
                    // Swap
                    Flight temp = flights[j];
                    flights[j] = flights[j + 1];
                    flights[j + 1] = temp;
                }
            }
        }
        System.out.println("Sorted by flight number using Bubble Sort (O(n²))");
    }
    
    // Selection Sort for Flights by departure time
    public static void selectionSortFlightsByTime(Flight[] flights, int size) {
        for (int i = 0; i < size - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < size; j++) {
                if (flights[j] != null && flights[minIndex] != null &&
                    flights[j].getDepartureTime().compareTo(flights[minIndex].getDepartureTime()) < 0) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                Flight temp = flights[i];
                flights[i] = flights[minIndex];
                flights[minIndex] = temp;
            }
        }
        System.out.println("Sorted by departure time using Selection Sort (O(n²))");
    }
    
    // Insertion Sort for Bookings by date
    public static void insertionSortBookingsByDate(Booking[] bookings, int size) {
        for (int i = 1; i < size; i++) {
            Booking key = bookings[i];
            int j = i - 1;
            
            while (j >= 0 && bookings[j] != null && key != null &&
                   bookings[j].getFlightDate().compareTo(key.getFlightDate()) > 0) {
                bookings[j + 1] = bookings[j];
                j--;
            }
            bookings[j + 1] = key;
        }
        System.out.println("Sorted by date using Insertion Sort (O(n²))");
    }
    
    // Quick Sort for Bookings by price
    public static void quickSortBookingsByPrice(Booking[] bookings, int low, int high) {
        if (low < high) {
            int pi = partition(bookings, low, high);
            quickSortBookingsByPrice(bookings, low, pi - 1);
            quickSortBookingsByPrice(bookings, pi + 1, high);
        }
    }
    
    private static int partition(Booking[] bookings, int low, int high) {
        double pivot = bookings[high].getTotalPrice();
        int i = low - 1;
        
        for (int j = low; j < high; j++) {
            if (bookings[j].getTotalPrice() <= pivot) {
                i++;
                Booking temp = bookings[i];
                bookings[i] = bookings[j];
                bookings[j] = temp;
            }
        }
        
        Booking temp = bookings[i + 1];
        bookings[i + 1] = bookings[high];
        bookings[high] = temp;
        
        return i + 1;
    }
    
    public static void quickSortBookingsByPriceWrapper(Booking[] bookings, int size) {
        quickSortBookingsByPrice(bookings, 0, size - 1);
        System.out.println("Sorted by price using Quick Sort (O(n log n))");
    }
}

// ==================== HASH TABLE IMPLEMENTATION (CO 04) ====================

/**
 * Simple HashMap implementation for CO 04 - Hash-based Data Structures
 * @param <K> Key type
 * @param <V> Value type
 */
class SimpleHashMap<K, V> {
    private static class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next; // For chaining
        
        Entry(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }
    
    private Entry<K, V>[] buckets;
    private int size;
    private int capacity;
    private static final float LOAD_FACTOR = 0.75f;
    
    @SuppressWarnings("unchecked")
    public SimpleHashMap(int capacity) {
        this.capacity = capacity;
        this.buckets = new Entry[capacity];
        this.size = 0;
    }
    
    private int hash(K key) {
        return Math.abs(key.hashCode()) % capacity;
    }
    
    // Put operation with chaining
    public void put(K key, V value) {
        int index = hash(key);
        Entry<K, V> newEntry = new Entry<>(key, value);
        
        if (buckets[index] == null) {
            buckets[index] = newEntry;
            size++;
        } else {
            Entry<K, V> current = buckets[index];
            Entry<K, V> prev = null;
            
            // Check if key already exists
            while (current != null) {
                if (current.key.equals(key)) {
                    current.value = value; // Update existing
                    return;
                }
                prev = current;
                current = current.next;
            }
            
            // Add to chain (at the end)
            prev.next = newEntry;
            size++;
        }
        
        // Check load factor and resize if needed
        if ((float) size / capacity > LOAD_FACTOR) {
            resize();
        }
    }
    
    // Get operation with chaining traversal
    public V get(K key) {
        int index = hash(key);
        Entry<K, V> current = buckets[index];
        
        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }
    
    // Remove operation
    public V remove(K key) {
        int index = hash(key);
        Entry<K, V> current = buckets[index];
        Entry<K, V> prev = null;
        
        while (current != null) {
            if (current.key.equals(key)) {
                if (prev == null) {
                    buckets[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return current.value;
            }
            prev = current;
            current = current.next;
        }
        return null;
    }
    
    public boolean containsKey(K key) {
        return get(key) != null;
    }
    
    public int size() {
        return size;
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    @SuppressWarnings("unchecked")
    private void resize() {
        int newCapacity = capacity * 2;
        Entry<K, V>[] newBuckets = new Entry[newCapacity];
        
        // Rehash all entries
        for (int i = 0; i < capacity; i++) {
            Entry<K, V> current = buckets[i];
            while (current != null) {
                int newIndex = Math.abs(current.key.hashCode()) % newCapacity;
                Entry<K, V> next = current.next;
                
                // Insert at beginning of new bucket
                current.next = newBuckets[newIndex];
                newBuckets[newIndex] = current;
                
                current = next;
            }
        }
        
        buckets = newBuckets;
        capacity = newCapacity;
        System.out.println("HashMap resized to " + newCapacity + " buckets");
    }
    
    public void displayStats() {
        int usedBuckets = 0;
        int longestChain = 0;
        int totalEntries = 0;
        
        for (int i = 0; i < capacity; i++) {
            if (buckets[i] != null) {
                usedBuckets++;
                int chainLength = 0;
                Entry<K, V> current = buckets[i];
                while (current != null) {
                    chainLength++;
                    totalEntries++;
                    current = current.next;
                }
                if (chainLength > longestChain) {
                    longestChain = chainLength;
                }
            }
        }
        
        System.out.println("\n=== HashMap Statistics ===");
        System.out.println("Capacity: " + capacity);
        System.out.println("Size: " + size);
        System.out.println("Used Buckets: " + usedBuckets);
        System.out.println("Load Factor: " + String.format("%.2f", (float) size / capacity));
        System.out.println("Longest Chain: " + longestChain);
        System.out.println("Average Chain Length: " + 
                          String.format("%.2f", usedBuckets > 0 ? (float) size / usedBuckets : 0));
    }
}

/**
 * Simple HashSet implementation using HashMap
 */
class SimpleHashSet<E> {
    private static final Object PRESENT = new Object();
    private SimpleHashMap<E, Object> map;
    
    public SimpleHashSet(int capacity) {
        map = new SimpleHashMap<>(capacity);
    }
    
    public boolean add(E element) {
        if (map.containsKey(element)) {
            return false;
        }
        map.put(element, PRESENT);
        return true;
    }
    
    public boolean contains(E element) {
        return map.containsKey(element);
    }
    
    public boolean remove(E element) {
        return map.remove(element) != null;
    }
    
    public int size() {
        return map.size();
    }
    
    public boolean isEmpty() {
        return map.isEmpty();
    }
}

// ==================== FLIGHT MANAGEMENT SYSTEM ====================
public class FlightManagementSystem {
    private static final int MAX_USERS = 100;
    private static final int MAX_FLIGHTS = 50;
    private static final int MAX_BOOKINGS = 200;
    private static final int MAX_SEATS = 30;
    private static final int MAX_NOTIFICATIONS = 50;
    private static final int MAX_CANCELLATIONS = 50;
    
    // Array-based storage
    private User[] users;
    private int userCount;
    
    private Flight[] flights;
    private int flightCount;
    
    private Booking[] bookings;
    private int bookingCount;
    
    private Seat[] seats;
    private int seatCount;
    
    // CO 04: Hash-based data structures
    private SimpleHashMap<String, User> userHashMap;        // O(1) user lookup by email
    private SimpleHashMap<String, Flight> flightHashMap;    // O(1) flight lookup by flight number
    private SimpleHashMap<String, Booking> bookingHashMap;  // O(1) booking lookup by booking ID
    private SimpleHashMap<String, SimpleHashSet<String>> userBookingsMap; // User's bookings
    private SimpleHashSet<String> emailHashSet;             // Prevent duplicate emails
    
    // Stack and Queue
    private String[] notifications;
    private int notificationCount;
    private int notificationTop;
    
    private String[] cancellationRequests;
    private int cancellationCount;
    private int cancellationFront;
    private int cancellationRear;
    
    private Scanner scanner;
    private String currentUserId;
    
    public FlightManagementSystem() {
        // Initialize array-based storage
        users = new User[MAX_USERS];
        userCount = 0;
        
        flights = new Flight[MAX_FLIGHTS];
        flightCount = 0;
        
        bookings = new Booking[MAX_BOOKINGS];
        bookingCount = 0;
        
        seats = new Seat[MAX_SEATS];
        seatCount = 0;
        
        // CO 04: Initialize hash-based structures
        userHashMap = new SimpleHashMap<>(MAX_USERS);
        flightHashMap = new SimpleHashMap<>(MAX_FLIGHTS);
        bookingHashMap = new SimpleHashMap<>(MAX_BOOKINGS);
        userBookingsMap = new SimpleHashMap<>(MAX_USERS);
        emailHashSet = new SimpleHashSet<>(MAX_USERS);
        
        // Initialize stack and queue
        notifications = new String[MAX_NOTIFICATIONS];
        notificationCount = 0;
        notificationTop = -1;
        
        cancellationRequests = new String[MAX_CANCELLATIONS];
        cancellationCount = 0;
        cancellationFront = 0;
        cancellationRear = -1;
        
        scanner = new Scanner(System.in);
        currentUserId = null;
        
        initializeData();
    }
    
    private void initializeData() {
        // Add sample flights with different prices
        addFlight(new Flight("SW101", "New York (JFK)", "London (LHR)", "10:30 AM", "10:45 PM", 850, 30));
        addFlight(new Flight("SW202", "London (LHR)", "Tokyo (HND)", "09:15 AM", "10:30 AM (+1)", 1200, 25));
        addFlight(new Flight("SW303", "Tokyo (HND)", "Singapore (SIN)", "02:45 PM", "08:30 PM", 950, 20));
        addFlight(new Flight("SW404", "Dubai (DXB)", "Sydney (SYD)", "11:20 PM", "06:15 PM (+1)", 2100, 15));
        addFlight(new Flight("SW505", "Paris (CDG)", "New York (JFK)", "07:30 AM", "10:15 AM", 680, 28));
        addFlight(new Flight("SW606", "Frankfurt (FRA)", "Dubai (DXB)", "08:45 AM", "05:30 PM", 750, 22));
        addFlight(new Flight("SW707", "Singapore (SIN)", "Sydney (SYD)", "09:30 AM", "07:45 PM", 890, 18));
        addFlight(new Flight("SW808", "London (LHR)", "New York (JFK)", "06:00 PM", "08:30 PM", 790, 32));
        
        // Initialize seats
        String[] rows = {"1", "2", "3", "4", "5"};
        String[] cols = {"A", "B", "C", "D", "E", "F"};
        
        for (int r = 0; r < rows.length; r++) {
            for (int c = 0; c < cols.length; c++) {
        
                String seatNum = rows[r] + cols[c];
                boolean isEmergency = rows[r].equals("3"); // Row 3 are emergency exit seats
                addSeat(new Seat(seatNum, isEmergency));
            }
        }
        
        // Mark some seats as occupied for demo
        if (seatCount > 0) {
            seats[2].book("DEMO");  // 1C
            seats[3].book("DEMO");  // 1D
            seats[8].book("DEMO");  // 2C
            seats[9].book("DEMO");  // 2D
            seats[16].book("DEMO"); // 3E (emergency exit)
            seats[17].book("DEMO"); // 3F (emergency exit)
            seats[22].book("DEMO"); // 4E
            seats[23].book("DEMO"); // 4F
            seats[27].book("DEMO"); // 5C
            seats[28].book("DEMO"); // 5D
        }
        
        // Add a demo user for testing
        addUser(new User("John Doe", "john@example.com", "1234567890", "password123"));
        addUser(new User("Jane Smith", "jane@example.com", "0987654321", "password456"));
    }
    
    private void addUser(User user) {
        if (userCount < MAX_USERS) {
            // CO 04: Check for duplicate email using HashSet
            if (emailHashSet.contains(user.getEmail())) {
                System.out.println("Email already exists! User not added.");
                return;
            }
            
            users[userCount] = user;
            
            // CO 04: Add to HashMap for O(1) lookup
            userHashMap.put(user.getEmail(), user);
            emailHashSet.add(user.getEmail());
            
            // Initialize user's bookings set
            userBookingsMap.put(user.getUserId(), new SimpleHashSet<>(10));
            
            userCount++;
            System.out.println("User added successfully. Total users: " + userCount);
        } else {
            System.out.println("Cannot add more users. Maximum limit reached.");
        }
    }
    
    private void addFlight(Flight flight) {
        if (flightCount < MAX_FLIGHTS) {
            flights[flightCount] = flight;
            
            // CO 04: Add to HashMap for O(1) lookup
            flightHashMap.put(flight.getFlightNumber(), flight);
            
            flightCount++;
        }
    }
    
    private void addBooking(Booking booking) {
        if (bookingCount < MAX_BOOKINGS) {
            bookings[bookingCount] = booking;
            
            // CO 04: Add to HashMap for O(1) lookup
            bookingHashMap.put(booking.getBookingId(), booking);
            
            // CO 04: Add to user's bookings set
            SimpleHashSet<String> userBookings = userBookingsMap.get(booking.getUserId());
            if (userBookings != null) {
                userBookings.add(booking.getBookingId());
            }
            
            bookingCount++;
        }
    }
    
    private void addSeat(Seat seat) {
        if (seatCount < MAX_SEATS) {
            seats[seatCount] = seat;
            seatCount++;
        }
    }
    
    // Stack operations for notifications
    private void pushNotification(String notification) {
        if (notificationTop < MAX_NOTIFICATIONS - 1) {
            notificationTop++;
            notifications[notificationTop] = notification;
            notificationCount++;
        } else {
            System.out.println("Notification stack is full!");
        }
    }
    
    private String popNotification() {
        if (notificationTop >= 0) {
            String notif = notifications[notificationTop];
            notifications[notificationTop] = null;
            notificationTop--;
            notificationCount--;
            return notif;
        }
        return null;
    }
    
    private void displayNotifications() {
        if (notificationTop < 0) {
            System.out.println("No notifications");
            return;
        }
        System.out.println("\n=== Notifications (Latest First - Stack LIFO) ===");
        for (int i = notificationTop; i >= 0; i--) {
            System.out.println((notificationTop - i + 1) + ". " + notifications[i]);
        }
    }
    
    // Queue operations for cancellation requests
    private void enqueueCancellation(String bookingId) {
        if (cancellationCount < MAX_CANCELLATIONS) {
            cancellationRear = (cancellationRear + 1) % MAX_CANCELLATIONS;
            cancellationRequests[cancellationRear] = bookingId;
            cancellationCount++;
            System.out.println("Cancellation request for " + bookingId + " added to queue");
        } else {
            System.out.println("Cancellation queue is full!");
        }
    }
    
    private String dequeueCancellation() {
        if (cancellationCount > 0) {
            String bookingId = cancellationRequests[cancellationFront];
            cancellationRequests[cancellationFront] = null;
            cancellationFront = (cancellationFront + 1) % MAX_CANCELLATIONS;
            cancellationCount--;
            return bookingId;
        }
        return null;
    }
    
    private void processCancellationQueue() {
        if (cancellationCount == 0) {
            System.out.println("No pending cancellations");
            return;
        }
        
        System.out.println("\n=== Processing Cancellation Queue (FIFO) ===");
        int processed = 0;
        while (cancellationCount > 0 && processed < 5) {
            String bookingId = dequeueCancellation();
            if (bookingId != null) {
                System.out.println("Processing cancellation: " + bookingId);
                
                // CO 04: O(1) lookup using HashMap
                Booking booking = bookingHashMap.get(bookingId);
                if (booking != null) {
                    booking.setStatus("CANCELLED");
                    booking.setRefundStatus("Processed (80% refund)");
                    
                    // Calculate refund (80% of ticket price)
                    double refundAmount = booking.getTotalPrice() * 0.8;
                    
                    // Update seat availability
                    String[] seats_booked = booking.getSeats();
                    for (int i = 0; i < booking.getSeatCount(); i++) {
                        for (int j = 0; j < seatCount; j++) {
                            if (seats[j].getSeatNumber().equals(seats_booked[i])) {
                                seats[j].cancel();
                                break;
                            }
                        }
                    }
                    
                    // Update flight available seats
                    Flight flight = flightHashMap.get(booking.getFlightNumber());
                    if (flight != null) {
                        for (int i = 0; i < booking.getSeatCount(); i++) {
                            flight.cancelSeat();
                        }
                    }
                    
                    pushNotification("Booking " + bookingId + " cancelled - Refund: $" + String.format("%.2f", refundAmount));
                    System.out.println("Booking " + bookingId + " cancelled - Refund processed: $" + String.format("%.2f", refundAmount));
                }
            }
            processed++;
        }
        System.out.println("Processed " + processed + " cancellations");
    }
    
    private String getCurrentTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return now.format(formatter);
    }
    
    private String getCurrentDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return now.format(formatter);
    }
    
    private int getIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    private double getDoubleInput() {
        try {
            return Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
    
    // CO 04: Demo method to show hash-based lookup performance
    private void demonstrateHashPerformance() {
        System.out.println("\n========== HASH-BASED DATA STRUCTURES DEMO (CO 04) ==========");
        
        // Show HashMap statistics
        userHashMap.displayStats();
        
        // Demonstrate O(1) lookup vs O(n) linear search
        System.out.println("\n=== Performance Comparison ===");
        
        // Linear search demo
        long startTime = System.nanoTime();
        int linearIndex = SearchAlgorithms.linearSearchUserByEmail(users, userCount, "john@example.com");
        long linearTime = System.nanoTime() - startTime;
        
        // HashMap lookup demo
        startTime = System.nanoTime();
        User hashUser = userHashMap.get("john@example.com");
        long hashTime = System.nanoTime() - startTime;
        
        System.out.println("Linear Search (O(n)): " + linearTime + " ns");
        System.out.println("HashMap Lookup (O(1)): " + hashTime + " ns");
        if (hashTime > 0) {
            System.out.println("HashMap is " + (linearTime / hashTime) + "x faster!");
        }
        
        // Demonstrate HashSet duplicate prevention
        System.out.println("\n=== HashSet Duplicate Prevention ===");
        String testEmail = "test@example.com";
        boolean added1 = emailHashSet.add(testEmail);
        boolean added2 = emailHashSet.add(testEmail);
        System.out.println("First add: " + (added1 ? "Success" : "Failed"));
        System.out.println("Second add (duplicate): " + (added2 ? "Success" : "Failed - prevented by HashSet!"));
        
        // Demonstrate chaining
        System.out.println("\n=== HashMap Chaining Demonstration ===");
        SimpleHashMap<Integer, String> chainingDemo = new SimpleHashMap<>(5);
        for (int i = 1; i <= 12; i++) {
            chainingDemo.put(i, "Value" + i);
        }
        chainingDemo.displayStats();
    }
    
    // ==================== USER INTERFACE METHODS ====================
    
    public void run() {
        while (true) {
            if (currentUserId == null) {
                showMainMenu();
            } else {
                showUserMenu();
            }
        }
    }
    
    private void showMainMenu() {
        System.out.println("\n=============================================");
        System.out.println("   SKYWINGS FLIGHT MANAGEMENT SYSTEM");
        System.out.println("=============================================");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. View All Flights");
        System.out.println("4. Check Flight Status");
        System.out.println("5. Demo Hash-based Structures (CO 04)");
        System.out.println("6. Exit");
        System.out.println("---------------------------------------------");
        System.out.print("Enter your choice: ");
        
        int choice = getIntInput();
        
        switch (choice) {
            case 1: login(); break;
            case 2: register(); break;
            case 3: displayAllFlights(); break;
            case 4: checkFlightStatus(); break;
            case 5: demonstrateHashPerformance(); break;
            case 6: 
                System.out.println("\nThank you for using SkyWings!");
                System.out.println("Total bookings made: " + bookingCount);
                System.out.println("Goodbye!");
                System.exit(0);
                break;
            default: System.out.println("Invalid choice!");
        }
    }
    
    private void showUserMenu() {
        System.out.println("\n=============================================");
        System.out.println("   USER MENU - Welcome Back!");
        System.out.println("=============================================");
        System.out.println("1. Search Flights by Route");
        System.out.println("2. Book a Flight");
        System.out.println("3. View My Bookings");
        System.out.println("4. Request Cancellation");
        System.out.println("5. View Notifications (Stack)");
        System.out.println("6. Process Cancellations (Queue)");
        System.out.println("7. Sort Flights (Algorithm Demo)");
        System.out.println("8. Fast Lookup Demo (HashMap)");
        System.out.println("9. Logout");
        System.out.println("---------------------------------------------");
        System.out.print("Enter your choice: ");
        
        int choice = getIntInput();
        
        switch (choice) {
            case 1: searchFlights(); break;
            case 2: bookFlight(); break;
            case 3: viewMyBookings(); break;
            case 4: requestCancellation(); break;
            case 5: displayNotifications(); break;
            case 6: processCancellationQueue(); break;
            case 7: sortFlightsMenu(); break;
            case 8: demonstrateFastLookup(); break;
            case 9: 
                currentUserId = null;
                System.out.println("Logged out successfully!");
                break;
            default: System.out.println("Invalid choice!");
        }
    }
    
    private void login() {
        System.out.print("Enter email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();
        
        System.out.println("Attempting login with email: " + email);
        
        // CO 04: O(1) lookup using HashMap
        User user = userHashMap.get(email);
        
        if (user != null) {
            if (user.validatePassword(password)) {
                currentUserId = user.getUserId();
                System.out.println("\n✅ Login successful! Welcome " + user.getFullName());
                pushNotification("Logged in at " + getCurrentTime());
            } else {
                System.out.println("\n❌ Invalid password!");
            }
        } else {
            System.out.println("\n❌ Email not found!");
            System.out.println("Total registered users: " + userHashMap.size());
        }
    }
    
    private void register() {
        System.out.println("\n=== New User Registration ===");
        System.out.print("Enter full name: ");
        String name = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine().trim();
        
        // CO 04: Check for duplicate email using HashSet
        if (emailHashSet.contains(email)) {
            System.out.println("\n❌ Email already registered! Please use a different email.");
            return;
        }
        
        System.out.print("Enter phone number: ");
        String phone = scanner.nextLine().trim();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Confirm password: ");
        String confirm = scanner.nextLine();
        
        if (!password.equals(confirm)) {
            System.out.println("\n❌ Passwords do not match!");
            return;
        }
        
        User newUser = new User(name, email, phone, password);
        addUser(newUser);
        System.out.println("\n✅ Registration successful! Please login.");
        pushNotification("New user registered: " + name);
    }
    
    private void displayAllFlights() {
        if (flightCount == 0) {
            System.out.println("No flights available");
            return;
        }
        System.out.println("\n=== AVAILABLE FLIGHTS ===");
        System.out.println("-----------------------------------------------------------------");
        for (int i = 0; i < flightCount; i++) {
            System.out.print((i + 1) + ". ");
            flights[i].display();
        }
        System.out.println("-----------------------------------------------------------------");
    }
    
    private void searchFlights() {
        System.out.println("\n=== Search Flights ===");
        System.out.print("Enter departure city (e.g., New York (JFK)): ");
        String from = scanner.nextLine();
        System.out.print("Enter destination city (e.g., London (LHR)): ");
        String to = scanner.nextLine();
        
        int[] results = new int[20];
        int count = SearchAlgorithms.searchFlightsByRoute(flights, flightCount, from, to, results, 20);
        
        if (count == 0) {
            System.out.println("\n❌ No flights found for this route");
            return;
        }
        
        System.out.println("\n=== FLIGHTS FOUND (" + count + ") ===");
        System.out.println("-----------------------------------------------------------------");
        for (int i = 0; i < count; i++) {
            System.out.print((i + 1) + ". ");
            flights[results[i]].display();
        }
        System.out.println("-----------------------------------------------------------------");
    }
    
    private void checkFlightStatus() {
        System.out.print("Enter flight number (e.g., SW101): ");
        String flightNum = scanner.nextLine().toUpperCase().trim();
        
        // CO 04: O(1) lookup using HashMap
        Flight flight = flightHashMap.get(flightNum);
        
        if (flight == null) {
            System.out.println("\n❌ Flight not found!");
            return;
        }
        
        System.out.println("\n=== FLIGHT STATUS ===");
        System.out.println("-----------------------------------------------------------------");
        flight.display();
        System.out.println("-----------------------------------------------------------------");
    }
    
    private void bookFlight() {
        if (currentUserId == null) {
            System.out.println("❌ Please login first!");
            return;
        }
        
        displayAllFlights();
        System.out.print("\nEnter flight number to book (e.g., SW101): ");
        String flightNum = scanner.nextLine().toUpperCase().trim();
        
        // CO 04: O(1) lookup using HashMap
        Flight flight = flightHashMap.get(flightNum);
        
        if (flight == null) {
            System.out.println("\n❌ Flight not found!");
            return;
        }
        
        if (flight.getAvailableSeats() <= 0) {
            System.out.println("\n❌ No seats available on this flight!");
            return;
        }
        
        System.out.println("\n=== SEAT MAP ===");
        displaySeatMap();
        
        System.out.print("\nEnter number of seats to book (1-" + flight.getAvailableSeats() + "): ");
        int numSeats = getIntInput();
        
        if (numSeats <= 0 || numSeats > flight.getAvailableSeats()) {
            System.out.println("❌ Invalid number of seats! Available: " + flight.getAvailableSeats());
            return;
        }
        
        // Get flight date (7 days from now)
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String flightDate = now.plusDays(7).format(formatter);
        
        Booking booking = new Booking(currentUserId, flightNum, flightDate, numSeats);
        double totalPrice = 0;
        int seatsBooked = 0;
        
        System.out.println("\nSelect " + numSeats + " seats (format: row+letter, e.g., 1A, 2B, 3C):");
        
        while (seatsBooked < numSeats) {
            System.out.print("Seat " + (seatsBooked + 1) + ": ");
            String seatNum = scanner.nextLine().toUpperCase().trim();
            
            // Validate seat format (e.g., 1A, 2B, 3C, 4D, 5E, 5F)
            if (!seatNum.matches("[1-5][A-F]")) {
                System.out.println("❌ Invalid seat format! Please use format like 1A, 2B, 3C (row 1-5, letter A-F)");
                continue;
            }
            
            boolean seatFound = false;
            boolean seatAvailable = false;
            int seatIndex = -1;
            
            for (int j = 0; j < seatCount; j++) {
                if (seats[j].getSeatNumber().equals(seatNum)) {
                    seatFound = true;
                    seatIndex = j;
                    if (!seats[j].isOccupied()) {
                        seatAvailable = true;
                    }
                    break;
                }
            }
            
            if (!seatFound) {
                System.out.println("❌ Seat " + seatNum + " does not exist!");
            } else if (!seatAvailable) {
                System.out.println("❌ Seat " + seatNum + " is already occupied! Please choose another seat.");
            } else {
                // Book the seat
                seats[seatIndex].book(currentUserId);
                booking.addSeat(seatNum);
                
                // Calculate price with potential extras
                double seatPrice = flight.getBasePrice();
                
                // Add extra for emergency exit seats (premium)
                if (seats[seatIndex].isEmergencyExit()) {
                    seatPrice += 50; // Extra $50 for emergency exit seats with more legroom
                    System.out.println("  ✨ Emergency exit seat - additional $50 (extra legroom)");
                }
                
                totalPrice += seatPrice;
                seatsBooked++;
                
                System.out.println("  ✅ Seat " + seatNum + " booked at $" + String.format("%.2f", seatPrice));
            }
        }
        
        // Apply group discount (10% off for bookings of 3+ seats)
        if (numSeats >= 3) {
            double discount = totalPrice * 0.10;
            totalPrice -= discount;
            System.out.println("\n🎉 Group booking discount (10%): -$" + String.format("%.2f", discount));
        }
        
        // Add service fee
        double serviceFee = 25.00;
        totalPrice += serviceFee;
        System.out.println("Service fee: +$" + String.format("%.2f", serviceFee));
        
        booking.setTotalPrice(totalPrice);
        addBooking(booking);
        
        // Update flight available seats
        for (int i = 0; i < numSeats; i++) {
            flight.bookSeat();
        }
        
        System.out.println("\n==========================================");
        System.out.println("        BOOKING CONFIRMATION");
        System.out.println("==========================================");
        System.out.println("Booking ID: " + booking.getBookingId());
        System.out.println("Flight: " + flight.getFlightNumber() + " - " + flight.getRoute());
        System.out.println("Date: " + flightDate);
        System.out.println("Passenger: " + getUserName(currentUserId));
        System.out.println("Seats: " + booking.getSeatsDisplay());
        System.out.println("Number of Seats: " + numSeats);
        System.out.println("Price per Seat: $" + flight.getBasePrice());
        System.out.println("------------------------------------------");
        System.out.println("SUBTOTAL: $" + String.format("%.2f", totalPrice - serviceFee));
        System.out.println("Service Fee: $" + String.format("%.2f", serviceFee));
        System.out.println("------------------------------------------");
        System.out.println("TOTAL AMOUNT: $" + String.format("%.2f", totalPrice));
        System.out.println("==========================================");
        
        pushNotification("New booking created: " + booking.getBookingId() + " for $" + String.format("%.2f", totalPrice));
    }
    
    private String getUserName(String userId) {
        for (int i = 0; i < userCount; i++) {
            if (users[i] != null && users[i].getUserId().equals(userId)) {
                return users[i].getFullName();
            }
        }
        return "Unknown";
    }
    
    private void displaySeatMap() {
        System.out.println("\n     A    B    C    D    E    F");
        System.out.println("   +----+----+----+----+----+----+");
        
        for (int i = 0; i < 5; i++) {
            System.out.print((i + 1) + "  ");
            for (int j = 0; j < 6; j++) {
                int index = i * 6 + j;
                if (index < seatCount) {
                    if (seats[index].isOccupied()) {
                        System.out.print("| \u001B[31m X \u001B[0m "); // Red X for occupied
                    } else if (seats[index].isEmergencyExit()) {
                        System.out.print("| \u001B[33m E \u001B[0m "); // Yellow E for emergency
                    } else {
                        System.out.print("| \u001B[32m   \u001B[0m "); // Green empty
                    }
                }
            }
            System.out.println("|");
            System.out.println("   +----+----+----+----+----+----+");
        }
        
        System.out.println("\n\u001B[32m[ ]\u001B[0m Available  \u001B[31m[X]\u001B[0m Occupied  \u001B[33m[E]\u001B[0m Emergency Exit (+$50)");
        System.out.println("Note: Row 3 seats are emergency exits with extra legroom");
    }
    
    private void viewMyBookings() {
        if (currentUserId == null) {
            System.out.println("❌ Please login first!");
            return;
        }
        
        // CO 04: Get user's bookings from HashMap
        SimpleHashSet<String> userBookingIds = userBookingsMap.get(currentUserId);
        
        if (userBookingIds == null || userBookingIds.isEmpty()) {
            System.out.println("\n📭 No bookings found");
            return;
        }
        
        System.out.println("\n==========================================");
        System.out.println("           MY BOOKINGS");
        System.out.println("==========================================");
        
        int count = 1;
        double totalSpent = 0;
        
        for (int i = 0; i < bookingCount; i++) {
            if (bookings[i] != null && bookings[i].getUserId() != null && 
                bookings[i].getUserId().equals(currentUserId)) {
                System.out.println("\n--- Booking " + count + " ---");
                bookings[i].display();
                totalSpent += bookings[i].getTotalPrice();
                count++;
            }
        }
        
        System.out.println("\n------------------------------------------");
        System.out.println("TOTAL SPENT: $" + String.format("%.2f", totalSpent));
        System.out.println("==========================================");
    }
    
    private void requestCancellation() {
        if (currentUserId == null) {
            System.out.println("❌ Please login first!");
            return;
        }
        
        viewMyBookings();
        
        System.out.print("\nEnter booking ID to cancel: ");
        String bookingId = scanner.nextLine().toUpperCase().trim();
        
        // CO 04: O(1) lookup using HashMap
        Booking booking = bookingHashMap.get(bookingId);
        
        if (booking == null) {
            System.out.println("❌ Booking not found!");
            return;
        }
        
        if (!booking.getUserId().equals(currentUserId)) {
            System.out.println("❌ This booking does not belong to you!");
            return;
        }
        
        if (!booking.getStatus().equals("UPCOMING")) {
            System.out.println("❌ This booking cannot be cancelled (status: " + booking.getStatus() + ")");
            return;
        }
        
        System.out.println("\nBooking Details:");
        booking.display();
        
        double refundAmount = booking.getTotalPrice() * 0.8;
        System.out.println("\nRefund amount (80%): $" + String.format("%.2f", refundAmount));
        
        System.out.print("\nConfirm cancellation? (yes/no): ");
        String confirm = scanner.nextLine().toLowerCase().trim();
        
        if (confirm.equals("yes") || confirm.equals("y")) {
            enqueueCancellation(bookingId);
            pushNotification("Cancellation requested for " + bookingId);
            System.out.println("✅ Cancellation request submitted. It will be processed shortly.");
        } else {
            System.out.println("Cancellation cancelled.");
        }
    }
    
    private void sortFlightsMenu() {
        System.out.println("\n=== SORT FLIGHTS (Algorithm Demonstration) ===");
        System.out.println("1. Sort by Flight Number (Bubble Sort - O(n²))");
        System.out.println("2. Sort by Departure Time (Selection Sort - O(n²))");
        System.out.println("3. Sort by Price (Quick Sort - O(n log n))");
        System.out.print("Enter choice: ");
        
        int choice = getIntInput();
        
        switch (choice) {
            case 1:
                SortAlgorithms.bubbleSortFlightsByNumber(flights, flightCount);
                break;
            case 2:
                SortAlgorithms.selectionSortFlightsByTime(flights, flightCount);
                break;
            case 3:
                // Sort flights by price using quick sort
                quickSortFlightsByPrice(flights, 0, flightCount - 1);
                System.out.println("Sorted by price using Quick Sort (O(n log n))");
                break;
            default:
                System.out.println("Invalid choice!");
                return;
        }
        
        displayAllFlights();
    }
    
    private void quickSortFlightsByPrice(Flight[] flights, int low, int high) {
        if (low < high) {
            int pi = partitionFlights(flights, low, high);
            quickSortFlightsByPrice(flights, low, pi - 1);
            quickSortFlightsByPrice(flights, pi + 1, high);
        }
    }
    
    private int partitionFlights(Flight[] flights, int low, int high) {
        double pivot = flights[high].getBasePrice();
        int i = low - 1;
        
        for (int j = low; j < high; j++) {
            if (flights[j].getBasePrice() <= pivot) {
                i++;
                Flight temp = flights[i];
                flights[i] = flights[j];
                flights[j] = temp;
            }
        }
        
        Flight temp = flights[i + 1];
        flights[i + 1] = flights[high];
        flights[high] = temp;
        
        return i + 1;
    }
    
    private void demonstrateFastLookup() {
        System.out.println("\n=== FAST LOOKUP DEMO (HashMap O(1) vs Linear Search O(n)) ===");
        
        System.out.print("Enter email to lookup: ");
        String email = scanner.nextLine().trim();
        
        // HashMap lookup
        long startTime = System.nanoTime();
        User hashUser = userHashMap.get(email);
        long hashTime = System.nanoTime() - startTime;
        
        // Linear search
        startTime = System.nanoTime();
        int linearIndex = SearchAlgorithms.linearSearchUserByEmail(users, userCount, email);
        long linearTime = System.nanoTime() - startTime;
        
        System.out.println("\nResults:");
        if (hashUser != null) {
            System.out.println("✅ User found via HashMap in " + hashTime + " ns:");
            hashUser.display();
        } else {
            System.out.println("❌ User not found via HashMap in " + hashTime + " ns");
        }
        
        System.out.println("\nPerformance Comparison:");
        System.out.println("  HashMap Lookup (O(1)): " + hashTime + " ns");
        System.out.println("  Linear Search (O(n)): " + linearTime + " ns");
        
        if (hashTime > 0 && linearTime > 0) {
            double speedup = (double) linearTime / hashTime;
            System.out.println("  HashMap is " + String.format("%.2f", speedup) + "x faster!");
        }
        
        // Show total users
        System.out.println("\nTotal users in system: " + userCount);
        System.out.println("Total users in HashMap: " + userHashMap.size());
    }
    
    // ==================== MAIN METHOD ====================
    
    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("   SKYWINGS FLIGHT MANAGEMENT SYSTEM");
        System.out.println("   with Complete DSA Implementation");
        System.out.println("=================================================");
        System.out.println("Date: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        System.out.println("Student ID: 173030211");
        System.out.println("=================================================");
        
        FlightManagementSystem system = new FlightManagementSystem();
        
        System.out.println("\n📋 TEST ACCOUNT:");
        System.out.println("   Email: john@example.com");
        System.out.println("   Password: password123");
        System.out.println("\n   Email: jane@example.com");
        System.out.println("   Password: password456");
        System.out.println("\n📌 FEATURES IMPLEMENTED:");
        System.out.println("   ✅ CO 01: Sorting & Searching Algorithms");
        System.out.println("   ✅ CO 02: Arrays & Linked Lists (via chaining)");
        System.out.println("   ✅ CO 03: Stacks (Notifications) & Queues (Cancellations)");
        System.out.println("   ✅ CO 04: HashMaps & HashSets with chaining");
        System.out.println("   ✅ CO 05: Complete Flight Management Application");
        System.out.println("   ✅ CO 06: Full DSA Implementation Skills");
        System.out.println("=================================================");
        
        system.run();
    }
}