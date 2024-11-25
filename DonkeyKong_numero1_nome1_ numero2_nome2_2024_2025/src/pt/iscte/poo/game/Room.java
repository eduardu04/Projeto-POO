package pt.iscte.poo.game;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.colorchooser.ColorChooserComponentFactory;


import objects.*;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Direction;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import pt.iscte.poo.gui.ImageTile;
public class Room {
	
	private Point2D heroStartingPosition = new Point2D(1, 0);
	private Manel manel;
	private String level;
	private static char[][] matrixRoom;
	
	public Room() {
		manel = new Manel(heroStartingPosition);
		ImageGUI.getInstance().addImage(manel);
		addFloor();


	}

	public void moveManel(Direction d) {
		if(canMove(d)){
			manel.move(d);
		}
		
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

	public boolean canMove(Direction d){
		Point2D nextPosition = manel.getPosition().plus(d.asVector());
		if(nextPosition.getX()==-1||nextPosition.getX()==10||nextPosition.getY()==-1||nextPosition.getY()==10){
			return false;
		}
		if(isBlock(nextPosition)){
			return false;
		}
		return true;
	}

	public boolean isBlock(Point2D p){
		char c = matrixRoom[p.getY()][p.getX()];
		if(c== 'W'||c== 't'){
			return true;
		}
		return false;
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
		ImageTile imagem;
		for(int i = 0; i != 10;i++){
			for(int j = 0; j != 10; j++){
				switch (matrixRoom[i][j]) {
					case 'W':
						imagem = new Wall(new Point2D(j,i));
						ImageGUI.getInstance().addImage(imagem);
						break;
					case 'S':
						imagem = new Stairs(new Point2D(j,i));
						ImageGUI.getInstance().addImage(imagem);
						break;
					case 't':
						imagem = new Trap(new Point2D(j,i));
						ImageGUI.getInstance().addImage(imagem);
						break;
					case '0':
						imagem = new Door(new Point2D(j,i));
						ImageGUI.getInstance().addImage(imagem);
						break;
					case 'G':
						imagem = new DonkeyKong(new Point2D(j,i));
						ImageGUI.getInstance().addImage(imagem);
						break;
					case 's':
						imagem = new Sword(new Point2D(j,i));
						ImageGUI.getInstance().addImage(imagem);
						break;
					default:
						break;
				}
			}
		}
	}

	

	
}