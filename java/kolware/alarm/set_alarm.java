package kolware.alarm;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class set_alarm extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View v= inflater.inflate(R.layout.alarm, parent, false);
        final TextView alarm_set=(TextView)v.findViewById(R.id.alarm_set);
        final ImageView cross=(ImageView)v.findViewById(R.id.imageView);
        final TextView no_alarm=(TextView)v.findViewById(R.id.textView);
        final TextView editText = (TextView)v.findViewById(R.id.editText);
        final TextView txt = (TextView)v.findViewById(R.id.textView2);
        final TextView submit_button =(TextView) v.findViewById(R.id.submitButton);
        final FloatingActionButton fab=(FloatingActionButton) v.findViewById(R.id.fab);

        final File file=new File(getContext().getFilesDir()+"/"+"alarm time.txt");
        if(file.exists()) {
            StringBuilder text = new StringBuilder();

            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;

                while ((line = br.readLine()) != null) {
                    text.append(line);
                    text.append('\n');
                }
                br.close();
            } catch (IOException e) {
            }
            cross.setVisibility(View.INVISIBLE);
            no_alarm.setVisibility(View.INVISIBLE);
            fab.hide();
            alarm_set.setText(text);
        }
        else {
            alarm_set.setVisibility(View.INVISIBLE);
            submit_button.setVisibility(View.INVISIBLE);
            editText.setVisibility(View.INVISIBLE);
            txt.setVisibility(View.INVISIBLE);
        }

        TimePickerFragment ok = new TimePickerFragment();




        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timepicker=new TimePickerFragment();
                timepicker.show(getFragmentManager(),"time picker");
            }
        });
        return v;
    }

}
