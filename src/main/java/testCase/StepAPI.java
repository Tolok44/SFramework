package testCase;

import org.json.JSONObject;

public class StepAPI {
    private String number;
    private String keyword;
    private String url;
    private String uri;
    private String parameters;
    private JSONObject valueAPI;
    private String statusCode;
    private String validationType;
    private String validationValue;

    public StepAPI(String number, String keyword, String url, String uri, String parameters, JSONObject valueAPI,
                   String statusCode,String validationType, String validationValue) {
        this.number = number;
        this.keyword = keyword;
        this.url = url;
        this.uri = uri;
        this.parameters = parameters;
        this.valueAPI = valueAPI;
        this.statusCode = statusCode;
        this.validationType = validationType;
        this.validationValue = validationValue;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return this.number;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return this.keyword;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return this.uri;
    }

    public void setParamaters(String parameters) {
        this.parameters = parameters;
    }

    public String getParameters() {
        return this.parameters;
    }

    public void setValueAPI(JSONObject valueAPI) {
        this.valueAPI = valueAPI;
    }

    public JSONObject getValueAPI() {
        return this.valueAPI;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusCode() {
        return this.statusCode;
    }

    public void setValidationType(String validationType) {
        this.validationType = validationType;
    }
    public String getValidationType() {
        return this.validationType;
    }

    public void setValidationValue(String validationValue) {
        this.validationValue = validationValue;
    }

    public String getValidationValue() {
        return this.validationValue;
    }


}