package eventorganizer;

import java.util.Calendar;

/**
 * Writing our own Date class to initialize dates and check for validity
 * @author Daniel Guan, Palak Singh
 */
public class Date implements Comparable<Date>{
    private int year;
    private int month;
    private int day;

    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUARTERCENTENNIAL = 400;
    public static final int MONTH_30_DAYS = 30;
    public static final int MONTH_31_DAYS = 31;
    public static final int FEBRUARY_DAYS = 28;
    public static final int FEBRUARY_LEAP_DAYS = 29;
    static final int JAN = 1;
    static final int MAR = 3;
    static final int APR = 4;
    static final int MAY = 5;
    static final int JUN = 6;
    static final int JUL = 7;
    static final int AUG = 8;
    static final int SEP = 9;
    static final int OCT = 10;
    static final int NOV = 11;
    static final int DEC = 12;

    /**
     * Constructor to create a date with year, month, and day
     * @param dateString date in format "mm/dd/yyyy"
     */
    public Date(String dateString){
        String[] dateSplit = dateString.split("/", 3);
        this.year = Integer.parseInt(dateSplit[2]);
        this.month = Integer.parseInt(dateSplit[0]);
        this.day = Integer.parseInt(dateSplit[1]);
    }

    /**
     * Overrides the compare to method to find the order of the dates
     * @param date the object to be compared.
     * @return -1, 0, or 1 depending on if this.date comes before, at, or after date, respectively
     */
    @Override
    public int compareTo(Date date){
        if(this.year < date.year){
            return -1;
        }else if (this.year > date.year){
            return 1;
        }

        if (this.month < date.month){
            return -1;
        }else if(this.month > date.month){
            return 1;
        }

        if (this.day < date.day){
            return -1;
        }else if(this.day > date.day){
            return 1;
        }

        return 0;
    }

    /**
     * Checks to see if the date is within 6 months into the future
\     * @return boolean value indicating whether the date is within 6 months into the future
     */
    public boolean within6Months(){
        Calendar today = Calendar.getInstance();
        int today_year = today.get(Calendar.YEAR);
        int today_month = today.get(Calendar.MONTH) + 1;   //to make the range for months 1-12
        int today_day = today.get(Calendar.DATE);

        int month_max = today_month+6;
        int year_max = today_year;
        if(month_max > 12){
            month_max = month_max%12;
            year_max++;
        }
        if ((year < year_max) || (year == year_max && month <= month_max)){
            if (month == month_max) {
                return (day < today_day);
            }else{
                return true;
            }
        }
        return false;
    }

    /**
     * Checks that a date is in the future
     * @return boolean whether the date is after today's date
     */
    public boolean checkFutureDate(){
        Calendar today = Calendar.getInstance();
        int today_year = today.get(Calendar.YEAR);
        int today_month = today.get(Calendar.MONTH) + 1;   //to make the range for months 1-12
        int today_day = today.get(Calendar.DATE);
        if ((year < today_year) || (year == today_year && month < today_month)
                || (year == today_year && month == today_month && day < today_day)){
            return false;
        }
        return true;
    }

