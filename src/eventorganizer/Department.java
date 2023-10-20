package eventorganizer;

/**
 * Defines and stores the department names as constants
 * @author Palak Singh, Daniel Guan
 */
public enum Department {
    CS ("Computer Science"),
    EE ("Electrical Engineering"),
    ITI ("Information Technology and Informatics"),
    MATH ("Mathematics"),
    BAIT ("Business Analytics and Information Technology");

    private final String full_name;

    /**
     * Constructor to initialize a new department
     * @param full_name full name of the department
     */
    Department(String full_name){
        this.full_name = full_name;
    }

    /**
     * toString override to print the full name
     * @return String full name of department
     */
    @Override
    public String toString(){
        return full_name;
    }
}
