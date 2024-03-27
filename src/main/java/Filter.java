import java.time.LocalTime;
import java.util.ArrayList;

public class Filter {

    private final type filterType;
    private String department;
    private Professor professor;
    private String startTime;
    private String endTime;

    public enum type {
        PROFESSOR,
        DEPARTMENT,
        TIMES
    }

    public Filter(type filterType, String department) {
        this.filterType = filterType;
        this.department = department;
    }

    public Filter(type filterType, Professor professor) {
        this.filterType = filterType;
        this.professor = professor;
    }

    public Filter(type filterType, String startTime, String endTime) {
        this.filterType = filterType;
        this.startTime = startTime;
        this.endTime = endTime;
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

    public ArrayList<String> getTimes() {
        ArrayList<String> times = new ArrayList<>();
        times.add(startTime);
        times.add(endTime);
        return times;
    }

}
