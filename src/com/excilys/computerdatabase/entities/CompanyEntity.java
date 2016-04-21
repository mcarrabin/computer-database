package com.excilys.computerdatabase.entities;

public class CompanyEntity {
	private Long id;
	private String name;

	public CompanyEntity() {
		id = -1L;
		name = "";
	}

	public CompanyEntity(Long pId, String pName) {
		this.id = pId;
		this.name = pName;
	}

	public Long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public void setId(Long pId) {
		this.id = pId;
	}

	public void setName(String pName) {
		this.name = pName;
	}

	public String toString() {
		String res = "";
		res += "Name: " + this.name + "\n";
		res += "Id: " + this.id + "\n";

		return res;
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
		CompanyEntity other = (CompanyEntity) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
