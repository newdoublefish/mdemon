/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.easemob.chatuidemo.activity;

import java.util.HashMap;
import java.util.Map;

import netUtility.HttpUrl;
import netUtility.HttpUtils;
import netUtility.networkTask;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.easemob.EMError;
import com.easemob.chat.EMChatManager;
import com.easemob.chatuidemo.DemoApplication;
import com.easemob.chatuidemo.R;
import com.easemob.chatuidemo.domain.User;
import com.easemob.exceptions.EaseMobException;
import com.ray.tool.Action;
import com.ray.tool.DUser;

/**
 * 注册页
 * 
 */
public class RegisterActivity extends BaseActivity {
	private EditText userNameEditText;
	private EditText passwordEditText;
	private EditText confirmPwdEditText;
    private networkTask mNetworkTask;
    private Context context;
    private BluetoothAdapter mBluetoothAdapter = null;
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		userNameEditText = (EditText) findViewById(R.id.username);
		passwordEditText = (EditText) findViewById(R.id.password);
		confirmPwdEditText = (EditText) findViewById(R.id.confirm_password);
		
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            
        // Otherwise, setup the chat session
        }
	}

	/**
	 * 注册
	 * 
	 * @param view
	 */
	public void register(View view) {
		final String username = userNameEditText.getText().toString().trim();
		final String pwd = passwordEditText.getText().toString().trim();
		String confirm_pwd = confirmPwdEditText.getText().toString().trim();
		if (TextUtils.isEmpty(username)) {
			Toast.makeText(this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
			userNameEditText.requestFocus();
			return;
		} else if (TextUtils.isEmpty(pwd)) {
			Toast.makeText(this, "密码不能为空！", Toast.LENGTH_SHORT).show();
			passwordEditText.requestFocus();
			return;
		} else if (TextUtils.isEmpty(confirm_pwd)) {
			Toast.makeText(this, "确认密码不能为空！", Toast.LENGTH_SHORT).show();
			confirmPwdEditText.requestFocus();
			return;
		} else if (!pwd.equals(confirm_pwd)) {
			Toast.makeText(this, "两次输入的密码不一致，请重新输入！", Toast.LENGTH_SHORT).show();
			return;
		}

		if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(pwd)) {
			final ProgressDialog pd = new ProgressDialog(this);
			pd.setMessage("正在注册...");
			pd.show();
			new Thread(new Runnable() {
				public void run() {
					try {
						// 调用sdk注册方法
						EMChatManager.getInstance().createAccountOnServer(username, pwd);
						runOnUiThread(new Runnable() {
							public void run() {
								if (!RegisterActivity.this.isFinishing())
									pd.dismiss();
								// 保存用户名
								DemoApplication.getInstance().setUserName(username);
								Toast.makeText(getApplicationContext(), "注册成功", 0).show();
								Register();
								
							}
						});
					} catch (final EaseMobException e) {
						runOnUiThread(new Runnable() {
							public void run() {
								if (!RegisterActivity.this.isFinishing())
									pd.dismiss();
								int errorCode=e.getErrorCode();
								if(errorCode==EMError.NONETWORK_ERROR){
									Toast.makeText(getApplicationContext(), "网络异常，请检查网络！", Toast.LENGTH_SHORT).show();
								}else if(errorCode==EMError.USER_ALREADY_EXISTS){
									Toast.makeText(getApplicationContext(), "用户已存在！", Toast.LENGTH_SHORT).show();
								}else if(errorCode==EMError.UNAUTHORIZED){
									Toast.makeText(getApplicationContext(), "注册失败，无权限！", Toast.LENGTH_SHORT).show();
								}else{
									Toast.makeText(getApplicationContext(), "注册失败: " + e.getMessage(), Toast.LENGTH_SHORT).show();
								}
							}
						});
					}
				}
			}).start();

		}
	}

	public void back(View view) {
		finish();
	}
	
    private String registerUser()
    {
        
        //return this.currentMacAddress;
        String url =HttpUrl.URL+Action.ADDDEVICE;
        DUser user=new DUser();
        user.setName(userNameEditText.getText().toString().trim());
            user.setGender("male"); 
        user.setPasswd(passwordEditText.getText().toString().trim());
        user.setBtmac(mBluetoothAdapter.getAddress());
        user.setAge("18");
        
        Map map=new HashMap();
        map.put("name", user.getName());
        map.put("nick", user.getName());
        map.put("passwd", user.getPasswd());
        map.put("gender", user.getGender());
        map.put("age", user.getAge());
        map.put("btmac", user.getBtmac());
        map.put("address", "notKnow");
        map.put("tel", "notKnow");
        map.put("hobby", "notKnow");
        map.put("address", "notKnow");
        map.put("imname", "notKnow");
        map.put("impasswd", "notKnow");
        map.put("country", "notKnow");
        map.put("city", "notKnow");
        
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
                    
                    finish();

                }

            }
        };
        mNetworkTask=new networkTask(context,callback);
        mNetworkTask.execute(new Void[0]);  
    }   
	

}
