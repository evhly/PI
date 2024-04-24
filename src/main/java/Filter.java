import java.time.LocalTime;
import java.util.ArrayList;

public class Filter {

    private final type filterType; // users may filter by Professor, department, or by time range
    private String department;  // the department to filter by
    private Professor professor; // the professor to filter by
    private String startTime; // the beginning of the course's meeting time
    private String endTime;   // the end of the course's meeting time
    private String startAmPm;
    private String endAmPm;

    public enum type {
        PROFESSOR,
        DEPARTMENT,
        TIMES
    }


    /**
     * Makes a DEPARTMENT filter
     * @param filterType should be DEPARTMENT
     * @param department what department to filter by
     */
    public Filter(type filterType, String department) {
        this.filterType = filterType;
        this.department = department;
    }

    /**
     * Makes a PROFESSOR filter
     * @param filterType should be PROFESSOR
     * @param professor what Professor to filter by
     */
    public Filter(type filterType, Professor professor) {
        this.filterType = filterType;
        this.professor = professor;
    }

    /**
     * Makes a TIMES filter
     * @param filterType should be TIMES
     * @param startTime the earliest time in the time range to filter by
     * @param endTime the latest time in the time range to filter by
     */
    public Filter(type filterType, String startTime, String endTime /*String startAmPm, String endAmPm*/) {
        if (startTime.charAt(1) == ':') { // if the time is not padded with a 0
            startTime = "0" + startTime;
        }
        if (endTime.charAt(1) == ':') { // if the time is not padded with a 0
            endTime = "0" + endTime;
        }
        this.filterType = filterType;
        this.startTime = startTime;
        this.endTime = endTime;
//        this.startAmPm = startAmPm;
//        this.endAmPm = endAmPm;
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
