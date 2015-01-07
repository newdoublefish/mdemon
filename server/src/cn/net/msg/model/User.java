package cn.net.msg.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;
/*
 * 瀹炰綋绫伙紝瀵瑰簲鐫�鏁版嵁搴撲腑鐨勮〃t_demo
 */
@Component("user")
@Entity
@Table(name="user")
public class User{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	
	@Column(name="nick")
	private String nick;
	
	@Column(name="name")
	private String name;
	
	@Column(name="passwd")
	private String passwd;
	
	@Column(name="gender")
	private String gender;
	
	@Column(name="age")
	private String age;
	
	@Column(name="btmac")
	private String btmac;
	
	@Column(name="tel")
	private String tel;	
	
	@Column(name="hobby")
	private String hobby;	
	
	@Column(name="address")
	private String address;	
	
	@Column(name="imname")
	private String imname;	
	
	@Column(name="impasswd")
	private String impasswd;
	
	@Column(name="country")
	private String country;
	
	@Column(name="city")
	private String city;
	
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
	
	public String getImpasswd() {
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
