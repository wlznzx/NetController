package cn.alauncher.netcontroller;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

class AppAdapter extends BaseAdapter {

    List<MyAppInfo> myAppInfos = new ArrayList<MyAppInfo>();
    
    private Context mContext;
    
    public static String TAG = "AppAdapter"; 
    
    public INetController mINetController;
    
    public AppAdapter(Context mContext) {
		super();
		this.mContext = mContext;
	}
    
    public void setINetController(INetController pINetController){
    	mINetController = pINetController;
    }
	public void setData(List<MyAppInfo> myAppInfos) {
        this.myAppInfos = myAppInfos;
        notifyDataSetChanged();
    }

    public List<MyAppInfo> getData() {
        return myAppInfos;
    }

    @Override
    public int getCount() {
        if (myAppInfos != null && myAppInfos.size() > 0) {
            return myAppInfos.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (myAppInfos != null && myAppInfos.size() > 0) {
            return myAppInfos.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        MyAppInfo myAppInfo = myAppInfos.get(position);
        if (convertView == null) {
            mViewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_app_info, null);
            mViewHolder.iv_app_icon = (ImageView) convertView.findViewById(R.id.iv_app_icon);
            mViewHolder.tx_app_name = (TextView) convertView.findViewById(R.id.tv_app_name);
            mViewHolder.cb_wlan_disable = (CheckBox) convertView.findViewById(R.id.wlan_disable);
            convertView.setTag(R.id.tag_view, mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag(R.id.tag_view);
        }
        mViewHolder.iv_app_icon.setImageDrawable(myAppInfo.getImage());
        mViewHolder.tx_app_name.setText(myAppInfo.getAppName());
        mViewHolder.cb_wlan_disable.setOnCheckedChangeListener(disableWifiChange);
        mViewHolder.cb_wlan_disable.setTag(R.id.tag_info, myAppInfo);
        return convertView;
    }

    class ViewHolder {
        ImageView iv_app_icon;
        TextView tx_app_name;
        CheckBox cb_wlan_disable;
    }
    
    private CompoundButton.OnCheckedChangeListener disableWifiChange = new CompoundButton.OnCheckedChangeListener(){
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			MyAppInfo myAppInfo = (MyAppInfo) buttonView.getTag(R.id.tag_info);
			Log.i(TAG,myAppInfo.toString());
			if(mINetController != null) {
				mINetController.setNetRule(myAppInfo.getPackageName(), myAppInfo.getUid(), "wlan", isChecked);
			}
		}
    };
}
