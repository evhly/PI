import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class SchedulePage extends Page {

    final DefaultListModel<Course> searchResults = new DefaultListModel<>();
    Set<String> terms;
    CourseReader CR;
    Search search;
    String curTerm = "F20";
    private Schedule schedule;
    public void draw(){}
    public SchedulePage(App app){
        search = init();
//        readCsvs();

        setLayout(new MigLayout("fill"));
        ImageIcon backArrowIcon = new ImageIcon("resources/arrow-left-icon.png");
        ImageIcon undoIcon = new ImageIcon("resources/reply-arrow-icon.png");
        ImageIcon pdfIcon = new ImageIcon("resources/pdf-files-icon.png");
        ImageIcon plusIcon = new ImageIcon("resources/plus-line-icon.png");


        Image backArrow = backArrowIcon.getImage();
        Image newimg = backArrow.getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH);
        backArrowIcon = new ImageIcon(newimg);

        Image undo = undoIcon.getImage();
        Image newimg2 = undo.getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH);
        undoIcon = new ImageIcon(newimg2);

        Image pdf = pdfIcon.getImage();
        Image newimg3 = pdf.getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH);
        pdfIcon = new ImageIcon(newimg3);

        Image plus = plusIcon.getImage();
        Image newimg4 = plus.getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH);
        plusIcon = new ImageIcon(newimg4);

        JButton backBtn = new JButton();
        backBtn.setIcon(backArrowIcon);
        add(backBtn, "cell 0 0");

//        JButton undoBtn = new JButton();
//        undoBtn.setIcon(undoIcon);
//        add(undoBtn, "cell 2 0, align right");

//        JButton pdfBtn = new JButton();
//        pdfBtn.setIcon(pdfIcon);
//        add(pdfBtn, "cell 3 0, align left, wrap");

        JButton plusBtn = new JButton();
        plusBtn.setIcon(plusIcon);
        add(plusBtn, "cell 4 0, align left, wrap");


        JTextField searchBar = new JTextField();
        searchBar.setPreferredSize(new Dimension(200,20));
        JButton searchBtn = new JButton("SEARCH");
        add(searchBar, "align right");

        JList<Course> list = new JList<>(searchResults);
        searchBtn.addActionListener((event) -> {
            String query = searchBar.getText();
            search.modifyQuery(query);
            System.out.println(search.resultsStrs().length);
            searchResults.clear();
            for(Course c : search.getResults()) {
                searchResults.addElement(c);
            }
        });
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.blue));
        add(scrollPane, "cell 2 1");


        CalendarComponent calendar = new CalendarComponent(schedule);
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

        plusBtn.addActionListener((event) -> {
            Course selected = searchResults.getElementAt(list.getSelectedIndex());
            System.out.println(selected);
            if(schedule.addCourse(selected)){
                calendar.removeAll();
                calendar.add(new CalendarComponent(schedule));
                calendar.repaint();
                calendar.revalidate();
            } else {
                // TODO: error
            }
        });

    }

    private Search init(){
        schedule = new Schedule();

        ArrayList<LocalTime> times1 = new ArrayList<>();
        times1.add(LocalTime.parse("12:00:00"));
        HashMap<DayOfWeek, ArrayList<LocalTime>> myMap1 = new HashMap<>();
        myMap1.put(DayOfWeek.MONDAY, times1);

        ArrayList<LocalTime> times2 = new ArrayList<>();
        times2.add(LocalTime.parse("12:00:00"));
        HashMap<DayOfWeek, ArrayList<LocalTime>> myMap2 = new HashMap<>();
        myMap2.put(DayOfWeek.TUESDAY, times2);

        ArrayList<LocalTime> times = new ArrayList<>();
        times.add(LocalTime.parse("12:00:00"));
        HashMap<DayOfWeek, ArrayList<LocalTime>> myMap = new HashMap<>();
        myMap.put(DayOfWeek.TUESDAY, times);

        CourseDatabase courseDB = new CourseDatabase();
        Course c1 = new Course("COMP 141 A", "Computer Programming 1", null, 3, null, null, null, null,
                myMap2, null, null);
        Course c2 = new Course("COMP 141 B", "Computer Programming 1", null, 3, null, null, null, null,
                myMap1, null, null);
        Course c3 = new Course("COMP 220 A", "Computer Programming 2", null, 3, null, null, null, null,
                myMap,
                null, null);
        courseDB.addCourse(c1);
        courseDB.addCourse(c2);
        courseDB.addCourse(c3);
        search = new Search(courseDB);
        return search;

    }

    private void readCsvs(){
        schedule = new Schedule();
        CR = new CourseReader();
        String path = "src/main/csvs";
        File folder = new File(path);
        for(File fileEntry : folder.listFiles()){
            if(!fileEntry.isDirectory()){
                String fullPath = path + "/" + fileEntry.getName();
                System.out.println("Attempting to read " + fullPath);
                CR.parseCsv(fullPath);
                System.out.println("Successfully read " + fullPath);
            }
        }
        terms = CR.getTerms();
        search = new Search(CR.getCourseDatabase("F20"));
    }
}
