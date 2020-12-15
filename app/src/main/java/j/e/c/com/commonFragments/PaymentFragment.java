package j.e.c.com.commonFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import butterknife.OnClick;
import j.e.c.com.R;

public class PaymentFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.backArrow, R.id.creditPay, R.id.wechatPay, R.id.aliPay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backArrow:
                getFragmentManager().popBackStack();
                break;
            case R.id.creditPay:
                break;
            case R.id.wechatPay:
                payPopUp(R.drawable.barcodewechat);
                break;
            case R.id.aliPay:
                payPopUp(R.drawable.barcodealipay);
                break;
        }
    }

    private void payPopUp(int imageId) {
        androidx.appcompat.app.AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

        View paymentItem = getLayoutInflater().inflate(R.layout.payment_item, null);

        ImageView imageView = paymentItem.findViewById(R.id.image);
        imageView.setImageResource(imageId);
        alert.setView(paymentItem);

        alert.setPositiveButton("OK", (dialog, whichButton) -> {
            //What ever you want to do with the value
        });

        alert.setNegativeButton("CANCEL", (dialog, whichButton) -> {
            // what ever you want to do with No option.
        });

        alert.show();
    }
}
