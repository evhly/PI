import org.json.JSONException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;

public class CourseReader {

    /**
     * Key: term, e.g., "F20"
     * Value: CourseDatabase containing all the courses offered during that term
     */
    Map<String, CourseDatabase> courseDatabaseMap;

    public CourseReader(){
        courseDatabaseMap = new HashMap<>();
    }

    /**
     * Parses a csv file and adds all courses in the file to courseDatabaseMap
     * @param filename path to a csv file to parse for courses
     */
    public void parseCsv(String filename)  {
        ArrayList<Course> data = new ArrayList<>();
        File f = new File(filename);
        try {
            Scanner fs = new Scanner(f);

        //get rid of header
        fs.nextLine();

        while (fs.hasNext()){
            // Scanner ls = new Scanner(fs.nextLine());
            Course c = parseCourseInfo(fs.nextLine());

            // add course to the map
            if(courseDatabaseMap.keySet().contains(c.getTerm())){
                courseDatabaseMap.get(c.getTerm()).addCourse(c);
            } else {
                CourseDatabase CD = new CourseDatabase();
                CD.addCourse(c);
                courseDatabaseMap.put(c.getTerm(), CD);
            }
        }
        fs.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }

    /**
     * Parses a line from the CSV file (the file that holds all courses offered in a year)
     * @param csvLine A line from the CSV file
     * @return A newly minted Course
     */
    public Course parseCourseInfo(String csvLine){

        // parse the line to get parameters for Course
        Scanner s = new Scanner(csvLine);
        s.useDelimiter(",");
        String yr_cde_str = s.next();
        String trm_cde = s.next();
        String crs_comp1 = s.next();
        String crs_comp2 = s.next();
        String crs_comp3 = s.next();
        String crs_title = s.next();
        String credit_hrs = s.next();
        String crs_capacity	= s.next();
        String crs_enrollment = s.next();
        // TODO: add room and bldg code columns
        String[] days = new String[5];
        for(int i = 0; i < 5; i++){
            days[i] = s.next();
        }
        String begin_tim = s.next();
        String end_tim = s.next();
        String last_name = s.next();
        String first_name = s.next();
        String preferred_name = s.next();
        if(s.hasNext()) {
            String comment_txt = s.next();
        }
        s.close();

        // adjust and format certain values from csv line
        int yr_cde = Integer.parseInt(yr_cde_str);
        if(Objects.equals(trm_cde, "10")){
            trm_cde = "F";
            trm_cde += String.valueOf(yr_cde - 2000);
        } else {
            trm_cde = "S";
            trm_cde += String.valueOf(yr_cde - 2000 + 1);
        }
        int credits = 0;
        if (!credit_hrs.equals("")) {
            credits = Integer.parseInt(credit_hrs); //reading str as int
        }

        // parse meeting times and create a map of days of the week to meeting times
        HashMap<DayOfWeek, ArrayList<LocalTime>> meetings = new HashMap<>();
        for (String day : days) {
            if(!day.isEmpty()) {
                ArrayList<LocalTime> beginAndEnd = new ArrayList<>();
                DateTimeFormatter ampmFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
                if (begin_tim.charAt(1) == ':') { // if the time is not padded with a 0
                    begin_tim = "0" + begin_tim;
                }
                if (end_tim.charAt(1) == ':') { // if the time is not padded with a 0
                    end_tim = "0" + end_tim;
                }
                beginAndEnd.add(LocalTime.parse(begin_tim, ampmFormatter)); // reads the String as a time (HH:MM:SS AM/PM)
                beginAndEnd.add(LocalTime.parse(end_tim, ampmFormatter));
                    switch (day) {
                        case "M" -> meetings.put(DayOfWeek.MONDAY, beginAndEnd);
                        case "T" -> meetings.put(DayOfWeek.TUESDAY, beginAndEnd);
                        case "W" -> meetings.put(DayOfWeek.WEDNESDAY, beginAndEnd);
                        case "R" -> meetings.put(DayOfWeek.THURSDAY, beginAndEnd);
                        case "F" -> meetings.put(DayOfWeek.FRIDAY, beginAndEnd);
                        default -> meetings.put(DayOfWeek.SATURDAY, beginAndEnd); //TODO: change to DayOfWeek.SUNDAY;
                                                                                  // add Saturday option
                    }
            }
        }

        // create a new Course from these parameters
        Course c = new Course(crs_comp1 + " " + crs_comp2 + " " + crs_comp3,
                crs_title,
                crs_comp1,
                credits,
                null,
                null,
                new Professor(first_name, last_name),
                null,
                meetings,
                trm_cde,
                null
            );
        return c;
    }

    public CourseDatabase getCourseDatabase(String term){
        return courseDatabaseMap.get(term);
    }

    public Set<String> getTerms(){
        return courseDatabaseMap.keySet();
    }

    /**
     *
     * @return CourseReader object, where the courseDatabaseMap member variable is initialized to contain all courses
     * found in the csvs located in the csvs directory
     */
    public static CourseReader getAllCourseDatabases() {
        CourseReader CR = new CourseReader();
        String path = "src/main/csvs";
        File folder = new File(path);
        for(File fileEntry : folder.listFiles()){
            if(!fileEntry.isDirectory()){
                String fullPath = path + "/" + fileEntry.getName();
                System.out.println("Attempting to read " + fullPath);
                CR.parseCsv(fullPath);
                System.out.println("Successfully read " + fullPath);
            }
        }
        return CR;
    }

    public static CourseReader getJSONCourseDatabases()  {
        JsonCourseReader CR = new JsonCourseReader();
        try {
            CR.parseJson();
        } catch (Exception e) {
            System.out.println("Error reading JSON");
        }
        return CR;
    }
}
