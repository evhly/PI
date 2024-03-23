import java.io.File;
import java.io.FileNotFoundException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;

public class CourseReader {


    Map<String, CourseDatabase> courseDatabaseMap;

    public CourseReader(){
        courseDatabaseMap = new HashMap<>();
    }
    public void parseCsv(String filename) throws FileNotFoundException {
        ArrayList<Course> data = new ArrayList<>();
        File f = new File(filename);
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
    }

    public boolean sameCourse(Course c, String section) {
        // TODO
        return false;
    }

    public void appendToCourse(){
       // TODO
    }

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
//        String bldg_cde	= s.next();
//        String room_cde	= s.next();
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

        // adjust and format values from csv line
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

        HashMap<DayOfWeek, ArrayList<LocalTime>> meetings = new HashMap<>();
        for (String day : days) {
            ArrayList<LocalTime> beginAndEnd = new ArrayList<>();
            DateTimeFormatter ampmFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
            beginAndEnd.add(LocalTime.parse(begin_tim, ampmFormatter)); // reads the String as a time (HH:MM:SS AM/PM)
            beginAndEnd.add(LocalTime.parse(end_tim, ampmFormatter));
            if (day != null) {
                switch (day) {
                    case "M" -> meetings.put(DayOfWeek.MONDAY, beginAndEnd);
                    case "T" -> meetings.put(DayOfWeek.TUESDAY, beginAndEnd);
                    case "W" -> meetings.put(DayOfWeek.WEDNESDAY, beginAndEnd);
                    case "R" -> meetings.put(DayOfWeek.THURSDAY, beginAndEnd);
                    case "F" -> meetings.put(DayOfWeek.FRIDAY, beginAndEnd);
                    default -> meetings.put(DayOfWeek.SATURDAY, beginAndEnd); // more of a placeholder than anything
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

}
