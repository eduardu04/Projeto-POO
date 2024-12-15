package pt.iscte.poo.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.PrintWriter;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Score;

public class GameEngine implements Observer {

	private int levelNum = 0;
	private Room currentRoom = new Room(levelNum);
	private static int lastTickProcessed = 0;
	private Score score;
	private List<Score> scores = new ArrayList<>();
	private boolean savedPrincess = false;

	public GameEngine() {
		ImageGUI.getInstance().update();
	}

	@Override
	public void update(Observed source) {

		if (ImageGUI.getInstance().wasKeyPressed()) {
			int k = ImageGUI.getInstance().keyPressed();
			// System.out.println("Keypressed " + k);
			if (Direction.isDirection(k)) {
				// System.out.println("Direction! ");
				currentRoom.moveManel(Direction.directionFor(k));
			}
			if (k == 66) {
				currentRoom.manelDropBomb();
			}
		}
		int t = ImageGUI.getInstance().getTicks();
		while (lastTickProcessed < t) {
			processTick();
		}

		currentRoom.processRoom();

		if (currentRoom.restartGame()) {
			levelNum = 0;
			currentRoom = new Room(levelNum);
			lastTickProcessed = 0;

			ImageGUI.getInstance().setStatusMessage("Jogo reiniciado!");
		}

		if (savedPrincess == false) {
			ImageGUI.getInstance().update();
		}

		if (currentRoom.getLoadNextLevel()) {
			if (levelNum == 2) {
				if (currentRoom.getPrincesa().isSaved()) {// A princesa foi salva
					savedPrincess = true;
					score = new Score(ImageGUI.getInstance().getTicks(), LocalDateTime.now());
					System.out.println("tou aquii");
					updateScores();
					System.out.println("O Primeiro da lista é " + scores.get(0).getScoreTime());
					ImageGUI.getInstance().setStatusMessage("Princesa foi salva! BOM TRABALHO!!");
					ImageGUI.getInstance().dispose();

				}
				return;
			}
			levelNum++;
			currentRoom = new Room(levelNum);
		}

	}

	private void processTick() {
		System.out.println("Tic Tac : " + lastTickProcessed);
		lastTickProcessed++;
	}

	private void updateScores() {
		loadScores();
		scores.add(score);
		scores.sort((a, b) -> a.getScoreTime() - b.getScoreTime());
		List<Score> top10 = scores;
		if (scores.size() >= 10) {
			top10 = scores.subList(0, 9);
		}
		try {

			PrintWriter writer = new PrintWriter(new File("scores/scores.txt")); // 'true' para acrescentar ao ficheiro

			for (Score s : top10) {
				writer.println(s.toString());
			}

			writer.close();

		} catch (FileNotFoundException e) {

			System.out.println("Erro ao escrever no ficheiro scores.txt");
		}

	}

	private void loadScores() {
		String scorePath = "scores/scores.txt";
		File file = new File(scorePath);

		try (Scanner sc = new Scanner(file)) {
			while (sc.hasNextLine()) {
				Score scoreString = Score.readScoreString(sc.nextLine());
				scores.add(scoreString);
			}

			sc.close();
		} catch (FileNotFoundException e) {
			System.err.println("Ficheiro score.txt não encontrado! ");
		}
	}

	public Room getCurrentRoom() {
		return currentRoom;
	}

	public boolean princessSaved() {
		return savedPrincess;
	}

	public static int getLastTickProcessed() {
		return lastTickProcessed;
	}

}