package core.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;

public class EcpayCheckMacValue {
    // 計算 CheckMacValue 的方法
    public static String generateCheckMacValue(Map<String, String> params, String hashKey, String hashIV) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        // 1. 先建立 TreeMap 依 key 排序 (A-Z)
        TreeMap<String, String> sortedParams = new TreeMap<>(params);

        // 2. 將參數串接成 query string
        StringBuilder sb = new StringBuilder();
        sb.append("HashKey=").append(hashKey);
        for (Map.Entry<String, String> entry : sortedParams.entrySet()) {
            sb.append("&").append(entry.getKey()).append("=").append(entry.getValue());
        }
        sb.append("&HashIV=").append(hashIV);

        // 3. URL Encode
        String urlEncoded = urlEncode(sb.toString()).toLowerCase();

        // 4. SHA256 加密
        String sha256 = sha256(urlEncoded);

        // 5. 轉大寫
        return sha256.toUpperCase();
    }

    // URL Encode 方法 (符合綠界規則)
    private static String urlEncode(String value) throws UnsupportedEncodingException {
        String encoded = URLEncoder.encode(value, "UTF-8");

        // 綠界特殊字元替換
        encoded = encoded.replace("%2d", "-")
                         .replace("%5f", "_")
                         .replace("%2e", ".")
                         .replace("%21", "!")
                         .replace("%2a", "*")
                         .replace("%28", "(")
                         .replace("%29", ")");

        return encoded;
    }

    // SHA256 方法
    private static String sha256(String str) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(str.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    // 測試範例
    public static void main(String[] args) throws Exception {
        Map<String, String> params = Map.of(
			// 特店編號(固定不動)
            "MerchantID", "3002607",
			// 訂單編號(只能英文+數字，且一定要英文當開台然後訂單編號不能重複)
            "MerchantTradeNo", "order202511161636",
			// 特店交易時間(格式為：yyyy/MM/dd HH:mm:ss)
            "MerchantTradeDate", "2025/11/16 16:36:00",
			// 交易類型(測試區固定填入aio)
            "PaymentType", "aio",
			// 交易總金額
            "TotalAmount", "1000",
			// 交易描述
            "TradeDesc", "testpay",
			// 商品名稱
            "ItemName", "test",
			// 付款完成通知回傳網址
            "ReturnURL", "http://localhost:3000/",
			// 選擇預設付款方式(固定填入ALL)
            "ChoosePayment", "ALL",
			// CheckMacValue加密類型(固定填入1，使用SHA256加密)	
            "EncryptType", "1"
        );
		
		// 串接金鑰HashKey
        String hashKey = "pwFHCqoQZGmho4w6";
		// 串接金鑰HashIV
        String hashIV = "EkRm7iFT261dpevs";

        String checkMacValue = generateCheckMacValue(params, hashKey, hashIV);
        System.out.println("CheckMacValue: " + checkMacValue);
    }

}
