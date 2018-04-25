package hades.bg.datatype;

import java.math.BigDecimal;
import java.util.Date;

public enum OracleDataType {

    // int
    NUMBER, INT, INTEGER, SMALLINT,
    // long
    LONG,
    // double
    FLOAT,
    // BigDecimal
    DECIMAL,
    // String
    CHAR, NCHAR, VARCHAR2, NVARCHAR2,
    // Data
    DATE, TIMESTAMP,
    // byte
    BLOB, CLOB;

    public Class<?> getPropClass() {
        switch (this) {
        case NUMBER:
        case INT:
        case INTEGER:
        case SMALLINT:
            return int.class;
        case FLOAT:
            return double.class;
        case LONG:
            return long.class;
        case DECIMAL:
            return BigDecimal.class;
        case CHAR:
        case NCHAR:
        case VARCHAR2:
        case NVARCHAR2:
            return String.class;
        case DATE:
        case TIMESTAMP:
            return Date.class;
        case BLOB:
        case CLOB:
            return byte[].class;
        default:
            return null;
        }
    }
}
