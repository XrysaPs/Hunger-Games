import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Map; 
import java.util.Collections;
import java.util.Map.Entry;

public class HeuristicPlayer extends Player{
    private ArrayList<int[]> path;
    final static int r=3;
	public int xPixelHeuristic=925;
	public int yPixelHeuristic=575;
	
	public void setXPixelHeuristic(int xPixelHeuristic) {
		this.xPixelHeuristic = xPixelHeuristic;
	}
	public void setYPixelHeuristic(int yPixel) {
		this.yPixelHeuristic = yPixelHeuristic;
	}
	
	public int getXPixelHeuristic() {
		return xPixelHeuristic;
	}
	public int getYPixelHeuristic() {
		return yPixelHeuristic;
	}

    public HeuristicPlayer() {
        super();
        path = new ArrayList<int[]> ();
    }

    public HeuristicPlayer(int id,String name,Board board,int score,int x,int y,Weapon bow,Weapon pistol,Weapon sword,int n ) {
        super(id, name, board, score, x, y, bow, pistol, sword);
        path = new ArrayList<int[]> (n);
    }

    public float forceKillValue(int x, int y, Player p) {  // we calculate the Euclidean distance of the players
        if ((float)Math.sqrt(Math.pow(x-p.x, 2)+Math.pow(y-p.y, 2))<=r && pistol != null) { //we take into consideration whether or not the players are visible to each other (distance<=r) and if the Hp packs a pistol
            return (float)Math.sqrt(Math.pow(x-p.x, 2)+Math.pow(y-p.y, 2));
        }
        return -1;
    }

    public float playerDistance(Player p) {  // we calculate the distance of the players in rings that wrap around the Hp
        int d;
        for(d=3; d>=0; d--) {
            int x1=x-d;
            int x2=x+d;
            int y1=y-d;
            int y2=y+d;
            if (x>0 && x-d<=0) {
                x1=x1-1;
            }
            if (x<0 && x+d>=0) {
                x2=x2+1;
            }
            if (y>0 && y-d<=0) {
                y1=y1-1;
            }
            if (y<0 && y+d>=0) {
                y2=y2+1;
            }
            for(int i=y1; i<=y2; i++) {
                if(i==0) {
                    continue;
                }
                for(int j=x1; j<=x2; j++) {
                    if(j==0) {
                        continue;
                    }
                    if (i>y1 && i<y2) {
                        if (j>x1 && j<x2) {
                            continue;
                        }
                    }
                    if(p.x==j && p.y==i) {
                        if(d<=r) {
                            if(pistol!=null || p.pistol!=null) {
                                return d;
                            }
                        }
                    }
                }
            }
        }
        return -1;
    }

