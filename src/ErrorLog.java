import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
/**
 * Trida obsahuje vsechny chybove ale i informacni vyskakovaci okna
 * @author Jokertwo
 *
 */
public class ErrorLog {

	private String toImg = "Export to img";
	private String toTxt = "Export to .txt";
	
	/**
	 * Nebylo zadano cislo
	 * 
	 * @param sValue String ktery nelze prevest na cislo
	 */
	public void notNumber(String sValue){
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("'" + sValue + "'" + " is not number.");
		alert.show();
	}
	
	/**
	 * Chybova hlaska pokud nebyla zadana zadna hodnota
	 */
	public void noInputFromTF(){
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Error");
		alert.setHeaderText("No input from field size.");
		alert.show();
	}
	
	/**
	 * informace o tom ze nelze pouzit mezeru jako symbol pro zed pri zapisovani do 
	 * textoveho souboru
	 */
	public void spaceUse(){
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Error");
		alert.setHeaderText("Using space");
		alert.setContentText("You can not use 'space' symbol for wall.");
		alert.show();
	}
	
	/**
	 * Chybove hlase pri zadani nemsiho cisla nez jedna
	 */
	public void higherThanOne(){
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Size of maze must be higher than 1");
		alert.show();
	}
	
	/**
	 * Chybova hlaska pri zadani prilis velkeho cisla
	 * @param number cislo ziskane z textFieldu od uzivatele
	 */
	public void tooHigh(int number){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Info");
		alert.setHeaderText("Can't display so big maze");
		alert.setContentText("Maximum size of maze can be " + number);
		alert.show();
	}
	
	
	
	/**
	 * Zobrazi okno pro zadani znaku ktery ma byt pouzit pro 
	 * symbol zdi pri exportu do textoveho souboru
	 * @return pokud byl znak zadan vrati ho.... jinak vraci null
	 */
	public String choiseSymbol(){
		TextInputDialog dia = new TextInputDialog();
		dia.setTitle("Export");
		dia.setHeaderText("Export maze to .txt");
		dia.setContentText("Please choose the symbol for wall: ");
		
		Optional<String> sym = dia.showAndWait();
		
		try{
		//pokud byl zadan symbol vrati
		if(sym.isPresent()){
			
			//ziskani retezce z pole
			String symbol =  sym.get();				
				//byl zadan alespon jeden znak
				if(symbol.length() > 0){
					//retezec/znak neobsahuje mezery
					if(!symbol.contains(" ")){
						return symbol;
					}
					else{
						//informovani o tom ze nelze pouzit symbol mezery
						spaceUse();
						return null;
					}										
				}
				//informace o tom ze nebyl zadan zadny znak
				else{
					noInputFromTF();
					return null;
				}
		}}
			//zachyceni vyjjimky
			catch(NoSuchElementException e){
				return null;
			}
		//jinak vraci null
			return null;		
	}	
	
/**
 * infomacni okno s dotazem jak ma byt bludiste vyexportovano
 * vraci promenou podle toho jaky byl zvolen zpusob pro export
 * @return 20 pro textovy export, 10 pro graficky export, -1 chyba
 */
	public int howToExport(){
		List<String> choises = new ArrayList<>();
		choises.add(toTxt);
		choises.add(toImg);
		
		ChoiceDialog<String> dia  = new ChoiceDialog<>("Choose how",choises);
		dia.setTitle("Choice Dialog");
		dia.setHeaderText("Look and Choice");
		dia.setContentText("Choose how do you want export maze:");
		
		Optional<String> result = dia.showAndWait();
		try{
		if(result.isPresent()){
			
			//byl zvolen export do txt
			if(result.get() == toTxt){
				return Gui.TXT;
			}
			//byl zvolen export do img
			else if(result.get() == toImg){
				return Gui.IMG;
			}
		}		
		}
		//zachyceni vyjjimky
		catch(NoSuchElementException e){
			return Gui.NON;
		}
		//nebyla zvolena zadna moznost
		return Gui.NON;
	}
}


