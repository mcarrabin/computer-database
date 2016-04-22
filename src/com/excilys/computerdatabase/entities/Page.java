package com.excilys.computerdatabase.entities;

import java.util.ArrayList;
import java.util.List;

public class Page {
	private int nbreLine;
	private int numPage;
	private List<Computer> computers;
	
	public Page(){
		this.nbreLine = 0;
		this.numPage = 0;
		this.computers = new ArrayList<Computer>();
	}
	
	public static class PageBuilder{
		private Page page;
		
		public PageBuilder(){
			this.page = new Page();
		}
		
		public PageBuilder nbreLine(int nbreLine){
			this.page.setNbreLine(nbreLine);
			return this;
		}
		
		public PageBuilder numPage(int numPage){
			this.page.setNumPage(numPage);
			return this;
		}
		
		public PageBuilder computers(List<Computer> computers){
			this.page.setComputers(computers);
			return this;
		}
		
		public Page build(){
			return this.page;
		}
	}

	public int getNbreLine() {
		return nbreLine;
	}

	public void setNbreLine(int nbreLine) {
		this.nbreLine = nbreLine;
	}

	public int getNumPage() {
		return numPage;
	}

	public void setNumPage(int numPage) {
		this.numPage = numPage;
	}

	public List<Computer> getComputers() {
		return computers;
	}

	public void setComputers(List<Computer> computers) {
		this.computers = computers;
	}
	
	

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		for(Computer c : computers){
			result.append(c.toString()).append("\n");
		}
		result.append("Page [nbreLine=" + nbreLine + ", numPage=" + numPage + "]");
		return result.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + numPage;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Page other = (Page) obj;
		if (computers == null) {
			if (other.computers != null)
				return false;
		} else if (!computers.equals(other.computers))
			return false;
		if (nbreLine != other.nbreLine)
			return false;
		if (numPage != other.numPage)
			return false;
		return true;
	}
}
