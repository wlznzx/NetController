package cn.alauncher.netcontroller;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.List;


public class ApkTool {
    static  String TAG = "ApkTool";
    public static List<MyAppInfo> mLocalInstallApps = null;

    public static List<MyAppInfo> scanLocalInstallAppList(PackageManager packageManager) {
        List<MyAppInfo> myAppInfos = new ArrayList<MyAppInfo>();
        try {
            List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
            for (int i = 0; i < packageInfos.size(); i++) {
                PackageInfo packageInfo = packageInfos.get(i);
//            if ((ApplicationInfo.FLAG_SYSTEM & packageInfo.applicationInfo.flags) != 0) {
//                continue;
//            }
                MyAppInfo myAppInfo = new MyAppInfo();
                myAppInfo.setAppName(packageInfo.applicationInfo.loadLabel(packageManager).toString());
                myAppInfo.setPackageName(packageInfo.packageName);
                myAppInfo.setUid(String.valueOf(packageInfo.applicationInfo.uid));
                if (packageInfo.applicationInfo.loadIcon(packageManager) == null) {
                    continue;
                }
                myAppInfo.setImage(packageInfo.applicationInfo.loadIcon(packageManager));
                myAppInfos.add(myAppInfo);
            }
        }catch (Exception e){
            
        }
        return myAppInfos;
    }

}
