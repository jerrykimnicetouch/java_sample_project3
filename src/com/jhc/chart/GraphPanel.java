package com.jhc.chart;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;


	public class GraphPanel extends JPanel {
	 
	  //private String title;
	  // ArrayList<Double> values=new ArrayList<Double>();//Creating arraylist  
	 // ArrayList<String> names=new ArrayList<String>();//Creating arraylist  
	  double[] x;
	  double[] y;
	  
	  //int size = x.length;
      //int[] xx = new int[size];
      //int[] yy = new int[size];
	  
	  int size;
	  
	  int[] xx ;
      int[] yy ;
	  
      String xlabel, ylabel, title;
      int xdim, ydim, yzero, xzero, xdraw, ydraw;
      double xtic, ytic, xpoint, ypoint;
      double xmax, xmin, ymax, ymin; 
      

   
	  
	  public GraphPanel(ArrayList<Double> v, ArrayList<Double> n, String t) {
		  
		  if( (v.size() > 0) && (n.size() > 0) ) {
			 
			  updateGraphPanel(v,n,t);
		  }
		
		 
	
	  }
	  
	  public void updateGraphPanel(ArrayList<Double> v, ArrayList<Double> n, String t) {
		  

		  this.x = new double[n.size()];
		  for (int i = 0; i < x.length; i++) {
			    // x[i] = n.get(i).doubleValue();  // java 1.4 style
			    // or:
			    x[i] = n.get(i);                // java 1.5+ style (outboxing)
		  }
		  
		  this.y = new double[v.size()];
		  for (int i = 0; i < y.length; i++) {
			    y[i] = v.get(i);                // java 1.5+ style (outboxing)
		  }
		  
		  title = t;
		  
		  size = x.length;
    	  
    	  xx = new int[size];
          yy = new int[size];
    	  
          
		 
		//  xdim = 600;
	    //  ydim = 600;
          
          xdim = 1000;
          ydim = 325;
          
          
	         // xtic = 10; //this shoud be ymax/10
	         // ytic = 100; //this shoud be ymax/10
	          xlabel = ("Time");
	          ylabel = ("Bytes");
	          //title = ylabel + " versus " + xlabel;

	      xmax = x[0];
	              xmin = x[0];
	              ymax = y[0];
	              ymin = y[0];
	               
	              for (int i=0; i < size; i++){
	              if (x[i] > xmax) {
	              xmax = x[i];
	              }
	                      if (x[i] < xmin) {
	                    	  xmin = x[i];
	                      }
	                      if (y[i] > ymax) {
	                    	  ymax = y[i];
	                      }
	                      if (y[i] < ymin) {
	                    	  ymin = y[i];
	                      }
	                       
	              }
	              
	              
	              System.out.println("ymax::"+ ymax);
	              
	              
	              
	               xtic = xmax/10; //this shoud be ymax/10
	               ytic = ymax/10; //this shoud be ymax/10  
                       
                       
	       
	       
	      //xx and yy are the scaled x and y used for plotting
	                               
	              for (int i=0; i < size; i++){
		             xx[i] = (int) (50 + (((x[i]-xmin)/(xmax-xmin)) * (xdim-100)));
		              yy[i] = (int) ((ydim - 50) - (((y[i]-ymin)/(ymax-ymin)) * (ydim-100)));
	              }
	               
	//Find Zero point on y-axis required for drawing the axes
	               
	              if ((ymax*ymin) < 0){
	            	  yzero = (int) ((ydim - 50) - (((0-ymin)/(ymax-ymin)) * (ydim-100)));
	              }
	              else{
	            	  yzero = (int) ((ydim - 50) - ((0/(ymax-ymin)) * (ydim-100)));
	              }
	               
	//Find zero point on x-axis required for drawing the axes
	               
	              if ((xmax*xmin) < 0) {
	            	  xzero = (int) (50 + (((0-xmin)/(xmax-xmin)) * (xdim-100)));
	              }
	              else{
	            	  xzero = (int) (50 + ((0/(xmax-xmin)) * (xdim-100)));
	              }
	               
	//Now ready to plot the results
	              repaint();     
	               
	  }
	  
	     
 
 
	  public void paintComponent(Graphics g){  
		  super.paintComponent(g);
		  
		  if(size>0) {
               
              Font f1 = new Font("Monospaced", Font.PLAIN, 10);
              g.setFont(f1);
               
//First draw the axes
                
//y-axis
               
              g.drawLine(xzero, 50, xzero, ydim-50);
              g.drawLine(xzero, 50, (xzero - 5), 55);
              g.drawLine(xzero, 50, (xzero + 5), 55);
                               
//x-axis
               
              g.drawLine(50, yzero, xdim-50, yzero);
              g.drawLine((xdim-50), yzero, (xdim-55), (yzero + 5));
              g.drawLine((xdim-50), yzero, (xdim-55), (yzero - 5));
               
//Initialise the labelling taking into account the xtic and ytic values
                               
              //x-axis labels
               
              if (xmin <= 0){
            	  xpoint = xmin - (xmin%xtic);
              }else{
                  xpoint = xmin - (xmin%xtic) + xtic;
              }
               
              do{
	              xdraw = (int) (50 + (((xpoint-xmin)/(xmax-xmin))*(xdim-100)));
	              
	              g.drawString( ((int)xpoint) + "", xdraw, (yzero+10));
	              
	              xpoint = xpoint + xtic;
              }while (xpoint <= xmax);
               
              if (ymin <= 0){
            	  ypoint = ymin - (ymin%ytic);
              }else{
                  ypoint = ymin - (ymin%ytic) + ytic;
              }
               
              do{
	             // ydraw = (int) ((ydim - 50) - (((ypoint - ymin)/(ymax-ymin))*(ydim-100)));
	                ydraw = (int) ((ydim - 50) - (((ypoint - ymin)/(ymax-ymin))*(ydim-100)));
	                
	              g.drawString( ((int)ypoint) + "", (xzero - 20), ydraw);
	              
	              ypoint = ypoint + ytic;
	              
              }while (ypoint <= ymax);
              
//Titles and labels
              Font f2 = new Font("TimesRoman", Font.BOLD, 14);
              g.setFont(f2);
            
              g.drawString(xlabel, (xdim - 100), (yzero + 25));
              g.drawString(ylabel, (xzero - 25), 40);
              g.drawString(title, (xdim/2 - 75), 20);
               
//Draw Lines
               
             
              g.setColor(Color.RED);
              for (int j = 0; j < size; j++)
              {
               
              	
                    g.drawLine(xx[j], yy[j], xx[j], yzero);  
              	  //g.drawLine(xx[j], yy[j], xx[j+1], yy[j+1]);
              } 
              
		  }//if size>0
               
               
      }
 

	 
	}

