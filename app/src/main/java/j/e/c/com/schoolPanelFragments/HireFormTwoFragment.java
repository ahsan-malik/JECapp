package j.e.c.com.schoolPanelFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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
    @BindView(R.id.fullOrPart)
    AppCompatAutoCompleteTextView fullOrPart;
    @BindView(R.id.arrivalDate)
    TextInputLayout arrivalDate;
    @BindView(R.id.nativeOrNot)
    AppCompatAutoCompleteTextView nativeOrNot;

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
        fullOrPart.setAdapter(Helper.getSimpleSpinnerAdapter(R.array.jobArray, getContext()));
        nativeOrNot.setAdapter(Helper.getSimpleSpinnerAdapter(R.array.nationalityArray, getContext()));
    }

    @OnClick({R.id.backArrow, R.id.submitBtn, R.id.arrivalDate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backArrow:
                getFragmentManager().popBackStack();
                break;
            case R.id.submitBtn:
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new WelcomeFragment()).addToBackStack(null).commit();
                break;
            case R.id.arrivalDate:
                Helper.setDate(arrivalDate);
                break;
        }
    }
}
