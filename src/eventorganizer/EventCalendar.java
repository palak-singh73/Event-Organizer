package eventorganizer;

/**
 * Creates an EventCalendar, which holds a list of events and the # of events present
 * Performs all the adding, removing, and printing operations
 * @author Palak Singh, Daniel Guan
 */
public class EventCalendar {

    private Event[] events;
    private int numEvents;
    static final int NOT_FOUND = -1;

    /**
     * Constructor to make a EventCalendar
     * @param events list containing all the events
     * @param numEvents how many events are currently in the list (note: not equivalent to hoe many events are in the list)
     */
    EventCalendar(Event[] events, int numEvents){
        this.events = events;
        this.numEvents = numEvents;
    }

    /**
     * Getter method to access number of events, numEvents
     * @return int numEvents representing number of events,
     */
    public int getNumEvents(){
        return numEvents;
    }

    /**
     * Finding a specific event in the list
     * @param event we are trying to find in the list
     * @return int index at which the event is present, -1 if the event is not found
     */
    private int find(Event event){
        for (int i = 0; i < numEvents; i++){
            if((events[i].getDate().toString().equals(event.getDate().toString()))
                    && (events[i].getStartTime().getHour() == event.getStartTime().getHour())
                    && (events[i].getLocation().toString().equals(event.getLocation().toString()))){
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Adds 4 additions spots at the end of the list
     */
    private void grow(){
        int new_size = events.length+4;
        Event [] new_cal = new Event[new_size];
        for (int i = 0; i < events.length; i++){
            new_cal[i] = events[i];
        }
        events = new_cal;
    }

    /**
     * Adds the event to the next null space
     * If there is no space left, it grows 4 more spaces and then adds to the list
     * @param event that we want to add to the list
     * @return true once the event is successfully added to the list
     */
    public boolean add(Event event){
        if(!contains(event)){
            if(events.length == numEvents) {
                grow();
            }
            events[numEvents] = event;
            numEvents++;
            return true;
        }else{
            return false;
        }
    }

    /**
     * Removing a given event from the list
     * @param event that we are trying to remove from the list
     * @return true if the method removes the event, false if even doesn't exist in the list
     */
    public boolean remove (Event event){
        if(contains(event)){
            int index_removal = find(event);
            if (index_removal == (events.length-1)){
                events[index_removal] = null;
            }else if(index_removal != -1){
                for(int i = index_removal; i < numEvents - 1; i++){
                    events[i] = events[i + 1];
                }
                events[numEvents - 1] = null;
            }
            numEvents--;
            return true;
        }
        return false;
    }

    /**
     * Checks to see if a given event is in the list
     * @param event that we are looking for in the list
     * @return boolean true is the event is found, false if the event is not in the list
     */
    public boolean contains(Event event){
        for (int i = 0; i < numEvents; i++){
            if((events[i].getDate().toString().equals(event.getDate().toString()))
                    && (events[i].getStartTime().getHour() == event.getStartTime().getHour())
                    && (events[i].getLocation().toString().equals(event.getLocation().toString()))){
                return true;
            }
        }
        return false;
    }

    /**
     * Events are printed out just as they appear in the list
     */
    public void print(){
        for (int i = 0; i <numEvents; i++) {
            System.out.println(events[i].toString());
        }
        if(events.length == 0){
            System.out.println("Event calendar is empty!");
        }else{
            System.out.println("* end of event calendar *");
        }
    }
    /**
     * Swaps two events in the array, given the two indices you want to swap
     */
    private void swap(int a, int b){
        Event temp_Event;
        temp_Event = events[a];
        events[a] = events[b];
        events[b] = temp_Event;
    }

    /**
     * Sorts and prints the events organized by date, earliest to latest, relative from today's date
     */
    public void printByDate(){
        if(events.length > 0){
            System.out.println("* Event calendar by event date and start time *");
            for (int i = 0; i < numEvents; i++){
                for (int j = i; j<numEvents; j++) {
                    if(events[i].compareTo(events[j]) > 0){
                        swap(i, j);
                    }
                }
            }
            print();
        }else{
            System.out.println("Event calendar is empty!");
        }
    }

    /**
     * Sorts and prints the events organized by building and campus
     */
    public void printByCampus(){
        if(events.length > 0){
            System.out.println("* Event calendar by campus and building *");
            for (int i = 1; i < numEvents; i++){
                if(events[i-1].getLocation().toString().equals(events[i].getLocation().toString())){
                    continue;
                }else{
                    for (int j = i; j < numEvents; j++) {
                        if(events[i - 1].getLocation().toString().equals(events[j].getLocation().toString())){
                            swap(i, j);
                            break;
                        }
                    }
                }
            }
            for (int i = 1; i < numEvents; i++){
                if(events[i - 1].getLocation().toString().equals(events[i].getLocation().toString())){
                    continue;
                }else{
                    for (int j = i; j < numEvents; j++) {
                        if(events[i - 1].getLocation().getCampus().equals(events[j].getLocation().getCampus())){
                            swap(i, j);
                            break;
                        }
                    }
                }
            }
            print();
        }else{
            System.out.println("Event calendar is empty!");
        }
    }

    /**
     * Sorts and prints the events organized by department
     */
    public void printByDepartment(){
        if(events.length>0){
            System.out.println("* Event calendar by department *");
            int same_Department_Event_index;
            for (int i = 1; i < numEvents; i++){
                same_Department_Event_index = -1;
                if(events[i - 1].getContact().getDepartment().equals(events[i].getContact().getDepartment())){
                    continue;
                }else{
                    for (int j = i; j<numEvents; j++) {
                        if(events[i - 1].getContact().getDepartment().equals(events[j].getContact().getDepartment())){
                            same_Department_Event_index = j;
                            break;
                        }
                    }
                }
                if(same_Department_Event_index > -1){
                    swap(i, same_Department_Event_index);
                }
            }
            print();
        }else{
            System.out.println("Event calendar is empty!");
        }
    }
}
