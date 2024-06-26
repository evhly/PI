import org.json.JSONException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

public class Console {
    static Search search;
    //current schedule
    static Schedule sch;
    static CourseReader cr;
    static JsonCourseReader jcr;
    static Scanner scn;
    static Student st;
    static CourseDatabase cdb;

    /**
     * runner for console
     *
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws IOException, JSONException {
//        // read in all files in csv directory
//        CourseReader CR = new CourseReader();
//        String path = "src/main/csvs";
//        File folder = new File(path);
//        for(File fileEntry : folder.listFiles()){
//            if(!fileEntry.isDirectory()){
//                String fullPath = path + "/" + fileEntry.getName();
//                System.out.println("Attempting to read " + fullPath);
//                CR.parseCsv(fullPath);
//                System.out.println("Successfully read " + fullPath);
//            }
//        }
        //TODO: if student has schedules, set the first one to the initial sch
        sch = new Schedule("F20");
        // just read in test2.csv
        boolean useCurrentData = true;
        if(useCurrentData) {
            jcr = new JsonCourseReader();
            jcr.parseJson();
            cdb = jcr.getCourseDatabase("F24");
        }
        else {
            cr = new CourseReader();
            //   cr.parseCsv("src/main/csvs/extraCsvs/test2.csv");
            cr.parseCsv("src//main//csvs//2020-2021.csv");
            cdb = cr.getCourseDatabase("F20");
            //  System.out.println("Done");
        }

        scn = new Scanner(System.in);

        //CourseDatabase DB = new CourseDatabase("src/main/csvs/extraCsvs");

        search = new Search(cdb);

        Credentials cr = getCreds();
        st = new Student(cr,cdb);
        st.addSchedule(sch);

        homeScreen();

        // System.out.println("Enter in the file you want to read:");
//        fileName = "test2.csv";//scn.next();
//        //read from csv
//        db = loadDB(fileName);
//        System.out.println("Printing file contents ...");
//        for (int i = 0; i < db.size(); i++){
//            System.out.print(db.get(i) + "\t");
//        }

    }

    private static Credentials getCreds() {
        return new Credentials("a","b", 7,"m","22",
                "s@s.s", false);
        //TODO: make interactive
    }

    private static void homeScreen() {
        System.out.println("Welcome to console debugger :D");
        System.out.println("Chose an option: \nEnter 1 for search\nEnter 2 for view schedule" +
                "\nEnter 3 for load schedule\nEnter 4 for save schedule\nEnter 5 for new empty schedule" +
                "\nEnter 6 to recover schedule from logs\nEnter 7 to undo\nEnter 8 to redo\n" +
                "Enter 9 to export a PDF of the current schedule\nEnter 10 to quit");
        //TODO: enter loadSchedule and saveSchedule options
        //restore schedule from file option using logs
        //ask for course name
        //Log.txt added to it
        //create that file
        //send that + name to restore the schedule
        //test by going to view schedule

        int choice = getInt(10); //scn.nextInt();
        // moving to search screen
        if (choice == 1) {
            searchScreen();
        }
        // moving to view schedule screen
        else if (choice == 2) {
            scheduleScreen();
        }
        // load schedule
        else if (choice == 3) {
            ArrayList<Schedule> schedules = st.getSchedules();
            //prints out schedule names
            for (int i = 0; i < schedules.size(); i++){
                System.out.println(i + ": " + schedules.get(i).getTitle());
            }
            System.out.println("Enter id of desired schedule");
            int schToView = getInt(schedules.size()); //scn.nextInt();
            sch = schedules.get(schToView);
            System.out.println("Returning home ...");
            homeScreen();
        }
        // save schedule
        else if (choice == 4) {
            st.save();
            System.out.println("Saved!\n");
            homeScreen();
        }
        // overwriting and creating new schedule
        else if (choice == 5) {
            System.out.println("Enter schedule name:");
            String name = scn.next();
            sch = new Schedule(name, "F20");
            st.addSchedule(sch);
            System.out.println("New empty schedule created\nReturning home ... \n\n");
            homeScreen();
        } else if (choice == 6) {
            System.out.println("Enter schedule name:");
            String schName = scn.next();
            File schFile = new File(schName+"Log.txt");//accessing log file for that schedule
            sch = new Schedule(schFile,schName+"1",cdb);
            System.out.println("Schedule restored!\nReturning home ... \n\n");
            homeScreen();
        }
        //undoing last change
        else if (choice == 7) {
            boolean success = consoleUndo();
            if (success) {
                System.out.println("Undo successful\n\n");
                homeScreen();
            } else {
                System.out.println("Undo not successful\n\n");
                homeScreen();
            }
        }

        else if (choice == 8) {
            boolean success = consoleRedo();
            if (success) {
                System.out.println("Redo successful\n\n");
                homeScreen();
            } else {
                System.out.println("Redo not successful\n\n");
                homeScreen();
            }
        }

        else if (choice == 9) {
            try {
                PDF.create(sch, new Student(new Credentials("Jon", "Do",
                        1001, "Underwater Basket Weaving",
                        "be5tPa55w0rd3v3r", "e@e.com", false)), new File("./test.pdf"));
            } catch (IOException e) {
                System.err.println("Issue with PDF exporting");;
            }
        }

        //exiting program
        else if (choice == 10) {
            System.out.println("quitting ...");
            System.out.println("   3.141592\n" +
                    "  653589793\n" +
                    " 23    84\n" +
                    "6 2    64\n" +
                    "  3    38\n" +
                    "  3    27\n" +
                    "  9    50 2\n" +
                    "8 8    4197\n" +
                    " 16     93  SS\n");
            System.exit(0);
        }
        //enter bad input
        else {                  //bad input
            System.out.println("ERROR: bad input");
        }
    }

    private static void scheduleScreen() {
        ArrayList<Course> schCos = sch.getCourses();
        System.out.println("\nEnter 1 to view current schedule\nEnter 2 to remove a course.");
        int choice = getInt(2); // scn.nextInt();
        if (choice == 1) {
            System.out.println(sch);
            homeScreen();
        } else if (choice == 2) {  //remove
            System.out.println(sch);
            System.out.println("Enter id of course you would like to remove");
            int toRemove = getInt(15); //scn.nextInt(); //TODO: find the max
            sch.deleteCourse(schCos.get(toRemove));
            System.out.println("Course removed!\n" + sch + "\nReturning home ...\n\n");
            homeScreen();
        }
    }

    /**
     * screen that facilitates search
     */
    private static void searchScreen() {
        System.out.println("Welcome to search! \n1 to search (with query)" +
                "\n2 to return home\n3 to schedule");
        int choice = getInt(3); //scn.nextInt();
        //TODO(Evelyn): add error checking
        if (choice == 1) {
            searchWTerm();
        } /*else if (choice == 2) {
            searchWFilter();
        }*/ else if (choice == 2) {
            homeScreen();
        } else if (choice == 3) {
            scheduleScreen();
        }
    }

