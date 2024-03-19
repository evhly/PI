import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ChooseSchedulePage extends Page{

    private ArrayList<Schedule> schedules;
    public void openSchedule(Schedule scheduleToOpen){}
    public void deleteSchedule(Schedule scheduleToDelete){}
    public void draw(Graphics g){}
    public ChooseSchedulePage(App app){

        super();

//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.insets = new Insets(5,5, 5, 5);
//        gbc.gridwidth = GridBagConstraints.REMAINDER;
//        gbc.gridheight = GridBagConstraints.VERTICAL;
//        gbc.anchor = GridBagConstraints.CENTER;
//        gbc.fill = GridBagConstraints.HORIZONTAL;
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        int width = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
        int height = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
        Dimension halfScreen = new Dimension(width, height/2);

        JPanel homeButtonsContainer = new JPanel();
        //gbc.anchor = GridBagConstraints.SOUTH;
        homeButtonsContainer.setMinimumSize(halfScreen);
        homeButtonsContainer.setMaximumSize(halfScreen);
        homeButtonsContainer.setPreferredSize(halfScreen);
        homeButtonsContainer.setBackground(Color.red);
        homeButtonsContainer.setFocusable(true);
        add(homeButtonsContainer, 0);

        JPanel scheduleContainer = new JPanel();
        //gbc.anchor = GridBagConstraints.SOUTH;
        scheduleContainer.setMinimumSize(halfScreen);
        scheduleContainer.setMaximumSize(halfScreen);
        scheduleContainer.setPreferredSize(halfScreen);
        scheduleContainer.setBackground(Color.black);
        scheduleContainer.setFocusable(true);
        add(scheduleContainer);


        for(int i = 0; i < 3; i++){
            HomePageScheduleComponent scheduleComponent = new HomePageScheduleComponent(/*schedules.get(i)*/);
            scheduleContainer.add(scheduleComponent);
        }

    }
}
