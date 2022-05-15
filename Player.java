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
import java.util.Random; 
public class Player {
	protected int id;
	protected String name;
	protected Board board;
	protected int score;
	protected int x;
	protected int y;
	protected Weapon bow;
	protected Weapon pistol;
	protected Weapon sword;
	protected int xPixel=925;
	protected int yPixel=575;
	
	public Player() {//κενός constructor 
		id=0;
		name="";
		board = null;
		score=0;
		x=0;
		y=0;
		bow = null;
		pistol = null;
		sword = null;
	}
	public Player(int id,String name,Board board,int score,int x,int y,Weapon bow,Weapon pistol,Weapon sword) {//constructor με ορίσματα
		this.id=id;
		this.name=name;
		this.board=board;
		this.score=score;
		this.x=x;
		this.y=y;
		this.bow=bow;
		this.pistol=pistol;
		this.sword=sword;
	}
	
	public void setId(int id) {//setters των μεταβλητών της κλάσης 
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setBoard(Board board) {
		this.board = board;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public void setBow(Weapon bow) {
		this.bow = bow;
	}
	public void setPistol(Weapon pistol) {
		this.pistol = pistol;
	}
	public void setSword(Weapon sword) {
		this.sword = sword;
	}
	public void setXPixel(int xPixel) {
		this.xPixel = xPixel;
	}
	public void setYPixel(int yPixel) {
		this.yPixel = yPixel;
	}
	
	public int getId() {//getters των μεταβλητών της κλάσης
		return id;
	}
	public String getName() {
		return name;
	}
	public Board getBoard() {
		return board;
	}
	public int getScore() {
		return score;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public Weapon getBow() {
		return bow;
	}
	public Weapon getPistol() {
		return pistol;
	}
	public Weapon getSword() {
		return sword;
	}
	public int getXPixel() {
		return xPixel;
	}
	public int getYPixel() {
		return yPixel;
	}
	
	/*η συνάρτηση αυτή δίνει με τυχαίο τρόπο νέες τιμές στις συντεταγμένες του παίκτη ,
	 * λαμβάνοντας υπ'όψιν και τους περιορισμούς που υπάρχουν λόγω των ορίων του ταμπλό.έτσι αφού 
	 * ενημερώσει τις συντεταγμένες του παίκτη ,τις τοποθετεί σε έναν πίνακα και τον επιστρέφει */
	public int[] getRandomMove(){
		Random rand = new Random(); 
		int[] randomMove = new int[2];
		int move;
		move = rand.nextInt(8) + 1;
		if(getX() == -board.getN()/2) {
			if(getY() == -board.getM()/2) {
				move = rand.nextInt(3) + 3;
			}
			else if (getY() == board.getM()/2) {
				move = rand.nextInt(3) + 1;
			}
			else {
				move = rand.nextInt(5) + 1;
			}
		}
		if(getX() == board.getN()/2) {
			if(getY() == -board.getM()/2) {
				move = rand.nextInt(3) + 5;
			}
			else if(getY() == board.getM()/2) {
				int r = rand.nextInt(3);
				move = new int[] {1, 7, 8} [r];
			}
			else {
				int r = rand.nextInt(5);
				move = new int[] {1, 5, 6, 7, 8} [r];
			}
		}
		if(getY() == -board.getM()/2 && Math.abs(getX()) != board.getN()/2) {
			move = rand.nextInt(5) + 3;
		}
		if(getY() == board.getM()/2 && Math.abs(getX()) != board.getN()/2) {
			int r = rand.nextInt(5);
			move = new int[] {1, 2, 3, 7, 8} [r];
		}/*οι 8 ειδικές περιπτώσεις που ο παίκτης δεν μπορεί να μετακινηθεί
		προς όλες τις κατευθύνσεις*/
		
		switch (move) {
		case 1:
			randomMove[0] = getX();
			randomMove[1] = getY() - 1;
			yPixel -= 25;
			if (randomMove[1] == 0) {
				randomMove[1] --;
			}
			break;
		case 2:
			randomMove[0] = getX() + 1;
			randomMove[1] = getY() - 1;
			xPixel +=25;
			yPixel -=25;
			if (randomMove[1] == 0) {
				randomMove[1] --;
			}
			if (randomMove[0] == 0) {
				randomMove[0] ++;
			}
			break;
		case 3:
			randomMove[0] = getX() + 1;
			randomMove[1] = getY();
			xPixel +=25;
			if (randomMove[0] == 0) {
				randomMove[0] ++;
			}
			break;
		case 4:
			randomMove[0] = getX() + 1;
			randomMove[1] = getY() + 1;
			xPixel +=25;
			yPixel +=25;
			if (randomMove[0] == 0) {
				randomMove[0] ++;
			}
			if (randomMove[1] == 0) {
				randomMove[1] ++;
			}
			break;
		case 5:
			randomMove[0] = getX();
			randomMove[1] = getY() + 1;
			yPixel +=25;
			if (randomMove[1] == 0) {
				randomMove[1] ++;
			}
			break;
		case 6:
			randomMove[0] = getX() - 1;
			randomMove[1] = getY() + 1;
			xPixel -=25;
			yPixel +=25;
			if (randomMove[1] == 0) {
				randomMove[1] ++;
			}
			if (randomMove[0] == 0) {
				randomMove[0] --;
			}
			break;
		case 7:
			randomMove[0] = getX() - 1;
			randomMove[1] = getY();
			xPixel -=25;
			if (randomMove[0] == 0) {
				randomMove[0] --;
			}
			break;
		case 8:
			randomMove[0] = getX() - 1;
			randomMove[1] = getY() - 1;
			xPixel -=25;
			yPixel -=25;
			if (randomMove[0] == 0) {
				randomMove[0] --;
			}
			if (randomMove[1] == 0) {
				randomMove[1] --;
			}
		}
		System.out.println("Random move: " + move);
		System.out.println("New position of " + getName() + " is: (x, y) = (" + randomMove[0] + ", " + randomMove[1] + ")");

		setX(randomMove[0]);
		setY(randomMove[1]);
		return randomMove;
	}
	/*η συνάρτηση επιστρέφει έναν πίνακα τύπου int που περιλαμβάνει τις χ και y συντεταγμένες του παίκτη,
	 * τον αριθμό των όπλων ,τον αριθμό των παγίδων και τον αριθμό των εφοδίων του κάθε παίκτη ενημερωμένους
	 * για κάθε γύρο.Ακόμα ενημερώνει τον παίκτη ,με εκτύπωση του κατάλληλου μηνύματος στην κονσόλα,όταν πέφτει 
	 * σε παγίδες,εφόδια και όπλα*/
	public int[] move() {
		int t=0;
		int w=0;
		int f=0;
		int[] a = new int[5];
		a[0] = getX();
		a[1] = getY();
		for (int i = 0; i<board.getW(); i++) {
			if(getX() == board.getWeapons()[i].getX() && getY() == board.getWeapons()[i].getY() && getId() == board.getWeapons()[i].getPlayerId()) {
				System.out.println(getName() + " found weapon with number " + board.getWeapons()[i].getId() + " witch is a "+board.getWeapons()[i].getType()+" !");
				w++;
				a[2] = w;
				board.getWeapons()[i].setX(0);
				board.getWeapons()[i].setY(0);
				if (board.getWeapons()[i].getType() == "Bow") {
					setBow(board.getWeapons()[i]);
				}
				else if (board.getWeapons()[i].getType() == "Sword") {
					setSword(board.getWeapons()[i]);
				}
				else if (board.getWeapons()[i].getType() == "Pistol") {
					setPistol(board.getWeapons()[i]);
				}
			}
		}
		for (int i = 0; i<board.getF(); i++) {
			if(getX() == board.getFood()[i].getX() && getY() == board.getFood()[i].getY() ) {
				
				System.out.println("Bon Appetit!!!" + getName() + " found food with number " + board.getFood()[i].getId() + "!");
				f++;
				a[3]=f;
				board.getFood()[i].setX(0);
				board.getFood()[i].setY(0);
				setScore(getScore() + board.getFood()[i].getPoints());
			}
		}
		for (int i = 0; i<board.getT(); i++) {
			if(getX() == board.getTraps()[i].getX() && getY() == board.getTraps()[i].getY() ) {		
				System.out.println("Oops! " + getName() + " fell into the trap with number " + board.getTraps()[i].getId() + " ...");
				t++;
				a[4]=t;
				if (sword == null && board.getTraps()[i].getType() == "Rope") {
					setScore(getScore() + board.getTraps()[i].getPoints());
					System.out.println(getName() + " had not a sword.");
				}
				if (bow == null && board.getTraps()[i].getType() == "Animal") {
					setScore(getScore() + board.getTraps()[i].getPoints());
					System.out.println(getName() + " had not a bow.");
				}
				if (sword != null && board.getTraps()[i].getType() == "Rope") {
					System.out.println(getName() + " luckily had a sword.");
				}
				if (bow != null && board.getTraps()[i].getType() == "Animal") {
					System.out.println(getName() + " luckily had a bow.");
				}
			}
		}
		return a;
	}
}