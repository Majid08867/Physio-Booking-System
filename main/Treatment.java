public class Treatment {
    private String id;
    private String name;
    private String dateTime;
    private String status;
    private Patient patient;

    public Treatment(String id, String name, String dateTime) {
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
        this.status = "available";
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDateTime() { return dateTime; }
    public String getStatus() { return status; }
    public Patient getPatient() { return patient; }

    public boolean book(Patient patient) {
        if (status.equals("available")) {
            this.patient = patient;
            this.status = "booked";
            return true;
        }
        return false;
    }

    public void cancel() {
        this.status = "cancelled";
    }

    public void attend() {
        this.status = "attended";
    }

    @Override
    public String toString() {
        return "Treatment ID: " + id + ", Name: " + name + ", Time: " + dateTime +
                ", Status: " + status + (patient != null ? ", Patient: " + patient.getName() : "");
    }
}