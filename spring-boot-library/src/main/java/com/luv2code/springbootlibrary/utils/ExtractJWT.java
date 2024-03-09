package com.luv2code.springbootlibrary.utils;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class ExtractJWT {

    public static String payloadJWTExtraction(String token, String extraction) {
        System.out.println(token);
        token.replace("Bearer ", "");

        // Chia nhỏ token thành từng phần tại các dấu "."
        String[] chunks = token.split("\\.");
        // Base64 object to decode JWT into information that we can understand.
        Base64.Decoder decoder = Base64.getUrlDecoder();

        // Convert playload of JWT to String data
        String payload = new String(decoder.decode(chunks[1]));
        String[] entries = payload.split(",");

        Map<String, String> map = new HashMap<String, String>();

        for (String entry : entries) {
            String[] keyValue = entry.split(":");
            if (keyValue[0].equals(extraction)) {
                int remove = 1;
                if (keyValue[1].endsWith("}")) {
                    remove = 2;
                }

                keyValue[1] = keyValue[1].substring(0, keyValue[1].length() - remove);
                System.out.println(keyValue[1]);
                keyValue[1] = keyValue[1].substring(1);

                map.put(keyValue[0], keyValue[1]);
            }
        }

        if(map.containsKey(extraction)) {
            return map.get(extraction);
        }
        return null;
    }
}
