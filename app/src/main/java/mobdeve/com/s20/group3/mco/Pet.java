package mobdeve.com.s20.group3.mco;

public class Pet {
    private int id;
    private String name;
    private String type;
    private int imageResId; // Resource ID for the image in drawable
    private String nextFeedingSchedule;
    private String areaWeather;
    private double areaTemperature;
    private String petLocation;

    // Updated constructor (no default -1 for new pets)
    public Pet(String name, String type, int imageResId, String nextFeedingSchedule, String areaWeather, double areaTemperature, String petLocation) {
        this.name = name;
        this.type = type;
        this.imageResId = imageResId;
        this.nextFeedingSchedule = nextFeedingSchedule;
        this.areaWeather = areaWeather;
        this.areaTemperature = areaTemperature;
        this.petLocation = petLocation;
    }

    // Constructor with ID for updating an existing pet
    public Pet(int id, String name, String type, int imageResId, String nextFeedingSchedule, String areaWeather, double areaTemperature, String petLocation) {
        this.id = id; // Set the ID for existing pets
        this.name = name;
        this.type = type;
        this.imageResId = imageResId;
        this.nextFeedingSchedule = nextFeedingSchedule;
        this.areaWeather = areaWeather;
        this.areaTemperature = areaTemperature;
        this.petLocation = petLocation;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Other getters and setters

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getNextFeedingSchedule() {
        return nextFeedingSchedule;
    }

    public String getAreaWeather() {
        return areaWeather;
    }

    public double getAreaTemperature() {
        return areaTemperature;
    }

    public String getPetLocation() {
        return petLocation;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setNextFeedingSchedule(String nextFeedingSchedule) {
        this.nextFeedingSchedule = nextFeedingSchedule;
    }

    public void setAreaWeather(String areaWeather) {
        this.areaWeather = areaWeather;
    }

    public void setAreaTemperature(double areaTemperature) {
        this.areaTemperature = areaTemperature;
    }

    public void setPetLocation(String petLocation) {
        this.petLocation = petLocation;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }
}
