package hackduke.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);
        Button btnLearn = (Button) findViewById(R.id.btnLearn);
        Button btnDict = (Button) findViewById(R.id.btnDict);

        btnLearn.setOnClickListener(buttonhandler);
        btnDict.setOnClickListener(buttonhandler);
    }
    View.OnClickListener buttonhandler=new View.OnClickListener() {
        public void onClick(View v){
            switch(v.getId()){
                case R.id.btnDict:
                    Intent i2 = new Intent(v.getContext(), AskActivity.class);
                    startActivity(i2);
                    break;
                case R.id.btnLearn:
                    Intent i1 = new Intent(v.getContext(), LearnActivity.class);
                    startActivity(i1);
                    break;

            }
        }
    };

}
