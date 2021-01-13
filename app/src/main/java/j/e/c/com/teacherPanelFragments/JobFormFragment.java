package j.e.c.com.teacherPanelFragments;

import android.os.Bundle;
import android.text.TextUtils;
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
import com.hbb20.CountryCodePicker;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import j.e.c.com.Others.Helper;
import j.e.c.com.R;
import j.e.c.com.commonFragments.WelcomeFragment;

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
    @BindView(R.id.visaQualified)
    AppCompatAutoCompleteTextView visaQualified;
    @BindView(R.id.countrySpinner)
    CountryCodePicker countrySpinner;
    @BindView(R.id.residence)
    TextInputLayout residence;

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
        visaQualified.setAdapter(Helper.getSimpleSpinnerAdapter(R.array.yesNoArray, getContext()));
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

        String pattern = "dd-MM-yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = new Date();
        whenCanJoin.getEditText().setText(simpleDateFormat.format(date));
    }

    @OnClick({R.id.backArrow, R.id.submitBtn, R.id.whenCanJoin})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.backArrow:
                getFragmentManager().popBackStack();
                break;
            case R.id.submitBtn:
                if (validateFields())
                    Helper.fragmentTransaction(this, new WelcomeFragment());
                break;
            case R.id.whenCanJoin:
                Helper.setDate(whenCanJoin);
                break;
        }

    }

    boolean validateFields(){
        boolean b = true;
        if(!Helper.validateField(name))
            b = false;
        if(!Helper.validateField(age))
            b = false;
        if(!Helper.validateField(nationality))
            b = false;
        if(!Helper.validateField(gender))
            b = false;
        if(!Helper.validateField(salary))
            b = false;
        if(!Helper.validateField(education))
            b = false;
        if(!Helper.validateField(graduation))
            b = false;
        if(!Helper.validateField(job))
            b = false;
        if(!Helper.validateField(visa))
            b = false;
        else if (TextUtils.equals(visa.getText().toString(), "Have")) {
            if (!Helper.validateField(visaType))
                b = false;
        }
        if(!Helper.validateField(visaQualified))
            b = false;
        if(!Helper.validateField(workPlace))
            b = false;
        if(!Helper.validateField(beenChina))
            b = false;
        else if (TextUtils.equals(beenChina.getText().toString(), "Yes")) {
            if (!Helper.validateField(yearsInChina))
                b = false;
        }
        if(!Helper.validateField(experience))
            b = false;
        if(!Helper.validateField(whenCanJoin))
            b = false;
        if(!Helper.validateField(residence))
            b = false;

        return b;
    }

}
