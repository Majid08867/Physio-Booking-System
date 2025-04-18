import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BookingSystemTest {

    @Test
    public void testBookTreatment() {
        Patient patient = new Patient("P100", "Alice", "1 Lane", "555-0001");
        Treatment treatment = new Treatment("T100", "Acupuncture", "Monday 8AM");

        assertTrue(treatment.book(patient));
        assertEquals("booked", treatment.getStatus());
        assertEquals("Alice", treatment.getPatient().getName());
    }

    @Test
    public void testCancelTreatment() {
        Patient patient = new Patient("P101", "Bob", "2 Street", "555-0002");
        Treatment treatment = new Treatment("T101", "Massage", "Tuesday 10AM");
        treatment.book(patient);
        treatment.cancel();

        assertEquals("cancelled", treatment.getStatus());
    }

    @Test
    public void testAttendTreatment() {
        Patient patient = new Patient("P102", "Charlie", "3 Avenue", "555-0003");
        Treatment treatment = new Treatment("T102", "Physiotherapy", "Wednesday 9AM");
        treatment.book(patient);
        treatment.attend();

        assertEquals("attended", treatment.getStatus());
    }

    @Test
    public void testDoubleBookingFails() {
        Patient p1 = new Patient("P103", "Dana", "4 Blvd", "555-0004");
        Patient p2 = new Patient("P104", "Evan", "5 Rd", "555-0005");
        Treatment treatment = new Treatment("T103", "Rehab", "Thursday 11AM");

        assertTrue(treatment.book(p1));
        assertFalse(treatment.book(p2)); // Should fail as already booked
    }

    @Test
    public void testPhysiotherapistAddTreatment() {
        Physiotherapist physio = new Physiotherapist("PH1", "Dr. Test", "101 Way", "555-9999", 
                                                    java.util.List.of("Massage"));
        Treatment t = new Treatment("T104", "Massage", "Friday 1PM");
        physio.addTreatment(t);

        assertEquals(1, physio.getTreatments().size());
        assertEquals("Massage", physio.getTreatments().get(0).getName());
    }
}