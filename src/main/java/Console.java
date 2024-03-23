import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Console {
    static Search search;
    static Schedule sch;
    static Student s;
    static ArrayList<Course> db;
    static Scanner scn;

    //excel
    //ctrl+ h
    //10 = fall class
    //30 = spring

    /**
     * runner for console
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {

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

//        // just read in test2.csv
//        CourseReader CR = new CourseReader();
//        CR.parseCsv("src/main/csvs/extraCsvs/test2.csv");
//        System.out.println("Done");

        scn = new Scanner(System.in);

        CourseDatabase DB = new CourseDatabase("src/main/csvs/extraCsvs");
        search = new Search(DB);
        //String fName;
        boolean exit = false;
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
        System.out.println("Chose an option: \nEnter 1 for search\nEnter 2 for schedule");
        //TODO: enter loadSchedule and saveSchedule options
        int choice = scn.nextInt();
        //TODO(Evelyn): add error checking
        if (choice == 1){
            searchScreen();
        }
        else if (choice == 2){
            scheduleScreen();
        } else {
            System.out.println("ERROR: bad input");
        }
    }

    private static void scheduleScreen() {
        //TODO: show schedule and add schedule options here
        System.out.println("\nEnter name for desired schedule:");
        String schNm = scn.next();
        Schedule sched = new Schedule(schNm);
        System.out.println("--- " + schNm + " Schedule ---");
        System.out.println("Current schedule:");



        System.out.println("What do you want to do now?\nEnter 1 to return home\nEnter 2 for search");
        int choice = scn.nextInt();
        //TODO(Evelyn): add error checking
        if (choice == 1){
            homeScreen();
        }
        else if (choice == 2){
            searchScreen();
        }
    }

    private static void searchScreen() {
        //TODO: add search methods and search UI

        System.out.println("What do you want to do now?\nEnter 1 to return home\nEnter 2 for schedule");
        int choice = scn.nextInt();
        //TODO(Evelyn): add error checking
        if (choice == 1){
            homeScreen();
        }
        else if (choice == 2){
            scheduleScreen();
        }
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
