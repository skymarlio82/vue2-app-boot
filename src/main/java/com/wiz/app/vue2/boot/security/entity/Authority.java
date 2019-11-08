
package com.wiz.app.vue2.boot.security.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name="AUTHORITY")
public class Authority {

	@Id
	@Column(name="ID")
	private Long id = 0L;

	@Column(name="NAME", length=50)
	@NotNull
	@Enumerated(EnumType.STRING)
	private AuthorityName name = null;

	@ManyToMany(mappedBy="authorities", fetch=FetchType.LAZY)
	private List<User> users = null;
}