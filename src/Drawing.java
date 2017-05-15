
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


/**
 * trida starajici o vykresleni bludiste
 * Kreslim pomoci canvas a jako parametr potrebuje matici
 *  
 * 
 * @author Jokertwo
 *
 */
public class Drawing {

	
	
	public Canvas canvas = new Canvas(0,0);
	public GraphicsContext gc;
	public int SIRKA_PIXELU;
	private Color wall = Color.GREEN;
	private Color start = Color.RED;
	private Color goal = Color.BLUE;
	private Color way = Color.YELLOW;
	private Color solution = Color.BLACK;
	
	public Drawing(int sirkaPixelu){
		this.SIRKA_PIXELU = sirkaPixelu;
	}
	
	
		/**
		 * Kreslim na Canvas ktery i vrati spolecne s vykreslenym bludistem
		 * 
		 * @param matrix s vygenerovanym bludistem
		 * @return vraci canvas s bludistem
		 */
	public Canvas drawShapes(int[][] matrix){
		//kreslici platno
		canvas.setWidth(matrix.length * SIRKA_PIXELU);
		canvas.setHeight(matrix.length * SIRKA_PIXELU);
		
		gc = canvas.getGraphicsContext2D();
		    
	        for(int i = 0; i < matrix.length; i++){
	        	for(int j = 0; j < matrix.length; j++){        		
	        			gc.setFill(color(matrix[i][j]));
	        			if(matrix[i][j] != -50){
	        			gc.fillRect(i*SIRKA_PIXELU, j*SIRKA_PIXELU, SIRKA_PIXELU, SIRKA_PIXELU);
	        			}
	        			else{
	        				gc.setFill(way);
	        				gc.fillRect(i*SIRKA_PIXELU, j*SIRKA_PIXELU, SIRKA_PIXELU, SIRKA_PIXELU);
	        				gc.setFill(color(matrix[i][j]));
	        				gc.fillOval(i*SIRKA_PIXELU, j*SIRKA_PIXELU, SIRKA_PIXELU, SIRKA_PIXELU);
		        			
	        			}
	        			
	        			
	        	}
	        }
	     return canvas;   
	}
	
	/**
	 * Nastavi barvu kterou se ma kreslit
	 * @param number cislo na konretni pozici v matici
	 * @return Color color vraci barvu kterou se bude kreslit
	 */
	public Color color(int number){
		Color color = null;
		
		switch(number){
			//barva pro zacatek
			case Main.begin: color = start;
						break;
			//barva pro zdi... podklad
			case Main.wall: color = wall;
						break;
			//barva pro konec
			case Main.goal: color = goal;
						break;
			//barva pro nalezenou cestu
			case Main.solution: color = solution;
						break;
			//barva pro cesty
			default: color = way;
		}
		
		return color;
	}
	
}
