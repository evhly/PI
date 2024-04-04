import java.time.LocalTime;
import java.util.ArrayList;

public class Filter {

    private final type filterType;
    private String department;
    private Professor professor;
    private String startTime;
    private String endTime;

    private boolean enabled;

    public enum type {
        PROFESSOR,
        DEPARTMENT,
        TIMES
    }

    public Filter(type filterType, String department, boolean enabled) {
        this.filterType = filterType;
        this.department = department;
        this.enabled = enabled;
    }

    public Filter(type filterType, Professor professor, boolean enabled) {
        this.filterType = filterType;
        this.professor = professor;
        this.enabled = enabled;
    }

    public Filter(type filterType, String startTime, String endTime, boolean enabled) {
        if (startTime.charAt(1) == ':') { // if the time is not padded with a 0
            startTime = "0" + startTime;
        }
        if (endTime.charAt(1) == ':') { // if the time is not padded with a 0
            endTime = "0" + endTime;
        }
        this.filterType = filterType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.enabled = enabled;
    }

    public type getType() {
        return filterType;
    }

    public Professor getProfessor() {
        return professor;
    }

    public String getDepartment() {
        return department;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public ArrayList<String> getTimes() {
        ArrayList<String> times = new ArrayList<>();
        times.add(startTime);
        times.add(endTime);
        return times;
    }

}
