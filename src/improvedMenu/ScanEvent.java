/**
 * @authors @ryokasai19 @drewgoldman117
 */

package improvedMenu;

public class ScanEvent {
    protected int day;
    protected int hour;
    protected int minute;
    protected int userId;
    protected int enteringTime;
    protected int duration;
    protected int leavingTime;


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
    //outputs string representation of the scan event
    public String toString() {
        if (minute >= 10){
            return "[" + userId + " at " + hour + ":" + minute + "]";
        }
        //maintains HH:MM format
        return "[" + userId + " at " + hour + ":" + "0" + minute + "]";
    }
}
