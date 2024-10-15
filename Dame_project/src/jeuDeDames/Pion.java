package jeuDeDames;

import java.io.IOException;

public class Pion {
	private String name; //Le nom du pion
	private String couleur; //La couleur du pion
	private Case c; //La case sur laquelle est positionn� le pion actuellement
	private Partie partie;
	private boolean dame;
	private int score;
	private boolean alive = true, devenuDame = false;
		
	public Pion (String name, String co, Case ca, Partie part) throws IOException {
		this.partie = part;
		this.name = name;
		this.couleur = co;
		this.dame = false;
		this.c = ca;
		this.c.setPionPresent(this);
		System.out.println("Vous venez de creer un pion " + this.couleur + " et de le poser sur la case " + this.c.getPosition() + ".");
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getCouleur() {
		return this.couleur;
	}
	
	public Case getCase() {
		return this.c;
	}
	
	public void setCase(Case cas) throws IOException {
		this.c.setPionAbsent();
		this.partie.setCaseDevenueVide(this.c);
		this.c = cas;
		this.c.setPionPresent(this);
	}
	
	public void setDead() throws IOException {
		this.c.setPionAbsent();
		this.alive = false;
		if (this.couleur.equals("blanc")) {
			this.score = 1000;
		} else if (this.couleur.equals("noir")) {
			this.score = 0;
		}
	}
	
	public Partie getPartie() {
		return this.partie;
	}

	public void devientDame() throws IOException {
		this.dame = true;
		this.devenuDame = true;
		this.partie.getMessage().setText("Le pion " + this.name + " est devenu une dame !");
	}
	
	public void setDame(boolean b) {
		this.dame = b;
	}
	
	public boolean isDame() {
		return this.dame;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public boolean isDevenuDame() {
		return devenuDame;
	}
	
	public void setDevenuDame(boolean b) {
		this.devenuDame = b;
	}
}
