package com.excilys.computerdatabase.entities;

public enum SQLSort {
    ASC(" asc"), DESC(" desc");

    private String sqlSyntax;

    SQLSort(String sqlSyntax) {
        this.sqlSyntax = sqlSyntax;
    }

    public String getSqlSyntax() {
        return this.sqlSyntax;
    }

    @Override
    public String toString() {
        return this.sqlSyntax;
    }
}
