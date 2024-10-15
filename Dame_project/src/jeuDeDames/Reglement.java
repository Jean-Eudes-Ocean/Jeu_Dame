package jeuDeDames;

import java.io.IOException;
import java.util.Arrays;

import javax.swing.JPanel;

public class Reglement {
	
	private Pion pionMange; //le pion qu'on essaye de manger dans la fonction "tryEat"
	private String[] abcisse = new String[10]; //un tableau de 10 caract�res qui contient les abcisses du plateau, pour "traduire" les lettres en entier et calculer les abcisses
	private String abcisseActuelle; //l'abcisse actuelle du pion qu'on cherche � d�placer
	private int ordonneeActuelle; //l'ordonn�e actuelle du pion qu'on cherche � d�placer
	private String abcisseCible; //l'abcisse de la case destination
	private int ordonneeCible; //l'ordonn�e de la case destination
	private int index; //la variable qui va nous permettre de traduire l'abcisse du pion en entier
	private int indexCible; //la variable qui va nous permettre de traduire l'abcisse de la case destination en entier
	
	public void initValues(Pion pion, Case cas) { //permet d'initialiser toutes les variables
		this.abcisse[0] = "A";
		this.abcisse[1] = "B";
		this.abcisse[2] = "C";
		this.abcisse[3] = "D";
		this.abcisse[4] = "E";
		this.abcisse[5] = "F";
		this.abcisse[6] = "G";
		this.abcisse[7] = "H";
		this.abcisse[8] = "I";
		this.abcisse[9] = "J";
		this.abcisseActuelle = pion.getCase().getAbcisse();
		this.ordonneeActuelle = pion.getCase().getOrdonnee();
		this.abcisseCible = cas.getAbcisse();
		this.ordonneeCible = cas.getOrdonnee();
		this.index=0;
		this.indexCible=0;
		
		index = calculate(abcisseActuelle);
		indexCible = calculate(abcisseCible);
	}
	
	/** M�thode qui permet de tester si ce deplacement est autorise
	 * @param pion : le pion qu'on veut deplacer
	 * @param cas : la case sur laquelle on veut deplacer le pion
	 * @param plat : le plateau sur lequel on joue
	 * @return true : si le deplacement est autorise, false sinon
	 */
	public boolean tryMove(Pion pion, Case cas, Plateau plat) {
		this.initValues(pion, cas); //on initialise toutes les variables dont on va avoir besoin pour d�terminer la possibilit� du mouvement
		if (cas.pionPresent()) { //s'il y a un pion sur la case destination, on ne peut pas s'y d�placer
			return false;
		}
		boolean checkAbcisse = false; //si le pion essaye de se d�placer d'une case � gauche ou � droite, ce sera vrai
		if (abcisseActuelle.equals(abcisseCible)) { //si le pion et sa destination ont la m�me abcisse
			//System.out.println("Vous ne pouvez vous d�placer qu'en diagonale.");
			return false;
		}
		if (ordonneeActuelle == ordonneeCible) { //si le pion et sa destination ont la m�me ordonn�e
			//System.out.println("Vous ne pouvez vous deplacer qu'en diagonale.");
			return false;
		}
		
		if (indexCible==index+1) { //si le pion se deplace d'une case � droite
			checkAbcisse = true;
		} else if (indexCible==index-1) { //si le pion se deplace d'une case a gauche
			checkAbcisse = true;
		}
		if (checkAbcisse) {
			if (pion.getCouleur().equals("blanc") || pion.isDame()) { //si le pion est blanc
				if (ordonneeCible==ordonneeActuelle+1) { //les pions blancs ne peuvent se d�placer que d'une case vers le haut
					return true; //si l'abcisse fait 1 de diff, et l'ordonn�e aussi
				}
			}
			if (pion.getCouleur().equals("noir") || (pion.isDame())) { //si le pion est noir
				if (ordonneeCible==ordonneeActuelle-1) { //les pions blancs ne peuvent se d�placer que d'une case vers le bas
					return true; //si l'abcisse fait 1 de diff, et l'ordonn�e aussi
				}
			} else { //si l'abcisse fait 1 de diff, alors l'ordonn�e aussi, sinon return false
				//System.out.println("Vous ne pouvez vous d�placer qu'en diagonale.");
				return false;
			}
		}
		
		if (pion.isDame()) { //si le pion est une dame, il a le droit de se d�placer de plus d'une case
			int difference = 0; //le nombre de case qu'il y a entre le pion et sa destination
			int ordonnee = 0; //cet entier va nous servir � tester si la diff�rence entre les deux abcisses et les deux ordonn�es est la m�me
			if (index<indexCible) {	//si la destination est � droite du pion
				difference = indexCible - index; //la diff�rence entre les deux abcisses
			} else { //si la destination est � gauche du pion
				difference = index - indexCible; //la diff�rence entre les deux abcisses
			}
			if (ordonneeCible>ordonneeActuelle) { //si la destination est en haut du pion
				ordonnee = ordonneeActuelle + difference; //il faut que la diff�rence entre les deux abcisses et les deux ordonn�es est la m�me 
			} else { //si la destination est en bas du pion
				ordonnee = ordonneeActuelle - difference; //il faut que la diff�rence entre les deux abcisses et les deux ordonn�es est la m�me 
			}
			if (ordonneeCible == ordonnee) { //si le pion essaye de se d�placer en diagonale
				int nbCaseCheck = difference - 1; //si il y a x cases des diff�rences entre le pion et sa destination, alors il y a x-1 cases � check
				for (int i=1; i<=nbCaseCheck; i++) { //la boucle qui va nous permettre de v�rifier les x cases
					if (index<indexCible) { //si la desination est � droite du pion
						if (ordonneeActuelle<ordonneeCible) { //si la destination est en haut � droite du pion
							if (!plat.getCase(abcisse[index+i] + (ordonneeActuelle+i)).pionPresent()) { //on v�rifie sur la i�me case � v�rifier qu'il n'y a pas de pion
								//System.out.println("Il n'y a pas de pion sur la case " + plat.getCase(abcisse[index+i] + (ordonn�eActuelle+i)).getPosition());
								if (i==nbCaseCheck) { //il y avait x cases � v�rifier, si i == x, alors on a v�rifi� toutes les cases
									return true;
								}
							} else return false; //si il y a un pion sur la i�me case check, on ne peut pas se d�placer
						} else { //si la destination est en bas � droite du pion
							if (!plat.getCase(abcisse[index+i] + (ordonneeActuelle-i)).pionPresent()) { //si la destination est en bas � droite du pion
								//System.out.println("Il n'y a pas de pion sur la case " + plat.getCase(abcisse[index+i] + (ordonn�eActuelle-i)).getPosition());
								if (i==nbCaseCheck) { //il y avait x cases � v�rifier, si i == x, alors on a v�rifi� toutes les cases
									return true;
								}
							} else return false; //si il y a un pion sur la i�me case check, on ne peut pas se d�placer
						}
					} else { //si la destination est � gauche du pion
						if (ordonneeActuelle<ordonneeCible) { //si la destination est en haut � gauche du pion
							if (!plat.getCase(abcisse[index-i] + (ordonneeActuelle+i)).pionPresent()) { //on v�rifie sur la i�me case � v�rifier qu'il n'y a pas de pion
								//System.out.println("Il n'y a pas de pion sur la case " + plat.getCase(abcisse[index-i] + (ordonn�eActuelle+i)).getPosition());
								if (i==nbCaseCheck) { //il y avait x cases � v�rifier, si i == x, alors on a v�rifi� toutes les cases
									return true;
								}
							} else return false; //si il y a un pion sur la i�me case check, on ne peut pas se d�placer
						} else { //si la destination est en bas � gauche du pion
							if (!plat.getCase(abcisse[index-i] + (ordonneeActuelle-i)).pionPresent()) { //si la destination est en bas � gauche du pion
								//System.out.println("Il n'y a pas de pion sur la case " + plat.getCase(abcisse[index-i] + (ordonn�eActuelle-i)).getPosition());
								if (i==nbCaseCheck) { //il y avait x cases � v�rifier, si i == x, alors on a v�rifi� toutes les cases
									return true;
								}
							} else return false; //si il y a un pion sur la i�me case check, on ne peut pas se d�placer
						}
					}
				} //si i<nbCaseCheck, i++, pour check la case suivante
			} else { //s'il y a x de diff�rence entre les deux abcisses, alors il doit y avoir 2 de diff�rence entre les deux ordonn�es, sinon return false
				//System.out.println("Vous ne pouvez vous d�placer qu'en diagonale.");
				return false;
			}
		}
		return false; //si le d�placement n'est pas autoris�
	}
	
