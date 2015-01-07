package com.ray.tool;


public class DUser {

	private long id;
	

	private String nick;
	

	private String name;
	

	private String passwd;
	

	private String gender;
	

	private String age;
	

	private String btmac;
	

	private String tel;	
	

	private String hobby;	
	

	private String address;	
	

	private String imname;	
	

	private String impasswd;
	

	private String country;
	

	private String city;
	
	public int maxMissCount=10;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getNick() {
		return nick;
	}
	
	public void setNick(String nick) {
		this.nick = nick;
	}
	
    public String getPasswd()
    {
    	return passwd;
    }
    
    public void setPasswd(String passwd)
    {
    	this.passwd=passwd;
    }


	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}
	public String getBtmac() {
		return btmac;
	}

	public void setBtmac(String btmac) {
		this.btmac = btmac;
	}
	
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	
	public String getHobby() {
		return this.hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getImname() {
		return this.imname;
	}

	public void setImname(String imname) {
		this.imname = imname;
	}
	
	public String getImPasswd() {
		return this.impasswd;
	}

	public void setImpasswd(String impasswd) {
		this.impasswd = impasswd;
	}
	
	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}	

}
