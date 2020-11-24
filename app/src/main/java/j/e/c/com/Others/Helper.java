package j.e.c.com.Others;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import j.e.c.com.R;

public class Helper {

    public static final int CAPTURE_REQUEST_CODE = 100;
    public static final int VIDEO_REQUEST_CODE = 101;
    public static final int CV_REQUEST_CODE = 102;
    public static final int IMAGE_REQUEST_CODE = 103;
    public static final String CHANNEL_ID = "technopoints_id";

    public static void Toast(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static ArrayAdapter<CharSequence> getSimpleSpinnerAdapter(int dataArray, Context context){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, dataArray, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    public static ArrayAdapter<String> getAutoCompleteAdapter(Context context){
        String[] type = new String[] {"Bed-sitter", "Single", "1- Bedroom", "2- Bedroom","3- Bedroom"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.spinner_item, type);
        return adapter;
    }

    public static void fragmentTransaction(Fragment current, Fragment target){
        FragmentTransaction transaction = Objects.requireNonNull(current.getActivity()).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, target).addToBackStack(null).commit();
    }

    public static void setDate(TextInputLayout dateView){

        DatePickerDialog.OnDateSetListener onDateSetListener = (view, year, month, dayOfMonth) -> {
            int mMonth = month + 1;
            String mDate = dayOfMonth + "-" + mMonth + "-" + year;
            Objects.requireNonNull(dateView.getEditText()).setText(mDate);
        };


        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(dateView.getContext(), R.style.Theme_MaterialComponents_Dialog_MinWidth, onDateSetListener, year, month, day);
        Objects.requireNonNull(datePickerDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    public static void setTime(TextInputLayout timeView){
        // Get Current Time
        Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(timeView.getContext(),
                (view, hourOfDay, minute) -> timeView.getEditText().setText(hourOfDay + ":" + minute), mHour, mMinute, false);
        timePickerDialog.show();
    }

    public static void setTime(TextView timeView){
        // Get Current Time
        Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(timeView.getContext(),
                (view, hourOfDay, minute) -> {
            String amPm, time;
            if (hourOfDay<12){
                amPm = "AM";
            }else {
                amPm = "PM";
                hourOfDay -= 12;
            }
            if(hourOfDay == 0)
                hourOfDay = 12;
            if(minute<10)
                time = hourOfDay + ":0" + minute + " " + amPm;
            else time = hourOfDay + ":" + minute + " " + amPm;
            timeView.setText(time);

                }, mHour, mMinute, false);
        timePickerDialog.show();

    }

    public static void openCamera(Fragment fragment, int requestCode) {
        Dexter.withContext(fragment.getContext()).withPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                if (multiplePermissionsReport.areAllPermissionsGranted()) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fragment.startActivityForResult(intent, requestCode);
                }
                // check for permanent denial of any permission
                if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                    // show alert dialog navigating to Settings
                    showSettingsDialog(fragment);
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).withErrorListener(dexterError -> Toast.makeText(fragment.getContext(), dexterError.toString(), Toast.LENGTH_SHORT).show()).check();
    }

    public static void getFileFromStorage(Fragment fragment, int requestCode){
        Dexter.withContext(fragment.getContext()).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                switch (requestCode){
                    case CV_REQUEST_CODE:
                    case IMAGE_REQUEST_CODE:
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        fragment.startActivityForResult(intent, requestCode);
                        break;
                    case VIDEO_REQUEST_CODE:
                        Intent videoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                        fragment.startActivityForResult(videoIntent , VIDEO_REQUEST_CODE);
                        break;
                }
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                // check for permanent denial of permission
                if (permissionDeniedResponse.isPermanentlyDenied()) {
                    showSettingsDialog(fragment);
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).withErrorListener(dexterError -> {
            Toast.makeText(fragment.getContext(), dexterError.toString(), Toast.LENGTH_SHORT).show();
        }).check();
    }

    private static void showSettingsDialog(Fragment fragment) {
        AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getContext());
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", (dialog, which) -> {
            dialog.cancel();
            openSettings(fragment);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();

    }

    // navigating user to app settings
    private static void openSettings(Fragment fragment) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", fragment.getContext().getPackageName(), null);
        intent.setData(uri);
        fragment.startActivityForResult(intent, 101);
    }


    public static boolean validateField(TextInputLayout textInputLayout){
        if (TextUtils.isEmpty(textInputLayout.getEditText().getText().toString())) {
            textInputLayout.getEditText().setError("Field can't be empty");
            return false;
        }else
            textInputLayout.setError(null);
        return true;
    }

    public static boolean validateField(AppCompatAutoCompleteTextView spinner){
        if (TextUtils.isEmpty(spinner.getText().toString())) {
            spinner.setError("");
            return false;
        }else
            spinner.setError(null);
        return true;
    }

    public static void popUpEditText(TextView targetView, String title){
        AlertDialog.Builder alert = new AlertDialog.Builder(targetView.getContext());
        final EditText edittext = new EditText(targetView.getContext());
        alert.setTitle(title);

        alert.setView(edittext);

        alert.setPositiveButton("OK", (dialog, whichButton) -> {
            //What ever you want to do with the value
            if (!edittext.getText().toString().trim().equals(""))
                targetView.setText(edittext.getText().toString());
        });

        alert.setNegativeButton("CANCEL", (dialog, whichButton) -> {
            // what ever you want to do with No option.
        });

        alert.show();
    }



}
