package com.excilys.computerdatabase.entities;

public enum SQLColumnNames {
    COMPUTER_NAME(" c.name"), INTRODUCED(" c.introduced"), DISCONTINUED(" c.discontinued"), COMPANY_NAME(" comp.name");

    private String sqlSyntax = "";

    // Constructeur
    SQLColumnNames(String sqlSyntax) {
        this.sqlSyntax = sqlSyntax;
    }

    public String getSqlSyntax() {
        return this.sqlSyntax;
    }

    @Override
    public String toString() {
        return sqlSyntax;
    }
}
