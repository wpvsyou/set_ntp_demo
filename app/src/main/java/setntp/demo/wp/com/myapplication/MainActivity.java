package setntp.demo.wp.com.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

    EditText mHost, mTimeOut;
    TextView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHost = (EditText)findViewById(R.id.host);
        mTimeOut = (EditText)findViewById(R.id.mTimeOut);
        mTitle = (TextView)findViewById(R.id.title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onSntpSetting(View v) {
        Settings.Global.putString(getContentResolver(), "ntp_server", mHost.getText().toString());
        Settings.Global.putString(getContentResolver(), "ntp_timeout", mHost.getText().toString());
        String currentHost = Settings.Global.getString(getContentResolver(), "ntp_server");
        mTitle.setText(currentHost);
    }
}
