package runner;

import excelManager.ReadExcelFile;

import javax.swing.*;
import java.io.IOException;

public class GUI {

    public String[] showGui() throws IOException {
        javax.swing.JFileChooser jfc = new javax.swing.JFileChooser();
        String path = "";
        String fileName = "";
        String[] tcSelected;
        String[] dataReturn = new String[3];
        try {
            if(jfc.showSaveDialog(null) == jfc.APPROVE_OPTION){
                path = jfc.getSelectedFile().getPath();
                fileName = jfc.getSelectedFile().getName();
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        ReadExcelFile excel = new ReadExcelFile();





        //Window to select test case

        /** The array namesTc save to names the Test cases */

        Object[] objArr = excel.getSheets(path).toArray();
        String[] namesTc = new String[objArr.length];
        System.arraycopy(objArr, 0, namesTc, 0, objArr.length);

        JRadioButton[] radio = new JRadioButton[namesTc.length];
        //create panel interface
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel label = new JLabel();
        label.setText("Which Test Cases wish execute");
        panel.add(label);
        panel.setBounds(10, 10, 375, 150);

        /** This for create and add to array radio all the buttons that we will show */
        String name = "";
        for (int u = 0; u < namesTc.length; u++) {

            name = namesTc[u];
            if (name != null) {
                JRadioButton button = new JRadioButton(name);
                radio[u] = button;
                panel.add(radio[u]);
            }

            if(radio[u] == null) {
                panel.remove(radio[u]);
            }

        }

        /** Show the Panel */
        JOptionPane.showMessageDialog(null, panel);

        /** This for save the selected Test cases */
        tcSelected = new String[radio.length];
        String radios="";
        for (int t = 0; t < radio.length; t++) {

            if (radio[t].isSelected()) {

                radios += radio[t].getText()+"-";

            }
        }

        dataReturn[0] = path;
        dataReturn[1] = fileName;
        dataReturn[2] = radios;

        return dataReturn;


    }

}
