public class Trap{
	private int id;
	private int x;
	private int y;
	private String type;
	private int points;


	public Trap() {//êåíüò constructor
		id=0;
		x=0;
		y=0;
		type="";
		points=0;
	}
	public Trap(int id,int x,int y,String type,int points) {//constructor ìå ïñßóìáôá
		this.id=id;
		this.x=x;
		this.y=y;
		if(type=="Rope"||type=="Animal") {
			this.type=type;
		}
		else {
			System.out.println("This is not a valid type of trap.I will set it as Rope");
			type="Rope";
		}
		this.points=points;
	}
	public Trap(Trap TrapObject) {//áíôéêåßìåíï ôýðïõ Trap
		id=TrapObject.id;
		x=TrapObject.x;
		y=TrapObject.y;
		type=TrapObject.type;
		points=TrapObject.points;
	}
	public void setId(int id) {//setters ôùí ìåôáâëçôþí ôçò êëÜóçò 
		this.id=id;
		
	}
	public void setX(int x) {
		this.x=x;
	}
	public void setY(int y) {
		this.y=y;
	}
	public void setType(String type) {
		if(type=="Rope"||type=="Animal") {
			this.type=type;
		}
		else {
			System.out.println("This is not a valid type of trap.I will set it as Rope");
			type="Rope";
		}
		}
	public void setPoints(int points) {
		this.points=points;
		}
	public int getId() {//getters ôùí ìåôáâëçôþí ôçò êëÜóçò 
		return id;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public String getType() {
		return type;
	}
	public int getPoints() {
		return points;
	}
}
