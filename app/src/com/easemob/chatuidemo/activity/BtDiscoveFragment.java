package com.easemob.chatuidemo.activity;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import netUtility.HttpUrl;
import netUtility.HttpUtils;
import netUtility.networkTask;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Toast;

import com.easemob.chat.EMContactManager;
import com.easemob.chat.EMNotifier;
import com.easemob.chatuidemo.Constant;
import com.easemob.chatuidemo.DemoApplication;
import com.easemob.chatuidemo.R;
import com.ray.tool.Action;
import com.ray.tool.DUser;
import com.ray.tool.JASONHelper;

public class BtDiscoveFragment extends Fragment{
    private static final String TAG = "MeetActivity";
    private static final boolean D = true;
    private ListView mListView;
    private networkTask mNetworkTask;
    private Context context;
    private String currentMacAddress="11:22:33:44";
    private BluetoothAdapter mBluetoothAdapter = null;
    private String toAddUsername;
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    private ImageButton ib;
    MyAdapter mAdapter;
    private boolean newDiscover=false;
    private ProgressDialog progressDialog;
    private ArrayList<DUser> userList=new ArrayList<DUser>();
    Handler handler=new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //扫描
            handler.postDelayed(this, 5000);
            if (D) Log.d(TAG, "------------------scan");

