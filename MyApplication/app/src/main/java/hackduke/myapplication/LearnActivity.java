package hackduke.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.FileOutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class LearnActivity extends AppCompatActivity{
    public static final String PREFS_NAME = "MyPrefsFile";

    protected static final int RESULT_SPEECH = 1;
    private TextView txtText, defaultText, txtCorrectness;
    private ImageButton btnSpeak;
    public String[] wordList = new String[20];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        wordList[0] = "Waterloo";
        wordList[1] = "Florida";
        wordList[2] = "learning";
        wordList[3] = "orange";
        wordList[4] = "wonderful";
        wordList[5] = "amazing";
        wordList[6] = "mystery";
        wordList[7] = "confusing";
        wordList[8] = "phrase";
        wordList[9] = "speech";
        wordList[10] = "definition";
        wordList[11] = "cartwheel";
        wordList[12] = "adventure";
        wordList[13] = "ignorance";
        wordList[14] = "dictionary";
        wordList[15] = "acute";
        wordList[16] = "monitor";
        wordList[17] = "ominous";
        wordList[18] = "phenomenon";
        wordList[19] = "twenty";
        super.onCreate(savedInstanceState);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        int counter = settings.getInt("counter",0);

        setContentView(R.layout.activity_learn);
        txtCorrectness = (TextView) findViewById(R.id.txtSpeechCorrectness);
        txtText = (TextView) findViewById(R.id.txtSpeechInput);
        defaultText = (TextView) findViewById(R.id.defaultText);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);
        String temp = wordList[counter];
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("default", temp);
        editor.commit();
        defaultText.setText(wordList[counter]);

        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(
                        RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

                try {
                    startActivityForResult(intent, RESULT_SPEECH);
                    txtText.setText("");

                } catch (ActivityNotFoundException a) {
                    Toast t = Toast.makeText(getApplicationContext(),
                            "Opps! Your device doesn't support Speech to Text",
                            Toast.LENGTH_SHORT);
                    t.show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String correctness;

        switch (requestCode) {
            case RESULT_SPEECH: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    txtText.setText(text.get(0));
                    String test = text.get(0);
                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                    int counter = settings.getInt("counter", 2);
                    String temp = settings.getString("default", "");
                    SharedPreferences.Editor editor = settings.edit();
                    //check for if the strings are equal
                     if(test.equals(temp)){counter++;
                     correctness = "That is a correct pronunciation!";
                     }
                    else{correctness = "That is an incorrect pronunciation. Try again";}
                    editor.putInt("counter", counter);
                    editor.commit();
                    txtCorrectness.setText(correctness);

                }
                break;
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.home:
                Intent i = new Intent(this, MainMenuActivity.class);
                startActivity(i);
                return true;
            case R.id.refresh:
                Intent i2 = new Intent(this, LearnActivity.class);
                startActivity(i2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

