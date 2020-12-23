package j.e.c.com.commonFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import butterknife.OnClick;
import j.e.c.com.Others.Helper;
import j.e.c.com.R;
import j.e.c.com.teacherPanelFragments.AppliedJobsFragment;
import j.e.c.com.teacherPanelFragments.TeacherApplyFragment;
import j.e.c.com.teacherPanelFragments.TeacherShowingJobsFragment;

public class RecApplyFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rec_apply, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.backArrow, R.id.your_application, R.id.post_application, R.id.apply_for_job, R.id.applied, R.id.schedule})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backArrow:
                getFragmentManager().popBackStack();
                break;
            case R.id.your_application:
                break;
            case R.id.post_application:
                Helper.fragmentTransaction(this, new TeacherApplyFragment());
                break;
            case R.id.apply_for_job:
                Helper.fragmentTransaction(this, new TeacherShowingJobsFragment());
                break;
            case R.id.applied:
                Helper.fragmentTransaction(this, new AppliedJobsFragment());
                break;
            case R.id.schedule:
                Helper.fragmentTransaction(this, new ScheduledInterviewsFragment());
                break;
        }
    }
}
