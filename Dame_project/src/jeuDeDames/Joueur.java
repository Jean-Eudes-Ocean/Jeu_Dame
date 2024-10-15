package jeuDeDames;

import java.io.IOException;

import javax.swing.JLabel;

public class Joueur {

	private String name; //le nom du joueur
	private String couleur; //la couleur du joueur
	private Plateau plateau; //le plateau sur lequel se d�roule la partie
	private Reglement r; //le r�glement que doit suivre la partie
	private Partie partie; //la partie en cours
	private int nbPions; //le nombre de pions restant � ce joueur
	private int nbKill = 0; //le nombre de pions que ce joueur a mang�
	private int nbWin = 0; //le nombre de victoire de ce joueur
	private int nbDames = 0;
	private JLabel labelName, labelNbP, labelNbD, labelNbK;
	private boolean bot;
	
	public Joueur (Partie part, String nam, String cou, boolean bot, Plateau plat) {
		this.partie = part;
		if (bot) {
			this.name = nam + " (bot)";
			this.bot = true;
		} else {
			this.name = nam;
			this.bot = false;
		}
		this.couleur = cou;
		this.plateau = plat;
		if (this.plateau.getTaille()==8) {
			this.nbPions = 12;
		} else {
			this.nbPions = 20;
		}
		this.r = this.partie.getReglement();
		System.out.println("Vous venez de creer le joueur " + this.name + " qui jouera du cote " + this.couleur + ".");
		this.labelName = new JLabel("            Joueur 1 : " + this.name);
		this.labelNbP = new JLabel("            Nombre de pions :  " + nbPions);
		this.labelNbD = new JLabel("            Nombre de dames :  " + nbDames);
		this.labelNbK = new JLabel("            Nombre de kills :  " + nbKill);
	}
	
	public Joueur (Partie part, String nameJ1, String couleurJ1, String nam, boolean bot, Plateau plat) {
		this.partie = part;
		if (bot) {
			this.name = nam + " (bot)";
			this.bot = true;
		} else {
			this.name = nam;
			this.bot = false;
		}
		if (couleurJ1=="blanc") {
			this.couleur = "noir";
		} else {
			this.couleur = "blanc";
		}
		this.plateau = plat;
		if (this.plateau.getTaille()==8) {
			this.nbPions = 12;
		} else {
			this.nbPions = 20;
		}
		this.r = this.partie.getReglement();
		System.out.println("Vous venez de creer le joueur " + this.name + " qui jouera du cote " + this.couleur + ".");
		this.labelName = new JLabel("            Joueur 2 : " + this.name);
		this.labelNbP = new JLabel("            Nombre de pions :  " + nbPions);
		this.labelNbD = new JLabel("            Nombre de dames :  " + nbDames);
		this.labelNbK = new JLabel("            Nombre de kills :  " + nbKill);
	}
	
	public boolean move(Pion p, Case c) throws IOException {
		boolean ret = false;
		if (this.r.tryMove(p, c, this.plateau)) {
			this.partie.getMessage().setText("Le joueur " + this.couleur + " vient de deplacer le pion " + p.getName() + " sur la case " + c.getPosition() + ".");
			if (p.isDame()) {
			} else {
				if (this.plateau.getTaille()==8) {
					if (c.getOrdonnee()==1 || c.getOrdonnee()==8) {		
						p.devientDame();
						this.nbDames++;
						this.labelNbD.setText("            Nombre de dames :  " + this.nbDames);
					}
				} else { //si plateau fait 10*10
					if (c.getOrdonnee()==1 || c.getOrdonnee()==10) {		
						p.devientDame();
						this.nbDames++;
						this.labelNbD.setText("            Nombre de dames :  " + this.nbDames);
					}
				}
			}
			p.setCase(c);
			ret = true;
		}
		return ret;
	}
	
	public boolean eat(Pion p, Case c) throws IOException {
		boolean ret = false;
		Pion ennemi;
		if (this.r.tryEat(p, c, this.plateau)) {
			ennemi = this.r.getPionMange();
			ennemi.setDead();
			this.nbKill++;
			this.labelNbK.setText("            Nombre de kills :  " + this.nbKill);
			this.partie.getMessage().setText("Le joueur " + this.couleur + " vient de manger le pion " + ennemi.getName() + " en d�placant le pion " + p.getName() + " sur la case " + c.getPosition() + ".");
			if (p.isDame()) {
			} else {
				if (this.plateau.getTaille()==8) {
					if (p.getCouleur().equals("noir") & c.getOrdonnee()==1 || p.getCouleur().equals("blanc") &  c.getOrdonnee()==8) {		
						p.devientDame();
						this.nbDames++;
						this.labelNbD.setText("            Nombre de dames :  " + this.nbDames);
					}
				} else { //si plateau fait 10*10
					if (p.getCouleur().equals("noir") & c.getOrdonnee()==1 || p.getCouleur().equals("blanc") & c.getOrdonnee()==10) {		
						p.devientDame();
						this.nbDames++;
						this.labelNbD.setText("            Nombre de dames :  " + this.nbDames);
					}
				}
			}
			p.setCase(c);
			this.partie.signalerKill(ennemi, this);
			this.partie.setMange(true);
			ret = true;
			}
		return ret;
	}
	
	public void souffler(Pion p) throws IOException {
		p.getCase().setPionAbsent();
		this.nbKill++;
		this.labelNbK.setText("            Nombre de kills :  " + this.nbKill);
		this.partie.getMessage().setText("Souffler n'est pas jouer ! Pion " + p.getCouleur() + " sur " + p.getCase().getPosition());
		this.partie.signalerKill(p, this);
	}
	
	public void pionKilled(Pion killed) {
		this.nbPions--;
		this.labelNbP.setText("            Nombre de pions :  " + this.nbPions);
		if (killed.isDame()) {
			this.nbDames--;
			this.labelNbD.setText("            Nombre de dames :  " + this.nbDames);
		}
	}
	
	public void reset() throws IOException {
		if (this.plateau.getTaille()==8) {
			this.nbPions = 12;
		} else {
			this.nbPions = 20;
		}
		this.nbKill = 0;
		this.nbDames = 0;
		this.labelNbP = new JLabel("            Nombre de pions :  " + nbPions);
		this.labelNbD = new JLabel("            Nombre de dames :  " + nbDames);
		this.labelNbK = new JLabel("            Nombre de kills :  " + nbKill);
		this.plateau = this.partie.getPlateau();
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getCouleur() {
		return this.couleur;
	}
	
	public int getNbPions() {
		return this.nbPions;
	}
	
	public int getNbKill() {
		return this.nbKill;
	}

	public int getNbDames() {
		return nbDames;
	}

	public JLabel getLabelNbP() {
		return labelNbP;
	}

	public JLabel getLabelNbD() {
		return labelNbD;
	}

	public JLabel getLabelNbK() {
		return labelNbK;
	}

	public int getNbWin() {
		return this.nbWin;
	}
	
	public Partie getPartie() {
		return this.partie;
	}

	public void setCouleur(String coul) {
		this.couleur = coul;
	}
	
	public void setNbWin(int i) {
		this.nbWin = i;
	}
	
	public void setPlateau(Plateau p) {
		this.plateau = p;
	}

	public boolean isBot() {
		return bot;
	}

	public JLabel getLabelName() {
		return labelName;
	}
}
