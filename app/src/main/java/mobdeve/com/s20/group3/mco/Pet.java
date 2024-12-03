package mobdeve.com.s20.group3.mco;

public class Pet {
    private int id;
    private String name;
    private String type;
    private double temp;
    private int imageResId; // Resource ID for the image in drawable
    private String nextFeedingSchedule;
    private String areaWeather;
    private double areaTemperature;
    private String petLocation;

    public Pet(String name, String type, int imageResId, String nextFeedingSchedule, String areaWeather, double areaTemperature, String petLocation) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.imageResId = imageResId;
        this.nextFeedingSchedule = nextFeedingSchedule;
        this.areaWeather = areaWeather;
        this.areaTemperature = areaTemperature;
        this.petLocation = petLocation;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getId(){
        return id;
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
}
