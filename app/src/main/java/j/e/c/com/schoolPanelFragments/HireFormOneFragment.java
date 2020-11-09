package j.e.c.com.schoolPanelFragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import j.e.c.com.Others.Helper;
import j.e.c.com.R;

import static android.app.Activity.RESULT_OK;

public class HireFormOneFragment extends Fragment {

    boolean seflToogle = false;

    @BindView(R.id.agentSpinner)
    AppCompatAutoCompleteTextView agentSpinner;
    @BindView(R.id.agentSpinnerParent)
    TextInputLayout agentSpinnerParent;
    @BindView(R.id.schoolLocationSpinner)
    AppCompatAutoCompleteTextView schoolLocationSpinner;
    @BindView(R.id.self)
    TextView self;
    @BindView(R.id.licenseImage)
    ImageView licenseImage;
    @BindView(R.id.picture)
    ImageView picture;
    @BindView(R.id.applicantName)
    TextInputLayout applicantName;
    @BindView(R.id.positionInSchool)
    TextInputLayout positionInSchool;
    @BindView(R.id.contact)
    TextInputLayout contact;
    @BindView(R.id.wechat)
    TextInputLayout wechat;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_school_hire_form_one, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        agentSpinner.setAdapter(Helper.getSimpleSpinnerAdapter(R.array.agentArray, getContext()));
        schoolLocationSpinner.setAdapter(Helper.getSimpleSpinnerAdapter(R.array.workSpace, getContext()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case Helper.CAPTURE_REQUEST_CODE:
                    if (data.getData() != null)
                        picture.setImageURI(data.getData());
                    else
                        picture.setImageBitmap((Bitmap) data.getExtras().get("data"));
                    break;
                case Helper.CV_REQUEST_CODE:
                    break;
            }
        }
    }

    @OnClick({R.id.backArrow, R.id.self, R.id.licensebtn, R.id.camera, R.id.fillBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backArrow:
                getFragmentManager().popBackStack();
                break;
            case R.id.self:
                if (!seflToogle) {
                    self.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_react01));
                    self.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                    agentSpinnerParent.setEnabled(false);
                    seflToogle = !seflToogle;
                } else {
                    self.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_rect02));
                    self.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                    agentSpinnerParent.setEnabled(true);
                    seflToogle = !seflToogle;
                }
                break;
            case R.id.licensebtn:
                Helper.getFileFromStorage(this, Helper.CV_REQUEST_CODE);
                break;
            case R.id.camera:
                Helper.openCamera(this, Helper.CAPTURE_REQUEST_CODE);
                break;
            case R.id.fillBtn:
                if (validateField())
                    Helper.fragmentTransaction(this, new HireFormTwoFragment());
                break;
        }
    }

    boolean validateField() {
        boolean b = true;
        if (!seflToogle && !Helper.validateField(agentSpinner))
            b = false;
        if (!Helper.validateField(schoolLocationSpinner))
            b = false;
        if (!Helper.validateField(applicantName))
            b = false;
        if (!Helper.validateField(positionInSchool))
            b = false;
        if (!Helper.validateField(contact))
            b = false;
        if (!Helper.validateField(wechat))
            b = false;

        return b;
    }

}
