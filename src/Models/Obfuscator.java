package Models;
import java.util.ArrayList;

public class Obfuscator {
	
	private String code;
	private ArrayList<Item> identificators;
	private ArrayList<Item> obfuscatedIdentificators;
	
	public Obfuscator(String sourceCode, ArrayList<Item> sourceIdentificators) {
		code = sourceCode;
		identificators = sourceIdentificators;
		obfuscatedIdentificators = new ArrayList<Item>(); 
    }
	
	public ArrayList<Item> GetObfuscatedIdentificators() {
		return obfuscatedIdentificators;
	}
	
	private String AddSpacesToCode(String code) {
		String[] operators = {"(", ")", ":", ",", ";", "*", "/", "+", "-", "&", "=", "<", ">", "'", "\"", "."};
		String strToReplace = "";
		for (String o : operators) {
			strToReplace = " " + o + " ";
			code = code.replace(o, strToReplace);
		}
		return code;
	}
    
    private String DeleteUncompiledSpaces(String code) {
    	String[] compoundOperatorsForSearch = {"<  =", ":  =", "/  =", ">  =", "=  >", "*  *", " ' ", " \" ", " . ", " ;", "'u'", "'x'", "'z'", "'w'", "'l'", "'h'", "-  -"};
		String[] compoundOperatorsForReplace = {"<=", ":=", "/=", ">=", "=>", "**", "'", "\"", ".", ";", "'U'", "'X'", "'Z'", "'W'", "'L'", "'H'", "--"};
		String strToReplace = "";
    	for (int i = 0; i < compoundOperatorsForSearch.length; i++) {
    		strToReplace = compoundOperatorsForReplace[i];
			code = code.replace(compoundOperatorsForSearch[i], strToReplace);
		}
		return code;  	
    }
	
	public String ObfuscateCode() {
		code = AddSpacesToCode(code);
        for (Item i : identificators) {
        	Item newIdentificator = GenerateObfuscatedIdentificator(i);
        	obfuscatedIdentificators.add(newIdentificator);
        	code = code.replace(" " + i.Name + " ", " " + newIdentificator.Name + " ");
        	code = code.replace("\t" + i.Name + " ", "\t" + newIdentificator.Name + " ");
        }    
        code = DeleteUncompiledSpaces(code);
		return code;
	}

	private Item GenerateObfuscatedIdentificator(Item identificator) {
		Item i = new Item(identificator.Name + "TopSecret", identificator.Type);
		return i;
	}
}