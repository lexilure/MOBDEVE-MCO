package mobdeve.com.s20.group3.mco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class PetDatabase {

    private PetDatabaseHelper petDatabaseHelper;

    // Initialize the PetDatabaseHelper instance using the provided context
    public PetDatabase(Context context) {
        this.petDatabaseHelper = new PetDatabaseHelper(context);
    }

    // Inserts a provided Pet into the database and returns the row ID of the new entry
    public long addPet(Pet pet) {
        SQLiteDatabase db = petDatabaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(PetDatabaseHelper.COLUMN_NAME, pet.getName());
        contentValues.put(PetDatabaseHelper.COLUMN_TYPE, pet.getType());
        contentValues.put(PetDatabaseHelper.COLUMN_IMAGE_RES_ID, pet.getImageResId());
        contentValues.put(PetDatabaseHelper.COLUMN_NEXT_FEEDING_SCHEDULE, pet.getNextFeedingSchedule());
        contentValues.put(PetDatabaseHelper.COLUMN_AREA_WEATHER, pet.getAreaWeather());
        contentValues.put(PetDatabaseHelper.COLUMN_AREA_TEMPERATURE, pet.getAreaTemperature());
        contentValues.put(PetDatabaseHelper.COLUMN_PET_LOCATION, pet.getPetLocation());

        long id = db.insert(PetDatabaseHelper.TABLE_PETS, null, contentValues);
        db.close();

        return id;
    }

    // Updates an existing Pet entry in the database
    public void updatePet(Pet pet) {
        SQLiteDatabase db = petDatabaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(PetDatabaseHelper.COLUMN_NAME, pet.getName());
        contentValues.put(PetDatabaseHelper.COLUMN_TYPE, pet.getType());
        contentValues.put(PetDatabaseHelper.COLUMN_IMAGE_RES_ID, pet.getImageResId());
        contentValues.put(PetDatabaseHelper.COLUMN_NEXT_FEEDING_SCHEDULE, pet.getNextFeedingSchedule());
        contentValues.put(PetDatabaseHelper.COLUMN_AREA_WEATHER, pet.getAreaWeather());
        contentValues.put(PetDatabaseHelper.COLUMN_AREA_TEMPERATURE, pet.getAreaTemperature());
        contentValues.put(PetDatabaseHelper.COLUMN_PET_LOCATION, pet.getPetLocation());

        // Ensure `pet.getId()` retrieves the correct ID if a method is added for it.
        db.update(
                PetDatabaseHelper.TABLE_PETS,
                contentValues,
                PetDatabaseHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(pet.getId())} // Replace getId() if needed
        );

        db.close();
    }

    // Deletes a Pet entry from the database
    public void deletePet(Pet pet) {
        SQLiteDatabase db = petDatabaseHelper.getWritableDatabase();

        db.delete(
                PetDatabaseHelper.TABLE_PETS,
                PetDatabaseHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(pet.getId())} // Replace getId() if needed
        );

        db.close();
    }

    // Retrieves all Pet entries from the database and returns them as an ArrayList
    public ArrayList<Pet> getAllPets() {
        ArrayList<Pet> result = new ArrayList<>();

        SQLiteDatabase db = petDatabaseHelper.getReadableDatabase();
        String query = "SELECT * FROM " + PetDatabaseHelper.TABLE_PETS;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Pet pet = new Pet(
                        cursor.getString(cursor.getColumnIndexOrThrow(PetDatabaseHelper.COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(PetDatabaseHelper.COLUMN_TYPE)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(PetDatabaseHelper.COLUMN_IMAGE_RES_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(PetDatabaseHelper.COLUMN_NEXT_FEEDING_SCHEDULE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(PetDatabaseHelper.COLUMN_AREA_WEATHER)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(PetDatabaseHelper.COLUMN_AREA_TEMPERATURE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(PetDatabaseHelper.COLUMN_PET_LOCATION))
                );

                result.add(pet);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return result;
    }
}
