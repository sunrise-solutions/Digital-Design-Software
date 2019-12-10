package Models;
import java.util.ArrayList;

public class Obfuscator {
	
	String code;
	ArrayList<Item> identificators;
	ArrayList<Item> obfuscatedIdentificators;
	
	public Obfuscator(String sourceCode, ArrayList<Item> sourceIdentificators) {
		code = sourceCode;
		identificators = sourceIdentificators;
    }
	
	// TODO: ADD SPACES!
	private void AddSpacesToCode() {
		
		ArrayList<String> operators;
		
	}
	
	public String ObfuscateCode() {
		
		Item newIdentificator;
		
		for (Item identificator : identificators) {
			newIdentificator = GenerateObfuscatedIdentificator(identificator);
			
			obfuscatedIdentificators.add(newIdentificator);
			
			// Not a comment words! (& think about spaces)
			while (code.contains(" " + identificator.Name + " ")) {
				code.replace(" " + identificator.Name + " ", " " + newIdentificator + " ");
			}
		}
		return code;
	}
	
	private Item GenerateObfuscatedIdentificator(Item identificator) {
		Item i = new Item("Ho-Ho-Ho!", identificator.Type);
		return i;
	}
}

