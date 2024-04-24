import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Course {

    private String code; // 4-letter course code, 3-digit number, and section letter: e.g. COMP 350 B
    private String name; // long-form name of course: e.g. Software Engineering
    private String department; // 4-letter code: e.g. COMP
    private int credits; // how many credit hours the course has: e.g. 4 for Calculus I
    private String description; // A brief description of the course
    private ArrayList<Course> prereqs; // Prerequisite courses
    private Professor professor; // The primary instructor for that course
    private String endDate; // When the course officially stops meeting (can be different from actual last class meeting)
    // meetingTimes maps days of the week (e.g. Monday, Tuesday) to pairs of times (a starting time and an ending time)
    private HashMap<DayOfWeek,ArrayList<LocalTime>> meetingTimes;
    private String term; // always a String in the format like "S24" or "F22"
    private ArrayList<String> room; // Where the course meets
    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public int getCredits() {
        return credits;
    }

    public String getDepartment() {
        return department;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Course> getPrereqs() {
        return prereqs;
    }

    public ArrayList<String> getRoom() {
        return room;
    }

    public HashMap<DayOfWeek, ArrayList<LocalTime>> getMeetingTimes() {
        return meetingTimes;
    }

    public Professor getProfessor() {
        return professor;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getTerm() {
        return term;
    }

    public Course(
            String code,
            String name,
            String department,
            int credits,
            String description,
            ArrayList<Course> prereqs,
            Professor professor,
            String endDate,
            HashMap<DayOfWeek,ArrayList<LocalTime>> meetingTimes,
            String term,
            ArrayList<String> room
    ){
        this.code = code;
        this.name = name;
        this.department = department;
        this.credits = credits;
        this.description = description;
        this.prereqs = prereqs;
        this.professor = professor;
        this.endDate = endDate;
        this.meetingTimes = meetingTimes;
        this.term = term;
        this.room = room;
    }

    public Course() {
    }

    /**
     * Primarily used for testing (makes dummy courses with just meetingTimes)
     * @param meetingDays days of the week that the course meets on
     * @param startTime   The start time of those meetings
     * @param endTime     The end time of those meetings
     */
    public Course(ArrayList<DayOfWeek> meetingDays, String startTime, String endTime) {
        HashMap<DayOfWeek, ArrayList<LocalTime>> m = new HashMap<>();
        for (DayOfWeek day : meetingDays) {
            ArrayList<LocalTime> times = new ArrayList<>();
            DateTimeFormatter ampmFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
            if (startTime.charAt(1) == ':') { // if the time is not padded with a 0
                startTime = "0" + startTime;
            }
            if (endTime.charAt(1) == ':') { // if the time is not padded with a 0
                endTime = "0" + endTime;
            }
            times.add(LocalTime.parse(startTime, ampmFormatter));
            times.add(LocalTime.parse(endTime, ampmFormatter));
            m.put(day,times);
        }
        this.meetingTimes = m;
    }

    /**
     * Primarily used for testing (makes dummy courses with just meetingTimes)
     * @param code         Identifier of the course
     * @param meetingTimes Which days and times the course meets
     */
    public Course(String code, HashMap<DayOfWeek,ArrayList<LocalTime>> meetingTimes) {
        this.code = code;
        this.meetingTimes = meetingTimes;
    }

    /**
     * Another toString()
     * @return coures code and class name
     */
    public String consoleString() {
        return code + " " + name;
    }

    /**
     * Checks if Course other has a time conflict with this course.
     * @param other The course to check if this has a time conflict with
     * @return True if there is a conflict, false otherwise
     */
    public boolean hasConflict(Course other) {
        if (this.meetingTimes == null || other.meetingTimes == null) {
            return false;
        }
        HashMap<DayOfWeek, ArrayList<LocalTime>> otherTimes = other.getMeetingTimes();
        for (DayOfWeek day : DayOfWeek.values()) { // for each day in the week
            if (otherTimes.containsKey(day) && this.meetingTimes.containsKey(day)) { // if both classes meet on that day
                if (timeRangesOverlap(this.meetingTimes.get(day).get(0),
                        this.meetingTimes.get(day).get(1), otherTimes.get(day).get(0),
                        otherTimes.get(day).get(1))) { // if the ranges of the meetingTimes overlap
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean timeRangesOverlap(LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2) {
        // Cases:
        // if start1 == start2
        if (start1.equals(start2)) {
            return true;
        }
        // if end1 == end2
        if (end1.equals(end2)) {
            return true;
        }
        // if start1 is after start2 but before end2
        if (start1.isAfter(start2) && start1.isBefore(end2)) {
            return true;
        }
        // if start2 is after start1 but before end1
        if (start2.isAfter(start1) && start2.isBefore(end1)) {
            return true;
        }
        // if end1 is after start2 but before end2
        if (end1.isAfter(start2) && end1.isBefore(end2)) {
            return true;
        }
        // if end2 is after start1 but before end1
        if (end2.isAfter(start1) && end2.isBefore(end1)) {
            return true;
        }
        // if start1 is after start2 and end1 is before end2
        if (start1.isAfter(start2) && end1.isBefore(end2)) {
            return true;
        }
        // if start2 is after start1 and end2 is before end1
        if (start2.isAfter(start1) && end2.isBefore(end1)) {
            return true;
        }
        return false;
    }

    /**
     * Creates a string representation of a Course in this format: CODE; NAME; DayOfWeek hh:mm:ss a-hh:mm:ss a, DayOfWeek2 hh:...
     * @return A string representation of a Course with meeting time data
     */
    @Override
    public String toString() {
        String str = code + "; " + name + "; ";
        boolean times = false;
        for (Map.Entry<DayOfWeek, ArrayList<LocalTime>> meeting : meetingTimes.entrySet()) {
            str += meeting.getKey().toString() + " " + meeting.getValue().get(0) + "-" + meeting.getValue().get(1) + ", ";
            times = true;
        }
        if (times) {
            str = str.substring(0, str.length() - 2);
        }
        return str;
    }
}
