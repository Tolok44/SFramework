package test;

import java.io.IOException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import excelManager.ReadExcelFile;

public class TestExcelNG {
	
	
	@Test(dataProvider = "hybridData")
	public void impirmir(String test, String paso, String keyword, String URL, String URI, String Param, String val) {
		System.out.println("test= "+ test);
		System.out.println("paso= "+paso);
		System.out.println("keyword= "+keyword);
		System.out.println("url= "+URL);
		System.out.println("URI= "+URI);
		System.out.println("Param= "+Param);
		System.out.println("val= "+val);
	}
	
	@DataProvider(name="hybridData")
    public Object[][] getDataFromDataprovider() throws IOException{
    Object[][] object = null;
    ReadExcelFile file = new ReadExcelFile();
    //Read keyword sheet
    Sheet TC03 = file.readExcel("E:\\hexaware\\framework", "plantillaBack.xlsx", "TC03");
    //Find number of rows in excel file
    int rowCount = 4;
    object = new Object[rowCount][7];
    for (int i = 0; i < object.length; i++) {
        //Loop over all the rows
        Row row = TC03.getRow(i);
        //Create a loop to print cell values in a row
        for (int j = 0; j < object[1].length; j++) {
            //Print excel data in console
            object[i][j] = row.getCell(j).toString();
        }
    }
    System.out.println("");
     return object;    
    }
}
