package net.mithra.familly.ws.controller.model;

public class UserInfoModel extends CommonModel{


	private String login;
	private String email;
	private Long avatar;

	
	public UserInfoModel(){
		super();
	}
	


	
public UserInfoModel(String login, String email, Long avatar) {
		super();
		this.login = login;
		this.email = email;
		this.avatar = avatar;
	}




public String getLogin() {
	return login;
}




public void setLogin(String login) {
	this.login = login;
}




public String getEmail() {
	return email;
}




public void setEmail(String email) {
	this.email = email;
}




public Long getAvatar() {
	return avatar;
}




public void setAvatar(Long avatar) {
	this.avatar = avatar;
}






	
	
}
