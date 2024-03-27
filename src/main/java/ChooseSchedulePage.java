import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;

public class ChooseSchedulePage extends Page{

    private JPanel schedulePanel;
    private JLabel userNameLabel;
    public void openSchedule(Schedule schedule){
        App app = App.getInstance();
        app.setCurrSchedule(schedule);
        app.switchPages("schedule-page");
    }
    public void deleteSchedule(Schedule scheduleToDelete){
        App.getInstance().getLoggedInStudent().deleteSchedule(scheduleToDelete);
    }
    public void draw(){
        App app = App.getInstance();
        Student loggedInStudent = app.getLoggedInStudent();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        int width = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
        int height = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
        Dimension topScreen = new Dimension(width, height/3);
        Dimension bottomScreen = new Dimension(width, ((height/3) * 2));

        JPanel homeButtonsContainer = new JPanel();
        homeButtonsContainer.setMinimumSize(topScreen);
        homeButtonsContainer.setMaximumSize(topScreen);
        homeButtonsContainer.setPreferredSize(topScreen);
        homeButtonsContainer.setBackground(Color.white);
        homeButtonsContainer.setFocusable(true);
        add(homeButtonsContainer, 0);

        homeButtonsContainer.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.weighty = 1;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        JButton addScheduleBtn = new JButton("ADD SCHEDULE");
        addScheduleBtn.addActionListener((e) -> {
            Schedule schedule = new Schedule();
            loggedInStudent.addSchedule(schedule);
            HomePageScheduleComponent scheduleComponent = new HomePageScheduleComponent(schedule, this);
            schedulePanel.add(scheduleComponent);
            schedulePanel.revalidate();
            scheduleComponent.repaint();
        });
        homeButtonsContainer.add(addScheduleBtn, gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        userNameLabel = new JLabel();
        homeButtonsContainer.add(userNameLabel, gbc);


        gbc.anchor = GridBagConstraints.NORTHEAST;
        JButton logoutBtn = new JButton("LOG OUT");
        logoutBtn.addActionListener((event) ->{
            App.getInstance().switchPages("login-page");
        });
        homeButtonsContainer.add(logoutBtn, gbc);

        schedulePanel = new JPanel();
        schedulePanel.setLayout(new GridLayout(0, 3, 10, 10));
        schedulePanel.setBackground(Color.white);

        //Add In New Schedule Components for Loaded Student

        userNameLabel.setText("Hello " + app.getLoggedInStudent().getInformation().getFirstName() + "!");
        for(Schedule schedule: loggedInStudent.getSchedules()) {
            HomePageScheduleComponent scheduleComponent = new HomePageScheduleComponent(schedule, this);
            schedulePanel.add(scheduleComponent);
        }

        JScrollPane scrollPane = new JScrollPane(schedulePanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setMinimumSize(bottomScreen);
        scrollPane.setMaximumSize(bottomScreen);
        scrollPane.setPreferredSize(bottomScreen);
        add(scrollPane);
    }

}
