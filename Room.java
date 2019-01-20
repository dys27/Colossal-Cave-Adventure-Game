package AdventureGame;

//class Room

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Enumeration;
import java.util.Vector;

public class Room implements CaveSite {
	
	private String description;
	
	private CaveSite[] side = new CaveSite[6];
	
	private ArrayList<Item> contents = new ArrayList<Item>();
	
	Room(){
		side[0] = new Wall();
		side[1] = new Wall();
		side[2] = new Wall();
		side[3] = new Wall();
		side[4] = new Wall();
		side[5] = new Wall();
	}
	
	public void setDesc(String d) {
		description = d;
	}
	
	public void setSide(int direction, CaveSite m) {
		side[direction] = m;
	}
	
	public void addItem(Item theItem) {
		contents.add(theItem);
	}
	
	public void removeItem(Item theItem) {
		contents.remove(theItem);
	}
	
	public boolean roomEmpty() {
		return contents.isEmpty();
	}
	
	public Item[] getRoomContents() {
		Item[] contentsArray = new Item[contents.size()];
		contentsArray = contents.toArray(contentsArray);
		return contentsArray;
	}
	
	public void enter(Player p) {
		p.setLoc(this);
	}
	
	public void exit(int direction, Player p) {
		side[direction].enter(p);
	}
	
	public String getDesc() {
		ListIterator<Item> roomContents = contents.listIterator();
		String contentString = "";
		while(roomContents.hasNext())
			contentString = contentString + (roomContents.next()).getDesc() + " ";
		
		return description + '\n' + '\n' +
				"Room Contents: " + contentString + '\n';
	}
	
}