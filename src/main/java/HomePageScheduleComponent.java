import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class HomePageScheduleComponent extends JPanel {

    public static ImageIcon trashcanIcon = new ImageIcon("resources/recycle-bin-icon.png");
    public static ImageIcon pencilIcon = new ImageIcon("resources/pencil-icon.png");

    public HomePageScheduleComponent(Schedule schedule, ChooseSchedulePage csp){
        setMinimumSize(new Dimension(150, 150));
        setMaximumSize(new Dimension(150, 150));
        setPreferredSize(new Dimension(150, 150));
        setBackground(Color.white);
        setBorder(BorderFactory.createLineBorder(Color.black));
        setFocusable(true);

        setLayout(new MigLayout("fill"));

        Dimension btnDimension = new Dimension(30, 30);

        JLabel scheduleTitle = new JLabel(schedule.getTitle());
        add(scheduleTitle, "span, wrap, align center");
        JLabel scheduleTerm = new JLabel(schedule.getTerm());
        add(scheduleTerm, "span, wrap, align center");

        JButton deleteSchedule = new JButton();
        Image trashcan = trashcanIcon.getImage();
        Image newimg = trashcan.getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH);
        trashcanIcon = new ImageIcon(newimg);
        deleteSchedule.setIcon(trashcanIcon);
        deleteSchedule.setPreferredSize(btnDimension);
        deleteSchedule.addActionListener((e) -> {
            Container parent = getParent();
            parent.remove(this);
            parent.revalidate();
            parent.repaint();
            csp.deleteSchedule(schedule);
        });
        add(deleteSchedule,"align center");

        JButton editSchedule = new JButton();
        Image pencil = pencilIcon.getImage();
        Image newimg2 = pencil.getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH);
        pencilIcon = new ImageIcon(newimg2);
        editSchedule.setIcon(pencilIcon);
        editSchedule.setPreferredSize(btnDimension);
        editSchedule.addActionListener((event) -> {
            csp.openSchedule(schedule);
        });
        add(editSchedule, "align center");
    }

}
