package jeuDeDames;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Case {

	private String abcisse; //l'abcisse de la case, allant de "A" a "H" sur un plateau classique de 64 cases
	private int ordonnee; //l'ordonnee de la case, allant de 1 a 8 sur un plateau classique de 64 cases
	private String position; //l'abcisse et l'ordonnee de la case
	private boolean pionPresent = false; //false s'il n'y a pas de pion sur la case, true si il y a un pion sur la case
	private boolean pionBlanc = false; //false s'il n'y a pas de pion blanc sur la case, true si il y a un pion blanc sur la case
	private boolean pionNoir = false; //false s'il n'y a pas de pion noir sur la case, true si il y a un pion noir sur la case
	private Pion pion; //Le pion present sur la case
	private String path; //Le chemin de l'image a afficher dans le bouton de la case
	private JButton bouton; //Le bouton de la case avec une image de pion ou de case rouge vide
	private JPanel panel; //Le panel de la case dans lequel on place le bouton
	private Clique clique; //l'objet qui permet de reagir au clique sur la case
	private Partie partie;
	
	public Case (String a, int o, Partie part) {
		this.partie = part;
		this.abcisse = a;
		this.ordonnee = o;
		this.position = abcisse + ordonnee; 
		System.out.println("Vous venez de creer la case : " + this.position);
	}
	
	public String getAbcisse() {
		return this.abcisse;
	}
	
	public int getOrdonnee() {
		return this.ordonnee;
	}
	
	public String getPosition() {
		return this.position;
	}
	
	public Pion getPion() {
		return this.pion;
	}
	
	public void displayPosition() {
		System.out.println("Abcisse de la case : " + this.abcisse + " /  Ordonnee de la case : " + this.ordonnee);
	}
	
	public void setPionPresent(Pion p) throws IOException {
		this.pionPresent = true;
		this.pion = p;
		String text = null;
		this.panel.remove(this.bouton);if (p.getCouleur().equals("blanc")) {
			this.pionBlanc = true;
			if (this.pion.isDame()) {
				text = "dameBlanche";
			} else {
				text = "pionBlanc";
			}
		} else if (p.getCouleur().equals("noir")) {
			this.pionNoir = true;
			if (this.pion.isDame()) {
				text = "dameNoire";
			} else {
				text = "pionNoir";
			}
		}
		if (this.partie.getPlateau().getTaille()==8) {
			this.path="/data/" + text + ".png";
		} else {
			this.path="/data/" + text + "10.png";
		}
		BufferedImage picture = ImageIO.read(getClass().getResource(this.path));
		this.bouton = new JButton(new ImageIcon(picture));
		this.bouton.setName(text);
		this.clique = new Clique(this);
		this.panel.add(this.bouton);
	}
	
	public void setPionAbsent() throws IOException {
		this.pionPresent = false;
		this.pionBlanc = false;
		this.pionNoir = false;
		this.panel.remove(this.bouton);
		this.path="/data/caseVide.png";
		BufferedImage picture = ImageIO.read(getClass().getResource(this.path));
		this.bouton = new JButton(new ImageIcon(picture));
		this.bouton.setName("caseVide");
		this.clique = new Clique(this);
		this.panel.add(this.bouton);
	}
	
	public boolean pionPresent() {
		return this.pionPresent;
	}
	
	public boolean pionBlancPresent() {
		return this.pionBlanc;
	}
	
	public boolean pionNoirPresent() {
		return this.pionNoir;
	}
	
	public JPanel createImage(String typeCase) throws IOException {
		this.path="/data/caseVide.png";
		BufferedImage picture = ImageIO.read(getClass().getResource(path));
		this.bouton = new JButton(new ImageIcon(picture));
		this.bouton.setName(typeCase);
		this.panel = new JPanel();
		this.partie.getReglement().determinerAffichage(this, panel, this.partie.getPlateau());
		this.clique = new Clique(this);
		this.panel.add(this.bouton);
		return panel;
	}
	
	public void caseShown(boolean b) throws IOException {
		if (b==true) {
			this.panel.remove(this.bouton);
			this.path="/data/caseShown.png";
			BufferedImage picture = ImageIO.read(getClass().getResource(this.path));
			this.bouton = new JButton(new ImageIcon(picture));
			this.bouton.setName("caseShown");
			this.clique = new Clique(this);
			this.panel.add(this.bouton);
		} else {
			this.panel.remove(this.bouton);
			this.path="/data/caseVide.png";
			BufferedImage picture = ImageIO.read(getClass().getResource(this.path));
			this.bouton = new JButton(new ImageIcon(picture));
			this.bouton.setName("caseVide");
			this.clique = new Clique(this);
			this.panel.add(this.bouton);
		}
		this.partie.repaint();
	}
	
	public JButton getBouton() {
		return this.bouton;
	}
	
	public JPanel getPanel() {
		return this.panel;
	}
	
	public Clique getClique() {
		return this.clique;
	}
	
	public Partie getPartie() {
		return this.partie;
	}
}
