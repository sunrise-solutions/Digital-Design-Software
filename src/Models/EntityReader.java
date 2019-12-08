package Models;
import java.io.*;
import java.util.ArrayList;

public class EntityReader {
	File currentfile;
    public String fileContent;
    private String space = " ";

    ArrayList<String> listContent = new ArrayList<String>();
    ArrayList<String> listEntity = new ArrayList<String>();

    ArrayList<String> contentIn = new ArrayList<String>();
    ArrayList<String> contentOut = new ArrayList<String>();


    public EntityReader(String content) {
        fileContent = content;
    }

    public void fileToList() {
        for (String entry: fileContent.split(System.lineSeparator())) {
        	if ( (!entry.equals("\r")) && (!entry.equals("")) && (!entry.equals("\n"))) {
        		for (String word: entry.split(space)) {
        			for (String thisWord: word.split("\t")) {
        				String currentWord = thisWord;
                        String temp = "";
                        if (!currentWord.equals("")) {
                            boolean flag = false;
                            int indexT = 0; 
                            for (int i = 0; i < currentWord.length(); i++) {
                                if (currentWord.charAt(i) == '\t') {
                                    indexT = i;
                                    flag = true;
                                }
                            }

                            if (flag) {
                                for (int j = indexT + 1; j < currentWord.length(); j++)
                                    temp += currentWord.charAt(j);
                                currentWord = temp;
                            }

                            listContent.add(currentWord);
                        }
        			}
        		}
        	}
        }
    }
    
    public ArrayList<Item> getProcesses() {
    	ArrayList<Item> result = new ArrayList<Item>();
    	for (int i = 2; i < listContent.size(); i = i + 1) {
    		if (listContent.get(i).contains("process") && listContent.get(i - 1).contains(":")) {
    			Item item = new Item(listContent.get(i - 2), "process");
                result.add(item);
    		}
    	}
    	
		return result;
    }

    public ArrayList<Item> getEntityItems(Entity component, String upperBound, String mark, String lowerBound) {
    	ArrayList<Item> result = new ArrayList<Item>();
    	listEntity.clear();
        int indexOfEntity = 0;
        int indexOfStart = 0;
        int indexOfEnd = 0;
        String entityName = "";
        indexOfEntity = listContent.indexOf(upperBound);
        if (indexOfEntity != 0)                           
            entityName = listContent.get(indexOfEntity + 1);
        component.Name = entityName;
        
        //indexOfEnd = listContent.indexOf("end");
        for (int i = indexOfStart + 1; i < listContent.size(); i++) {                
            if(listContent.get(i).contains(lowerBound)) {
            	indexOfEnd = i;
                break;
            }
        }
        
        for (int i = indexOfEntity + 1 ; i < indexOfEnd; i++) {                
            if(listContent.get(i).contains(mark)) {
            	indexOfStart = i;
                break;
            }
        }
        
        if (indexOfStart != 0 && indexOfEnd != 0) {
            for (int i = indexOfStart; i < indexOfEnd; i++)
                listEntity.add(listContent.get(i));
        }
        
        int indexOfType = 0;
        int currentPos = 0;
        String type = "";
        while (currentPos < listEntity.size()) {
        	type = getCurrentType(currentPos);
            if (!type.equals("")) {
            	if (type.equals(mark)) {
            		indexOfType = getIndexOfElement(listEntity, ":", currentPos);
            	} else {
            		indexOfType = getIndexOfElement(listEntity, type, currentPos);
            	}
            	
                ArrayList<String> listCurrentItems = new ArrayList<String>();
                listCurrentItems = getItems(currentPos, indexOfType);
                for (String itemName: listCurrentItems) {
                	if (!itemName.equals(type)) {
                		Item item = new Item(itemName, type);
                        result.add(item);
                	}
                }
                
                currentPos = findNextPart(indexOfType);
            }
        }
        
        return result;
    }

    public String getCurrentType(int currentPos) {
        String type = "";
        for (int i = currentPos; i < listEntity.size(); i = i + 1 ) {
        	if (listEntity.get(i).equals("in") || listEntity.get(i).equals("out") || listEntity.get(i).equals("inout")) {
        		type = listEntity.get(i);
        		break;
        	}
        	
        	if ( (i != currentPos) && listEntity.get(i - 1).contains("signal")) {
        		type = "signal";
        		break;
        	}
        	
        	if ( (i != currentPos) && listEntity.get(i - 1).contains("variable")) {
        		type = "variable";
        		break;
        	}
        	
        	if ( (i != currentPos) && listEntity.get(i - 1).contains("constant")) {
        		type = "constant";
        		break;
        	}
        	
        	if (listEntity.get(i).contains("process")) {
        		type = "process";
        		break;
        	}
        }
        
        return type;
    }
    
    public int getIndexOfElement(ArrayList<String> array, String str, int start) {
    	for (int i = start; i < array.size(); i = i + 1) {
    		if (array.get(i).equals(str)) {
    			return i;
    		}
    	}
    	
		return -1;
    }
    
    public ArrayList<String> getItems(int currentPos, int indexOfType) {
    	ArrayList<String> result = new ArrayList<String>();
        for (int i = currentPos; i < indexOfType; i++) {
            if (listEntity.get(i).contains("\r") == false && listEntity.get(i).contains("port") == false) {
                String temp = "";
                temp = listEntity.get(i);
                for (int j = 0; j < temp.length(); j = j + 1) {
                    if ((temp.charAt(j) == ':' || temp.charAt(j) == ',' || temp.charAt(j) == '(') && j == temp.length() - 1) {                            
                        String tempString = "";
                        for (int k = 0; k < j; k++)
                            tempString += temp.charAt(k);
                        temp = tempString;
                        break;
                    }
                }

                for (int j = 0; j < temp.length(); j++) {
                    if ((temp.charAt(j) == ',' || temp.charAt(j) == '(') && j != temp.length() - 1) {
                        String tempString = "";
                        for (int k = j+1; k < temp.length(); k++)
                            tempString += temp.charAt(k);
                        temp = tempString;
                        break;
                    }
                }
                
                if (!temp.equals("")) {
                    result.add(temp);
                }
            }
            else
                currentPos++;
        }
        
        return result;
    }
    
    public int findNextPart(int indexOfType) {
        int position = -1;
        for (int k = indexOfType + 1; k < listEntity.size(); k++) {
            boolean flag = false;
            if (listEntity.get(k).contains(";")) {
            	position = k;
                flag = true;
                break;
            }

            if (k == listEntity.size() - 1 && flag == false)
                break;
        }
        
        position++;
        return position;
    }
}
