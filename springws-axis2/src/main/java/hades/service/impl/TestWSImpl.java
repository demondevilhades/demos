package hades.service.impl;

import javax.jws.WebParam;

import hades.service.TestWS;

/**
 * http://localhost:8091/springws/services/testWS1?wsdl
 * 
 * wsdl2java.bat -uri http://localhost:8088/springws/ws/testWS?wsdl -p
 * com.agrantsem.testws.test -o D:/stub -s –noBuildXML
 * 
 * @author zhangshuai
 */
public class TestWSImpl implements TestWS {

    @Override
    public String aaa(@WebParam(name = "str") String str) {
        System.out.println("str:" + str);
        return "aaa_return";
    }

    @Override
    public String bbb(String str) {
        System.out.println("str:" + str);
        return "bbb_return";
    }
}
