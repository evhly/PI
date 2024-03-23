import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;

public class ChooseSchedulePage extends Page{

    private JPanel schedulePanel;
    private Student loggedInStudent;
    private JLabel userNameLabel;
    public void openSchedule(Schedule scheduleToOpen){

    }
    public void deleteSchedule(Schedule scheduleToDelete){
        loggedInStudent.getSchedules().remove(scheduleToDelete);
        try {
            Schedule.saveSchedules(loggedInStudent);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void draw(Graphics g){}
    public ChooseSchedulePage(App app){
        super();

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

            try {
                Schedule.saveSchedules(loggedInStudent);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        homeButtonsContainer.add(addScheduleBtn, gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        userNameLabel = new JLabel("HELLO USERNAME");
        homeButtonsContainer.add(userNameLabel, gbc);


        gbc.anchor = GridBagConstraints.NORTHEAST;
        JButton logoutBtn = new JButton("LOG OUT");
        homeButtonsContainer.add(logoutBtn, gbc);

        schedulePanel = new JPanel();
        schedulePanel.setLayout(new GridLayout(0, 3, 10, 10));
        schedulePanel.setBackground(Color.white);

        JScrollPane scrollPane = new JScrollPane(schedulePanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setMinimumSize(bottomScreen);
        scrollPane.setMaximumSize(bottomScreen);
        scrollPane.setPreferredSize(bottomScreen);
        add(scrollPane);
    }


    public void loadStudentSchedules(Credentials credentials) throws IOException {
        CourseDatabase cb = new CourseDatabase("2020-2021.csv");
        loggedInStudent = new Student(credentials);
        Schedule.loadSchedules(cb, loggedInStudent);
        userNameLabel.setText(credentials.getFirstName());
        for(Schedule schedule: loggedInStudent.getSchedules()) {
            HomePageScheduleComponent scheduleComponent = new HomePageScheduleComponent(schedule, this);
            schedulePanel.add(scheduleComponent);
        }
    }

}
