package net.mithra.familly.security.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserOpen implements UserDetails, Serializable {


	private static final long serialVersionUID = -4807147726869220726L;
	
	@NotNull
	private String uid;

	@NotNull
	private String nom;
	
	private String prenom;
	
	private String mail;
	
	private boolean blocked;
	
	private String password;
	
	private boolean adminPortail = false;
	
	private Collection<? extends GrantedAuthority> authorities;
	
	public UserOpen(){
		nom=new String();
		prenom=new String();
		
		mail = new String();
		authorities = new ArrayList<GrantedAuthority>();
	}
	
	

	public String getUid() {
		return uid;
	}



	public void setUid(String uid) {
		this.uid = uid;
	}



	public String getNom() {
		return nom;
	}



	public void setNom(String nom) {
		this.nom = nom;
	}



	public String getPrenom() {
		return prenom;
	}



	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}



	public String getMail() {
		return mail;
	}



	public void setMail(String mail) {
		this.mail = mail;
	}



	public boolean isBlocked() {
		return blocked;
	}



	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}



	public boolean isAdminPortail() {
		return adminPortail;
	}

	public void setAdminPortail(boolean adminPortail) {
		this.adminPortail = adminPortail;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}



	@Override
	public String getUsername() {
		return getUid();
	}

	@Override
	public boolean isAccountNonExpired() {
		// Les comptes n'expirent jamais.
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !isBlocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// L'expiration des mots de passe ne doit pas empecher de se logger.
		// Cela est géré via isMdpExpire().
		return true;
	}

	@Override
	public boolean isEnabled() {
		return !isBlocked();
	}
	

}
