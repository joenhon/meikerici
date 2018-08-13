package com.util;


import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;
import com.entity.account;
import org.apache.commons.codec.binary.Base64;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
/*import com.tn.base.Log;
import com.tn.entity.coin.UserCoinAddressEntity;
import com.tn.util.BigDecimalUtil;*/
import com.util.HttpUtil;


public class ustd {
    //private Logger log = Logger.getLogger("");
    /**
     * USDT查询余额
     * @return
     * */
     public String getBalance(String address){
         JSONObject json = doRequest(account.METHOD_GET_BALANCE,address,account.propertyid);
         if(isError(json)){
             System.out.println(json.get("error"));
            //log.error("获取USDT余额:{}",json.get("error"));
            return null;
         }
         return json.getString(account.RESULT);
     }
    public String newAddress(){
        JSONObject json = doRequest(account.METHOD_NEW_ADDRESS);
        if(isError(json)){
            System.out.println(json.get("error"));
            //log.error("获取USDT地址失败:{}",json.get("error"));
            return "";
        }
        return json.getString(account.RESULT);

    }
    public String getAddresses(){
        JSONObject json = doRequest("getaddressesbyaccount","");
        if(isError(json)){
            System.out.println(json.get("error"));
            //log.error("获取USDT地址失败:{}",json.get("error"));
            return "";
        }
        return json.getString(account.RESULT);
    }
    /**
     *
     * @param json
     * @return
     */
    private boolean isError(JSONObject json){
        if( json == null || (StringUtils.isNotEmpty(json.getString("error")) && json.get("error") != "null")){
            return true;
        }
        return false;
    }

    private JSONObject doRequest(String method,Object... params){
        JSONObject param = new JSONObject();
        param.put("id",System.currentTimeMillis()+"");
        param.put("jsonrpc","2.0");
        param.put("method",method);
        if(params != null){
            param.put("params",params);
        }
        String creb = Base64.encodeBase64String((account.username+":"+account.password).getBytes());
        Map<String,String> headers = new HashMap<>(2);
        headers.put("Authorization","Basic "+creb);
        String resp = "";
        if (account.METHOD_GET_TRANSACTION.equals(method)){
            try{
                resp = HttpUtil.jsonPost(account.url,headers,param.toJSONString());
            }catch (Exception e){
                if (e instanceof IOException){
                    resp = "{}";
                }
            }
        }else{
            resp = HttpUtil.jsonPost(account.url,headers,param.toJSONString());
        }
        return JSON.parseObject(resp);
    }

}