	/**
	 * M�thode qui permet de tester si ce "mangement" est autoris�
	 * @param pion : le pion qu'on veut d'placer
	 * @param cas : la case sur laquelle on veut d�placer le pion
	 * @param plat : le plateau sur lequel on joue
	 * @return true : si le "mangement" est autoris�, false sinon
	 * initialise l'attribut this.pionMang� si le "mangement" est autoris�
	 */
	public boolean tryEat(Pion pion, Case cas, Plateau plat) {
		this.initValues(pion, cas); //on initialise toutes les variables dont on va avoir besoin pour d�terminer la possibilit� du "mangement"
		if (cas.pionPresent()) { //s'il y a un pion sur la case destination, on ne peut pas s'y d�placer
			return false;
		}
		boolean checkAbcisse = false; //si le pion essaye de se d�placer d'une case � gauche ou � droite, ce sera vrai
		int indexMange=0, ordonneeMange; //les coordonn�es du pion qu'on essaye de manger
		if (abcisseActuelle.equals(abcisseCible)) { //si le pion et sa destination ont la m�me abcisse
			return false;
		}
		if (ordonneeActuelle==ordonneeCible) { //si le pion et sa destination ont la m�me ordonn�e
			return false;
		}
		
		if (indexCible==index+2) { //si le pion se d�place de deux cases � droite
			indexMange = index+1;
			checkAbcisse = true;
		} else if (indexCible==index-2) { //si le pion se d�place de deux cases � gauche
			indexMange = index-1;
			checkAbcisse = true;
		}
		
		if (checkAbcisse) {
			if (ordonneeCible==ordonneeActuelle+2) { //si la destination est deux cases au dessus du pion
				ordonneeMange = ordonneeActuelle+1;
			} else if (ordonneeCible==ordonneeActuelle-2) { //si la destination est deux cases en dessous du pion
				ordonneeMange = ordonneeActuelle-1;
			} else { //si la destination n'est pas � deux hauteurs d'�cart avec le pion
				//System.out.println("Vous ne pouvez vous d�placer qu'en diagonale.");
				return false;
			}
			if (plat.getCase(abcisse[indexMange] + ordonneeMange).pionPresent()) { //si le pion passe au dessus d'un autre pion pour arriver � sa case destination
				this.pionMange = plat.getCase(abcisse[indexMange] + ordonneeMange).getPion(); //nous avons trouv� le pion qu'on essaye de manger
				if (pion.getCouleur().equals(pionMange.getCouleur())) { //si les deux pions sont de la m�me couleur
					//System.out.println("Vous ne pouvez pas manger un pion de votre couleur.");
					return false;
				} else { //si les deux pions ne sont pas de la m�me couleur
					return true;
				}
			}
		}
		
		if (pion.isDame()) { //si le pion est une dame, il a le droit de se d�placer de plus d'une case
			int difference = 0; //le nombre de case qu'il y a entre le pion et sa destination
			int ordonnee = 0; //cet entier va nous servir � tester si la diff�rence entre les deux abcisses et les deux ordonn�es est la m�me
			int nbPions = 0; //le nombre de pions crois�s sur la route de la dame
			if (index<indexCible) {	//si la destination est � droite du pion
				difference = indexCible - index; //la diff�rence entre les deux abcisses
			} else { //si la destination est � gauche du pion
				difference = index - indexCible; //la diff�rence entre les deux abcisses
			}
			if (ordonneeCible>ordonneeActuelle) { //si la destination est en haut du pion
				ordonnee = ordonneeActuelle + difference; //il faut que la diff�rence entre les deux abcisses et les deux ordonn�es est la m�me 
			} else { //si la destination est en bas du pion
				ordonnee = ordonneeActuelle - difference; //il faut que la diff�rence entre les deux abcisses et les deux ordonn�es est la m�me 
			}
			if (ordonneeCible == ordonnee) { //si le pion essaye de se d�placer en diagonale
				int nbCaseCheck = difference - 1; //si il y a x cases des diff�rences entre le pion et sa destination, alors il y a x-1 cases � check
				for (int i=1; i<=nbCaseCheck; i++) { //la boucle qui va nous permettre de v�rifier les x cases
					if (index<indexCible) { //si la desination est � droite du pion
						if (ordonneeActuelle<ordonneeCible) { //si la destination est en haut � droite du pion
							if (!plat.getCase(abcisse[index+i] + (ordonneeActuelle+i)).pionPresent()) { //on v�rifie sur la i�me case � v�rifier qu'il n'y a pas de pion
								//System.out.println("Il n'y a pas de pion sur la case " + plat.getCase(abcisse[index+i] + (ordonn�eActuelle+i)).getPosition());
								if (i==nbCaseCheck) { //il y avait x cases � v�rifier, si i == x, alors on a v�rifi� toutes les cases
									if (nbPions==1) { //s'il y a un pion ennemi sur le chemin, on peut le manger
										return true;
									} else { //s'il n'y a pas de pion sur le chemin, on ne peut pas manger
										//System.out.println("Il n'y a pas de pion a manger...");
										return false;
									}
								}
							} else {
								this.pionMange = plat.getCase(abcisse[index+i] + (ordonneeActuelle+i)).getPion(); //nous avons trouv� un pion qu'on essaye de manger
								if (pion.getCouleur().equals(pionMange.getCouleur())) { //si les deux pions sont de la m�me couleur
									//System.out.println("Il y a un alli� sur la case " + pionMang�.getCase().getPosition());
									return false;
								} else { //si les deux pions ne sont pas de la m�me couleur
									//System.out.println("Il y a un ennemi sur la case " + pionMang�.getCase().getPosition());
									nbPions++;
									if (nbPions==2) {
										//System.out.println("Il y a plusieurs pions sur le chemin.");
										return false;
									}
									if (i==nbCaseCheck) { //il y avait x cases � v�rifier, si i == x, alors on a v�rifi� toutes les cases
										return true;
									}
								}
							}
						} else { //si la destination est en bas � droite du pion
							if (!plat.getCase(abcisse[index+i] + (ordonneeActuelle-i)).pionPresent()) { //on v�rifie sur la i�me case � v�rifier qu'il n'y a pas de pion
								//System.out.println("Il n'y a pas de pion sur la case " + plat.getCase(abcisse[index+i] + (ordonn�eActuelle-i)).getPosition());
								if (i==nbCaseCheck) { //il y avait x cases � v�rifier, si i == x, alors on a v�rifi� toutes les cases
									if (nbPions==1) { //s'il y a un pion ennemi sur le chemin, on peut le manger
										return true;
									} else { //s'il n'y a pas de pion sur le chemin, on ne peut pas manger
										//System.out.println("Il n'y a pas de pion a manger...");
										return false;
									}
								}
							} else {
								this.pionMange = plat.getCase(abcisse[index+i] + (ordonneeActuelle-i)).getPion(); //nous avons trouv� un pion qu'on essaye de manger
								if (pion.getCouleur().equals(pionMange.getCouleur())) { //si les deux pions sont de la m�me couleur
									//System.out.println("Il y a un alli� sur la case " + pionMang�.getCase().getPosition());
									return false;
								} else { //si les deux pions ne sont pas de la m�me couleur
									//System.out.println("Il y a un ennemi sur la case " + pionMang�.getCase().getPosition());
									nbPions++;
									if (nbPions==2) {
										//System.out.println("Il y a plusieurs pions sur le chemin.");
										return false;
									}
									if (i==nbCaseCheck) { //il y avait x cases � v�rifier, si i == x, alors on a v�rifi� toutes les cases
										return true;
									}
								}
							}
						}
					} else { //si la destination est � gauche du pion
						if (ordonneeActuelle<ordonneeCible) { //si la destination est en haut � gauche du pion
							if (!plat.getCase(abcisse[index-i] + (ordonneeActuelle+i)).pionPresent()) { //on v�rifie sur la i�me case � v�rifier qu'il n'y a pas de pion
								//System.out.println("Il n'y a pas de pion sur la case " + plat.getCase(abcisse[index-i] + (ordonn�eActuelle+i)).getPosition());
								if (i==nbCaseCheck) { //il y avait x cases � v�rifier, si i == x, alors on a v�rifi� toutes les cases
									if (nbPions==1) { //s'il y a un pion ennemi sur le chemin, on peut le manger
										return true;
									} else { //s'il n'y a pas de pion sur le chemin, on ne peut pas manger
										//System.out.println("Il n'y a pas de pion a manger...");
										return false;
									}
								}
							} else {
								this.pionMange = plat.getCase(abcisse[index-i] + (ordonneeActuelle+i)).getPion(); //nous avons trouv� un pion qu'on essaye de manger
								if (pion.getCouleur().equals(pionMange.getCouleur())) { //si les deux pions sont de la m�me couleur
									//System.out.println("Il y a un alli� sur la case " + pionMang�.getCase().getPosition());
									return false;
								} else { //si les deux pions ne sont pas de la m�me couleur
									//System.out.println("Il y a un ennemi sur la case " + pionMang�.getCase().getPosition());
									nbPions++;
									if (nbPions==2) {
										//System.out.println("Il y a plusieurs pions sur le chemin.");
										return false;
									}
									if (i==nbCaseCheck) { //il y avait x cases � v�rifier, si i == x, alors on a v�rifi� toutes les cases
										return true;
									}
								}
							}
						} else { //si la destination est en bas � gauche du pion
							if (!plat.getCase(abcisse[index-i] + (ordonneeActuelle-i)).pionPresent()) { //on v�rifie sur la i�me case � v�rifier qu'il n'y a pas de pion
								//System.out.println("Il n'y a pas de pion sur la case " + plat.getCase(abcisse[index-i] + (ordonn�eActuelle-i)).getPosition());
								if (i==nbCaseCheck) { //il y avait x cases � v�rifier, si i == x, alors on a v�rifi� toutes les cases
									if (nbPions==1) { //s'il y a un pion ennemi sur le chemin, on peut le manger
										return true;
									} else { //s'il n'y a pas de pion sur le chemin, on ne peut pas manger
										//System.out.println("Il n'y a pas de pion a manger...");
										return false;
									}
								}
							} else {
								this.pionMange = plat.getCase(abcisse[index-i] + (ordonneeActuelle-i)).getPion(); //nous avons trouv� un pion qu'on essaye de manger
								if (pion.getCouleur().equals(pionMange.getCouleur())) { //si les deux pions sont de la m�me couleur
									//System.out.println("Il y a un alli� sur la case " + pionMang�.getCase().getPosition());
									return false;
								} else { //si les deux pions ne sont pas de la m�me couleur
									//System.out.println("Il y a un ennemi sur la case " + pionMang�.getCase().getPosition());
									nbPions++;
									if (nbPions==2) {
										//System.out.println("Il y a plusieurs pions sur le chemin.");
										return false;
									}
									if (i==nbCaseCheck) { //il y avait x cases � v�rifier, si i == x, alors on a v�rifi� toutes les cases
										return true;
									}
								}
							}
						}
					}
				} //si i<nbCaseCheck, i++, pour check la case suivante
			} else { //s'il y a x de diff�rence entre les deux abcisses, alors il doit y avoir x de diff�rence entre les deux ordonn�es, sinon return false
				//System.out.println("Vous ne pouvez vous d�placer qu'en diagonale.");
				return false; 
			}
		}		
		//System.out.println("Un pion ne peut se d�placer que d'une case en diagonale (deux si il mange).");
		return false; //si le d�placement n'est pas autoris�
	}

