package web.order.controller;

import java.util.HashMap;

import java.util.Map;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import core.util.EcpayCheckMacValue;


@Controller
@RequestMapping("/ecpay")
public class EpayController {
    @PostMapping("/generateCheckMac")
    public Map<String, String> generateCheckMac(@RequestBody Map<String, String> params) throws Exception {
        String hashKey = "pwFHCqoQZGmho4w6";
        String hashIV = "EkRm7iFT261dpevs";

        String checkMacValue = EcpayCheckMacValue.generateCheckMacValue(params, hashKey, hashIV);

        // 回傳前端
        Map<String, String> result = new HashMap<>();
        result.put("CheckMacValue", checkMacValue);
        return result;
//        return null;
    }

    // 綠界付款結果通知存儲
    @PostMapping("/paymentResult")
    public String paymentResult(@RequestParam Map<String, String> params) {
        // 存資料庫...略
        System.out.println("綠界回傳資料: " + params);
        return "1|OK"; // 回覆綠界收到
    }
}
