package mobdeve.com.s20.group3.mco;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PetDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "pets.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_PETS = "pets";

    // Column names
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_IMAGE_RES_ID = "imageResId";
    public static final String COLUMN_NEXT_FEEDING_SCHEDULE = "nextFeedingSchedule";
    public static final String COLUMN_AREA_WEATHER = "areaWeather";
    public static final String COLUMN_AREA_TEMPERATURE = "areaTemperature";
    public static final String COLUMN_PET_LOCATION = "petLocation";

    private static final String CREATE_TABLE_PETS =
            "CREATE TABLE " + TABLE_PETS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_TYPE + " TEXT, " +
                    COLUMN_IMAGE_RES_ID + " INTEGER, " +
                    COLUMN_NEXT_FEEDING_SCHEDULE + " TEXT, " +
                    COLUMN_AREA_WEATHER + " TEXT, " +
                    COLUMN_AREA_TEMPERATURE + " REAL, " +
                    COLUMN_PET_LOCATION + " TEXT" +
                    ");";

    public PetDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the pets table
        db.execSQL(CREATE_TABLE_PETS);

        // Insert dummy data
        insertDummyData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PETS);
        onCreate(db);
    }

    private void insertDummyData(SQLiteDatabase db) {
        // Replace R.drawable.<image> with appropriate image resource IDs based on the app's drawable
        String[] dummyData = {
                "INSERT INTO " + TABLE_PETS + " (" +
                        COLUMN_NAME + ", " +
                        COLUMN_TYPE + ", " +
                        COLUMN_IMAGE_RES_ID + ", " +
                        COLUMN_NEXT_FEEDING_SCHEDULE + ", " +
                        COLUMN_AREA_WEATHER + ", " +
                        COLUMN_AREA_TEMPERATURE + ", " +
                        COLUMN_PET_LOCATION +
                        ") VALUES ('Buddy', 'Dog', 1, 'Tomorrow, 7 AM', 'Sunny', 22.5, 'Home')",
                "INSERT INTO " + TABLE_PETS + " (" +
                        COLUMN_NAME + ", " +
                        COLUMN_TYPE + ", " +
                        COLUMN_IMAGE_RES_ID + ", " +
                        COLUMN_NEXT_FEEDING_SCHEDULE + ", " +
                        COLUMN_AREA_WEATHER + ", " +
                        COLUMN_AREA_TEMPERATURE + ", " +
                        COLUMN_PET_LOCATION +
                        ") VALUES ('Whiskers', 'Cat', 2, 'Today, 6 PM', 'Cloudy', 24.0, 'Apartment')",
                "INSERT INTO " + TABLE_PETS + " (" +
                        COLUMN_NAME + ", " +
                        COLUMN_TYPE + ", " +
                        COLUMN_IMAGE_RES_ID + ", " +
                        COLUMN_NEXT_FEEDING_SCHEDULE + ", " +
                        COLUMN_AREA_WEATHER + ", " +
                        COLUMN_AREA_TEMPERATURE + ", " +
                        COLUMN_PET_LOCATION +
                        ") VALUES ('Polly', 'Parrot', 3, 'Tomorrow, 8 AM', 'Rainy', 23.3, 'Home')",
                "INSERT INTO " + TABLE_PETS + " (" +
                        COLUMN_NAME + ", " +
                        COLUMN_TYPE + ", " +
                        COLUMN_IMAGE_RES_ID + ", " +
                        COLUMN_NEXT_FEEDING_SCHEDULE + ", " +
                        COLUMN_AREA_WEATHER + ", " +
                        COLUMN_AREA_TEMPERATURE + ", " +
                        COLUMN_PET_LOCATION +
                        ") VALUES ('Ducky', 'Leopard Gecko', 4, 'Today, 10 PM', 'Clear', 28.0, 'Dorm Room')"
        };

        for (String query : dummyData) {
            db.execSQL(query);
        }
    }

    // Update pet information
    public boolean updatePet(int petId, String name, String type, int imageResId, String nextFeedingSchedule, String areaWeather, double areaTemperature, String petLocation) {
        SQLiteDatabase db = this.getWritableDatabase();

        // ContentValues to hold the updated values
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_TYPE, type);
        values.put(COLUMN_IMAGE_RES_ID, imageResId);
        values.put(COLUMN_NEXT_FEEDING_SCHEDULE, nextFeedingSchedule);
        values.put(COLUMN_AREA_WEATHER, areaWeather);
        values.put(COLUMN_AREA_TEMPERATURE, areaTemperature);
        values.put(COLUMN_PET_LOCATION, petLocation);

        // Update the row where the pet ID matches
        int rowsAffected = db.update(TABLE_PETS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(petId)});

        db.close();
        return rowsAffected > 0;
    }

    // Get pet by ID

}