    /**
     * searches by filtering results
     */
    private static void searchWFilter() {

        //after get search results, instead of return home, etc.,
        //have an option: add a filter
        //then get text from user



//        System.out.println("Enter 1 for filtering by professor\nEnter 2 for department");
//        int choice = scn.nextInt();
//        if (choice == 1) {
//            //TODO: print results filtered by professor
//            //number for each item
//        } else if (choice == 2) {
//            //TODO: print results filtered by department
//            //number for each item
//        }
//
//        System.out.println("Enter 1 to add a result to schedule\n2 to return to search");
//        int choice2 = scn.nextInt();
//        if (choice2 == 1) {
//            addToSch();
//        } else if (choice2 == 2) {
//            searchScreen();
//        }
        System.out.println("Enter 1 to search" + "\nEnter 2 to filter\nEnter 3 to return home\nEnter 4 to schedule");
        int choice = getInt(4);  // scn.nextInt();
        if (choice == 1) {
            searchWTerm();
        } else if (choice == 2) {
            searchWFilter();
        } else if (choice == 3) {
            homeScreen();
        } else if (choice == 4) {
            scheduleScreen();
        }
    }

    /**
     * searches by sending in search string
     */
    private static void searchWTerm() {
        if (scn.hasNextLine()) {
            scn.nextLine();
        }
        System.out.println("Enter search term:");
        String query = scn.nextLine();
        //query = scn.nextLine();
        ArrayList<Course> results = search.modifyQuery(query);
        int numPrint = Math.min(6, results.size());
        for (int i = 0; i < numPrint; i++) {
            System.out.println(i + ": " +  results.get(i).consoleString());//+"tostring for course"
        }
        System.out.println("\nEnter 1 to add course to schedule\nEnter 2 to modify search" +
                "\nEnter 3 to view schedule\nEnter 4 to return home");//add filter
        int choice = getInt(4);  //scn.nextInt();
        if (choice == 1) {
            System.out.println("Which course would you like to add? (enter the index number)");
            int toAdd = getInt(numPrint); //scn.nextInt();
            if (toAdd >= 0 && toAdd < results.size()){
                sch.addCourse(results.get(toAdd)); //add course to schedule
                System.out.println("Course added! returning home ...\n\n");
                homeScreen();
            } else {
                System.out.println("Error, search again");
                searchWTerm();
                //TODO: abstract and user check
            }

        } else if (choice == 2) {
            searchScreen();
        } else if (choice == 3) {
            scheduleScreen();
        } else if (choice == 4) {
            homeScreen();
        }
    }

    /**
     * add course to schedule
     */
//    private static void addToSch() {
//        //add to sch:
//        System.out.println("Enter course index from numbered list above"); //eg 1 - 10
//        //sch.addCourse();
//        //TODO: add course to schedule
//        System.out.println("Added to schedule!\nEnter 1 for search\n2 for schedule\n3 to return home");
//        //added to sch
//
//        //enter 1 for .....
//    }
//




    public static void login(){
//        Scanner s = new Scanner(System.in);
//        System.out.println("--- Welcome to Sched-o-matic! ---\nPlease log in, " +
//                "or sign up if you don't have an account.");
//        System.out.println("Enter in name:");
//        String name = s.next();
//        System.out.println("Enter email:");
//        String email = s.next();
//        System.out.println("Enter password:");
//        String password = s.next();
//        System.out.println("Enter major:");
//        String major = s.next();
//        System.out.println("Enter student id number:");
//        int id = s.nextInt();
//
//        Credentials userCred = new Credentials(name,id,major,password,email);
//
//        //assign user info
//        Student currUser = new Student(userCred);

    }

    /**
     *
     * @param max valid input value
     * @return user input int
     */
    public static int getInt(int max){
        int input = -1;
        boolean valid = false;
        while (!valid) {
            if (!scn.hasNextInt()){
                scn.next();//discard bad input
                System.out.println("Please enter an integer.\nTry again");
                //input = scn.nextInt();
                continue; //skip to next loop
            }
            input = scn.nextInt();

            if (input < 0 || input > max){
                System.out.println("Must be between 0 and " + max + ".\nTry again");
            } else {
                valid = true;
            }
        }
        return input;
    }

    public static boolean consoleUndo() {
        return sch.undo();
    }

    public static boolean consoleRedo() {
        return sch.redo();
    }

}
