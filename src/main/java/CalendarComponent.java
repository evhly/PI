import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Map;

public class CalendarComponent extends DynamicComponent {

    public void draw() {
        App app = App.getInstance();
        Schedule schedule = app.getCurrSchedule();

        setMinimumSize(new Dimension(550, 500));
        setMaximumSize(new Dimension(550, 500));
        setPreferredSize(new Dimension(550, 500));
        setBackground(Color.white);
        setBorder(BorderFactory.createLineBorder(Color.black));
        setFocusable(true);

        //add master panel boxlayout with a left side being list and a right panel (calendar component)
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        String[][] data = new String[11][7];
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 7; j++) {
                data[i][j] = " ";
            }
        }
        for(Course c : schedule.getCourses()){
            if(c.getMeetingTimes() != null){
                for (Map.Entry<DayOfWeek, ArrayList<LocalTime>> entry : c.getMeetingTimes().entrySet()){
                    int day = entry.getKey().getValue() % 7;
                    int time = (int)ChronoUnit.HOURS.between(LocalTime.parse("08:00:00"), entry.getValue().get(0));
                    System.out.println("time: " + time);
                    data[time][day] = c.getCode();
                }
            }
        }
        String[] columnNames = {"Sun", "Mon", "Tues", "Wed", "Thurs", "Fri", "Sat"};

        TableModel model = new DefaultTableModel(data, columnNames)
        {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JPanel tablePanel = new JPanel();
            JTable calendar = new JTable(model);
            calendar.setRowHeight(40);
            tablePanel.add(calendar.getTableHeader());
            tablePanel.add(calendar);

            String[] rowNames = {"8am", "9am", "10am", "11am", "12pm", "1pm", "2pm", "3pm", "4pm", "5pm", "6pm"};
            JList<String> rowHeader = new JList<String>(rowNames);
            rowHeader.setFixedCellHeight(40);
        add(rowHeader);
        add(tablePanel);

        InputMap inputMap = calendar.getInputMap(WHEN_FOCUSED);
        ActionMap actionMap = calendar.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "delete");
        actionMap.put("delete", new AbstractAction() {
            public void actionPerformed(ActionEvent evt) {
                int row = calendar.getSelectedRow();
                int col = calendar.getSelectedColumn();
                if (row >= 0 && col >= 0) {
                    row = calendar.convertRowIndexToModel(row);
                    col = calendar.convertColumnIndexToModel(col);
                    String courseCode = (String)calendar.getModel().getValueAt(row, col);
                    Course courseToDelete = app.getCourseDatabase().getCourseData(courseCode);
                    schedule.deleteCourse(courseToDelete);
                    app.getLoggedInStudent().save();
                    redraw();
                }
            }
        });

    }
}
