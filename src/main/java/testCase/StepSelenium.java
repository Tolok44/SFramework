package testCase;

import org.json.JSONObject;

public class StepSelenium {
    private String number;
    private String keyword;
    private String locatorType;
    private String locatorValue;
    private String value;
    private String description;
    private String stepDescription;
    private String name;
    private String takeScreenShot;

    public StepSelenium(String number, String keyword, String locatorType, String locatorValue, String value, String description, String stepDescription, String name) {
        this.number = number;
        this.keyword = keyword;
        this.locatorType = locatorType;
        this.locatorValue = locatorValue;
        this.value = value;
        this.description = description;
        this.stepDescription = stepDescription;
        this.name = name;
        this.takeScreenShot = number;
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

    public void setlocatorType(String locatorType) {
        this.locatorType = locatorType;
    }

    public String getLocatorType() {
        return this.locatorType;
    }

    public void setLocatorValue(String locatorValue) {
        this.locatorValue = locatorValue;
    }

    public String getLocatorValue() {
        return this.locatorValue;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public void setStepDescription(String stepDescription) {
        this.stepDescription = stepDescription;
    }

    public String getStepDescription() {
        return this.stepDescription;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getTakeScreenShot() {
		return takeScreenShot;
	}

	public void setTakeScreenShot(String takeScreenShot) {
		this.takeScreenShot = takeScreenShot;
	}

    @Override
    public String toString() {
        return "StepSelenium [number=" + number + ", keyword=" + keyword + ", locatorType=" + locatorType
                + ", locatorValue=" + locatorValue + ", value=" + value + ", description=" + description
                + ", stepDescription=" + stepDescription + "]";
    }

}