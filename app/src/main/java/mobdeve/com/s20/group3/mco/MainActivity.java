package mobdeve.com.s20.group3.mco;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PetAdapter petAdapter;
    private PetDatabase petDatabase;
    private List<Pet> petList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the database
        petDatabase = new PetDatabase(this);

        // Retrieve all pets from the database
        petList = petDatabase.getAllPets();

        // Set up the RecyclerView for pets
        RecyclerView petsRecyclerView = findViewById(R.id.petsRecyclerView);
        petsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // 2-column grid

        petAdapter = new PetAdapter(petList, this);
        petsRecyclerView.setAdapter(petAdapter);
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
