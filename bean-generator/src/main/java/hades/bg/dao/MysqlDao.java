package hades.bg.dao;

import hades.bg.bean.ColumnInfo;
import hades.bg.datatype.MysqlDataType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MysqlDao extends DBAbstractDao {

    @Override
    public Map<String, List<ColumnInfo>> getTableInfoMap(String owner) throws SQLException {
        String sql = "select TABLE_SCHEMA, TABLE_NAME, COLUMN_NAME, DATA_TYPE from information_schema.columns where table_schema = ?";
        Map<String, List<ColumnInfo>> map = new HashMap<String, List<ColumnInfo>>();
        processingSelect(new RsOperator() {

            @Override
            public void operator(ResultSet rs) throws SQLException {
                String tableName = rs.getString("TABLE_NAME");
                String dataType = rs.getString("DATA_TYPE").toUpperCase();
                List<ColumnInfo> list = map.get(tableName);
                if (list == null) {
                    list = new ArrayList<ColumnInfo>();
                    map.put(tableName, list);
                }
                list.add(new ColumnInfo(tableName, rs.getString("COLUMN_NAME"), dataType, MysqlDataType.valueOf(
                        dataType).getPropClass()));
            }
        }, sql, new Object[] { owner });
        return map;
    }
}
