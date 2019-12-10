package Models;

import java.util.ArrayList;


public class FileParser {
	private EntityReader reader;
	public Entity entity = new Entity();

    public FileParser(String content) {
    	String code = deleteComments(content);
        reader = new EntityReader(code);
    }
    
    private String deleteComments(String content) {
    	int stopIndex = 0;
    	String code = "";
    	String temp = "";
    	for (String row: content.split("\n")) {
    		stopIndex = row.length();
    		for (int i = 0; i < row.length(); i = i + 1) {
    			if ((i != row.length() - 1) && (row.charAt(i) == '-') && (row.charAt(i + 1) == '-')) {
    				stopIndex = i;
    				break;
    			}
    		}
    		temp = row.substring(0,  stopIndex).trim();
    		temp = temp.replace(":", " : ");
    		temp = temp.replace(",", " , ");
    		code += (!temp.equals("")) ? (temp + "\r\n") : "";
    	}
    	
    	return code;
    }

    public void ParseFile(Entity e) {
    	entity = e;
        reader.fileToList();
        ArrayList<Item> items = reader.getEntityItems(entity, "entity", "port", "end");
        items.addAll(reader.getEntityItems(entity, "architecture", "signal", "begin"));
        items.addAll(reader.getEntityItems(entity, "architecture", "variable", "begin"));
        items.addAll(reader.getEntityItems(entity, "architecture", "constant", "begin"));
        items.addAll(reader.getEntityItems(entity, "entity", "generic", ")"));
        items.addAll(reader.getProcesses());
        items.addAll(reader.getProcessItems(entity, "process", "variable", "begin"));
        entity.AddItems(items);
    } 
}
