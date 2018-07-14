package cn.alauncher.netcontroller;

import android.graphics.drawable.Drawable;
/**
 * Created by gray_dog3 on 16/3/3.
 */
public class MyAppInfo {
    private Drawable image;
    private String appName;
    private String packageName;
    private String uid;

    public MyAppInfo(Drawable image, String appName) {
        this.image = image;
        this.appName = appName;
    }
    public MyAppInfo() {
    	
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	@Override
	public String toString() {
		return "MyAppInfo [image=" + image + ", appName=" + appName + ", packageName=" + packageName + ", uid=" + uid + "]";
	}
	
	
}
