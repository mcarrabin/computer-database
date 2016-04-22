package com.excilys.computerdatabase.entities;

import java.time.LocalDateTime;

public class Computer {
	private long id;
	private LocalDateTime introduced;
	private LocalDateTime discontinued;
	private Company company;
	private String name;
	
	/**
	 * Builder du computer
	 * @author excilys
	 *
	 */
	public static class ComputerBuilder {
		private Computer computer;
		
		public ComputerBuilder(){
			this.computer = new Computer();
		}
		
		public Computer build(){
			return computer;
		}
		
		public ComputerBuilder introduced(LocalDateTime introduced){
			this.computer.setIntroduced(introduced);
			return this;
		}
		
		public ComputerBuilder discontinued(LocalDateTime discontinued){
			this.computer.setDiscontinued(discontinued);
			return this;
		}
		
		public ComputerBuilder name(String name){
			this.computer.setName(name);
			return this;
		}
		
		public ComputerBuilder company(Company company){
			this.computer.setCompany(company);
			return this;
		}
		
		public ComputerBuilder id(long id){
			this.computer.setId(id);
			return this;
		}
	}

//	public Computer(long id, String name, LocalDateTime introduced, LocalDateTime discontinued,
//			Company company) {
//		this.id = id;
//		this.name = name;
//		this.introduced = introduced;
//		this.discontinued = discontinued;
//		this.company = company;
//	}

	public Computer() {
		this.id = -1;
		this.name = "";
		this.introduced = null;
		this.discontinued = null;
		this.company = new Company();
	}

	public long getId() {
		return this.id;
	}

	public LocalDateTime getIntroduced() {
		return this.introduced;
	}

	public LocalDateTime getDiscontinued() {
		return this.discontinued;
	}

	public Company getCompany() {
		return this.company;
	}

	public String getName() {
		return this.name;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setIntroduced(LocalDateTime introduced) {
		this.introduced = introduced;
	}

	public void setDiscontinued(LocalDateTime discontinued) {
		this.discontinued = discontinued;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + id);
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
		Computer other = (Computer) obj;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (discontinued == null) {
			if (other.discontinued != null)
				return false;
		} else if (!discontinued.equals(other.discontinued))
			return false;
		if (id != other.id)
			return false;
		if (introduced == null) {
			if (other.introduced != null)
				return false;
		} else if (!introduced.equals(other.introduced))
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
		return "Computer [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued + "\n\t company="
				+ company.toString() + "]";
	}
}
