package j.e.c.com.schoolPanelFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import j.e.c.com.Others.Helper;
import j.e.c.com.R;
import j.e.c.com.commonFragments.WelcomeFragment;

public class HireFormTwoFragment extends Fragment {
    @BindView(R.id.kpm)
    AppCompatAutoCompleteTextView kpm;
    @BindView(R.id.arrivalDate)
    TextInputLayout arrivalDate;
    @BindView(R.id.jobTitle)
    AppCompatAutoCompleteTextView jobTitle;
    @BindView(R.id.demand)
    AppCompatAutoCompleteTextView demand;
    @BindView(R.id.city)
    TextInputLayout city;
    @BindView(R.id.workingTime)
    TextInputLayout workingTime;
    @BindView(R.id.kidsAge)
    TextInputLayout kidsAge;
    @BindView(R.id.noOfTeachers)
    TextInputLayout noOfTeachers;
    @BindView(R.id.requirements)
    TextInputLayout requirements;
    @BindView(R.id.salary)
    TextInputLayout salary;
    @BindView(R.id.housing)
    TextInputLayout housing;
    @BindView(R.id.advantages)
    TextInputLayout advantages;
    @BindView(R.id.checkbox)
    CheckBox checkbox;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_school_hire_form_two, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        kpm.setAdapter(Helper.getSimpleSpinnerAdapter(R.array.kpmArray, getContext()));
        jobTitle.setAdapter(Helper.getSimpleSpinnerAdapter(R.array.jobArray, getContext()));
        demand.setAdapter(Helper.getSimpleSpinnerAdapter(R.array.demandArray, getContext()));
    }

    @OnClick({R.id.backArrow, R.id.submitBtn, R.id.arrivalDate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backArrow:
                getFragmentManager().popBackStack();
                break;
            case R.id.submitBtn:
                //if (validateFields())
                    Helper.fragmentTransaction(this, new WelcomeFragment());
                break;
            case R.id.arrivalDate:
                Helper.setDate(arrivalDate);
                break;
        }
    }

    boolean validateFields(){
        boolean b = true;
        if(!Helper.validateField(city))
            b = false;
        if(!Helper.validateField(jobTitle))
            b = false;
        if(!Helper.validateField(workingTime))
            b = false;
        if(!Helper.validateField(kidsAge))
            b = false;
        if(!Helper.validateField(kpm))
            b = false;
        if(!Helper.validateField(arrivalDate))
            b = false;
        if(!Helper.validateField(demand))
            b = false;
        if(!Helper.validateField(noOfTeachers))
            b = false;
        if(!Helper.validateField(requirements))
            b = false;
        if(!Helper.validateField(salary))
            b = false;
        if(!Helper.validateField(housing))
            b = false;
        if(!Helper.validateField(advantages))
            b = false;
        if (!checkbox.isChecked()){
            checkbox.setError("You need to agree with us");
            b = false;
        }else checkbox.setError(null);
        return b;
    }

}
