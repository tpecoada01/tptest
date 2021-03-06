package fr.adaming.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * classe admin
 * @author inti0277
 *
 */

@Entity
@Table(name="admins")
public class Admin {
	
/**
 * 	attributs
 */
	@Id
	@Column(name="id_admin")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idAdmin;
	
	@Column(name="nom_admin")
	private String nomAdmin;
	@Column(name="password_admin")
	private String passwordAdmin;
	
/**
 * 	get et set
 * @return
 */
	public long getIdAdmin() {
		return idAdmin;
	}
	public void setIdAdmin(long idAdmin) {
		this.idAdmin = idAdmin;
	}
	public String getNomAdmin() {
		return nomAdmin;
	}
	public void setNomAdmin(String nomAdmin) {
		this.nomAdmin = nomAdmin;
	}
	public String getPasswordAdmin() {
		return passwordAdmin;
	}
	public void setPasswordAdmin(String passwordAdmin) {
		this.passwordAdmin = passwordAdmin;
	}
	
/**
 * 	constructeurs
 * @param idAdmin
 * @param nomAdmin
 * @param passwordAdmin
 */
	public Admin(long idAdmin, String nomAdmin, String passwordAdmin) {
		super();
		this.idAdmin = idAdmin;
		this.nomAdmin = nomAdmin;
		this.passwordAdmin = passwordAdmin;
	}
	
	
public Admin() {
	super();
}



public Admin(String nomAdmin, String passwordAdmin) {
	super();
	this.nomAdmin = nomAdmin;
	this.passwordAdmin = passwordAdmin;
}

@Override
public String toString() {
	return "Admin [idAdmin=" + idAdmin + ", nomAdmin=" + nomAdmin + ", passwordAdmin=" + passwordAdmin + "]";
}

	

	
}
