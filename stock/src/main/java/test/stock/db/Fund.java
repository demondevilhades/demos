package test.stock.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class Fund {

    public void select() {
        String sql = "select * from fund";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = MysqlConnextion.getConn();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String str = rs.getString(0);
                System.out.println(str);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void insert(JSONArray jsonArray, String tableName, String pk) {
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        Set<String> keySet = jsonObject.keySet();
        String[] keyArr = keySet.toArray(new String[0]);

        StringBuilder sb = new StringBuilder("insert into ");
        StringBuilder placeholder = new StringBuilder(" value ( ?");
        sb.append(tableName).append(" ( ").append(pk);
        for (int i = 0; i < keyArr.length; i++) {
            sb.append(" , ").append(keyArr[i]);
            placeholder.append(" , ? ");
        }

        String sql = sb.append(" ) ").append(placeholder).append(")").toString();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = MysqlConnextion.getConn();
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(sql);
            insertBatch(ps, jsonArray, keyArr);
            int[] result = ps.executeBatch();
            for (int i = 0; i < result.length; i++) {
                if (result[i] != 1) {
                    throw new SQLException("result[" + i + "] = 0");
                }
            }
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertBatch(PreparedStatement ps, JSONArray jsonArray, String[] keyArr) throws SQLException {
        int size = jsonArray.size();
        int paramSize = keyArr.length;
        JSONObject jsonObject = null;
        for (int i = 0; i < size; i++) {
            jsonObject = jsonArray.getJSONObject(i);
            ps.setString(1, UUID.getUUID());
            for (int j = 0; j < paramSize; j++) {
                ps.setString(j + 2, jsonObject.getString(keyArr[j]));
            }
            ps.addBatch();
        }
    }
}
