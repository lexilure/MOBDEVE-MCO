package mobdeve.com.s20.group3.mco;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class GroomingActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private SharedPreferences sharedPreferences;
    private PetDatabase petDatabase; // Database instance
    private ArrayList<Pet> pets = new ArrayList<>(); // List to store pets
    private LinearLayout groomingListLayout; // Layout for displaying grooming items

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grooming);

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE); // SharedPreferences for saving grooming dates
        calendarView = findViewById(R.id.calendarViewGrooming);
        groomingListLayout = findViewById(R.id.groomingListLayout); // Initialize the layout

        petDatabase = new PetDatabase(this); // Initialize PetDatabase

        // Fetch all pets from the database
        pets = petDatabase.getAllPets();

        // Set up the calendar view click listener
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth; // Format the date
            openPetSelectionDialog(selectedDate);
        });

        // Update the list of pets with grooming dates
        updateGroomingList();
    }

    // Open dialog to select a pet for grooming
    private void openPetSelectionDialog(String selectedDate) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Pet for Grooming");

        // If no pets are available in the database, show a message
        if (pets.isEmpty()) {
            Toast.makeText(this, "No pets available. Please add pets first.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convert pets list to a CharSequence array (for displaying in the dialog)
        CharSequence[] petArray = new CharSequence[pets.size()];
        for (int i = 0; i < pets.size(); i++) {
            petArray[i] = pets.get(i).getName(); // Use pet name for display
        }

        builder.setItems(petArray, (dialog, which) -> {
            String selectedPet = pets.get(which).getName(); // Get the selected pet's name
            saveGroomingDate(selectedPet, selectedDate);
            Toast.makeText(this, "Grooming scheduled for " + selectedPet + " on " + selectedDate, Toast.LENGTH_SHORT).show();

            // After scheduling, update the grooming list
            updateGroomingList();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    // Save the grooming date for the selected pet in SharedPreferences
    private void saveGroomingDate(String selectedPet, String selectedDate) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(selectedPet + "_grooming_date", selectedDate); // Save date under the pet's name
        editor.apply();
    }

    // Update the list of pets with grooming dates in ascending order
    private void updateGroomingList() {
        // Clear existing list items
        groomingListLayout.removeAllViews();

        ArrayList<String> groomingDates = new ArrayList<>();

        // Retrieve grooming dates for all pets
        for (Pet pet : pets) {
            String groomingDate = sharedPreferences.getString(pet.getName() + "_grooming_date", null);
            if (groomingDate != null) {
                groomingDates.add(pet.getName() + ": " + groomingDate);
            }
        }

        // Sort the grooming dates in ascending order
        Collections.sort(groomingDates, (date1, date2) -> {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date d1 = dateFormat.parse(date1.split(": ")[1]);
                Date d2 = dateFormat.parse(date2.split(": ")[1]);
                return d1.compareTo(d2);
            } catch (ParseException e) {
                e.printStackTrace();
                return 0;
            }
        });

        // Inflate the layout for each grooming entry
        LayoutInflater inflater = LayoutInflater.from(this);
        for (String groomingEntry : groomingDates) {
            View groomingItemView = inflater.inflate(R.layout.grooming_list_item, groomingListLayout, false);

            // Set pet image and grooming date in the layout item
            ImageView petImageView = groomingItemView.findViewById(R.id.petImageView);
            TextView petNameTextView = groomingItemView.findViewById(R.id.petNameTextView);
            TextView groomingTextView = groomingItemView.findViewById(R.id.groomingTextView);
            TextView removeButton = groomingItemView.findViewById(R.id.removeButton);

            // Extract pet name and grooming date
            String[] groomingDetails = groomingEntry.split(": ");
            String petName = groomingDetails[0];
            String groomingDate = groomingDetails[1];

            // Set pet name and grooming date
            petNameTextView.setText(petName);
            groomingTextView.setText("Grooming Date: " + groomingDate);

            // Set click listener for the remove button
            removeButton.setOnClickListener(v -> {
                // Remove grooming date from SharedPreferences
                removeGroomingDate(petName);
                // Refresh the list after removal
                updateGroomingList();
            });

            // Optionally, set a pet image here (e.g., based on pet type or name)
            // petImageView.setImageResource(R.drawable.dog); // Replace with actual pet image logic

            // Add the grooming item view to the layout
            groomingListLayout.addView(groomingItemView);
        }
    }


    // Remove the grooming date for a pet
    private void removeGroomingDate(String petName) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(petName + "_grooming_date");
        editor.apply();
    }
}
