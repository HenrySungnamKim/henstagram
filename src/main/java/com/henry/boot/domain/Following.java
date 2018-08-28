package com.henry.boot.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Following {
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="user_id")
	private User user;
	
	private Long followingId;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getFollowingId() {
		return followingId;
	}

	public void setFollowingId(Long followingId) {
		this.followingId = followingId;
	}

	
}
