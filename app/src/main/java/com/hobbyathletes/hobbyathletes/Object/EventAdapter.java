package com.hobbyathletes.hobbyathletes.Object;

        import com.hobbyathletes.hobbyathletes.Framework.Tool;
        import com.hobbyathletes.hobbyathletes.R;
        import android.app.Activity;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.Filter;
        import android.widget.Filterable;
        import android.widget.ImageView;
        import android.widget.TextView;
        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Locale;

public class EventAdapter extends BaseAdapter implements Filterable {

    private final Activity context;
    private final String[] string_myevent;
    private final int[] int_event;
    private ArrayList<HashMap<String, String>> items;

    private List<MyEventClass> myeventclass = null;
    private ArrayList<MyEventClass> arraylist;

  //  Context mContext;
   // LayoutInflater inflater;

    public EventAdapter(Activity context, List<MyEventClass> myeventclass,
                        String[] string_myevent, int[] int_event) {
        //super(context, items, R.layout.list_item_myevent, string_myevent, int_event);
        //super(context, R.layout.list_item_myevent, items);
        //http://www.learn2crack.com/2013/10/android-custom-listview-images-text-example.html
        //http://www.androidbegin.com/tutorial/android-search-listview-using-filter/

        this.context = context;
        this.string_myevent = string_myevent;
        this.int_event = int_event;
        //this.items = items;
        this.myeventclass = myeventclass;

        this.arraylist = new ArrayList<MyEventClass>();
        this.arraylist.addAll(myeventclass);
    }

    public class ViewHolder {
        TextView tv_name;
        TextView tv_date;
        ImageView iv_image;
        ImageView iv_flag;
    }

    @Override
    public int getCount() {
        return myeventclass.size();
    }

    @Override
    public MyEventClass getItem(int position) {
        return myeventclass.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        //View rowView = inflater.inflate(R.layout.list_item_myevent, null, true);

        final ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.list_item_myevent, null, true);
            holder = new ViewHolder();
            holder.tv_name = (TextView) view.findViewById(int_event[0]);
            holder.tv_date = (TextView) view.findViewById(int_event[1]);
            holder.iv_image = (ImageView) view.findViewById(int_event[2]);
            holder.iv_flag = (ImageView) view.findViewById(int_event[3]);
            view.setTag(holder);
        }  else {
            holder = (ViewHolder) view.getTag();
        }

//        System.out.println("Items position: " + items.get(position));
 //       MyEventClass currentEvent = new MyEventClass(items.get(position).get("name").toString(), items.get(position).get("location"), items.get(position).get("date")
  //                  , items.get(position).get("type"), items.get(position).get("theme"),items.get(position).get("link"), Integer.parseInt(items.get(position).get("myevents_ref")));

    //    TextView txtName = (TextView) rowView.findViewById(int_event[0]);
    //    TextView txtDate = (TextView) rowView.findViewById(int_event[1]);
    //    ImageView imageView = (ImageView) rowView.findViewById(int_event[2]);

   //     txtName.setText(currentEvent.getEvent_name());
   //     txtDate.setText(android.text.format.DateFormat.format("dd MMMM yyyy",currentEvent.getDate()));

        holder.tv_name.setText(myeventclass.get(position).getEvent_name());
        holder.tv_date.setText(android.text.format.DateFormat.format("dd MMMM yyyy", myeventclass.get(position).getDate()));

        if(myeventclass.get(position).getType().equals("triathlon")) {
            holder.iv_image.setImageResource(R.drawable.triathlon_70);
        }
        if(myeventclass.get(position).getType().equals("run")) {
            holder.iv_image.setImageResource(R.drawable.ic_run_70);
        }
        if(myeventclass.get(position).getType().equals("cycling")) {
            holder.iv_image.setImageResource(R.drawable.ic_cycle_70);
        }

        holder.iv_flag.setImageBitmap(Tool.getCountryFlag(myeventclass.get(position).getLocation().split(",")[0], context.getResources(), R.drawable.flagssmall));

        /*
        if(currentEvent.getType().equals("triathlon")) {
            imageView.setImageResource(R.drawable.triathlon_70);
        }
        if(currentEvent.getType().equals("run")) {
            imageView.setImageResource(R.drawable.ic_run_70);
        }
        if(currentEvent.getType().equals("cycling")) {
            imageView.setImageResource(R.drawable.ic_cycle_70);
        }
*/
        /*
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Send single item click data to SingleItemView Class
                Intent intent = new Intent(mContext, SingleItemView.class);
                // Pass all data rank
                intent.putExtra("rank",(myeventclass.get(position).getRank()));
                // Pass all data country
                intent.putExtra("country",(myeventclass.get(position).getCountry()));
                // Pass all data population
                intent.putExtra("population",(myeventclass.get(position).getPopulation()));
                // Pass all data flag
                // Start SingleItemView Class
                mContext.startActivity(intent);
            }
        });
*/
        return view;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        myeventclass.clear();
        if (charText.length() == 0) {
            myeventclass.addAll(arraylist);
        }
        else
        {
            for (MyEventClass wp : arraylist)
            {
                if (wp.getEvent_name().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    myeventclass.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }


    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                items = (ArrayList<HashMap<String, String>>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                ArrayList<HashMap<String, String>> FilteredList = new ArrayList<HashMap<String, String>>();
                if (constraint == null || constraint.length() == 0) {
                    // No filter implemented we return all the list
                    results.values = items;
                    results.count = items.size();

                } else {

                    for (HashMap<String, String> item : items) {
                        String data = item.get("name");

                        if (data.toLowerCase().contains(
                                constraint.toString().toLowerCase())) {
                            FilteredList.add(item);
                        }
                    }
                    results.values = FilteredList;
                    results.count = FilteredList.size();
                }

                return results;
            }
        };

        return filter;
    }

}
