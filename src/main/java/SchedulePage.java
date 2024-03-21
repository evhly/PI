import java.awt.*;

public class SchedulePage extends Page{

    private Schedule schedule;
    public void draw(Graphics g){}
    public SchedulePage(App app){
        CalendarComponent calendar = new CalendarComponent();
        add(calendar);
    }
}
