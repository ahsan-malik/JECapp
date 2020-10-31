package j.e.c.com.teacherPanelFragments;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import j.e.c.com.Others.Helper;
import j.e.c.com.R;

public class JobFormFragment extends Fragment {


    @BindView(R.id.nationality)
    AppCompatAutoCompleteTextView nationality;
    @BindView(R.id.visa)
    AppCompatAutoCompleteTextView visa;
    @BindView(R.id.visaType)
    AppCompatAutoCompleteTextView visaType;
    @BindView(R.id.visaTypeParent)
    TextInputLayout visaTypeParent;
    @BindView(R.id.gender)
    AppCompatAutoCompleteTextView gender;
    @BindView(R.id.job)
    AppCompatAutoCompleteTextView job;
    @BindView(R.id.beenChina)
    AppCompatAutoCompleteTextView beenChina;
    @BindView(R.id.experience)
    AppCompatAutoCompleteTextView experience;
    @BindView(R.id.residence)
    AppCompatAutoCompleteTextView residence;
    @BindView(R.id.backArrow)
    ImageView backArrow;
    @BindView(R.id.toolBar)
    LinearLayout toolBar;
    @BindView(R.id.name)
    TextInputLayout name;
    @BindView(R.id.salary)
    TextInputLayout salary;
    @BindView(R.id.education)
    TextInputLayout education;
    @BindView(R.id.graduation)
    TextInputLayout graduation;
    @BindView(R.id.yearsInChina)
    AppCompatAutoCompleteTextView yearsInChina;
    @BindView(R.id.yearsInChinaparent)
    TextInputLayout yearsInChinaparent;
    @BindView(R.id.submitBtn)
    Button submitBtn;
    @BindView(R.id.age)
    TextInputLayout age;
    @BindView(R.id.workPlace)
    TextInputLayout workPlace;
    @BindView(R.id.whenCanJoin)
    TextInputLayout whenCanJoin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job_form, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        nationality.setAdapter(Helper.getSimpleSpinnerAdapter(R.array.nationalityArray, getContext()));
        gender.setAdapter(Helper.getSimpleSpinnerAdapter(R.array.genderArray, getContext()));
        job.setAdapter(Helper.getSimpleSpinnerAdapter(R.array.jobArray, getContext()));
        beenChina.setAdapter(Helper.getSimpleSpinnerAdapter(R.array.yesNoArray, getContext()));
        yearsInChina.setAdapter(Helper.getSimpleSpinnerAdapter(R.array.yearsArray, getContext()));
        experience.setAdapter(Helper.getSimpleSpinnerAdapter(R.array.yearsArray, getContext()));
        visaType.setAdapter(Helper.getSimpleSpinnerAdapter(R.array.typeOfVisa, getContext()));
        residence.setAdapter(Helper.getSimpleSpinnerAdapter(R.array.residence, getContext()));
        visa.setAdapter(Helper.getSimpleSpinnerAdapter(R.array.visa, getContext()));
        visa.setOnItemClickListener((parent, view, position, id) -> {
            //Toast.makeText(getContext(), String.valueOf(position), Toast.LENGTH_LONG).show();
            if (position == 0) {
                visaTypeParent.setVisibility(View.VISIBLE);
            } else {
                visaTypeParent.setVisibility(View.GONE);
            }
        });

        beenChina.setOnItemClickListener((parent, view, position, id) -> {
            if (position == 0) {
                yearsInChinaparent.setVisibility(View.VISIBLE);
            } else {
                yearsInChinaparent.setVisibility(View.GONE);
            }
        });
        //nationality.setAdapter(Helper.getAutoCompleteAdapter(getContext()));
        //visaSpinner.setAdapter(Helper.getSimpleSpinnerAdapter(R.array.typeOfVisa, getContext()));
        //workspaceSpinner.setAdapter(Helper.getSimpleSpinnerAdapter(R.array.workSpace, getContext()));
        //residenceSpinner.setAdapter(Helper.getSimpleSpinnerAdapter(R.array.residence, getContext()));

        DatePickerDialog.OnDateSetListener onDateSetListener = (view, year, month, dayOfMonth) -> {
            int mMonth = month + 1;
            String mDate = dayOfMonth + "-" + mMonth + "-" + year;
            whenCanJoin.getEditText().setText(mDate);
        };

        String pattern = "dd-MM-yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = new Date();
        whenCanJoin.getEditText().setText(simpleDateFormat.format(date));
        whenCanJoin.getEditText().setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), R.style.Theme_MaterialComponents_Dialog_MinWidth, onDateSetListener, year, month, day);
            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePickerDialog.show();
        });





    }
}
