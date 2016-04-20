package com.excilys.computerdatabase.entities;

public class Page {
	private int start;
	private int end;
	private int lineByPage;
	private int index;
	
	public int getStart(){
		return start;
	}
	public int getEnd(){
		return end;
	}
	public int getLineByPage(){
		return lineByPage;
	}
	public int getIndex(){
		return index;
	}
	public void setStart(int pStart){
		start = pStart;
	}
	public void setEnd(int pEnd){
		end = pEnd;
	}
	public void setLineByPage(int pLineByPage){
		lineByPage = pLineByPage;
	}
	public void setIndex(int pIndex){
		index = pIndex;
	}
}
