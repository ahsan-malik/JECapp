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

    public static ArrayAdapter<String> getAutoCompleteAdapter(Context context){
        String[] type = new String[] {"Bed-sitter", "Single", "1- Bedroom", "2- Bedroom","3- Bedroom"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.spinner_item, type);
        return adapter;
    }

}
