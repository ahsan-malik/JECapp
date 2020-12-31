package j.e.c.com.teacherPanelFragments;

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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import j.e.c.com.Models.School;
import j.e.c.com.R;

public class TeacherShowingJobsFragment extends Fragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    ArrayList<School> schoolList;

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
                ArrayList<School> schools = new ArrayList<>();
                for (School school : schoolList){
                    schools.add(school);
                }

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
                    String location = locationEditText.getText().toString().trim().toLowerCase();

                    if(nationalityGroup.getCheckedRadioButtonId() == R.id.nativeRadioBtn){
                        if (addSchoolToListByDemand(schoolArrayList, schools, "Native")){
                            schools.clear();
                            for (School school: schoolArrayList){
                                schools.add(school);
                            }
                        }
                    }
                    else if(nationalityGroup.getCheckedRadioButtonId() == R.id.nonNativeRadioBtn){
                        if(addSchoolToListByDemand(schoolArrayList, schools,"Non Native")){
                            schools.clear();
                            for (School school: schoolArrayList){
                                schools.add(school);
                            }
                        }
                    }
                    if(jobTypeGroup.getCheckedRadioButtonId() == R.id.fullTimeRadioBtn){
                        if(addSchoolToListByJobType(schoolArrayList, schools,"Full Time")){
                            schools.clear();
                            for (School school: schoolArrayList){
                                schools.add(school);
                            }
                        }
                     }
                    else if(jobTypeGroup.getCheckedRadioButtonId() == R.id.partTimeRadioBtn){
                        if(addSchoolToListByJobType(schoolArrayList, schools, "Part Time")){
                            schools.clear();
                            for (School school: schoolArrayList){
                                schools.add(school);
                            }
                        }
                    }
                    if (!location.isEmpty()){
                        if (addSchoolToListByLocation(schoolArrayList, schools, location)){
                            schools.clear();
                            for (School school: schoolArrayList){
                                schools.add(school);
                            }
                        }
                    }

                    if (!maxS.isEmpty() && !minS.isEmpty()){
                        if (addSchoolToListBySalary(schoolArrayList, schools, Integer.parseInt(minS), Integer.parseInt(maxS))){
                            schools.clear();
                            for (School school: schoolArrayList){
                                schools.add(school);
                            }
                        }
                    }
                });

                alert.setNegativeButton("cancel", ((dialog, which) -> dialog.dismiss()));

                alert.show();

                break;
        }
    }

    boolean addSchoolToListByDemand(ArrayList<School> schoolList, ArrayList<School> clonedList, String condition){
        schoolList.clear();
        boolean i = false;
        for (School school: clonedList){
            if (school.getDemand().equals(condition)) {
                schoolList.add(school);
                i = true;
            }
        }
        return i;
    }
    boolean addSchoolToListByJobType(ArrayList<School> schoolList, ArrayList<School> clonedList, String condition){
        schoolList.clear();
        boolean i = false;
        for (School school: clonedList){
            if (school.getJobTitle().equals(condition)) {
                schoolList.add(school);
                i = true;
            }
        }
        return i;
    }

    boolean addSchoolToListByLocation(ArrayList<School> schoolList, ArrayList<School> clonedList, String condition){
        schoolList.clear();
        boolean i = false;
        for (School school: clonedList){
            if (school.getSchoolLocation().toLowerCase().contains(condition)) {
                schoolList.add(school);
                i = true;
            }
        }
        return i;
    }

    boolean addSchoolToListBySalary(ArrayList<School> schoolList, ArrayList<School> clonedList, int min, int max){
        schoolList.clear();
        boolean i = false;
        for (School school: clonedList){
            int salary = Integer.parseInt(school.getSalary());
            if (salary >= min && salary <= max) {
                schoolList.add(school);
                i = true;
            }
        }
        return i;
    }
}
