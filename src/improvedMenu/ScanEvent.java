package improvedMenu;

/**
 * Here we have a single scan event for a person entering a dining hall.
 * Stores the day, time, and the user ID of the person and also computes entering time in terms of minutes.
 *
 * @authors: @ryokasai19, @drewgoldman117, @Uras1717
 */

public class ScanEvent {
    //the day of entering the dining hall (the event)
    protected int day;

    //the hour of event
    protected int hour;

    //the minute of event
    protected int minute;

    //the userID of the person entering
    protected int userId;

    //the time the user entered in minutes, to be calculated
    protected int enteringTime;

    //the duration the user stays inside
    protected int duration;

    //the leaving time of the user, again in minutes
    protected int leavingTime;



    /**
     * Creates the scan event with day, hour, minute, and userID.
     *
     * @param d The day of the event
     * @param h The hour timestamp of the event
     * @param m The minute timestamp of event
     * @param u User ID
     */
    public ScanEvent(int d, int h, int m, int u) {
        this.day = d;
        this.hour = h;
        this.minute = m;
        this.userId = u;
        this.enteringTime = (h * 60 + m);
    }

    /**
     * @return the day of the event
     */
    public int getDay(){
        return day;
    }

    /**
     * @return time in minutes of the event
     */
    public int getTime(){
        return hour * 60 + minute;
    }


    /**
     * @return UserID involved in the event
     */
    public int getUserId(){
        return userId;
    }

    @Override
    /**
     * @return the string representation of the event; userID, then hours:minutes
     */
    public String toString() {
        if (minute >= 10){
            return "[" + userId + " at " + hour + ":" + minute + "]";
        }
        //maintains HH:MM format
        return "[" + userId + " at " + hour + ":" + "0" + minute + "]";
    }
}
