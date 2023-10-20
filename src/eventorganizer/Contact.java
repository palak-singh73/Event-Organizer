package eventorganizer;

/**
 * Creates a contact for each department
 * including the name of the department and its email
 * @author Palak Singh, Daniel Guan
 */
public class Contact {

    private Department department;
    private String email;

    /**
     * Parameterized constructor to initialize a new contact
     * @param department Department name for the contact
     * @param email String department's email
     */
    public Contact(Department department, String email){
        this.department = department;
        this.email = email;
    }

    /**
     * Getter method to access department
     * @return Department
     */
    public Department getDepartment() {
        return department;
    }

    /**
     * Check to see if the contact is valid
     * Makes sure the department is from the list and email domain is @rutgers.edu
     * @return boolean value depending on if the contact is valid
     */
    public boolean isValid(){
        int LENGTH_OF_DOMAIN_NAME = 12;
        String VALID_DOMAIN_NAME = "@rutgers.edu";
        if (!email.substring(email.length() - LENGTH_OF_DOMAIN_NAME).equals(VALID_DOMAIN_NAME)){
            return false;
        }
        for (Department d : Department.values()){
            if(department.equals(d)){
                return true;
            }
        }
        return false;
    }

    /**
     * toString method to help print the department and email
     * @return String in the format DEPARTMENT, department@rutgers.edu
     */
    public String toString(){
        return department.toString() + ", " + email;
    }

}
