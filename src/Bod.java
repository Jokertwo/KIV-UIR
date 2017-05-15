/**
 * Trida repreyentujici jednu bunku/bod v bludisti.
 * Trida se pouziva pri hledani cesty skrz bludiste
 * 
 * 
 * @author Jokertwo
 *
 */
public class Bod {

	
	int i;
	int j;
	//vzdalenost od zacatku bludiste
	int hodnota;
	
	/**
	 * konstruktor tridy
	 * jako parametry prijima indexy matice
	 * @param i index v matici
	 * @param j index v matici
	 */
	public Bod(int i, int j){
		this.j = j;
		this.i = i;
		this.hodnota = -100;
	}
	
	/**
	 * tisk vzdalenosti konretni bunky od pocatku bludiste
	 */
	public void printBod(){
		System.out.println(this.hodnota);
	}
}
