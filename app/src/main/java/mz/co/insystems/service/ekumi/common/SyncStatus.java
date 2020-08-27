package mz.co.insystems.service.ekumi.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import mz.co.insystems.service.ekumi.base.AbstractBaseVO;

/**
 * Created by Voloide Tamele on 5/11/2018.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SyncStatus extends AbstractBaseVO {

    @JsonProperty("status")
    private int code;
    private String message;
    private ObjectMapper objectMapper = new ObjectMapper();

    public SyncStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public SyncStatus() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
