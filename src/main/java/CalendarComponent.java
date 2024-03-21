import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;

public class CalendarComponent extends JPanel {
    public CalendarComponent(){
        setMinimumSize(new Dimension(600, 600));
        setMaximumSize(new Dimension(600, 600));
        setPreferredSize(new Dimension(600, 600));
        setBackground(Color.white);
        setBorder(BorderFactory.createLineBorder(Color.black));
        setFocusable(true);
        setLayout(new BorderLayout());



        String[][] data = new String[13][8];
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 8; j++) {
                data[i][j] = " ";
            }
        }
        String[] columnNames = {" ", "Sun", "Mon", "Tues", "Wed", "Thurs", "Fri", "Sat"};

        TableModel model = new DefaultTableModel(data, columnNames)
        {
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
        model.setValueAt("Sunday", 0, 1);
        model.setValueAt("Monday", 0, 2);
        model.setValueAt("Tuesday", 0, 3);
        model.setValueAt("Wednesday", 0, 4);
        model.setValueAt("Thursday", 0, 5);
        model.setValueAt("Friday", 0, 6);
        model.setValueAt("Saturday", 0, 7);
        model.setValueAt("8am", 1, 0);
        model.setValueAt("9am", 2, 0);
        model.setValueAt("10am", 3, 0);
        model.setValueAt("11am", 4, 0);
        model.setValueAt("12pm", 5, 0);
        model.setValueAt("1pm", 6, 0);
        model.setValueAt("2pm", 7, 0);
        model.setValueAt("3pm", 8, 0);
        model.setValueAt("4pm", 9, 0);
        model.setValueAt("5pm", 10, 0);
        model.setValueAt("6pm", 11, 0);

        JTable calendar = new JTable(model);
        calendar.setRowHeight(50);
        add(calendar);

    }
}
