package com.bs.beans;

import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int userId;
	private String name;
	private String password;
	private boolean active;
	private String roles;
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@MapKey(name = "tradeId")
	@JsonIgnore
	private Map<Integer, Trade> trades;

	public User() {
		super();
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", password=" + password + ", active=" + active
				+ ", roles=" + roles + ", trades=" + trades + "]";
	}

	public User(int userId, String name, String password, boolean active, String roles, Map<Integer, Trade> trades) {
		super();
		this.userId = userId;
		this.name = name;
		this.password = password;
		this.active = active;
		this.roles = roles;
		this.trades = trades;
	}

}
