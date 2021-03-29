package runner;

import excelManager.ReadExcelFile;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GUI   extends JFrame{
	 JScrollPane scrollpane;
	 JRadioButton[] radio ;
	  String path = "";
      String fileName = "";
      String driver="";
      
	  public GUI() {
		    super("Select Test Cases");
		    setSize(600, 400);
		    setDefaultCloseOperation(EXIT_ON_CLOSE);
		    init();
		  }
	  
	  public void init() {
		  
		     javax.swing.JFileChooser jfc = new javax.swing.JFileChooser();
		        FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel files", "xls", "xlsx");
		        jfc.setFileFilter(filter);
		      
		     
		        try {
		            if(jfc.showSaveDialog(null) == jfc.APPROVE_OPTION){
		                path = jfc.getSelectedFile().getPath();
		                fileName = jfc.getSelectedFile().getName();
		            }
		        } catch (Exception e){
		            e.printStackTrace();
		        }

		        
		     
		        String[] namesTc = {};
		        ReadExcelFile excel = new ReadExcelFile();
		        try {
		        	 Object[] objArr = excel.getSheets(path).toArray();
				      
				       namesTc= new String[objArr.length];
				        System.arraycopy(objArr, 0, namesTc, 0, objArr.length);
		        } catch (IOException e){
		            e.printStackTrace();
		        }


		         radio = new JRadioButton[namesTc.length];
		     
		        //create panel interface
 
		     
		     JButton btnAll = new javax.swing.JButton();
		     btnAll.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		     btnAll.setForeground(new java.awt.Color(51, 51, 255));
		     btnAll.setText("Select all TCs");
		     btnAll.setFocusable(false);
		     btnAll.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		     btnAll.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		     btnAll.addActionListener(new java.awt.event.ActionListener() {
		            public void actionPerformed(java.awt.event.ActionEvent evt) {
		                btnAllActionPerformed(evt);
		            }
		        });
		     JButton btnClear = new javax.swing.JButton();
		     btnClear.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		     btnClear.setForeground(new java.awt.Color(51, 51, 255));
		     btnClear.setText("Clear Selection");
		     btnClear.setFocusable(false);
		     btnClear.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		     btnClear.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		     btnClear.addActionListener(new java.awt.event.ActionListener() {
		            public void actionPerformed(java.awt.event.ActionEvent evt) {
		                btnClearActionPerformed(evt);
		            }
		        });	  
		     
		     JToggleButton option1 = new JToggleButton("Chrome Driver");
		     option1.addActionListener(new java.awt.event.ActionListener() {
		            public void actionPerformed(java.awt.event.ActionEvent evt) {
		                option1ActionPerformed(evt);
		            }
		        });	 
		     JToggleButton option2 = new JToggleButton("FireFox Driver");
		     option2.addActionListener(new java.awt.event.ActionListener() {
		            public void actionPerformed(java.awt.event.ActionEvent evt) {
		                option2ActionPerformed(evt);
		            }
		        });	 
		     
		 
             option1.setSelected(true);
             driver="chrome";
             
		     ButtonGroup group = new ButtonGroup();
		     group.add(option1);
		     group.add(option2); 

		   JPanel p = new JPanel();
		//   JPanel p2 = new JPanel(new BorderLayout(1,5));
		  JPanel p2 = new JPanel();
		    p.setSize(600, 400);
		    p.setLayout(new GridLayout(4, 4));
		    
			   p.add(btnAll);
			   p.add(btnClear);
			   p.add(option1);
			   p.add(option2);
		//   p2.add(btnClear);
		   p2.add(p);
		   
	       String name = "";
	        for (int u = 0; u < namesTc.length; u++) {

	            name = namesTc[u];
	            if (name != null) {
	                JRadioButton button = new JRadioButton(name);
	                radio[u] = button;
	                p.add(radio[u]);
	            
	            }

	            if(radio[u] == null) {
	                p.remove(radio[u]);
	            }

	        }

		   
		    scrollpane = new JScrollPane(p2);
		    scrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		    getContentPane().add(scrollpane, BorderLayout.CENTER);
		  }
	  
		  private void btnAllActionPerformed(java.awt.event.ActionEvent evt) {
			 
			  for (int t = 0; t < radio.length; t++) {
		           radio[t].setSelected(true);		            
		        }
		  }
		    
		  private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {
				 
			  for (int t = 0; t < radio.length; t++) {
		           radio[t].setSelected(false);		            
		        }
		  }	
		  private void option1ActionPerformed(java.awt.event.ActionEvent evt) {
				 
			  driver="chrome";
		  }	
		  
		  private void option2ActionPerformed(java.awt.event.ActionEvent evt) {
				 
			driver ="firefox";
		  }

    public String[] showGui() throws IOException {
    	// String[] tcSelected;
    	  String[] dataReturn = new String[4];
        
        /** Show the Panel */
       JOptionPane.showMessageDialog(null, scrollpane);

        /** This for save the selected Test cases */
     //  tcSelected = new String[radio.length];
        String radios="";
        for (int t = 0; t < radio.length; t++) {

            if (radio[t].isSelected()) {
                radios += radio[t].getText()+"-";

            }
        }

        dataReturn[0] = path;
        dataReturn[1] = fileName;
        dataReturn[2] = radios;
        dataReturn[3] = driver;
       return dataReturn;

    }

}
