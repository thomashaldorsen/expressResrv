package com.expressResrv;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Calendar;


//vi implimenterer OnClickListener slik at vi slipper og opprette OnClickListner pr knapp.

public class TimeActivity extends AppCompatActivity implements View.OnClickListener {
    TextView minCounter;
    TextView hourCounter;
    Button button15min;
    Button button30min;
    Button button60min;
    Button buttonBook;
    Button buttonReset;



    //Tellere for min og t.
    int counterMIN = 0;
    int counterHOUR = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        buttonBook = findViewById(R.id.buttonBook);
        minCounter = findViewById(R.id.minCounter);
        hourCounter = findViewById(R.id.hourCounter);
        button15min = findViewById(R.id.button15min);
        button30min = findViewById(R.id.button30min);
        button60min = findViewById(R.id.button60min);
        buttonReset = findViewById(R.id.buttonReset);


        minCounter.setText("0");
        hourCounter.setText("0");

    }


    //OnClick
    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.button15min:
                //Øker med 15 min så lenge det counerMIN er over 45.
                if (counterMIN < 45) {
                    counterMIN = counterMIN + 15;
                    minCounter.setText(String.valueOf(counterMIN));
                }
                else {
                    //Counter på 45, øker med 1time.
                    counterMIN = 0;
                    counterHOUR = counterHOUR + 1;
                    hourCounter.setText(String.valueOf(counterHOUR));
                    minCounter.setText(String.valueOf(counterMIN));
                }
                break;

            case R.id.button30min:
                if (counterMIN == 45) {
                    //Counter på 45, øker med en time og 15 min i display
                    counterMIN = 15;
                    counterHOUR = counterHOUR + 1;
                    hourCounter.setText(String.valueOf(counterHOUR));
                    minCounter.setText(String.valueOf(counterMIN));

                }
                //Counter på 30, legger til hel time
                else if (counterMIN == 30) {
                    counterMIN = 0;
                    counterHOUR = counterHOUR + 1;
                    hourCounter.setText(String.valueOf(counterHOUR));
                    minCounter.setText(String.valueOf(counterMIN));
                }
                else {
                    //Legger til 30 min.
                    counterMIN = counterMIN + 30;
                    minCounter.setText(String.valueOf(counterMIN));
                }
                break;

            case R.id.button60min:
                //Øker med hel time
                counterHOUR = counterHOUR + 1;
                hourCounter.setText(String.valueOf(counterHOUR));

                break;

            case R.id.buttonBook:
                //Sendes til openRecpitActivity2.
                openRecpitActivity();
                break;

            case R.id.buttonReset:
                //Sendes til resetTimeValues for reset av minutt og timer.
                resetTimeValues();
                break;

        }

    }
    public void openRecpitActivity() {
        //Tar minutt og time og legger til tidspunkt nå.
        Calendar bookingTime = Calendar.getInstance();
        bookingTime.add(Calendar.MINUTE, counterMIN);
        bookingTime.add(Calendar.HOUR_OF_DAY, counterHOUR);

        //Verdi skal sendes med intent til openRecpitActivity2


        System.out.println(bookingTime.getTime());
        Intent intent = new Intent(this, ReciptActivity.class);

        startActivity(intent);
    }

    public void resetTimeValues() {
        //Tilbakestiller minutter og timer.
        counterHOUR = 0;
        counterMIN = 0;
        hourCounter.setText(String.valueOf(counterHOUR));
        minCounter.setText(String.valueOf(counterMIN));
    }


}