	/**
	 * Permet de d�terminer la position � laquelle on va afficher l'image selon la case
	 * @param c : la case sur laquelle on veut afficher l'image
	 * @param p : le panel � positionner, qui contier l'image � afficher
	 * @param plat : le plateau sur lequelle se d�roule la partie
	 */
	public void determinerAffichage(Case c, JPanel p, Plateau plat) { //permet de d�terminer la position de la case pour afficher le JButton sur le plateau
		int abcisseFrame; //l'abcisse qui va nous servir � positionner le panel
		int ordonneeFrame; //l'ordonn�e qui va nous servir � positionner le panl
		if (plat.getTaille()==8) { //si le plateau fait 8*8
			if (c.getAbcisse()=="A" ) { //si l'abcisse de la case est "A", alors on doit positionner le panel sur l'abcisse 125
				abcisseFrame = 125;
			} else if (c.getAbcisse()=="B" ) {
				abcisseFrame = 206;			
			} else if (c.getAbcisse()=="C" ) {
				abcisseFrame = 286;			
			} else if (c.getAbcisse()=="D" ) {
				abcisseFrame = 366;			
			} else if (c.getAbcisse()=="E" ) {
				abcisseFrame = 446;			
			} else if (c.getAbcisse()=="F" ) {
				abcisseFrame = 526;			
			} else if (c.getAbcisse()=="G" ) {
				abcisseFrame = 606;			
			} else {
				abcisseFrame = 686;			
			}
			if (c.getOrdonnee()==1) { //si l'ordonn�e de la case est 1, alors on doit positionner le panel sur l'ordonn�e 621
				ordonneeFrame = 621;
			} else if (c.getOrdonnee()==2) {
				ordonneeFrame = 541;
			} else if (c.getOrdonnee()==3) {
				ordonneeFrame = 461;
			} else if (c.getOrdonnee()==4) {
				ordonneeFrame = 381;
			} else if (c.getOrdonnee()==5) {
				ordonneeFrame = 300;
			} else if (c.getOrdonnee()==6) {
				ordonneeFrame = 220;
			} else if (c.getOrdonnee()==7) {
				ordonneeFrame = 140;
			} else {
				ordonneeFrame = 60;
			}
			p.setBounds(abcisseFrame,ordonneeFrame,72,82); //On positionne le panel en position "abcisseFrame", "ordonn�eFrame", ce panel fait 72 large, et 82 de haut
		} else { //si le plateau fait 10*10
			if (c.getAbcisse()=="A" ) { //si l'abcisse de la case est "A", alors on doit positionner le panel sur l'abcisse 77
				abcisseFrame = 77;
			} else if (c.getAbcisse()=="B" ) {
				abcisseFrame = 148;			
			} else if (c.getAbcisse()=="C" ) {
				abcisseFrame = 218;			
			} else if (c.getAbcisse()=="D" ) {
				abcisseFrame = 288;			
			} else if (c.getAbcisse()=="E" ) {
				abcisseFrame = 358;			
			} else if (c.getAbcisse()=="F" ) {
				abcisseFrame = 428;			
			} else if (c.getAbcisse()=="G" ) {
				abcisseFrame = 498;			
			} else if (c.getAbcisse()=="H" ) {
				abcisseFrame = 568;			
			} else if (c.getAbcisse()=="I" ) {
				abcisseFrame = 639;			
			} else {
				abcisseFrame = 711;			
			}
			if (c.getOrdonnee()==1) { //si l'ordonn�e de la case est 1, alors on doit positionner le panel sur l'ordonn�e 678
				ordonneeFrame = 678;
			} else if (c.getOrdonnee()==2) {
				ordonneeFrame = 606;
			} else if (c.getOrdonnee()==3) {
				ordonneeFrame = 535;
			} else if (c.getOrdonnee()==4) {
				ordonneeFrame = 464;
			} else if (c.getOrdonnee()==5) {
				ordonneeFrame = 393;
			} else if (c.getOrdonnee()==6) {
				ordonneeFrame = 322;
			} else if (c.getOrdonnee()==7) {
				ordonneeFrame = 252;
			} else if (c.getOrdonnee()==8) {
				ordonneeFrame = 181;
			} else if (c.getOrdonnee()==9) {
				ordonneeFrame = 110;
			} else {
				ordonneeFrame = 39;
			}
			p.setBounds(abcisseFrame,ordonneeFrame,65,75); //On positionne le panel en position "abcisseFrame", "ordonn�eFrame", ce panel fait 65 large, et 75 de haut
		}
	}
	
