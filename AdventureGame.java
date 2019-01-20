package AdventureGame;

/** Modified from James M. Bieman
	Adventure game program code
		
	The AdventureGame is a Java implementation of the old text based
	adventure Game from long ago. The design was adapted from
	one in Gamma, Helm, Johnson, Vlissides (The gang of Four),
	"Design Patterns: Elements of Reusable Object-Oriented Software",
	Addison-Wesley, 1997.
	
	To really be consistent with the old game we would need a 
	much larger cave system with a hundred or so rooms, and a 
	more "understanding" user interface.
	
	The old game just put you near the cave, displayed the "view"
	as text, and offered no instructions. If you gave a command that
	it understood, you could proceed. If your command could not
	be interpreted, nothing would happen. Rooms were never identified
	precisely; your only clues came from the descriptions. You would
	have to remember or create your own map of the cave system to
	find your way around. Sometimes you could not return exactly
	the way you came. An exit to the east may not enter the west
	side of the "adjacent room"; the passage might curve.
	
	Perhaps, this implementation can evolve to be closer to
	the original game, or even go beyond it.
**/

import java.io.*;

public class AdventureGame {
	private Adventure theCave;
	private Player thePlayer;
	
/** Our system-wide internal representation of directions
 	is integers. Here, we convert input string directions
 	into integers. Internally, we use integers 0-9 as 
 	directions throughout the program.
 **/
 
private int convertDirection(String input) {
	char d = input.charAt(0);
	int theDirection = 9999;
	switch(d) {
		case 'n':case 'N': theDirection = 0;break;
		case 's':case 'S': theDirection = 1;break;
		case 'e':case 'E': theDirection = 2;break;
		case 'w':case 'W': theDirection = 3;break;
		case 'u':case 'U': theDirection = 4;break;
		case 'd':case 'D': theDirection = 5;break;
	}
	return theDirection;
}	
/** choosePickupItem determines the specific item
 	that a player wants to pick up. **/
 private Item choosePickupItem(Player p, BufferedReader keyB)
 				throws IOException{
	 Item[] contentsArray = (p.getLoc()).getRoomContents();
	 String inputString = "prepare";
	 int theChoice = -1;
	 
	 do {
		 System.out.println("The room has:");
		 for (int i=0; i <contentsArray.length; i++)
		 System.out.println((i+1) + ": "
				 + contentsArray[i].getDesc());
		 System.out.print("Enter the number of the item to grab: ");
		 inputString = keyB.readLine();
		 System.out.println('\n');
		 if (inputString.equals("")) inputString = " ";
		 try {
			 theChoice = Integer.parseInt(inputString);
		 } catch (NumberFormatException e) {
			 System.out.println("Invalid input.");
			 theChoice = -1;
		 }
		 if (theChoice < 0 || theChoice > contentsArray.length)
			 System.out.print("That item is not in the room.");
	 } while (theChoice < 0 || theChoice > contentsArray.length);
	 
	 return contentsArray[theChoice-1];
	 
 }
 
 /** chooseDropItem determines the specific item
  	 that a player wants to drop **/
 private int chooseDropItem (Player p, BufferedReader keyB)
 	throws IOException{
	 String inputString = "prepare";
	 int theChoice = -1;
	 do {
		 System.out.println("You are carrying: " +
				 p.showMyThings() + '\n');
		 System.out.print("Enter the number of the item to drop: ");
		 inputString = keyB.readLine();
		 try {theChoice = Integer.parseInt(inputString);}
	catch (NumberFormatException e) {
		System.out.println("Invalid input.");
		theChoice = -1;
		 }
		 if (theChoice < 0 || theChoice > p.numItemsCarried())
			 System.out.print("Invalid choice.");
	 } while (theChoice < 0 || theChoice > p.numItemsCarried());
	return theChoice;
 }
 
 public void startQuest() throws IOException{
	 Player thePlayer = new Player();
	 Adventure theCave = new Adventure();
	 Room startRm = theCave.createAdventure();
	 thePlayer.setRoom(startRm);
	 
	 /** Create the keyboard to control the game; we only need one */
	 BufferedReader keyboard 
	   = new BufferedReader(new InputStreamReader (System.in));
	 String inputString = "prepare";
	 
	 /* The main query user, get command, interpret, execute cycle. */
	 while (inputString.charAt(0)!='q') {
		 System.out.println(thePlayer.look());
		 System.out.println("You are carrying: " +
				 thePlayer.showMyThings() + '\n');
		 /* get next move */
	int direction = 9;
	
		System.out.println("Which way (n,s,e,w,u,d)," +
				" or grab(g) or toss(t) an item," +
				" or quit(q)?" + '\n');
		inputString = keyboard.readLine();
	System.out.println('\n');
	if(inputString.equals("")) inputString = " ";
	char key = inputString.charAt(0);
	switch(key) {
		//Go
		case 'n': case 'N': case 's': case 'S':
		case 'e': case 'E': case 'w': case 'W':
		case 'u': case 'U': case 'd': case 'D':
			direction = convertDirection(inputString);
		 thePlayer.go(direction);
		 	break;
		 //Grab item
		case 'g': case 'G':
		  if (thePlayer.handsFull())
		System.out.println("Your hands are full.");
		  else if ((thePlayer.getLoc()).roomEmpty())
			  System.out.println("The room is empty.");
		  else {
			  Item itemToGrab = 
			  choosePickupItem(thePlayer,keyboard);
			  thePlayer.pickUp(itemToGrab);
			  (thePlayer.getLoc()).removeItem(itemToGrab);
		  }
		break;
		//Drop item
		case 't': case 'T':
		if (thePlayer.handsEmpty())
		System.out.println("You have nothing to drop.");
		else {
			int itemToToss = 
				chooseDropItem(thePlayer,keyboard);
				  thePlayer.drop(itemToToss);
		}
		}
	 }
 }
 
public static void main(String args[])
throws IOException{
System.out.println("Welcome to the Collosal Cave Adventure Game");
AdventureGame theGame = new AdventureGame();
theGame.startQuest();
}
}