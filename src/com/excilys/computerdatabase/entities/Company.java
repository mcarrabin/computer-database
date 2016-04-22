package com.excilys.computerdatabase.entities;

public class Company {
	private long id;
	private String name;

	public Company() {
		id = -1;
		name = "";
	}
	
	public static class CompanyBuilder {
		private Company company;
		
		public CompanyBuilder(){
			this.company = new Company();
		}
		
		public CompanyBuilder id(long id){
			this.company.setId(id);
			return this;
		}
		
		public CompanyBuilder name(String name){
			this.company.setName(name);
			return this;
		}
		
		public Company build(){
			return this.company;
		}
	}

//	public Company(Long id, String name) {
//		this.id = id;
//		this.name = name;
//	}

	public long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + id);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Company other = (Company) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + "]";
	}
}
