package test;

import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import excelManager.ReadExcelFile;

public class TestExcelIterator {

	public static void main(String[] args) throws IOException {
		ReadExcelFile excel = new ReadExcelFile();
		Sheet tc01 = excel.readExcel("E:\\hexaware\\framework", "plantilla.xlsx", "TC02");
		Iterator<Row> rowIterator = tc01.iterator();
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
				System.out.println("test case name: " + TestCaseName);
				System.out.println("description: " + description);
			}
			else {
				String step = row.getCell(2).toString();
				String keyword = row.getCell(3).toString();
				String LocatorType = row.getCell(4).toString();
				String LocatorValue = row.getCell(5).toString();
				String values = row.getCell(6).toString();
				System.out.println("step: " + step);
				System.out.println("keyword: " + keyword);
				System.out.println("locator type: " + LocatorType);
				System.out.println("locator value: " + LocatorValue);
				System.out.println("values: " + values);
				
			}
			/*
			Iterator<Cell> cellIterator = row.iterator();
			while(cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				if(cell.toString().length()==0)
					continue;
				System.out.println(cell.toString());
			}*/
		}

	}

}
