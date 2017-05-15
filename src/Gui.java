



import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
/**
 * Trrida ve kterem se posklada postupne cele Gui
 * 
 * trida obsahuje take jednotlivou obsluhu jednotlivych tlacitek
 * 
 *trida by si zaslouzila rozdelit a oddelit tak obsluhu tlacitek od grafickzch prvku 
 * 
 * @author Jokertwo
 *
 */
public class Gui {

	public int SIRKA_PIXELU = 10;
	public int SIRKA_OKRAJE = 10;
	
	//promena kam se ulozi posledni zadana velikost bludiste
	private int lastSize = -1;
	
	//promena podle ktere se urci jakym algoritmem bylo vygenerovno bludiste
	private int typeOfAlgorim = 0;
	private int SimpAlgo = 1;
	private int DfsAlgo = 2;
	
	
	//disable tlacitka export pri prvnim spusteni
	//disable tlacitka solution pri prvnim spusteni
	private boolean export_solution = true;
	
	
	//pole pro zadani velikosti bludiste
	public TextField sizeTF;
	

	public Generovani g = new Generovani();	
	public DFSMaze d = new DFSMaze();
	public Drawing draw = new Drawing(SIRKA_PIXELU);
	public BorderPane root2 = new BorderPane();
	public Scene scne2;
	public Stage primaryStage;
	
	public ErrorLog err = new ErrorLog();
	//inicializace promene kde bude ulozen cas jak dlouho trvalo generovani promene
	long duration = -1;
	
	//promene pouzivane pro urceni exportu
	static final int IMG = 10;
	static final int TXT = 20;
	static final int NON = -1;
 
	
	
	public Gui(Stage primaryStage,Scene scne2){
		
		this.primaryStage = primaryStage;
		
		this.scne2 = scne2;
		
	}

	
	/**
	 * TextFiled kam se zadava velikost bludiste
	 * @return vrati HBox obsahujici textField
	 */
	public Node textField(){
		HBox box = new HBox();
		Label nameL = new Label("Size: ");
		sizeTF = new TextField();
		box.getChildren().addAll(nameL,sizeTF);
		box.setSpacing(5);		
		return box;		
	}
	
	/**
	 * Popis s informaci o celkovem casu potrebnem ke generovani bludiste
	 * @return pocis vcetne informace/hodnoty jak dlouho trvalo generovani bludiste
	 */
	public Label timeToGenerate(){
		//vytvoreni popisku
		Label duration = new Label();
		
		//nastaveni textu
		duration.setText("Duration of generate: " + this.duration + "ms");
		
		return duration;
	}
	
	/**
	 * metoda zjisti hodnotu v TextField a tu vrati
	 * vcetne vsech osetrenych vyjimek
	 * @return ziskane cislo z textfieldu
	 */
	public int getValueFromTF(){
		int iValue = -1;
		String sValue = sizeTF.getText();
		
		//pole neni vyplneno
		if(sValue.length() <= 0){
			//uz byla zadana nejaka hodnota a tak se pouzije posledni
			if(lastSize > 0){
				return lastSize;
			}
			//prvni spusteni programu -> musi byt zadana alespon nejaka hodnota 
			else{
				err.noInputFromTF();
			return -1;
			}
		}
		
		//prevedeni String na integer
		try{
		iValue = Integer.parseInt(sValue);
		}
		//zachyceni chyby pokud nebylo zadano cislo
		catch(NumberFormatException e){
			err.notNumber(sValue);
			return -1;
		}
		//ulozeni posledni hodnoty
		lastSize = iValue;
		
		//kontrola hodnoty jeslti neni prilis velka nebo mala
		return errorLog(iValue);
	}
	
	
	