	/**
	 * M�thode qui permet d'afficher toutes les possibilit�s de d�placement du pion selectionn�, en affichant les cases possibles en orange
	 * @param pion : le pion que l'on a selectionn�
	 * @param plat : le plateau sur lequel on joue
	 * @throws IOException : peut d�clencher une erreur si l'image de case orange n'existe pas, par exemple
	 */
	public void showMove(Pion pion, Plateau plat) throws IOException {	
		int movePossible=0; //le nombre de d�placement(s) possible(s)
		int eatPossible=0; //le nombre de "mangement(s)" possible(s)
		for (int i=0; i<plat.getCases().length; i++) { //on v�rifie toutes les cases une par une pour conna�tre la liste de tous les d�placements/"mangements" possibles
			if (!plat.getCases(i).pionPresent()) { //s'il y a un pion sur la case i, pas besoin de la v�rifier, le d�placement/"mangement" est impossible
				if (pion.getPartie().getMange()) { //si le pion est en train de manger, il ne peut que manger encore avant de terminer son tour, on ne montre que les "mangements" possibles
					if (this.tryEat(pion, plat.getCases(i), plat)) { //si le pion peut manger, on affiche la case i en orange, on incr�mente le nombre de "mangements" possibles
						plat.getCases(i).caseShown(true);
						eatPossible++;
					}
				} else {
					if (this.tryMove(pion, plat.getCases(i), plat)) { //si le pion peut se d�placer, on affiche la case i en orange, on incr�mente le nombre de d�placements possibles
						plat.getCases(i).caseShown(true);
						movePossible++; 
					} else if (this.tryEat(pion, plat.getCases(i), plat)) { //si le pion peut manger, on affiche la case i en orange, on incr�mente le nombre de "mangements" possibles
						plat.getCases(i).caseShown(true);
						eatPossible++;
					}
				}
			}
		}
		if (movePossible==0 & eatPossible==0) { //si aucun d�placement n'est possible
			pion.getPartie().getMessage().setText("Il n'y a aucun d�placement possible.");
		} else if (movePossible==1 & eatPossible==0) { //si un d�placement est possible
			pion.getPartie().getMessage().setText("Seulement " + movePossible + " d�placement possible."); 
		} else if (movePossible>1 & eatPossible==0) { //si plusieurs d�placements sont possibles
			pion.getPartie().getMessage().setText("Il y a " + movePossible + " d�placements possibles."); 
		} else if (movePossible==0 & eatPossible==1) { //si un "mangement" est possible
			pion.getPartie().getMessage().setText("Seulement " + eatPossible + " mangement possible."); 
		} else if (movePossible==0 & eatPossible>1) { //si plusieurs "mangements" sont possibles
			pion.getPartie().getMessage().setText("Il y a " + eatPossible + " mangements possibles."); 
		} else if (movePossible==1 & eatPossible==1) { //si un d�placement et un "mangement" sont possibles
			pion.getPartie().getMessage().setText(movePossible + " d�placement possible et " + eatPossible + " mangement possible.");
		} else if (movePossible>1 & eatPossible==1) { //si plusieurs d�placements et un "mangement" sont possibles
			pion.getPartie().getMessage().setText(movePossible + " d�placements possibles et " + eatPossible + " mangement possible.");
		} else if (movePossible==1 & eatPossible>1) { //si un d�placement et plusieurs "mangements" sont possibles
			pion.getPartie().getMessage().setText(movePossible + " d�placement possible et " + eatPossible + " mangements possibles.");
		} else if (movePossible>1 & eatPossible>1) { //si plusieurs d�placements et plusieurs "mangements" sont possibles
			pion.getPartie().getMessage().setText(movePossible + " d�placements possibles et " + eatPossible + " mangements possibles.");
		}
	}
	
	/**
	 * M�thode qui permet de remettre � z�ros la/les case(s) orange(s)
	 * @param plat : le plateau sur lequel se d�roule la partie
	 * @throws IOException : peut d�clencher une erreur si l'image de case vide n'existe pas, par exemple
	 */
	public void resetCasesVides(Plateau plat) throws IOException {
		for (int i=0; i<plat.getCases().length; i++) { //on v�rifie toutes les cases une par une pour conna�tre la liste des cases oranges
			if (plat.getCases(i).getBouton().getName().equals("caseShown")) { //si la case est en orange, on la r�initialise
				plat.getCases(i).caseShown(false);
			}				
		}
	}
	
