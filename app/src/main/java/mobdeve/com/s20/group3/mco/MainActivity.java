package mobdeve.com.s20.group3.mco;

import android.os.Bundle;

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

        // Prepare the dummy data
        List<Pet> petList = new ArrayList<>();
        petList.add(new Pet("Buddy", "Dog", R.drawable.dog));
        petList.add(new Pet("Whiskers", "Cat", R.drawable.cat));
        petList.add(new Pet("Polly", "Parrot", R.drawable.parrot));
        petList.add(new Pet("Ducky", "Leopard Gecko", R.drawable.leopardgecko));

        // Set up the RecyclerView
        RecyclerView petsRecyclerView = findViewById(R.id.petsRecyclerView);
        petsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // Set a 2-column grid
        PetAdapter adapter = new PetAdapter(petList, this);
        petsRecyclerView.setAdapter(adapter);
    }
}
