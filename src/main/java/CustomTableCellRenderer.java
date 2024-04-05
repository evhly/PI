import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class CustomTableCellRenderer extends DefaultTableCellRenderer {

    // custom cell renderer used for the cells in the GUI calendar
    public Component getTableCellRendererComponent
            (JTable table, Object value, boolean isSelected,
             boolean hasFocus, int row, int column)
    {
        JComponent cell = (JComponent) super.getTableCellRendererComponent
                (table, value, isSelected, hasFocus, row, column);
        cell.setFont(new Font("Arial", Font.PLAIN, 8));
        cell.setForeground(Color.white);

        if(!value.toString().isEmpty()){
            cell.setBackground(new Color(166, 38, 34));
        } else {
            cell.setBackground(Color.white);
        }

        if(row % 4 == 0){
            cell.setBorder(new MatteBorder( 1, 0, 0, 0, Color.black));
        }
        return cell;
    }
}
