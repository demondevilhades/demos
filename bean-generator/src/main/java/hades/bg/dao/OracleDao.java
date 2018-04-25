package hades.bg.dao;

import hades.bg.bean.ColumnInfo;
import hades.bg.datatype.OracleDataType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OracleDao extends DBAbstractDao {

    public Map<String, List<ColumnInfo>> getTableInfoMap(String owner) throws SQLException {
        String sql = "SELECT * FROM user_tab_cols WHERE TABLE_NAME IN ( SELECT TABLE_NAME FROM dba_tables WHERE OWNER = ? )";
        Map<String, List<ColumnInfo>> map = new HashMap<String, List<ColumnInfo>>();
        processingSelect(new RsOperator() {

            @Override
            public void operator(ResultSet rs) throws SQLException {
                String tableName = rs.getString("TABLE_NAME");
                String dataType = rs.getString("DATA_TYPE");
                List<ColumnInfo> list = map.get(tableName);
                if (list == null) {
                    list = new ArrayList<ColumnInfo>();
                    map.put(tableName, list);
                }
                list.add(new ColumnInfo(tableName, rs.getString("COLUMN_NAME"), dataType, OracleDataType.valueOf(
                        dataType).getPropClass()));
            }
        }, sql, new Object[] { owner });
        return map;
    }
}
