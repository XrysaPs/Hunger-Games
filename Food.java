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
public class Food{
	private int id;
	private int x;
	private int y;
	private int points;
	
	public Food() {
		//κενός  constructor
		id= 0;
		x = 0;
		y = 0;
		points = 0;
	}
	
	public Food(int id, int x, int y, int points) {
		//constructor με ορίσματα
		this.id = id;
		this.x = x;
		this.y = y;
		this.points = points;
	}
	
	public Food(Food a) {
		//αντικείμενο τύπου Food
		this.id = a.id;
		this.x = a.x;
		this.y = a.y;
		this.points = a.points;
	}
	
	void setId(int id) {//setters των μεταβλητών της κλάσης 
		this.id = id;
	}
	
	void setX(int x) {
		this.x = x;
	}
	void setY(int y) {
		this.y = y;
	}
	
	void setPoints(int points) {
		this.points = points;
	}
	
	int getId() {//getters των μεταβλητών της κλάσης
		return id;
	}
	
	int getX() {
		return x;
	}
	
	int getY() {
		return y;
	}
	
	int getPoints() {
		return points;
	}
}