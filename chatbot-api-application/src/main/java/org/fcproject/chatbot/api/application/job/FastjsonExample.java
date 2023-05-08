package org.fcproject.chatbot.api.application.job;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class FastjsonExample {
    public static void main(String[] args) {
        String jsonString = "{\n" +
                "     \"model\": \"gpt-3.5-turbo\",\n" +
                "     \"messages\": [{\"role\": \"user\", \"content\": \"Say this is a test!\"}],\n" +
                "     \"temperature\": 0.7\n" +
                "   }";
        // 将JSON字符串解析为JSONObject对象
        JSONObject jsonObject = JSON.parseObject(jsonString);

        // 从JSONObject对象中提取值
        String model = jsonObject.getString("model");
        JSONArray messages = jsonObject.getJSONArray("messages");
        double temperature = jsonObject.getDoubleValue("temperature");

        System.out.println("Model: " + model);
        System.out.println("Messages: " + messages);
        System.out.println("Temperature: " + temperature);
    }
}
