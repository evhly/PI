import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.MaskFormatter;
import javax.swing.text.html.CSS;

class MenuItemListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getActionCommand().equals("Computer Science")) {
                Desktop.getDesktop().browse(new URI("https://my.gcc.edu/docs/registrar/programguides/statussheets/2023/ComputerScienceBS_2027.pdf"));
            } else {
                Desktop.getDesktop().browse(new URI("https://my.gcc.edu/docs/registrar/programguides/statussheets/2023/Mathematics_2027.pdf"));
            }
        }catch(Exception exc){
            exc.printStackTrace();
        }
    }
}

public class SchedulePage extends Page {

    final DefaultListModel<Course> searchResults = new DefaultListModel<>();

    boolean viewStatusSheets = false;
    JFormattedTextField startTimeField;
    MaskFormatter startTimeMask;

    JFormattedTextField endTimeField;
    MaskFormatter endTimeMask;


    public void draw(){
        App app = App.getInstance();
        Schedule schedule = app.getCurrSchedule();

        setLayout(new MigLayout("fill"));
        ImageIcon backArrowIcon = new ImageIcon("resources/arrow-left-icon.png");
        ImageIcon undoIcon = new ImageIcon("resources/reply-arrow-icon.png");
        ImageIcon pdfIcon = new ImageIcon("resources/pdf-files-icon.png");
        ImageIcon plusIcon = new ImageIcon("resources/plus-line-icon.png");
        ImageIcon deleteIcon = new ImageIcon("resources/minus-round-line-icon.png");
        ImageIcon editIcon = new ImageIcon("resources/pencil-icon.png");


        Image backArrow = backArrowIcon.getImage();
        Image newimg = backArrow.getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH);
        backArrowIcon = new ImageIcon(newimg);

        Image plus = plusIcon.getImage();
        Image newimg4 = plus.getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH);
        plusIcon = new ImageIcon(newimg4);

        Image edit = editIcon.getImage();
        Image newimg6 = edit.getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH);
        editIcon = new ImageIcon(newimg6);


        JButton backBtn = new JButton();
        backBtn.setIcon(backArrowIcon);
        backBtn.addActionListener((event) -> {
            app.switchPages("choose-schedule-page");
        });
        add(backBtn, "cell 0 0");

        JLabel scheduleTitle = new JLabel(schedule.getTitle());
        add(scheduleTitle, "cell 2 0, align right");

        JButton editTitleBtn = new JButton();
        editTitleBtn.setIcon(editIcon);
        add(editTitleBtn, "cell 3 0, align left");

        editTitleBtn.addActionListener((event) -> {
            String title = JOptionPane.showInputDialog("Enter Schedule Name", null);
            if(title == null) {
                schedule.rename(scheduleTitle.getText());
            }else {
                schedule.rename(title);
            }
            app.getLoggedInStudent().save();
            redraw();
        });

        JButton statusSheetBtn = new JButton("View Status Sheets");

        add(statusSheetBtn, "cell 3 0");

        JPopupMenu statusSheetPopup = new JPopupMenu();
        JMenuItem CSStatusSheet = new JMenuItem("Computer Science");
        CSStatusSheet.setActionCommand("Computer Science");
        JMenuItem mathStatusSheet = new JMenuItem("Math");
        mathStatusSheet.setActionCommand("Math");
        MenuItemListener menuListener = new MenuItemListener();
        CSStatusSheet.addActionListener(menuListener);
        mathStatusSheet.addActionListener(menuListener);
        statusSheetPopup.add(CSStatusSheet);
        statusSheetPopup.add(mathStatusSheet);
        add(statusSheetPopup);
        statusSheetBtn.addActionListener((event) -> {
            statusSheetPopup.setVisible(true);
            statusSheetPopup.setPopupSize(200, 100);
            statusSheetPopup.show(this,500, 80);
        });

        String[] departmentFilter = {
                "",
                "COMP",
                "MATH"
        };

        JComboBox<String >departmentComboBox = new JComboBox<>(departmentFilter);
        add(departmentComboBox, "cell 2 2");


        Professor[] facultyFilter = {
                new Professor("", ""),
                new Professor("Brian", "Dellinger"),
                new Professor("Brian", "Dickinson"),
                new Professor("Dale", "McIntyre")
        };

        JComboBox<Professor>facultyComboBox = new JComboBox<>(facultyFilter);
        add(facultyComboBox, "cell 2 2");


        String[] startTimeFilter = {
                "",
                "1",
                "2",
                "3",
                "4",
                "5",
                "6",
                "7",
                "8",
                "9",
                "10",
                "11",
                "12"
        };
        JComboBox<String> startTimeFilterCB = new JComboBox<>(startTimeFilter);
        String[] amPm = {
                "",
                "AM",
                "PM"
        };
        JComboBox<String> startTimeAmPm = new JComboBox<>(amPm);
        add(startTimeFilterCB, "cell 3 2");
        add(startTimeAmPm, "cell 3 2");


        String[] endTimeFilter = {
                "",
                "1",
                "2",
                "3",
                "4",
                "5",
                "6",
                "7",
                "8",
                "9",
                "10",
                "11",
                "12"
        };
        JComboBox<String> endTimeFilterCB = new JComboBox<>(endTimeFilter);
        JComboBox<String> endTimeAmPm = new JComboBox<>(amPm);
        add(endTimeFilterCB, "cell 3 2");
        add(endTimeAmPm, "cell 3 2");

