import java.awt.*;
import java.util.ArrayList;

public class ChooseSchedulePage extends Page{

    private ArrayList<Schedule> schedules;
    public void openSchedule(Schedule scheduleToOpen){}
    public void deleteSchedule(Schedule scheduleToDelete){}
    public void draw(Graphics g){}
    public ChooseSchedulePage(App app){
        super();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5, 5, 5);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        setLayout(new GridBagLayout());

        HomePageScheduleComponent scheduleComponent = new HomePageScheduleComponent();
        add(scheduleComponent);



    }
}
