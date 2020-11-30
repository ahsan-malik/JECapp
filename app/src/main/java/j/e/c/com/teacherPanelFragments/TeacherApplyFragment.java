package j.e.c.com.teacherPanelFragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
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

import com.google.android.material.textfield.TextInputLayout;
import com.gowtham.library.utils.CompressOption;
import com.gowtham.library.utils.TrimVideo;
import com.gowtham.library.utils.TrimVideoOptions;
import com.gowtham.library.utils.TrimmerUtils;
import com.iceteck.silicompressorr.SiliCompressor;


import java.net.URISyntaxException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import j.e.c.com.Others.Helper;
import j.e.c.com.R;

import static android.app.Activity.RESULT_OK;

public class TeacherApplyFragment extends Fragment {

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
    @BindView(R.id.beautyPicImage)
    ImageView beautyPicImage;
    @BindView(R.id.beautyPicTextView)
    TextView beautyPicTextView;

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
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case Helper.CAPTURE_REQUEST_CODE:
                    picture.setImageBitmap((Bitmap) data.getExtras().get("data"));
                    break;
                case Helper.IMAGE_REQUEST_CODE:
                    beautyPicImage.setImageURI(data.getData());
                    break;
                case Helper.VIDEO_REQUEST_CODE:
                    videoTextView.setVisibility(View.VISIBLE);

                   /* try {
                        String filePath = SiliCompressor.with(getContext()).compressVideo(data.getData().toString(), "/storage/emulated/0/DCIM/TESTFOLDER");
                        videoTextView.setText(filePath);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }*/

                    trimVideo(data.getData().toString());
                    break;
                case 324:
                    videoTextView.setText(TrimVideo.getTrimmedVideoPath(data));
                    break;
                case Helper.CV_REQUEST_CODE:
                    cvTextView.setVisibility(View.VISIBLE);
                    cvTextView.setText(data.getData().toString());
                    break;
            }
        }

    }


    void updateSpinners() {
        agentSpinner.setAdapter(Helper.getSimpleSpinnerAdapter(R.array.agentArray, getContext()));
    }

    @SuppressLint("ResourceAsColor")
    @OnClick({R.id.backArrow, R.id.self, R.id.camera, R.id.uploadBeautyPicBtn, R.id.uploadVideoBtn, R.id.uploadCVbtn, R.id.fillBtn})
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
                Helper.openCamera(this, Helper.CAPTURE_REQUEST_CODE);
                break;
            case R.id.uploadBeautyPicBtn:
                Helper.getFileFromStorage(this, Helper.IMAGE_REQUEST_CODE);
                break;
            case R.id.uploadVideoBtn:
                Helper.getFileFromStorage(this, Helper.VIDEO_REQUEST_CODE);
                break;
            case R.id.uploadCVbtn:
                Helper.getFileFromStorage(this, Helper.CV_REQUEST_CODE);
                break;
            case R.id.fillBtn:
                Helper.fragmentTransaction(this, new JobFormFragment());
                break;
        }
    }

    void trimVideo(String videoUri){
        int[] videoWidthHeight = TrimmerUtils.getVideoWidthHeight(getActivity(), Uri.parse(videoUri));
        //int sourceFrameRate = TrimmerUtils.getFrameRate(getActivity(), Uri.parse(videoUri));
        //int sourceBitRate = TrimmerUtils.getBitRate(getActivity(), Uri.parse(videoUri));

        //Helper.Toast(getContext(), "framerate: "+sourceFrameRate +" bitrate: "+sourceBitRate);

        TrimVideo.activity(videoUri)
                .setCompressOption(new CompressOption(30, "2M", videoWidthHeight[0], videoWidthHeight[1]))
                .setDestination("/storage/emulated/0/DCIM/TESTFOLDER")
                .start(this);
    }


}
