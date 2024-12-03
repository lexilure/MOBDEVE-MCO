package mobdeve.com.s20.group3.mco;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddPetActivity extends AppCompatActivity {

    private EditText etPetName, etSpecies, etLocation, etTempRange, etHumidityRange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);

        // Initialize views
        etPetName = findViewById(R.id.etPetName);
        etSpecies = findViewById(R.id.etSpecies);
        etLocation = findViewById(R.id.etLocation);
        etTempRange = findViewById(R.id.etTempRange);
        etHumidityRange = findViewById(R.id.etHumidityRange);
    }

    // Method to save the new pet
    public void savePet(View view) {
        String petName = etPetName.getText().toString();
        String species = etSpecies.getText().toString();
        String location = etLocation.getText().toString();
        String tempRange = etTempRange.getText().toString();
        String humidityRange = etHumidityRange.getText().toString();

        // Validating input fields
        if (petName.isEmpty() || species.isEmpty() || location.isEmpty()) {
            Toast.makeText(this, "Please fill in all required fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a Pet object with the entered data
        Pet pet = new Pet(
                petName,
                species,
                0, // Image resource ID is not provided yet
                "", // Empty values for optional fields like next feeding schedule
                "", // Area weather
                Double.parseDouble(tempRange.isEmpty() ? "0" : tempRange), // Convert temperature
                location
        );

        // Save the data to the database
        PetDatabase petDatabase = new PetDatabase(this);
        long result = petDatabase.addPet(pet);

        if (result != -1) {
            Toast.makeText(this, "Pet added successfully!", Toast.LENGTH_SHORT).show();

            // Return result to MainActivity to refresh the pet list
            Intent returnIntent = new Intent();
            setResult(RESULT_OK, returnIntent); // Notify MainActivity of success
            finish(); // Close the activity after successful addition
        } else {
            Toast.makeText(this, "Failed to add pet.", Toast.LENGTH_SHORT).show();
        }
    }
}
