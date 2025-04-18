
import java.util.*;

public class BookingSystem {
    private Map<String, Patient> patients;
    private List<Physiotherapist> physios;
    private Scanner scanner;
    private Map<String, Treatment> allBookings;

    public BookingSystem() {
        patients = new HashMap<>();
        physios = new ArrayList<>();
        scanner = new Scanner(System.in);
        allBookings = new HashMap<>();
        initializeSampleData();
    }

    private void initializeSampleData() {
        Physiotherapist p1 = new Physiotherapist("P1", "Dr. Smith", "123 Main St", "123456", List.of("Physiotherapy"));
        Physiotherapist p2 = new Physiotherapist("P2", "Dr. Jane", "456 High St", "654321", List.of("Rehabilitation", "Massage"));

        Treatment t1 = new Treatment("T1", "Massage", "Monday 1st May 10:00");
        Treatment t2 = new Treatment("T2", "Physiotherapy", "Tuesday 2nd May 11:00");
        Treatment t3 = new Treatment("T3", "Rehabilitation", "Wednesday 3rd May 09:00");
        Treatment t4 = new Treatment("T4", "Massage", "Thursday 4th May 14:00");

        p1.addTreatment(t1);
        p1.addTreatment(t2);
        p2.addTreatment(t3);
        p2.addTreatment(t4);

        physios.add(p1);
        physios.add(p2);

        allBookings.put(t1.getId(), t1);
        allBookings.put(t2.getId(), t2);
        allBookings.put(t3.getId(), t3);
        allBookings.put(t4.getId(), t4);
    }

    public void start() {
        while (true) {
            System.out.println("\n--- Boost Physio Clinic ---");
            System.out.println("1. Add Patient");
            System.out.println("2. Remove Patient");
            System.out.println("3. Book Appointment");
            System.out.println("4. Change Appointment");
            System.out.println("5. Attend Appointment");
            System.out.println("6. Cancel Appointment");
            System.out.println("7. Print Report");
            System.out.println("8. Exit");
            System.out.print("Choose: ");

            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> addPatient();
                case 2 -> removePatient();
                case 3 -> bookAppointment();
                case 4 -> changeAppointment();
                case 5 -> attendAppointment();
                case 6 -> cancelAppointment();
                case 7 -> printReport();
                case 8 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void addPatient() {
        System.out.print("Enter ID: ");
        String id = scanner.nextLine();
        if (patients.containsKey(id)) {
            System.out.println("ID already exists.");
            return;
        }
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Address: ");
        String address = scanner.nextLine();
        System.out.print("Phone: ");
        String phone = scanner.nextLine();

        patients.put(id, new Patient(id, name, address, phone));
        System.out.println("Patient added.");
    }

    private void removePatient() {
        System.out.print("Enter ID to remove: ");
        String id = scanner.nextLine();
        if (patients.remove(id) != null) {
            System.out.println("Patient removed.");
        } else {
            System.out.println("Patient not found.");
        }
    }

    private void bookAppointment() {
        System.out.print("Enter Patient ID: ");
        String id = scanner.nextLine();
        if (!patients.containsKey(id)) {
            System.out.println("Patient not found.");
            return;
        }

        System.out.println("View by: 1. Expertise 2. Physiotherapist");
        int option = Integer.parseInt(scanner.nextLine());
        List<Treatment> available = new ArrayList<>();

        if (option == 1) {
            System.out.print("Enter Expertise: ");
            String ex = scanner.nextLine();
            for (Physiotherapist p : physios) {
                if (p.getExpertise().contains(ex)) {
                    available.addAll(p.getTreatments());
                }
            }
        } else {
            System.out.print("Enter Physio Name: ");
            String name = scanner.nextLine();
            for (Physiotherapist p : physios) {
                if (p.getName().equalsIgnoreCase(name)) {
                    available.addAll(p.getTreatments());
                }
            }
        }

        available.removeIf(t -> !t.getStatus().equals("available"));
        if (available.isEmpty()) {
            System.out.println("No appointments found.");
            return;
        }

        for (int i = 0; i < available.size(); i++) {
            System.out.println((i + 1) + ". " + available.get(i));
        }
        System.out.print("Choose slot: ");
        int idx = Integer.parseInt(scanner.nextLine()) - 1;

        Treatment selected = available.get(idx);
        if (selected.book(patients.get(id))) {
            System.out.println("Booked successfully. Booking ID: " + selected.getId());
        } else {
            System.out.println("Booking failed.");
        }
    }

    private void changeAppointment() {
        System.out.print("Enter Booking ID to change: ");
        String bookingId = scanner.nextLine();
        Treatment treatment = allBookings.get(bookingId);

        if (treatment == null || !treatment.getStatus().equals("booked")) {
            System.out.println("Booking not found or not valid.");
            return;
        }

        treatment.cancel();
        System.out.println("Previous booking cancelled. Proceed to re-book.");
        bookAppointment();
    }

    private void attendAppointment() {
        System.out.print("Enter Booking ID: ");
        String id = scanner.nextLine();
        Treatment treatment = allBookings.get(id);
        if (treatment != null && treatment.getStatus().equals("booked")) {
            treatment.attend();
            System.out.println("Marked as attended.");
        } else {
            System.out.println("Appointment not found or not booked.");
        }
    }

    private void cancelAppointment() {
        System.out.print("Enter Booking ID: ");
        String id = scanner.nextLine();
        Treatment treatment = allBookings.get(id);
        if (treatment != null && treatment.getStatus().equals("booked")) {
            treatment.cancel();
            System.out.println("Cancelled successfully.");
        } else {
            System.out.println("Booking not found.");
        }
    }

    private void printReport() {
        System.out.println("\n--- Report ---");
        physios.sort((a, b) -> Long.compare(b.countAttended(), a.countAttended()));
        for (Physiotherapist p : physios) {
            System.out.println("\nPhysio: " + p.getName());
            p.printSchedule();
        }
        System.out.println("\nSorted by number of attended appointments.");
    }

    public static void main(String[] args) {
        new BookingSystem().start();
    }
}
