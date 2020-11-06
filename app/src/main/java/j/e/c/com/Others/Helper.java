package j.e.c.com.Others;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ArrayAdapter;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Objects;

import j.e.c.com.R;

public class Helper {

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

}
