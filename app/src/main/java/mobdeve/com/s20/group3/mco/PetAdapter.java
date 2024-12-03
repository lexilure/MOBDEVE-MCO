package mobdeve.com.s20.group3.mco;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.PetViewHolder> {

    private List<Pet> petList;
    private Context context;

    public PetAdapter(List<Pet> petList, Context context) {
        this.petList = petList;
        this.context = context;
    }

    // Create and return a ViewHolder for each pet item
    @Override
    public PetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pet_item, parent, false);
        return new PetViewHolder(view);
    }

    // Bind data to each ViewHolder
    @Override
    public void onBindViewHolder(PetViewHolder holder, int position) {
        Pet pet = petList.get(position);
        holder.petName.setText(pet.getName());
        holder.petSpecies.setText(pet.getType());

        // Use a placeholder image for now; you can modify this to display actual images
        holder.petImage.setImageResource(R.drawable.dog); // Placeholder image

        // Set OnClickListener for each pet item
        holder.itemView.setOnClickListener(v -> {
            // Intent to open PetProfileActivity and pass the pet data
            Intent intent = new Intent(context, PetProfileActivity.class);
            intent.putExtra("pet_name", pet.getName());
            intent.putExtra("pet_type", pet.getType());
            intent.putExtra("pet_image", R.drawable.dog); // Replace with actual image resource or URL
            intent.putExtra("next_feeding", pet.getNextFeedingSchedule()); // Assuming this is a property of Pet
            intent.putExtra("area_weather", pet.getAreaWeather()); // Assuming this is a property of Pet
            intent.putExtra("area_temp", pet.getAreaTemperature()); // Assuming this is a property of Pet
            intent.putExtra("pet_location", pet.getPetLocation()); // Assuming this is a property of Pet
            context.startActivity(intent);
        });
    }

    // Return the size of the pet list
    @Override
    public int getItemCount() {
        return petList.size();
    }

    // ViewHolder class to hold individual pet views
    public static class PetViewHolder extends RecyclerView.ViewHolder {
        TextView petName, petSpecies;
        ImageView petImage;

        public PetViewHolder(View itemView) {
            super(itemView);
            petName = itemView.findViewById(R.id.petName);
            petSpecies = itemView.findViewById(R.id.petSpecies);
            petImage = itemView.findViewById(R.id.petImage);
        }
    }
}
