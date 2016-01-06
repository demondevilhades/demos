package test.stock.clint.impl.wmcloud;

import org.junit.Test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class FundTest {

    private Fund fund = new Fund();

    // @Test
    public void getFundJsonStrTest() {
        System.out.println(fund.getFundJsonStr("161207"));
    }

//    @Test
    public void getFundJsonTestByEtfLof() {
        JSONArray jsonArray;
        int size;
        jsonArray = fund.getFundJson(null, null, FundParam.EtfLof.ETF.value(), null, null);
        size = jsonArray.size();
        System.out.println(size);
        for (int i = 0; i < size; i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            int isClass = jsonObject.getIntValue("isClass");
            if (isClass != 0) {
                System.out.println(jsonObject.getString("ticker"));
            }
        }
        System.out.println("===================");
        jsonArray = fund.getFundJson(null, null, FundParam.EtfLof.LOF.value(), null, null);
        size = jsonArray.size();
        System.out.println(size);
        for (int i = 0; i < size; i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            int isClass = jsonObject.getIntValue("isClass");
            if (isClass != 0) {
                System.out.println(jsonObject.getString("ticker"));
            }
        }

        // 结果显示 EFT 与 LOF 均无分级基金
    }

//    @Test
    public void getFundJsonTestByListStatusCd() {
        JSONArray jsonArray;
        int size;
        jsonArray = fund.getFundJson(null, FundParam.ListStatusCd.L.value(), null, null, null);
        size = jsonArray.size();
        System.out.println(size);
        for (int i = 0; i < size; i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            int isClass = jsonObject.getIntValue("isClass");
            if (isClass != 0) {
                System.out.println(jsonObject.getString("ticker"));
            }
        }
    }
    
    @Test
    public void getFundInfoStrTest(){
        String result = fund.getFundInfoStr("150278");
        System.out.println(result);
    }
}
