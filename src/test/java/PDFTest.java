import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class PDFTest {

    @Test
    void pdfHelloWorld() throws IOException {
        Schedule schedule = new Schedule("MySchedule","XYZ");
        for (int i = 1; i <= 5; i++) {
            HashMap<DayOfWeek, ArrayList<LocalTime>> meetingTimes = new HashMap<>();
            ArrayList<LocalTime> times = new ArrayList();
            times.add(LocalTime.NOON);
            meetingTimes.put(DayOfWeek.MONDAY, times);
            meetingTimes.put(DayOfWeek.TUESDAY, times);
            schedule.addCourseWithoutCheckingOrLogging(new Course("TEST 10" + i,
                    "Test Class Number " + i, meetingTimes));
        }
        PDF.create(schedule);
    }
}
