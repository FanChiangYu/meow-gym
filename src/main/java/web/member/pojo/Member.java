package web.member.pojo;

import java.sql.Timestamp;

import core.pojo.Core;

public class Member extends Core {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String username;
	private String password;
	private String nickname;
	private Boolean pass;
	private Integer roleId;
	private String creator;
	private Timestamp createdAt;
	private String updater;
	private Timestamp lastUpdatedAt;
	
	public Member() {
	}

	public Member(Integer id, String username, String password, String nickname, Boolean pass, Integer roleId,
			String creator, Timestamp createdAt, String updater, Timestamp lastUpdatedAt) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.nickname = nickname;
		this.pass = pass;
		this.roleId = roleId;
		this.creator = creator;
		this.createdAt = createdAt;
		this.updater = updater;
		this.lastUpdatedAt = lastUpdatedAt;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Boolean getPass() {
		return pass;
	}

	public void setPass(Boolean pass) {
		this.pass = pass;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	public Timestamp getLastUpdatedAt() {
		return lastUpdatedAt;
	}

	public void setLastUpdatedAt(Timestamp lastUpdatedAt) {
		this.lastUpdatedAt = lastUpdatedAt;
	}
}