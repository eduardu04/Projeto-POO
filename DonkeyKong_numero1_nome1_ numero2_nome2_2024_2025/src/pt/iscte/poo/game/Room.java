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
	
	private Point2D heroStartingPosition = new Point2D(3, 1);
	private Manel manel;
	private String level;
	
	public Room() {
		manel = new Manel(heroStartingPosition);
		ImageGUI.getInstance().addImage(manel);
		addFloor();


	}

	public void moveManel(Direction d) {
		manel.move(d);
	}

	public static Room readRoomFile(File f) {
		Room sala = new Room();
		try {
			
			Scanner sc = new Scanner(f);
			sala.level = sc.nextLine();
			String letras = "";
			while(sc.hasNext()){
				letras+=sc.nextLine();
			}
			
			for(int i = 0; i != letras.length();i++){
				ImageTile imagem;
				if(letras.charAt(i)=='W'){
					imagem = new Wall(vectorIndexToCoordenate(i));
					ImageGUI.getInstance().addImage(imagem);

				}
			}
			
			
		} catch (Exception FileNotFoundException) {
			System.err.println("Erroooo");
		}
		return sala;
		


		
	}

	

	public static Point2D vectorIndexToCoordenate(int i){
		return new Point2D(i%10,i/10);
	}

	public void addFloor(){
		for(int i = 0; i != 10; i++){
			for(int j = 0; j != 10; j++){
				ImageGUI.getInstance().addImage(new Floor(new Point2D(i,j)));
			}
		}
	}

	
}