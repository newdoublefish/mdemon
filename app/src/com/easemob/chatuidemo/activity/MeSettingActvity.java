package com.easemob.chatuidemo.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import netUtility.HttpUrl;
import netUtility.HttpUtils;
import netUtility.networkTask;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.easemob.chatuidemo.R;
import com.ray.tool.Action;
import com.ray.tool.DUser;
import com.ray.tool.JASONHelper;

public class MeSettingActvity extends Activity {
    private networkTask mNetworkTask;
    
    private DUser user;
    private Context cx;
    
    private String currentMacAddress;
    
    EditText etNick;
    EditText etAge;
    EditText etHobby;
    EditText etCountry;
    EditText etCity;
    EditText etTel;
    EditText etAddress;
    
    private boolean btStatus;//FALSE 修改，TRUE确定
    
    Button bt;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_me_setting);
        cx=this;
        Init();
        showMe();
        
    }
    
    void Init()
    {
        etNick=(EditText)this.findViewById(R.id.nickname);
        etAge=(EditText)this.findViewById(R.id.age1);
        etHobby=(EditText)this.findViewById(R.id.hobby);
        etCountry=(EditText)this.findViewById(R.id.country);
        etCity=(EditText)this.findViewById(R.id.city);
        etTel=(EditText)this.findViewById(R.id.tel);
        etAddress=(EditText)this.findViewById(R.id.address);
        
        etNick.setEnabled(false);
        etAge.setEnabled(false);
        etHobby.setEnabled(false);
        etCountry.setEnabled(false);
        etCity.setEnabled(false);
        etTel.setEnabled(false);
        etAddress.setEnabled(false);
        
        btStatus=false;
        
        bt=(Button)this.findViewById(R.id.btId);
        bt.setText("修改");
        bt.setBackgroundColor(Color.RED);
        bt.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if(btStatus)
                {
                    Register();//提交修改
                    
                    btStatus=false;
                    bt.setText("修改");
                    bt.setBackgroundColor(Color.RED);

                    
                    etNick.setEnabled(false);
                    etHobby.setEnabled(false);
                    etCountry.setEnabled(false);
                    etAge.setEnabled(false);
                    etCity.setEnabled(false);
                    etTel.setEnabled(false);
                    etAddress.setEnabled(false);
                }else
                {
                    btStatus=true;
                    bt.setText("确定");
                    bt.setBackgroundColor(Color.BLUE);
                    
                    etNick.setEnabled(true);
                    etAge.setEnabled(true);
                    etHobby.setEnabled(true);
                    etCountry.setEnabled(true);
                    etCity.setEnabled(true);
                    etTel.setEnabled(true);
                    etAddress.setEnabled(true);

                }    
                
            }
            
        });
        
        
    }
    
    void setUser(DUser user)
    {
        etNick.setText(user.getNick());
        etAge.setText(user.getAge());
        etHobby.setText(user.getHobby());
        etCountry.setText(user.getCountry());
        etCity.setText(user.getCity());
        etTel.setText(user.getTel());
        etAddress.setText(user.getAddress());
    }
    
    
    public void back(View view) {
        finish();
    }

    public void showMe()
    {
        BluetoothAdapter mBluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        currentMacAddress=mBluetoothAdapter.getAddress();
        getUserInfo();
        
        //startActivity(new Intent(cx, MeSettingActvity.class));
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
                        //AddToUserList(u);
                        user=u;
                        setUser(user);
                    }   
                    //System.out.println("result="+result);
                   
                    mNetworkTask.cancel(true);
                }
            }
        };
        mNetworkTask=new networkTask(cx,callback);
        mNetworkTask.execute(new Void[0]);  
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
    /*
     *     EditText etNick;
    EditText etAge;
    EditText etHobby;
    EditText etCountry;
    EditText etCity;
    EditText etTel;
    EditText etAddress;
     * */
    private void refreshUser(DUser user)
    {
        user.setNick(etNick.getText().toString());
        user.setAge(etAge.getText().toString());
        user.setHobby(etHobby.getText().toString());
        user.setCountry(etCountry.getText().toString());
        user.setCity(etCity.getText().toString());
        user.setTel(etTel.getText().toString());
        user.setAddress(etAddress.getText().toString());
    }
    private String registerUser()
    {
        
        //return this.currentMacAddress;
        String url =HttpUrl.URL+Action.ADDDEVICE;
        refreshUser(user);
        Map map=new HashMap();
        map.put("name", user.getName());
        map.put("nick", user.getNick());
        map.put("passwd", user.getPasswd());
        map.put("gender", user.getGender());
        map.put("age", user.getAge());
        map.put("btmac", user.getBtmac());
        map.put("address",user.getAddress());
        map.put("tel", user.getTel());
        map.put("hobby", user.getHobby());
        map.put("address",user.getAddress());
        map.put("imname", user.getImname());
        map.put("impasswd", user.getImPasswd());
        map.put("country", user.getCountry());
        map.put("city",user.getCity());
        
        String result = HttpUtils.postData(url, map);
        //System.out.println("result="+result);
        return result;   
    }
    
    protected void Register()
    {

        // TODO Auto-generated method stub
        if (mNetworkTask != null && mNetworkTask.getStatus() == networkTask.Status.RUNNING) {
            mNetworkTask.cancel(true); // 如果Task还在运行，则先取消它
            }
        networkTask.Interface callback= new networkTask.Interface() {                
            public String perform() {
                // TODO Auto-generated method stub
                String result=registerUser();//login();
                return result;
            }
            public void Callback(String result) {
                // TODO Auto-generated method stub
                //Log.d(LOGTAG, "delete asyncTaskFinishCallback,result="+result);
                if(null != result){


                    System.out.println("result="+result);

                    mNetworkTask.cancel(true);
                    
                    //finish();

                }

            }
        };
        mNetworkTask=new networkTask(cx,callback);
        mNetworkTask.execute(new Void[0]);  
    }       
    
}
