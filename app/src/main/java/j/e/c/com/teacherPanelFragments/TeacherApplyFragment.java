package j.e.c.com.teacherPanelFragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import j.e.c.com.Others.Helper;
import j.e.c.com.R;

import static android.app.Activity.RESULT_OK;

public class TeacherApplyFragment extends Fragment {

    static final int IMAGE_REQUEST_CODE = 100;
    static final int VIDEO_REQUEST_CODE = 101;
    static final int CV_REQUEST_CODE = 102;

    boolean seflToogle = false;


    @BindView(R.id.agentSpinner)
    AppCompatAutoCompleteTextView agentSpinner;
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
    @BindView(R.id.agentSpinnerParent)
    TextInputLayout agentSpinnerParent;
    @BindView(R.id.cvTextView)
    TextView cvTextView;
    @BindView(R.id.videoTextView)
    TextView videoTextView;

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            switch (requestCode) {
                case IMAGE_REQUEST_CODE:
                    picture.setImageURI(data.getData());
                    break;
                case VIDEO_REQUEST_CODE:
                    videoTextView.setVisibility(View.VISIBLE);
                    videoTextView.setText(data.getData().toString());
                    break;
                case CV_REQUEST_CODE:
                    cvTextView.setVisibility(View.VISIBLE);
                    cvTextView.setText(data.getData().toString());
                    break;
            }
        }

    }

    void updateSpinners() {
        agentSpinner.setAdapter(Helper.getSimpleSpinnerAdapter(R.array.agentArray, getContext()));
    }

    @OnClick(R.id.fillBtn)
    public void onViewClicked() {
        FragmentTransaction transaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new JobFormFragment()).addToBackStack(null).commit();
    }

    @SuppressLint("ResourceAsColor")
    @OnClick({R.id.backArrow, R.id.self, R.id.camera, R.id.uploadVideoBtn, R.id.uploadCVbtn})
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
            case R.id.camera:
                openCamera();
                break;
            case R.id.uploadVideoBtn:
                openGalleryForVideo();
                break;
            case R.id.uploadCVbtn:
                getResumeFromPhone();
                break;
        }
    }

    private void getResumeFromPhone() {
        Dexter.withContext(getContext()).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                Intent intent = new Intent();
                intent.setType("application/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select file"), CV_REQUEST_CODE);
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

            }
        }).withErrorListener(new PermissionRequestErrorListener() {
            @Override
            public void onError(DexterError dexterError) {

            }
        }).check();
    }

    private void openGalleryForVideo() {
        Dexter.withContext(getContext()).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent , VIDEO_REQUEST_CODE);
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

            }
        }).withErrorListener(new PermissionRequestErrorListener() {
            @Override
            public void onError(DexterError dexterError) {

            }
        }).check();
    }

    private void openCamera() {
        Dexter.withContext(getContext()).withPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                if (multiplePermissionsReport.areAllPermissionsGranted()) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, IMAGE_REQUEST_CODE);
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {

            }
        }).withErrorListener(dexterError -> Toast.makeText(getContext(), dexterError.toString(), Toast.LENGTH_SHORT).show()).check();
    }
}