    public double evaluate (int dice, Player p) {
        int gainWeapons = 0;
        int avoidTraps = 0;
        int gainPoints = 0;
        float forceKill = 0;
        int m=1;
        int k=1;
        switch (dice) {   // we evaluate the move based only on the data of the first ring that surrounds the player
            case 1:
                if(y==1) {  // avoid y = 0
                    k=2;
                }
                for (int f=0; f<board.getF(); f++) {
                    if (board.getFood()[f].getX()==x && board.getFood()[f].getY()==y-k) { // adding the points of the food to the score of the Hp
                        gainPoints=board.getFood()[f].getPoints();
                        break;
                    }
                }
                for (int t=0; t<board.getT(); t++) {
                    if (board.getTraps()[t].getX()==x && board.getTraps()[t].getY()==y-k) {    // check for possible exception in order not to take into consideration the specific trap
                        if (sword != null && board.getTraps()[t].getType() == "Rope") {
                            avoidTraps=0;
                            break;
                        }
                        else if (bow != null &&  board.getTraps()[t].getType() == "Animal") {
                            avoidTraps=0;
                            break;
                        }
                        avoidTraps=board.getTraps()[t].getPoints();
                        break;
                    }
                }
                for (int w=0; w<board.getW(); w++) {
                    if (board.getWeapons()[w].getX()==x && board.getWeapons()[w].getY()==y-k && board.getWeapons()[w].getPlayerId()==id) {    // we check for weapons prioritizing them over the food (gainWeapons > gainPoints) and the pistol over the other weapons
                        if (board.getWeapons()[w].getType() == "Pistol") {
                            gainWeapons=15;
                        }
                        else if (board.getWeapons()[w].getType() == "Bow" || board.getWeapons()[w].getType() == "Sword") {
                            gainWeapons=11;
                        }
                        break;
                    }
                }
                forceKill = forceKillValue(x, y-k, p);   // it assigns a value to forceKill only if the other player is visible and Hp packs a pistol, if the previous specifications are met then this variable helps the Hp to get closer to the other player
                break;
            case 2:
                if (x==-1) {
                    m=2;
                }
                if (y==1) {
                    k=2;
                }
                for (int f=0; f<board.getF(); f++) {
                    if (board.getFood()[f].getX()==x+m && board.getFood()[f].getY()==y-k) {
                        gainPoints=board.getFood()[f].getPoints();
                        break;
                    }
                }
                for (int t=0; t<board.getT(); t++) {
                    if (board.getTraps()[t].getX()==x+m && board.getTraps()[t].getY()==y-k) {
                        if (sword != null && board.getTraps()[t].getType() == "Rope") {
                            avoidTraps=0;
                            break;
                        }
                        else if (bow != null &&  board.getTraps()[t].getType() == "Animal") {
                            avoidTraps=0;
                            break;
                        }
                        avoidTraps=board.getTraps()[t].getPoints();
                        break;
                    }
                }
                for (int w=0; w<board.getW(); w++) {
                    if (board.getWeapons()[w].getX()==x+m && board.getWeapons()[w].getY()==y-k && board.getWeapons()[w].getPlayerId()==id) {
                        if (board.getWeapons()[w].getType() == "Pistol") {
                            gainWeapons=15;
                        }
                        else if (board.getWeapons()[w].getType() == "Bow" || board.getWeapons()[w].getType() == "Sword") {
                            gainWeapons=11;
                        }
                        break;
                    }
                }
                forceKill = forceKillValue(x+m, y-k, p);
                break;
            case 3:
                if (x==-1) {
                    m=2;
                }
                for (int f=0; f<board.getF(); f++) {
                    if (board.getFood()[f].getX()==x+m && board.getFood()[f].getY()==y) {
                        gainPoints=board.getFood()[f].getPoints();
                        break;
                    }
                }
                for (int t=0; t<board.getT(); t++) {
                    if (board.getTraps()[t].getX()==x+m && board.getTraps()[t].getY()==y) {
                        if (sword != null && board.getTraps()[t].getType() == "Rope") {
                            avoidTraps=0;
                            break;
                        }
                        else if (bow != null &&  board.getTraps()[t].getType() == "Animal") {
                            avoidTraps=0;
                            System.out.println("trap with number"+board.getTraps()[t].getId()+" points: "+board.getTraps()[t].getPoints()+"Player has bow "+bow.getId());
                            break;
                        }
                        avoidTraps=board.getTraps()[t].getPoints();
                        break;
                    }
                }
                for (int w=0; w<board.getW(); w++) {
                    if (board.getWeapons()[w].getX()==x+m && board.getWeapons()[w].getY()==y && board.getWeapons()[w].getPlayerId()==id) {
                        if (board.getWeapons()[w].getType() == "Pistol") {
                            gainWeapons=15;
                        }
                        else if (board.getWeapons()[w].getType() == "Bow" || board.getWeapons()[w].getType() == "Sword") {
                            gainWeapons=11;
                        }
                        break;
                    }
                }
                forceKill = forceKillValue(x+m, y, p);
                break;
            case 4:
                if (x==-1) {
                    m=2;
                }
                if (y==-1) {
                    k=2;
                }
                for (int f=0; f<board.getF(); f++) {
                    if (board.getFood()[f].getX()==x+m && board.getFood()[f].getY()==y+k) {
                        gainPoints=board.getFood()[f].getPoints();
                        break;
                    }
                }
                for (int t=0; t<board.getT(); t++) {
                    if (board.getTraps()[t].getX()==x+m && board.getTraps()[t].getY()==y+k) {
                        if (sword != null && board.getTraps()[t].getType() == "Rope") {
                            avoidTraps=0;
                            break;
                        }
                        else if (bow != null &&  board.getTraps()[t].getType() == "Animal") {
                            avoidTraps=0;
                            System.out.println("trap with number"+board.getTraps()[t].getId()+" points: "+board.getTraps()[t].getPoints()+"Player has bow "+bow.getId());
                            break;
                        }
                        avoidTraps=board.getTraps()[t].getPoints();
                        break;
                    }
                }
                for (int w=0; w<board.getW(); w++) {
                    if (board.getWeapons()[w].getX()==x+m && board.getWeapons()[w].getY()==y+k && board.getWeapons()[w].getPlayerId()==id) {
                        if (board.getWeapons()[w].getType() == "Pistol") {
                            gainWeapons=15;
                        }
                        else if (board.getWeapons()[w].getType() == "Bow" || board.getWeapons()[w].getType() == "Sword") {
                            gainWeapons=11;
                        }
                        break;
                    }
                }
                forceKill = forceKillValue(x+m, y+k, p);
                break;
            case 5:
                if (y==-1) {
                    k=2;
                }
                for (int f=0; f<board.getF(); f++) {
                    if (board.getFood()[f].getX()==x && board.getFood()[f].getY()==y+k) {
                        gainPoints=board.getFood()[f].getPoints();
                        break;
                    }
                }
                for (int t=0; t<board.getT(); t++) {
                    if (board.getTraps()[t].getX()==x && board.getTraps()[t].getY()==y+k) {
                        if (sword != null && board.getTraps()[t].getType() == "Rope") {
                            avoidTraps=0;
                            break;
                        }
                        else if (bow != null &&  board.getTraps()[t].getType() == "Animal") {
                            avoidTraps=0;
                            break;
                        }
                        avoidTraps=board.getTraps()[t].getPoints();
                        System.out.println("trap with number"+board.getTraps()[t].getId()+" points: "+board.getTraps()[t].getPoints());
                        break;
                    }
                }
                for (int w=0; w<board.getW(); w++) {
                    if (board.getWeapons()[w].getX()==x && board.getWeapons()[w].getY()==y+k && board.getWeapons()[w].getPlayerId()==id) {
                        if (board.getWeapons()[w].getType() == "Pistol") {
                            gainWeapons=15;
                        }
                        else if (board.getWeapons()[w].getType() == "Bow" || board.getWeapons()[w].getType() == "Sword") {
                            gainWeapons=11;
                        }
                        break;
                    }
                }
                forceKill = forceKillValue(x, y+k, p);
                break;
            case 6:
                if (x==1) {
                    m=2;
                }
                if (y==-1) {
                    k=2;
                }
                for (int f=0; f<board.getF(); f++) {
                    if (board.getFood()[f].getX()==x-m && board.getFood()[f].getY()==y+k) {
                        gainPoints=board.getFood()[f].getPoints();
                        break;
                    }
                }
                for (int t=0; t<board.getT(); t++) {
                    if (board.getTraps()[t].getX()==x-m && board.getTraps()[t].getY()==y+k) {
                        if (sword != null && board.getTraps()[t].getType() == "Rope") {
                            avoidTraps=0;
                            break;
                        }
                        else if (bow != null &&  board.getTraps()[t].getType() == "Animal") {
                            avoidTraps=0;
                            System.out.println("trap with number"+board.getTraps()[t].getId()+" points: "+board.getTraps()[t].getPoints()+"Player has bow "+bow.getId());
                            break;
                        }
                        avoidTraps=board.getTraps()[t].getPoints();
                        break;
                    }
                }
                for (int w=0; w<board.getW(); w++) {
                    if (board.getWeapons()[w].getX()==x-m && board.getWeapons()[w].getY()==y+k && board.getWeapons()[w].getPlayerId()==id) {
                        if (board.getWeapons()[w].getType() == "Pistol") {
                            gainWeapons=15;
                        }
                        else if (board.getWeapons()[w].getType() == "Bow" || board.getWeapons()[w].getType() == "Sword") {
                            gainWeapons=11;
                        }
                        break;
                    }
                }
                forceKill = forceKillValue(x-m, y+k, p);
                break;
            case 7:
                if (x==1) {
                    m=2;
                }
                for (int f=0; f<board.getF(); f++) {
                    if (board.getFood()[f].getX()==x-m && board.getFood()[f].getY()==y) {
                        gainPoints=board.getFood()[f].getPoints();
                        break;
                    }
                }
                for (int t=0; t<board.getT(); t++) {
                    if (board.getTraps()[t].getX()==x-m && board.getTraps()[t].getY()==y) {
                        if (sword != null && board.getTraps()[t].getType() == "Rope") {
                            avoidTraps=0;
                            break;
                        }
                        else if (bow != null &&  board.getTraps()[t].getType() == "Animal") {
                            avoidTraps=0;
                            break;
                        }
                        avoidTraps=board.getTraps()[t].getPoints();
                        break;
                    }
                }
                for (int w=0; w<board.getW(); w++) {
                    if (board.getWeapons()[w].getX()==x-m && board.getWeapons()[w].getY()==y && board.getWeapons()[w].getPlayerId()==id) {
                        if (board.getWeapons()[w].getType() == "Pistol") {
                            gainWeapons=15;
                        }
                        else if (board.getWeapons()[w].getType() == "Bow" || board.getWeapons()[w].getType() == "Sword") {
                            gainWeapons=11;
                        }
                        break;
                    }
                }
                forceKill = forceKillValue(x-m, y, p);
                break;
            case 8:
                if (x==1) {
                    m=2;
                }
                if (y==1) {
                    k=2;
                }
                for (int f=0; f<board.getF(); f++) {
                    if (board.getFood()[f].getX()==x-m && board.getFood()[f].getY()==y-k) {
                        gainPoints=board.getFood()[f].getPoints();
                        break;
                    }
                }
                for (int t=0; t<board.getT(); t++) {
                    if (board.getTraps()[t].getX()==x-m && board.getTraps()[t].getY()==y-k) {
                        if (sword != null && board.getTraps()[t].getType() == "Rope") {
                            avoidTraps=0;
                            break;
                        }
                        else if (bow != null &&  board.getTraps()[t].getType() == "Animal") {
                            avoidTraps=0;
                            System.out.println("trap with number"+board.getTraps()[t].getId()+" points: "+board.getTraps()[t].getPoints()+"Player has bow "+bow.getId());
                            break;
                        }
                        avoidTraps=board.getTraps()[t].getPoints();
                        break;
                    }
                }
                for (int w=0; w<board.getW(); w++) {
                    if (board.getWeapons()[w].getX()==x-m && board.getWeapons()[w].getY()==y-k && board.getWeapons()[w].getPlayerId()==id) {
                        if (board.getWeapons()[w].getType() == "Pistol") {
                            gainWeapons=15;
                        }
                        else if (board.getWeapons()[w].getType() == "Bow" || board.getWeapons()[w].getType() == "Sword") {
                            gainWeapons=11;
                        }
                        break;
                    }
                }
                forceKill = forceKillValue(x-m, y-k, p);
                break;
        }
        if (pistol!=null) {
            return (float) gainWeapons*0.05 + avoidTraps*0.1 + gainPoints*0.2 + (1/forceKill)*0.65; // if Hp is armed with the pistol then he will look for the kill on the other player
        }
        return (float) gainWeapons*0.35 + avoidTraps*0.1 + gainPoints*0.2;
    }
    
