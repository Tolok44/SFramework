package test;

import java.io.IOException;
import java.util.List;

import excelManager.GetTCData;
import testCase.StepAPI;
import testCase.StepSelenium;

public class Test {

	public static void main(String[] args) throws IOException {
		List<StepSelenium> steps = GetTCData.getStepSelenium();
		Object[][] datos = new Object[steps.size()][1];
		for(int row=0;row<steps.size();row++) {
			datos[row][0]=steps.get(row);
			}
		System.out.println(datos.toString());
		for(int row=0; row<datos.length; row++) {
			for(int col =0; col< datos[0].length; col++) {
				System.out.println(datos[row][col]);
			}
		}
		

	}

}
