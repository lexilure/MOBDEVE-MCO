package mobdeve.com.s20.group3.mco;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.PetViewHolder> {

    private List<Pet> petList;
    private Context context;

    public PetAdapter(List<Pet> petList, Context context) {
        this.petList = petList;
        this.context = context;
    }

    public static class PetViewHolder extends RecyclerView.ViewHolder {
        TextView petNameTextView;
        TextView petTypeTextView;
        ImageView petImageView;

        public PetViewHolder(@NonNull View itemView) {
            super(itemView);
            petNameTextView = itemView.findViewById(R.id.pet_name);
            petTypeTextView = itemView.findViewById(R.id.pet_type);
            petImageView = itemView.findViewById(R.id.pet_image);
        }
    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pet_item, parent, false);
        return new PetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {
        Pet currentPet = petList.get(position);
        holder.petNameTextView.setText(currentPet.getName());
        holder.petTypeTextView.setText(currentPet.getType());
        holder.petImageView.setImageResource(currentPet.getImageResId());

        // Set the onClickListener to open the PetProfileActivity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, PetProfileActivity.class);
            intent.putExtra("pet_name", currentPet.getName());
            intent.putExtra("pet_type", currentPet.getType());
            intent.putExtra("pet_image", currentPet.getImageResId());

            // Pass the new data
            intent.putExtra("next_feeding", currentPet.getNextFeedingSchedule());
            intent.putExtra("area_weather", currentPet.getAreaWeather());
            intent.putExtra("area_temp", currentPet.getAreaTemperature()); // Make sure this is a double
            intent.putExtra("pet_location", currentPet.getPetLocation());

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return petList.size();
    }
}
