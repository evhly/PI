import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class CustomTableCellRenderer extends DefaultTableCellRenderer {

    public Component getTableCellRendererComponent
            (JTable table, Object value, boolean isSelected,
             boolean hasFocus, int row, int column)
    {
        JComponent cell = (JComponent) super.getTableCellRendererComponent
                (table, value, isSelected, hasFocus, row, column);
//        cell.setBorder(new MatteBorder( 0, 0, 0, 1, Color.black));
        if(!value.toString().isEmpty()){
//            System.out.println("here I am: " + row + ", " + column + ", " + value + ".");
            cell.setBackground(new Color(194,111,109));

        } else {
            cell.setBackground(Color.white);
        }
        return cell;
    }
}
