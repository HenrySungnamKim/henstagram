package com.henry.boot.domain;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class User extends Time {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable=false, length=40, unique=true)
	private String userId;
	private String userEmail;
	
	@Column(nullable=false, length=40)
	private String userName;
	private String userPassword;

	@OneToMany(cascade = CascadeType.ALL,mappedBy="user")
	private Collection<Following> following;

	@Column(nullable=false, length=255, columnDefinition="VARCHAR(255) default''")
	private String userIntro="";

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public Collection<Following> getFollowing() {
		return following;
	}

	public void setFollowing(Collection<Following> following) {
		this.following = following;
	}

	public String getUserIntro() {
		return userIntro;
	}

	public void setUserIntro(String userIntro) {
		this.userIntro = userIntro;
	}
	
	public void update(User newUser) {
		this.userEmail=newUser.userEmail;
		this.userId=newUser.userId;
		this.userName=newUser.userName;
		this.userPassword=newUser.userPassword;
		this.userIntro=newUser.userIntro;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userId=" + userId + ", userEmail=" + userEmail + ", userName=" + userName
				+ ", userPassword=" + userPassword + ", following=" + following + ", userIntro=" + userIntro + "]";
	}
		
}