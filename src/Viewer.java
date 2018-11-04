import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileSystemView;

import com.jhc.chart.GraphPanel;
import com.jhc.chart.MyPatternReplace;

import javax.swing.JLabel;
import java.awt.GridLayout;


public class Viewer {//extends JFrame {

	//private JPanel contentPane;
	
	private JFrame frame;
	JPanel panel;
	private GraphPanel gPanel;
	
	JRadioButton rdbtnSourceHosts;
	JRadioButton rdbtnDestHostsRadioButton;
	
	
	//String[] ipComboList = new String[] {"Select IP Address"};
	String[] ipComboList = new String[] {};
	DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(ipComboList);
	
	JComboBox<String> ipList; 
	
	ArrayList<Double> bytes=new ArrayList<Double>();//Creating arraylist  bytes
	ArrayList<Double> time=new ArrayList<Double>();//Creating arraylist   time
	
	ArrayList<String> sourceIP=new ArrayList<String>();//Creating arraylist   sourceIP
	ArrayList<String> desIP=new ArrayList<String>();//Creating arraylist   desIP
	
	
	ArrayList<Double> selectedBytes=new ArrayList<Double>();//Creating arraylist  bytes
	ArrayList<Double> selectedTime=new ArrayList<Double>();//Creating arraylist   time
	
	ActionListener sourceA;
	ActionListener desA;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Viewer viewer = new Viewer();
					viewer.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	/*public Viewer() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}*/
	
	
	
