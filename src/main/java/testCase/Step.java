package testCase;

public class Step {
	String number;
	String keyword;
	String locatorType;
	String locatorValue;
	String value;
	
	public Step(String number, String keyword, String locatorType, String locatorValue, String value) {
		this.number = number;
		this.keyword = keyword;
		this.locatorType = locatorType;
		this.locatorValue = locatorValue;
		this.value = value;
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
}	
