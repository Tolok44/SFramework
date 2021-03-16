package test;

import java.io.IOException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.json.JSONObject;

import excelManager.ReadExcelFile;

public class TestExcel {
	public static void main(String[] args) throws IOException {
		String cont;
		ReadExcelFile excel = new ReadExcelFile();
		Sheet tc01 = excel.readExcel("E:\\hexaware\\framework", "plantilla.xlsx", "TC02");
		int rowcount = tc01.getLastRowNum()-tc01.getFirstRowNum();
		for(int i= 1; i<=rowcount; i++) {
			Row row = tc01.getRow(i);
			try {
				cont = row.getCell(0).toString();}
			catch(NullPointerException e) {
				cont = null;
			}
			if(cont==null) {
				System.out.println("no hay no existe");
			}
			else if(cont.length()==0) {
				System.out.println(row.getCell(2).toString());
				//JSONObject json = new JSONObject(row.getCell(6).toString());
				//System.out.println(json);
			}
			else {
				//IPRIME EL NOMBRE DEL TEST CASE Y SU DESCRIPCION
				System.out.println("TEST CASE: " + row.getCell(0).toString());
				System.out.println("DESCRIPCION: " + row.getCell(1).toString());
			}
		}
	}
}
