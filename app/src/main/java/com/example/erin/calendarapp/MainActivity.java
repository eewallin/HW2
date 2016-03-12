package com.example.erin.calendarapp;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.provider.CalendarContract.*;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements EventFragment.OnDataPass {

    public static final String[] EVENT_PROJECTION = new String[]{
            Calendars._ID,                           // 0
            Calendars.ACCOUNT_NAME,                  // 1
            Calendars.CALENDAR_DISPLAY_NAME,         // 2
            Calendars.OWNER_ACCOUNT                  // 3
    };
    private static final int PROJECTION_ID_INDEX = 0;
    private static final int PROJECTION_ACCOUNT_NAME_INDEX = 1;
    private static final int PROJECTION_DISPLAY_NAME_INDEX = 2;
    private static final int PROJECTION_OWNER_ACCOUNT_INDEX = 3;

    private String id = Integer.toString(R.id.calendarView);
    private CalendarView cal;
    private Button addEvent;
    private ListView eventsView;
    private ArrayAdapter<String> adapter;
    private ArrayList<Event> allEvents;
    private ArrayList<String> eventsForDay;

    private int yearHolder;
    private int monthHolder;
    private int dayHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cal = (CalendarView)findViewById(R.id.calendarView);
        addEvent = (Button)findViewById(R.id.addEvent);
        eventsView = (ListView)findViewById(R.id.listView);

        //allEvents = new ArrayList<Event>();
        allEvents = testEvents();

        eventsForDay = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, eventsForDay);

        eventsView.setAdapter(adapter);

        //Date date = Calendar.getInstance().getTime();
        //yearHolder = date.getYear();
        //monthHolder = date.getMonth();
        //dayHolder = date.getDay();

        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                //Toast.makeText(getApplicationContext(), "" + dayOfMonth, Toast.LENGTH_SHORT).show();
                yearHolder = year;
                monthHolder = month;
                dayHolder = dayOfMonth;

                eventsForDay.clear();
                eventsForDay.addAll(getEventsForDay(year, month, dayOfMonth));
                adapter.notifyDataSetChanged();
            }
        });



        ContentResolver resolver = getContentResolver();
        Uri uri = Calendars.CONTENT_URI;
        Cursor cursor = null;

        String selection = "(" + Calendars._ID + " = ?)";
        String[] selectionArgs = new String[]{id};

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        cursor = resolver.query(uri, EVENT_PROJECTION, selection, selectionArgs, null);




            /*while (cursor.moveToNext()) {
                long calID = 0;
                String displayName = null;
                String accountName = null;
                String ownerName = null;

                // Get the field values
                calID = cursor.getLong(PROJECTION_ID_INDEX);
                displayName = cursor.getString(PROJECTION_DISPLAY_NAME_INDEX);
                accountName = cursor.getString(PROJECTION_ACCOUNT_NAME_INDEX);
                ownerName = cursor.getString(PROJECTION_OWNER_ACCOUNT_INDEX);

                // put values into view
            }*/

        /*Calendar beginTime = Calendar.getInstance();
        beginTime.set(2012, 0, 19, 7, 30);
        Calendar endTime = Calendar.getInstance();
        endTime.set(2012, 0, 19, 8, 30);
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                .putExtra(Events.TITLE, "Yoga")
                .putExtra(Events.DESCRIPTION, "Group class")
                .putExtra(Events.EVENT_LOCATION, "The gym")
                .putExtra(Events.AVAILABILITY, Events.AVAILABILITY_BUSY)
                .putExtra(Intent.EXTRA_EMAIL, "rowan@example.com,trevor@example.com");
        startActivity(intent);
        */
    }

    private ArrayList<String> getEventsForDay(int year, int month, int dayOfMonth) {
        ArrayList<String> events = new ArrayList();

        for (Event event : allEvents) {
            if ( (event.getDay() == dayOfMonth) && (event.getMonth() == month) &&( event.getYear() == year) ) {
                events.add(event.getName() + ",  " + event.getStartTime() + " - " + event.getEndTime());
            }
        }
        return events;
    }

    public void addEvent(View view) {
        //Toast.makeText(getApplicationContext(), "ADD ADD ADD", Toast.LENGTH_SHORT).show();

        //Intent intent = new Intent(this, EventActivity.class);
        //startActivity(intent);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment_container, EventFragment.newInstance(yearHolder,monthHolder,dayHolder))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onDataPass(Event data) {
        allEvents.add(data);
        eventsForDay.add(data.getName() + ",  " + data.getStartTime() + " - " + data.getEndTime());
        adapter.notifyDataSetChanged();
        Toast.makeText(getApplicationContext(), "\"" + data.getName() + "\" added!", Toast.LENGTH_SHORT).show();
    }


    private ArrayList<Event> testEvents() {
        ArrayList<Event> events = new ArrayList<Event>();
        for (int i = 0; i < 20; i++) {
            events.add(new Event(2016, 2, i, "name", "0:00", "0:00"));
        }
        return events;
    }

}
