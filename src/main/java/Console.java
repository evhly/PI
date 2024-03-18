import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Console {
    static Search search;
    static Schedule sch;
    static Student s;
    static ArrayList<Course> db;

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
        boolean exit = false;
        Scanner scn = new Scanner(System.in);
        //search = new Search();
        String fileName;
        String userInput;

        //set up console loop

        System.out.println("Welcome to console debugger :D");
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
        runner();

        //next check for creating a student
        //maybe login?
        //then search

        System.out.println("end");

        //scn.close();
    }

    public static void runner(){
        Scanner s = new Scanner(System.in);
        System.out.println("--- Welcome to Sched-o-matic!\nPlease log in, " +
                "or sign up if you don't have an account.");
        System.out.println("Enter in name:");
        String name = s.next();
        System.out.println("Enter student id:");
        int id = s.nextInt();
        System.out.println("Enter major:");
        String major = s.next();


        //Credentials userCred = new Credentials(name,id,major);
//
//        userCred.setName(s.next());
//
//        //assign user info
//        Student currUser = new Student(userCred);
//        currUser.changeProfile();


    }
}
