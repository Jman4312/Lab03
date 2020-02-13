package com.lowejimmy.lab03;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;

public class MainActivity extends AppCompatActivity {
    Button submitButton;
    EditText responseText, response2Text;
    TextView displayText, displayText2;
    Timer timer = new Timer();
    SharedPreferences mPreferences;
    int duration = 0;
    int numOfClicks = 0;
    SeekBar mySeekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initialize preferences
        mPreferences = getSharedPreferences("com.traf1.demo.sharedprefs",MODE_PRIVATE);
        //response2Text = findViewById(R.id.response2EditText);
        submitButton=findViewById(R.id.clickButton);
        responseText=findViewById(R.id.responseEditText);
        displayText=findViewById(R.id.textBox);
        displayText2=findViewById(R.id.textBox2);
        //restore preferences into submit button
        //submitButton.setText(mPreferences.getString("mResponse","defaultString")
        //        +mPreferences.getInt("mResponseNum",99));
        mySeekBar=findViewById(R.id.simpleSeekBar);
        mySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                                 public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                                                     float textSize = mySeekBar.getProgress();
                                                     TextView corner1 = findViewById(R.id.cornerBox1);
                                                     TextView corner2 = findViewById(R.id.cornerBox2);
                                                     TextView corner3 = findViewById(R.id.cornerBox3);
                                                     TextView corner4 = findViewById(R.id.cornerBox4);
                                                     corner1.setTextSize(textSize);
                                                     corner2.setTextSize(textSize);
                                                     corner3.setTextSize(textSize);
                                                     corner4.setTextSize(textSize);
                                                 }

                                                 @Override
                                                 public void onStartTrackingTouch(SeekBar seekBar) {

                                                 }

                                                 @Override
                                                 public void onStopTrackingTouch(SeekBar seekBar) {

                                                 }
                                             });
        responseText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(responseText.getText().toString().equals("TJ")){
                        displayText.setText("TJ Rocks!");
                        responseText.setText("");
                        responseText.setHint("That's a good name.");
                    }
                }
            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        //store values into preferences onPause
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putString("mResponse", responseText.getText().toString());
        // preferencesEditor.putInt("mResponseNum", Integer.parseInt(response2Text.getText().toString()));
        preferencesEditor.apply();
    }
    public void submit(View view) {//process button onClick event
    /*
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String currentTime = getString(R.string.time,duration++);
                        displayText.setText(currentTime);
                        if(duration>=5){
                            timer.cancel();
                            timer.purge();
                            timer=new Timer();
                            duration=0;
                            Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                            intent.putExtra("com.traf1.demo.extra.MESSAGE",displayText.getText().toString());
                            startActivityForResult(intent, 1);
                        }
                    }
                });
            }
        }, 1000, 1000);
    */
        String text = "";
        // ArrayList<String> texts = new ArrayList<>();
        // texts.add("Hi Mom!");
        // texts.add("Hi Dad!");
        // texts.add("Hi Person!");
        String[] texts = getResources().getStringArray(R.array.textArray);
        displayText.setText(texts[numOfClicks]);
        numOfClicks += 1;
        if (numOfClicks >= texts.length){ numOfClicks = 0;}
        //Toast.makeText(getApplicationContext(),"Done reading.",Toast.LENGTH_SHORT).show();
        displayText2.setText(responseText.getText());

        // Create New Activity
        /*
        Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
        intent.putExtra("com.lowejimmy.quizapp.extra.MESSAGE",displayText2.getText().toString());
        startActivityForResult(intent, 1);
        */
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 & resultCode==RESULT_OK){
            displayText.setText(data.getStringExtra("com.lowejimmy.quizapp.extra.REPLY"));
        }
    }

    public void textClicked(View view) {
        //do the things
        TextView cornerBox = findViewById(view.getId());
        Context context = getApplicationContext();
        SharedPreferences sharedPreferences = getSharedPreferences("com.lowejimmy.lab03", Context.MODE_PRIVATE);
        CharSequence text = cornerBox.getText() + ": " + sharedPreferences.getInt(cornerBox.getText().toString(), 0);
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        SharedPreferences.Editor preferencesEditor = sharedPreferences.edit();
        preferencesEditor.putInt(cornerBox.getText().toString(), sharedPreferences.getInt(cornerBox.getText().toString(), 0) + 1);
        // preferencesEditor.putInt("mResponseNum", Integer.parseInt(response2Text.getText().toString()));
        preferencesEditor.apply();

    }
}