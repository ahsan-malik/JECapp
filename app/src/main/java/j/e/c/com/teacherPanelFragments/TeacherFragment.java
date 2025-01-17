package j.e.c.com.teacherPanelFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import butterknife.ButterKnife;
import butterknife.OnClick;
import j.e.c.com.R;
import j.e.c.com.schoolPanelFragments.HireFormOneFragment;

public class TeacherFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teacher, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @OnClick({R.id.backArrow, R.id.applyBtn, R.id.hireBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backArrow:
                getFragmentManager().popBackStack();
                break;
            case R.id.applyBtn:
                openFragment(new TeacherApplyFragment());
                break;
            case R.id.hireBtn:
                //openFragment(new DummyListingFragment());
                openFragment(new HireFormOneFragment());
                break;
        }
    }

    void openFragment(Fragment fragment){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }

}
