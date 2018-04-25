package hades.bg.bean;

public class ColumnInfo {

    private String tableName;
    private String columnName;
    private String columnType;
    private Class<?> fieldType;

    public ColumnInfo() {
    }

    public ColumnInfo(String tableName, String columnName, String columnType, Class<?> fieldType) {
        this.tableName = tableName;
        this.columnName = columnName;
        this.columnType = columnType;
        this.fieldType = fieldType;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public Class<?> getFieldType() {
        return fieldType;
    }

    public void setFieldType(Class<?> fieldType) {
        this.fieldType = fieldType;
    }
}