	/**
	 * kontrola jestli cislo vyhovuje parametrum pro generovani 
	 * v pripade ze je mimo povoleny rozsah zamezi jeho vygenerovani
	 * @param iValue  velikost poyadovaneho bludiste
	 * @return vraci velikost poradvaneho bludiste pokud je v pozadovanem rozsahu jinak -1
	 */
	public int errorLog(int iValue){
		//nelze vygenerovat bludiste s velikosti 1 
				if(iValue <= 1){
					err.higherThanOne();
					return -1;
				}
				//omezeni maximalni velikosti bludiste
				if(iValue >Main.maxSizeOfMaze){
					err.tooHigh(Main.maxSizeOfMaze);
					return -1;
				}
			return iValue;
	}
	
	
	/**
	 * posklada vyskledne okno.....!!!! jeden z parametru musi byt NULL !!!!
	 * 
	 * @param g Generovani
	 * @param d DFSMaze
	 */
	public void setScene(Generovani g, DFSMaze d){
		int[][] matrix;
		
		//zjisteni ktery algoritmus byl pouzit
		if(g == null){
			matrix = d.getMatrix();
		}else{
			matrix = g.getMatrix();
		}		
		if(scne2 == null){
			scne2 = new Scene(root2);
		}
		//povoleni exportu
		export_solution = false;
		
		//poskladani vysledneho okna s bludistem
		root2.setCenter(draw.drawShapes(matrix));
		root2.setTop(topPanel());
		
		
		//uspusobeni velikosti okna
	    this.primaryStage.setScene(scne2);
	    this.primaryStage.setHeight(matrix.length * SIRKA_PIXELU + (SIRKA_OKRAJE * 6)+ Main.Y_PRVNI_OKNO);
	    this.primaryStage.setWidth(matrix.length * SIRKA_PIXELU + (SIRKA_OKRAJE * 4));

		
		
	}
	
	
	/**
	 * obsluha tlacitka ktere generuje bludiste pomoci
	 * naivniho jednoducheho algoritmu
	 * 
	 */
	public void serviceOfButtonSimple(){
		
		int sizeOfMaze = getValueFromTF();
		lastSize = sizeOfMaze;
		if(sizeOfMaze > 0 && sizeOfMaze <= Main.maxSizeOfMaze){
				//nastaveni velikosti bludiste
			g.setSize(sizeOfMaze);
			    //zacatek mereni casu generovani
			long startTime  = System.currentTimeMillis();
				//generuje matici s bludistem
			g.generateMaze();	
				//konec casu generovani
			long endTime  = System.currentTimeMillis();
			    //spocitani casu generovani
			this.duration = endTime - startTime;
				
				
				//nastavi promenou pro urceni typu algoritmu ktery byl pouzit
				//jako posledni
			typeOfAlgorim = SimpAlgo;
				//vytvoreni okna s bludistem 
			setScene(g, null);
		}
	}
	
	
	
	/**
	 * obsluha tlacitka generujici bludiste
	 * pomoci algoritmu DFS
	 * 
	 */
	public void servisOfButtonDFS(){
		int sizeOfMaze = getValueFromTF();

		if(sizeOfMaze > 0 && sizeOfMaze <= Main.maxSizeOfMaze){
			//zacatek mereni casu generovani
			long startTime  = System.currentTimeMillis();
				//generuje matici s bludistem
			d.generateMaze(sizeOfMaze);
				//konec casu generovani
			long endTime  = System.currentTimeMillis();
				//spocitani casu generovani
			this.duration = endTime - startTime;
			
			
				
				//nastavi promenou pro urceni tzpu algoritmu kterz bzl pouzit
				//jako posledni
				typeOfAlgorim = DfsAlgo;
				
			setScene(null, d);
		}
	}
	
	/**
	 * Obluha tlacitka ktere ve vygenerovanem bludisti nalezne
	 * cestu z jednoho bodu do druheho
	 * 
	 */
	public void serviceOfButtonSolution(){
		Solution s = new Solution();
		
		if(typeOfAlgorim == 0){
			System.out.println("Chyba");
		}
		else if(typeOfAlgorim == SimpAlgo){
			
			
		    s.setStart(g.getStart());
		    s.setFinis(g.getFinis());
		    
		    s.BFS(g.getMatrix());
		    draw.drawShapes(g.getMatrix());
		}
		else if(typeOfAlgorim == DfsAlgo){
			s.setStart(d.getStart());
		    s.setFinis(d.getFinis());
		    
		    s.BFS(d.getMatrix());
		    draw.drawShapes(d.getMatrix());
		}
	}
	
	/**
	 * Otevre okno s textfieldem pro zadani znaku pro zed
	 * pote podle typu nposled pouziteho algoritmu otevre fileChooser
	 * pro zvoleni kam se ma vysledny soubor exportovat a vyexportuje bludiste jako .txt
	 */
	
