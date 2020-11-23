package j.e.c.com.schoolPanelFragments;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;


import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.OnClick;
import j.e.c.com.Others.Helper;
import j.e.c.com.R;

public class HireTeacher extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_school_hire_teacher, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.backArrow, R.id.filter, R.id.hireBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backArrow:
                getFragmentManager().popBackStack();
                break;
            case R.id.filter:
                break;
            case R.id.hireBtn:
                scheduleInterview();
                break;
        }
    }

    void scheduleInterview() {

        View startTimeLayout;
        TextView dayView, monthView, yearView, startTimeView;

        View dateTimeView = getLayoutInflater().inflate(R.layout.date_time_dialog, null);

        dayView = dateTimeView.findViewById(R.id.day);
        monthView = dateTimeView.findViewById(R.id.month);
        yearView = dateTimeView.findViewById(R.id.year);
        startTimeView = dateTimeView.findViewById(R.id.startTime);

        startTimeLayout = dateTimeView.findViewById(R.id.starTimeLayout);

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(dateTimeView);

        dayView.setOnClickListener(v -> setDate(dayView, monthView, yearView));
        monthView.setOnClickListener(v -> setDate(dayView, monthView, yearView));
        yearView.setOnClickListener(v -> setDate(dayView, monthView, yearView));
        startTimeLayout.setOnClickListener(v -> Helper.setTime(startTimeView));

        alert.setPositiveButton("OK", (dialog, whichButton) -> {

        });

        alert.setNegativeButton("CANCEL", (dialog, whichButton) -> {
            // what ever you want to do with No option.
        });

        alert.show();
    }

    void setDate(TextView dayView, TextView monthView, TextView yearView){

        Calendar calendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener onDateSetListener = (view, year, month, dayOfMonth) -> {
            int mMonth = month + 1;
            //String mDate = dayOfMonth + "-" + mMonth + "-" + year;
            //Objects.requireNonNull(dateView.getEditText()).setText(mDate);


            dayView.setText(String.valueOf(dayOfMonth));
            monthView.setText(theMonth(month));
            yearView.setText(String.valueOf(year));
        };

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), R.style.Theme_MaterialComponents_Dialog_MinWidth, onDateSetListener, year, month, day);
        Objects.requireNonNull(datePickerDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    String theMonth(int month){
        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return monthNames[month];
    }

}
