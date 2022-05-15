import java.awt.Graphics;
import java.util.Random;

public class Board implements Cloneable{
	
	private int N, M;
	private int W, F, T;
	private int[][] weaponAreaLimits;
	private int [][] foodAreaLimits;
	private int [][] trapAreaLimits;
	private Weapon[] weapons;
	private Food[] food;
	private Trap[] traps;
	
	public Object clone(){  
	    try{  
	        return super.clone();  
	    }catch(Exception e){ 
	        return null; 
	    }
	}
	
	public Board() {//êåíüò constructor 
		N = 0;
		M = 0;
		W = 0;
		F = 0;
		T = 0;
		weaponAreaLimits = new int[4][2];
		foodAreaLimits = new int[4][2];
		trapAreaLimits = new int [4][2];
		weapons = new Weapon[W];
		food = new Food[F];
		traps = new Trap[T];
	}
	
	public Board(int N, int M, int W, int F, int T) {//constructor ìå ïñßóìáôá 
		if (N==M) {
			this.M = M;
		}
		else {
			System.out.println("Must be N=M! I will keep the value of N for both!");
			this.M = N;
		}
		this.N = N;
		this.W = W;
		this.F = F;
		this.T = T;
		weapons = new Weapon[W];
		food = new Food[F];
		traps = new Trap[T];
	}
	
	public Board(Board b) {//áíôéêåßìåíï ôýðïõ Board
		this.N = b.N;
		this.M = b.N;
		this.W = b.W;
		this.F = b.F;
		this.T = b.T;
	}
	
	public int getN() {//getters ôùí ìåôáâëçôþí ôçò êëÜóçò 
		return N;
	}
	public int getM() {
		return M;
	}
	public int getW() {
		return W;
	}
	public int getF() {
		return F;
	}
	public int getT() {
		return T;
	}
	public int[][] getWeaponAreaLimits() {//getters ãéá ôïõò ðßíáêåò ôçò êëÜóçò
		return weaponAreaLimits;
	}
	public int[][] getFoodAreaLimits(){
		return foodAreaLimits;
	}
	public int[][] getTrapAreaLimits(){
		return trapAreaLimits;
	}
	public Weapon[] getWeapons() {//getters ãéá ôïõò ðßíáêåò áíôéêåéìÝíùí ôçò êëÜóçò
		return weapons;
	}
	public Food[] getFood() {
		return food;
	}
	public Trap[] getTraps() {
		return traps;
	}
	
	public void setN(int N) {//setters ãéá ôéò ìåôáâëçôÝò ôçò êëÜóçò 
		this.N = N;
	}
	public void setM(int M) {
		if (N==M) {
			this.M = M;
		}
		else {
			System.out.println("Must be N=M! I will keep the value of N for both!");
			this.M = N;
		}
	}
	public void setW(int W) {
		this.W = W;
	}
	public void setF(int F) {
		this.F = F;
	}
	public void setT(int T) {
		this.T = T;
	}
	public void setWeaponAreaLimits(int[][] x) {//setters ãéá ôïõò ðßíáêåò  ôçò êëÜóçò
		weaponAreaLimits = x;
	}
	public void setFoodAreaLimits(int[][] y) {
		foodAreaLimits = y;
	}
	public void setTrapAriaLimits(int[][] z) {
		trapAreaLimits = z;
	}
	public void setWeapons(Weapon[] w) { //setters ãéá ôïõò ðßíáêåò áíôéêåéìÝíùí ôçò êëÜóçò{
		weapons = w;
	}
	public void setFood(Food[] f) {
		food = f;
	}
	public void setTraps(Trap[] t) {
		traps = t;
	}
	
