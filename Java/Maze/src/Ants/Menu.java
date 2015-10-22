package Ants;

import java.util.Scanner;

public class Menu {
	
	public static Scanner sc = new Scanner(System.in);

	public static String chooseDifficulty()
	{
		String mazeDifficulty;
		mainMenuLoop:
			while(true){
				System.out.println("Select maze difficulty:");
				System.out.println("1) Easy");
				System.out.println("2) Medium");
				System.out.println("3) Hard");
				System.out.println("4) INSANE");
				int choice = sc.nextInt();
				switch (choice){
				case 1:
					mazeDifficulty = "easy";
					break mainMenuLoop;
				case 2:
					mazeDifficulty = "medium";
					break mainMenuLoop;
				case 3:
					mazeDifficulty = "hard";
					break mainMenuLoop;
				case 4:
					mazeDifficulty = "insane";
					break mainMenuLoop;
				default:
					System.out.println("No valid number entered");
					break;
				}
			}
		
		
		return mazeDifficulty;
	}

	public static int chooseReleaseMethod()
	{
		int releaseMethod;
		
		modeSelectLoop:
			while(true)
			{
				System.out.println("Select mode:");
				System.out.println("1) One by one");
				System.out.println("2) Concurrent + No resetting of Ants");
				System.out.println("3) Concurrent + Reset all Ants, if one Ant finds destination");
				int choice = sc.nextInt();
				switch (choice){
				case 1:
					releaseMethod = 1;
					break modeSelectLoop;
				case 2:
					releaseMethod = 2;
					break modeSelectLoop;
				case 3:
					releaseMethod = 3;
					break modeSelectLoop;
				default:
					System.out.println("No valid number entered");
					break;
				}
			}
		return releaseMethod;
	}
}