package mx.admino.models;

import java.time.LocalDateTime;
import java.util.List;

public class ReCaptchaResponse {

    private Boolean success;

    private LocalDateTime challenge_ts;

    private String hostname;

    private List<Object> errorCodes;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public LocalDateTime getChallenge_ts() {
        return challenge_ts;
    }

    public void setChallenge_ts(LocalDateTime challenge_ts) {
        this.challenge_ts = challenge_ts;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public List<Object> getErrorCodes() {
        return errorCodes;
    }

    public void setErrorCodes(List<Object> errorCodes) {
        this.errorCodes = errorCodes;
    }
}
