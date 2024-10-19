package mobdeve.com.s20.group3.mco;

public class Pet {
    private String name;
    private String type;
    private int imageResId; // Resource ID for the image in drawable

    public Pet(String name, String type, int imageResId) {
        this.name = name;
        this.type = type;
        this.imageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getImageResId() {
        return imageResId;
    }
}