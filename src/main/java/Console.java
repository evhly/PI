import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Console {
    static Search search;
    static Schedule sch;
    static Student s;
    static CourseReader cr;
    static Scanner scn;

    //excel
    //ctrl+ h
    //10 = fall class
    //30 = spring

    /**
     * runner for console
     *
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
//
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

        sch = new Schedule();
        s = new Student(null);
        // just read in test2.csv
        cr = new CourseReader();
     //   cr.parseCsv("src/main/csvs/extraCsvs/test2.csv");
        cr.parseCsv("C://Users//HUTCHINSEJ19//IdeaProjects//PIb//src//main//csvs//2020-2021.csv");
        System.out.println("Done");

        scn = new Scanner(System.in);

        //CourseDatabase DB = new CourseDatabase("src/main/csvs/extraCsvs");
        search = new Search(cr.getCourseDatabase("F20"));
        //String fName;
        //boolean exit = false;
        //String fileName;
        //String userInput;

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

    private static void homeScreen() {
        System.out.println("Welcome to console debugger :D");
        System.out.println("\nChose an option: \nEnter 1 for search\nEnter 2 for schedule" +
                "\nEnter -1 to quit");
        //TODO: enter loadSchedule and saveSchedule options

        int choice = scn.nextInt();
        //TODO(Evelyn): add error checking
        if (choice == 1) {         //moving to search screen
            searchScreen();
        } else if (choice == 2) {  //moving to schedule screen
            scheduleScreen();
        } else if (choice == -1) { //exiting program
            System.out.println("quitting ...");
        } else {                  //bad input
            System.out.println("ERROR: bad input");
        }
    }

    private static void scheduleScreen() {
        //TODO: show schedule and add schedule options here
        System.out.println("\nEnter name for desired schedule:");
        String schNm = scn.next();
        Schedule sched = new Schedule(schNm);
        System.out.println("--- " + schNm + " Schedule ---");


        System.out.println("\nWhat do you want to do now?\n" +
                "1 to return home\n2 for search");
        int choice = scn.nextInt();
        //TODO(Evelyn): add error checking
        if (choice == 1) {
            homeScreen();
        } else if (choice == 2) {
            searchScreen();
        }
    }

    /**
     * screen that facilitates search
     */
    private static void searchScreen() {
        System.out.println("Welcome to search! \n1 to search" +
                "\n2 to filter\n3 to return home\n4 to schedule");
        int choice = scn.nextInt();
        //TODO(Evelyn): add error checking
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
     * searches by filtering results
     */
    private static void searchWFilter() {
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
        System.out.println("Enter 1 to search" +
                "\n2 to filter\n3 to return home\n4 to schedule");
        int choice = scn.nextInt();
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
        System.out.println("Enter search term:");
        String query = scn.nextLine();
        query = scn.nextLine();
        ArrayList<Course> results = search.modifyQuery(query);
        int numPrint = Math.min(6, results.size());
        for (int i = 0; i < numPrint; i++) {
            System.out.println(i + ": " +  results.get(i).consoleString());//+"tostring for course"
        }
    }

    /**
     * add course to schedule
     */
    private static void addToSch() {
        //add to sch:
        System.out.println("Enter course index from numbered list above"); //eg 1 - 10
        //sch.addCourse();
        //TODO: add course to schedule
        System.out.println("Added to schedule!\nEnter 1 for search\n2 for schedule\n3 to return home");
        //added to sch

        //enter 1 for .....
    }





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
}
