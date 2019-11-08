
package com.wiz.app.vue2.boot.security.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name="USER")
public class User {

	@Id
	@Column(name="ID")
	private Long id = 0L;

	@Column(name="USERNAME", length=50, unique=true)
	@NotNull
	@Size(min=4, max=50)
	private String username = null;

	@Column(name="PASSWORD", length=100)
	@NotNull
	@Size(min=4, max=100)
	private String password = null;

	@Column(name="ENABLED")
	@NotNull
	private Boolean enabled = false;

	@Column(name="LASTPASSWORDRESETDATE")
	@NotNull
	private Date lastPasswordResetDate = null;

	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="USER_AUTHORITY", joinColumns={ @JoinColumn(name="USER_ID", referencedColumnName="ID") }, inverseJoinColumns={ @JoinColumn(name="AUTHORITY_ID", referencedColumnName="ID") })
	private List<Authority> authorities = null;

	@Column(name="MENU")
	@NotNull
	private String menu = null;

	public User() {
		
	}
}