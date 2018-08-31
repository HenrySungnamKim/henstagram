package com.henry.boot.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Access(AccessType.PROPERTY)
@Table(name = "USERS")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String userId;
	private String userName;
	private String userPassword;
	private String userEmail;
	private String userIntro = "";
	private Set<User> followers;
	private Set<User> following;

	public User() {
	}

	public User(String userName, String userId, String userPassword, String userEmail) {
		this.userName = userName;
		this.userId = userId;
		this.userPassword = userPassword;
		this.userEmail = userEmail;
		this.followers = new HashSet<User>();
		this.following = new HashSet<User>();
	}

	public void update(User newUser) {
		this.userEmail = newUser.userEmail;
		this.userId = newUser.userId;
		this.userName = newUser.userName;
		this.userPassword = newUser.userPassword;
		this.userIntro = newUser.userIntro;
		this.followers = new HashSet<User>();
		this.following = new HashSet<User>();
	}

	public boolean matchPassword(String matchUserPassword) {

		if (matchUserPassword == null) {
			return false;
		}
		return matchUserPassword.equals(userPassword);

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Basic
	@Column(nullable = false, unique = true)
	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(nullable = false)
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

	@Column(nullable = true, length = 255, columnDefinition = "VARCHAR(255)")
	public String getUserIntro() {
		return userIntro;
	}

	public void setUserIntro(String userIntro) {
		this.userIntro = userIntro;
	}

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "USER_RELATIONS", joinColumns = @JoinColumn(name = "FOLLOWED_ID"), inverseJoinColumns = @JoinColumn(name = "FOLLOWER_ID"))
	public Set<User> getFollowers() {
		return followers;
	}

	public void setFollowers(Set<User> followers) {
		this.followers = followers;
	}

	public void addFollower(User follower) {
		followers.add(follower);
		follower.following.add(this);
	}

	@ManyToMany(mappedBy = "followers")
	public Set<User> getFollowing() {
		return following;
	}

	public void setFollowing(Set<User> following) {
		this.following = following;
	}

	public void addFollowing(User followed) {
		followed.addFollower(this);
	}

}