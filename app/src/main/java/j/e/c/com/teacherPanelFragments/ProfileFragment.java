package j.e.c.com.teacherPanelFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.blogspot.atifsoftwares.circularimageview.CircularImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import j.e.c.com.Others.Helper;
import j.e.c.com.Others.Prefrence;
import j.e.c.com.R;
import j.e.c.com.commonFragments.RecruitmentFragment;
import j.e.c.com.commonFragments.SettingsFragment;

public class ProfileFragment extends Fragment {

    @BindView(R.id.imageView)
    CircularImageView imageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teacher_profile, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Prefrence.getProfileImage(getContext()) != null )
            imageView.setImageBitmap(Prefrence.getProfileImage(getContext()));
    }

    @OnClick({R.id.backArrow, R.id.profile, R.id.recruitment, R.id.setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backArrow:
                getFragmentManager().popBackStack();
                break;
            case R.id.profile:
                Helper.fragmentTransaction(this, new TeacherMyProfileFragment());
                break;
            case R.id.recruitment:
                Helper.fragmentTransaction(this, new RecruitmentFragment());
                break;
            case R.id.setting:
                Helper.fragmentTransaction(this, new SettingsFragment());
                break;
        }
    }
}
