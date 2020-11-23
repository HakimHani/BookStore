package com.springboot.bookshop;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.springboot.bookshop.enums.AccountType;


@Entity
@Table(name="account")
public class Account implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", insertable=true, updatable=true, unique=true, nullable=false)
	private long id;
	
	@Column(name="email", nullable = false, unique = true)
	private String email;

	@Column(name="password", nullable = false)
	private String password;

	@Column(name="account_type", nullable = false)
	private AccountType accountType;
	
	@Column(name="register_date", nullable = false)
	private String registerDate;




	public Account() {

	}

	public Account(String email, String password,AccountType accountType,String registerDate) {
		super();
		this.email = email;
		this.password = password;
		this.accountType = accountType;
		this.registerDate = registerDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public String getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}






}
