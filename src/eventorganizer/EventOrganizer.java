package eventorganizer;

import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Takes in user input, processes it, and provides the appropriate output
 * Contains that run method that would be used in RunProject1 to run the program
 * @author Daniel Guan, Palak Singh
 */

public class EventOrganizer {

    private static EventCalendar calendar;

    /**
     * Constructor to making a new calendar of events
     */
    public EventOrganizer(){
        calendar = new EventCalendar(new Event[]{}, 0);
    }

    /**
     * Run method to take in user input, process the input, and run the appropriate output
     */
    public static void run(){
        Scanner sc = new Scanner(System.in);
        boolean printIntro = false;
        while(true){
            String input_line = sc.nextLine().trim();
            if (!printIntro){
                System.out.println("\nEvent Organizer running.....\n");
                printIntro = true;
            }
            if (input_line.equals("Q")){
                System.out.println("Event Organizer terminated.");
                break;
            }else if(!input_line.isEmpty()){
                perform(input_line);
            }
        }
    }

    /**
     * Takes in a line input and processes it accordingly
     * @param input_line that we read from the user input
     */
    private static void perform(String input_line){
        StringTokenizer line = new StringTokenizer(input_line, " ");
        String command = line.nextToken();
        if (command.equals("A")){
            addEvent(line.nextToken(), line.nextToken(), line.nextToken(), line.nextToken(), line.nextToken(), line.nextToken());
        }else if (command.equals("R")){
            removeEvent(line.nextToken(), line.nextToken(), line.nextToken());
        }else if (command.equals("P")){
            if(calendar.getNumEvents() != 0){
                System.out.println("* Event Calendar *");
            }
            calendar.print();
        }else if (command.equals("PE")){
            calendar.printByDate();
        }else if (command.equals("PC")){
            calendar.printByCampus();
        }else if (command.equals("PD")){
            calendar.printByDepartment();
        }else{
            System.out.println(command + " is an invalid command!");
        }
    }

    /**
     * Checks to see if the event is valid and then adds it to the calendar
     * @param date of the event we are going to add
     * @param timeslot of the event we are going to add
     * @param location of the event we are going to add
     * @param department of the event we are going to add
     * @param email of the event we are going to add
     * @param duration of the event we are going to add
     */
    private static void addEvent(String date, String timeslot, String location, String department, String email, String  duration){
        if(validEventInput(date, timeslot, location, department, email,  duration)){
            Date dateToAdd = new Date(date);
            Timeslot timeslotToAdd = null;
            switch(timeslot.toUpperCase()){
                case "MORNING": timeslotToAdd = Timeslot.MORNING; break;
                case "AFTERNOON": timeslotToAdd = Timeslot.AFTERNOON; break;
                case "EVENING": timeslotToAdd = Timeslot.EVENING; break;
            }
            Location locationToAdd = null;
            for (Location l : Location.values()) {
                if (l.name().equalsIgnoreCase(location)) {
                    locationToAdd = l;
                }
            }
            Department departmentToAdd = null;
            for (Department d : Department.values()) {
                if (d.name().equalsIgnoreCase(department)) {
                    departmentToAdd = d;
                }
            }
            Contact contactToAdd = new Contact(departmentToAdd, email);
            if(calendar.add(new Event(dateToAdd, timeslotToAdd, locationToAdd, contactToAdd, Integer.parseInt(duration)))){
                System.out.println("Event is added to the Calendar.");
            }else{
                System.out.println("The event is already on the calendar.");
            }
        }
    }

