package models;

import java.util.List;

import be.objectify.deadbolt.java.models.Permission;
import be.objectify.deadbolt.java.models.Role;
import be.objectify.deadbolt.java.models.Subject;

public class Usuario implements Subject{

	private String login;
	private String nome;
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String getIdentifier() {
		return login;
	}
	
	@Override
	public List<? extends Permission> getPermissions() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<? extends Role> getRoles() {
		// TODO Auto-generated method stub
		return null;
	}
}
