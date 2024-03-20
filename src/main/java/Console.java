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


    //read from csv method -- loadDB(fileName) ->return arraylist

    /**
     * loads data from a csv file into ArrayList
     * @param fileName for csv
     * @return ArrayList of data
     * @throws FileNotFoundException
     */
    public static ArrayList<Course> loadDB(String fileName) throws FileNotFoundException {
        ArrayList<Course> data = new ArrayList<>();
        File f = new File(fileName);
        Scanner fs = new Scanner(f);
        //get rid of header
        fs.nextLine();

        while (fs.hasNext()){
           // Scanner ls = new Scanner(fs.nextLine());
            Course c = new Course(fs.nextLine());
            data.add(c);
        }
        fs.close();
        return data;
    }

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
        scn = new Scanner(System.in);

        CourseDatabase DB = new CourseDatabase(null);
        search = new Search(DB);
        String fName;
        boolean exit = false;
        String fileName;
        String userInput;

        homeScreen();

       // System.out.println("Enter in the file you want to read:");
//        fileName = "test2.csv";//scn.next();
//        //read from csv
//        db = loadDB(fileName);
//        System.out.println("Printing file contents ...");
//        for (int i = 0; i < db.size(); i++){
//            System.out.print(db.get(i) + "\t");
//        }
//        while(!exit){
//            userInput = scn.next();
//
//            //to finish using console, enter "exit".
//            if(userInput.contains("exit")){
//                exit = true;
//            }
//        }


        //next check for creating a student
        //maybe login?
        //then search


        //scn.close();
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
        }
    }

    private static void scheduleScreen() {
        //TODO: show schedule and add schedule options here

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
