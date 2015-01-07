/**
 * GlobalContext.java [V 1.0.0]
 * classes : GlobalContext.java.GlobalContext
 * ������ Create at 2012-7-31 ����10:52:28
 */
package netUtility;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpProtocolParams;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;


/**
 * com.hymobile.qqs.util.GlobalContext
 * 
 * @author xiaonan create at 2012-7-31 ����10:52:28
 */
@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
public class GlobalContext {

	private static final String TAG = "GlobalContext";
	private static GlobalContext instance;
	private HashSet<Activity> acts;
	private HttpClient httpClient = null;

	private GlobalContext() {
		initHttpClient();
		this.acts = new HashSet();
	}

	/**
	 * ʵ����GlobalContext
	 * 
	 * @return
	 */
	public static GlobalContext getInstance() {
		if (instance == null)
			instance = new GlobalContext();
		return instance;
	}

	/**
	 * ��ʼ��httpClient
	 */
	private void initHttpClient() {
		Log.i("GlobalContext", "��ʼ��http");
		BasicHttpParams localBasicHttpParams = new BasicHttpParams();
		ConnManagerParams.setMaxTotalConnections(localBasicHttpParams, 100);
		HttpProtocolParams.setVersion(localBasicHttpParams,
				HttpVersion.HTTP_1_1);
		SchemeRegistry localSchemeRegistry = new SchemeRegistry();
		localSchemeRegistry.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		this.httpClient = new DefaultHttpClient(
				new ThreadSafeClientConnManager(localBasicHttpParams,
						localSchemeRegistry), localBasicHttpParams);
	}

	public void shutdownHttpClient() {
		this.httpClient.getConnectionManager().shutdown();
	}

	public HashSet<Activity> getActs() {
		return this.acts;
	}

	/**
	 * ע��activity
	 * 
	 * @param paramActivity
	 */
	public void registerAct(Activity paramActivity) {
		if (paramActivity != null)
			this.acts.add(paramActivity);
	}

	/**
	 * �Ƴ�activity
	 * 
	 * @param paramActivity
	 */
	public void removeAct(Activity paramActivity) {
		if (paramActivity != null)
			this.acts.remove(paramActivity);
	}

	public void destroy() {
		shutdownHttpClient();
	}

	public HttpClient getHttpClient() {
		if (this.httpClient == null)
			initHttpClient();
		return this.httpClient;
	}

	/**
	 * ��ȡ��ԴId
	 * 
	 * @param paramContext
	 *            �౾�� ����this
	 * @param paramString1
	 *            ��Դ���� ����string
	 * @param paramString2
	 *            ��Դ���� ���� app_name
	 * @return
	 */
	public int getResid(Context paramContext, String paramString1,
			String paramString2) {
		int i = 0;
		try {
			Field localField = Class.forName(
					paramContext.getPackageName() + ".$R" + paramString1)
					.getField(paramString2);
			i = Integer.parseInt(localField.get(localField.getName())
					.toString());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return i;
	}

	/**
	 * �ȴ�����
	 * 
	 * @param context
	 * @param id
	 * @return
	 */
	public ProgressDialog myProgressDialog(Context context, int id) {
		ProgressDialog myDialog = new ProgressDialog(context, id);
		myDialog.setIndeterminate(true);
		myDialog.setCancelable(true);
		myDialog.setCanceledOnTouchOutside(false);
		return myDialog;
	}

	/**
	 * ����list�߶�
	 * 
	 * @param listView
	 */
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}
}
