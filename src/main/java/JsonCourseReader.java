import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//import com.google.gson.Gson;
//import com.google.gson.JsonObject;
//import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class JsonCourseReader extends CourseReader {
    //public Map<String, CourseDatabase> courseDatabaseMap;

    public JsonCourseReader(){
        super();
    }

    public void parseJson () throws IOException, JSONException {
        Scanner jScnr = new Scanner(new File("data_wolfe.json"));
        jScnr.useDelimiter("\\[");
//        System.out.println(jScnr.next()); //reads up to [
        jScnr.next();
        jScnr.useDelimiter("");
//        System.out.println(jScnr.next()); //grabs [
        jScnr.next();

        Stack<String> inStack = new Stack<>();
        String firstChar = jScnr.next(); //{
        String course = "";

        while (true) {
            inStack.push(firstChar); //push {
//            System.out.println(firstChar);
            course = firstChar;

            while (!inStack.empty()) {
                firstChar = jScnr.next();
                course = course + firstChar;

                if (firstChar.equals("{")) {
                    inStack.push(firstChar);
                } else if (firstChar.equals("}")) {
                    inStack.pop();
                }
            }
            //create course
            //System.out.println(course);
            JSONObject j = new JSONObject(course);
            //System.out.println(j.get("name")); //course title
            Course c = createCourse(j);

            // add course to the map
            if(courseDatabaseMap.keySet().contains(c.getTerm())){
                courseDatabaseMap.get(c.getTerm()).addCourse(c);
            } else {
                CourseDatabase CD = new CourseDatabase();
                CD.addCourse(c);
                courseDatabaseMap.put(c.getTerm(), CD);
            }

            String nextC = jScnr.next();
            if (!nextC.equals(",")){
                break;
            }
            firstChar = jScnr.next();
        }
    }
    private String metricToUSATime (String timeString){
        String hour = timeString.charAt(0) + "" + timeString.charAt(1);
        int timeHour = Integer.parseInt(hour);
//        System.out.println(time);
        if (timeHour > 12){
            timeHour = timeHour - 12;
            timeString = timeString + " PM";
            timeString = timeString.replaceFirst(hour, Integer.toString(timeHour));
//            System.out.println(timeString);
        } else if (timeHour == 12) {
            timeString = timeString + " PM";
//            System.out.println(timeString);
        }
        else {
            timeString = timeString + " AM";
            if (timeHour < 10){
                timeString = timeString.replaceFirst(hour, Integer.toString(timeHour));
//                System.out.println(timeString);
            }
        }
        return timeString;
    }

    protected Course createCourse(JSONObject j) throws JSONException {
        String yr_cde_str = (j.get("semester")).toString().substring(0,4);
        String sem = (j.get("semester")).toString().substring(5,6);
        String trm_cde = "";
        if (sem.equals("F")){
            trm_cde = "10";
        } else {
            trm_cde = "30";
        }
        String crs_comp1 = j.get("subject").toString();
        String crs_comp2 = j.get("number").toString();
        String crs_comp3 = j.get("section").toString();
        String crs_title = j.get("name").toString();
        String credit_hrs = j.get("credits").toString();
        String crs_capacity	= j.get("total_seats").toString();
        String crs_enrollment = j.get("open_seats").toString();

        //faculty name
        JSONArray nameArray = j.getJSONArray("faculty");
        String name = nameArray.get(0).toString();
        String last_name = "";
        String first_name = "";
        Scanner scn = new Scanner(name);
        scn.useDelimiter(",");
        if (scn.hasNext()){
            last_name = scn.next();
            scn.useDelimiter(" ");
            //skips over space
            scn.next();
            //grabs first name
            first_name = scn.next();
        }

        //times
        JSONArray timeArray = j.getJSONArray("times");
        String begin_tim = "";
        String end_tim = "";
        String[] days = new String[5];
        for(int i = 0; i < 5; i++){
            days[i] = "";
        }
        if (timeArray.length()==0) {
            // no day/time
        } else {
            JSONObject timesss = new JSONObject(timeArray.get(0).toString());
            begin_tim = timesss.get("start_time").toString(); //begin time
            begin_tim = metricToUSATime(begin_tim);
            end_tim = timesss.get("end_time").toString();  //end time
            end_tim = metricToUSATime(end_tim);

            for (int i = 0; i < timeArray.length(); i++) {
                JSONObject jj = new JSONObject(timeArray.get(i).toString());
                days[i] = jj.get("day").toString();
//              System.out.println(days[i]);
            }
        }
        scn.close();


        // adjust and format certain values from csv line
        int yr_cde = Integer.parseInt(yr_cde_str);
        if(Objects.equals(trm_cde, "10")){
            trm_cde = "F";
            trm_cde += String.valueOf(yr_cde - 2000);
        } else {
            trm_cde = "S";
            trm_cde += String.valueOf(yr_cde - 2000 + 1);
        }
        int credits = 0;
        if (!credit_hrs.equals("")) {
            credits = Integer.parseInt(credit_hrs); //reading str as int
        }

        // parse meeting times and create a map of days of the week to meeting times
        HashMap<DayOfWeek, ArrayList<LocalTime>> meetings = new HashMap<>();
        for (String day : days) {
            if(!day.isEmpty()) {
                ArrayList<LocalTime> beginAndEnd = new ArrayList<>();
                DateTimeFormatter ampmFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
                if (begin_tim.charAt(1) == ':') { // if the time is not padded with a 0
                    begin_tim = "0" + begin_tim;
                }
                if (end_tim.charAt(1) == ':') { // if the time is not padded with a 0
                    end_tim = "0" + end_tim;
                }
                beginAndEnd.add(LocalTime.parse(begin_tim, ampmFormatter)); // reads the String as a time (HH:MM:SS AM/PM)
                beginAndEnd.add(LocalTime.parse(end_tim, ampmFormatter));
                switch (day) {
                    case "M" -> meetings.put(DayOfWeek.MONDAY, beginAndEnd);
                    case "T" -> meetings.put(DayOfWeek.TUESDAY, beginAndEnd);
                    case "W" -> meetings.put(DayOfWeek.WEDNESDAY, beginAndEnd);
                    case "R" -> meetings.put(DayOfWeek.THURSDAY, beginAndEnd);
                    case "F" -> meetings.put(DayOfWeek.FRIDAY, beginAndEnd);
                    default -> meetings.put(DayOfWeek.SATURDAY, beginAndEnd); //TODO: change to DayOfWeek.SUNDAY;
                    // add Saturday option
                }
            }
        }

        // create a new Course from these parameters
        Course c = new Course(crs_comp1 + " " + crs_comp2 + " " + crs_comp3,
                crs_title,
                crs_comp1,
                credits,
                null,
                null,
                new Professor(first_name, last_name),
                null,
                meetings,
                trm_cde,
                null
        );
        return c;
    }

}
