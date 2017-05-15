
import java.util.Random;

public class Generovani {

	
	private int[][] matrix;
	private int size;
	private int basic = 0;
	private int[] start = new int[2];
	private int[] finis = new int[2];
	public Random r = new Random();
	
	public int[] getStart(){
		return this.start;
	}
	public int[] getFinis(){
		return this.finis;
	}
	
	public int[][] getMatrix(){
		return matrix;
	}
	
	public void setSize(int size){
		this.size = size * 2 + 1;
		matrix = new int[this.size][this.size];
	}
	
	/**
	 * Vytvori zaklad matice pro generovani budouciho bludiste
	 * @return vraci matici ohranicenou zdi v4etne vytvorenych zakladu; 
	 */
	public int[][] createMatrix()
	{
		
		for(int i = 0; i < matrix.length; i++){
			for(int j = 0; j <matrix[i].length; j++){
				//ohraniceni okraju zdi
				if(i == 0 || i == matrix.length-1 || j == 0 || j == matrix.length-1){
					matrix[i][j] = Main.wall;
				}
				else{
					//vytvoreni zakladu z kterych se budou zacinat stavet zdi
					if(i % 2 == 0 && j % 2 == 0){
						matrix[i][j] = Main.basic;
						basic++;
					}
					else{
					//ostatni policka jsou volna -> cesta
						matrix[i][j] = Main.way;
					}				
				}				
			}
		}		
		return matrix;		
	}
	
	/**
	 * Vybere start a cil v bludisti
	 * 
	 */
	public void chooseStartAndEnd(){
	int iStart,iFinis,jStart,jFinis;	
		
		iStart = r.nextInt(this.size);
			while(iStart % 2 == 0){
				iStart = r.nextInt(this.size);
			}
		jStart = r.nextInt(this.size);
			while(jStart % 2 == 0){
				jStart = r.nextInt(this.size);
			}
	do{
		iFinis = r.nextInt(this.size);
			while(iFinis % 2 == 0){
				iFinis = r.nextInt(this.size);
			}
		jFinis = r.nextInt(this.size);
			while(jFinis % 2 == 0){
				jFinis = r.nextInt(this.size);
			}
	}while(iFinis == iStart && jFinis == jStart);
			
		matrix[iStart][jStart] = Main.begin;
		matrix[iFinis][jFinis] = Main.goal;
		
		start[0] = iStart;
		start[1] = jStart;
		
		finis[0] = iFinis;
		finis[1] = jFinis;
	
	}

	/**
	 * tiskne matici do konzole
	 */
	public void printMatrix(){
		for(int i = 0; i < matrix.length; i++){
			for(int j = 0; j < matrix[i].length; j++){
				System.out.print(matrix[i][j]+ " ");
			}
			System.out.println();
		}
	}

	/**
	 * metoda pocita pocet zbyvajicich zakladu z kterych lze stavet
	 * @return vraci pocet zbyvajicich zakladu v matici
	 */
	public int countBasic(){
		
		int count = 0;
		
		for(int i = 0; i < matrix.length; i++){
			for(int j = 0; j < matrix[i].length; j++){
				if(matrix[i][j] == Main.basic){
					count++;
				}				
			}
		}
		return count;
	}
	
	
	/**
	 * nahdone ze vsech pocatecnich bodu vzbere jeden 
	 * a zjisti jeho souradnice v matici
	 * 
	 * @param all pocet ybzvajici pocatecnich bodu
	 * @return vraci pole s indexem pocatecniho pole odkud se bude stavet dalsi zed
	 */
	public int[] coordinates(int all){
		
		int[] coordinates = new int[2];
		int i,j;
		
		int count = 0;
		int start =  r.nextInt(all);
		
		if(start == 0){
			start++;
		}
		
		for(i = 0; i < matrix.length; i++){
			for(j = 0; j < matrix[i].length; j++){
				
				if(matrix[i][j] == Main.basic){
					count++;
					if(count == start){
						coordinates[0] = i;
						coordinates[1] = j;
						return coordinates;
					}							
				}				
			}
		}
		//nenalezl jsem uz zadny zacatek odkud lze postavit zed
		coordinates[0] = -1;
		coordinates[1] = -1;
		return coordinates;	
	}
	
	public void north(int i ,int j){
		while(i != 0 && matrix[i][j] != Main.wall){
			matrix[i][j] = Main.wall;
			i--;
		}			
	}
	public void south(int i,int j){
			
		while(i != matrix.length-1 && matrix[i][j] != Main.wall){
			matrix[i][j] = Main.wall;
			i++;
		}
	}
	public void west(int i,int j){
		while(j != 0 && matrix[i][j] != Main.wall){
			matrix[i][j] = Main.wall;
			j--;
		}
	}
	public void east(int i,int j){
		while(j != matrix[i].length-1 && matrix[i][j] != Main.wall){
			matrix[i][j] = Main.wall;
			j++;
		}
	}
	
	/**
	 * vytvori z bodu (souradnice predane v argumentu)
	 * zed nahodnym smerem
	 * 
	 * @param coor pole obsahujici index v matici odkud se ma zacit stavet
	 */
	public void wall(int[] coor){
		int direction;
		
		
		if(coor[0] != -1 && coor[1] != -1){
			
				direction = r.nextInt(4);
			if(direction == 0){
				north(coor[0], coor[1]);
			}
			if(direction == 1){
				east(coor[0], coor[1]);
			}
			if(direction == 2){
				west(coor[0], coor[1]);
			}
			if(direction == 3){
				south(coor[0], coor[1]);
			}
		}
		
	}
	/**
	 * Hlavni metoda pro vytvoreni bludiste
	 */
	public void generateMaze(){
		//vytvori zaklad
		createMatrix();
		
		
		//postupne tvori zdi dokud jsou k dispozici pocatecni body
		while(basic != 0){
			
			wall(coordinates(basic));
			//snizeni poctu zakladu
			basic--;
		}
		chooseStartAndEnd();
	}
	
	

}
