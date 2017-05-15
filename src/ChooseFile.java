import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;


import javax.imageio.ImageIO;


import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ChooseFile {

	public void exportIMG(Canvas canvas,Stage stage){
		FileChooser fileChooser = new FileChooser();
        
        //nastaveni filtru/povoleni ktere pripony lze pouzit
        FileChooser.ExtensionFilter extFilter = 
                new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        FileChooser.ExtensionFilter extFilter2 = 
                new FileChooser.ExtensionFilter("GIF files (*.gif)", "*.gif");
        FileChooser.ExtensionFilter extFilter3 = 
                new FileChooser.ExtensionFilter("PCX files (*.pcx)", "*.pcx");
        FileChooser.ExtensionFilter extFilter4 = 
                new FileChooser.ExtensionFilter("EPS files (*.eps)", "*.eps");
        FileChooser.ExtensionFilter extFilter5 = 
                new FileChooser.ExtensionFilter("PS files (*.ps)", "*.ps");
        fileChooser.getExtensionFilters().addAll(extFilter,extFilter2,extFilter3,extFilter4,extFilter5);
       
        //zobrazeni fileChooser
        File file = fileChooser.showSaveDialog(stage);
         
        
        if(file != null){
        	String fileName = file.getName();
        	String fileExtension  = fileName.substring(fileName.indexOf(".") + 1, file.getName().length());
            try {
                WritableImage writableImage = new WritableImage((int)canvas.getHeight(), (int)canvas.getWidth());
                canvas.snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(renderedImage,fileExtension, file);
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
	}
	public String numbreOfSpace(String symbol){
		String space  = "";
		for(int i = 0; i < symbol.length();i++){
			space += " ";
		}
		return space;
	}
	
	public String numberOfSolution(String symbol){
		String solution  = "";
		for(int i = 0; i < symbol.length();i++){
			if(symbol.contains("#")){
				solution += "*";
			}
			else{
				solution += "#";
			}
			
		}
		return solution;
	}
	
	public void exportTXT(int[][] matrix,Stage stage,String symbol){
		
		FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Maze");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
            );
        fileChooser.getExtensionFilters().addAll(
        		 new FileChooser.ExtensionFilter("Text", "*.txt")
        		);
        
        File file = fileChooser.showSaveDialog(stage);
		
		if(file != null){
		try{
		    PrintWriter writer = new PrintWriter(file, "UTF-8");
		    
		    for(int i = 0; i < matrix.length; i++){
		    	for(int j = 0; j < matrix[i].length; j++){
		    		if(matrix[i][j] == Main.wall){
		    			writer.print(symbol);
		    		}
		    		else if(matrix[i][j] == Main.solution){
		    			writer.print(numberOfSolution(symbol));
		    		}
		    		
		    		else {
		    			writer.print(numbreOfSpace(symbol));
		    		}
		    		
		    	}
		    	writer.println();
		    }
		    
		    writer.close();
		} catch (IOException e) {
		   // do something
		}
		}
		
	}
	
}
