package hades.bg.util;

public class CamelCaseUtil {
    private static final String REGEX = "_";

    public static String getClassName(String tableName) {
        String[] split = tableName.split(REGEX);
        StringBuilder sb = new StringBuilder();
        for (String str : split) {
            if (!"".equals(str)) {
                sb.append(str.substring(0, 1).toUpperCase());
                if (str.length() > 1) {
                    sb.append(str.substring(1, str.length()).toLowerCase());
                }
            }
        }
        return sb.toString();
    }

    public static String getFieldName(String columnName) {
        String[] split = columnName.split(REGEX);
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (String str : split) {
            if (!"".equals(str)) {
                if (first) {
                    sb.append(str.toLowerCase());
                    first = false;
                } else {
                    sb.append(str.substring(0, 1).toUpperCase());
                    if (str.length() > 1) {
                        sb.append(str.substring(1, str.length()).toLowerCase());
                    }
                }
            }
        }
        return sb.toString();
    }
}
