package cn.alauncher.netcontroller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";
	private String args0 = "touch /mnt/sdcard/xxx.txt";
	
	private String DISABLE_BRO = "/system/bin/iptables -A INPUT -i wlan0 -m owner --uid-owner=10027 -j DROP";
	private String ENABLE_BRO = "/system/bin/iptables -D INPUT -i wlan0 -m owner --uid-owner=10027 -j DROP";
	
	
	private String ENABLE_BRO_2 = "/system/bin/sh /mnt/sdcard/disable.sh";
	
	private String ROOT = "/system/xbin/daemonsu --auto-daemon &";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		findViewById(R.id.btn_disable).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(TAG, "Do Disable New..");
				CommandExecution.execCommand(ROOT, false);
				CommandExecution.execCommand(DISABLE_BRO, true);
			}
		});
		
		findViewById(R.id.btn_enable).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(TAG, "Do Enable.");
				CommandExecution.execCommand(ENABLE_BRO, true);
			}
		});
	}

	public static void Memtester(String command) {
		Runtime r = Runtime.getRuntime();
		Process p;
		try {
			p = r.exec(command);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String inline;
			while ((inline = br.readLine()) != null) {
				Log.d(TAG,inline);
			}
			br.close();
			p.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
