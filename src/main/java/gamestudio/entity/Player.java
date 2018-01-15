package gamestudio.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Player {
	@Id
	@GeneratedValue
	private int ident;
public int getIdent() {
		return ident;
	}

	public void setIdent(int ident) {
		this.ident = ident;
	}

private String login;
private String password;



public void setLogin(String login) {
	this.login = login;
}

public void setPassword(String password) {
	this.password = password;
}

public String getPassword() {
	return password;
}

public String getLogin() {
	return login;
}


}
