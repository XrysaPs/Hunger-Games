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

//Adding image to the frame
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CircleObject extends JPanel implements ActionListener {

    Timer tm = new Timer(5, this);
    int x = 0, velX = 2;
    int xPixel, yPixel;
    public CircleObject(int x, int y) {
    	xPixel = x;
    	yPixel = y;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        ImageIcon i = new ImageIcon("C:\\Users\\user\\Downloads\\title2.png");
        i.paintIcon(this, g, xPixel, yPixel);

        tm.start();
    }

    public void actionPerformed(ActionEvent ae) {
        x+=velX;
        repaint();
    }
}