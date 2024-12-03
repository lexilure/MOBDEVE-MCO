package mobdeve.com.s20.group3.mco;

import android.os.Bundle;

import android.app.AlertDialog;
import android.app.Dialog;
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

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // REWORKED TO IMPLEMENT BACKEND

        // Initialize the database
        PetDatabase petDatabase = new PetDatabase(this);
        // Retrieve all pets from the database
        List<Pet> petList = petDatabase.getAllPets();

        // Set up the RecyclerView
        RecyclerView petsRecyclerView = findViewById(R.id.petsRecyclerView);
        petsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // Set a 2-column grid
        PetAdapter adapter = new PetAdapter(petList, this);
        petsRecyclerView.setAdapter(adapter);
    }
}
