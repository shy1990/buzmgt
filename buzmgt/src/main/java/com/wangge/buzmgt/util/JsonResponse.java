package com.wangge.buzmgt.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wangge.buzmgt.dto.ChartDto;

/**
 * @author barton
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class JsonResponse<R> {

    public enum Status {

        SUCCESS("success"), ERROR("error");

        private final String value;

        private Status(String value) {
            this.value = value;
        }

        @JsonValue
        public String getValue() {
            return this.value;
        }
    }

    // Default value is success
    private Status status = Status.SUCCESS;

    // Result data
    private R result;

    // Success message
    private String successMsg;

    private String errorCode;

    // Error message
    private String errorMsg;
    
    private boolean success;

    public JsonResponse() {
        super();
    }

    public JsonResponse(R result) {
        super();
        this.result = result;
    }

   

    public JsonResponse(R result, boolean success) {
      super();
      this.result = result;
      this.success = success;
    }

   

    public JsonResponse(String errorMsg, boolean success) {
      super();
      this.errorMsg = errorMsg;
      this.success = success;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public R getResult() {
        return result;
    }

    public void setResult(R result) {
        this.result = result;
    }

    public String getSuccessMsg() {
        return successMsg;
    }

    public void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.status = Status.ERROR;
        this.errorMsg = errorMsg;
    }

    public boolean isSuccess() {
      return success;
    }

    public void setSuccess(boolean success) {
      this.success = success;
    }
    
    
    
}
