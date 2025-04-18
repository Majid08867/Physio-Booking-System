import java.util.ArrayList;
import java.util.List;

public class Physiotherapist {
    private String id;
    private String name;
    private String address;
    private String phone;
    private List<String> expertise;
    private List<Treatment> treatments;

    public Physiotherapist(String id, String name, String address, String phone, List<String> expertise) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.expertise = expertise;
        this.treatments = new ArrayList<>();
    }

    public String getName() { return name; }
    public List<String> getExpertise() { return expertise; }
    public List<Treatment> getTreatments() { return treatments; }

    public void addTreatment(Treatment t) {
        treatments.add(t);
    }

    public void printSchedule() {
        System.out.println("\nSchedule for " + name);
        for (Treatment t : treatments) {
            System.out.println(t);
        }
    }

    public long countAttended() {
        return treatments.stream().filter(t -> t.getStatus().equals("attended")).count();
    }
}