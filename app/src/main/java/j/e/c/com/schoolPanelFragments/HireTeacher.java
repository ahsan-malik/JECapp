package j.e.c.com.schoolPanelFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
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

        View dateTimeView = getLayoutInflater().inflate(R.layout.date_time_dialog, null);

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        //alert.setTitle("Scheduling Interview");
        alert.setView(dateTimeView);

        //TextInputLayout dateLayout = dateTimeView.findViewById(R.id.date);
        //TextInputLayout timeLayout = dateTimeView.findViewById(R.id.time);

        //dateLayout.setOnClickListener(v -> Helper.setDate(dateLayout));
        //timeLayout.setOnClickListener(v -> Helper.setTime(timeLayout));

        alert.setPositiveButton("OK", (dialog, whichButton) -> {
            //What ever you want to do with the value
            //if (!edittext.getText().toString().trim().equals(""))
            //  targetView.setText(edittext.getText().toString());
        });

        alert.setNegativeButton("CANCEL", (dialog, whichButton) -> {
            // what ever you want to do with No option.
        });

        alert.show();
    }

}
