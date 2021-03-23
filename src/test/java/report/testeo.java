package report;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import excelManager.GetTCData;
import runner.GUI;
import testCase.StepSelenium;

public class testeo {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		GUI gui = new GUI();
		List<List<StepSelenium>> testCases = new ArrayList<>();
        String[] info = gui.showGui();
        String path = info[0];
        String fileName = info[1];
        String tcSelected = info[2];
        String[] cases = tcSelected.split("-");
        System.out.println(cases.length);
        for(int i=0; i<cases.length;i++) {
        	List<StepSelenium> steps = GetTCData.getStepSelenium(path, fileName, cases[i]);
        	testCases.add(steps);
        }
        
        Object[][] datos = new Object[testCases.size()][1];
        for(int row=0;row<testCases.size();row++) {
            datos[row][0]=testCases.get(row);
        }
        for(int row=0;row<datos.length;row++) {
        	List<StepSelenium> paso = (List<StepSelenium>) datos[row][0];
            System.out.println(paso.get(row).toString());
        }

	}

}
