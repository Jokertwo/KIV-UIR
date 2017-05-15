import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Trida generujici bludiste pomoci DFS algoritmu
 * vysledne bludiste ulozi v matici
 * 
 * 
 * @author Jokertwo
 *
 */

public class DFSMaze {
	
	
	private int size;
	private int[][] matrix;
	//pole s ulozenymi souradnicemi zacatku bludiste
	private int[] start = new int[2];
	//pole s ulozenymi souradnicemi cile bludiste
	private int[] finis = new int[2];
	public Random r = new Random();
	
	
	
	public void setMatrix(int size){
		this.size = size*2+1;
		matrix = new int[this.size][this.size];
	}
	public int[][] getMatrix(){
		return matrix;
	}
	public int[] getStart(){
		return this.start;
	}
	public int[] getFinis(){
		return this.finis;
	}
	
	
	/**
	 * Vytvori pocatecni matici kde oznaci okraje matice jako zdi
	 * 
	 */
	public void createMatrix(){
		for(int i = 0; i < this.size; i++){
			for(int j = 0; j < this.size; j++){
				matrix[i][j] = Main.wall;
			}
		}
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
			
		
		
		start[0] = iStart;
		start[1] = jStart;
		
		finis[0] = iFinis;
		finis[1] = jFinis;
		
	}
	
	
	/**
	 * vygerenuje pole se ctyrmi smery a nahodne promicha poradi
	 * @return vraci Integer pole se ctyrmi smery v nahodnem poradi
	 */
	 public Integer[] generateRandomDirections() {
	 
		 ArrayList<Integer> randoms = new ArrayList<Integer>();
	      for (int i = 0; i < 4; i++)
	           randoms.add(i + 1);
	      Collections.shuffle(randoms);
	 
	      return randoms.toArray(new Integer[4]);    
	 }
	 
	
	/**
	 * rekurzivne metoda vola sama sebe
	 * podle nahodne vzgenerovaneho pole smeru
	 * se snazi kope cesty bludistem
	 * @param i index v matici
	 * @param j index v matici
	 */
	public void generate(int i, int j){
		
		Integer[] direction = generateRandomDirections();
		
		for(int k = 0;k < direction.length; k++){
			switch(direction[k]){
			
				//smer nahoru
				case 1:	
					if(north(i, j)){						
						generate(i-2,j);
					}				
					break;
				//smer dolu
				case 2:
					if(south(i, j)){					
						generate(i+2,j);							
					}
					break;
				//smer doprava	
				case 3:
					if(east(i, j)){					
						generate(i,j+2);
					}
					break;
				//smer doleva
				case 4:
					if(west(i, j)){
						generate(i,j-2);	
					}
					break;
			}		
		}		
	}
	
	/**
	 * pokusi se vypopat cestu nahoru
	 * pokud se to povede vraci true jinak false
	 * jako parametr bere index v matici odkud ma zacit kopat
	 * @param i index matice
	 * @param j index matice
	 * @return true pokud se podarilo vykopat cestu
	 */
	public boolean north(int i,int j){
		
		if(i-2 > 0){
			
			 if(matrix[i-2][j] != Main.way ){
				 matrix[i-2][j] = Main.way;
				 matrix[i-1][j] = Main.way;			
				return true;
			 }			
		}
		return false;
	}
	/**
	 * pokusi se vypopat cestu dolu
	 * pokud se to povede vraci true jinak false
	 * jako parametr bere index v matici odkud ma zacit kopat
	 * @param i index matice
	 * @param j index matice
	 * @return true pokud se podarilo vykopat cestu
	 */
	public boolean south(int i,int j){
		
			if(i+2 < this.size){
				if(matrix[i+2][j] != Main.way ){
					matrix[i+2][j] = Main.way;
					matrix[i+1][j] = Main.way;
					
					return true;
				}								
		}
		return false;
	}
	/**
	 * pokusi se vypopat cestu doprava
	 * pokud se to povede vraci true jinak false
	 * jako parametr bere index v matici odkud ma zacit kopat
	 * @param i index matice
	 * @param j index matice
	 * @return true pokud se podarilo vykopat cestu
	 */
	public boolean east(int i,int j){
		
			if(j+2 < this.size){
				if( matrix[i][j + 2] != Main.way ){
					matrix[i][j+2] = Main.way;
					matrix[i][j+1] = Main.way;
				
					return true;
				}			
			}
		return false;
	}
	/**
	 * pokusi se vypopat cestu doleva
	 * pokud se to povede vraci true jinak false
	 * jako parametr bere index v matici odkud ma zacit kopat
	 * @param i index matice
	 * @param j index matice
	 * @return true pokud se podarilo vykopat cestu
	 */
	public boolean west(int i,int j){
		
			if(j-2 > 0){
				if(matrix[i][j - 2] != Main.way ){
					matrix[i][j-1] = Main.way;
					matrix[i][j-2] = Main.way;
					return true;
				}		
		}
		return false;
	}

	/**
	 * hlavni volana metoda ktera postupne zavola jednotlive dilci kroky
	 * pro vygenerovani bludiste
	 * jako parametr bere velikost pozadovaneho bludiste
	 * @param size velikost bludiste
	 */
	public void generateMaze(int size){
		setMatrix(size);
		createMatrix();
		chooseStartAndEnd();
		generate(start[0],start[1]);
		matrix[start[0]][start[1]] = Main.begin;
		matrix[finis[0]][finis[1]] = Main.goal;
	}
	
	
	/**
	 * metoda pouzivana pri testovani
	 * tiskne vygenerovanou matici do konzole 
	 */
	public void printMatrix(){
		for(int i = 0; i < matrix.length; i++){
			for(int j = 0; j < matrix.length; j++){
				System.out.print(matrix[i][j]+ " ");
			}
			System.out.println();
		}
		System.out.println("------------------------------");
	}
	
}
