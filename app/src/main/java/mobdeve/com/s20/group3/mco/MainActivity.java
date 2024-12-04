package mobdeve.com.s20.group3.mco;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PetAdapter petAdapter;
    private PetDatabase petDatabase;
    private List<Pet> petList;
    private List<String> petTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the database
        petDatabase = new PetDatabase(this);

        // Retrieve all pets and distinct pet types from the database
        petList = petDatabase.getAllPets();
        petTypes = petDatabase.getDistinctPetTypes();
        petTypes.add(0, "All"); // Add "All" option at the top

        // Set up the RecyclerView for pets
        RecyclerView petsRecyclerView = findViewById(R.id.petsRecyclerView);
        petsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // 2-column grid

        petAdapter = new PetAdapter(petList, this);
        petsRecyclerView.setAdapter(petAdapter);

        // Set up Spinner for sorting
        Spinner spinnerSort = findViewById(R.id.spinnerSort);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, petTypes);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSort.setAdapter(spinnerAdapter);

        // Handle Spinner selection events
        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedType = petTypes.get(position);

                if ("All".equals(selectedType)) {
                    // Show all pets
                    petList.clear();
                    petList.addAll(petDatabase.getAllPets());
                } else {
                    // Show pets of the selected type
                    petList.clear();
                    petList.addAll(petDatabase.getPetsByType(selectedType));
                }

                // Notify adapter of data changes
                petAdapter.notifyDataSetChanged();

                Toast.makeText(MainActivity.this, "Filtered by: " + selectedType, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No action needed
            }
        });
    }

    // Method to navigate to AddPetActivity
    public void goToAddPetActivity(View view) {
        Intent intent = new Intent(MainActivity.this, AddPetActivity.class);
        startActivityForResult(intent, 1); // Request code 1
    }

    // Handle result from AddPetActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // After a new pet is added, update the RecyclerView
            petList.clear();
            petList.addAll(petDatabase.getAllPets());
            petAdapter.notifyDataSetChanged();
        }
    }

    // Method to navigate to GroomingActivity
    public void goToGrooming(View view) {
        Intent intent = new Intent(MainActivity.this, GroomingActivity.class);
        startActivity(intent);
    }

    // Method to navigate to FeedingActivity
    public void goToFeeding(View view) {
        Intent intent = new Intent(MainActivity.this, FeedingActivity.class);
        startActivity(intent);
    }

    // Method to navigate to VeterinaryActivity
    public void goToVeterinary(View view) {
        Intent intent = new Intent(MainActivity.this, VeterinaryActivity.class);
        startActivity(intent);
    }
}
