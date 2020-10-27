package j.e.c.com.Others;

import android.content.Context;
import android.widget.ArrayAdapter;

import j.e.c.com.R;

public class Helper {

    public static ArrayAdapter<CharSequence> getSimpleSpinnerAdapter(int dataArray, Context context){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, dataArray, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

}
