//TODO: Add authors!

package improvedMenu;

public class ScanEvent {
    public int day;
    public int hour;
    public int minute;
    public int userId;
    // added by Ryo for DiningHall.java. Lmk if you have questions
    public int enteringTime;
    public int duration;
    public int leavingTime;

    //TODO add back if needed
    private int openOffset = 450; //offsets the entering time in minutes to start at 0 when time is 07:30

    public ScanEvent(int d, int h, int m, int u) {
        this.day = d;
        this.hour = h;
        this.minute = m;
        this.userId = u;
        this.enteringTime = (h * 60 + m);
    }

    //returns day of event
    public int getDay(){
        return day;
    }

    //returns time in minutes of event
    public int getTime(){
        return hour * 60 + minute;
    }


    //returns the userId of the event
    public int getUserId(){
        return userId;
    }



    @Override
    public String toString() {
        // This will print: [487 at 7:00]
        return "[" + userId + " at " + hour + ":" + minute + "]";
    }
}
