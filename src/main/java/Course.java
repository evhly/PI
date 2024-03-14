import java.lang.reflect.Array;
import java.time.DayOfWeek;
import java.util.ArrayList;

public class Course {
    private String code;
    private String name;
    private String department;
    private int credits;
    private String description;
    private ArrayList<Course> prereqs;
    private Professor professor;
    private String endDate;
    private ArrayList<DayOfWeek> days;
    private ArrayList<String> times;
    private Term term;
    private ArrayList<String> room;

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

    public ArrayList<DayOfWeek> getDays() {
        return days;
    }

    public ArrayList<String> getRoom() {
        return room;
    }

    public ArrayList<String> getTimes() {
        return times;
    }

    public Professor getProfessor() {
        return professor;
    }

    public String getEndDate() {
        return endDate;
    }

    public Term getTerm() {
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
            ArrayList<DayOfWeek> days,
            ArrayList<String> times,
            Term term,
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
        this.days = days;
        this.times = times;
        this.term = term;
        this.room = room;
    }

}
