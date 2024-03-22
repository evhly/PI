import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class SchedulePage extends Page {

    private Schedule schedule;
    public void draw(Graphics g){}
    public SchedulePage(App app){
        /*setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        JPanel topContainer = new JPanel();
        JLabel label = new JLabel("THIS IS THE TOP BAR");
        topContainer.add(label);
        add(topContainer);

        JPanel searchBarPanel = new JPanel();
        searchBarPanel.setLayout(new BoxLayout(searchBarPanel, BoxLayout.X_AXIS));
        searchBarPanel.setBackground(Color.RED);
        JTextField searchBar = new JTextField();
        searchBar.setMinimumSize(new Dimension(300, 64));
        searchBar.setMaximumSize(new Dimension(300, 64));
        searchBar.setPreferredSize(new Dimension(300, 64));
        JButton searchBtn = new JButton("Search");
        searchBarPanel.add(searchBar);
        searchBarPanel.add(searchBtn);
        //TODO change to course

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
        DefaultListModel<String> model = new DefaultListModel<>();
        JList<String> courseList = new JList<>( model );
        for ( int i = 0; i < 10; i++ ){
            model.addElement("Course" + i);
        }
        searchPanel.add(searchBarPanel);
        searchPanel.add(courseList);


        JPanel calendarInfoContainer = new JPanel();
        calendarInfoContainer.setLayout(new BoxLayout(calendarInfoContainer, BoxLayout.Y_AXIS));
        CalendarComponent calendar = new CalendarComponent();
        JTextArea courseInfo = new JTextArea(7, 50);
        courseInfo.setText("MORE INFO ABOUT CLASS");
        courseInfo.setEditable(false);
        courseInfo.setBorder(BorderFactory.createLineBorder(Color.black));
        calendarInfoContainer.add(calendar);
        calendarInfoContainer.add(courseInfo);

        JPanel bottomContainer = new JPanel();
        bottomContainer.setLayout(new BoxLayout(bottomContainer, BoxLayout.X_AXIS));
        bottomContainer.add(searchPanel);
        bottomContainer.add(calendarInfoContainer);
        add(bottomContainer);*/

        setLayout(new MigLayout());
        ImageIcon backArrowIcon = new ImageIcon("resources/arrow-left-icon.png");
        ImageIcon undoIcon = new ImageIcon("resources/reply-arrow-icon.png");
        ImageIcon pdfIcon = new ImageIcon("resources/pdf-files-icon.png");

        Image backArrow = backArrowIcon.getImage();
        Image newimg = backArrow.getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH);
        backArrowIcon = new ImageIcon(newimg);

        Image undo = undoIcon.getImage();
        Image newimg2 = undo.getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH);
        undoIcon = new ImageIcon(newimg2);

        Image pdf = pdfIcon.getImage();
        Image newimg3 = pdf.getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH);
        pdfIcon = new ImageIcon(newimg3);

        JButton backBtn = new JButton();
        backBtn.setIcon(backArrowIcon);
        add(backBtn);

        JButton undoBtn = new JButton();
        undoBtn.setIcon(undoIcon);
        add(undoBtn);

        JButton pdfBtn = new JButton();
        pdfBtn.setIcon(pdfIcon);
        add(pdfBtn, "wrap");



        JTextField searchBar = new JTextField();
        searchBar.setPreferredSize(new Dimension(100,20));
        JButton searchBtn = new JButton("SEARCH");
        add(searchBar, "top");
        CalendarComponent calendar = new CalendarComponent();
        add(searchBtn, "top");
        add(calendar, "span 1 2, wrap");
        DefaultListModel<String> model = new DefaultListModel<>();
        JList<String> courseList = new JList<>( model );
        for ( int i = 0; i < 10; i++ ){
            model.addElement("Course" + i);
        }
        add(courseList, "top, wrap");

        JTextArea courseInfo = new JTextArea(7, 50);
        courseInfo.setText("MORE INFO ABOUT CLASS");
        courseInfo.setEditable(false);
        courseInfo.setBorder(BorderFactory.createLineBorder(Color.black));
        add(courseInfo, "cell 2 3, span");

    }
}
