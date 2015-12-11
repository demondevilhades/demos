package test.stock.clint.impl.wmcloud;

public class FundParam {

    public static enum EtfLof {
        ETF("etf"), LOF("lof");

        private final String value;

        private EtfLof(String value) {
            this.value = value;
        }

        public String value() {
            return value;
        }
    }

    public static enum ListStatusCd {
        L("l"), S("s"), DE("de"), UN("un");

        private final String value;

        private ListStatusCd(String value) {
            this.value = value;
        }

        public String value() {
            return value;
        }
    }

    public static enum IsClass {
        M("1"), A("2"), B("3"), N("0");

        private final String value;

        private IsClass(String value) {
            this.value = value;
        }

        public String value() {
            return value;
        }
    }
}
