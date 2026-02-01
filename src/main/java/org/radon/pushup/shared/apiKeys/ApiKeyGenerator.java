package org.radon.pushup.shared.apiKeys;
import java.security.SecureRandom;
import java.util.AbstractMap;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class ApiKeyGenerator {

    private static final SecureRandom secureRandom = new SecureRandom();

    public static Map.Entry<String,String> generate() {
        byte[] randomBytes = new byte[32]; // 256 bits
        secureRandom.nextBytes(randomBytes);

        String encoded = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(randomBytes);

        return new AbstractMap.SimpleEntry<>("pu_live_",encoded);
    }
}
