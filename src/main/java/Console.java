import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

public class Console {
    static Search search;
    static Schedule sch;
    static Student s;
    static ArrayList<Course> db;


    /**
     * runner for console
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
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
        Set<String> terms = CR.getTerms();
        CourseDatabase F22 = CR.getCourseDatabase("F19");
        System.out.println("done!");
    }
}