    /**
     * Checks if a date is a valid calendar date
     * @return true or false whether the date is valid
     */
    public boolean isValid(){
        if(year < 0){ // valid year
            return false;
        }
        if((month < JAN) || (month > DEC)){ // valid month
            return false;
        }
        if((month == JAN) || (month == MAR) || (month == MAY) || (month == JUL)
                || (month == AUG) || (month == OCT) || (month == DEC)){
            if((day < 1) || (day > MONTH_31_DAYS)){ // checks months for 31 days max
                return false;
            }
        }else if((month == APR) || (month == JUN) || (month == SEP) || (month == NOV)){
            if((day < 1) || (day > MONTH_30_DAYS)){ // checks months for 30 days max
                return false;
            }
        }else{
            if(leap_detect()){
                if((day < 1) || (day > FEBRUARY_LEAP_DAYS)){ // checks February has 29 days max on a leap year
                    return false;
                }
            }else{
                if((day < 1) || (day > FEBRUARY_DAYS)){ // checks February has 28 days max on a non-leap year
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Determines whether a given year is a leap year
     * @return true or false whether the year is a leap year
     */
    private boolean leap_detect (){
        if (year % QUADRENNIAL != 0){
            return false;
        }else if (year % CENTENNIAL != 0){
            return true;
        }else if (year % QUARTERCENTENNIAL == 0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * toString method for Date to print the date
     * @return String returns the date in mm/dd/yyyy format
     */
    public String toString(){
        return month + "/" + day + "/" + year;
    }

    /**
     * Testbed main to exercise the isValid method
     * @param args command line arguments
     */
    public static void main(String[] args){
        testDaysInFeb_NonLeap();
        testDaysInFeb_Leap();
        testMonth_OutOfBound();
        testMonth_OutOfBound2();
        testDays_OutOfBound();
        testDays_OutOfBound2();
        testDays_OutOfBound3();
        testDate_Valid();
    }

    /**
     * Check if a given test case Pass or Fail
     * @param date the date we are testing for
     * @param expectedOutput what the output should be
     * @param actualOutput what the actual output the method provides us with
     */
    private static void testResult(Date date, boolean expectedOutput, boolean actualOutput){
        System.out.println("Test input: " + date.toString());
        System.out.println("Expected output: " + expectedOutput);
        System.out.print("Actual output: " + actualOutput);
        if (expectedOutput != actualOutput){
            System.out.println(" (Fail) \n");
        }else{
            System.out.println(" (Pass) \n");
        }
    }

    /**
     * Test Case #1
     */
    private static void testDaysInFeb_NonLeap(){
        Date date = new Date ("02/29/2011");
        boolean expectedOutput = false;
        boolean actualOutput = date.isValid();
        System.out.println("**Test case #1: # of days in Feb. in a non-leap year is 28");
        testResult (date, expectedOutput, actualOutput);
    }

    /**
     * Test Case #2
     */
    private static void testDaysInFeb_Leap(){
        Date date = new Date ("02/29/2016");
        boolean expectedOutput = true;
        boolean actualOutput = date.isValid();
        System.out.println("**Test case #2: # of days in Feb. in a leap year is 29");
        testResult (date, expectedOutput, actualOutput);
    }

    /**
     * Test Case #3
     */
    private static void testMonth_OutOfBound(){
        Date date = new Date ("13/5/2023");
        boolean expectedOutput = false;
        boolean actualOutput = date.isValid();
        System.out.println("**Test case #3: the month input is greater than 12");
        testResult (date, expectedOutput, actualOutput);
    }

    /**
     * Test Case #4
     */
    private static void testMonth_OutOfBound2(){
        Date date = new Date ("0/5/2023");
        boolean expectedOutput = false;
        boolean actualOutput = date.isValid();
        System.out.println("**Test case #4: the month input is less than 1");
        testResult (date, expectedOutput, actualOutput);
    }

    /**
     * Test Case #5
     */
    private static void testDays_OutOfBound(){
        Date date = new Date ("10/32/2023");
        boolean expectedOutput = false;
        boolean actualOutput = date.isValid();
        System.out.println("**Test case #5: the day input is greater than 31 (for a 31-day month)");
        testResult (date, expectedOutput, actualOutput);
    }

    /**
     * Test Case #6
     */
    private static void testDays_OutOfBound2(){
        Date date = new Date ("11/31/2023");
        boolean expectedOutput = false;
        boolean actualOutput = date.isValid();
        System.out.println("**Test case #6: the day input is greater than 30 (for a 30-day month)");
        testResult (date, expectedOutput, actualOutput);
    }

    /**
     * Test Case #7
     */
    private static void testDays_OutOfBound3(){
        Date date = new Date ("10/0/2023");
        boolean expectedOutput = false;
        boolean actualOutput = date.isValid();
        System.out.println("**Test case #7: the day input is less than 1");
        testResult (date, expectedOutput, actualOutput);
    }

    /**
     * Test Case #8
     */
    private static void testDate_Valid(){
        Date date = new Date ("1/10/2024");
        boolean expectedOutput = true;
        boolean actualOutput = date.isValid();
        System.out.println("**Test case #8: test a valid date input");
        testResult (date, expectedOutput, actualOutput);
    }

}