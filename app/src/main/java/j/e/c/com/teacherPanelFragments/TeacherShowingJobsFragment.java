package j.e.c.com.teacherPanelFragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import j.e.c.com.Models.School;
import j.e.c.com.Others.Helper;
import j.e.c.com.R;

public class TeacherShowingJobsFragment extends Fragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    ArrayList<School> schools;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teacher_showing_jobs, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @OnClick({R.id.backArrow, R.id.filter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backArrow:
                getFragmentManager().popBackStack();
                break;
            case R.id.filter:

                View filterView = getLayoutInflater().inflate(R.layout.pop_search_filter, null);

                TextView clearAll = filterView.findViewById(R.id.clearAll);
                RadioGroup nationalityGroup = filterView.findViewById(R.id.nationalityRadioGroup);
                RadioGroup jobTypeGroup = filterView.findViewById(R.id.jobTypeRadioGroup);
                EditText minSalary = filterView.findViewById(R.id.salaryMin);
                EditText maxSalary = filterView.findViewById(R.id.salaryMax);
                EditText locationEditText = filterView.findViewById(R.id.location);

                clearAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nationalityGroup.clearCheck();
                        jobTypeGroup.clearCheck();
                        minSalary.setText(null);
                        maxSalary.setText(null);
                        locationEditText.setText(null);
                    }
                });

                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

                alert.setView(filterView);

                alert.setPositiveButton("SEARCH", (dialog, which) -> {

                    ArrayList<School> schoolArrayList = new ArrayList<>();

                    String minS = minSalary.getText().toString().trim();
                    String maxS = maxSalary.getText().toString().trim();
                    String location = locationEditText.getText().toString().trim();

                    if(nationalityGroup.getCheckedRadioButtonId() == R.id.nativeRadioBtn){
                        addSchoolToList(schoolArrayList, "Native");
                    }
                    else if(nationalityGroup.getCheckedRadioButtonId() == R.id.nonNativeRadioBtn){
                        addSchoolToList(schoolArrayList, "Non Native");
                    }
                    if(jobTypeGroup.getCheckedRadioButtonId() == R.id.fullTimeRadioBtn){
                        addSchoolToList(schoolArrayList, "Full Time");

                     }
                    else if(jobTypeGroup.getCheckedRadioButtonId() == R.id.partTimeRadioBtn){
                        addSchoolToList(schoolArrayList, "Part Time");
                    }
                    if (!location.isEmpty()){

                    }
                    if (!minS.isEmpty()){

                    }
                    if (!maxS.isEmpty()){

                    }
                });

                alert.setNegativeButton("cancel", ((dialog, which) -> dialog.dismiss()));

                alert.show();

                break;
        }
    }

    boolean addSchoolToList(ArrayList<School> schoolList, String condition){
        boolean i = false;
        for (School school: schools){
            if (school.getDemand().equals(condition)) {
                schoolList.add(school);
                i = true;
            }
        }
        return i;
    }
}