	/**
	 * Create the application.
	 */
	public Viewer() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(0, 0, 1000, 500); //change width and height 
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2); //locate the frame at the screen center 
        		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		//frame.getContentPane().add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		rdbtnSourceHosts = new JRadioButton("Source hosts");
		panel.add(rdbtnSourceHosts);
		rdbtnSourceHosts.setHorizontalAlignment(SwingConstants.LEFT);
		
		rdbtnDestHostsRadioButton = new JRadioButton("Destination hosts");
		panel.add(rdbtnDestHostsRadioButton);
		rdbtnDestHostsRadioButton.setHorizontalAlignment(SwingConstants.LEFT);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmOpenTraceFile = new JMenuItem("Open trace file");
		mnFile.add(mntmOpenTraceFile);
		
		JMenuItem mntmQuit = new JMenuItem("Quit");
		mnFile.add(mntmQuit);
		
		//add button group 
		ButtonGroup bg = new ButtonGroup();
        bg.add(rdbtnSourceHosts);
        bg.add(rdbtnDestHostsRadioButton);
        rdbtnSourceHosts.setSelected(true);
		
		//add menu item action listener 
        mntmOpenTraceFile.addActionListener(new ActionListener() {
	        		
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				
    			int returnValue = jfc.showOpenDialog(null);
    			// int returnValue = jfc.showSaveDialog(null);

    			if (returnValue == JFileChooser.APPROVE_OPTION) {
    					File selectedFile = jfc.getSelectedFile();
    					//System.out.println(selectedFile.getAbsolutePath());
    					
    					BufferedReader br = null;
    					FileReader fr = null;

    					try {

    						//br = new BufferedReader(new FileReader(FILENAME));
    						fr = new FileReader(selectedFile.getAbsolutePath());
    						br = new BufferedReader(fr);

    						String sCurrentLine;

    						while ((sCurrentLine = br.readLine()) != null) {
    							//System.out.println(sCurrentLine);
    							
    							
    							//parse line here and save to arraylist
    							
    							MyPatternReplace mpr = new MyPatternReplace();
    							String str = mpr.replaceWithPattern(sCurrentLine, " ");
    					        
    							String[] strarray = str.split(" ");
    							
    							if(strarray.length > 8) {
	    							time.add(Double.parseDouble(strarray[1]));
                                                                
                                                                ////
	    							bytes.add(Double.parseDouble(strarray[13]));
                                                                
	    							sourceIP.add(strarray[2]);
	    							desIP.add(strarray[4]);
    							}else {
    								time.add(Double.parseDouble(strarray[1]));
    								bytes.add(0.0);
    								sourceIP.add("");
	    							desIP.add("");
    							}
    						
    						}
    						
    						//send array list to graph
    						showGraph();

    					} catch (IOException e) {

    						e.printStackTrace();

    					} finally {

    						try {

    							if (br != null)
    								br.close();

    							if (fr != null)
    								fr.close();

    						} catch (IOException ex) {

    							ex.printStackTrace();

    						}

    					}
    			}
				
			}
        });
        mntmQuit.addActionListener(new ActionListener() {
    		
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.exit(0);
				
			}
        });
        
        
		
		ipList =  new JComboBox<>(model);
		panel.add(ipList);
			
		ipList.setVisible(false);
		
       
		gPanel = new GraphPanel(bytes, time, "Volume[bytes]");
		frame.getContentPane().add(gPanel, BorderLayout.CENTER);
			
	}
	
	//@SuppressWarnings("deprecation")
	private void showGraph() {
		
		
		//reset();
		
		
		if( rdbtnSourceHosts.isSelected()) {
			
			showSourceIPGraph();
			
		}else {
			//if( rdbtnDestHostsRadioButton.isSelected() ) {
			
			showDesIPGraph();
		}
		//add radio change listener here
		rdbtnSourceHosts.addActionListener( new ActionListener() {
			
			public void actionPerformed(ActionEvent evt) {
			    Object source = evt.getSource();
			    
			    //reset();
			    
			    if (source == rdbtnSourceHosts) {
			    	if( ipList.getActionListeners() != null ) ipList.removeActionListener(desA);
			    	System.out.println("Source");
			    	showSourceIPGraph();
			    	
			    }else{ 
			    	//if (source == rdbtnDestHostsRadioButton) {
			    	//System.out.println("Des");
			    	//showDesIPGraph();
			    }
			    	
			    
			  }

		});
		
		//add radio change listener here
		rdbtnDestHostsRadioButton.addActionListener( new ActionListener() {
					
					public void actionPerformed(ActionEvent evt) {
					    Object source = evt.getSource();
					    
					    //reset();
					    
					    if (source == rdbtnSourceHosts) {
					    	//System.out.println("Source");
					    	//showSourceIPGraph();
					    	
					    }else{ 
					    	//if (source == rdbtnDestHostsRadioButton) {
					    	if( ipList.getActionListeners() != null ) ipList.removeActionListener(sourceA);
					    	System.out.println("Des");
					    	showDesIPGraph();
					    }
					    	
					    
					  }

		});
		
		
	}
	
	private void reset() {
		//model.removeAllElements();
		//ipList.setVisible(false);
		
		//selectedBytes = new ArrayList<Double>();
		//selectedTime = new ArrayList<Double>();
	}
	
	private void showSourceIPGraph() {
			
		//extract IP address
				Set<String> hs = new HashSet<>();
				hs.addAll(sourceIP);
				
				//add the list to model
				if(model.getSize()>0)model.removeAllElements();
				for(String s: hs) {
					if(!s.equals("")) model.addElement(s);
				}
				//show those at the combobox
				ipList.setVisible(true);
				
				if(model.getSize()>0) {
					//combobox select event listener
					//set default selected and show graph here
					ipList.setSelectedIndex(0);
					//prepare data for the default ip
					String ipSelected = ipList.getSelectedItem().toString();
					
					selectedBytes = new ArrayList<Double>();
					selectedTime = new ArrayList<Double>();
					
					for(int i = 0; i < sourceIP.size() ; i++) {
						if(sourceIP.get(i).equals(ipSelected)) {
							selectedBytes.add(bytes.get(i));
							selectedTime.add(time.get(i));
							
						}
					}
					//show the default graph here
					gPanel.updateGraphPanel(selectedBytes, selectedTime, "Volume[bytes]");
				
				}
				
				sourceA = new ActionListener () {
				    public void actionPerformed(ActionEvent e) {
					       // doSomething();
					    	//System.out.println("selected"+ipList.getSelectedIndex());
					    	//ipList.getSelectedIndex()
					    	if(model.getSize()>0) {
					    		String ipSelected = ipList.getSelectedItem().toString();
					    		
					    		System.out.println("from source:"+ipSelected);
					    	
						    	selectedBytes = new ArrayList<Double>();
								selectedTime = new ArrayList<Double>();
								
						    	for(int i = 0; i < sourceIP.size() ; i++) {
									if(sourceIP.get(i).equals(ipSelected)) {
										selectedBytes.add(bytes.get(i));
										selectedTime.add(time.get(i));
										
									}
								}
								//show the default graph here
								gPanel.updateGraphPanel(selectedBytes, selectedTime, "Volume[bytes]");
					    	}
					    	
						
					    	
					    }
				};
				
				//show selected graph here
				if( ipList.getActionListeners() != null ) ipList.removeActionListener(desA);
				ipList.addActionListener (sourceA);
	}
	private void showDesIPGraph() {
		
			//extract IP address
				Set<String> hs = new HashSet<>();
				hs.addAll(desIP);
				
				//add the list to model
				if(model.getSize()>0) model.removeAllElements();
				for(String s: hs) {
					if(!s.equals("")) model.addElement(s);
				}
				//show those at the combobox
				ipList.setVisible(true);
				
				
			
			 	//combobox select event listener
				if(model.getSize()>0) {
						//set default selected and show graph here
						ipList.setSelectedIndex(0);
						//prepare data for the default ip
						String ipSelected = ipList.getSelectedItem().toString();
						
						selectedBytes = new ArrayList<Double>();
						selectedTime = new ArrayList<Double>();
						
						for(int i = 0; i < desIP.size() ; i++) {
							if(desIP.get(i).equals(ipSelected)) {
								selectedBytes.add(bytes.get(i));
								selectedTime.add(time.get(i));
								
							}
						}
						//show the default graph here
						gPanel.updateGraphPanel(selectedBytes, selectedTime, "Volume[bytes]");
				}
						
				
				//show selected graph here
				desA = new ActionListener () {
				    public void actionPerformed(ActionEvent e) {
				       // doSomething();
				    	//System.out.println("selected"+ipList.getSelectedIndex());
				    	//ipList.getSelectedIndex()
				    	if(model.getSize()>0) {
				    						    		
				    		String ipSelected = ipList.getSelectedItem().toString();
				    		
				    		System.out.println("from des:"+ipSelected);
				    	
					    	selectedBytes = new ArrayList<Double>();
							selectedTime = new ArrayList<Double>();
							
					    	for(int i = 0; i < desIP.size() ; i++) {
								if(desIP.get(i).equals(ipSelected)) {
									selectedBytes.add(bytes.get(i));
									selectedTime.add(time.get(i));
									
								}
							}
							//show the default graph here
							gPanel.updateGraphPanel(selectedBytes, selectedTime, "Volume[bytes]");
				    	}
				    	
					
				    	
				    }
				};
				if( ipList.getActionListeners() != null ) ipList.removeActionListener(sourceA);
				ipList.addActionListener (desA);
				
				
	}

}
