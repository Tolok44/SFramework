package excelManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import readObject.ReadObject;
import testCase.Step;

public class GetTCData {
	
	public static List<Step> getStepArray() throws IOException {
		List<Step> steps = new ArrayList<>();
		ReadExcelFile excel = new ReadExcelFile();
		Sheet sheet = excel.readExcel("C:\\Users\\Training\\Documents", "plantilla.xlsx", "TC02");
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
				String description = row.getCell(1).toString();
			}
			else {
				String step = row.getCell(2).toString();
				String keyword = row.getCell(3).toString();
				String locatorType = row.getCell(4).toString();
				String locatorValue = row.getCell(5).toString();
				String values = row.getCell(6).toString();
				steps.add(new Step(step, keyword, locatorType, locatorValue, values));
			}
		}
		return steps;

	}
}
