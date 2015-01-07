package netUtility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class HttpUrl {
	//public static final String URL = "http://192.168.1.37:8080/bluetoothDemo/";http://bluetoothdemo.sinaapp.com/
    public static final String URL = "http://bluetoothdemo.sinaapp.com/bluetoothDemo/";
	//������֤
	public static boolean getNetStatus(Context paramContext){
		NetworkInfo localNetworkInfo =((ConnectivityManager)paramContext.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
		if((localNetworkInfo==null)||(!localNetworkInfo.isAvailable()))
			return false;
		else
			return true;
	}
}
