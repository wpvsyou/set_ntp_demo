package setntp.demo.wp.com.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;
import android.util.NtpTrustedTime;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MainActivity extends Activity {

    EditText mHost, mTimeOut;
    TextView mTitle;
    NtpTrustedTime mNtpTrustedTime;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            new WkThread(mHost.getText().toString(), new WkCallback() {
                @Override
                public void requestTime(boolean b) {
                    Log.d("WWW", "Local test force refresh time " +
                            (b ? "successfully!!!" : "failed!"));
                }
            }).start();
            super.handleMessage(msg);
        }
    };

    interface WkCallback {
        void requestTime(boolean b);
    }

    private class WkThread extends Thread {

        String server;
        WkCallback callback;

        public WkThread(String server, WkCallback callback) {
            this.server = server;
            this.callback = callback;
        }

        @Override
        public void run() {
            super.run();
            requestTime(server, 3000);

        }

        public void requestTime(String host, int timeout) {
            DatagramSocket socket = null;
            try {
                socket = new DatagramSocket();
                socket.setSoTimeout(timeout);
                InetAddress address = InetAddress.getByName(host);
                byte[] buffer = new byte[48];
                DatagramPacket request = new DatagramPacket(buffer, buffer.length, address, 123);
                // set mode = 3 (client) and version = 3
                // mode is in low 3 bits of first byte
                // version is in bits 3-5 of first byte
                buffer[0] = 3 | (3 << 3);
                socket.send(request);
                // read the response
                DatagramPacket response = new DatagramPacket(buffer, buffer.length);
                socket.receive(response);
            } catch (Exception e) {
                e.printStackTrace();
                callback.requestTime(false);
            } finally {
                if (socket != null) {
                    socket.close();
                }
            }
            callback.requestTime(true);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHost = (EditText) findViewById(R.id.host);
        mTimeOut = (EditText) findViewById(R.id.mTimeOut);
        mTitle = (TextView) findViewById(R.id.title);
        mNtpTrustedTime = NtpTrustedTime.getInstance(this);
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
        //android: 2.android.pool.ntp.org
//        Settings.Global.putString(getContentResolver(), "ntp_server", mHost.getText().toString());
//        Settings.Global.putString(getContentResolver(), "ntp_timeout", mHost.getText().toString());
        try {
            Field field = NtpTrustedTime.class.getDeclaredField("mServer");
            field.setAccessible(true);
            field.set(mNtpTrustedTime, mHost.getText().toString());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void onReboot(View v) {
        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
        pm.reboot("test");
    }

    public void onShowCurrentSetting(View v) {
//        String currentHost = Settings.Global.getString(getContentResolver(), "ntp_server");
        String currentHost = "un fount!";
        try {
            Field field = NtpTrustedTime.class.getDeclaredField("mServer");
            field.setAccessible(true);
            currentHost = (String) field.get(mNtpTrustedTime);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            currentHost = e.toString();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        mTitle.setText("Check current ntp host: " + currentHost);
    }

    public void onForceRefresh(View v) {
        boolean b = mNtpTrustedTime.forceRefresh();
        mTitle.setText("Force refresh " + (b ? "successfully!" : "failed!"));
    }

    public void onLocalRequestTime(View v) {
        mHandler.sendEmptyMessage(0);
    }
}
