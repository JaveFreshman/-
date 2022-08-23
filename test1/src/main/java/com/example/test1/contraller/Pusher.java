package com.example.test1.contraller;

import com.alibaba.fastjson.JSONArray;
import com.example.test1.model.Weather;
import com.example.test1.utils.CaiHongPiUtils;
import com.example.test1.utils.JiNianRiUtils;
import com.example.test1.utils.TxtTest;
import com.example.test1.utils.WeatherUtils;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

import java.io.IOException;
import java.util.Map;

public class Pusher {

    public static void main(String[] args) throws IOException {
        push();
    }
    private static String appId = "";
    private static String secret = "";
    private static String mubanID = "";

    public static void push() throws IOException {
        WxMpInMemoryConfigStorage wxStorage = new WxMpInMemoryConfigStorage();
        appId = TxtTest.readTxt().getString("appID");
        secret = TxtTest.readTxt().getString("appsecret");
        wxStorage.setAppId(appId);
        wxStorage.setSecret(secret);
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxStorage);
        JSONArray jsonArray = TxtTest.readTxt().getJSONArray("user");
        mubanID = TxtTest.readTxt().getString("mubanID");
        for (int i = 0; i < jsonArray.size(); i ++){
            WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                    .toUser(jsonArray.getString(i))
                    .templateId(mubanID)
                    .build();
            Weather weather = WeatherUtils.getWeather();
            Map<String, String> map = CaiHongPiUtils.getEnsentence();
            templateMessage.addData(new WxMpTemplateData("riqi",weather.getDate() + "  "+ weather.getWeek(),"#00BFFF"));
            templateMessage.addData(new WxMpTemplateData("tianqi",weather.getText_now(),"#173177"));
            templateMessage.addData(new WxMpTemplateData("low",weather.getLow() + "","#173177"));
            templateMessage.addData(new WxMpTemplateData("temp",weather.getTemp() + "","#EE212D"));
            templateMessage.addData(new WxMpTemplateData("high",weather.getHigh()+ "","#FF6347" ));
            templateMessage.addData(new WxMpTemplateData("windclass",weather.getWind_class()+ "","#42B857" ));
            templateMessage.addData(new WxMpTemplateData("winddir",weather.getWind_dir()+ "","#B95EA3" ));
            templateMessage.addData(new WxMpTemplateData("caihongpi",CaiHongPiUtils.getCaiHongPi().split(",")[0],"#FF69B4"));
            templateMessage.addData(new WxMpTemplateData("caihongpi1",CaiHongPiUtils.getCaiHongPi().split(",")[1],"#FF69B4"));
            templateMessage.addData(new WxMpTemplateData("lianai", JiNianRiUtils.getLianAi()+"","#FF1493"));
            templateMessage.addData(new WxMpTemplateData("shengri1",JiNianRiUtils.getBirthday_Jo()+"","#FFA500"));
            templateMessage.addData(new WxMpTemplateData("shengri2",JiNianRiUtils.getBirthday_Hui()+"","#FFA500"));
            templateMessage.addData(new WxMpTemplateData("en",map.get("en") +"","#C71585"));
            templateMessage.addData(new WxMpTemplateData("zh",map.get("zh") +"","#C71585"));
            String beizhu = "❤";
            if(JiNianRiUtils.getLianAi() % 365 == 0){
                beizhu = "今天是恋爱" + (JiNianRiUtils.getLianAi() / 365) + "周年纪念日！";
            }
            if(JiNianRiUtils.getBirthday_Jo()  == 0){
                beizhu = "今天是生日，生日快乐呀！";
            }
            if(JiNianRiUtils.getBirthday_Hui()  == 0){
                beizhu = "今天是生日，生日快乐呀！";
            }
            templateMessage.addData(new WxMpTemplateData("beizhu",beizhu,"#FF0000"));

            try {
                System.out.println(templateMessage.toJson());
                System.out.println(wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage));
            } catch (Exception e) {
                System.out.println("推送失败：" + e.getMessage());
                e.printStackTrace();
            }
        }
    }

}