//        Set<String> termSet = app.getCourseReader().getTerms();
//        String[] terms = new String[termSet.size()];
//        terms = termSet.toArray(terms);
//        JComboBox<String>termsComboBox = new JComboBox<>(terms);
//        termsComboBox.setSelectedItem(app.getCourseDatabase().getTerm());
//        add(termsComboBox, "cell 0 1");

        JTextArea courseInfo = new JTextArea();

        courseInfo.setEditable(false);
        courseInfo.setBorder(BorderFactory.createLineBorder(Color.black));
//        add(courseInfo, "cell 4 0, align right");

        JButton plusBtn = new JButton();
        plusBtn.setIcon(plusIcon);
//        add(plusBtn, "cell 4 0, align right, wrap");


        JTextField searchBar = new JTextField();
        searchBar.setPreferredSize(new Dimension(150,20));
        add(searchBar, "cell 0 1");

        JButton searchBtn = new JButton("SEARCH");
        searchBtn.addActionListener((event) -> {
            Search search = new Search(app.getCourseDatabase());
            String query = searchBar.getText();
            search.modifyQuery(query);
            searchResults.clear();
            String department = (String) departmentComboBox.getSelectedItem();
            Professor professor = (Professor) facultyComboBox.getSelectedItem();
            String startTime = (String)startTimeFilterCB.getSelectedItem();
            String startAmPm = (String)startTimeAmPm.getSelectedItem();
            System.out.println("START TIME: " + startTime);
            String endTime = (String)endTimeFilterCB.getSelectedItem();
            String endAmPm = (String)endTimeAmPm.getSelectedItem();
            System.out.println("END TIME: " + endTime);
            Filter departmentFilterSelected = new Filter(Filter.type.DEPARTMENT, department);
            Filter facultyFilterSelected = new Filter(Filter.type.PROFESSOR, (Professor) professor);
            Filter timeFilterSelected = new Filter(Filter.type.TIMES, startTime + ":00:00 " + startAmPm, endTime + ":00:00 " + endAmPm /*startAmPm, endAmPm*/);

            if(!Objects.equals(department, "")) {
                search.addFilter(departmentFilterSelected);
            }
            if(!Objects.equals(professor, new Professor("", ""))) {
                search.addFilter(facultyFilterSelected);
            }
            if(!Objects.equals(startTime, "") && !Objects.equals(endTime, "")
                    && !Objects.equals(startAmPm, "") && !Objects.equals(endAmPm, "")) {
                search.addFilter(timeFilterSelected);
            }

            for(Course c : search.search()) {
                searchResults.addElement(c);
            }
        });
        add(searchBtn, "cell 3 1");

        // when search button is pressed, display all the search results for the current search query
        JList<Course> list = new JList<>(searchResults);

        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                String toDisplay = "";
                if (e.getValueIsAdjusting()) {
                    String title = list.getSelectedValue().getName();
                    String profFN = list.getSelectedValue().getProfessor().getFirstName();
                    String profLN = list.getSelectedValue().getProfessor().getLastName();
                    String time = list.getSelectedValue().getMeetingTimes().toString();
                    toDisplay = (title + ": " + profFN + " " + profLN + "\n" + time);
                }

                int popupChoice = JOptionPane.showConfirmDialog(null, toDisplay);
            }
        });


        searchBar.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                Search search = new Search(app.getCourseDatabase());
                String query = searchBar.getText();
                String department = (String) departmentComboBox.getSelectedItem();
                Professor professor = (Professor) facultyComboBox.getSelectedItem();

                ArrayList<Course> courses = search.searchBarSearch(query, department, professor);
                searchResults.clear();

                for(Course c : courses) {
                    searchResults.addElement(c);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }

        });

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.black));
        scrollPane.setMinimumSize(new Dimension(300,500));
        scrollPane.setMaximumSize(new Dimension(300,500));
        scrollPane.setPreferredSize(new Dimension(300,500));
        add(scrollPane, "cell 2 1");


        String[] scheduleTextList = new String[schedule.getCourses().size()];
        for(int i = 0; i < schedule.getCourses().size(); i++){
            scheduleTextList[i] = schedule.getCourses().get(i).getCode();
        }
        JList<Course> curScheduleList = new JList<>(schedule.getCourses().toArray(new Course[0]));
        JScrollPane scheduleListPane = new JScrollPane(curScheduleList);
        scheduleListPane.setBorder(BorderFactory.createLineBorder(Color.black));
        scheduleListPane.setMinimumSize(new Dimension(150,500));
        scheduleListPane.setMaximumSize(new Dimension(150,500));
        scheduleListPane.setPreferredSize(new Dimension(150,500));
        add(scheduleListPane, "cell 3 1");

        CalendarComponent calendar = new CalendarComponent();
        calendar.draw();

        add(calendar, "span 1 0, align right, wrap");
        DefaultListModel<Course> model = new DefaultListModel<>();
        JList<Course> courseList = new JList<>( model );
        add(courseList, "top, align center, wrap");


        // when the plus button is pressed, add the course currently selected in the search results
        plusBtn.addActionListener((event) -> {
            if(list.getSelectedIndex() != -1) {
                Course selected = searchResults.getElementAt(list.getSelectedIndex());
                if (schedule.addCourse(selected)) {
                    calendar.removeAll();
                    calendar.add(new CalendarComponent());
                    calendar.repaint();
                    calendar.revalidate();
                    app.getLoggedInStudent().save();
                    redraw();
                } else {
                    int popupChoice = JOptionPane.showConfirmDialog(null, "Time conflict - Replace current course with new course?");
                    if(popupChoice == JOptionPane.YES_OPTION){
                        for(int i = 0; i < schedule.getCourses().size(); i++){
                            if(schedule.getCourses().get(i).hasConflict(selected)){
                                schedule.deleteCourse(schedule.getCourses().get(i));
                                schedule.addCourse(selected);
                                calendar.removeAll();
                                calendar.add(new CalendarComponent());
                                calendar.repaint();
                                calendar.revalidate();
                                app.getLoggedInStudent().save();
                                redraw();
                            }
                        }
                    }
                }
            }
        });

        // delete a course when delete key is pressed and a schedule in the schedule pane is selected
        Action deleteCourse = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                int row = curScheduleList.getSelectedIndex();
                if (row >= 0) {
                    Course courseToDelete = curScheduleList.getSelectedValue();
//                    System.out.println("about to delete " + courseToDelete.getCode());
                    schedule.deleteCourse(courseToDelete);
                    app.getLoggedInStudent().save();
                    redraw();
                }
            }
        };
        InputMap inputMap = scheduleListPane.getInputMap(
                JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke("DELETE"),
                "delete");
        scheduleListPane.getActionMap().put("delete",
                deleteCourse);

//        termsComboBox.addItemListener(new ItemListener() {
//            public void itemStateChanged(ItemEvent arg0) {
//                String newTerm = (String)termsComboBox.getSelectedItem();
//                app.setCourseDatabase(app.getCourseReader().getCourseDatabase(newTerm));
//                app.setCurrSchedule(new Schedule());
//                calendar.removeAll();
//                calendar.add(new CalendarComponent());
//                calendar.repaint();
//                calendar.revalidate();
//                app.getLoggedInStudent().save();
//                redraw();
//            }
//        });
    }
}
