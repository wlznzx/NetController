package cn.alauncher.netcontroller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ListView;

public class MainActivity extends Activity implements INetController{

	private static final String TAG = "MainActivity";
	private String args0 = "touch /mnt/sdcard/xxx.txt";
	
	private String DISABLE_BRO = "/system/bin/iptables -A INPUT -i wlan0 -m owner --uid-owner=10027 -j DROP";
	private String ENABLE_BRO = "/system/bin/iptables -D INPUT -i wlan0 -m owner --uid-owner=10027 -j DROP";
	
	
	private String ENABLE_BRO_2 = "/system/bin/sh /mnt/sdcard/disable.sh";
	
	private String ROOT = "/system/xbin/daemonsu --auto-daemon &";
	
	
	private ListView lv_app_list;
    private AppAdapter mAppAdapter;
    public Handler mHandler = new Handler();

    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
		setContentView(R.layout.activity_applist);
        lv_app_list = (ListView) findViewById(R.id.lv_app_list);
        mAppAdapter = new AppAdapter(this);
        mAppAdapter.setINetController(this);
        lv_app_list.setAdapter(mAppAdapter);
        initAppList();
		/*
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
		*/
		
		
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
	
	private void initAppList(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                //扫描得到APP列表
                final List<MyAppInfo> appInfos = ApkTool.scanLocalInstallAppList(MainActivity.this.getPackageManager());
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mAppAdapter.setData(appInfos);
                    }
                });
            }
        }.start();
    }

	@Override
	public void setNetRule(String packageName, String uid, String netType, boolean disable) {
		String cmd = null;
		if(disable){
			cmd = "/system/bin/iptables -A INPUT -i wlan0 -m owner --uid-owner=" + uid + " -j DROP";
		}else {
			cmd = "/system/bin/iptables -D INPUT -i wlan0 -m owner --uid-owner=" + uid + " -j DROP";
		}
		CommandExecution.execCommand(cmd, true);
		Log.d(TAG, cmd);
	}
	
}
