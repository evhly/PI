import javax.swing.*;
import java.awt.*;

public class HomePageScheduleComponent extends JPanel {

    public static ImageIcon trashcanIcon = new ImageIcon("resources/recycle-bin-icon.png");
    public static ImageIcon pencilIcon = new ImageIcon("resources/pencil-icon.png");

    public HomePageScheduleComponent(/*Schedule schedule*/){
        setMinimumSize(new Dimension(150, 150));
        setMaximumSize(new Dimension(150, 150));
        setPreferredSize(new Dimension(150, 150));
        setBackground(Color.white);
        setBorder(BorderFactory.createLineBorder(Color.black));
        setFocusable(true);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10, 10, 10);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.NONE;
        setLayout(new GridBagLayout());

        Dimension btnDimension = new Dimension(30, 30);

        JLabel scheduleTitle = new JLabel(/*schedule.getTitle()*/ "SCHEDULE TITLE");
        add(scheduleTitle, gbc);

        JButton deleteSchedule = new JButton();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        Image trashcan = trashcanIcon.getImage();
        Image newimg = trashcan.getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH);
        trashcanIcon = new ImageIcon(newimg);
        deleteSchedule.setIcon(trashcanIcon);
        deleteSchedule.setPreferredSize(btnDimension);
        add(deleteSchedule, gbc);


        JButton editSchedule = new JButton();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        Image pencil = pencilIcon.getImage();
        Image newimg2 = pencil.getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH);
        pencilIcon = new ImageIcon(newimg2);
        editSchedule.setIcon(pencilIcon);
        editSchedule.setPreferredSize(btnDimension);
        add(editSchedule, gbc);
    }

}
