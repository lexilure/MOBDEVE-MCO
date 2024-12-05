package mobdeve.com.s20.group3.mco;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;

public class FeedingActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private SharedPreferences sharedPreferences;
    private PetDatabase petDatabase;
    private ArrayList<Pet> pets = new ArrayList<>();
    private LinearLayout feedingListLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeding);

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        calendarView = findViewById(R.id.calendarViewFeeding);
        feedingListLayout = findViewById(R.id.feedingListLayout);

        petDatabase = new PetDatabase(this);
        pets = petDatabase.getAllPets();

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
            openPetSelectionDialog(selectedDate);
        });

        updateFeedingList();
    }

    // Open dialog to select a pet and schedule feeding
    private void openPetSelectionDialog(String selectedDate) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Pet for Feeding");

        if (pets.isEmpty()) {
            Toast.makeText(this, "No pets available. Please add pets first.", Toast.LENGTH_SHORT).show();
            return;
        }

        CharSequence[] petArray = new CharSequence[pets.size()];
        for (int i = 0; i < pets.size(); i++) {
            petArray[i] = pets.get(i).getName();
        }

        builder.setItems(petArray, (dialog, which) -> {
            String selectedPet = pets.get(which).getName();
            openFeedingTimeDialog(selectedPet, selectedDate);
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    // Open dialog to set feeding time for a selected pet
    private void openFeedingTimeDialog(String selectedPet, String selectedDate) {
        AlertDialog.Builder timeDialog = new AlertDialog.Builder(this);
        timeDialog.setTitle("Enter Feeding Time (HH:mm)");

        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_feeding_time, null);
        timeDialog.setView(customLayout);

        timeDialog.setPositiveButton("Save", (dialog, which) -> {
            TextView timeInput = customLayout.findViewById(R.id.feedingTimeInput);
            String feedingTime = timeInput.getText().toString();

            if (!feedingTime.matches("\\d{2}:\\d{2}")) {
                Toast.makeText(this, "Invalid time format. Use HH:mm.", Toast.LENGTH_SHORT).show();
                return;
            }

            String schedule = selectedDate + " " + feedingTime;
            saveFeedingSchedule(selectedPet, schedule);

            updateFeedingList();
        });

        timeDialog.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        timeDialog.show();
    }

    // Save feeding schedule for the selected pet
    private void saveFeedingSchedule(String selectedPet, String schedule) {
        String key = selectedPet + "_feeding_schedule";
        HashSet<String> schedules = new HashSet<>(sharedPreferences.getStringSet(key, new HashSet<>()));
        schedules.add(schedule);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(key, schedules);
        editor.apply();
    }

    // Update the list of pets with their feeding schedules
    private void updateFeedingList() {
        feedingListLayout.removeAllViews();

        ArrayList<String> feedingSchedules = new ArrayList<>();

        for (Pet pet : pets) {
            String key = pet.getName() + "_feeding_schedule";
            HashSet<String> schedules = new HashSet<>(sharedPreferences.getStringSet(key, new HashSet<>()));

            for (String schedule : schedules) {
                feedingSchedules.add(pet.getName() + ": " + schedule);
            }
        }

        Collections.sort(feedingSchedules, (schedule1, schedule2) -> {
            try {
                SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date d1 = dateTimeFormat.parse(schedule1.split(": ")[1]);
                Date d2 = dateTimeFormat.parse(schedule2.split(": ")[1]);
                return d1.compareTo(d2);
            } catch (ParseException e) {
                e.printStackTrace();
                return 0;
            }
        });

        LayoutInflater inflater = LayoutInflater.from(this);
        for (String feedingEntry : feedingSchedules) {
            View feedingItemView = inflater.inflate(R.layout.feeding_list_item, feedingListLayout, false);

            ImageView petImageView = feedingItemView.findViewById(R.id.petImageView);
            TextView petNameTextView = feedingItemView.findViewById(R.id.petNameTextView);
            TextView feedingTextView = feedingItemView.findViewById(R.id.feedingTextView);
            TextView removeButton = feedingItemView.findViewById(R.id.removeButton);

            String[] feedingDetails = feedingEntry.split(": ");
            String petName = feedingDetails[0];
            String feedingSchedule = feedingDetails[1];

            petNameTextView.setText(petName);
            feedingTextView.setText("Feeding Time: " + feedingSchedule);

            removeButton.setOnClickListener(v -> {
                removeFeedingSchedule(petName, feedingSchedule);
                updateFeedingList();
            });

            // Optionally, set a pet image here based on logic
            // petImageView.setImageResource(R.drawable.dog);

            feedingListLayout.addView(feedingItemView);
        }
    }

    // Remove a feeding schedule for a pet
    private void removeFeedingSchedule(String petName, String schedule) {
        String key = petName + "_feeding_schedule";
        HashSet<String> schedules = new HashSet<>(sharedPreferences.getStringSet(key, new HashSet<>()));

        if (schedules.contains(schedule)) {
            schedules.remove(schedule);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putStringSet(key, schedules);
            editor.apply();
        }
    }
}