	public static int getMaxEntry(Map<Integer, Double> map){
        double max = Collections.max(map.values());    //we get the max value of the map
        ArrayList<Integer> a = new ArrayList<Integer> ();
        for(Entry<Integer, Double> entry : map.entrySet()) { // we check if there are other keys with the max value in the map
            if(max == entry.getValue()) {
                a.add(entry.getKey());  //if there are then we save all the keys that point to the max value
            }
        }
        Random rand = new Random();
        int randomValue=a.get(rand.nextInt(a.size())); // we choose one of the saved keys randomly
        return randomValue;
    }

    int[] MoveHeuristicPlayer(Player p) {
        int[] FinalMove = new int[2];
        Map<Integer,Double> Evaluation =new HashMap<Integer, Double>();
        int die=0;
        int previousScore=0;
        // we evaluate the available moves and save them in a map (key=die & value=evaluation Of the Move)
        if(Math.abs(getX()) != board.getN()/2 && Math.abs(getY()) != board.getM()/2) { // if all moves are available
            for (die = 1; die <= 8; die++) {
                Evaluation.put(die, evaluate(die, p));
            }
        }

        if(getX() == -board.getN()/2){
            if(getY() == -board.getM()/2){ // if the Hp stands on the Left Up Corner
                for(die = 3; die <= 5; die++) {
                    Evaluation.put(die, evaluate(die, p));
                }
            }
            else if (getY() == board.getM()/2){ // if the Hp stands on the Left Down Corner
                for(die = 1; die<=3; die++) {
                    Evaluation.put(die, evaluate(die, p));
                }
            }
            else{
                for(die = 1; die<=5; die++) { // if the Hp stands on a tile that is on the first column except the tiles that are located on the LUC and LDC
                    Evaluation.put(die, evaluate(die, p));
                }
            }
        }

        if(getX() == board.getN()/2){ // if the Hp stands on Right Up Corner
            if(getY() == -board.getM()/2){
                for(die = 5; die<=7; die++) {
                    Evaluation.put(die, evaluate(die, p));
                }
            }
            else if(getY() == board.getM()/2){ // if the Hp stands on the Right Down Corner
                Evaluation.put(1, evaluate(1, p));
                Evaluation.put(7, evaluate(7, p));
                Evaluation.put(8, evaluate(8, p));
            }
            else{
                Evaluation.put(1, evaluate(1, p));  // if the Hp stands on a tile that is on the last column except the tiles that are located on the RUC and RDC
                for(die = 5; die<=8;  die++) {
                    Evaluation.put(die, evaluate(die, p));
                }
            }
        }

        if(getY() == -board.getM()/2 && Math.abs(getX()) != board.getN()/2){  // if the Hp stands on a tile that is on the first line except the tiles that are located on the LUC and RUC
            for(die = 3; die<=7; die++) {
                Evaluation.put(die, evaluate(die, p));
            }
        }

        if(getY() == board.getM()/2 && Math.abs(getX()) != board.getN()/2){ // if the Hp stands on a tile that is on the last line except the tiles that are located on the LDC and RDC
            for(die = 1; die<=3; die++) {
                Evaluation.put(die, evaluate(die, p));
            }
            Evaluation.put(7, evaluate(7, p));
            Evaluation.put(8, evaluate(8, p));
        }
        int maxDie=8;       // since the possible moves in the begging of the game have equal evaluation we programmed Hp in a way so that his move in the early game can be a bit more strategic
        if(x>0 && y>0) {
            maxDie = 8;
        }
        if (x<0 && y>0) {
            maxDie = 2;
        }
        if (x>0 && y<0) {
            maxDie = 6;
        }
        if (x<0 && y<0) {
            maxDie = 4;
        }
        if (Math.abs(x)<=5 && Math.abs(y)<=5) {  // he will move closer to the centre of the board until a specified limit ignoring the results of the method evaluate
            maxDie=getMaxEntry(Evaluation);
        }
        switch (maxDie) {
            case 1:     // we dont want the coordinates to be equal to 0 so we take precautions
                FinalMove[0] = getX();
                FinalMove[1] = getY() - 1;
    			yPixelHeuristic -=25;
                if (FinalMove[1] == 0) {
                    FinalMove[1] --;
                }
                break;
            case 2:
                FinalMove[0] = getX() + 1;
                FinalMove[1] = getY() - 1;
                xPixelHeuristic +=25;
    			yPixelHeuristic -=25;
                if (FinalMove[1] == 0) {
                    FinalMove[1] --;
                }
                if (FinalMove[0] == 0) {
                    FinalMove[0] ++;
                }
                break;
            case 3:
                FinalMove[0] = getX() + 1;
                FinalMove[1] = getY();
                xPixelHeuristic +=25;
                if (FinalMove[0] == 0) {
                    FinalMove[0] ++;
                }
                break;
            case 4:
                FinalMove[0] = getX() + 1;
                FinalMove[1] = getY() + 1;
                xPixelHeuristic +=25;
    			yPixelHeuristic +=25;
                if (FinalMove[0] == 0) {
                    FinalMove[0] ++;
                }
                if (FinalMove[1] == 0) {
                    FinalMove[1] ++;
                }
                break;
            case 5:
                FinalMove[0] = getX();
                FinalMove[1] = getY() + 1;
    			yPixelHeuristic +=25;
                if (FinalMove[1] == 0) {
                    FinalMove[1] ++;
                }
                break;
            case 6:
                FinalMove[0] = getX() - 1;
                FinalMove[1] = getY() + 1;
                xPixelHeuristic -=25;
    			yPixelHeuristic +=25;
                if (FinalMove[1] == 0) {
                    FinalMove[1] ++;
                }
                if (FinalMove[0] == 0) {
                    FinalMove[0] --;
                }
                break;
            case 7:
                FinalMove[0] = getX() - 1;
                FinalMove[1] = getY();
                xPixelHeuristic -=25;
                if (FinalMove[0] == 0) {
                    FinalMove[0] --;
                }
                break;
            case 8:
                FinalMove[0] = getX() - 1;
                FinalMove[1] = getY() - 1;
                xPixelHeuristic -=25;
    			yPixelHeuristic -=25;
                if (FinalMove[0] == 0) {
                    FinalMove[0] --;
                }
                if (FinalMove[1] == 0) {
                    FinalMove[1] --;
                }
        }
        System.out.println("HeuristicPlayer Move: " + maxDie);
        System.out.println("New position of " + getName() + " is: (x, y) = (" + FinalMove[0] + ", " + FinalMove[1] + ")");

        previousScore=score;

        setX(FinalMove[0]);
        setY(FinalMove[1]);
        int[] a = new int [5];
        a=move();
        a[0]=maxDie;
        a[1]=score-previousScore;
        path.add(a);
        return FinalMove;
    }

    static boolean killP1(Player p1, HeuristicPlayer p2,double d){  // used by the Hp only
        if (p2.playerDistance(p1)<=d && p2.playerDistance(p1) != -1) {
            if(p1.pistol!=null) {
                System.out.println("Player " +p2.id+ " is dead.");
                return true;
            }
        }
        return false;
    }

    static boolean killP2(Player p1, HeuristicPlayer p2,double d){  // used by the player only
        if (p2.playerDistance(p1)<=d && p2.playerDistance(p1) != -1) {
            if(p2.pistol!=null) {
                System.out.println("Player " +p1.id+ " is dead.");
                return true;
            }
        }
        return false;
    }

    public void statistics(int round) { // printing the values of the path variable
        System.out.println("In round " + round + " the Heuristic player rolled a " + path.get(round-1)[0] + ", gained " + path.get(round-1)[1] + " points, ate " + path.get(round-1)[3] + " food, interacted with " + path.get(round-1)[4] + " trap and picked up " + path.get(round-1)[2] + " weapon.");
    }
}
