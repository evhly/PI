import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Console {
    static Search search;
    static Schedule sch;
    static Student s;
    static ArrayList<Course> db;

    //read from csv methed -- loadDB(fileName) ->return arraylist

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
       // Scanner scn = new Scanner(System.in);
        search = new Search();
        String fName;

        System.out.println("Welcome to console debugger :D");
       // System.out.println("Enter in the file you want to read:");
        fName = "test2.csv";//scn.next();
        //read from csv
        db = loadDB(fName);
        System.out.println("Printing file contents ...");
        for (int i = 0; i < db.size(); i++){
            System.out.print(db.get(i) + "\t");
        }

        //next check for creating a student
        //maybe login?
        //then search

        System.out.println("end");

        //scn.close();
    }
}