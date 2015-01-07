package cn.net.msg.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import net.sf.json.JSONArray;

import cn.net.msg.model.User;
import cn.net.msg.service.UserService;

import com.opensymphony.xwork2.ActionSupport;
/*
 * 澶勭悊鐢ㄦ埛鎿嶄綔鐨凙ction绫�
 */
public class UserAction extends ActionSupport implements ServletRequestAware, ServletResponseAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7574227471454774126L;
	private User user;
	private List<User> list;
	@Resource(name="userService")
	UserService service;
	private HttpServletRequest request;  
    private HttpServletResponse response; 

	
	public String inseart() {
		service.insertUser(user);
		return "success";
	}

	public String delete(){
		service.deleteById(user.getId());
		return "success";
	}
	
	public void addUserInfo()
	{
		try {
			List<User> tempList=null;
			request.setCharacterEncoding("utf-8");
			String address=request.getParameter("btmac");
			tempList=service.findBybtmac(address);
			System.out.println("btMac="+address);			
			
			
			User user = new User();;
			request.setCharacterEncoding("utf-8");
			//user.setId(id);
			user.setName(request.getParameter("name"));
			user.setNick(request.getParameter("nick"));
			user.setPasswd(request.getParameter("passwd"));
			user.setGender(request.getParameter("gender"));
			user.setAge(request.getParameter("age"));
			user.setBtmac(request.getParameter("btmac"));
			user.setTel(request.getParameter("tel"));
			user.setHobby(request.getParameter("hobby"));
			user.setAddress(request.getParameter("address"));
			user.setImname(request.getParameter("imname"));
			user.setImpasswd(request.getParameter("impasswd"));
			user.setCountry(request.getParameter("country"));
			user.setCity(request.getParameter("city"));
			if(tempList.size()>0)
			{
				for(User u:tempList)
				{
					user.setId(u.getId());
				}
				service.updateUser(user);
			}else
			{	
			   service.insertUser(user);
			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void getUserInfo()
	{
		//list=service.findByName(name)
		
		List<User> tempList=null;
		try {
			request.setCharacterEncoding("utf-8");
			String address=request.getParameter("btmac");
			tempList=service.findBybtmac(address);
			System.out.println("btMac="+address);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(tempList!=null)
		{	
		    
			System.out.println("-----------getUserInfo----size="+tempList.size());
	    	JSONArray json=JSONArray.fromObject(tempList);
	    	try {
	    		this.response.setCharacterEncoding("UTF-8");  
				response.getWriter().print(json);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else
			System.out.println("-----------no user");
		
		
		
	}
	
	public String login()
	{
		/*List<User> templist=null;
	    templist=service.findByNameAndAge(user.getUsername(),user.getUserpassword());
	    System.out.println("userName="+user.getUsername()+","+"userPasswd="+user.getUserpassword()+","+"count="+templist.size());
		if(templist.size()>0)	
		{	
		 return "success";
		} 
		else
		{	
		  return "false";
		}*/
		return "success";
	}
	
	public String  findByName(){
		System.out.println("-----------findbyName----");
		setList(service.findByName(user.getName()));
		return "success";
	}
	
	public String update(){
		service.updateUser(user);
		return "success";
	}
	
	public String findAll(){
		System.out.println("-----------findAll----");
		setList(service.findAll());
		System.out.println("size="+list.size());
		return "success";
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<User> getList() {
		return list;
	}

	public void setList(List<User> list) {
		this.list = list;
	}
	/*
	 * 鏍￠獙鎻掑叆鏁版嵁鐨勬搷浣�
	 */
	public void validateInseart(){/*
		if(null==user.getName()||"".equals(user.getName().trim())) 
			this.addFieldError("name", "name can`t be null");
		else if(16<user.getName().trim().length()) 
			this.addFieldError("name", "name is limited in 16 characters");
		
		if(0>user.getAge()||150<user.getAge())
			this.addFieldError("age", "age is limited between 0~150");
		
		if(null==user.getSex()||((!user.getSex().equals("man"))&&(!user.getSex().equals("woman"))))
			this.addFieldError("sex", "value of sex is unvalid");*/
	}
	/*
	 * 鏍￠獙鏇存柊鏁版嵁鐨勬搷浣�
	 */
	public void validateUpdate(){/*
		if(100000>user.getId())
			this.addFieldError("id", "value of id is unvalid");
		
		if(null==user.getName()||"".equals(user.getName().trim())) 
			this.addFieldError("name", "name can`t be null");
		else if(16<user.getName().trim().length()) 
			this.addFieldError("name", "name is limited in 16 characters");
		
		if(0>user.getAge()||150<user.getAge())
			this.addFieldError("age", "age is limited between 0~150");
		
		if(null==user.getSex()||((!user.getSex().equals("man"))&&(!user.getSex().equals("woman"))))
			this.addFieldError("sex", "value of sex is unvalid");*/
	}
	/*
	 * 鏍￠獙鍒犻櫎鏁版嵁鐨勬搷浣�
	 */
	/*public void validateDelete(){
		if(100000>user.getUserid())
			this.addFieldError("id", "value of id is unvalid");
	}
	public void validateFindByName(){
		if(null==user.getUsername()||"".equals(user.getUsername().trim())) 
			this.addFieldError("name", "name can`t be null");
		else if(16<user.getUsername().trim().length()) 
			this.addFieldError("name", "name is limited in 16 characters");
	}*/

	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub
		this.response=arg0;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		this.request=arg0;
	}


}
