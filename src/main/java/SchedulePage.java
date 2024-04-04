import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

public class SchedulePage extends Page {

    final DefaultListModel<Course> searchResults = new DefaultListModel<>();
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

        Image undo = undoIcon.getImage();
        Image newimg2 = undo.getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH);
        undoIcon = new ImageIcon(newimg2);

        Image pdf = pdfIcon.getImage();
        Image newimg3 = pdf.getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH);
        pdfIcon = new ImageIcon(newimg3);

        Image plus = plusIcon.getImage();
        Image newimg4 = plus.getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH);
        plusIcon = new ImageIcon(newimg4);

        Image delete = deleteIcon.getImage();
        Image newimg5 = delete.getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH);
        deleteIcon = new ImageIcon(newimg5);

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
            schedule.rename(title);
            redraw();
        });


        String[] departmentFilter = {
                "",
                "COMP",
                "MATH"
        };

        JComboBox<String >departmentComboBox = new JComboBox<>(departmentFilter);
        add(departmentComboBox, "cell 3 2");


        Professor[] facultyFilter = {
                new Professor("", ""),
                new Professor("Brian", "Dellinger"),
                new Professor("Brian", "Dickinson"),
                new Professor("Dale", "McIntyre")
        };

        JComboBox<Professor>facultyComboBox = new JComboBox<>(facultyFilter);
        add(facultyComboBox, "cell 3 2");

//        JButton undoBtn = new JButton();
//        undoBtn.setIcon(undoIcon);
//        add(undoBtn, "cell 2 0, align right");

//        JButton pdfBtn = new JButton();
//        pdfBtn.setIcon(pdfIcon);
//        add(pdfBtn, "cell 3 0, align left, wrap");

        JButton plusBtn = new JButton();
        plusBtn.setIcon(plusIcon);
        add(plusBtn, "cell 4 0, align right, wrap");

//        JButton deleteBtn = new JButton();
//        deleteBtn.setIcon(deleteIcon);
//        add(deleteBtn, "cell 5 0, align right, wrap");


        JTextField searchBar = new JTextField();
        searchBar.setPreferredSize(new Dimension(150,20));
        JButton searchBtn = new JButton("SEARCH");
        add(searchBar, "cell 0 1");

        JList<Course> list = new JList<>(searchResults);
        searchBtn.addActionListener((event) -> {
            Search search = new Search(app.getCourseDatabase());
            String query = searchBar.getText();
            search.modifyQuery(query);
            searchResults.clear();
            String department = (String) departmentComboBox.getSelectedItem();
            Professor professor = (Professor) facultyComboBox.getSelectedItem();
            Filter departmentFilterSelected = new Filter(Filter.type.DEPARTMENT, department);
            Filter facultyFilterSelected = new Filter(Filter.type.PROFESSOR, (Professor) professor);

            if(!Objects.equals(department, "")) {
                search.addFilter(departmentFilterSelected);
            }
            if(!Objects.equals(professor, new Professor("", ""))) {
                search.addFilter(facultyFilterSelected);
            }

            for(Course c : search.search()) {
                searchResults.addElement(c);
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
//        scheduleListPane.add(new JLabel("Current Schedule")); // TODO: add title
        scheduleListPane.setBorder(BorderFactory.createLineBorder(Color.black));
        scheduleListPane.setMinimumSize(new Dimension(150,500));
        scheduleListPane.setMaximumSize(new Dimension(150,500));
        scheduleListPane.setPreferredSize(new Dimension(150,500));
        add(scheduleListPane, "cell 2 1");


        CalendarComponent calendar = new CalendarComponent();
        calendar.draw();

        add(searchBtn, "cell 3 1");
        add(calendar, "span 1 0, align right, wrap");
        DefaultListModel<Course> model = new DefaultListModel<>();
        JList<Course> courseList = new JList<>( model );
        add(courseList, "top, align center, wrap");

        JTextArea courseInfo = new JTextArea(7, 61);

        courseInfo.setEditable(false);
        courseInfo.setBorder(BorderFactory.createLineBorder(Color.black));
        add(courseInfo, "cell 4 3, align right");


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
                    courseInfo.setText("Time conflict - choose another course");
                }
            }
        });

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
    }
}
