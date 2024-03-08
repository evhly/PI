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
    ){}

}
