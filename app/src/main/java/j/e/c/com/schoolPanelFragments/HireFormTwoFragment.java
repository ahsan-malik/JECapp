package j.e.c.com.schoolPanelFragments;

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
import j.e.c.com.commonFragments.WelcomeFragment;

public class HireFormTwoFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_school_hire_form_two, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.backArrow, R.id.submitBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backArrow:
                getFragmentManager().popBackStack();
                break;
            case R.id.submitBtn:
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new WelcomeFragment()).addToBackStack(null).commit();
                break;
        }
    }
}
