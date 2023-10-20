package eventorganizer;

/**
 * Defines and stores the times meetings can be held
 * @author Palak Singh, Daniel Guan
 */
public enum Timeslot {
    MORNING (10, 30),
    AFTERNOON(2, 0),
    EVENING(6, 30);

    private final int hour;
    private final int minute;

    /**
     * Constructor to initialise a new timeslot
     * @param hour hour of day
     * @param minute minute of the hour
     */
    Timeslot(int hour, int minute){
        this.hour = hour;
        this.minute = minute;
    }

    /**
     * Getter method to get the hour of the timeslot
     * @return integer hours
     */
    public int getHour() {
        return hour;
    }

    /**
     * Getter method to get the minute of the timeslot
     * @return integer minute
     */
    public int getMinute() {
        return minute;
    }

    /**
     * toString override to print the hours and minutes instead of time of day
     * @return String in ##:## format
     */
    @Override
    public String toString(){
        if(hour == 2){
            return hour + ":00";
        }
        return hour + ":" + minute;
    }

}
