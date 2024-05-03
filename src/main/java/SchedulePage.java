import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.MaskFormatter;

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

public class SchedulePage extends Page implements DocumentListener {

    DefaultListModel<Course> searchResults;


    boolean viewStatusSheets = false;
    JFormattedTextField startTimeField;
    MaskFormatter startTimeMask;

    JFormattedTextField endTimeField;
    MaskFormatter endTimeMask;

    JTextArea searchBar;

    private static final String COMMIT_ACTION = "commit";
    private static enum Mode { INSERT, COMPLETION };
    private Mode mode = Mode.INSERT;
    private Boolean suggestWordsFlag;


    public void draw(){
        App app = App.getInstance();
        searchResults = new DefaultListModel<>();
        Schedule schedule = app.getCurrSchedule();
        app.setCourseDatabase(app.getCourseReader().getCourseDatabase(schedule.getTerm()));
        suggestWordsFlag = new Search(app.getCourseDatabase()).isSuggestWordsFlag();

        setLayout(null);
        ImageIcon backArrowIcon = new ImageIcon("resources/arrow-left-icon.png");
        ImageIcon undoIcon = new ImageIcon("resources/reply-arrow-icon.png");
        ImageIcon pdfIcon = new ImageIcon("resources/pdf-files-icon.png");
        ImageIcon redoIcon = new ImageIcon("resources/redo-arrow-icon-1.png");
        ImageIcon editIcon = new ImageIcon("resources/pencil-icon.png");


        Image backArrow = backArrowIcon.getImage();
        Image newimg = backArrow.getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH);
        backArrowIcon = new ImageIcon(newimg);

        Image edit = editIcon.getImage();
        Image newimg2 = edit.getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH);
        editIcon = new ImageIcon(newimg2);

        Image pdf = pdfIcon.getImage();
        Image newimg3 = pdf.getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH);
        pdfIcon = new ImageIcon(newimg3);

        Image undo = undoIcon.getImage();
        Image newimg4 = undo.getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH);
        undoIcon = new ImageIcon(newimg4);

        Image redo = redoIcon.getImage();
        Image newimg5 = redo.getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH);
        redoIcon = new ImageIcon(newimg5);


        JButton backBtn = new JButton();
        backBtn.setBackground(Color.decode("#99002a"));
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
        editTitleBtn.setBackground(Color.decode("#99002a"));
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
        statusSheetBtn.setBackground(Color.decode("#99002a"));
        statusSheetBtn.setForeground(Color.white);
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


        JTextArea courseInfo = new JTextArea();

