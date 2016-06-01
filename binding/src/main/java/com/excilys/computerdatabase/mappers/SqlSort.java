package com.excilys.computerdatabase.mappers;

import com.excilys.computerdatabase.entities.SQLColumnNames;

public class SqlSort {

    /**
     * Method that will get the SQL column name for the sorting query based on
     * the jsp column name.
     *
     * @param name
     *            is the jsp version column name
     * @return the SQL version of the column name (prefixed with table alias).
     */
    public static String getSortColumn(String name) {
        for (SQLColumnNames s : SQLColumnNames.values()) {
            if (s.getName().equalsIgnoreCase(name)) {
                return s.getSqlSyntax();
            }
        }
        return "";
    }
}
