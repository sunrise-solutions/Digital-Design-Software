package Models;

public class Item {
	public String Name;
	public String Type;
	
	public Item(String name, String type) {
		this.Name = name;
		this.Type = type;
	}
	
	public String getName() {
		return Name + " (" + Type + ")";
	}
}
