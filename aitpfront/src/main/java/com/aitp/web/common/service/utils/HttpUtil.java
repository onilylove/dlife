package com.aitp.web.common.service.utils;
import java.io.IOException;
import java.util.Base64;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http 工具类
 */
public class HttpUtil {
    static final Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);

    public static String doGetJson(String urlStr){
        String result="";
        HttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(urlStr);
        try {
            HttpResponse response = client.execute(get);
            int code = response.getStatusLine().getStatusCode();
            if(code==200){
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity, "UTF-8");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
      return result;
    }

    public static String doPostJson(String urlStr,JSONObject jsonObject){
        LOGGER.info("-------------do post json data: url>>{} data>>>{}",urlStr,jsonObject.toJSONString());
        String respContent = null;
        try {
            HttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(urlStr);
            StringEntity entity = new StringEntity(jsonObject.toString(),"utf-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            HttpResponse resp = client.execute(httpPost);
            LOGGER.info("Status Code:{}",resp.getStatusLine().getStatusCode());
            if(resp.getStatusLine().getStatusCode() == 201) {
                HttpEntity resultData = resp.getEntity();
                respContent = EntityUtils.toString(resultData,"UTF-8");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return respContent;
    }

    public static String baseEncoder(String text){
        final Base64.Encoder encoder = Base64.getEncoder();
        String encoderData=text;
        try {
            final byte[] textByte = text.getBytes("UTF-8");
            encoderData=encoder.encodeToString(textByte);
        } catch (Exception e) {
            LOGGER.error("Encoder error:{}",text);
        }
        return encoderData;
    }

    public static String baseDecoder(String encodeText){
        final Base64.Decoder decoder = Base64.getDecoder();
        String decodeData=encodeText;
        try {
            decodeData=new String(decoder.decode(encodeText), "UTF-8");
        } catch (Exception e) {
            LOGGER.error("Decoder error:{}",encodeText);
        }
        return decodeData;
    }

    public static void main(String[] args) {
        System.out.println(baseEncoder("毛磊"));
        System.out.println(baseDecoder("Mu+/vSjvv73vv70="));
//        String urlStr = "https://a5api.aitpgroup.tech/api/wechat-users";
//        JSONObject jsonObject=new JSONObject();
//        jsonObject.put("avatar","http://thirdwx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/46");
//        jsonObject.put("openId","88888888899");
//        jsonObject.put("nickName","Jerry");
//        jsonObject.put("sex",true);
//        String resultData = HttpUtil.doPostJson(urlStr,jsonObject);
//        System.out.println(resultData);
    }
}