	public void createRandomWeapon() {/*äçìéïõñãßá üðëùí êáé ôïðïèÝôçóç ôïõò óå ôõ÷áßåò èÝóåéò ëáìâÜíïíôáò õð' üøéí
		á)íá ìçí óõìðÝóåé ìå Üëëá üðëá,ðáãßäåò,ôñüöéìá,â)ôçí ðåñéï÷Þ ïñßùí ôùí üðëùí */
		Random rand = new Random(); 
		//6 ôõ÷áßåò ìåôáâëçôÝò ðïõ áíôéðñïóùðåýïõí ôá æåýãç ôùí ôõ÷áßùí óõíôåôáãìÝíùí ãéá êáèÝíá áðü ôïõò ôýðïõò ôùí üðëùí
		int randomX1=0;
		int randomY1=0;
		int randomX2=0;
		int randomY2=0;
		int randomX3=0;
		int randomY3=0;
		int i=0;//äåßêôçò åëÝã÷ïõ
		int s;//äåßêôçò åëÝã÷ïõ
		int id;
		for (int n = 1; n<=2; n++) {
			id = 1;
			do {
				s = i;
				randomX1 = rand.nextInt(Math.abs(2*getWeaponAreaLimits()[0][0]) + 1) - Math.abs(getWeaponAreaLimits()[0][0]);
				randomY1 = rand.nextInt(Math.abs(2*getWeaponAreaLimits()[0][1]) + 1) - Math.abs(getWeaponAreaLimits()[0][1]);
				
				while(s>0){
					
					s--;
					if( weapons[s].getX() != randomX1 || weapons[s].getY() != randomY1) {
						continue;
					}
					
					else if(weapons[s].getX() == randomX1 && weapons[s].getY() == randomY1) {
						randomX1 = rand.nextInt(Math.abs(2*getWeaponAreaLimits()[0][0]) + 1) - Math.abs(getWeaponAreaLimits()[0][0]);
						randomY1 = rand.nextInt(Math.abs(2*getWeaponAreaLimits()[0][1]) + 1) - Math.abs(getWeaponAreaLimits()[0][1]);
						s = i;
						continue;
					}
				}
			}while(randomX1 == 0 || randomY1 == 0);
			
			Weapon weapon1 = new Weapon(id, randomX1, randomY1, n, "Bow");
			weapons[i] = weapon1;
			
			i++;
			id++;
			
			do{
				s = i;
				randomX2 = rand.nextInt(Math.abs(2*getWeaponAreaLimits()[0][0]) + 1) - Math.abs(weaponAreaLimits[0][0]);
				randomY2 = rand.nextInt(Math.abs(2*getWeaponAreaLimits()[0][1]) + 1) - Math.abs(weaponAreaLimits[0][1]);
				
				while(s>0){
					s--;
					if(weapons[s].getX() != randomX2 || weapons[s].getY() != randomY2) {
						continue;
					}
					else if(weapons[s].getX() == randomX2 && weapons[s].getY() == randomY2) {
						randomX2 = rand.nextInt(Math.abs(2*getWeaponAreaLimits()[0][0]) + 1) - Math.abs(weaponAreaLimits[0][0]);
						randomY2 = rand.nextInt(Math.abs(2*getWeaponAreaLimits()[0][1]) + 1) - Math.abs(weaponAreaLimits[0][1]);
						s = i;
						continue;
					}
				}
			}while(randomX2 == 0 || randomY2 == 0);
			Weapon weapon2 = new Weapon(id, randomX2, randomY2, n, "Pistol");
			weapons[i] = weapon2;
			
			i++;
			id++;
			
			
			do{
				s = i;
				randomX3 = rand.nextInt(Math.abs(2*getWeaponAreaLimits()[0][0]) + 1) - Math.abs(weaponAreaLimits[0][0]);
				randomY3 = rand.nextInt(Math.abs(2*getWeaponAreaLimits()[0][1]) + 1) - Math.abs(weaponAreaLimits[0][1]);
				
				while(s>0){
					s--;
					if(weapons[s].getX() != randomX3 || weapons[s].getY() != randomY3) {
						continue;
					}
					else if(weapons[s].getX() == randomX3 && weapons[s].getY() == randomY3) {						
						randomX3 = rand.nextInt(Math.abs(2*getWeaponAreaLimits()[0][0]) + 1) - Math.abs(weaponAreaLimits[0][0]);
						randomY3 = rand.nextInt(Math.abs(2*getWeaponAreaLimits()[0][1]) + 1) - Math.abs(weaponAreaLimits[0][1]);
						s = i;
						continue;
					}
				}
			}while(randomX3 == 0 || randomY3 == 0);
			Weapon weapon3 = new Weapon(id, randomX3, randomY3, n, "Sword");
			weapons[i] = weapon3;
			
			i++;		
		}
	}

