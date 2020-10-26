package com.bs.beans;

import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"emailId"}))
public class User {

	
	//@TableGenerator( name = "USER_GEN",table = "USER_GENERATOR_TABLE", initialValue = 7,allocationSize = 1)
	//@GeneratedValue(strategy = GenerationType.TABLE,generator = "USER_GEN")
	@SequenceGenerator(name = "USER_ID_GENERATOR", sequenceName = "user_sequence", initialValue = 7,allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_ID_GENERATOR")
	@Id
	private int userId;
	private String emailId;
	private String name;
	private String password;
	private boolean active;
	private String roles;
	//mappedBy - gets the name of the variable on the many side on which @ManyToOne annotation is used
	//@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@OneToMany( cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@MapKey(name = "tradeId") 
	@JsonIgnore   // ignores unecessary json data while returning the map of trades 
	@JoinColumn(name = "userId")
	private Map<Integer, Trade> trades;
	
	public User() {
		super();
	}

	public User(int userId, String emailId, String name, String password, boolean active, String roles,
			Map<Integer, Trade> trades) {
		super();
		this.userId = userId;
		this.emailId = emailId;
		this.name = name;
		this.password = password;
		this.active = active;
		this.roles = roles;
		this.trades = trades;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + userId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (userId != other.userId)
			return false;
		return true;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public Map<Integer, Trade> getTrades() {
		return trades;
	}

	public void setTrades(Map<Integer, Trade> trades) {
		this.trades = trades;
	}



	@Override
	public String toString() {
		return "User [userId=" + userId + ", emailId=" + emailId + ", name=" + name + ", password=" + password
				+ ", active=" + active + ", roles=" + roles + ", trades=" + trades + "]";
	}



	public String getEmailId() {
		return emailId;
	}



	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	
}
