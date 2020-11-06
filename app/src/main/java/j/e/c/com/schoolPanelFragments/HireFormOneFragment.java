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
import androidx.fragment.app.FragmentTransaction;

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
    @BindView(R.id.demandSpinner)
    AppCompatAutoCompleteTextView demandSpinner;
    @BindView(R.id.self)
    TextView self;
    @BindView(R.id.licenseImage)
    ImageView licenseImage;
    @BindView(R.id.picture)
    ImageView picture;

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
        demandSpinner.setAdapter(Helper.getSimpleSpinnerAdapter(R.array.demandArray, getContext()));

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case Helper.IMAGE_REQUEST_CODE:
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
                Helper.openCamera(this, Helper.IMAGE_REQUEST_CODE);
                break;
            case R.id.fillBtn:
                Helper.fragmentTransaction(this, new HireFormTwoFragment());
                break;
        }
    }
}