	public void createRandomTrap() {/*äçìéïõñãßá ðáãßäùí êáé ôïðïèÝôçóç ôïõò óå ôõ÷áßåò èÝóåéò ëáìâÜíïíôáò õð' üøéí
		á)íá ìçí óõìðÝóåé ìå Üëëåò ðáãßäåò, üðëá êáé ôñüöéìá,â)ôçí ðåñéï÷Þ ïñßùí ôùí ðáãßäùí  */
		Random rand = new Random();
		for (int i=0; i<T; i++) {
			//2 ôõ÷áßåò ìåôáâëçôÝò ðïõ áíôéðñïóùðåýïõí ôá æåýãç ôùí ôõ÷áßùí óõíôåôáãìÝíùí ãéá êáèÝ ðáãßäá 
			int randomX, randomY;
			int t;//äåßêôçò åëÝã÷ïõ
			do {
				do {
					t=i;
					randomX = rand.nextInt(Math.abs(2*trapAreaLimits[0][0]) + 1) - Math.abs(trapAreaLimits[0][0]);
					randomY = rand.nextInt(Math.abs(2*trapAreaLimits[0][1]) + 1) - Math.abs(trapAreaLimits[0][1]);
					while(t>0) {
						t--;
						if (traps[t].getX() != randomX || traps[t].getY() != randomY) {
							continue;
						}
						else if (traps[t].getX() == randomX && traps[t].getY() == randomY) {
							randomX = rand.nextInt(Math.abs(2*trapAreaLimits[0][0]) + 1) - Math.abs(trapAreaLimits[0][0]);
							randomY = rand.nextInt(Math.abs(2*trapAreaLimits[0][1]) + 1) - Math.abs(trapAreaLimits[0][1]);
							t = i; 
							continue;
						}
					}
				}while (Math.abs(randomX) <= Math.abs(foodAreaLimits[0][0]) && Math.abs(randomY) <= Math.abs(foodAreaLimits[0][1]));
			}while(randomX == 0 || randomY == 0);
			int r = rand.nextInt(2);
			String randomType = new String [] {"Rope", "Animal"} [r];
			int randomPoints = rand.nextInt(10) - 10;
			Trap trap = new Trap(i+1, randomX, randomY, randomType, randomPoints);
			traps[i] = trap;
		}
	}
	public void createRandomFood() {/*äçìéïõñãßá åöïäßùí  êáé ôïðïèÝôçóç ôïõò óå ôõ÷áßåò èÝóåéò ëáìâÜíïíôáò õð' üøéí
		á)íá ìçí óõìðÝóåé ìå Üëëá åöüäéá ,üðëá êáé ðáãßäåò,â)ôçí ðåñéï÷Þ ïñßùí ôùí  åöïäßùí */
		Random rand = new Random();
		for (int i=0; i<F; i++) {
			//2 ôõ÷áßåò ìåôáâëçôÝò ðïõ áíôéðñïóùðåýïõí ôá æåýãç ôùí ôõ÷áßùí óõíôåôáãìÝíùí ãéá êáèÝ åöüäéï
			int randomX, randomY;
			int t;//äåßêôçò åëÝã÷ïõ
			do {
				do {
					t=i;
					randomX = rand.nextInt(Math.abs(2*foodAreaLimits[0][0]) + 1) - Math.abs(foodAreaLimits[0][0]);
					randomY = rand.nextInt(Math.abs(2*foodAreaLimits[0][1]) + 1) - Math.abs(foodAreaLimits[0][1]);
					while(t>0) {
						t--;
						if (food[t].getX() != randomX || food[t].getY() != randomY) {
							continue;
						}
						else if (food[t].getX() == randomX && food[t].getY() == randomY) {
							randomX = rand.nextInt(Math.abs(2*foodAreaLimits[0][0]) + 1) - Math.abs(foodAreaLimits[0][0]);
							randomY = rand.nextInt(Math.abs(2*foodAreaLimits[0][1]) + 1) - Math.abs(foodAreaLimits[0][1]);
							t = i; 
							continue;
						}
					}
				}while (Math.abs(randomX) <= Math.abs(weaponAreaLimits[0][0]) && Math.abs(randomY) <= Math.abs(weaponAreaLimits[0][1]));
			}while(randomX==0 || randomY==0);
			
			
			int randomPoints = rand.nextInt(10) + 1;
			Food food1 = new Food(i+1, randomX, randomY, randomPoints);
			food[i] = food1;
		}
	}
	
