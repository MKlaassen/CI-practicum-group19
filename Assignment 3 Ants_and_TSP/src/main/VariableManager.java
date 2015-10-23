package main;

import Maze.Maze;

public class VariableManager {
	
	private static String mazeDifficulty;
	private static int amountOfAnts;
	private static int maxIterations;
	private static int amountOfWinners;
	private static boolean limitIterations;
	private static int evaporationconstant;
	private static float alpha;
	private static float beta;
	private static Maze maze;
	private static int Q;
	
	public static String getMazeDifficulty() {
		return mazeDifficulty;
	}
	public static void setMazeDifficulty(String mazeDifficulty) {
		VariableManager.mazeDifficulty = mazeDifficulty;
	}
	public static int getAmountOfAnts() {
		return amountOfAnts;
	}
	public static void setAmountOfAnts(int amountOfAnts) {
		VariableManager.amountOfAnts = amountOfAnts;
	}
	public static int getEvaporationconstant() {
		return evaporationconstant;
	}
	public static void setEvaporationconstant(int evaporationconstant) {
		VariableManager.evaporationconstant = evaporationconstant;
	}
	public static float getAlpha() {
		return alpha;
	}
	public static void setAlpha(float alpha) {
		VariableManager.alpha = alpha;
	}
	public static float getBeta() {
		return beta;
	}
	public static void setBeta(float beta) {
		VariableManager.beta = beta;
	}
	public static Maze getMaze() {
		return maze;
	}
	public static void setMaze(Maze maze) {
		VariableManager.maze = maze;
	}
	public static int getQ() {
		return Q;
	}
	public static void setQ(int q) {
		Q = q;
	}
	public static int getMaxIterations() {
		return maxIterations;
	}
	public static void setMaxIterations(int maxIterations) {
		VariableManager.maxIterations = maxIterations;
	}
	public static boolean isLimitIterations() {
		return limitIterations;
	}
	public static void setLimitIterations(boolean limitIterations) {
		VariableManager.limitIterations = limitIterations;
	}
	public static int getAmountOfWinners() {
		return amountOfWinners;
	}
	public static void setAmountOfWinners(int amountOfWinners) {
		VariableManager.amountOfWinners = amountOfWinners;
	}


}
