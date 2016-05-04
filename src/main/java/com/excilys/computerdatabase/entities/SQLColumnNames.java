package com.excilys.computerdatabase.entities;

public enum SQLColumnNames {
    COMPUTER_NAME("name", " c.name"), INTRODUCED("introduced", " c.introduced"), DISCONTINUED("discontinued",
            " c.discontinued"), COMPANY_NAME("company", " comp.name");

    private String name = "";
    private String sqlSyntax = "";

    /**
     * public constructor.
     *
     * @param name
     *            is the name received and send in the jsp and servlets.
     * @param sqlSyntax
     *            is the value used in the dao to apply sorting.
     */
    SQLColumnNames(String name, String sqlSyntax) {
        this.name = name;
        this.sqlSyntax = sqlSyntax;
    }

    public String getName() {
        return name;
    }

    public String getSqlSyntax() {
        return sqlSyntax;
    }

    @Override
    public String toString() {
        return sqlSyntax;
    }
}
