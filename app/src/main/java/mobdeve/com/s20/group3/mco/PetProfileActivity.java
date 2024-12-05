package mobdeve.com.s20.group3.mco;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class PetProfileActivity extends AppCompatActivity {

    private PetDatabase petDatabase;
    private Pet currentPet;
    private Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_profile);

        // Initialize the PetDatabase instance
        petDatabase = new PetDatabase(this);

        // Get the Intent that started this activity and extract the data
        Intent intent = getIntent();
        String petName = intent.getStringExtra("pet_name");
        String petType = intent.getStringExtra("pet_type");
        int petImageResId = intent.getIntExtra("pet_image", 0);
        String nextFeeding = intent.getStringExtra("next_feeding");
        String areaWeather = intent.getStringExtra("area_weather");
        double areaTemp = intent.getDoubleExtra("area_temp", 0.0);
        String petLocation = intent.getStringExtra("pet_location");

        // Initialize the current pet object
        currentPet = new Pet(petName, petType, petImageResId, nextFeeding, areaWeather, areaTemp, petLocation);

        // Capture the layout's TextViews and ImageView and set the values
        TextView nameTextView = findViewById(R.id.petProfileName);
        TextView typeTextView = findViewById(R.id.petProfileType);
        ImageView petImageView = findViewById(R.id.petProfileImage);
        TextView nextFeedingTextView = findViewById(R.id.petProfileNextFeeding);
        TextView areaWeatherTextView = findViewById(R.id.petProfileAreaWeather);
        TextView areaTempTextView = findViewById(R.id.petProfileAreaTemp);
        TextView petLocationTextView = findViewById(R.id.petProfileLocation);

        // Set the pet data into the views
        nameTextView.setText(petName);
        typeTextView.setText(petType);
        petImageView.setImageResource(petImageResId);
        nextFeedingTextView.setText("Next Feeding: " + nextFeeding);
        areaWeatherTextView.setText("Weather: " + areaWeather);
        areaTempTextView.setText("Temperature: " + areaTemp + "°C");
        petLocationTextView.setText("Location: " + petLocation);

        // Initialize the delete button
        deleteButton = findViewById(R.id.deletePetProfileButton);

        // Set up the delete button
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePetProfile();
            }
        });
    }

    private void deletePetProfile() {
        // Create a confirmation dialog before deleting the pet profile
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to delete this pet profile?")
                .setCancelable(false) // Prevent the dialog from being dismissed by touching outside
                .setPositiveButton("Yes", (dialog, id) -> {
                    // Proceed with the deletion if "Yes" is clicked
                    boolean isDeleted = petDatabase.deletePet(currentPet.getName()); // Use currentPet.getId() if ID is available

                    if (isDeleted) {
                        Toast.makeText(this, "Pet profile deleted successfully.", Toast.LENGTH_SHORT).show();

                        // Trigger refresh by finishing the current activity and returning to the previous one
                        Intent intent = new Intent(PetProfileActivity.this, MainActivity.class); // Assuming MainActivity is the one displaying the pets list
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Clear all other activities in the stack
                        startActivity(intent); // Restart the MainActivity to refresh the pets list
                        finish(); // Close current activity
                    } else {
                        Toast.makeText(this, "Failed to delete pet profile.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", (dialog, id) -> {
                    // Dismiss the dialog if "No" is clicked
                    dialog.dismiss();
                })
                .create()
                .show();
    }


}