//        courseInfo.setEditable(false);
//        courseInfo.setBorder(BorderFactory.createLineBorder(Color.black));
//        add(courseInfo, "cell 4 0, align right");
//
//        JButton plusBtn = new JButton();
//        plusBtn.setBackground(Color.decode("#99002a"));
//        plusBtn.setIcon(plusIcon);
//        add(plusBtn, "cell 4 0, align right, wrap");


        searchBar = new JTextArea();
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        searchBar.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        searchBar.setBounds(25, 100, 140, 35);
        add(searchBar);

        searchBar.getDocument().addDocumentListener(this);
        InputMap im = searchBar.getInputMap();
        ActionMap am = searchBar.getActionMap();
        im.put(KeyStroke.getKeyStroke("ENTER"), COMMIT_ACTION);
        am.put(COMMIT_ACTION, new CommitAction());

        JButton searchBtn = new JButton("SEARCH");
        searchBtn.setBackground(Color.decode("#99002a"));
        searchBtn.setForeground(Color.white);
        searchBtn.addActionListener((event) -> {
            Search search = new Search(app.getCourseDatabase());
            String query = searchBar.getText().substring(0, searchBar.getCaretPosition());
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
        searchBtn.setBounds(175, 103, 100, 30);
        add(searchBtn);

        JButton pdfBtn = new JButton();
        pdfBtn.setBackground(Color.decode("#99002a"));
        pdfBtn.setIcon(pdfIcon);
        pdfBtn.addActionListener((event) -> {
            int popupChoice = JOptionPane.showConfirmDialog(null, "Save to PDF?");
            if(popupChoice == JOptionPane.YES_OPTION) {
                try {
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF", "pdf");
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setFileFilter(filter);
                    int choice = fileChooser.showSaveDialog(this);
                    if(choice == JFileChooser.APPROVE_OPTION) {
                        File file = fileChooser.getSelectedFile();
                        if (!file.getAbsolutePath().toLowerCase().endsWith(".pdf")) {
                            file = new File(file.getAbsolutePath() + ".pdf");
                        }
                        PDF.create(app.getCurrSchedule(), app.getLoggedInStudent(), file);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        pdfBtn.setBounds(925, 650, 60, 35);
        add(pdfBtn);


        // when search button is pressed, display all the search results for the current search query
        JList<Course> list = new JList<>(searchResults);

        CalendarComponent calendar = new CalendarComponent();
        calendar.draw();
        calendar.setBounds(700, 0, 550, 600);
        add(calendar);

        JButton undoBtn = new JButton();
        undoBtn.setBackground(Color.decode("#99002a"));
        undoBtn.setIcon(undoIcon);
        undoBtn.addActionListener((event) -> {
            int popupChoice = JOptionPane.showConfirmDialog(null, "Undo last action?");
            if(popupChoice == JOptionPane.YES_OPTION) {
                app.getCurrSchedule().undo();
                calendar.removeAll();
                calendar.add(new CalendarComponent());
                calendar.repaint();
                calendar.revalidate();
                app.getLoggedInStudent().save();
                redraw();
            }
        });
        undoBtn.setBounds(1000, 650, 60, 35);
        add(undoBtn);

        JButton redoBtn = new JButton();
        redoBtn.setBackground(Color.decode("#99002a"));
        redoBtn.setIcon(redoIcon);
        redoBtn.addActionListener((event) -> {
            int popupChoice = JOptionPane.showConfirmDialog(null, "Redo last action?");
            if(popupChoice == JOptionPane.YES_OPTION) {
                app.getCurrSchedule().redo();
                calendar.removeAll();
                calendar.add(new CalendarComponent());
                calendar.repaint();
                calendar.revalidate();
                app.getLoggedInStudent().save();
                redraw();
            }
        });
        redoBtn.setBounds(1075, 650, 60, 35);
        add(redoBtn);

        JLabel currentCoursesLabel = new JLabel("Current Courses");
        currentCoursesLabel.setBounds(500, 60, 100, 25);
        add(currentCoursesLabel);

        JLabel currentCoursesLabel2 = new JLabel("(select to delete)");
        currentCoursesLabel2.setBounds(500, 75, 100, 25);
        add(currentCoursesLabel2);


        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Course course = list.getSelectedValue();

                String title = course.getName();
                String profFN = course.getProfessor().getFirstName();
                String profLN = course.getProfessor().getLastName();
                String code = course.getCode();
                String times = course.getMeetingTimes().entrySet().stream()
                        .map(map-> map.getKey()+": "+map.getValue())
                        .collect(Collectors.joining(", "));
                String toDisplay = ("Add Course? \n" + title + ": " + code + " " + profFN + " " + profLN + "\n" + times);

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

            }

            @Override
            public void keyReleased(KeyEvent e) {
                Search search = new Search(app.getCourseDatabase());
                String query = searchBar.getText().substring(0, searchBar.getCaretPosition());
                String department = (String) departmentComboBox.getSelectedItem();
                Professor professor = (Professor) facultyComboBox.getSelectedItem();

                ArrayList<Course> courses = search.searchBarSearch(query, department, professor);
                searchResults.clear();

                for(Course c : courses) {
                    searchResults.addElement(c);
                }
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
        curScheduleList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String toDisplay = "";
                Course selected = curScheduleList.getSelectedValue();
                String title = selected.getName();
                String code = selected.getCode();
                String times = selected.getMeetingTimes().entrySet().stream()
                        .map(map-> map.getKey()+": "+map.getValue())
                        .collect(Collectors.joining(", "));
                toDisplay = ("Delete Course? \n" + title + ": " + code + "\n" + times);


                int popupChoice = JOptionPane.showConfirmDialog(null, toDisplay);
                // when the plus button is pressed, add the course currently selected in the search results
                if(popupChoice == JOptionPane.YES_OPTION) {
                    if (curScheduleList.getSelectedIndex() != -1) {
                        schedule.deleteCourse(selected);
                        calendar.removeAll();
                        calendar.add(new CalendarComponent());
                        calendar.repaint();
                        calendar.revalidate();
                        app.getLoggedInStudent().save();
                        redraw();
                    }
                }
            }
        });
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

    @Override
    public void insertUpdate(DocumentEvent ev){
        if(suggestWordsFlag) {
            if (ev.getLength() != 1) {
                return;
            }

            int pos = ev.getOffset();
            String content = null;
            try {
                content = searchBar.getText(0, pos + 1);
//            System.out.println("searchbar: " + content);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }

            // Find where the word starts
            int w;
            for (w = pos; w >= 0; w--) {
                if (!Character.isLetter(content.charAt(w))) {
                    break;
                }
            }
            if (pos - w < 1) {
                // Too few chars
                return;
            }

            String prefix = content.substring(w + 1).toLowerCase();
//        System.out.println("prefix: " + prefix);
            Search search = new Search(App.getInstance().getCourseDatabase());
            String suggest = search.suggestWord(prefix);
            if (suggest != null) {
                String completion = suggest.substring(pos - w);
                SwingUtilities.invokeLater(
                        new CompletionTask(completion, pos + 1));
            } else {
                mode = Mode.INSERT;
            }
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {

    }

    @Override
    public void changedUpdate(DocumentEvent e) {

    }

    private class CompletionTask implements Runnable {
        String completion;
        int position;

        CompletionTask(String completion, int position) {
            this.completion = completion;
            this.position = position;
        }

        public void run() {
            searchBar.insert(completion, position);
            searchBar.setCaretPosition(position + completion.length());
            searchBar.moveCaretPosition(position);
            mode = Mode.COMPLETION;
        }
    }

    private class CommitAction extends AbstractAction {
        public void actionPerformed(ActionEvent ev) {
            if (mode == Mode.COMPLETION) {
                int pos = searchBar.getSelectionEnd();
                searchBar.insert(" ", pos);
                searchBar.setCaretPosition(pos + 1);
                mode = Mode.INSERT;
            }
        }
    }
}
