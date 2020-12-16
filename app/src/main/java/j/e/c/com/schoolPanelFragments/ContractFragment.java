package j.e.c.com.schoolPanelFragments;

import android.Manifest;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import j.e.c.com.Others.Helper;
import j.e.c.com.R;
import j.e.c.com.commonFragments.PaymentFragment;

import static android.app.Activity.RESULT_OK;

public class ContractFragment extends Fragment {
    @BindView(R.id.contractImage)
    ImageView contractImage;

    View downloadContract;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contract, container, false);
        ButterKnife.bind(this, view);

        downloadContract = view.findViewById(R.id.download);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        downloadContract.setOnClickListener(v -> {
            Helper.Toast(getContext(), "downloading...");
            CopyReadAssets();
            //downloadContract();
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case Helper.IMAGE_REQUEST_CODE:
                    contractImage.setImageURI(data.getData());
                    contractImage.setTag("d");
                    break;
            }
        }
    }

    @OnClick({R.id.backArrow, R.id.upload, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backArrow:
                getFragmentManager().popBackStack();
                break;
            case R.id.upload:
                Helper.getFileFromStorage(this, Helper.IMAGE_REQUEST_CODE);
                break;
            case R.id.submit:

                if (contractImage.getTag().equals("d")) {

                    AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

                    alert.setTitle("Please Pay The Commission First!");

                    alert.setPositiveButton("PAY", (dialog, whichButton) -> {
                        //What ever you want to do with the value
                        Helper.fragmentTransaction(this, new PaymentFragment());
                    });

                    alert.setNegativeButton("CANCEL", (dialog, whichButton) -> {
                        // what ever you want to do with No option.
                    });

                    alert.show();
                }else {
                    Helper.alert("Please Upload Filled Contract First!", getContext());
                }

                break;
        }
    }

    private void downloadContract() {

        Dexter.withContext(getContext()).withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.banner1);
                contractImage.setImageBitmap(bm);

                String root = Environment.getExternalStorageDirectory().toString();
                File myDir = new File(root + "/JEC_images");
                myDir.mkdirs();

                String fname = "JECcontract"+".png";

                File file = new File(myDir, fname);
                if (file.exists()) file.delete();
                try {
                    FileOutputStream out = new FileOutputStream(file);
                    bm.compress(Bitmap.CompressFormat.PNG, 100, out);
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                if (permissionDeniedResponse.isPermanentlyDenied())
                    Helper.showSettingsDialog(ContractFragment.this);
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).withErrorListener(dexterError -> {
            Toast.makeText(getContext(), dexterError.toString(), Toast.LENGTH_SHORT).show();
        }).check();
    }

    private void CopyReadAssets() {
        AssetManager assetManager = getActivity().getAssets();
        InputStream in = null;
        OutputStream out = null;
        File file = new File(getActivity().getFilesDir(), "JECcontract.pdf");
        try
        {
            in = assetManager.open("JECcontract.pdf");
            out = getActivity().openFileOutput(file.getName(), getContext().MODE_WORLD_READABLE);

            copyFile(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e)
        {
            Log.e("tag", e.getMessage());
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(
                Uri.parse("file://" + getActivity().getFilesDir() + "/JECcontract.pdf"),
                "application/pdf");

        startActivity(intent);
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1)
        {
            out.write(buffer, 0, read);
        }
    }

}