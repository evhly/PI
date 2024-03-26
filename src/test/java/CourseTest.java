import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CourseTest {

    @Test
    void localTimeTest() {
        DateTimeFormatter ampmFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        LocalTime s = LocalTime.parse("12:00:00 PM", ampmFormatter);
    }

    @Test
    void timeRangesOverlap() {
        DateTimeFormatter ampmFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        LocalTime start1 = LocalTime.parse("12:00:00 PM", ampmFormatter);
        LocalTime end1 = LocalTime.parse("12:50:00 PM", ampmFormatter);
        LocalTime start2 = LocalTime.parse("01:00:00 PM", ampmFormatter);
        LocalTime end2 = LocalTime.parse("01:50:00 PM", ampmFormatter);

        boolean overlap1 = Course.timeRangesOverlap(start1, end1, start2, end2);
        System.out.println(overlap1);

        start2 = LocalTime.parse("12:30:00 PM", ampmFormatter);
        end2 = LocalTime.parse("01:45:00 PM", ampmFormatter);

        boolean overlap2 = Course.timeRangesOverlap(start1,end1,start2,end2);
        System.out.println(overlap2);

        start2 = LocalTime.parse("11:00:00 AM", ampmFormatter);
        end2 = LocalTime.parse("12:15:00 PM", ampmFormatter);

        boolean overlap3 = Course.timeRangesOverlap(start1,end1,start2,end2);
        System.out.println(overlap3);

        boolean overlap4 = Course.timeRangesOverlap(start1,end1,start1,end1);
        System.out.println(overlap4);

        start2 = LocalTime.parse("12:15:00 PM", ampmFormatter);
        end2 = LocalTime.parse("12:45:00 PM", ampmFormatter);

        boolean overlap5 = Course.timeRangesOverlap(start1,end1,start2,end2);
        System.out.println(overlap5);
    }

    @Test
    void hasConflictTest() {
        ArrayList<DayOfWeek> days = new ArrayList<>();
        days.add(DayOfWeek.MONDAY);
        Course c1 = new Course(days, "12:00:00 PM", "12:50:00 PM");
        Course c2 = new Course(days, "01:00:00 PM", "01:50:00 PM");
        assertFalse(c1.hasConflict(c2));
        assertFalse(c2.hasConflict(c1));
        Course c3 = new Course(days, "12:30:00 PM","01:45:00 PM");
        assertTrue(c1.hasConflict(c3));
        assertTrue(c3.hasConflict(c1));
        ArrayList<DayOfWeek> days2 = new ArrayList<>();
        days2.add(DayOfWeek.TUESDAY);
        Course c4 = new Course(days2, "12:30:00 PM","01:45:00 PM");
        assertFalse(c1.hasConflict(c4));
        assertFalse(c4.hasConflict(c1));
    }

    @Test
    void paddingTest() {
        ArrayList<DayOfWeek> days = new ArrayList<>();
        days.add(DayOfWeek.FRIDAY);
        Course c = new Course(days, "8:00:00 AM","08:50:00 AM");
    }
}