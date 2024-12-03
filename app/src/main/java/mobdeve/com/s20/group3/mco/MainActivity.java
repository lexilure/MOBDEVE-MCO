package mobdeve.com.s20.group3.mco;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Apply padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Prepare the dummy data for pets
        List<Pet> petList = new ArrayList<>();
        petList.add(new Pet("Buddy", "Dog", R.drawable.dog, "Tomorrow, 7 AM", "Sunny", 22.5, "Home"));
        petList.add(new Pet("Whiskers", "Cat", R.drawable.cat, "Today, 6 PM", "Cloudy", 24.0, "Apartment"));
        petList.add(new Pet("Polly", "Parrot", R.drawable.parrot, "Tomorrow, 8 AM", "Rainy", 23.3, "Home"));
        petList.add(new Pet("Ducky", "Leopard Gecko", R.drawable.leopardgecko, "Today, 10 PM", "Clear", 28.0, "Dorm Room"));

        // Set up the RecyclerView for pets
        RecyclerView petsRecyclerView = findViewById(R.id.petsRecyclerView);
        petsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // 2-column grid
        PetAdapter adapter = new PetAdapter(petList, this);
        petsRecyclerView.setAdapter(adapter);
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
