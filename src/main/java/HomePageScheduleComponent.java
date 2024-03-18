import javax.swing.*;
import java.awt.*;

public class HomePageScheduleComponent extends JPanel {

    public HomePageScheduleComponent(){

        setMinimumSize(new Dimension(150, 150));
        setMaximumSize(new Dimension(150, 150));
        setPreferredSize(new Dimension(150, 150));
        setBackground(Color.white);
        setBorder(BorderFactory.createLineBorder(Color.black));
        setFocusable(true);

        JLabel scheduleTitle = new JLabel("SCHEDULE NAME");
        add(scheduleTitle);
        JButton deleteSchedule = new JButton("DELETE");
        add(deleteSchedule);
        JButton editSchedule = new JButton("EDIT");
        add(editSchedule);

    }

}
