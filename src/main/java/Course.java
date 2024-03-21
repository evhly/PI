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
    private ArrayList<Meeting> meetings;

    // term should always be a String in the format like "S24" or "F22"
    private String term;
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

    public ArrayList<Meeting> getMeetings() {
        return meetings;
    }

    public ArrayList<String> getRoom() {
        return room;
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
            ArrayList<Meeting> meetings,
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
        this.meetings = meetings;
        this.term = term;
        this.room = room;
    }


    //may be fields need to change. if so, change them here
    /**
     *
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
     */

    public String toString() {
        return code;
    }
//    private String description;
//    private ArrayList<Course> prereqs;
//    private String endDate;
//    private ArrayList<DayOfWeek> days;
//    private ArrayList<String> times;
//    private Term term;
//    private ArrayList<String> room;
}
