import java.lang.reflect.Array;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Scanner;

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

    public Course() {
        code = "";
        name = "";
        department = "";
        credits = -1;
        description = "";
        prereqs = new ArrayList<>();
        professor = new Professor();
        endDate = "";
        days = new ArrayList<>();
        times = new ArrayList<>();
        term = new Term();
        room = new ArrayList<>();
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
    ) {
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


    //may be fields need to change. if so, change them here

    /**
     * yr_cde
     * trm_cde
     * crs_cde
     * crs_comp1
     * crs_comp2
     * crs_comp3
     * crs_title
     * credit_hrs
     * x_listed_parnt_crs
     * acad_credit_varies
     * acad_credit_label
     * crs_capacity	max_capacity
     * crs_enrollment
     * bldg_cde	room_cde
     * monday_cde
     * tuesday_cde
     * wednesday_cde
     * thursday_cde
     * friday_cde
     * begin_tim
     * end_tim
     * last_name
     * first_name
     * preferred_name
     * comment_txt
     *
     * @param csvLine
     */
    public Course(String csvLine) {
        Scanner s = new Scanner(csvLine);
        s.useDelimiter(",");
        String yr_cde = s.next();
        String trm_cde = s.next();
        String crs_cde = s.next();
        code = crs_cde;
        String crs_comp1 = s.next();
        department = crs_comp1;
        String crs_comp2 = s.next();
        String crs_comp3 = s.next();
        String crs_title = s.next();
        name = crs_title;
        String credit_hrs = s.next();
        if (!credit_hrs.equals("")) {
            credits = Integer.parseInt(credit_hrs); //reading str as int
        }
        String x_listed_parnt_crs = s.next();
        String acad_credit_varies = s.next();
        String acad_credit_label = s.next();
        String crs_capacity = s.next();
        String crs_enrollment = s.next();
        String bldg_cde = s.next();
        String room_cde = s.next();
        String monday_cde = s.next();
        String tuesday_cde = s.next();
        String wednesday_cde = s.next();
        String thursday_cde = s.next();
        String friday_cde = s.next();
        String begin_tim = s.next();
        String end_tim = s.next();
        String last_name = s.next();
        professor = new Professor(last_name, department);
        String first_name = s.next();
        String preferred_name = s.next();
        if (s.hasNext()) {
            String comment_txt = s.next();
        }

        s.close();
    }
}
