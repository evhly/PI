import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SchedulePage extends Page {

    final DefaultListModel<String> searchResults = new DefaultListModel<>();
    private Schedule schedule;
    public void draw(Graphics g){}
    public SchedulePage(App app){

        Search search = init();

        setLayout(new MigLayout("fill"));
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
        add(backBtn, "cell 0 0");

        JButton undoBtn = new JButton();
        undoBtn.setIcon(undoIcon);
        add(undoBtn, "cell 2 0, align right");

        JButton pdfBtn = new JButton();
        pdfBtn.setIcon(pdfIcon);
        add(pdfBtn, "cell 3 0, align left, wrap");


        JTextField searchBar = new JTextField();
        searchBar.setPreferredSize(new Dimension(200,20));
        JButton searchBtn = new JButton("SEARCH");
        add(searchBar, "align right");

        JList<String> list = new JList<>(searchResults);
        searchBtn.addActionListener((event) -> {
            String query = searchBar.getText();
            search.modifyQuery(query);
            System.out.println(search.resultsStrs().length);
            searchResults.clear();
            for(String s : search.resultsStrs()) {
                searchResults.addElement(s);
            }
        });
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.blue));
        add(scrollPane);


        CalendarComponent calendar = new CalendarComponent();
        add(searchBtn);
        add(calendar, "span 1 2, align right, wrap");
        DefaultListModel<Course> model = new DefaultListModel<>();
        JList<Course> courseList = new JList<>( model );
        add(courseList, "top, align center, wrap");

        JTextArea courseInfo = new JTextArea(7, 61);
        courseInfo.setText("MORE INFO ABOUT CLASS");
        courseInfo.setEditable(false);
        courseInfo.setBorder(BorderFactory.createLineBorder(Color.black));
        add(courseInfo, "cell 2 3, align right");

    }

    private Search init(){
        CourseDatabase courseDB = new CourseDatabase();
        ArrayList<Course> courses = new ArrayList<>();
        Course c1 = new Course("COMP 141 A", "Computer Programming 1", null, 3, null, null, null, null, null, null, null, null);
        Course c2 = new Course("COMP 141 B", "Computer Programming 1", null, 3, null, null, null, null, null, null, null, null);
        Course c3 = new Course("COMP 220 A", "Computer Programming 2", null, 3, null, null, null, null, null, null, null, null);
        courseDB.addCourse(c1);
        courseDB.addCourse(c2);
        courseDB.addCourse(c3);
        Search s = new Search(courseDB);
        return s;

    }
}
