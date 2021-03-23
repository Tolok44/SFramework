package excelManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcelFile {
	public Sheet readExcel(String path, String fileName, String sheetName) throws IOException {
		//File file = new File(path+"\\" + fileName);
		File file = new File(path);
		FileInputStream inputStream = new FileInputStream(file);
		Workbook book = null;
		String extension = fileName.substring(fileName.indexOf("."));
		if(extension.equals(".xlsx"))
			book = new XSSFWorkbook(inputStream);
		else if(extension.equals(".xls"))
			book = new HSSFWorkbook(inputStream);
		Sheet TestSuite = book.getSheet(sheetName);
	     return TestSuite;    
		
	}

	public List<String> getSheets(String path) throws IOException {
		File file = new File(path);
		Workbook book = WorkbookFactory.create(file);

		List<String> sheetNames = new ArrayList<String>();
		for(int i = 0; i < book.getNumberOfSheets(); i++){
			sheetNames.add(book.getSheetName(i));
		}

		return sheetNames;
	}
}
