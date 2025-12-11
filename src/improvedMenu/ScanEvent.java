/**
 * @authors @ryokasai19 @drewgoldman117
 */

package improvedMenu;

public class ScanEvent {
    public int day;
    public int hour;
    public int minute;
    public int userId;
    public int enteringTime;
    public int duration;
    public int leavingTime;


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
