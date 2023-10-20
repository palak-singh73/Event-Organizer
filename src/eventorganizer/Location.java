package eventorganizer;

/**
 * Defines and stores the locations as constants
 * Building names and Campus are stored as properties
 * @author Palak Singh, Daniel Guan
 */
public enum Location {
    HLL114 ("Hill Center", "Busch"),
    ARC103 ("Allison Road Classroom", "Busch"),
    BE_AUD ("Beck Hall", "Livingston"),
    TIL232 ("Tillett Hall", "Livingston"),
    AB2225 ("Academic Building", "College Avenue"),
    MU302 ("Murray Hall", "College Ave");

    private final String buildingName;
    private final String campus;

    /**
     * Constructor to initialize a new location
     * @param buildingName name of the building the room is in
     * @param campus campus the building is on
     */
    Location(String buildingName, String campus){
        this.buildingName = buildingName;
        this.campus = campus;
    }
    /**
     * Getter method to access campus
     * @return String campus name
     */
    public String getCampus() {
        return campus;
    }

    /**
     * toString override to print the full location
     * @return String in the format of @room (building_name, campus)
     */
    @Override
    public String toString(){
        return "@" + name() + " (" + buildingName + ", " + campus + ")";
    }

}