	public void createBoard() {/*äçìéïõñãïýìå ôï ôáìðëü ôïõ ðáé÷íéäßïõ  */
		createRandomWeapon();
		createRandomFood();
		createRandomTrap();
	}
	
	public void resizeBoard(Player p1, Player p2) {//áëëÜæåé ôï ìÝãåèïò ôïõ ðßíáêá áöáéñþíôáò ðåñéìåôñéêÜ Ýíá äá÷ôõëßäé áðü ôï ôáìðëü
		if(Math.abs(p1.getX()) != N/2 && Math.abs(p1.getY()) != M/2 && Math.abs(p2.getX()) != N/2 && Math.abs(p2.getY()) != M/2) {
			N = N-2;
			M = M-2;
		}
	}
//åêôõðþíåé ôïí ðßíáêá board ðïõ ÷ñçóéìïðïéÞóáìå ãéá íá åêöñÜóïõìå ôï ôáìðëü ôïõ ðáé÷íéäßïõ 
	public String[][] getStringRepresentation(Player p1, HeuristicPlayer hp) {
		String[][] board = new String[M][N];		
		for (int q=0; q<M; q++) {
			for (int d = 0; d<N; d++) {
				board[q][d] = "______";
			}
		}
		int k=0;
		int l=0;

		for(int i=-M/2; i<=M/2; i++) {
			if (i == 0 ) {
				continue;
			}
			for(int j=-N/2; j<=N/2; j++) {
				if (j == 0) {
					continue;
				}
				for (int w=0; w<W; w++) {
					if (weapons[w].getX() == j && weapons[w].getY() == i) {
						board[k][l] = "W" + weapons[w].getPlayerId() + weapons[w].getId()+"___";
						break;
					}
				}
				for(int f=0; f<F; f++) {
					if (food[f].getX() == j && food[f].getY() == i) {
						board[k][l] = "F" + food[f].getId()+"____";
						break;
					}
				}
				for(int t=0; t<T; t++) {
					if (traps[t].getX() == j && traps[t].getY() == i) {
						board[k][l] = "T" + traps[t].getId();
						if(p1.getX()==j && p1.getY()==i) {
							board[k][l]=board[k][l] + "&P" + p1.getId()+"_";
						}
						else if(hp.getX()==j && hp.getY()==i) {
							board[k][l]=board[k][l] + "&P" + hp.getId()+"_";
						}
						else if(p1.getX()==j && p1.getY()==i && hp.getX()==j && hp.getY()==i) {
							board[k][l]=board[k][l] + "&P" + p1.getId() + "&P" + hp.getId();
						}
						else {
							board[k][l]=board[k][l] + "____";
						}
						break;
					}
				}
				if(p1.getX()==j && p1.getY()==i && board[k][l]=="______") {
					board[k][l]="P" + p1.getId()+"____";
					if(hp.getX()==j && hp.getY()==i) {
						board[k][l]="P" + p1.getId()+ "&P" + hp.getId()+"_";
					}
				}
				if(hp.getX()==j && hp.getY()==i && board[k][l]=="______") {
					board[k][l]="P" + hp.getId()+"____";
					if(p1.getX()==j && p1.getY()==i) {
						board[k][l]="P" + p1.getId()+ "&P" + hp.getId()+"_";
					}
				}
				if(l<N-1) {
					l++;
				}
				
			}
			if(k<M-1) {
				k++;
			}
			l=0;
		}
		for (int i=0; i<M; i++) {
			for (int j=0; j<N; j++) {
				System.out.print(board[i][j]+"   ");
			}
			System.out.println();
		}
		return board;
	}
}
