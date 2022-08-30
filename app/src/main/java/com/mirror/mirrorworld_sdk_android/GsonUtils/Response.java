
package com.mirror.mirrorworld_sdk_android.GsonUtils;



public class Response {


    private Long code;

    private Data data;

    private String message;

    private String status;

    public Long getCode() {
        return code;
    }

    public Data getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public static class Builder {

        private Long code;
        private Data data;
        private String message;
        private String status;

        public Response.Builder withCode(Long code) {
            this.code = code;
            return this;
        }

        public Response.Builder withData(Data data) {
            this.data = data;
            return this;
        }

        public Response.Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public Response.Builder withStatus(String status) {
            this.status = status;
            return this;
        }

        public Response build() {
            Response response = new Response();
            response.code = code;
            response.data = data;
            response.message = message;
            response.status = status;
            return response;
        }

    }

}
