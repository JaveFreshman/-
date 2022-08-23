package com.example.test1.utils;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class TxtTest {

    private static final Logger logger = LoggerFactory.getLogger(TxtTest.class);

    public static JSONObject readTxt() throws IOException {
        String s = "";
        InputStreamReader in = new InputStreamReader(new FileInputStream("D:\\test\\test.txt"),"UTF-8");
        BufferedReader br = new BufferedReader(in);
        StringBuffer content = new StringBuffer();
        while ((s=br.readLine())!=null){
            content = content.append(s);
        }
        JSONObject jsonObject = JSONObject.parseObject(content.toString());
        return jsonObject;
    }


}
