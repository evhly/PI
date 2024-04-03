import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class CustomTableCellRenderer extends DefaultTableCellRenderer {

    public Component getTableCellRendererComponent
            (JTable table, Object value, boolean isSelected,
             boolean hasFocus, int row, int column)
    {
        Component cell = super.getTableCellRendererComponent
                (table, value, isSelected, hasFocus, row, column);
        if(!value.toString().isEmpty()){
            System.out.println("here I am: " + row + ", " + column + ", " + value + ".");
            cell.setBackground(Color.orange);
        } else {
            cell.setBackground(Color.white);
        }

//        if( value instanceof Integer )
//        {
//            Integer amount = (Integer) value;
//            if( amount.intValue() < 0 )
//            {
//                cell.setBackground( Color.red );
//                // You can also customize the Font and Foreground this way
//                // cell.setForeground();
//                // cell.setFont();
//            }
//            else
//            {
//                cell.setBackground( Color.white );
//            }
//        }
        return cell;
    }
}
