package hackduke.myapplication;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class AskActivity extends AppCompatActivity {
    public static final String PREFS_NAME = "MyPrefsFile";
    protected static final int RESULT_SPEECH = 1;
    private TextView askedText, defText;
    private ImageButton btnAsk;
    public String[] wordList = new String[20];
    //HashMap dict = new HashMap();
    //dict.put(String key, String value);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        int counter = settings.getInt("counter",0);

        setContentView(R.layout.activity_ask);
        defText = (TextView) findViewById(R.id.definedText);
        askedText = (TextView) findViewById(R.id.askedText);
        btnAsk = (ImageButton) findViewById(R.id.btnAsk);

        String temp = wordList[counter];
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("Waterloo", "The best college at MLH events");
        editor.putString("Florida", "The best state in the United States");
        editor.putString("learning", "The process of gaining new knowledge");
        editor.putString("orange","The only word that has no rhymes");
        editor.putString("wonderful", "Word used to describe something very good");
        editor.putString("amazing", "Word used to describe something very very good");
        editor.putString("mystery", "Something that is difficult to understand or explain");
        editor.putString("confusing", "When something does not make any sense");
        editor.putString("phrase", "A deer, a female deer, ray a drop of golden sun");
        editor.putString("speech", "A formal form of communication");
        editor.putString("definition", "A statement of the exact meaning of a word");
        editor.putString("cartwheel", "The wheel of a cart");
        editor.putString("adventure", "Fun stuff");
        editor.putString("ignorance", "I’m not sure what to put here");
        editor.putString("dictionary", "Hashmap");
        editor.putString("acute", "Having or showing a perceptive understanding or insight");
        editor.putString("monitor", "An instrument to show the beauty of code");
        editor.putString("ominous", "An impression that something bad is going to happen");

        editor.commit();

        btnAsk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(
                        RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

                try {
                    startActivityForResult(intent, RESULT_SPEECH);
                    defText.setText("");
                    askedText.setText("");

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

                    askedText.setText(text.get(0));
                    String test = text.get(0);
                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    String temp = "Word has not been taught";
                    String temp2 = settings.getString(test, temp);
                    editor.commit();
                    defText.setText(temp2);

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
                Intent i2 = new Intent(this, AskActivity.class);
                startActivity(i2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
