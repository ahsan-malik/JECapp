package j.e.c.com.teacherPanelFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import j.e.c.com.Others.Helper;
import j.e.c.com.R;

public class JobFormFragment extends Fragment {
    @BindView(R.id.visaSpinner)
    Spinner visaSpinner;
    @BindView(R.id.workspaceSpinner)
    Spinner workspaceSpinner;
    @BindView(R.id.residenceSpinner)
    Spinner residenceSpinner;

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

        visaSpinner.setAdapter(Helper.getSimpleSpinnerAdapter(R.array.typeOfVisa, getContext()));
        workspaceSpinner.setAdapter(Helper.getSimpleSpinnerAdapter(R.array.workSpace, getContext()));
        residenceSpinner.setAdapter(Helper.getSimpleSpinnerAdapter(R.array.residence, getContext()));
    }
}
