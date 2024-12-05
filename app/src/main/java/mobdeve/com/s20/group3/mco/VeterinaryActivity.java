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

public class VeterinaryActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private SharedPreferences sharedPreferences;
    private PetDatabase petDatabase; // Database instance
    private ArrayList<Pet> pets = new ArrayList<>(); // List to store pets
    private LinearLayout veterinaryListLayout; // Layout for displaying veterinary appointments

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veterinary);

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE); // SharedPreferences for saving veterinary dates
        calendarView = findViewById(R.id.calendarViewVeterinary);
        veterinaryListLayout = findViewById(R.id.veterinaryListLayout); // Initialize the layout

        petDatabase = new PetDatabase(this); // Initialize PetDatabase

        // Fetch all pets from the database
        pets = petDatabase.getAllPets();

        // Set up the calendar view click listener
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth; // Format the date
            openPetSelectionDialog(selectedDate);
        });

        // Update the list of pets with veterinary appointment dates
        updateVeterinaryList();
    }

    // Open dialog to select a pet for veterinary appointment
    private void openPetSelectionDialog(String selectedDate) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Pet for Veterinary Appointment");

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
            saveVeterinaryDate(selectedPet, selectedDate);

            // After scheduling, update the veterinary list
            updateVeterinaryList();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    // Save the veterinary date for the selected pet in SharedPreferences
    private void saveVeterinaryDate(String selectedPet, String selectedDate) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(selectedPet + "_veterinary_date", selectedDate); // Save date under the pet's name
        editor.apply();
    }

    // Update the list of pets with veterinary appointment dates in ascending order
    private void updateVeterinaryList() {
        // Clear existing list items
        veterinaryListLayout.removeAllViews();

        ArrayList<String> veterinaryDates = new ArrayList<>();

        // Retrieve veterinary dates for all pets
        for (Pet pet : pets) {
            String veterinaryDate = sharedPreferences.getString(pet.getName() + "_veterinary_date", null);
            if (veterinaryDate != null) {
                veterinaryDates.add(pet.getName() + ": " + veterinaryDate);
            }
        }

        // Sort the veterinary dates in ascending order
        Collections.sort(veterinaryDates, (date1, date2) -> {
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

        // Inflate the layout for each veterinary entry
        LayoutInflater inflater = LayoutInflater.from(this);
        for (String veterinaryEntry : veterinaryDates) {
            View veterinaryItemView = inflater.inflate(R.layout.veterinary_list_item, veterinaryListLayout, false);

            // Set pet image and veterinary date in the layout item
            ImageView petImageView = veterinaryItemView.findViewById(R.id.petImageView);
            TextView petNameTextView = veterinaryItemView.findViewById(R.id.petNameTextView);
            TextView veterinaryTextView = veterinaryItemView.findViewById(R.id.veterinaryTextView);
            TextView removeButton = veterinaryItemView.findViewById(R.id.removeButton);

            // Extract pet name and veterinary date
            String[] veterinaryDetails = veterinaryEntry.split(": ");
            String petName = veterinaryDetails[0];
            String veterinaryDate = veterinaryDetails[1];

            // Set pet name and veterinary date
            petNameTextView.setText(petName);
            veterinaryTextView.setText("Appointment Date: " + veterinaryDate);

            // Set click listener for the remove button
            removeButton.setOnClickListener(v -> {
                // Remove veterinary date from SharedPreferences
                removeVeterinaryDate(petName);
                // Refresh the list after removal
                updateVeterinaryList();
            });

            // Optionally, set a pet image here (e.g., based on pet type or name)
            // petImageView.setImageResource(R.drawable.dog); // Replace with actual pet image logic

            // Add the veterinary item view to the layout
            veterinaryListLayout.addView(veterinaryItemView);
        }
    }

    // Remove the veterinary date for a pet
    private void removeVeterinaryDate(String petName) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(petName + "_veterinary_date");
        editor.apply();
    }
}
