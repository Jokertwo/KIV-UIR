import java.util.ArrayList;

public class Test {
	
	Generovani g;
	DFSMaze d;
	//opakovane testy pri stejne velikosti bludiste
	//pro omezeni krajnich hodnot
	public double numberOfSecondTest = 50;
	//maximalni testovaci velikost bludiste
	public int numberOfMainTest = 50;
	ArrayList<Integer> valueX = new ArrayList<>();
	ArrayList<Double> valueY = new ArrayList<>();
	
	
	
	
	public Test(Generovani g, DFSMaze d){
		this.g= g;
		this.d = d;
	}
	
	
	public void testGenerovani(){
	long start;
	long finish;
	long duration;
	
	
	//testovani doby pro jednotlive velikosti bludiste
	for(int i = 2; i < numberOfMainTest; i++){
		//sem se ulozi celkova doba pro vytvoreni
		double sum = 0;
		//pomocny index
		//pouziva se pro opakovane vytvoreni jedne velikosti bludiste
		int j = 0;
		//nastaveni velikosti bludiste
		g.setSize(i);;
		while(j < numberOfSecondTest){
			//zacatek mereni
			start = System.currentTimeMillis();
			//generovani bludiste
			g.generateMaze();
			//konec mereni
			finish = System.currentTimeMillis();
			//doba potrebna pro generovani
			duration = finish - start;
			//celkova doba pro vytvoreni
			sum += duration;
			//inkrementace pomocne promene
			j++;
		}
		//ulozeni honoty pro X osu (velikost bludiste)
		valueX.add(i);
		//prumerna cas pro vytvoreni bludiste
		valueY.add(sum/numberOfSecondTest);
					
	}
		
}
	
	
	public void testDFSMaze(){
		
		long start;
		long finish;
		long duration;
		
		
		//testovani doby pro jednotlive velikosti bludiste
		for(int i = 2; i < numberOfMainTest; i++){
			//sem se ulozi celkova doba pro vytvoreni
			double sum = 0;
			//pomocny index
			//pouziva se pro opakovane vytvoreni jedne velikosti bludiste
			int j = 0;
			while(j < numberOfSecondTest){
				//zacatek mereni
				start = System.currentTimeMillis();
				//generovani bludiste
				d.generateMaze(i);
				//konec mereni
				finish = System.currentTimeMillis();
				//doba potrebna pro generovani
				duration = finish - start;
				//celkova doba pro vytvoreni
				sum += duration;
				//inkrementace pomocne promene
				j++;
			}
			//ulozeni honoty pro X osu (velikost bludiste)
			valueX.add(i);
			
			//prumerna cas pro vytvoreni bludiste
			valueY.add(sum/numberOfSecondTest);
						
		}
		
		
		
	}

	
	
}
