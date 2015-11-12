package org.tarena.entity;

import java.io.Serializable;
import java.sql.Date;

public class Service implements Serializable{
	private Integer id;
	//由于已经存在了account属性，该属性中有ID值
	//可以替代accountId属性，因此这个属性就多余了，去掉它
	//private Integer accountId
	private String unix_host;
	private String os_username;
	private String login_passwd;
	private String status;
	private Date create_date;
	private Date pause_date;
	private Date close_date;
	private Integer cost_id;
	
	//追加属性，封装对应的Account，该属性已经包含了accountId
	private Account account;
	
	
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUnix_host() {
		return unix_host;
	}
	public void setUnix_host(String unix_host) {
		this.unix_host = unix_host;
	}
	public String getOs_username() {
		return os_username;
	}
	public void setOs_username(String os_username) {
		this.os_username = os_username;
	}
	public String getLogin_passwd() {
		return login_passwd;
	}
	public void setLogin_passwd(String login_passwd) {
		this.login_passwd = login_passwd;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public Date getPause_date() {
		return pause_date;
	}
	public void setPause_date(Date pause_date) {
		this.pause_date = pause_date;
	}
	public Date getClose_date() {
		return close_date;
	}
	public void setClose_date(Date close_date) {
		this.close_date = close_date;
	}
	public Integer getCost_id() {
		return cost_id;
	}
	public void setCost_id(Integer cost_id) {
		this.cost_id = cost_id;
	}
	
}
