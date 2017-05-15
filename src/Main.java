
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;





public class Main extends Application{


	static final int maxSizeOfMaze = 42;
	static final int X_PRVNI_OKNO = 530;
	static final int Y_PRVNI_OKNO = 75;
	static final int maxSize = 900;
	public Stage primaryStage;	
	public Scene scne2;
	
		//promena oznacujici cestu
		static final int way = 0;
		//promena oznacujici zacatek bludiste
		static final int begin = -1;
		//promena oznacujici zed
		static final int wall = -2;	
		//promena oznacujici cil bludiste
		static final int goal = -3;
		//promena oznacujici zaklad ze ktereho bude zacinat zed
		static final int basic = -4;
		//promena oznacujici nalezenou cestu
		static final int solution = -50;
	
	public BorderPane root = new BorderPane();

	
	public void start(Stage primaryStage){
		this.primaryStage = primaryStage;
		Gui gui = new Gui(this.primaryStage,scne2);

		root.setTop(gui.topPanel());
		
		
		Scene scne1 = new Scene(root,X_PRVNI_OKNO,Y_PRVNI_OKNO);
		
		
        
		this.primaryStage.setTitle("Maze");
		this.primaryStage.setScene(scne1);
		this.primaryStage.setMinHeight(Y_PRVNI_OKNO);
		this.primaryStage.setMinWidth(X_PRVNI_OKNO);
		this.primaryStage.setMaxHeight(maxSize+ Y_PRVNI_OKNO);
		this.primaryStage.setMaxWidth(maxSize);
		this.primaryStage.show();
	}
	
	public static void main(String[] args) {
		
		launch(args);
		
	}
}
