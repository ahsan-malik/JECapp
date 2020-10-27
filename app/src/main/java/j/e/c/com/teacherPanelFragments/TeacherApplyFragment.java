package j.e.c.com.teacherPanelFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hbb20.CountryCodePicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import j.e.c.com.Others.Helper;
import j.e.c.com.R;

public class TeacherApplyFragment extends Fragment {


    @BindView(R.id.agentSpinner)
    Spinner agentSpinner;
    @BindView(R.id.nationalitySpinner)
    Spinner nationalitySpinner;
    @BindView(R.id.countrySpinner)
    CountryCodePicker countrySpinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teacher_apply, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {

        super.onResume();
        updateSpinners();
    }

    void updateSpinners() {

        //agent spinner
        agentSpinner.setAdapter(Helper.getSimpleSpinnerAdapter(R.array.agentArray, getContext()));
        //nationality spinner
        nationalitySpinner.setAdapter(Helper.getSimpleSpinnerAdapter(R.array.nationalityArray, getContext()));
    }
}
