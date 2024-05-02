import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;


public class PDF  {

    public static void create (Schedule schedule, Student student, File file) throws IOException {
        PDDocument doc = new PDDocument();
        PDPage page = new PDPage();
        doc.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(doc,page);
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
        contentStream.setLeading(17.5f);

        contentStream.newLineAtOffset(50,700);
        contentStream.showText(student.getInformation().getFirstName() + " " + student.getInformation().getLastName());
        contentStream.newLine();
        contentStream.showText(schedule.getTitle());
        contentStream.newLine();
        contentStream.showText("TERM: " + schedule.getTerm());
        contentStream.newLine();
        contentStream.showText("_____________________________________________");
        contentStream.newLine();

        for (Course course : schedule.getCourses()) {

            HashMap<DayOfWeek, ArrayList<LocalTime>> meetingTimes = course.getMeetingTimes();
            StringBuilder meetingSB = new StringBuilder();
            meetingSB.append("Meeting times: ");
            for (DayOfWeek day : meetingTimes.keySet()) {
                meetingSB.append(day.toString());
                meetingSB.append(" ");
                for (LocalTime time : meetingTimes.get(day)) {
                    meetingSB.append(time.toString());
                    meetingSB.append(" ");
                }
            }

            contentStream.showText(course.getName() + " (" + course.getCode() + ")");
            contentStream.newLine();
            contentStream.showText(meetingSB.toString());
            contentStream.newLine();
            contentStream.showText("___________________");
            contentStream.newLine();
        }

        contentStream.endText();
        contentStream.close();

        doc.save(file);
        doc.close();
    }
}
