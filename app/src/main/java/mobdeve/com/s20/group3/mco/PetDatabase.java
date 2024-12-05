package mobdeve.com.s20.group3.mco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class PetDatabase {

    private PetDatabaseHelper petDatabaseHelper;

    // Initialize the PetDatabaseHelper instance using the provided context
    public PetDatabase(Context context) {
        this.petDatabaseHelper = new PetDatabaseHelper(context);
    }

    // Inserts a provided Pet into the database and returns the row ID of the new entry
    public long addPet(Pet pet) {
        SQLiteDatabase db = petDatabaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PetDatabaseHelper.COLUMN_NAME, pet.getName());
        values.put(PetDatabaseHelper.COLUMN_TYPE, pet.getType());
        values.put(PetDatabaseHelper.COLUMN_IMAGE_RES_ID, pet.getImageResId());
        values.put(PetDatabaseHelper.COLUMN_NEXT_FEEDING_SCHEDULE, pet.getNextFeedingSchedule());
        values.put(PetDatabaseHelper.COLUMN_AREA_WEATHER, pet.getAreaWeather());
        values.put(PetDatabaseHelper.COLUMN_AREA_TEMPERATURE, pet.getAreaTemperature());
        values.put(PetDatabaseHelper.COLUMN_PET_LOCATION, pet.getPetLocation());

        long petId = db.insert(PetDatabaseHelper.TABLE_PETS, null, values); // Insert pet and get the ID
        db.close();
        return petId;
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

        db.update(
                PetDatabaseHelper.TABLE_PETS,
                contentValues,
                PetDatabaseHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(pet.getId())} // Use the ID of the pet to update it
        );

        db.close();
    }

    // Deletes a Pet entry from the database
    public boolean deletePet(String name) {
        SQLiteDatabase db = petDatabaseHelper.getWritableDatabase();
        int rowsDeleted = db.delete(
                PetDatabaseHelper.TABLE_PETS,
                PetDatabaseHelper.COLUMN_NAME + " = ?",
                new String[]{name}
        );
        db.close();
        return rowsDeleted > 0;
    }

    // Deletes a Pet entry from the database by its ID
    public boolean deletePetById(int id) {
        SQLiteDatabase db = petDatabaseHelper.getWritableDatabase();
        int rowsDeleted = db.delete(
                PetDatabaseHelper.TABLE_PETS,
                PetDatabaseHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}
        );
        db.close();
        return rowsDeleted > 0;
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
                        cursor.getInt(cursor.getColumnIndexOrThrow(PetDatabaseHelper.COLUMN_ID)), // Ensure ID is retrieved
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

    // Retrieves pets by type
    public ArrayList<Pet> getPetsByType(String petType) {
        ArrayList<Pet> result = new ArrayList<>();

        SQLiteDatabase db = petDatabaseHelper.getReadableDatabase();

        // Query for pets that match the given pet type
        String query = "SELECT * FROM " + PetDatabaseHelper.TABLE_PETS + " WHERE " + PetDatabaseHelper.COLUMN_TYPE + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{petType});

        if (cursor.moveToFirst()) {
            do {
                // Create a new Pet object using the updated constructor
                Pet pet = new Pet(
                        cursor.getInt(cursor.getColumnIndexOrThrow(PetDatabaseHelper.COLUMN_ID)), // Ensure ID is retrieved
                        cursor.getString(cursor.getColumnIndexOrThrow(PetDatabaseHelper.COLUMN_NAME)), // Name
                        cursor.getString(cursor.getColumnIndexOrThrow(PetDatabaseHelper.COLUMN_TYPE)), // Type
                        cursor.getInt(cursor.getColumnIndexOrThrow(PetDatabaseHelper.COLUMN_IMAGE_RES_ID)), // Image resource ID
                        cursor.getString(cursor.getColumnIndexOrThrow(PetDatabaseHelper.COLUMN_NEXT_FEEDING_SCHEDULE)), // Next feeding schedule
                        cursor.getString(cursor.getColumnIndexOrThrow(PetDatabaseHelper.COLUMN_AREA_WEATHER)), // Area weather
                        cursor.getDouble(cursor.getColumnIndexOrThrow(PetDatabaseHelper.COLUMN_AREA_TEMPERATURE)), // Area temperature
                        cursor.getString(cursor.getColumnIndexOrThrow(PetDatabaseHelper.COLUMN_PET_LOCATION)) // Pet location
                );

                result.add(pet);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return result;
    }

    // Retrieve all distinct pet types
    public List<String> getDistinctPetTypes() {
        List<String> result = new ArrayList<>();

        SQLiteDatabase db = petDatabaseHelper.getReadableDatabase();
        String query = "SELECT DISTINCT " + PetDatabaseHelper.COLUMN_TYPE +
                " FROM " + PetDatabaseHelper.TABLE_PETS;

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                result.add(cursor.getString(cursor.getColumnIndexOrThrow(PetDatabaseHelper.COLUMN_TYPE)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return result;
    }

    // Method to get a pet by its ID
    public Pet getPetById(int id) {
        SQLiteDatabase db = petDatabaseHelper.getReadableDatabase();
        String query = "SELECT * FROM " + PetDatabaseHelper.TABLE_PETS + " WHERE " + PetDatabaseHelper.COLUMN_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

        if (cursor != null && cursor.moveToFirst()) {
            Pet pet = new Pet(
                    cursor.getInt(cursor.getColumnIndexOrThrow(PetDatabaseHelper.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(PetDatabaseHelper.COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(PetDatabaseHelper.COLUMN_TYPE)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(PetDatabaseHelper.COLUMN_IMAGE_RES_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(PetDatabaseHelper.COLUMN_NEXT_FEEDING_SCHEDULE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(PetDatabaseHelper.COLUMN_AREA_WEATHER)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(PetDatabaseHelper.COLUMN_AREA_TEMPERATURE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(PetDatabaseHelper.COLUMN_PET_LOCATION))
            );
            cursor.close();
            db.close();
            return pet; // Return the pet object if found
        } else {
            cursor.close();
            db.close();
            return null; // No pet found with the given ID
        }
    }
}
