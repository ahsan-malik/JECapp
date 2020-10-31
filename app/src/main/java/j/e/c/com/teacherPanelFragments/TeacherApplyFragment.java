package j.e.c.com.teacherPanelFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import j.e.c.com.Others.Helper;
import j.e.c.com.R;

public class TeacherApplyFragment extends Fragment {

    boolean seflToogle = false;


    @BindView(R.id.agentSpinner)
    AppCompatAutoCompleteTextView agentSpinner;
    @BindView(R.id.countrySpinner)
    CountryCodePicker countrySpinner;
    @BindView(R.id.email)
    TextInputLayout email;
    @BindView(R.id.contact)
    TextInputLayout contact;
    @BindView(R.id.wechat)
    TextInputLayout wechat;
    @BindView(R.id.camera)
    ImageView camera;
    @BindView(R.id.picture)
    ImageView picture;
    @BindView(R.id.uploadCVbtn)
    Button uploadCVbtn;
    @BindView(R.id.uploadVideoBtn)
    Button uploadVideoBtn;
    @BindView(R.id.fillBtn)
    Button fillBtn;
    @BindView(R.id.self)
    TextView self;

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

        agentSpinner.setAdapter(Helper.getSimpleSpinnerAdapter(R.array.agentArray, getContext()));
    }

    @OnClick(R.id.fillBtn)
    public void onViewClicked() {
        FragmentTransaction transaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new JobFormFragment()).addToBackStack(null).commit();
    }

    @SuppressLint("ResourceAsColor")
    @OnClick({R.id.backArrow, R.id.self})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backArrow:
                getFragmentManager().popBackStack();
                break;
            case R.id.self:
                if (!seflToogle) {
                    self.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_react01));
                    self.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                    agentSpinner.setEnabled(false);
                    seflToogle = !seflToogle;
                }else {
                    self.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_rect02));
                    self.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                    agentSpinner.setEnabled(true);
                    seflToogle = !seflToogle;
                }
                break;
        }
    }
}
