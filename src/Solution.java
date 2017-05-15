import java.util.LinkedList;
import java.util.Queue;

public class Solution {

	
	
	private int[] Start;
	private int[] Finis;
	public int numberOfSteps;
	
	public void setFinis(int[] finis){
		this.Finis = finis;
	}
	public void setStart(int[] start){
		this.Start = start;
	}
	
	public void BFS(int[][] matrix){
		Queue<Bod> fronta = new LinkedList<Bod>(); 
		Bod zacatek = new Bod(Start[0],Start[1]);
		zacatek.hodnota = 1;
		numberOfSteps = 0;
		
		
	
		fronta.add(zacatek);
		
		while(!fronta.isEmpty()){
			
			Bod zaklad = fronta.remove();
			
			Bod sever = sever(zaklad,matrix);
			Bod vychod = vychod(zaklad,matrix);
			Bod jih = jih(zaklad,matrix);
			Bod zapad = zapad(zaklad,matrix);
			
			if(sever != null){
				
			
					sever.hodnota = zaklad.hodnota+1;
					matrix[sever.i][sever.j] = sever.hodnota;				
					fronta.add(sever);
					if(sever.i-1 == Finis[0] && sever.j == Finis[1]){
						numberOfSteps = sever.hodnota;
						break;
					}
				
			}
			if(vychod != null){
				
				
					vychod.hodnota = zaklad.hodnota+1;
					matrix[vychod.i][vychod.j] = vychod.hodnota;
					fronta.add(vychod);
					if(vychod.i == Finis[0] && vychod.j+1 == Finis[1]){
						numberOfSteps = vychod.hodnota;
						break;	
					}
				
			}
				
			if(jih != null){
				
				
					jih.hodnota = zaklad.hodnota +1;
					matrix[jih.i][jih.j] = jih.hodnota;
					fronta.add(jih);
					if(jih.i+1 == Finis[0] && jih.j == Finis[1]){
						numberOfSteps = jih.hodnota;
						break;
					}
				
			}
			if(zapad != null){
				
				
					zapad.hodnota = zaklad.hodnota + 1;
					matrix[zapad.i][zapad.j] = zapad.hodnota;
					fronta.add(zapad);
					if(zapad.i  == Finis[0] && zapad.j-1 == Finis[1]){
						numberOfSteps = zapad.hodnota;
						break;
					}
			}
		}	
		
		fronta.clear();
		markSolution(matrix, numberOfSteps, Finis[0], Finis[1]);
		
	}
	
	public void markSolution(int[][] matrix,int way,int i, int j){
		
		
		if(way > 1){
			if(matrix[i-1][j] == way){
				matrix[i-1][j] = -50;
				markSolution(matrix, way-1, i-1, j);
			}
			else if(matrix[i+1][j] == way){
				matrix[i+1][j] = -50;
				markSolution(matrix, way-1, i+1, j);
			}
			else if(matrix[i][j-1] == way){
				matrix[i][j-1] = -50;
				markSolution(matrix, way-1, i, j-1);
			}
			else if(matrix[i][j+1] == way){
				matrix[i][j+1] = -50;
				markSolution(matrix, way-1, i, j+1);
			}
			
		}
		
		
	}
	
	public Bod sever(Bod zaklad, int[][] matrix){
		Bod sever = null;
		if(matrix[zaklad.i-1][zaklad.j] == 0){
			sever = new Bod(zaklad.i-1,zaklad.j);
			return sever;
		}
		return null;		
	}
	public Bod vychod(Bod zaklad, int[][] matrix){
		Bod vychod = null;
		if(matrix[zaklad.i][zaklad.j+1] == 0){
			vychod = new Bod(zaklad.i,zaklad.j+1);
			return vychod;
		}
		return null;
	}
	public Bod jih(Bod zaklad, int[][] matrix){
		Bod jih = null;
		if(matrix[zaklad.i+1][zaklad.j] == 0){
			jih = new Bod(zaklad.i+1,zaklad.j);
			return jih;
		}
		return null;
	}
	public Bod zapad(Bod zaklad, int[][] matrix){
		Bod zapad = null;
		if(matrix[zaklad.i][zaklad.j-1] == 0){
			zapad = new Bod(zaklad.i,zaklad.j-1);
			return zapad;
		}
		return null;
	}
		
	public void printMatrix(int[][] matrix){
		for(int i = 0; i < matrix.length; i++){
			for(int j = 0; j < matrix[i].length; j++){
				if(matrix[i][j] == -2){
				System.out.print(matrix[i][j]+ " ");
				}
				else{
					System.out.print(matrix[i][j]+ "  ");
				}
			}
			System.out.println();
		}
		System.out.println(" ");
	}
	
}
