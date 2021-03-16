package excelManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.json.JSONObject;

import readObject.ReadObject;
import testCase.StepAPI;
import testCase.StepSelenium;

public class GetTCData {
	
	public static List<StepSelenium> getStepSelenium() throws IOException {
		String step;
		String keyword;
		String locatorType;
		String locatorValue;
		String values;
		String description = null;
		String stepDescription = null;
		
		List<StepSelenium> steps = new ArrayList<>();
		ReadExcelFile excel = new ReadExcelFile();
		Sheet sheet = excel.readExcel("E:\\hexaware\\framework", "plantilla.xlsx", "TC04");
		Iterator<Row> rowIterator = sheet.iterator();
		Row row = rowIterator.next();
		while(rowIterator.hasNext()) {
			row = rowIterator.next();
			int rowsize = row.getLastCellNum();
			String contenido;
			try {
				Cell cel = row.getCell(0);
				contenido = row.getCell(0).toString();
				}
			catch(NullPointerException e) {
				contenido = "";
			}
			if(contenido.length() != 0) {
				String TestCaseName = row.getCell(0).toString();
				if(TestCaseName.equals("ENDCASE")) break;
				description = row.getCell(1).toString();
			}
			else {
				stepDescription = row.getCell(2).toString();
				step = row.getCell(3).toString();
				keyword = row.getCell(4).toString();
				locatorType = row.getCell(5).toString();
				locatorValue = row.getCell(6).toString();
				values = row.getCell(7).toString();
				steps.add(new StepSelenium(step, keyword, locatorType, locatorValue, values, description, stepDescription));
			}
		}
		return steps;
	}
	
	public static List<StepAPI> getStepAPI() throws IOException {
		String description;
		String step;
		String keyword;
		String url;
		String uri;;
		String parameters;
		JSONObject valueAPI;
		String statusCode;
		String validationType;
		String validationValue;
		
		List<StepAPI> steps = new ArrayList<>();
		ReadExcelFile excel = new ReadExcelFile();
		Sheet sheet = excel.readExcel("E:\\hexaware\\framework", "plantillaBack.xlsx", "TC03");
		Iterator<Row> rowIterator = sheet.iterator();
		Row row = rowIterator.next();
		while(rowIterator.hasNext()) {
			row = rowIterator.next();
			int rowsize = row.getLastCellNum();
			String contenido;
			String contenidoStep;
			Cell cel;
			try {
				cel = row.getCell(0);
				contenido = row.getCell(0).toString();
				}
			catch(NullPointerException e) {
				contenido = "";
			}
			try {
				cel = row.getCell(1);
				contenidoStep = row.getCell(1).toString();
			}
			catch(NullPointerException e) {
				contenidoStep = "";
			}
			if(contenido.length() != 0) {
				description = row.getCell(0).toString();
			}
			else if (contenido.length() == 0 && contenidoStep.length()>0){
				step = row.getCell(1).toString();
				keyword = row.getCell(2).toString();
				url = row.getCell(3).toString();
				uri = row.getCell(4).toString();
				parameters = row.getCell(5).toString();
				valueAPI = new JSONObject(row.getCell(6).toString());
				statusCode = row.getCell(7).toString();
				validationType = row.getCell(8).toString();
				validationValue = row.getCell(9).toString();
				steps.add(new StepAPI(step, keyword, url, uri, parameters,valueAPI, statusCode,
						validationType, validationValue));
			}
		}
		return steps;
	}
}
