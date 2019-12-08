package Models;

import java.util.ArrayList;


public class FileParser {
	private EntityReader reader;
	public Entity entity = new Entity();

    public FileParser(String content)
    {
        reader = new EntityReader(content);
    }

    public void ParseFile(Entity e)
    {
    	entity = e;
        reader.fileToList();
        ArrayList<Item> items = reader.getEntityItems(entity);
        entity.AddItems(items);
    } 
}
