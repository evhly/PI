//import org.junit.jupiter.api.Test;
//
//import java.io.File;
//import java.io.IOException;
//import java.time.DayOfWeek;
//import java.util.ArrayList;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class StudentTest {
//
//    @Test
//    public void readFileTest() {
//        File testFile = new File("test_credential_data.txt");
//        Credentials creds = Student.readFile(testFile);
//        assertEquals(creds.getFirstName(), "Billy");
//        assertEquals(creds.getLastName(), "Bob");
//        assertEquals(creds.getId(),12345);
//        assertEquals(creds.getMajor(), "B.S. in Computer Science");
//        assertEquals(creds.getPassword(), "pa55word");
//        assertEquals(creds.getEmail(), "bobbjXY@gcc.edu");
//
//    }
//
////    @Test
////    public void scheduleTest() throws IOException {
////        Student bb = new Student(Student.readFile(new File("test_credential_data.txt")));
////        Schedule newSchedule = new Schedule("testSchedule");
////        Course testCourse = new Course("TEST 101","testCourse","TEST",0,"testDescription",new ArrayList<Course>(),new Professor("Dr. Test","TEST"),"November 31",new ArrayList<DayOfWeek>(),new ArrayList<String>(),new Term("Day","Day2","Mud Season"),new ArrayList<String>());
////        newSchedule.addCourse(testCourse);
////        System.out.println(newSchedule.toSave());
////        bb.addSchedule(newSchedule);
////        bb.saveSchedules();
////    }
////}