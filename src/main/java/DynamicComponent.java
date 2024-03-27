import javax.swing.*;

public abstract class DynamicComponent extends JPanel {

    protected abstract void draw();

    public final void redraw(){
        removeAll();
        draw();
        revalidate();
        repaint();
    }



}
