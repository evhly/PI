import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class ChooseSchedulePage extends Page{

    private JPanel schedulePanel;
    public void openSchedule(Schedule scheduleToOpen){}
    public void deleteSchedule(Schedule scheduleToDelete){}
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
            HomePageScheduleComponent scheduleComponent = new HomePageScheduleComponent(new Schedule(null, "Added Schedule"));
            schedulePanel.add(scheduleComponent);
            schedulePanel.revalidate();
            scheduleComponent.repaint();
        });
        homeButtonsContainer.add(addScheduleBtn, gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        JLabel userNameLabel = new JLabel("HELLO USERNAME");
        homeButtonsContainer.add(userNameLabel, gbc);


        gbc.anchor = GridBagConstraints.NORTHEAST;
        JButton logoutBtn = new JButton("LOG OUT");
        homeButtonsContainer.add(logoutBtn, gbc);

        schedulePanel = new JPanel();
        schedulePanel.setLayout(new GridLayout(0, 3, 10, 10));
        schedulePanel.setBackground(Color.white);

        for(Schedule schedule: app.getLoggedInStudent().getSchedules()) {
            HomePageScheduleComponent scheduleComponent = new HomePageScheduleComponent(schedule);
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
