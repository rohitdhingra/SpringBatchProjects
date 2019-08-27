package com.rohit.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name="User2")
public class User {
	@Id
	private int id;
	private String name;
	private String profession;
	private int age;
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(int id, String name, String profession, int age) {
		super();
		this.id = id;
		this.name = name;
		this.profession = profession;
		this.age = age;
	}

}
