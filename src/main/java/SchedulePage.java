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
import java.util.stream.Collectors;
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

        setLayout(null);
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
        backBtn.setBounds(25, 0, 60, 35);
        add(backBtn);

        JLabel scheduleTitle = new JLabel(schedule.getTitle());
        scheduleTitle.setBounds(300, 0, 100, 50);
        add(scheduleTitle);

        JButton editTitleBtn = new JButton();
        editTitleBtn.setIcon(editIcon);
        editTitleBtn.setBounds(400, 0, 60, 35);
        add(editTitleBtn);

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
        statusSheetBtn.setBounds(500, 0, 150, 35);
        add(statusSheetBtn);

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

        JLabel departmentLabel = new JLabel("Department");
        departmentLabel.setBounds(25, 625, 100, 25);
        add(departmentLabel);
        JComboBox<String >departmentComboBox = new JComboBox<>(departmentFilter);
        departmentComboBox.setBounds(25, 650, 100, 25);
        add(departmentComboBox);


        Professor[] facultyFilter = {
                new Professor("", ""),
                new Professor("Brian", "Dellinger"),
                new Professor("Brian", "Dickinson"),
                new Professor("Dale", "McIntyre")
        };

        JLabel professorLabel = new JLabel("Professor");
        professorLabel.setBounds(150, 625, 100, 25);
        add(professorLabel);
        JComboBox<Professor>facultyComboBox = new JComboBox<>(facultyFilter);
        facultyComboBox.setBounds(150, 650, 100, 25);
        add(facultyComboBox);


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
        JLabel startTimeLabel = new JLabel("Start Time");
        startTimeLabel.setBounds(275, 625, 100, 25);
        add(startTimeLabel);
        JLabel ampm = new JLabel("AM/PM");
        ampm.setBounds(400, 625, 100, 25);
        add(ampm);
        JComboBox<String> startTimeAmPm = new JComboBox<>(amPm);
        startTimeFilterCB.setBounds(275, 650, 100, 25);
        add(startTimeFilterCB);
        startTimeAmPm.setBounds(400, 650, 100, 25);
        add(startTimeAmPm);


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
        JLabel endTimeLabel = new JLabel("End time");
        endTimeLabel.setBounds(525, 625, 100, 25);
        add(endTimeLabel);
        JLabel ampm2 = new JLabel("AM/PM");
        ampm2.setBounds(650, 625, 100, 25);
        add(ampm2);
        JComboBox<String> endTimeFilterCB = new JComboBox<>(endTimeFilter);
        JComboBox<String> endTimeAmPm = new JComboBox<>(amPm);
        endTimeFilterCB.setBounds(525, 650, 100, 25);
        add(endTimeFilterCB);
        endTimeAmPm.setBounds(650, 650, 100, 25);
        add(endTimeAmPm);

//        Set<String> termSet = app.getCourseReader().getTerms();
//        String[] terms = new String[termSet.size()];
//        terms = termSet.toArray(terms);
//        JComboBox<String>termsComboBox = new JComboBox<>(terms);
//        termsComboBox.setSelectedItem(app.getCourseDatabase().getTerm());
//        add(termsComboBox, "cell 0 1");



        JTextField searchBar = new JTextField();
        searchBar.setBounds(25, 100, 150, 25);
        add(searchBar);

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
        searchBtn.setBounds(175, 100, 100, 30);
        add(searchBtn);

        // when search button is pressed, display all the search results for the current search query
        JList<Course> list = new JList<>(searchResults);

        CalendarComponent calendar = new CalendarComponent();
        calendar.draw();
        calendar.setBounds(700, 0, 550, 600);
        add(calendar);

        JLabel currentCoursesLabel = new JLabel("Current Courses");
        currentCoursesLabel.setBounds(500, 75, 100, 25);
        add(currentCoursesLabel);
        add(currentCoursesLabel);

        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                String toDisplay = "";
                if (e.getValueIsAdjusting()) {
                    String title = list.getSelectedValue().getName();
                    String profFN = list.getSelectedValue().getProfessor().getFirstName();
                    String profLN = list.getSelectedValue().getProfessor().getLastName();
                    String times = list.getSelectedValue().getMeetingTimes().entrySet().stream()
                            .map(map-> map.getKey()+": "+map.getValue())
                            .collect(Collectors.joining(", "));
                    toDisplay = ("Add Course? \n" + title + ": " + profFN + " " + profLN + "\n" + times);
                }

                int popupChoice = JOptionPane.showConfirmDialog(null, toDisplay);
                // when the plus button is pressed, add the course currently selected in the search results
                if(popupChoice == JOptionPane.YES_OPTION) {
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
                            int popupConflict = JOptionPane.showConfirmDialog(null, "Time conflict - Replace current course with new course?");
                            if(popupConflict == JOptionPane.YES_OPTION){
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
                }
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

        JLabel searchResultsLabel = new JLabel("Search Results");
        searchResultsLabel.setBounds(300, 75, 100, 25);
        add(searchResultsLabel);
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.black));
        scrollPane.setBounds(300, 100, 175, 500);
        add(scrollPane);


        String[] scheduleTextList = new String[schedule.getCourses().size()];
        for(int i = 0; i < schedule.getCourses().size(); i++){
            scheduleTextList[i] = schedule.getCourses().get(i).getCode();
        }


        JList<Course> curScheduleList = new JList<>(schedule.getCourses().toArray(new Course[0]));
        JScrollPane scheduleListPane = new JScrollPane(curScheduleList);
        scheduleListPane.setBorder(BorderFactory.createLineBorder(Color.black));
        scheduleListPane.setBounds(475, 100, 175, 500);
        add(scheduleListPane);


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
