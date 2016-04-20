package com.excilys.computerdatabase.entities;

public class CompanyEntity {
	private int id;
	private String name;
	
	public CompanyEntity(){
		id = -1;
		name = "";
	}
	
	public CompanyEntity(int pId, String pName){
		this.id = pId;
		this.name = pName;
	}
	
	public int getId(){
		return this.id;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setId(int pId){
		this.id = pId;
	}
	
	public void setName(String pName){
		this.name = pName;
	}
	
	public String toString(){
		String res = "";
		res += "Name: " + this.name + "\n";
		res += "Id: " + this.id + "\n";
		
		return res;
	}
}