            doDiscovery();
        }
    };

    private void doDiscovery() {
        if (D) Log.d(TAG, "doDiscovery()");

        // Indicate scanning in the title
        // Turn on sub-title for new devices
        //findViewById(R.id.title_new_devices).setVisibility(View.VISIBLE);
        refreshUserList();

        // If we're already discovering, stop it
        if (mBluetoothAdapter.isDiscovering()) {
            //mBluetoothAdapter.cancelDiscovery();
            
        }else
        {
            mBluetoothAdapter.startDiscovery();
        }   

        // Request discover from BluetoothAdapter
        
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frament_btdiscover, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //防止被T后，没点确定按钮然后按了home键，长期在后台又进app导致的crash
        if(savedInstanceState != null && savedInstanceState.getBoolean("isConflict", false))
            return;
        
        context=getActivity();
        mAdapter=new MyAdapter(getActivity());
        mListView = (ListView) getView().findViewById(R.id.listview);
        mListView.setAdapter(mAdapter);

        registerForContextMenu(mListView);
        
        ib=(ImageButton)getView().findViewById(R.id.visible);
        ib.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //可见
                ensureDiscoverable();
            }
            
        });
        
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        context.registerReceiver(mReceiver, filter);

        // Register for broadcasts when discovery has finished
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        context.registerReceiver(mReceiver, filter);
        
        setDiscoverableTimeout(1000);
        
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            //Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            //finish();
            return;
        }
        
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }  
        
        handler.postDelayed(runnable, 3000);       
        System.out.println("---------------onCreate-----------");
    }

    
    private void ensureDiscoverable() {
        if(D) Log.d(TAG, "ensure discoverable");
        if (mBluetoothAdapter.getScanMode() !=
            BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }

    
    public void closeDiscoverableTimeout() {//关闭可见性
        BluetoothAdapter adapter=BluetoothAdapter.getDefaultAdapter();
        try {
            Method setDiscoverableTimeout = BluetoothAdapter.class.getMethod("setDiscoverableTimeout", int.class);
            setDiscoverableTimeout.setAccessible(true);
            Method setScanMode =BluetoothAdapter.class.getMethod("setScanMode", int.class,int.class);
            setScanMode.setAccessible(true);
            
            setDiscoverableTimeout.invoke(adapter, 1);
            setScanMode.invoke(adapter, BluetoothAdapter.SCAN_MODE_CONNECTABLE,1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void setDiscoverableTimeout(int timeout) {//开启可见s
        BluetoothAdapter adapter=BluetoothAdapter.getDefaultAdapter();
        try {
            Method setDiscoverableTimeout = BluetoothAdapter.class.getMethod("setDiscoverableTimeout", int.class);
            setDiscoverableTimeout.setAccessible(true);
            Method setScanMode =BluetoothAdapter.class.getMethod("setScanMode", int.class,int.class);
            setScanMode.setAccessible(true);
            
            setDiscoverableTimeout.invoke(adapter, timeout);
            setScanMode.invoke(adapter, BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE,timeout);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }  
    
    private String getUser()
    {
        
        //return this.currentMacAddress;
        String url =HttpUrl.URL+Action.GETUSERINFO;
        Map map=new HashMap();
        map.put("btmac", currentMacAddress);
        String result = HttpUtils.postData(url, map);
        System.out.println("result="+result);
        return result;   
    }
    
    private void refreshUserList()
    {
        for(DUser u:userList)
        {
            u.maxMissCount--;
            if(u.maxMissCount==0)
            {
                userList.remove(u);
            }   
        }   
        refreshListView();
    }
    
    private void AddToUserList(DUser user)
    {
        for(DUser u:userList)
        {
            if(u.getName().equals(user.getName()))
            {
                u.maxMissCount=10;
                return;
            }   
        }
        
        userList.add(user);
        newDiscover=true;
        
        //提示音
        EMNotifier.getInstance(getActivity().getApplicationContext()).notifyOnNewMsg();
        //TODO:
        //提示是老乡 ，还是单身
        
        
        refreshListView();
    }
    
    public boolean isAnyNewDiscover()
    {
        if(userList.size()==0)
        {
            newDiscover=false;
        }    
        return newDiscover;
    }
    
    private void refreshListView() {
        mAdapter.notifyDataSetChanged();
    }
    
    protected void getUserInfo()
    {

        // TODO Auto-generated method stub
        if (mNetworkTask != null && mNetworkTask.getStatus() == networkTask.Status.RUNNING) {
            mNetworkTask.cancel(true); // 如果Task还在运行，则先取消它
            }
        networkTask.Interface callback= new networkTask.Interface() {                
            public String perform() {
                // TODO Auto-generated method stub
                String result=getUser();//login();
                return result;
            }
            public void Callback(String result) {
                // TODO Auto-generated method stub
                //Log.d(LOGTAG, "delete asyncTaskFinishCallback,result="+result);
                if(null != result){
//                  Log.d("hello", "delete ,result="+result);
                    //Toast.makeText(context, result, Toast.LENGTH_LONG).show();                    
                    //cList=JASONHelper.getDeviceListByJason(result);
                    
//                  for(CameraDevice d : cList)
//                  {
//                      System.out.println(""+d.getId()+","+d.getAddress()+","+d.getDeviceName()
//                              +","+d.getPassword()+","+d.getPort()+","+d.getRegisterId());
//                  }   
                    
                    //Intent intent =new Intent(context,DeviceListShow.class);
                    //context.startActivity(intent);
                    ArrayList<DUser> cList=JASONHelper.getDeviceListByJason(result);
                    for(DUser u : cList)
                    {
                        //deviceInfoArrayAdapter.add("name:"+u.getName()+" gender:"+u.getGender()); 
                        AddToUserList(u);
                    }   
                    System.out.println("result="+result);
                    mNetworkTask.cancel(true);
                }
            }
        };
        mNetworkTask=new networkTask(context,callback);
        mNetworkTask.execute(new Void[0]);  
    }       
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (D) Log.d(TAG, "BroadcastReceiver---------------");
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // If it's already paired, skip it, because it's been listed already
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    //deviceInfoArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                    if(D) Log.d(TAG,"NAME="+device.getName()+"\n address=" + device.getAddress());
                    currentMacAddress=device.getAddress();
                    getUserInfo();
                    //getUser();
                }
            // When discovery is finished, change the Activity title
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                //setProgressBarIndeterminateVisibility(false);
                if(D) Log.d(TAG,"--------discovery finished");
                //setTitle(R.string.select_device);

            }
        }
    };
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
            getActivity().getMenuInflater().inflate(R.menu.context_btdiscover_list, menu);     
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_contact) {
            DUser user=mAdapter.getItem(((AdapterContextMenuInfo) item.getMenuInfo()).position);
            Toast.makeText(getActivity(), user.getName(), Toast.LENGTH_SHORT).show();
            addContact(user.getName());
            
        }else if(item.getItemId() == R.id.sayhi)
        {
            Toast.makeText(getActivity(), "say hi", Toast.LENGTH_SHORT).show();
        }    

        return super.onContextItemSelected(item);
    }
    
    public void addContact(String username){
        if(DemoApplication.getInstance().getUserName().equals(username)){
            startActivity(new Intent(getActivity(), AlertDialog.class).putExtra("msg", "不能添加自己"));
            return;
        }
        
        if(DemoApplication.getInstance().getContactList().containsKey(username)){
            startActivity(new Intent(getActivity(), AlertDialog.class).putExtra("msg", "此用户已是你的好友"));
            return;
        }
        
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("正在发送请求...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        toAddUsername=username;
        new Thread(new Runnable() {
            public void run() {
                
                try {
                    //demo写死了个reason，实际应该让用户手动填入
                    
                    EMContactManager.getInstance().addContact(toAddUsername, "加个好友呗");
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "发送请求成功,等待对方验证", 1).show();
                        }
                    });
                } catch (final Exception e) {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "请求添加好友失败:" + e.getMessage(), 1).show();
                        }
                    });
                }
            }
        }).start();
    }    
    

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

    }

    @Override
    public void onResume() {
        super.onResume();

    }


    
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(((MainActivity)getActivity()).isConflict){
            outState.putBoolean("isConflict", true);
        }else if(((MainActivity)getActivity()).getCurrentAccountRemoved()){
            outState.putBoolean(Constant.ACCOUNT_REMOVED, true);
        }
    }
        public final class ViewHolder {
            public TextView deviceName;
            public TextView info;

        }

        public class MyAdapter extends BaseAdapter {
            private LayoutInflater mInflater;

            public MyAdapter(Context context) {
                this.mInflater = LayoutInflater.from(context);
            }

            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return userList.size();
            }

            @Override
            public DUser getItem(int arg0) {
                // TODO Auto-generated method stub
                return userList.get(arg0);
            }

            @Override
            public long getItemId(int position) {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub
                ViewHolder viewholder = null;
                if (convertView == null) {
                    viewholder = new ViewHolder();
                    convertView = mInflater.inflate(R.layout.row_btdiscover, null);
                    viewholder.deviceName = (TextView) convertView
                            .findViewById(R.id.name);
                    viewholder.info=(TextView)convertView.findViewById(R.id.info);
                    
                    //viewholder.userAge=(TextView)convertView.findViewById(R.id.age);
                    //viewholder.userGender=(TextView)convertView.findViewById(R.id.gender);
                    convertView.setTag(viewholder);
                } else {
                    // viewholder.deviceName.setText(deviceList.get(position).Name);
                    viewholder = (ViewHolder) convertView.getTag();

                }
                viewholder.deviceName.setText(userList.get(position).getName());
                String userInfo="性别:"+userList.get(position).getGender()+
                        ",年龄:"+userList.get(position).getAge()+
                        ",城市:"+userList.get(position).getCity();
                viewholder.info.setText(userInfo);
                //viewholder.userAge.setText(userList.get(position).getAge());
                //viewholder.userGender.setText(userList.get(position).getGender());
                return convertView;
            }
        }  
    

    
    
}
