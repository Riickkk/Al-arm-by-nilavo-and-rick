package kolware.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MenuActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    private DrawerLayout drawer;
    NavigationView navigationView;
    private static final String FILE_NAME="alarm time.txt";
    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        navigationView=(NavigationView)findViewById(R.id.nav_view);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        analog_clock_frag fragment = new analog_clock_frag();
        fragmentTransaction.replace(R.id.fragment_container,fragment,"analog clock");
        fragmentTransaction.commit();


        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.dark_neon));
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        setnavdrawer();
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else
        {
            android.support.v4.app.FragmentManager fm=getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction ft=fm.beginTransaction();
            analog_clock_frag f=(analog_clock_frag)getSupportFragmentManager().findFragmentByTag("analog clock");
            if (f != null && f.isVisible()) {
                super.onBackPressed();
            }
            else
            {

                analog_clock_frag obj=new analog_clock_frag();
                ft.replace(R.id.fragment_container,obj,"analog clock");
                ft.commit();

            }
        }
    }

    private void setnavdrawer() {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                android.support.v4.app.FragmentManager fragmentManager=getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                if (id == R.id.go_set_alarm){
                    set_alarm fragment=new set_alarm();
                    fragmentTransaction.replace(R.id.fragment_container,fragment,"set alarm");
                    fragmentTransaction.commit();

                } else if (id == R.id.go_select_apps) {
                    SelectAppFragment fragment = new SelectAppFragment();
                    fragmentTransaction.replace(R.id.fragment_container,fragment,"select apps");
                    fragmentTransaction.commit();

                } else if (id == R.id.go_settings) {
                    SettingFragment fragment = new SettingFragment();
                    fragmentTransaction.replace(R.id.fragment_container,fragment , "settings");
                    fragmentTransaction.commit();

                } else if (id == R.id.go_contact) {

                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });


    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        String min="";
        if(minute<10)
        {
            min="0"+minute;
        }
        else { min=Integer.toString(minute);}
        int timeleft_alarm = 0;
        String time=hour+":"+min;
        Log.i("ok","ok");
       


        FileOutputStream fos=null;
        try{
            fos=openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
        }catch (FileNotFoundException e) {
        }
        try {
            fos.write(time.getBytes());
            fos.close();
        }catch (IOException e){}



        ImageView cross=findViewById(R.id.imageView);
        TextView no_alarm=findViewById(R.id.textView);
        cross.setVisibility(View.INVISIBLE);
        no_alarm.setVisibility(View.INVISIBLE);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.hide();
        TextView alarm_set=findViewById(R.id.alarm_set);
        alarm_set.setVisibility(View.VISIBLE);
        TextView editText = (TextView)findViewById(R.id.editText);
        TextView txt = (TextView)findViewById(R.id.textView2);
        TextView submit_button =(TextView)findViewById(R.id.submitButton);

        submit_button.setVisibility(View.VISIBLE);
        editText.setVisibility(View.VISIBLE);
        txt.setVisibility(View.VISIBLE);

        alarm_set.setText(hour+":"+min);
        int totalMinutes;
        totalMinutes = hour*60 + minute;
        if (android.os.Build.VERSION.SDK_INT >= 24){
            Calendar c = Calendar.getInstance();
            int currentHour = c.get(Calendar.HOUR_OF_DAY);
            int currentMinute = c.get(Calendar.MINUTE);
            int diffHour = 0, diffMinute = 0;
            int currentTotalMinutes = currentHour*60 + currentMinute;
            int diff = totalMinutes - currentTotalMinutes;
            if(diff < 0) {
                diffHour = 24 - Math.abs(diff/60);
                diffMinute =  Math.abs(diff%60);
            }
            else if(diff > 0){
                diffHour = Math.abs(diff/60);
                diffMinute = Math.abs(diff%60);
            }
            else if(diff == 0){
                diffHour = 0;
                diffMinute = 0;
            }

            Toast.makeText(this, "Al arm set for " + diffHour + " hr" + diffMinute + " min",
                    Toast.LENGTH_LONG).show();
            timeleft_alarm=(diffHour*60*60)+(diffMinute*60);
        }

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Log.i("hour",Integer.toString(timeleft_alarm));
        Intent myIntent=new Intent(MenuActivity.this,AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),0,myIntent,0);
        alarmManager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+timeleft_alarm*1000,pendingIntent);
        deleteFile("alarm time.txt");
    }


}
