import java.lang.reflect.Array;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
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
//    private ArrayList<DayOfWeek> days;
//    private ArrayList<String> times;
    // term should always be a String in the format like "S24" or "F22"
    private HashMap<DayOfWeek,ArrayList<LocalTime>> meetingTimes;
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

//    public ArrayList<DayOfWeek> getDays() {
//        return days;
//    }
    public ArrayList<String> getRoom() {
        return room;
    }

//    public ArrayList<String> getTimes() {
//        return times;
//    }

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
//            ArrayList<DayOfWeek> days,
//            ArrayList<String> times,
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
//        this.days = days;
//        this.times = times;
        this.meetingTimes = meetingTimes;
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

    /**
     * Checks if Course other has a time conflict with this course.
     * @param other The course to check if this has a time conflict with
     * @return True if there is a conflict, false otherwise
     */
    public boolean hasConflict(Course other) {
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
        return false; //TODO: implement
    }

}
