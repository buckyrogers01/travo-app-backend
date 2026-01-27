package com.application.travo.Service;

import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.twilio.rest.api.v2010.account.Message;
import java.time.Instant;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpService {

    private static final int OTP_EXPIRY_SECONDS = 300; // 5 min
    @Value("${twilio.phone.number}")
    private String fromNumber;
    private static class OtpData {
        String otp;
        Instant expiry;
    }

    private final Map<String, OtpData> otpStore = new ConcurrentHashMap<>();

    public void sendOtp(String phone) {

        String otp = generateOtp();

        OtpData data = new OtpData();
        data.otp = otp;
        data.expiry = Instant.now().plusSeconds(OTP_EXPIRY_SECONDS);

        otpStore.put(phone, data);

        // 🔔 Send SMS (Twilio)
        System.out.println("DEV OTP for " + phone + " = " + otp);
        Message.creator(
                new PhoneNumber(phone), // TO
                new PhoneNumber(fromNumber), // FROM
                "DEV OTP for " + phone + " = " + otp
        ).create();
    }

    public boolean verifyOtp(String phone, String otp) {

        String normalizedPhone = normalizePhone(phone);
        OtpData data = otpStore.get(normalizedPhone);

        if (data == null) return false;

        if (Instant.now().isAfter(data.expiry)) {
            otpStore.remove(normalizedPhone);
            return false;
        }

        if (!data.otp.equals(otp)) return false;

        otpStore.remove(normalizedPhone);
        return true;
    }

    private String generateOtp() {
        return String.valueOf(100000 + new Random().nextInt(900000));
    }
    private String normalizePhone(String phone) {
        return phone.replaceAll("\\s+", "");
    }
}
