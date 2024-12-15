package pt.iscte.poo.game;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import objects.*;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Room {
	
	private static Point2D heroStartingPosition;
	private static Manel manel;
	private static char[][] matrixRoom;
	private static String level;
	private static List<Interactable> objetosInteractable;
	private static List<Movable> objetosMoveis;
	private static Door currentDoor;
	private static List<Timable> objetosTimable;
	private static List<GameObject> objetosFixos;
	private int lastTickProcessedRoom;
	private boolean loadNextLevel=false;
	private static int levelNum;
	private static Princesa princesa;
		
	
		public Room(int levelNum) {
			this.levelNum = levelNum;
			level = "room" + levelNum + ".txt";
	
			if(levelNum > 0){
				clearPreviousLevel();
				respawnManel(heroStartingPosition, false);
			}
	
			addFloor();
			objetosInteractable = new ArrayList<>();
			objetosMoveis = new ArrayList<>();
			objetosTimable = new ArrayList<>();
			objetosFixos = new ArrayList<>();
			
			System.out.println("A carregar nível: " + level);
			readRoomFile(level);
		}
	
		public void processRoom(){
			manelFall();
			manelStatus();
	
			if(lastTickProcessedRoom % 3 == 0){
				moveMovables();
			}
			if(lastTickProcessedRoom % 8 == 0)	{
				attack();
			}
			
			interact();
			processTimables();
			
			if(currentDoor.getDoorStatus() == 0)	{
				loadNextLevel = true;
				System.out.println("A carregar o próximo nível!");
			}
	
			if(princesa != null && princesa.getIsSaved() == true){
				ImageGUI.getInstance().setStatusMessage("Princesa foi salva! BOM TRABALHO!!");
				return;
			}
		
			lastTickProcessedRoom++;
		}
	
		public void moveManel(Direction d) {
			if(canMove(manel,d)){
				manel.move(d);
			}
			
		}
	
		public void moveMovables(){ 
			Iterator<Movable> iterator = objetosMoveis.iterator();
		
			while (iterator.hasNext()) {
				Movable i = iterator.next();
				if(i.getName() == "Banana"){
					Point2D nextPoint = i.getPosition().plus(Direction.DOWN.asVector());
						if(insideMap(nextPoint)){
							i.move(Direction.DOWN);
						}else{
							deleteObject(i.getPosition());
							iterator.remove();
							objetosInteractable.remove(i);
						}
				} else {
					try {
						i.move(randomPossibleDirection(i));
					} catch (Exception IndexOutOfBoundsException) {
						// caso nao haja direção pra ir
					}
				
				}
			}
		}
	
		public void processTimables(){
			Iterator<Timable> iterator = objetosTimable.iterator();
			while(iterator.hasNext()){
				Timable i = iterator.next();
				i.processTick();

				if(i.hasChanged()&& i.getName().equals("Bomb")){
					explodeBomb(i);
					deleteObject(i.getPosition());
					iterator.remove();
					objetosInteractable.remove(i);

				}

				
			}
		}
	
		public void interact() {
			Iterator<Interactable> iterator = objetosInteractable.iterator();
		
			while (iterator.hasNext()) {
				Interactable i = iterator.next();
				if (canInteract(i)) {
					i.interact(manel);
					System.out.println("A interagir com objeto: " + i.getName());
					if(i.isDeletable())	{  
						deleteObject(i.getPosition());
						iterator.remove();
						objetosMoveis.remove(i);	
					}
					
				}
				
				
				
				
				
			}
	
			
		}

		
		
		public void attack(){
			if(randomDonkeyIndex()!= -1){
				Banana banana = new Banana(objetosInteractable.get(randomDonkeyIndex()).getPosition());
				ImageGUI.getInstance().addImage(banana);
				objetosMoveis.add(banana);
				objetosInteractable.add(banana);
			}
			
			
			
		}
	
		public int randomDonkeyIndex(){//dá um indice aleatório de um Donkey Kong na lista dos Interactable, retorna -1 se não houver gorilas no mapa
			List<Integer> indexes = new ArrayList<>();
			for(int i = 0; i != objetosInteractable.size(); i++){
				if(objetosInteractable.get(i).getName()=="DonkeyKong"){
					indexes.add(i);
				}
			}
			if(indexes.size()==0){
				return -1;
			}
			return indexes.get((int)(Math.random()*indexes.size()));
	
		}
	
		public boolean canInteract(Interactable i) {
			
			Point2D objectPosition = i.getPosition(); 
			
			if (i.getName()=="Trap"|| i.getName()=="Wall") {
				Point2D manelGround = manel.getPosition().plus(new Vector2D(0, 1));
	
				if (manelGround.equals(objectPosition)) {
					System.out.println("Armadilha ativada!");
					return true;
				}
			}
			if (i.getName()=="DonkeyKong"&& arroundManel(i)) {
				System.out.println("Atacando macaco");
				return true;
				
			}
			if (i.getName().equals("Bomb")&& manel.hasBomb()&&i.getPosition().equals(manel.getPosition())) {
				System.out.println("O manel já tem bomba");
				return false;
				
			}
			
			

			
	
			return manel.getPosition().equals(objectPosition);
		}
	
		public boolean arroundManel(Interactable i){
			Point2D down = manel.getPosition().plus(new Vector2D(0, 1));
			Point2D up = manel.getPosition().plus(new Vector2D(0, -1));
			Point2D right = manel.getPosition().plus(new Vector2D(1, 0));
			Point2D left = manel.getPosition().plus(new Vector2D(-1, 0));
			
			if(down.equals(i.getPosition())|| up.equals(i.getPosition())||right.equals(i.getPosition())||left.equals(i.getPosition())){
				return true;
			}
			return false;
		}
	
		public void deleteObject(Point2D posAt) {
			try {
				ImageTile tile = findObjectByPoint(posAt);
				ImageGUI.getInstance().removeImage(tile); 
			} catch (IllegalArgumentException e) {
				
			}
			
			
			
		}
		
		public Direction randomPossibleDirection(Movable ob){
			List<Direction> possDirections = new ArrayList<>();
			for(Direction d : Direction.values()){
				if(canMove(ob, d)){
					possDirections.add(d);
				}
			}
			
			return possDirections.get((int)(Math.random()*possDirections.size()));
		}
	
		public void readRoomFile(String f) {	
			String levelPath = "rooms/" + f;
			File file = new File(levelPath);		
			System.out.println("A ler ficheiro: " + levelPath);
			
			try (Scanner sc = new Scanner(file)) { 
				if(levelNum != 2){
					level = sc.nextLine(); 
				}
	
				String letras = "";
	
				while (sc.hasNextLine()) {
					letras += sc.nextLine();
				}
				
				matrixRoom = stringToMatrix(letras); 
				loadMap(); 
				sc.close();
			} catch (FileNotFoundException e) {
				System.err.println("Ficheiro não encontrado! " + levelPath);
			}
			
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
			
			if(insideMap(nextPosition) && !isBlock(nextPosition) && !isActivatedBomb(nextPosition) &&!isDonkeyKong(nextPosition) && !manel.getPosition().equals(nextPosition)){
				if(ob.getName().equals("DonkeyKong")&&isDoor(nextPosition)){
					return false;
				}
	
				if(d.name()=="UP" && !isStairs(ob.getPosition())){//Caso o objeto nao esteja numa escada não pode subir de posição ou seja "voar"!
					return false;
				}
				
				return true;
			}
			
			
			return false;
		}

		public boolean isActivatedBomb(Point2D p){// retorna true se naquela posição tem uma bomba ativada
			for(Timable t : objetosTimable){
				
				if(t.getName().equals("Bomb")&& t.getPosition()==p){
					System.out.println(("é bomba aseguir"));
					return true;
				}
			}
			return false;
		}
	
	
	
		public boolean isBlock(Point2D p){
			char c = matrixRoom[p.getY()][p.getX()];
			if(c== 'W'||c== 't'|c== 'h'|| c=='P'){
				
				return true;
			}
			return false;
		}
	
		public boolean isDonkeyKong(Point2D p){//método para saber se naquele ponto está um gorila
			for(Movable i : objetosMoveis){
				if(i.getName()=="DonkeyKong"){
					if(i.getPosition().equals(p)){
						return true;
					}
				}
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
	
		public boolean isTrap(Point2D p){
			char c = matrixRoom[p.getY()][p.getX()];
			if(c == 't'){
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
	
		public void manelFall(){//Quando nao tem nada por baixo o Manel cai
			Point2D manelGround = manel.getPosition().plus(new Vector2D(0, 1));
			if(!isBlock(manelGround) && !isStairs(manelGround)){
				moveManel(Direction.DOWN);
			}
		}

		public void manelDropBomb(){
			if(manel.hasBomb()){
				Bomb b = manel.getManelBomb();
				manel.dropBomb();
				System.out.println("Bomba a contar em "+ b.getPosition().toString());
				ImageGUI.getInstance().addImage(b);
				objetosTimable.add(b); 
				objetosInteractable.add(b); 
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
			}
			return matr;
		}
	
	
		public static void loadMap(){
			GameObject fixo;
			Interactable interactable;
	
			for(int i = 0; i != 10;i++){
				for(int j = 0; j != 10; j++){
					switch (matrixRoom[i][j]) {
						case 'W':			
							fixo = new Wall(new Point2D(j,i));
							objetosFixos.add(fixo);
							ImageGUI.getInstance().addImage(fixo);
							break;
						
						case 'S':
							fixo = new Stairs(new Point2D(j,i));
							objetosFixos.add(fixo);
							ImageGUI.getInstance().addImage(fixo);
							break;
							
						case 't':
							Trap trap = new Trap(new Point2D(j,i));
							objetosInteractable.add(trap);
							objetosFixos.add(trap);
							ImageGUI.getInstance().addImage(trap);
							break;

						case 'h':
							Trap trapH = new Trap(new Point2D(j,i));
							trapH.setHidden();
							objetosInteractable.add(trapH);
							objetosFixos.add(trapH);
							ImageGUI.getInstance().addImage(trapH);
							break;
							
						case '0':
							Door door = new Door(new Point2D(j,i));
							ImageGUI.getInstance().addImage(door);
							objetosInteractable.add(door);
							currentDoor=door;
							break;
						
						
						case 'H':
							heroStartingPosition= new Point2D(j,i);
							if(levelNum == 0){ 
								manel = new Manel(heroStartingPosition);
								ImageGUI.getInstance().addImage(manel);
							}
							break;
							
						case 'G':
							DonkeyKong donkeyKong = new DonkeyKong(new Point2D(j,i));
							ImageGUI.getInstance().addImage(donkeyKong);
							objetosMoveis.add(donkeyKong);
							objetosInteractable.add(donkeyKong);
							break;
					
						case 's':
							interactable = new Sword(new Point2D(j,i));
							ImageGUI.getInstance().addImage(interactable);
							objetosInteractable.add(interactable);
							break; 
						 
						case 'b':
							Bife bife = new Bife(new Point2D(j,i));	
							ImageGUI.getInstance().addImage(bife); 
							objetosInteractable.add(bife);  
							objetosTimable.add(bife);  
							break;

						case 'B':
							Bomb bomb = new Bomb(new Point2D(j,i));	
							ImageGUI.getInstance().addImage(bomb); 
							objetosInteractable.add(bomb);  
							objetosTimable.add(bomb);  
							break;
	
						case 'P':
							Princesa newPrincesa = new Princesa(new Point2D (j,i));
							princesa = newPrincesa;
							ImageGUI.getInstance().addImage(newPrincesa);
							objetosInteractable.add(newPrincesa);
						break;

					default:
						break;
				}
			}
		}

		
	}
	
	public ImageTile findObjectByPoint(Point2D p) {
		for (ImageTile i : objetosInteractable) {
			if (i.getPosition().equals(p)) {
				return i;
			}
		}
		for (ImageTile i : objetosTimable) {
			if (i.getPosition().equals(p)) {
				return i;
			}
		}
		System.err.println("Objeto não encontrado na posição " + p);
		return null;
	}

	public void manelStatus() {	
		if(manel.getHealth() < 0){
			respawnManel(heroStartingPosition,true);
		}
		if(manel.hasBomb()){
			ImageGUI.getInstance().setStatusMessage("Vidas: " + manel.getLives() + "  Saúde: " + manel.getHealth() +  "  Dano: " + manel.getDamage()+ "  💣");
		}else{
			ImageGUI.getInstance().setStatusMessage("Vidas: " + manel.getLives() + "  Saúde: " + manel.getHealth() +  "  Dano: " + manel.getDamage());
		}
		
	}

	public boolean getLoadNextLevel(){
		return loadNextLevel;
	}

	public void clearPreviousLevel(){	
		clearMovables();
		clearInteractables();
		clearMap();
	}

	public void clearMovables(){
		Iterator<Movable> iterator = objetosMoveis.iterator();

		while(iterator.hasNext()) {
			Movable i = iterator.next();
			ImageGUI.getInstance().removeImage(i);
		}
	}

	public void clearInteractables(){
		Iterator<Interactable> iterator = objetosInteractable.iterator();

		while(iterator.hasNext()) {
			Interactable i = iterator.next();
			ImageGUI.getInstance().removeImage(i);
		}
	}

	public void clearMap(){
		Iterator<GameObject> iterator = objetosFixos.iterator();

		while(iterator.hasNext()){
			GameObject i = iterator.next();
			ImageGUI.getInstance().removeImage(i);
		}
	}

	public void respawnManel(Point2D startingPosition, boolean killed){
		Manel deadManel = manel;
		System.out.println("A apagar Manel");
		ImageGUI.getInstance().removeImage(deadManel);
		int lives = 0;
		int damageLevel = 0;
		int health = 0;

		if(killed){
			lives = deadManel.getLives() - 1;
			health = 100;
			damageLevel = 25;
			

		} else {
			lives = deadManel.getLives();
			damageLevel = deadManel.getDamage();
			health = deadManel.getHealth();
			
		}
		
		manel = new Manel(heroStartingPosition);
		manel.setLives(lives);
		manel.setDamageLevel(damageLevel);
		manel.setHealth(health);
		

		ImageGUI.getInstance().addImage(manel);
	}

	





	public void explodeBomb(Timable b){
		Point2D p = b.getPosition();
		Point2D down = p.plus(new Vector2D(0, 1));
		Point2D up = p.plus(new Vector2D(0, -1));
		Point2D right = p.plus(new Vector2D(1, 0));
		Point2D left = p.plus(new Vector2D(-1, 0));
		deleteObjectsFromList(up);
		deleteObjectsFromList(down);
		deleteObjectsFromList(right);
		deleteObjectsFromList(left);
		deleteObjectsFromList(p);
			
		
		
	};


	

	public void deleteObjectsFromList(Point2D p){//apaga exceto escadas e blocos fixos
		if(insideMap(p) && !isStairs(p)&& !isBlock(p) ){
			deleteObject(p);

		}

	}

	public void deleteFromListInteractable(Point2D p){
		int i = 0;
		for(Interactable it : objetosInteractable){
			if(it.getPosition().equals(p)){
				objetosInteractable.remove(i);
				
			}
			i++;
		}
	}

	public void deleteFromListMovable(Point2D p){
		int i = 0;
		for(Movable it : objetosMoveis){
			if(it.getPosition().equals(p)){
				objetosMoveis.remove(i);
				
			}
			i++;
		}
	}
}