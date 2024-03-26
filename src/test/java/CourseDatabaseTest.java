import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class CourseDatabaseTest {

    @Test
    void addCourse() {
        DateTimeFormatter ampmFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");

        HashMap<DayOfWeek, ArrayList<LocalTime>> meetingTimes = new HashMap<>();
        ArrayList<LocalTime> t1 = new ArrayList<>();
        t1.add(LocalTime.parse("12:00:00 PM", ampmFormatter));
        t1.add(LocalTime.parse("12:50:00 PM", ampmFormatter));
        meetingTimes.put(DayOfWeek.MONDAY, t1);
        Course c1 = new Course("TEST 101", meetingTimes);

        HashMap<DayOfWeek, ArrayList<LocalTime>> meetingTimes2 = new HashMap<>();
        ArrayList<LocalTime> t2 = new ArrayList<>();
        t2.add(LocalTime.parse("01:00:00 PM", ampmFormatter)); // this would not be padded in csv but CourseReader would pad it for you
        t2.add(LocalTime.parse("01:50:00 PM", ampmFormatter));
        meetingTimes2.put(DayOfWeek.TUESDAY, t2);
        Course c2 = new Course("TEST 101", meetingTimes2);

        CourseDatabase db = new CourseDatabase();
        db.addCourse(c1);
        db.addCourse(c2);
        System.out.println(db.toString());
    }
}