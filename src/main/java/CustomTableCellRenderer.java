import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class CustomTableCellRenderer extends DefaultTableCellRenderer {

    public Component getTableCellRendererComponent
            (JTable table, Object value, boolean isSelected,
             boolean hasFocus, int row, int column)
    {
        JComponent cell = (JComponent) super.getTableCellRendererComponent
                (table, value, isSelected, hasFocus, row, column);
        cell.setFont(new Font("Arial", Font.PLAIN, 10));

        if(!value.toString().isEmpty()){
//            System.out.println("here I am: " + row + ", " + column + ", " + value + ".");
            cell.setBackground(new Color(194,111,109));

        } else {
            cell.setBackground(Color.white);
        }

        if(row % 4 == 0){
            cell.setBorder(new MatteBorder( 1, 0, 0, 0, Color.black));
        }
        return cell;
    }
}
