package com.ray.tool;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class JASONHelper {

	public static ArrayList<DUser> getDeviceListByJason(String result)
	{
		ArrayList<DUser> cList=new ArrayList<DUser>();
		try {
			JSONArray array=new JSONArray(result);
			for(int i=0;i<array.length();i++)
			{
				JSONObject item=array.getJSONObject(i);
				//String name=item.getString(name);
				DUser user=new DUser();
				user.setId(Long.parseLong((item.getString("id"))));
				user.setName(item.getString("name"));
				user.setNick(item.getString("nick"));
				user.setPasswd(item.getString("passwd"));
				user.setGender(item.getString("gender"));
				user.setAge(item.getString("age"));
				user.setBtmac(item.getString("btmac"));
				user.setTel(item.getString("tel"));
				user.setHobby(item.getString("hobby"));
				
				user.setAddress(item.getString("address"));
				user.setImname(item.getString("imname"));
				user.setImpasswd(item.getString("impasswd"));
				user.setCountry(item.getString("country"));
				user.setCity(item.getString("city"));
				
				cList.add(user);
			}	
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return cList;
	}
}
