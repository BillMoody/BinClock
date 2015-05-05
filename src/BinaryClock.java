import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import javax.imageio.ImageIO;
import javax.swing.*;

public class BinaryClock extends JPanel {
    private static final long serialVersionUID = 351188162597697369L;
    private final int MAX = 6; 
    private static int[] dots = { 32, 16, 8, 4, 2, 1 };
    private static int[] vpos = { 25,75,125,175,225,275 };
    private int hourArr[] = new int[MAX];
    private int minArr[] = new int[MAX];
    private JFrame frame;
    private BufferedImage onImage;
    private BufferedImage offImage;
    private String curTime = "";
    
    public BinaryClock() {
        frame = new JFrame();
        frame.setSize(125, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(Color.darkGray);
        frame.getContentPane().add(this);
        frame.setVisible(true);
        
        try {      
         onImage = ImageIO.read(BinaryClock.class.getResourceAsStream("/aqua_ball.png"));
         offImage = ImageIO.read(BinaryClock.class.getResourceAsStream("/black_ball.png"));

         } catch (IOException ex) {
            System.err.println("Cannot find image.");
         }
        
    	tick();
    }
    
    public void tick() {
    	int then = 0;
    	while (true) {
    		
    	  try {
			Thread.sleep(500);
		  } catch (InterruptedException e) {
			e.printStackTrace();
		  }
    		
    	   Calendar cal = Calendar.getInstance();
   		   int hour = cal.get(Calendar.HOUR);
   		   if (hour == 0)
   			   hour = 12; 
		   int minute = cal.get(Calendar.MINUTE);
		   int second = cal.get(Calendar.SECOND);
		   curTime =  hour + " : " + minute + " : "  + second;
		   
    	   if ( second != then ) {  
    		  hourArr = plot(hour);  
    		  minArr = plot(minute);
    		  repaint();
    	   }
    	   then = second;
    	}
    }
    
    public int convert(int target) {
       int rtn = 0;
       
       if (target == 0)
    	   return 0;
    
       for( int d : dots ) {
         rtn = d;
         if (d <= target) {
           break;
         }
       }

       return rtn;
    }
    
    public int[] plot(int num) {
    	int tot = 0;
    	int j = 0;
    	int x = num;
    	int times[] = new int[MAX];
  
      	if (num == 0)
      	  return times;
      	
    	while (tot < num) {
    	    int y = convert(x);
    	    tot += y;
    	    x = num - tot;
    	    times[j++] = y;
    	}
        return times;  
    }
    
    public int[] fill (int arr[]) {
    	int buf[] = new int[MAX];
        for (int i : arr) {
        	if (i == 32) buf[0] = 1;
        	if (i == 16) buf[1] = 1;
        	if (i ==  8) buf[2] = 1;
        	if (i ==  4) buf[3] = 1;
        	if (i ==  2) buf[4] = 1;
        	if (i ==  1) buf[5] = 1; 	
        }
        return buf;
    }
    
    
   private void show(Graphics2D g2 , int buf[], int hoz) {
       for (int i=0; i<MAX; i++)  {
         if (buf[i] > 0) {
          	g2.drawImage(onImage, hoz, vpos[i], null);
         } else {
          	g2.drawImage(offImage, hoz, vpos[i], null);
         }
      } 
   }
    
   @Override
    public void paintComponent(Graphics g) {
	    super.paintComponent(g);  
    
    	Graphics2D g2 = (Graphics2D)g;
    	
    	int hpos = 25;
    	 
        // Hours
        int hours[] = fill(hourArr);
        show(g2,hours,hpos);
        
        // Minutes
        int minutes[] = fill(minArr);
        show(g2,minutes,hpos + 50);
       
        // Print the time
    	g2.setColor(Color.white);
    	g2.drawString(curTime, 36, 350);
    }
    
    public static void main(String[] args) {
    	new BinaryClock();
    }
}
