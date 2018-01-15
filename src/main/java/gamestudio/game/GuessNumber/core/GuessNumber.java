package gamestudio.game.GuessNumber.core;

import java.util.Random;
import java.util.Scanner;

import gamestudio.consoleUI.ConsoleGameUI;
import gamestudio.entity.Score;

import java.lang.System;

public class GuessNumber implements ConsoleGameUI {

	@Override
	public void run() {
		
		long startTime = System.currentTimeMillis();
		
		System.out.println("Zadaj maxim�lne ��slo: ");
		Scanner inputMaxNum = new Scanner(System.in);

		int maxnum;
		maxnum = inputMaxNum.nextInt();

		Random rand = new Random();
		int number = rand.nextInt(maxnum) + 1;

		int tries = 0;

		boolean win = false;

		do {

			System.out.println("H�daj ��slo medzi 1 a " + maxnum + ": ");
			Scanner input = new Scanner(System.in);
			int guess = Integer.parseInt(input.nextLine());
			tries++;
			if ("x".equals(guess)) {
				System.exit(0);
			} else if (guess == number) {

				win = true;
			} else if (guess < number) {
				System.out.println("H�daj v��ie ��slo");
			} else if (guess > number) {
				System.out.println("H�daj men�ie ��slo");
			}
		} while (win == false);
		System.out.println("Vyhral si!");
		long stopTime = System.currentTimeMillis();
		long totalTime =( stopTime - startTime )/1000;
		Score score = new Score();
		score.setUsername(System.getProperty("user.name"));
		score.setGame(getName());
		score.setValue((int)totalTime);
		System.out.println("Tvoj po�et pokusov bol : " + tries);
		System.out.println("Tvoje skore je :" + totalTime);

	}

	@Override
	public String getName() {
		return "Guess Number";
	}
}
