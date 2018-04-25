package hades.bg.datatype;

import java.math.BigDecimal;
import java.util.Date;

public enum MysqlDataType {

    // int
    TINYINT, SMALLINT, MEDIUMINT, INT, INTEGER,
    // long
    BIGINT,
    // double
    FLOAT, DOUBLE,
    // BigDecimal
    DECIMAL,
    // String
    CHAR, VARCHAR, ENUM, TEXT, JSON,
    // Data
    TIMESTAMP, DATETIME, DATE,
    // byte[]
    BLOB;

    public Class<?> getPropClass() {
        switch (this) {
        case TINYINT:
        case SMALLINT:
        case MEDIUMINT:
        case INT:
        case INTEGER:
            return int.class;
        case FLOAT:
        case DOUBLE:
            return double.class;
        case BIGINT:
            return long.class;
        case DECIMAL:
            return BigDecimal.class;
        case CHAR:
        case VARCHAR:
        case ENUM:
        case TEXT:
        case JSON:
            return String.class;
        case DATETIME:
        case TIMESTAMP:
        case DATE:
            return Date.class;
        case BLOB:
            return byte[].class;
        default:
            return null;
        }
    }
}