	/**
	 * M�thode qui permet � un bot de se d�placer, s'il peut souffler il souffle, ensuite s'il peut manger il mange, enfin s'il peut se d�placer il se d�placer
	 * @param bot : le "joueur" bot qui doit jouer son tour
	 * @param plat : le plateau sur lequel se d�roule la partie
	 * @return true d�s qu'il a trouv� son d�placement, return false si aucun pion ne peut �tre d�plac�
	 * @throws IOException : peut d�clencher une erreur si l'image d'un pion ou d'une case vide n'existe pas, par exemple
	 */
	public boolean botMove(Joueur bot, Plateau plat) throws IOException {
		int nbPions = 0; //le nombre de pions blancs ou noirs existant au total sur le plateau
		if (plat.getTaille()==8) {
			nbPions = 12;
		} else if (plat.getTaille()==10) {
			nbPions = 20;
		} else {
			return false;
		}

		boolean canEat = true; //si aucun "mangement" n'est possible, canEat deviendra false
		
		int[] triBlancsCroissant = new int[nbPions]; //le tableau qui classe les pions blancs dans l'ordre croissant en fonction de leur position sur le plateau
			
		//on essaye de trouver le pion blanc le plus avanc� sur le plateau (le plus haut)
		for (int i=0; i<nbPions; i++) { //on v�rifie tous les pions un par un pour leur attribuer un score en fonction de leur position sur le plateau
			if (plat.getPionsBlancs(i).isAlive()) { //si le pion est encore en jeu
				String abcissePion;
				int scorePion = 0;
				abcissePion = plat.getPionsBlancs(i).getCase().getAbcisse();
				scorePion = calculate(abcissePion); //on traduit l'abcisse du pion en nombre entier, par exemple "A"=0, "B"=1, "C"=2 etc...
				scorePion++; //on incr�mente le score du pion pour qu'il ne puisse pas �tre �gal � 0
				int ordonneePion = plat.getPionsBlancs(i).getCase().getOrdonnee(); 
				ordonneePion = ordonneePion*10; //on multiplie par 10 l'ordonn�e du pion
				scorePion = scorePion + ordonneePion; //on ajoute le score "ordonn�ePion" au score du pion, par exemple "A1"=11, "B2"=22, "A3"=31 etc...
				if (plat.getPionsBlancs(i).isDame()) { //si le pion est une dame, on veut le jouer en priorit�, donc on ajoute 100 � son score
					scorePion = scorePion + 100;
				}
				plat.getPionsBlancs(i).setScore(scorePion); //on attribue le score calcul� au pion
				triBlancsCroissant[i] = scorePion; //on attribute le score calcul� � la position i du tableau
			} else { //si le pion est mort, on lui attribue une valeur tr�s �lev�e pour �tre s�r de ne pas le v�rifier en dernier
				triBlancsCroissant[i] = 1000;
			}
		}
		Arrays.sort(triBlancsCroissant); //on trie le tableau "triBlancsCroissant" dans l'ordre croissant
		int[] triBlancs = new int[nbPions];	//le tableau qui classe les pions blancs dans l'ordre d�croissant en fonction de leur position sur le plateau
		for (int i=0; i<nbPions; i++) { //on parcourt les scores des pions blancs 1 par 1
			triBlancs[i] = triBlancsCroissant[nbPions-1-i]; //on inverse l'ordre (croissant / d�croissant)
		}
		//On a la liste tri�e dans l'ordre d�croissant des scores des pions blancs
		//On va jouer en priorit� les pions qui ont le plus gros score
		//D'abord les dames, ensuite les pions les plus hauts du plateau, en dernier les pions les plus bas du plateau
		
		int[] triNoirs = new int[nbPions]; //le tableau qui classe les pions noirs dans l'ordre croissant en fonction de leur position sur le plateau
		int nbDames=0;
		
		//on essaye de trouver le pion noir le plus avanc� sur le plateau (le plus bas)
		for (int i=0; i<nbPions; i++) {  //on v�rifie tous les pions un par un pour leur attribuer un score en fonction de leur position sur le plateau
			if (plat.getPionsNoirs(i).isAlive()) { //si le pion est encore en jeu
				String abcissePion;
				int scorePion = 0;
				abcissePion = plat.getPionsNoirs(i).getCase().getAbcisse(); 
				scorePion = calculate(abcissePion); //on traduit l'abcisse du pion en nombre entier, par exemple "A"=0, "B"=1, "C"=2 etc...
				scorePion++; //on incr�mente le score du pion pour qu'il ne puisse pas �tre �gal � 0
				int ordonneePion = plat.getPionsNoirs(i).getCase().getOrdonnee();
				ordonneePion = ordonneePion*10; //on multiplie par 10 l'ordonn�e du pion
				scorePion = scorePion + ordonneePion; //on ajoute le score "ordonn�ePion" au score du pion, par exemple "A1"=11, "B2"=22, "A3"=31 etc...
				if (plat.getPionsNoirs(i).isDame()) { //si le pion est une dame, on veut le jouer en priorit�, donc on descend son score � 1 minimum
					nbDames++; //on incr�mente le nombre de dames pour que plusieurs pions ne puissent pas avoir le m�me score
					scorePion = nbDames;
				}
				plat.getPionsNoirs(i).setScore(scorePion); //on attribue le score calcul� au pion
				triNoirs[i] = scorePion; //on attribute le score calcul� � la position i du tableau
			} else { //si le pion est mort, on lui attribue la valeur de 0 pour �tre s�r de ne pas le v�rifier en dernier
				triNoirs[i] = 0;
			}
		}
		Arrays.sort(triNoirs);
		//On a la liste tri�e dans l'ordre croissant des scores des pions noirs
		//On va jouer en priorit� les pions qui ont le plus petit score
		//D'abord les dames, ensuite les pions les plus bas du plateau, en dernier les pions les plus haut du plateau	
		
		boolean souffle = bot.getPartie().getSouffle(); //la variable qui va nous emp�cher de souffler plusieurs pions au m�me tour, sera true quand un pion sera souffl�
		
		if (bot.getCouleur().equals("blanc")) { //si le joueur est blanc
			if (bot.getPartie().getNbTour()==1) {
				//on ne peut pas souffler de pion au tour 1
			} else {
				for (int i=0; i<nbPions; i++) { //on essaye de souffler tous les pions adverses un par un
					for (int j=0; j<plat.getCases().length; j++) { //pour chaque pion, on essaye toutes les cases du plateau une par une
						if (!souffle) { //si aucun pion n'a �t� souffl� pendant ce tour
							if (plat.getScoreNoir(triNoirs[i]).isAlive()) { //si le pion i est encore en jeu, sinon on ne fait rien
								if (bot.getPartie().getChance()==0) { //si aucun pion n'a mang� au tour pr�c�dent
									if (plat.getScoreNoir(triNoirs[i]).equals(bot.getPartie().getPionDeplace())) {
										//on ne peut pas souffler le pion qui vient de se d�placer, sauf s'il vient de manger
									} else {
										if (plat.getCasesReverse(j).equals(bot.getPartie().getCaseDevenueVide())) {
											//on ne peut pas souffler un pion qui ne pouvait pas manger avant que la case ne se soit vid�e
										} else { //si toutes les conditions sont r�unies pour souffler le pion i
											if (this.tryEat(plat.getScoreNoir(triNoirs[i]), plat.getCasesReverse(j), plat)) { //si on peut souffler le pion i, on le souffle, sinon on ne fait rien
												bot.souffler(plat.getScoreNoir(triNoirs[i]));
												souffle = true;
												bot.getPartie().setSouffle(souffle); //maintenant que le pion i a �t� souffl�, on ne peut pas en souffler un autre pendant ce tour
												return true;
											}
										}
									}
								} else if (plat.getScoreNoir(triNoirs[i]).equals(bot.getPartie().getPionDeplace()) & bot.getPartie().getChance()==3) { //si ce pion a mang� au tour pr�c�dent
									if (this.tryEat(plat.getScoreNoir(triNoirs[i]), plat.getCasesReverse(j), plat)) { //si on peut souffler ce pion, on le souffle, sinon on ne fait rien
										bot.souffler(plat.getScoreNoir(triNoirs[i]));
										souffle = true;
										bot.getPartie().setSouffle(souffle); //maintenant que le pion i a �t� souffl�, on ne peut pas en souffler un autre pendant ce tour
										return true;
									}
								}
							}
						}
					}
				}
			}
			
			for (int i=0; i<nbPions; i++) { //on essaye de jouer tous nos pions un par un
				for (int j=0; j<plat.getCases().length; j++) { //pour chaque pion, on essaye toutes les cases du plateau une par une
					if (plat.getScoreBlanc(triBlancs[i]).isAlive()) { //si le pion qu'on essaye de jouer est encore en jeu, sinon on ne fait rien
						if (i%2==0) { //si i est pair
							if (canEat) { //s'il reste des "mangements" � v�rifier
								if (this.tryEat(plat.getScoreBlanc(triBlancs[i]), plat.getCasesReverse(j), plat)) { //si le pion i peut manger en se d�placent sur la case j, il mange, sinon on ne fait rien
									bot.eat(plat.getScoreBlanc(triBlancs[i]), plat.getCasesReverse(j)); //le pion i mange un pion adverse en se d�placant sur la case j
									bot.getPartie().setMange(true);
									bot.getPartie().setKiller(plat.getScoreBlanc(triBlancs[i]));
									if (plat.getScoreBlanc(triBlancs[i]).isDevenuDame()) {
										plat.getScoreBlanc(triBlancs[i]).setDevenuDame(false);
										bot.getPartie().setMange(false);
	    								bot.getPartie().finDeTour();
	    								bot.getPartie().setChance(0);
	    							} else {
	    								bot.getPartie().getMessage().setText("Le joueur " + bot.getCouleur() + " peut-il manger un autre pion ? Non.");
	    							}
									return true;
								} else {
									if (i==nbPions-1 & j==plat.getCases().length-1) {
										if (this.mirroir(bot, plat)) {
											return true;
										}
										canEat = false;
										i=0;
										j=0;
									}
								}
							} else {
								if (plat.getScoreBlanc(triBlancs[i]).isDame()) {
									if (this.tryMove(plat.getScoreBlanc(triBlancs[i]), plat.getCaseG5(), plat)) {
										bot.move(plat.getScoreBlanc(triBlancs[i]), plat.getCaseG5());
										bot.getPartie().setChance(0);
										bot.getPartie().setPionClique(plat.getScoreBlanc(triBlancs[i]));
										bot.getPartie().finDeTour();
									} else if (this.tryMove(plat.getScoreBlanc(triBlancs[i]), plat.getCaseD4(), plat)) {
										bot.move(plat.getScoreBlanc(triBlancs[i]), plat.getCaseD4());
										bot.getPartie().setChance(0);
										bot.getPartie().setPionClique(plat.getScoreBlanc(triBlancs[i]));
										bot.getPartie().finDeTour();
									} else if (this.tryMove(plat.getScoreBlanc(triBlancs[i]), plat.getCaseF4(), plat)) {
										bot.move(plat.getScoreBlanc(triBlancs[i]), plat.getCaseF4());
										bot.getPartie().setChance(0);
										bot.getPartie().setPionClique(plat.getScoreBlanc(triBlancs[i]));
										bot.getPartie().finDeTour();
									} else if (this.tryMove(plat.getScoreBlanc(triBlancs[i]), plat.getCaseD6(), plat)) {
										bot.move(plat.getScoreBlanc(triBlancs[i]), plat.getCaseD6());
										bot.getPartie().setChance(0);
										bot.getPartie().setPionClique(plat.getScoreBlanc(triBlancs[i]));
										bot.getPartie().finDeTour();
									} else if (this.tryMove(plat.getScoreBlanc(triBlancs[i]), plat.getCaseH6(), plat)) {
										bot.move(plat.getScoreBlanc(triBlancs[i]), plat.getCaseH6());
										bot.getPartie().setChance(0);
										bot.getPartie().setPionClique(plat.getScoreBlanc(triBlancs[i]));
										bot.getPartie().finDeTour();
									}
								} else {
									if (this.tryMove(plat.getScoreBlanc(triBlancs[i]), plat.getCasesReverse(j), plat)) {
										bot.move(plat.getScoreBlanc(triBlancs[i]), plat.getCasesReverse(j));
										bot.getPartie().setChance(0);
										bot.getPartie().setPionClique(plat.getScoreBlanc(triBlancs[i]));
										bot.getPartie().finDeTour();
										return true;
									} else {
										if (i==nbPions-1 & j==plat.getCases().length-1) {
											bot.getPartie().getMessage().setText("Aucun mouvement possible, " + bot.getName() + " est contraint d'abandonner.");
											if (bot.equals(bot.getPartie().getJoueur1())) {
												bot.getPartie().victory(bot.getPartie().getJoueur2());
											} else {
												bot.getPartie().victory(bot.getPartie().getJoueur1());
											}
											return false;
										}
									}
								}
							}
						} else { //si i est impair
							if (canEat) { //s'il reste des "mangements" � v�rifier
								if (this.tryEat(plat.getScoreBlanc(triBlancs[i]), plat.getCases(j), plat)) { //si le pion i peut manger en se d�placent sur la case j, il mange, sinon on ne fait rien
									bot.eat(plat.getScoreBlanc(triBlancs[i]), plat.getCases(j)); //le pion i mange un pion adverse en se d�placant sur la case j
									bot.getPartie().setMange(true);
									bot.getPartie().setKiller(plat.getScoreBlanc(triBlancs[i]));
									if (plat.getScoreBlanc(triBlancs[i]).isDevenuDame()) {
										plat.getScoreBlanc(triBlancs[i]).setDevenuDame(false);
										bot.getPartie().setMange(false);
	    								bot.getPartie().finDeTour();
	    								bot.getPartie().setChance(0);
	    							} else {
	    								bot.getPartie().getMessage().setText("Le joueur " + bot.getCouleur() + " peut-il manger un autre pion ?");
	    							}
									return true;
								} else {
									if (i==nbPions-1 & j==plat.getCases().length-1) {
										canEat = false;
										i=0;
										j=0;
									}
								}
							} else {
								if (plat.getScoreBlanc(triBlancs[i]).isDame()) {
									if (this.tryMove(plat.getScoreBlanc(triBlancs[i]), plat.getCaseH6(), plat)) {
										bot.move(plat.getScoreBlanc(triBlancs[i]), plat.getCaseH6());
										bot.getPartie().setChance(0);
										bot.getPartie().setPionClique(plat.getScoreBlanc(triBlancs[i]));
										bot.getPartie().finDeTour();
										return true;
									} else if (this.tryMove(plat.getScoreBlanc(triBlancs[i]), plat.getCaseF6(), plat)) {
										bot.move(plat.getScoreBlanc(triBlancs[i]), plat.getCaseF6());
										bot.getPartie().setChance(0);
										bot.getPartie().setPionClique(plat.getScoreBlanc(triBlancs[i]));
										bot.getPartie().finDeTour();
										return true;
									} else if (this.tryMove(plat.getScoreBlanc(triBlancs[i]), plat.getCaseG7(), plat)) {
										bot.move(plat.getScoreBlanc(triBlancs[i]), plat.getCaseG7());
										bot.getPartie().setChance(0);
										bot.getPartie().setPionClique(plat.getScoreBlanc(triBlancs[i]));
										bot.getPartie().finDeTour();
										return true;
									} else if (this.tryMove(plat.getScoreBlanc(triBlancs[i]), plat.getCaseD4(), plat)) {
										bot.move(plat.getScoreBlanc(triBlancs[i]), plat.getCaseD4());
										bot.getPartie().setChance(0);
										bot.getPartie().setPionClique(plat.getScoreBlanc(triBlancs[i]));
										bot.getPartie().finDeTour();
										return true;
									} else if (this.tryMove(plat.getScoreBlanc(triBlancs[i]), plat.getCaseG5(), plat)) {
										bot.move(plat.getScoreBlanc(triBlancs[i]), plat.getCaseG5());
										bot.getPartie().setChance(0);
										bot.getPartie().setPionClique(plat.getScoreBlanc(triBlancs[i]));
										bot.getPartie().finDeTour();
										return true;
									} else if (this.tryMove(plat.getScoreBlanc(triBlancs[i]), plat.getCaseF4(), plat)) {
										bot.move(plat.getScoreBlanc(triBlancs[i]), plat.getCaseF4());
										bot.getPartie().setChance(0);
										bot.getPartie().setPionClique(plat.getScoreBlanc(triBlancs[i]));
										bot.getPartie().finDeTour();
										return true;
									}  else if (this.tryMove(plat.getScoreBlanc(triBlancs[i]), plat.getCaseD6(), plat)) {
										bot.move(plat.getScoreBlanc(triBlancs[i]), plat.getCaseD6());
										bot.getPartie().setChance(0);
										bot.getPartie().setPionClique(plat.getScoreBlanc(triBlancs[i]));
										bot.getPartie().finDeTour();
										return true;
									}
								} else {
									if (this.tryMove(plat.getScoreBlanc(triBlancs[i]), plat.getCases(j), plat)) {
										bot.move(plat.getScoreBlanc(triBlancs[i]), plat.getCases(j));
										bot.getPartie().setChance(0);
										bot.getPartie().setPionClique(plat.getScoreBlanc(triBlancs[i]));
										bot.getPartie().finDeTour();
										return true;
									} else {
										if (i==nbPions-1 & j==plat.getCases().length-1) {
											bot.getPartie().getMessage().setText("Aucun mouvement possible, " + bot.getName() + " est contraint d'abandonner.");
											if (bot.equals(bot.getPartie().getJoueur1())) {
												bot.getPartie().victory(bot.getPartie().getJoueur2());
											} else {
												bot.getPartie().victory(bot.getPartie().getJoueur1());
											}
											return false;
										}
									}
								}
							}
						}
					}
				}
			}
		} else { //si le joueur est noir
			for (int i=0; i<nbPions; i++) {
				for (int j=0; j<plat.getCases().length; j++) {
					if (!souffle) { //on ne peut souffler qu'un pion par tour
						if (plat.getScoreBlanc(triBlancs[i]).isAlive()) {
							if (bot.getPartie().getChance()==0) { //si aucun pion n'a mang� au tour pr�c�dent
								if (plat.getScoreBlanc(triBlancs[i]).equals(bot.getPartie().getPionDeplace())) {
									//on ne peut pas souffler le pion qui vient de se d�placer, sauf s'il vient de manger
								} else {
									if (plat.getCases(j).equals(bot.getPartie().getCaseDevenueVide())) {
										//on ne peut pas souffler un pion qui ne pouvait pas manger avant de vider la case
									} else {
										if (this.tryEat(plat.getScoreBlanc(triBlancs[i]), plat.getCases(j), plat)) {
											bot.souffler(plat.getScoreBlanc(triBlancs[i]));
											souffle = true;
											bot.getPartie().setSouffle(souffle); //maintenant que le pion i a �t� souffl�, on ne peut pas en souffler un autre pendant ce tour
											return true;
										}
									}
								}
							} else if (plat.getScoreBlanc(triBlancs[i]).equals(bot.getPartie().getPionDeplace()) & bot.getPartie().getChance()==3) { //si ce pion a mang� au tour pr�c�dent
								if (plat.getCases(j).equals(bot.getPartie().getCaseDevenueVide())) {
									//on ne peut pas souffler un pion qui ne pouvait pas manger avant de vider la case
								} else {
									if (this.tryEat(plat.getScoreBlanc(triBlancs[i]), plat.getCases(j), plat)) {
										bot.souffler(plat.getScoreBlanc(triBlancs[i]));
										souffle = true;
										bot.getPartie().setSouffle(souffle); //maintenant que le pion i a �t� souffl�, on ne peut pas en souffler un autre pendant ce tour
										return true;
									}
								}
							}
						}
					}
				}
			}
				
			for (int i=0; i<nbPions; i++) {
				for (int j=0; j<plat.getCases().length; j++) {
					if (plat.getScoreNoir(triNoirs[i]).isAlive()) {
						if (canEat) {
							if (this.tryEat(plat.getScoreNoir(triNoirs[i]), plat.getCases(j), plat)) {
								bot.eat(plat.getScoreNoir(triNoirs[i]), plat.getCases(j));
								bot.getPartie().setMange(true);
								bot.getPartie().setKiller(plat.getScoreNoir(triNoirs[i]));
								if (plat.getScoreNoir(triNoirs[i]).isDevenuDame()) {
									plat.getScoreNoir(triNoirs[i]).setDevenuDame(false);
									bot.getPartie().setMange(false);
    								bot.getPartie().finDeTour();
    								bot.getPartie().setChance(0);
    							} else {
    								bot.getPartie().getMessage().setText("Le joueur " + bot.getCouleur() + " peut-il manger un autre pion ?");
    							}
								return true;
							} else {
								if (i==nbPions-1 & j==plat.getCases().length-1) {
									if (this.mirroir(bot, plat)) {
										return true;
									}
									canEat = false;
									i=0;
									j=0;
								}
							}
						} else {
							if (this.tryMove(plat.getScoreNoir(triNoirs[i]), plat.getCaseH6(), plat)) {
								bot.move(plat.getScoreNoir(triNoirs[i]), plat.getCaseH6());
								bot.getPartie().setChance(0);
								bot.getPartie().setPionClique(plat.getScoreNoir(triNoirs[i]));
								bot.getPartie().finDeTour();
								return true;
							} else if (this.tryMove(plat.getScoreNoir(triNoirs[i]), plat.getCaseE7(), plat)) {
								bot.move(plat.getScoreNoir(triNoirs[i]), plat.getCaseE7());
								bot.getPartie().setChance(0);
								bot.getPartie().setPionClique(plat.getScoreNoir(triNoirs[i]));
								bot.getPartie().finDeTour();
								return true;
							} else if (this.tryMove(plat.getScoreNoir(triNoirs[i]), plat.getCaseG7(), plat)) {
								bot.move(plat.getScoreNoir(triNoirs[i]), plat.getCaseG7());
								bot.getPartie().setChance(0);
								bot.getPartie().setPionClique(plat.getScoreNoir(triNoirs[i]));
								bot.getPartie().finDeTour();
								return true;
							} else if (this.tryMove(plat.getScoreNoir(triNoirs[i]), plat.getCaseD4(), plat)) {
								bot.move(plat.getScoreNoir(triNoirs[i]), plat.getCaseD4());
								bot.getPartie().setChance(0);
								bot.getPartie().setPionClique(plat.getScoreNoir(triNoirs[i]));
								bot.getPartie().finDeTour();
								return true;
							} else if (this.tryMove(plat.getScoreNoir(triNoirs[i]), plat.getCaseF4(), plat)) {
								bot.move(plat.getScoreNoir(triNoirs[i]), plat.getCaseF4());
								bot.getPartie().setChance(0);
								bot.getPartie().setPionClique(plat.getScoreNoir(triNoirs[i]));
								bot.getPartie().finDeTour();
								return true;
							} else if (this.tryMove(plat.getScoreNoir(triNoirs[i]), plat.getCaseG5(), plat)) {
								bot.move(plat.getScoreNoir(triNoirs[i]), plat.getCaseG5());
								bot.getPartie().setChance(0);
								bot.getPartie().setPionClique(plat.getScoreNoir(triNoirs[i]));
								bot.getPartie().finDeTour();
								return true;
							} else if (this.tryMove(plat.getScoreNoir(triNoirs[i]), plat.getCaseD6(), plat)) {
								bot.move(plat.getScoreNoir(triNoirs[i]), plat.getCaseD6());
								bot.getPartie().setChance(0);
								bot.getPartie().setPionClique(plat.getScoreNoir(triNoirs[i]));
								bot.getPartie().finDeTour();
								return true;
							} else {
								if (this.tryMove(plat.getScoreNoir(triNoirs[i]), plat.getCases(j), plat)) {
									bot.move(plat.getScoreNoir(triNoirs[i]), plat.getCases(j));
									bot.getPartie().setChance(0);
									bot.getPartie().setPionClique(plat.getScoreNoir(triNoirs[i]));
									bot.getPartie().finDeTour();
									return true;
								} else {
									if (i==nbPions-1 & j==plat.getCases().length-1) {
										bot.getPartie().getMessage().setText("Aucun mouvement possible, " + bot.getName() + " est contraint d'abandonner.");
										if (bot.equals(bot.getPartie().getJoueur1())) {
											bot.getPartie().victory(bot.getPartie().getJoueur2());
										} else {
											bot.getPartie().victory(bot.getPartie().getJoueur1());
										}
										return false;
									}
								}
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	private boolean mirroir (Joueur bot, Plateau plat) throws IOException {
		if (bot.getCouleur().equals("blanc") & bot.getPartie().getNbTour()==1) {
			return false;
		}
		Case caseACopier = bot.getPartie().getCaseDevenueVide();
		Case deplacementACopier = bot.getPartie().getPionDeplace().getCase();
		String abcisseCaseCopiee;
		int ordonneeCaseCopiee;
		String abcisseDeplacementCopie;
		int ordonneeDeplacementCopie;
		if (plat.getTaille()==8) { //si le plateau fait 8*8
			if (caseACopier.getAbcisse().equals("A")) {
				abcisseCaseCopiee = "H";
			} else if (caseACopier.getAbcisse().equals("B")) {
				abcisseCaseCopiee = "G";
			} else if (caseACopier.getAbcisse().equals("C")) {
				abcisseCaseCopiee = "F";
			} else if (caseACopier.getAbcisse().equals("D")) {
				abcisseCaseCopiee = "E";
			} else if (caseACopier.getAbcisse().equals("E")) {
				abcisseCaseCopiee = "D";
			} else if (caseACopier.getAbcisse().equals("F")) {
				abcisseCaseCopiee = "C";
			} else if (caseACopier.getAbcisse().equals("G")) {
				abcisseCaseCopiee = "B";
			} else {
				abcisseCaseCopiee = "A";
			}
			if (caseACopier.getOrdonnee() == 1) {
				ordonneeCaseCopiee = 8;
			} else if (caseACopier.getOrdonnee() == 2) {
				ordonneeCaseCopiee = 7;
			} else if (caseACopier.getOrdonnee() == 3) {
				ordonneeCaseCopiee = 6;
			} else if (caseACopier.getOrdonnee() == 4) {
				ordonneeCaseCopiee = 5;
			} else if (caseACopier.getOrdonnee() == 5) {
				ordonneeCaseCopiee = 4;
			} else if (caseACopier.getOrdonnee() == 6) {
				ordonneeCaseCopiee = 3;
			} else if (caseACopier.getOrdonnee() == 7) {
				ordonneeCaseCopiee = 2;
			} else {
				ordonneeCaseCopiee = 1;
			}
			if (deplacementACopier.getAbcisse().equals("A")) {
				abcisseDeplacementCopie = "H";
			} else if (deplacementACopier.getAbcisse().equals("B")) {
				abcisseDeplacementCopie = "G";
			} else if (deplacementACopier.getAbcisse().equals("C")) {
				abcisseDeplacementCopie = "F";
			} else if (deplacementACopier.getAbcisse().equals("D")) {
				abcisseDeplacementCopie = "E";
			} else if (deplacementACopier.getAbcisse().equals("E")) {
				abcisseDeplacementCopie = "D";
			} else if (deplacementACopier.getAbcisse().equals("F")) {
				abcisseDeplacementCopie = "C";
			} else if (deplacementACopier.getAbcisse().equals("G")) {
				abcisseDeplacementCopie = "B";
			} else {
				abcisseDeplacementCopie = "A";
			}
			if (deplacementACopier.getOrdonnee() == 1) {
				ordonneeDeplacementCopie = 8;
			} else if (deplacementACopier.getOrdonnee() == 2) {
				ordonneeDeplacementCopie = 7;
			} else if (deplacementACopier.getOrdonnee() == 3) {
				ordonneeDeplacementCopie = 6;
			} else if (deplacementACopier.getOrdonnee() == 4) {
				ordonneeDeplacementCopie = 5;
			} else if (deplacementACopier.getOrdonnee() == 5) {
				ordonneeDeplacementCopie = 4;
			} else if (deplacementACopier.getOrdonnee() == 6) {
				ordonneeDeplacementCopie = 3;
			} else if (deplacementACopier.getOrdonnee() == 7) {
				ordonneeDeplacementCopie = 2;
			} else {
				ordonneeDeplacementCopie = 1;
			}
		} else { //si le plateau fait 10*10
			if (caseACopier.getAbcisse().equals("A")) {
				abcisseCaseCopiee = "J";
			} else if (caseACopier.getAbcisse().equals("B")) {
				abcisseCaseCopiee = "I";
			} else if (caseACopier.getAbcisse().equals("C")) {
				abcisseCaseCopiee = "H";
			} else if (caseACopier.getAbcisse().equals("D")) {
				abcisseCaseCopiee = "G";
			} else if (caseACopier.getAbcisse().equals("E")) {
				abcisseCaseCopiee = "F";
			} else if (caseACopier.getAbcisse().equals("F")) {
				abcisseCaseCopiee = "E";
			} else if (caseACopier.getAbcisse().equals("G")) {
				abcisseCaseCopiee = "D";
			} else if (caseACopier.getAbcisse().equals("H")) {
				abcisseCaseCopiee = "C";
			} else if (caseACopier.getAbcisse().equals("I")) {
				abcisseCaseCopiee = "B";
			} else {
				abcisseCaseCopiee = "A";
			}
			if (caseACopier.getOrdonnee() == 1) {
				ordonneeCaseCopiee = 10;
			} else if (caseACopier.getOrdonnee() == 2) {
				ordonneeCaseCopiee = 9;
			} else if (caseACopier.getOrdonnee() == 3) {
				ordonneeCaseCopiee = 8;
			} else if (caseACopier.getOrdonnee() == 4) {
				ordonneeCaseCopiee = 7;
			} else if (caseACopier.getOrdonnee() == 5) {
				ordonneeCaseCopiee = 6;
			} else if (caseACopier.getOrdonnee() == 6) {
				ordonneeCaseCopiee = 5;
			} else if (caseACopier.getOrdonnee() == 7) {
				ordonneeCaseCopiee = 4;
			} else if (caseACopier.getOrdonnee() == 8) {
				ordonneeCaseCopiee = 3;
			} else if (caseACopier.getOrdonnee() == 9) {
				ordonneeCaseCopiee = 2;
			} else {
				ordonneeCaseCopiee = 1;
			}
			if (deplacementACopier.getAbcisse().equals("A")) {
				abcisseDeplacementCopie = "J";
			} else if (deplacementACopier.getAbcisse().equals("B")) {
				abcisseDeplacementCopie = "I";
			} else if (deplacementACopier.getAbcisse().equals("C")) {
				abcisseDeplacementCopie = "H";
			} else if (deplacementACopier.getAbcisse().equals("D")) {
				abcisseDeplacementCopie = "G";
			} else if (deplacementACopier.getAbcisse().equals("E")) {
				abcisseDeplacementCopie = "F";
			} else if (deplacementACopier.getAbcisse().equals("F")) {
				abcisseDeplacementCopie = "E";
			} else if (deplacementACopier.getAbcisse().equals("G")) {
				abcisseDeplacementCopie = "D";
			} else if (deplacementACopier.getAbcisse().equals("H")) {
				abcisseDeplacementCopie = "C";
			} else if (deplacementACopier.getAbcisse().equals("I")) {
				abcisseDeplacementCopie = "B";
			} else {
				abcisseDeplacementCopie = "A";
			}
			if (deplacementACopier.getOrdonnee() == 1) {
				ordonneeDeplacementCopie = 10;
			} else if (deplacementACopier.getOrdonnee() == 2) {
				ordonneeDeplacementCopie = 9;
			} else if (deplacementACopier.getOrdonnee() == 3) {
				ordonneeDeplacementCopie = 8;
			} else if (deplacementACopier.getOrdonnee() == 4) {
				ordonneeDeplacementCopie = 7;
			} else if (deplacementACopier.getOrdonnee() == 5) {
				ordonneeDeplacementCopie = 6;
			} else if (deplacementACopier.getOrdonnee() == 6) {
				ordonneeDeplacementCopie = 5;
			} else if (deplacementACopier.getOrdonnee() == 7) {
				ordonneeDeplacementCopie = 4;
			} else if (deplacementACopier.getOrdonnee() == 8) {
				ordonneeDeplacementCopie = 3;
			} else if (deplacementACopier.getOrdonnee() == 9) {
				ordonneeDeplacementCopie = 2;
			} else {
				ordonneeDeplacementCopie = 1;
			}
		}
		if (bot.getCouleur().equals("blanc")) {
			if (plat.getCase(abcisseCaseCopiee + ordonneeCaseCopiee).pionBlancPresent()) {
				if (this.tryEat(plat.getCase(abcisseCaseCopiee + ordonneeCaseCopiee).getPion(), plat.getCase(abcisseDeplacementCopie + ordonneeDeplacementCopie), plat)) {
					bot.getPartie().getMessage().setText("c'est jamais sense arriver car on test tous les mangements du plateau avant de try ca");
					return true;
				} else if (this.tryMove(plat.getCase(abcisseCaseCopiee + ordonneeCaseCopiee).getPion(), plat.getCase(abcisseDeplacementCopie + ordonneeDeplacementCopie), plat)) {
					bot.move(plat.getCase(abcisseCaseCopiee + ordonneeCaseCopiee).getPion(), plat.getCase(abcisseDeplacementCopie + ordonneeDeplacementCopie));
					bot.getPartie().setChance(0);
					bot.getPartie().setPionClique(plat.getCase(abcisseDeplacementCopie + ordonneeDeplacementCopie).getPion());			
					bot.getPartie().finDeTour();
					return true;
				}
			}
		} else { //si le joueur est noir
			if (plat.getCase(abcisseCaseCopiee + ordonneeCaseCopiee).pionNoirPresent()) {
				if (this.tryEat(plat.getCase(abcisseCaseCopiee + ordonneeCaseCopiee).getPion(), plat.getCase(abcisseDeplacementCopie + ordonneeDeplacementCopie), plat)) {
					bot.getPartie().getMessage().setText("c'est jamais sense arriver car on test tous les mangements du plateau avant de try ca");
					return true;
				} else if (this.tryMove(plat.getCase(abcisseCaseCopiee + ordonneeCaseCopiee).getPion(), plat.getCase(abcisseDeplacementCopie + ordonneeDeplacementCopie), plat)) {
					bot.move(plat.getCase(abcisseCaseCopiee + ordonneeCaseCopiee).getPion(), plat.getCase(abcisseDeplacementCopie + ordonneeDeplacementCopie));
					bot.getPartie().setChance(0);
					bot.getPartie().setPionClique(plat.getCase(abcisseDeplacementCopie + ordonneeDeplacementCopie).getPion());			
					bot.getPartie().finDeTour();
					return true;
				}
			}
		}
		return false;
	}
	
	public int calculate (String abcissePion) {
		this.abcisse[0] = "A";
		this.abcisse[1] = "B";
		this.abcisse[2] = "C";
		this.abcisse[3] = "D";
		this.abcisse[4] = "E";
		this.abcisse[5] = "F";
		this.abcisse[6] = "G";
		this.abcisse[7] = "H";
		this.abcisse[8] = "I";
		this.abcisse[9] = "J";
		
		if (abcissePion.equals(abcisse[0])) { //si le pion est sur une case A
			return 0; 
		} else if (abcissePion.equals(abcisse[1])) { //si le pion est sur une case B
			return 1;
		} else if (abcissePion.equals(abcisse[2])) { //si le pion est sur une case C
			return 2;
		} else if (abcissePion.equals(abcisse[3])) { //si le pion est sur une case D
			return 3;
		} else if (abcissePion.equals(abcisse[4])) { //si le pion est sur une case E
			return 4;
		} else if (abcissePion.equals(abcisse[5])) { //si le pion est sur une case F
			return 5;
		} else if (abcissePion.equals(abcisse[6])) { //si le pion est sur une case G
			return 6;
		} else if (abcissePion.equals(abcisse[7])) { //si le pion est sur une case H
			return 7;
		} else if (abcissePion.equals(abcisse[8])) { //si le pion est sur une case I
			return 8;
		} else { //si le pion est sur une case J
			return 9;
		}
	}
	
	/**
	 * @return le dernier pion mang� sur ce plateau
	 */
	public Pion getPionMange() {
		return this.pionMange;
	}
}
