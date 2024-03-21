import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CourseReader {


    Map<Term, CourseDatabase> courseDatabaseMap;

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
        // TODO: add other parameters
        // check if they're the same course
        return false;
    }

    public void appendToCourse(){
       // TODO: append things to create the full course
    }

    public Course parseCourseInfo(String csvLine){
        // parse the line to get parameters for Course
        Scanner s = new Scanner(csvLine);
        s.useDelimiter(",");
        String yr_cde = s.next();
        String trm_cde = s.next();
        if(Objects.equals(trm_cde, "10")){
            trm_cde = "Fall";
        } else {
            trm_cde = "Spring";
        }
        trm_cde += yr_cde;
        String crs_cde = s.next();
        String crs_comp1 = s.next();
        String crs_comp2 = s.next();
        String crs_comp3 = s.next();
        String crs_title = s.next();
        String credit_hrs = s.next();
        int credits = -1; // TODO: what value should credits be if none?
        if (!credit_hrs.equals("")) {
             credits = Integer.parseInt(credit_hrs); //reading str as int
        }
        String x_listed_parnt_crs = s.next();
        String acad_credit_varies = s.next();
        String acad_credit_label = s.next();
        String crs_capacity	= s.next();
        String max_capacity	= s.next();
        String crs_enrollment = s.next();
        String bldg_cde	= s.next();
        String room_cde	= s.next();
        ArrayList<String> rooms = new ArrayList<>();
        rooms.add(bldg_cde + " " + room_cde);

        String[] days = new String[5];
        for(int i = 0; i < 5; i++){
            days[i] = s.next();
        }
        String begin_tim = s.next();
        String end_tim = s.next();
        ArrayList<Meeting> meetings = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            if(!Objects.equals(days[i], "")){
                meetings.add(new Meeting(begin_tim, end_tim, WeekDay.valueOf(String.valueOf(days[i]))));
            }
        }
        String last_name = s.next();
        String first_name = s.next();
        String preferred_name = s.next();
        if(s.hasNext()) {
            String comment_txt = s.next();
        }

        s.close();

        // create a new Course from these parameters
        Course c = new Course(crs_cde,
                crs_title,
                crs_comp1,
                credits,
                null,
                null,
                new Professor(first_name, last_name),
                null,
                meetings,
                new Term(null, null, trm_cde),
                rooms);
        return c;
    }

    public CourseDatabase getCourseDatabase(Term term){
        return courseDatabaseMap.get(term);
    }

    public Set<Term> getTerms(){
        return courseDatabaseMap.keySet();
    }

}
