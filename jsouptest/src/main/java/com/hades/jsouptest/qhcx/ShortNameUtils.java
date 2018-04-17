package com.hades.jsouptest.qhcx;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShortNameUtils {

    public void run() throws Exception {
        Map<String, Integer> map = new HashMap<String, Integer>();

        String layerNum = "2";
        List<String[]> list = null;
//        List<String[]> list = DBHelper.queryDatas("select name from med_region WHERE layer_num = ?", 1,
//                new String[] { layerNum });
//        List<String[]> list = DBHelper.queryDatas("select name from med_region WHERE layer_num = ? and `name` not like '%街道办事处' and `name` not like '%镇' and `name` not like '%乡' and `name` not like '%管理委员会'", 1,
//                new String[] { "3" });

        String str0;
        String[] split0;
        String str1;
        String[] split1;
        int k;
        int a;
        int b;
        for (int i = 0; i < list.size(); i++) {
            str0 = list.get(i)[0];
            if (str0.endsWith("街道办事处") || str0.endsWith("镇") || str0.endsWith("乡")
                    || str0.endsWith("管理委员会")) {
                continue;
            }
            split0 = str0.split("");
            for (int j = i + 1; j < list.size(); j++) {
                str1 = list.get(j)[0];
                if (str0.endsWith("街道办事处") || str0.endsWith("镇") || str0.endsWith("乡")
                        || str0.endsWith("管理委员会")) {
                    continue;
                }
                split1 = str1.split("");
                k = 0;
                a = split0.length;
                b = split1.length;
                while (a > 0 && b > 0 && split0[--a].equals(split1[--b])) {
                    k++;
                }
                if (k > 0) {
                    String key = str0.substring(split0.length - k, split0.length);
                    if (map.containsKey(key)) {
                        map.put(key, map.get(key) + 1);
                    } else {
                        map.put(key, 1);
                    }
                }
            }
        }

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "\t" + entry.getValue());
        }
    }

    public static void main(String[] args) throws Exception {
        new ShortNameUtils().run();
    }
}
