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
    	for (String row: content.split("\n")) {
    		stopIndex = row.length();
    		for (int i = 0; i < row.length(); i = i + 1) {
    			if ((i != row.length() - 1) && (row.charAt(i) == '-') && (row.charAt(i + 1) == '-')) {
    				stopIndex = i;
    				break;
    			}
    		}
    		code += row.substring(0,  stopIndex) + "\r\n";
    	}
    	
    	return code;
    }

    public void ParseFile(Entity e) {
    	entity = e;
        reader.fileToList();
        ArrayList<Item> items = reader.getEntityItems(entity);
        entity.AddItems(items);
    } 
}
