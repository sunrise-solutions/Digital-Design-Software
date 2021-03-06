package Models;

import java.util.ArrayList;

public class Entity {
	public String Name;
	public ArrayList<Item> items = new ArrayList<Item>();
	public ArrayList<Item> obfuscatedItems = new ArrayList<Item>();

    public void AddItems(ArrayList<Item> array) {
    	for (Item item: array) {
    		items.add(item);
    	}
    }
    
    public void AddObfuscatedItems(ArrayList<Item> array) {
    	for (Item item: array) {
    		obfuscatedItems.add(item);
    	}
    }
}
