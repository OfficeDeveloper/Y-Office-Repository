package leshencko.easymath;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity  implements View.OnClickListener {

    TextView A;
    TextView B;
    TextView ans1;
    TextView ans2;
    TextView ans3;
    Button start;
    Button info;

    int a = 0;
    int b = 0;
    int c = 0;
    int X;
    int Y;


    public void setTask(){
        X = (int) (1 + Math.random()*10);
        Y = (int) (1 + Math.random()*10);

        A.setText(String.valueOf(X));
        B.setText(String.valueOf(Y));

        int i = (int) ( 1 + Math.random()*3);
        switch (i){
            case 1:
                a = X + Y;
                b = a + 3;
                c = a - 3;
                break;
            case 2:
                b = X + Y;
                a = b + 3;
                c = b - 3;
                break;
            case 3:
                c = X + Y;
                a = c + 3;
                b = c - 3;
                break;
            default: break;
        }

        ans1.setText(String.valueOf(a));
        ans2.setText(String.valueOf(b));
        ans3.setText(String.valueOf(c));

        ans1.setEnabled(true);
        ans2.setEnabled(true);
        ans3.setEnabled(true);
    }

    public void checkTask(int id){
        TextView answer = (TextView) findViewById(id);
        int ans = Integer.parseInt(answer.getText().toString());
        if (ans == X+Y) {
            showAlert("Success!","This is right answer!","Next");
        } else {
            showAlert("Fail!","This is not right answer!","Next");
        }

    }

    public void showAlert(String title, String message, String button){
        AlertDialog.Builder looseAlert = new AlertDialog.Builder(MainActivity.this);
        looseAlert.setTitle(title)
                .setMessage(message)
                .setIcon(R.drawable.ic_launcher)
                .setCancelable(false)
                .setNegativeButton(button,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                setTask();
                            }
                        }
                );
        AlertDialog alert = looseAlert.create();
        alert.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        A = (TextView) (findViewById(R.id.A));
        B = (TextView) (findViewById(R.id.B));

        ans1 = (TextView) (findViewById(R.id.ans1));
        ans1.setOnClickListener(this);

        ans2 = (TextView) (findViewById(R.id.ans2));
        ans2.setOnClickListener(this);

        ans3 = (TextView) (findViewById(R.id.ans3));
        ans3.setOnClickListener(this);

        start = (Button) (findViewById(R.id.button));
        start.setOnClickListener(this);

        info = (Button) (findViewById(R.id.button3));
        info.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                setTask();
                break;

            case R.id.button3:
                showAlert("Information","Розроблено студенткою КН 1101\nЛещенко Анастасією","Start");
                break;

            case R.id.ans1:
                checkTask(v.getId());
                break;

            case R.id.ans2:
                checkTask(v.getId());
                break;

            case R.id.ans3:
                checkTask(v.getId());
                break;
        }
    }
}
