public class Time {

    private int hour;
    private int minute;

    private String ampm;

    Time(int hour, int minute, String ampm){
        this.hour = hour;
        this.minute = minute;
        this.ampm = ampm;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public String getAmpm() {
        return ampm;
    }
}
