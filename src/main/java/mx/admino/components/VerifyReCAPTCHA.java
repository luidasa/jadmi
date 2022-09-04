package mx.admino.components;

import mx.admino.models.ReCaptchaResponse;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public class VerifyReCAPTCHA {

    public static final String url = "https://www.google.com/recaptcha/api/siteverify";

    public static final String secret = "6LcpF88hAAAAAIPqDUCAVjdxJlj3iDvokediJ9cK";

    public static Boolean verify(String gRecaptchaResponse) throws IOException {
        if (gRecaptchaResponse == null || "".equals(gRecaptchaResponse)) {
            return false;
        }

        try {
            String formData = "secret=" + secret + "&response=" + gRecaptchaResponse;

            RestTemplate restTemplate = new RestTemplate();
            ReCaptchaResponse response
                    = restTemplate.postForObject(url + "?" + formData, null, ReCaptchaResponse.class);
            System.out.println(response.getSuccess());

            return response.getSuccess();
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}
