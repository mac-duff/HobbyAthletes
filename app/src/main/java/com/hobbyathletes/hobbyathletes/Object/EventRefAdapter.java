package com.hobbyathletes.hobbyathletes.Object;

import android.app.Activity;
import android.text.TextWatcher;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.hobbyathletes.hobbyathletes.MyEvent.myeventref;
import com.hobbyathletes.hobbyathletes.R;

import java.util.List;


public class EventRefAdapter extends ArrayAdapter<myeventref.MyEventRefRow> {

    private final Activity context;
    //private final String[] string_myeventref;
    //private final String[] string_myeventref_value;
    private int layoutResourceId;
    private List<myeventref.MyEventRefRow> elements;

    public EventRefAdapter(Activity context, int layoutResourceId, List<myeventref.MyEventRefRow> elements) {
        super(context, layoutResourceId, elements);

        //http://www.learn2crack.com/2013/10/android-custom-listview-images-text-example.html

        this.context = context;
        this.layoutResourceId = layoutResourceId;

        this.elements = elements;

        //this.string_myeventref = string_myeventref;
        //this.string_myeventref_value = string_myeventref_value;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        //LayoutInflater inflater = context.getLayoutInflater();
        //View rowView = inflater.inflate(R.layout.list_item_myeventref, null, true);
        //TextView txtname = (TextView) rowView.findViewById(R.id.textView_myeventref_name);
        //EditText edtxtvalue = (EditText) rowView.findViewById(R.id.editText_myeventref_value);

        //txtname.setText(string_myeventref[position]);
        //edtxtvalue.setText(string_myeventref_value[position]);

        View rowView = view;
        ViewHolder holder = null;
        final myeventref.MyEventRefRow item = elements.get(position);

        //final ViewHolder holder;
        if (rowView == null) {

            //LayoutInflater inflater = context.getLayoutInflater();
            rowView = context.getLayoutInflater().inflate(layoutResourceId, null, true);

            holder = new ViewHolder();
            holder.txtname = (TextView) rowView.findViewById(R.id.textView_myeventref_name);
            holder.edtxtvalue = (EditText) rowView.findViewById(R.id.editText_myeventref_value);

            rowView.setTag(holder);

        } else {

            holder = (ViewHolder) rowView.getTag();
        }

        if (holder.textWatcher != null) {
            holder.edtxtvalue.removeTextChangedListener(holder.textWatcher);
        }

        holder.textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                item.setValue(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        holder.edtxtvalue.addTextChangedListener(holder.textWatcher);

        holder.txtname.setText(item.getName());
        holder.edtxtvalue.setText(item.getValue());

        return rowView;
    }

    private class ViewHolder {
        TextView txtname;
        EditText edtxtvalue;
        public TextWatcher textWatcher;
    }
}
