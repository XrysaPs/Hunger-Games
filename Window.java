/* Στοιχεία φοιτητών:
    Τσιμρόγλου Στυλιανός
        ΑΕΜ: 9468
        web-mail: stsimrog@ece.auth.gr
        τηλέφωνο:6977030504
    Ψυρούκη Χρυσούλα
        ΑΕΜ:9446
        web-mail:chrypsyr@ece.auth.gr
        τηλέφωνο:6987242584
 */

//Drawing the frame
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class Window extends JPanel{
	Board b;
	Player p1;
	HeuristicPlayer p2;
	private int xPixel1, xPixel2, yPixel1, yPixel2;
	
	public Window (Board b){
		this.b = b;
    	xPixel1 = 925;
        yPixel1 = 575;
    	xPixel2 = 925;
    	yPixel2 = 575;
    }
    public Window (Board b, Player p1, HeuristicPlayer p2){
    	this.xPixel1 = p1.getXPixel();
    	this.xPixel2 = p2.getXPixelHeuristic();
    	this.yPixel1 = p1.getYPixel();
    	this.yPixel2 = p2.getYPixelHeuristic();
    	
    	this.b = b;
    	this.p1 = p1;
    	this.p2 = p2;
    }
    public void paintComponent(Graphics g){
    	super.paintComponent(g);
    	g.setColor(Color.YELLOW);
    	g.fillRect(0, 0, 1400, 700);
    	
    	//draw red rectangle for the board
    	g.setColor(Color.RED);
        g.fillRect(450+(20-b.getN())*25/2, 100+(20-b.getN())*25/2, 500-(20-b.getN())*25, 500-(20-b.getN())*25);
        	
        //draw blue rectangle inside the red rectangle for the area with the traps
        if(b.getN()>=8) {
        	g.setColor(Color.BLUE);
        	g.fillRect(600, 250, 200, 200);
        }
        else if(b.getN()<8) {
        	g.setColor(Color.BLUE);
        	g.fillRect(600+(20-b.getN())*25/2, 250+(20-b.getN())*25/2, 200-(20-b.getN())*25, 200-(20-b.getN())*25);
        }
        	
        //draw gray rectangle inside the blue rectangle for the area with the food
        if(b.getN()>=6) {
        	g.setColor(Color.GRAY);
        	g.fillRect(625, 275, 150, 150);
        }
        else if(b.getN()<6) {
        	g.setColor(Color.GRAY);
        	g.fillRect(625+(20-b.getN())*25/2, 275+(20-b.getN())*25/2, 150-(20-b.getN())*25, 150-(20-b.getN())*25);
       	}
        
        //draw white rectangle inside the gray rectangle for the area with the weapons
        g.setColor(Color.WHITE);
       	g.fillRect(650, 300, 100, 100);
       	
       	//draw lines get the tiles
       	g.setColor(Color.BLACK);
       	for (int i=450; i<=950; i+=25) {
    	   	g.drawLine(i, 100, i, 600);
        }
        for (int j=100; j<=600; j+=25) {
    	   	g.drawLine(450, j, 950, j);
       	}
        	
        //draw orange cycle for player 1
        g.setColor(Color.ORANGE);
        g.fillOval(xPixel1, yPixel1, 25, 25);
        	
        //draw green cycle for player 2
        g.setColor(Color.GREEN);
        g.fillOval(xPixel2, yPixel2, 25, 25);
        	
        //draw shapes for each trap, food and weapon
       	int k=250;
       	int l=600;
       		
       	for(int i=-4; i<=4; i++) {
        	if (i == 0 ) {
       			continue;
        	}
        	for(int j=-4; j<=4; j++) {
        		if (j == 0) {
        			continue;
        		}
        		for (int w=0; w<b.getW(); w++) {
        			if (b.getWeapons()[w].getX() == j && b.getWeapons()[w].getY() == i) {
        				if(b.getWeapons()[w].getType() == "Bow") {
        					//draw black cycle for Bow
        					g.setColor(Color.BLACK);
        		        	g.fillOval(l, k, 13, 13);
        				}
        				else if(b.getWeapons()[w].getType() == "Pistol") {
        					//draw blue cycle for Pistol
        					g.setColor(Color.BLUE);
        			       	g.fillOval(l, k, 13, 13);
        				}
        				else if(b.getWeapons()[w].getType() == "Sword") {
        					//draw black red for Sword
        					g.setColor(Color.RED);
        			       	g.fillOval(l, k, 13, 13);
        				}
       					break;
       				}
       			}
       			for(int f=0; f<b.getF(); f++) {
       				if (b.getFood()[f].getX() == j && b.getFood()[f].getY() == i) {
       					//draw red rectangles for Food
       					g.setColor(Color.RED);
   				       	g.fillRect(l, k, 13, 13);
       					break;
        			}
        		}
        		for(int t=0; t<b.getT(); t++) {
        			if (b.getTraps()[t].getX() == j && b.getTraps()[t].getY() == i) {
       					if(b.getTraps()[t].getType() == "Rope") {
       						//draw green rectangle for Rope
       						g.setColor(Color.GREEN);
       			        	g.fillRect(l, k, 13, 13);
       					}
       					else if(b.getTraps()[t].getType() == "Animal") {
       						//draw cyan rectangle for Animal
       						g.setColor(Color.CYAN);
       			        	g.fillRect(l, k, 13, 13);
       					}
        				break;
        			}
        		}
        			      
        		if(l<775) {
       				l+=25;
       			}
       				
       		}
       		if(k<425) {
       			k+=25;
       		}
       		l=600;
       	}
    }
}