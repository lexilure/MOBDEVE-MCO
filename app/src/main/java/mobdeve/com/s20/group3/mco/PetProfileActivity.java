package mobdeve.com.s20.group3.mco;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PetProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_profile);

        // Get the Intent that started this activity and extract the data
        Intent intent = getIntent();
        String petName = intent.getStringExtra("pet_name");
        String petType = intent.getStringExtra("pet_type");
        int petImageResId = intent.getIntExtra("pet_image", 0);
        String nextFeeding = intent.getStringExtra("next_feeding");
        String areaWeather = intent.getStringExtra("area_weather");
        double areaTemp = intent.getDoubleExtra("area_temp", 0.0);
        String petLocation = intent.getStringExtra("pet_location");

        // Capture the layout's TextViews and ImageView and set the values
        TextView nameTextView = findViewById(R.id.petProfileName);
        TextView typeTextView = findViewById(R.id.petProfileType);
        ImageView petImageView = findViewById(R.id.petProfileImage);
        TextView nextFeedingTextView = findViewById(R.id.petProfileNextFeeding); // New
        TextView areaWeatherTextView = findViewById(R.id.petProfileAreaWeather); // New
        TextView areaTempTextView = findViewById(R.id.petProfileAreaTemp);       // New
        TextView petLocationTextView = findViewById(R.id.petProfileLocation);    // New

        nameTextView.setText(petName);
        typeTextView.setText(petType);
        petImageView.setImageResource(petImageResId);
        nextFeedingTextView.setText("Next Feeding: " + nextFeeding);
        areaWeatherTextView.setText("Weather: " + areaWeather);
        areaTempTextView.setText("Temperature: " + areaTemp + "°C");
        petLocationTextView.setText("Location: " + petLocation);
    }
}
