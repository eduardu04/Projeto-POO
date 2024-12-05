package pt.iscte.poo.game;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.colorchooser.ColorChooserComponentFactory;
import objects.*;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;
import pt.iscte.poo.utils.Direction;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import pt.iscte.poo.gui.ImageTile;
public class Room {
	
	private static Point2D heroStartingPosition;
	private static Manel manel;
	private String level;
	private static char[][] matrixRoom;
	private static List<Interactable> objetosInteractable;
	private static List<Movable> objetosMoveis;
	
	public Room() {
		addFloor();
		objetosInteractable=new ArrayList<>();
		objetosMoveis=new ArrayList<>();
	}

	public void moveManel(Direction d) {
		if(canMove(manel,d)){
			manel.move(d);
		}
		
	}

	public void moveMovables(){ 
		for(Movable i : objetosMoveis){
			i.move(randomPossibleDirection(i));
		}
	}
	
	public void interactTemp() {
		for(Interactable i : objetosInteractable) {
			if(isInteractable(i) == true) {
				i.interact(manel);
				deleteObject(i);
			}
		}
	}

	public void deleteObject(Interactable obj) {
		Point2D posAt = ((ImageTile) obj).getPosition();
		matrixRoom[posAt.getY()][posAt.getX()]=' ';
		ImageTile espada= findObjectByPoint(posAt);
		ImageGUI.getInstance().removeImage(espada);	//remove imagem do mapa
		objetosInteractable.remove(obj);	//remove objeto da lista de objetos temporarios
		matrixRoom[posAt.getY()][posAt.getX()]=' ';//Apaga o objeto da matriz
	}
	
	public Direction randomPossibleDirection(Movable ob){
		Direction d = Direction.random();
		while(!canMove(ob,d)){
			d = Direction.random();
		}
		return d;
	}

	public static Room readRoomFile(File f) {
		Room sala = new Room();
		try {
			//scanear mapa
			Scanner sc = new Scanner(f);
			sala.level = sc.nextLine();
			String letras = "";
			while(sc.hasNext()){
				letras+=sc.nextLine();
			}
			matrixRoom=stringToMatrix(letras);
			loadMap();//carrega o mapa da mATRIxroom para o mapa mesmo
			sc.close();
			
			
		} catch (Exception FileNotFoundException) {
			System.err.println("Erroooo");
		}
		return sala;
	}

	public static void addFloor(){
		for(int i = 0; i != 10; i++){
			for(int j = 0; j != 10; j++){
				ImageGUI.getInstance().addImage(new Floor(new Point2D(i,j)));
			}
		}
	}

	public boolean canMove(Movable ob, Direction d){
		Point2D nextPosition = ob.getPosition().plus(d.asVector());
		if(insideMap(nextPosition) && !isBlock(nextPosition)){
			if(d.name()=="UP" && !isStairs(ob.getPosition())){//Caso o objeto nao esteja numa escada não pode subir de posição ou seja "voar"!
				return false;
			}
			return true;
		}
		
		return false;
	}

	public boolean isBlock(Point2D p){
		char c = matrixRoom[p.getY()][p.getX()];
		if(c== 'W'||c== 't'){
			return true;
		}
		return false;
	}
	public boolean isStairs(Point2D p){
		char c = matrixRoom[p.getY()][p.getX()];
		if(c == 'S'){
			return true;
		}
		return false;
	}

	public boolean isDoor(Point2D p) {
		char c = matrixRoom[p.getY()][p.getX()];
		if(c == '0') {
			return true;
		}
		return false;
	}

	public boolean isInteractable(Interactable object) {
		return manel.getPosition() == ((ImageTile) object).getPosition();
	}
	
	public void manelFall(){//Quando nao tem nada por baixo o Manel cai
		Point2D manelGround = manel.getPosition().plus(new Vector2D(0, 1));
		if(!isBlock(manelGround) && !isStairs(manelGround)){
			moveManel(Direction.DOWN);
		}
	}

	public boolean insideMap(Point2D p){
		if(p.getX()==-1||p.getX()==10||p.getY()==-1||p.getY()==10){
			return false;
		}
		return true;
	}

	public static char[][] stringToMatrix(String s){
		char[][] matr = new char[10][10];
		for(int i=0; i!= s.length();i++){
			matr[i/10][i%10]=s.charAt(i);
			System.out.println("indice ["+ i/10+"]"+ "["+i%10+"]"+ " é "+s.charAt(i) );
		}
		return matr;
	}


	public static void loadMap(){
		GameObject fixo;
		Interactable interactable;
		Movable movel;
		for(int i = 0; i != 10;i++){
			for(int j = 0; j != 10; j++){
				switch (matrixRoom[i][j]) {
					case 'W':			
						fixo = new Wall(new Point2D(j,i));
						ImageGUI.getInstance().addImage(fixo);
						break;
						
					case 'S':
						fixo = new Stairs(new Point2D(j,i));
						ImageGUI.getInstance().addImage(fixo);
						break;
						
					case 't':
						fixo = new Trap(new Point2D(j,i));
						ImageGUI.getInstance().addImage(fixo);
						break;
						
					case '0':
						fixo = new Door(new Point2D(j,i));
						ImageGUI.getInstance().addImage(fixo);
						break;
						
					case 'G':
						movel = new DonkeyKong(new Point2D(j,i));
						ImageGUI.getInstance().addImage(movel);
						objetosMoveis.add(movel);
						break;
						
					case 's':
						interactable = new Sword(new Point2D(j,i));
						ImageGUI.getInstance().addImage(interactable);
						objetosInteractable.add(interactable);
						break;
						
					case 'H':
						heroStartingPosition= new Point2D(j,i);
						manel = new Manel(heroStartingPosition);
						ImageGUI.getInstance().addImage(manel);
						break;
					default:
						break;
				}
			}
		}

		
	}
	
	public ImageTile findObjectByPoint(Point2D p){
		for(ImageTile i : objetosInteractable){
			if(i.getPosition().equals(p)){
				return i;
			}
		}
		throw new NullPointerException();
	}

	public void manelStatus() {	
		ImageGUI.getInstance().setStatusMessage("Vidas: " + manel.getLives() + " Saúde: " + manel.getHealth() +  " Dano: " + manel.getDamage());
	}
	
	

}