package j.e.c.com.commonFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import j.e.c.com.R;
import j.e.c.com.schoolPanelFragments.HireTeacher;

public class WelcomeFragment extends Fragment {
    @BindView(R.id.welcomView)
    LinearLayout welcomView;
    @BindView(R.id.waitView)
    LinearLayout waitView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcom, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        String text = getActivity().getIntent().getExtras().getString("target");
        if (text.equals("1")){
            waitView.setVisibility(View.GONE);
            welcomView.setVisibility(View.VISIBLE);
        }

    }

    @OnClick({R.id.waitBtn, R.id.goBtn})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.waitBtn:
                waitView.setVisibility(View.GONE);
                welcomView.setVisibility(View.VISIBLE);
                break;
                case R.id.goBtn:
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, new HireTeacher()).addToBackStack(null).commit();
                    break;
        }

    }
}