	public void fileChooser(){
		ChooseFile ch = new ChooseFile();
		
		int kindOfExp = err.howToExport();
		
		if(kindOfExp == TXT){
			String symbol = err.choiseSymbol();
		
			if(symbol != null){
				if(typeOfAlgorim == SimpAlgo){
	        		ch.exportTXT(g.getMatrix(),this.primaryStage,symbol);
	        	}
	        	if(typeOfAlgorim == DfsAlgo){
	        		ch.exportTXT(d.getMatrix(),this.primaryStage,symbol);
	        	}
			}
		}
		if(kindOfExp == IMG){
			ch.exportIMG(draw.canvas, primaryStage);
		}
		
	}
		
	public Node tlacitka(){
		//tlacitka pro generovani
				Button simply = new Button("Simply algorithm");
				Button dfs = new Button("DFS algorithm");
				Button solution = new Button("Solution");
				Button export1 = new Button("Export");
				Button test = new Button("Test");
				
				export1.setDisable(export_solution);
				solution.setDisable(export_solution);
				
				//obsluha tlacitek
				simply.setOnAction(event->serviceOfButtonSimple());
				dfs.setOnAction(event->servisOfButtonDFS());
				solution.setOnAction(event->serviceOfButtonSolution());
				export1.setOnAction(event->fileChooser());
				test.setOnAction(event->charts());
				
				HBox box2 = new HBox();
				
				box2.getChildren().addAll(simply,dfs,solution,export1,test);
				box2.setSpacing(2);
				box2.setPadding(new Insets(0, 20, 0, 20)); 
				return box2;
	}
	
	/**
	 * pomocna metoda ktera pouze zamezi pri prvnim spusteni
	 * zobrazit cas pro generovani bludiste
	 * 
	 * @return pri prvnim spusteni nevrati nic jinak pridava do horniho panelu cas trvani generovani
	 */
	public Node helpTopBox(){
		
		HBox hbox = new HBox();
		
		if(this.duration < 0){
			hbox.getChildren().add(textField());
		}
		else{
			hbox.getChildren().addAll(textField(),timeToGenerate());
		}
		hbox.setSpacing(5);
		hbox.setAlignment(Pos.CENTER);
		return hbox;
	}
	
	/**
	 * posklada horni panel s tlacitky a textFieldem a labelem  
	 * 
	 * @return vraci poskladany horni panel s tlacitky a textfieldem
	 */
	public Node topPanel(){
		VBox box = new VBox();
		
		box.getChildren().addAll(tlacitka(),helpTopBox());
		box.setSpacing(5);
		
		HBox box2 = new HBox();
		box2.getChildren().add(box);
		box2.setAlignment(Pos.CENTER);
		
		return box2;
		
	}
	
	
	/**
	 * otesuje oba algoritmy pouzivane pro generovani bludiste
	 * a zobrazi graf s vyslekem 
	 * 
	 */
	public void charts(){
		
		Test test = new Test(g,d);
		
		
		
		final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        //popis os 
        xAxis.setLabel("Size of maze (size of matrix)");
        yAxis.setLabel("Time (ms)");
        
        final LineChart<Number,Number> lineChart = 
                new LineChart<Number,Number>(xAxis,yAxis);
       
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Simple Algoritm");
        
        
        test.testGenerovani();
        
        for(int i = 0; i < test.valueX.size();i++){
        	series1.getData().add(new XYChart.Data(test.valueX.get(i),test.valueY.get(i)));
        }
       
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("DFS Algoritm");
       
        test.valueX.clear();
        test.valueY.clear();
        test.testDFSMaze();
        
        for(int i = 0; i < test.valueX.size();i++){
        	series2.getData().add(new XYChart.Data(test.valueX.get(i),test.valueY.get(i)));
        }
        
        lineChart.getData().addAll(series1,series2);
        
        if(scne2 == null){
			scne2 = new Scene(root2);
		}
        
        //vypnuti bodu na grafu
        lineChart.setCreateSymbols(false);
        
		//poskladani vysledneho okna s bludistem
		root2.setCenter(lineChart);
		root2.setTop(topPanel());
        
		
		this.primaryStage.setScene(scne2);
	    
	}
	
}