    /**
     * Checks if the event inputted is valid and exists in the calendar
     * Then removes the event
     * @param date of the event we are going to remove
     * @param timeslot of the event we are going to remove
     * @param location of the event we are going to remove
     */
    private static void removeEvent(String date, String timeslot, String location){
        Date dateToAdd = new Date(date);
        if(!dateToAdd.isValid()){
            System.out.println(dateToAdd.toString() + ": Invalid calendar date!");
        }else if(!dateToAdd.checkFutureDate()) {
            System.out.println(dateToAdd.toString() + ": Event date must be a future date!");
        }else if(!dateToAdd.within6Months()){
            System.out.println(dateToAdd.toString() + ": Event date must be within 6 months!");
        }else{
            Timeslot timeslotToAdd = null;
            switch(timeslot.toUpperCase()){
                case "MORNING": timeslotToAdd = Timeslot.MORNING; break;
                case "AFTERNOON": timeslotToAdd = Timeslot.AFTERNOON; break;
                case "EVENING": timeslotToAdd = Timeslot.EVENING; break;
            }
            Location locationToAdd = null;
            for (Location l : Location.values()) {
                if (l.name().equalsIgnoreCase(location)) {
                    locationToAdd = l;
                }
            }
            if(calendar.remove(new Event(dateToAdd, timeslotToAdd, locationToAdd, new Contact(Department.CS, "default@rutgers.edu"), 30))){
                System.out.println("Event has been removed from the calendar!");
            }else{
                System.out.println("Cannot remove; event is not in the calendar!");
            }
        }
    }

    /**
     * Check if the Event input is valid
     * @param date that we are going to check for validity
     * @param timeslot that we are going to check for validity
     * @param location that we are going to check for validity
     * @param department that we are going to check for validity
     * @param email that we are going to check for validity
     * @param duration that we are going to check for validity
     * @return boolean depending on if the event input is valid
     */
    private static boolean validEventInput(String date, String timeslot, String location, String department, String email, String  duration){
        Date dateToCheck = new Date(date);
        if(!dateToCheck.isValid()){
            System.out.println(dateToCheck.toString() + ": Invalid calendar date!");
            return false;
        }else if(!dateToCheck.checkFutureDate()){
            System.out.println(dateToCheck + ": Event date must be a future date!");
            return false;
        }else if(!dateToCheck.within6Months()){
            System.out.println(dateToCheck + ": Event date must be within 6 months!");
            return false;
        }else if(!timeslotIsValid(timeslot)){
            System.out.println("Invalid time slot!");
            return false;
        }else if(!locationIsValid(location)){
            System.out.println("Invalid location!");
            return false;
        }else if(!contactIsValid(department, email)){
            System.out.println("Invalid contact information!");
            return false;
        }else if(!durationIsValid(duration)){
            System.out.println("Event duration must be at least 30 minutes and at most 120 minutes");
            return false;
        }
        return true;
    }

    /**
     * Checks to see if the timeslot is one of the 3 listed
     * @param timeslot string from the input that we are checking
     * @return boolean value indicating whether the timeslot is valid or not
     */
    private static boolean timeslotIsValid(String timeslot){
        if (timeslot.equalsIgnoreCase("MORNING") || timeslot.equalsIgnoreCase("AFTERNOON") || timeslot.equalsIgnoreCase("EVENING")){
            return true;
        }
        return false;
    }

    /**
     * Checks to see if the location is one of the  6 listed
     * @param location string from the input that we are checking
     * @return boolean value indicating whether the location is valid or not
     */
    private static boolean locationIsValid(String location){
        return location.equalsIgnoreCase("HLL114") || location.equalsIgnoreCase("ARC103")
                || location.equalsIgnoreCase("BE_AUD") || location.equalsIgnoreCase("TIL232")
                || location.equalsIgnoreCase("AB2225") || location.equalsIgnoreCase("MU302");
    }

    /**
     * Checks to see if the department exists and the email is valid
     * @param department string from the input we are trying to match up against one of the five
     * @param email from the input we are checking to see if it has the valid domain tag
     * @return boolean value indicating whether the contact is valid or not
     */
    private static boolean contactIsValid(String department, String email){
        boolean validDepartment = false;
        Department departmentName = null;
        for (Department d : Department.values()){
            if(department.equalsIgnoreCase(d.name())){
                validDepartment = true;
                departmentName = d;
            }
        }
        if(validDepartment){
            Contact temp = new Contact(departmentName, email);
            return temp.isValid();
        }
        return false;
    }

    /**
     * Checks to make sure that the duration is between 30 and 120 minutes
     * @param duration string isolated from the input
     * @return boolean value indicating whether it is valid or not
     */
    private static boolean durationIsValid(String duration){
        int num = Integer.parseInt(duration);
        if(num >= 30 && num <= 120){
            return true;
        }
        return false;
    }
}