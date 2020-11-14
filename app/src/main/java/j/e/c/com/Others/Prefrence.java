package j.e.c.com.Others;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;

public class Prefrence {

    static SharedPreferences prefs;

    public static void saveProfileImage(Uri imageUri, Context context){
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putString("ImageUriString", imageUri.toString()).apply();
    }

    public static Uri getProfileImage(Context context){
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String imageString = prefs.getString("ImageUriString", null);
        if (imageString == null)
            return null;
        return Uri.parse(imageString);
    }
}
