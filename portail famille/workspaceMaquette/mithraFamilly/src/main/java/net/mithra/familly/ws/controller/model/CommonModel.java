package net.mithra.familly.ws.controller.model;

public class CommonModel {
	
	private String name;
	private String id;
	
	
	public CommonModel() {
		super();
	}
	public CommonModel(String id, String name) {
		super();
		this.name = name;
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	

}
