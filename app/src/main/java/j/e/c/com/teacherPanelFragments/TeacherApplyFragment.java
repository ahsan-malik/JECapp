package j.e.c.com.teacherPanelFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import j.e.c.com.R;

public class TeacherApplyFragment extends Fragment {


    @BindView(R.id.agentSpinner)
    Spinner agentSpinner;
    @BindView(R.id.nationalitySpinner)
    Spinner nationalitySpinner;
    @BindView(R.id.countrySpinner)
    Spinner countrySpinner;

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
        agentSpinner.setAdapter(getSpinnerAdapter(R.array.agentArray));
        //nationality spinner
        nationalitySpinner.setAdapter(getSpinnerAdapter(R.array.nationalityArray));
        //country spinner
        countrySpinner.setAdapter(getSpinnerAdapter(R.array.countryArray));
    }

    ArrayAdapter<CharSequence> getSpinnerAdapter(int dataArray){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), dataArray, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return  adapter;
    }
}
