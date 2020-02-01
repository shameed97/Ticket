package com.example.ticket;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NameAdapter extends BaseAdapter {

    private ArrayList<name> arrayList = new ArrayList<>();
    private List<name> naName;
    private Context Mcontext;
    private LayoutInflater inflater;
    private String name, number, count, seat, place, time, date, sex, address,con_name;
    private Button button;

    public NameAdapter(Context context, List<name> naName) {
        Mcontext = context;
        this.naName = naName;
        this.arrayList.addAll(naName);
        inflater = LayoutInflater.from(Mcontext);
    }

    @Override
    public int getCount() {
        return naName.size();
    }

    @Override
    public Object getItem(int position) {
        return naName.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ProductHolder {
        TextView textViewCount, textView, textView1, textView2, textView3, textView4, textView5, textView6, textView7;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ProductHolder productHolder;
        if (convertView == null) {
            row = inflater.inflate(R.layout.booking_list, null);
            productHolder = new ProductHolder();
            productHolder.textViewCount = row.findViewById(R.id.textCount);
            productHolder.textView = row.findViewById(R.id.textName);
            productHolder.textView1 = row.findViewById(R.id.textNumber);
            productHolder.textView2 = row.findViewById(R.id.textSeat);
            productHolder.textView3 = row.findViewById(R.id.textPlace);
            productHolder.textView4 = row.findViewById(R.id.textTime);
            productHolder.textView5 = row.findViewById(R.id.textDate);
            productHolder.textView6 = row.findViewById(R.id.textSex);
            productHolder.textView7 = row.findViewById(R.id.textAdd);


            row.setTag(productHolder);
        } else {
            productHolder = (ProductHolder) row.getTag();
        }

        final name det = (name) getItem(position);

        count = det.getCount();
        name = det.getName();
        number = det.getNumber();
        seat = det.getSeat();
        place = det.getPlace();
        time = det.getTime();
        date = det.getDate();
        sex = det.getSex();
        address = det.getAddress();
        con_name=det.getConName();
        productHolder.textViewCount.setText(count);
        productHolder.textView.setText(name);
        productHolder.textView1.setText(number);
        productHolder.textView2.setText(seat);
        productHolder.textView3.setText(place);
        productHolder.textView4.setText(time);
        productHolder.textView5.setText(date);
        productHolder.textView6.setText(sex);
        productHolder.textView7.setText(address);

        button = row.findViewById(R.id.messButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(det.getNumber(), null, "Confirmation from Conductor :" + det.getConName() + "\nBUS NO : PY-7207 \nWelcome " + det.getName() + "," + "\nTicket NO : " + det.getSeat() +
                        "\nRoute :" + det.getPlace() + "\nTime :" + det.getTime() + "\nDate :" + det.getDate() + "\nTicket Booked", null, null);
                Toast.makeText(Mcontext, "SMS sent to " + det.getName(), Toast.LENGTH_LONG).show();

            }
        });

        //OnClickListener for Listview row Click
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        // End OnClickListener for Listview row Click

        return row;
    }


}
