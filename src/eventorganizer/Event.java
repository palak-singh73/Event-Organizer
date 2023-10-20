package eventorganizer;

/**
 * Creates an event with a date, timeslot, location, contact, and duration
 * @author Palak Singh, Daniel Guan
 */
public class Event implements Comparable<Event>{
    private Date date;
    private Timeslot startTime;
    private Location location;
    private Contact contact;
    private int duration;
    private static final int MINS_IN_HOUR=60;

    /**
     * Constructor to set the attributes of event
     * @param date date of event
     * @param startTime timeslot start time of event
     * @param location building and campus location of event
     * @param contact department and contact email for event
     * @param duration time duration of event
     */
    public Event (Date date, Timeslot startTime, Location location, Contact contact, int duration){
        this.date = date;
        this.startTime = startTime;
        this.location = location;
        this.contact = contact;
        this.duration = duration;
    }

    /**
     * Getter method to access event date
     * @return Date event date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Getter method to access event timeslot
     * @return Timeslot startTime
     */
    public Timeslot getStartTime() {
        return startTime;
    }

    /**
     * Getter method to access event location
     * @return location event location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Getter method to access event contact information
     * @return Contact event contact
     */
    public Contact getContact() {
        return contact;
    }

    /**
     * Determines whether two events are the same date, location, and timeslot
     * @param event the event to be compared to
     * @return true if the events are on the same date, location, and timeslot; false if different
     */
    @Override
    public boolean equals(Object event){
        Event actualEvent = (Event) event;
        return (this.date.compareTo(actualEvent.date) == 0) && this.location.name().equals(actualEvent.location.name()) && this.startTime.name().equals(actualEvent.startTime.name());
    }

    /**
     * Compares two events based on their date and timeslot
     * @param event the event to be compared to
     * @return -1 if the given event is before the parameter event
     *          1 if the given event is after the parameter event
     *          0 if both events are planned to occur at the same time
     */
    @Override
    public int compareTo(Event event){
        if (this.date.compareTo(event.date) != 0){
            return this.date.compareTo(event.date);
        }else{
            if(this.startTime.name().equals("MORNING") && !event.startTime.name().equals("MORNING")){
                return -1;
            }else if(this.startTime.name().equals("AFTERNOON")){
                if (event.startTime.name().equals("MORNING")){
                    return 1;
                }else if(event.startTime.name().equals("EVENING")){
                    return -1;
                }
            }else if(this.startTime.name().equals("EVENING") && !event.startTime.name().equals("EVENING")){
                return 1;
            }
        }
        return 0;
    }

    /**
     * Overrides the toString method to print the full event description
     * @return String event description in the following format
     *   [Event Date: date] [Start: startTime] [End: endTime] @location (building, campus) [Contact: email]
     */
    @Override
    public String toString(){
        String start_am_pm_tag;
        if (startTime.name().equals("MORNING")){
            start_am_pm_tag = "am";
        }else{
            start_am_pm_tag = "pm";
        }
        int hoursAdded = duration / MINS_IN_HOUR;
        int minsAdded = duration % MINS_IN_HOUR;
        int endTimeHour = startTime.getHour() + hoursAdded;
        int endTimeMins = startTime.getMinute() + minsAdded;
        if(endTimeMins >= MINS_IN_HOUR){
            endTimeHour++;
            endTimeMins -= MINS_IN_HOUR;
        }
        String end_am_pm_tag;
        if(endTimeHour == 10 || endTimeHour == 11){
            end_am_pm_tag = "am";
        }else{
            end_am_pm_tag = "pm";
        }
        if(endTimeMins==0){
            end_am_pm_tag = "0" + end_am_pm_tag;
        }
        return "[Event Date: " + date + "] [Start: " + startTime + start_am_pm_tag + "] [End: " + endTimeHour +
            ":" + endTimeMins + end_am_pm_tag + "] " + location + " [Contact: " + contact.toString() + "]";
    }

    /**
     * Testbed main to exercise the equals method
     * @param args command line arguments
     */
    public static void main(String[] args){
        testEquals_DifferentEvents();
        testEquals_SameEvents();
        testEquals_SimilarEvents();
    }

    /**
     * Check if a given test case Pass or Fail
     * @param event1 the event we are testing for
     * @param event2 the event we are comparing with event1
     * @param expectedOutput what the output should be
     * @param actualOutput what the actual output the method provides us with
     */
    private static void testResult(Event event1, Event event2,  boolean expectedOutput, boolean actualOutput){
        System.out.println("Test input 1: " + event1.toString() + " \nTest input 2: " + event2.toString());
        System.out.println("Expected output: " + expectedOutput);
        System.out.print("Actual output: " + actualOutput);
        if (expectedOutput != actualOutput){
            System.out.println(" (Fail) \n");
        }else{
            System.out.println(" (Pass) \n");
        }
    }

    /**
     * Test case #1
     */
    private static void testEquals_DifferentEvents(){
        Event e1 = new Event(new Date("8/5/2024"), Timeslot.EVENING, Location.ARC103, new Contact(Department.EE, "ee@rutgyers.edu"), 175);
        Event e2 = new Event (new Date("10/05/2023"), Timeslot.MORNING, Location.AB2225, new Contact(Department.CS, "CS@rutgers.edu"), 90);
        boolean expectedOutput = false;
        boolean actualOutput = e1.equals(e2);
        System.out.println("**Test case #1: two completely different events");
        testResult(e1, e2, expectedOutput, actualOutput);
    }

    /**
     * Test case #2
     */
    private static void testEquals_SameEvents(){
        Event e1 = new Event(new Date("8/5/2024"), Timeslot.AFTERNOON, Location.HLL114, new Contact(Department.ITI, "iti@rutgyers.edu"), 85);
        Event e2 = new Event(new Date("8/5/2024"), Timeslot.AFTERNOON, Location.HLL114, new Contact(Department.ITI, "iti@rutgyers.edu"), 85);
        boolean expectedOutput = true;
        boolean actualOutput = e1.equals(e2);
        System.out.println("**Test case #2: two same events");
        testResult(e1, e2, expectedOutput, actualOutput);
    }

    /**
     * Test case #3
     */
    private static void testEquals_SimilarEvents(){
        Event e1 = new Event(new Date("8/05/2024"), Timeslot.AFTERNOON, Location.HLL114, new Contact(Department.ITI, "iti@rutgyers.edu"), 85);
        Event e2 = new Event(new Date("8/5/2024"), Timeslot.AFTERNOON, Location.HLL114, new Contact(Department.ITI, "iti@rutgyers.edu"), 80);
        boolean expectedOutput = true;
        boolean actualOutput = e1.equals(e2);
        System.out.println("**Test case #3: two same events, different durations");
        testResult(e1, e2, expectedOutput, actualOutput);
    }

}
