package mobdeve.com.s20.group3.mco;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.core.app.NotificationCompat;

public class VeterinaryNotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String petName = intent.getStringExtra("petName");

        // Create a notification to inform the user about the veterinary appointment
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "veterinaryChannel")
                .setSmallIcon(R.drawable.vet) // You can add an icon for the notification
                .setContentTitle(petName + " has a visit to the vet scheduled today")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Create a pending intent for opening the app when the notification is tapped
        Intent appIntent = new Intent(context, VeterinaryActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        // Send the notification
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(petName.hashCode(), builder.build());
        }

        // After sending the notification, remove the veterinary appointment date from SharedPreferences
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(petName + "_veterinary_date");  // Assuming you store veterinary dates with "_veterinary_date"
        editor.apply();
    }
}
