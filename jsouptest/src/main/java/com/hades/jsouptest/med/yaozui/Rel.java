package com.hades.jsouptest.med.yaozui;

import java.sql.SQLException;
import java.util.List;

public class Rel {

    public void buildRel(String jibing_code, String yaopin_code) throws SQLException {
//        DBHelper.executeUpdateExp(
//                "INSERT IGNORE INTO med_jibing_yaopin_rel (jibing_code, yaopin_code, create_time, update_time) VALUES (?, ?, now(), now())",
//                new String[] { jibing_code, yaopin_code });
    }

    public List<String[]> findUrlOfYaopin(String url) throws SQLException {
        throw new UnsupportedOperationException("TODO");
//        return DBHelper.queryDatas("SELECT * FROM med_yaopin WHERE url = ?", 1, new String[] { url });
    }
}
