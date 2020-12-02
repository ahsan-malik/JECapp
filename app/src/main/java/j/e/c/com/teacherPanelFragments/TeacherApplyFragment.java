package j.e.c.com.teacherPanelFragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.abedelazizshe.lightcompressorlibrary.CompressionListener;
import com.abedelazizshe.lightcompressorlibrary.VideoCompressor;
import com.abedelazizshe.lightcompressorlibrary.VideoQuality;
import com.google.android.material.textfield.TextInputLayout;
import com.gowtham.library.utils.CompressOption;
import com.gowtham.library.utils.TrimVideo;
import com.gowtham.library.utils.TrimmerUtils;

import org.jetbrains.annotations.NotNull;


import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import j.e.c.com.Others.FileUtils;
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

                    String path = FileUtils.getPath(data.getData(), getContext());

                    videoTextView.setText(path);
                    //Helper.Toast(getContext(), path);
                    //compressVideo(path);


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

    void compressVideo(String path){

        File desFile = saveVideoFile(path);

        VideoCompressor.start(path, desFile.getPath(), new CompressionListener() {
            @Override
            public void onStart() {
                Helper.Toast(getContext(), "start");
            }

            @Override
            public void onSuccess() {
                Helper.Toast(getContext(), "success "+desFile.length());
            }

            @Override
            public void onFailure(@NotNull String s) {
                Log.d("compressFail", s);
                Helper.Toast(getContext(), s);
            }

            @Override
            public void onProgress(float v) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Log.d("compressingVideo", ""+v);
                        Helper.Toast(getContext(), ""+v);
                    }
                });
            }

            @Override
            public void onCancelled() {
                Log.d("compressingVideoca", "cancel");
            }
        }, VideoQuality.MEDIUM, false, false);
    }

    private File saveVideoFile(String path) {
        File videoFile = new File(path);

        String videoFileName = System.currentTimeMillis() + videoFile.getName()+".mp4";
        File downloadsPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File desFile = new File(downloadsPath, videoFileName);

        if (desFile.exists())
            desFile.delete();

        try {
            desFile.createNewFile();
        } catch (IOException e) {
            Helper.Toast(getContext(), e.toString());
            e.printStackTrace();
        }

        return desFile;
    }

    void trimVideo(String videoUri){
        int[] videoWidthHeight = TrimmerUtils.getVideoWidthHeight(getActivity(), Uri.parse(videoUri));
        int sourceFrameRate = TrimmerUtils.getFrameRate(getActivity(), Uri.parse(videoUri));
        int sourceBitRate = TrimmerUtils.getBitRate(getActivity(), Uri.parse(videoUri));

        int frameRate = 30;
        int bitRate, width, height;
        double multiplyFactor;
        String bitRateString = "";


        if (sourceFrameRate == 30)
            frameRate = 24;
        if (sourceFrameRate<30)
            frameRate = sourceFrameRate;

        bitRate = sourceBitRate/(1000*1000);

        if (bitRate <= 1)
            bitRateString = "800K";
        else if (bitRate < 2 && bitRate > 1)
            bitRateString = "1M";
        else if (bitRate <= 4 )
            bitRateString = "2M";
        else if (bitRate>4)
            bitRateString = bitRate/2+"M";

        /*if (videoWidthHeight[0] >= 1920 || videoWidthHeight[1] >= 1920)
            multiplyFactor = 0.5;
        if (videoWidthHeight[0] >= 1280 || videoWidthHeight[1] >= 1280)
            multiplyFactor = 0.75;
        if (videoWidthHeight[0] >= 960 || videoWidthHeight[1] >= 960)
            multiplyFactor = 0.95;
        else multiplyFactor = 0.9;*/

        multiplyFactor = 0.5;
        if (videoWidthHeight[0] <= 360 || videoWidthHeight[1] <= 360) {
            multiplyFactor = 0.95;
        }

        width = (int)((videoWidthHeight[0] * multiplyFactor) / 16f) * 16;
        height = (int)((videoWidthHeight[1] * multiplyFactor) / 16f) * 16;

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        TextView edittext = new TextView(getContext());

        edittext.setText("frameRates: " + sourceFrameRate
                + " bitrate: " + sourceBitRate
                + "\n width: " + videoWidthHeight[0]
                + "\n height: " + videoWidthHeight[1]
                + "\n\n frameRate: " + frameRate
                + " bitrate: " + bitRate + " " + bitRateString
                + "\n width: " + width
                + "\n height: " + height
        );

        alert.setView(edittext);

        int finalFrameRate = frameRate;
        String finalBitRateString = bitRateString;
        alert.setPositiveButton("OK", (dialog, whichButton) -> {
            //What ever you want to do with the value
            TrimVideo.activity(videoUri)
                    .setCompressOption(new CompressOption(finalFrameRate, finalBitRateString, width, height))
                    .setDestination("/storage/emulated/0/DCIM/TESTFOLDER")
                    .start(this);
        });

        alert.setNegativeButton("CANCEL", (dialog, whichButton) -> {
            // what ever you want to do with No option.
        });

        alert.show();

        //Helper.Toast(getContext(), "framerate: "+sourceFrameRate +" bitrate: "+sourceBitRate);
    }
}
