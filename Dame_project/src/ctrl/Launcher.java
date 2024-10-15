package ctrl;

import java.io.IOException;
import jeuDeDames.Partie;

public class Launcher {

	public static void main (String args[]) {
		try {
			//Cree la partie
			new Partie(10); //dimensions du plateau 10*10 (50 cases)
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}