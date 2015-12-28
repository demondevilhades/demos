package test.stock.db;

public final class UUID {

    static String getUUID() {
        return java.util.UUID.randomUUID().toString().replaceAll("-", "");
    }
}
