package jeuDeDames;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Partie implements ActionListener, WindowListener {

	private int taille, manche, nbTour, etape, chance;
	private boolean frameExists = false;
	private boolean bot1 = false;
	private boolean bot2 = false;
	private boolean souffle = false;
	private Joueur joueur1, joueur2;
	private String saisie, couleur, score;
	private Plateau plateau;
	private JTextField saisirTexte;
	private JButton pvp, pve, botvsbot, boutonBlanc, boutonNoir, botgo, ff, restart, quit, yesFF, yesRestart, noPseudo, yesQuit, no, boutonRevanche, yesRevanche, noChanger, boutonReset, boutonQuit, logo, yesAide;
	private JLabel message, labelTour, labelDeroulement, labelScore, labelManche;
	private JPanel panelDeroulement;
	private JFrame frameCreation, frameInterface, frameConfirm, endingScreen=new JFrame("");
	private Pion pionClique, pionDeplace, killer;
	private Case caseDevenueVide;
	private Reglement r;
	private boolean victory = false, confirm = false, mange = false, aide = false;
	
	public Partie(int tail) throws IOException {
		if (tail==8 || tail==10) {
			this.taille = tail;
			this.r = new Reglement();
			this.manche=1;
			this.nbTour=1;
			this.etape=0;
			
			this.frameCreation = new JFrame("Creation de la partie");
			this.frameCreation.setLayout(new GridLayout(1,0));
			this.pvp = new JButton("Joueur VS Joueur");
			this.pve = new JButton("Joueur VS Bot");
			this.botvsbot = new JButton("Bot VS Bot");
			JPanel panel = new JPanel();
			panel.add(new JLabel("Quel type de partie voulez-vous jouer ?"));
			JPanel panelConfirm = new JPanel();
			panelConfirm.add(panel);
			panelConfirm.add(pvp);
			panelConfirm.add(pve);
			panelConfirm.add(botvsbot);
			this.frameCreation.add(panelConfirm);
			this.frameCreation.setBounds(315,350,300,190);
			this.frameCreation.setResizable(false);
			this.frameCreation.setAlwaysOnTop(true);
			this.frameCreation.addWindowListener(this);
			this.frameCreation.setVisible(true);
			this.pvp.addActionListener(this);
			this.pve.addActionListener(this);
			this.botvsbot.addActionListener(this);
		} else {
			System.out.println("Je ne sais pas creer un plateau de cette taille. Reessayez avec 8 ou 10.");
		}
	}
	
	public void actionPerformed(ActionEvent event) {
		try {
			if (confirm) {
				if (event.getSource().equals(this.no)) {
					this.frameConfirm.setVisible(false);
					this.confirm = false;
				} else if (event.getSource().equals(this.yesFF)) {
					this.frameConfirm.setVisible(false);
					if (this.aToiDeJouer().equals(this.joueur1)) {
						this.victory(this.joueur2);
					} else {
						this.victory(this.joueur1);
					}
					this.confirm = false;
				} else if (event.getSource().equals(this.yesRestart)) {
					this.frameConfirm.setVisible(false);
					this.confirm = false;
					this.resetScore();
					
					this.frameCreation = new JFrame("Creation de la partie");
					this.frameCreation.setLayout(new GridLayout(1,0));
					JPanel panel = new JPanel();
					panel.add(new JLabel("Quel type de partie voulez-vous jouer ?"));
					JPanel panelConfirm = new JPanel();
					panelConfirm.add(panel);
					panelConfirm.add(pvp);
					panelConfirm.add(pve);
					panelConfirm.add(botvsbot);
					this.frameCreation.add(panelConfirm);
					this.frameCreation.setBounds(315,350,300,190);
					this.frameCreation.setResizable(false);
					this.frameCreation.setAlwaysOnTop(true);
					this.frameCreation.addWindowListener(this);
					this.frameCreation.setVisible(true);
				} else if (event.getSource().equals(this.noPseudo)) {
					this.frameConfirm.setVisible(false);
					this.confirm = false;
					this.resetScore();
					this.joueur1.reset();
					this.joueur2.reset();
					this.play();
				} else if (event.getSource().equals(this.boutonRevanche)) {
					this.revanche(1);
				} else if (event.getSource().equals(boutonReset)) {
					if (this.endingScreen.getTitle().equals("")) {
					} else {
						this.endingScreen.setVisible(false);
					}
					this.frameConfirm = new JFrame("Recommencer");
					this.yesRestart = new JButton("Oui");
					this.noPseudo = new JButton("Non");
					this.no = new JButton("Annuler");
					JPanel panel = new JPanel();
					panel.add(new JLabel("Recreer les joueurs ?"));
					JPanel panelConfirm = new JPanel();
					this.frameConfirm.setLayout(new GridLayout(3,1));
					this.frameConfirm.add(panel);
					panelConfirm.add(yesRestart);
					panelConfirm.add(noPseudo);
					panelConfirm.add(no);
					this.frameConfirm.add(panelConfirm);
					this.frameConfirm.setBounds(315,350,315,150);
					this.frameConfirm.setResizable(false);
					this.frameConfirm.setAlwaysOnTop(true);
					this.frameConfirm.addWindowListener(this);
					this.frameConfirm.setVisible(true);
					this.confirm  = true;
					this.yesRestart.addActionListener(this);
					this.noPseudo.addActionListener(this);
					this.no.addActionListener(this);
				}
				else if (event.getSource().equals(this.yesRevanche)) {
					this.frameConfirm.setVisible(false);
					this.confirm = false;
					this.revanche(2);
				} else if (event.getSource().equals(this.noChanger)) {
					this.frameConfirm.setVisible(false);
					this.confirm = false;
					this.revanche(3);
				} else if (event.getSource().equals(this.yesQuit) || event.getSource().equals(this.boutonQuit)) {
		        	System.out.println("Merci d'avoir jouer au jeu! ");
		        	System.exit(0);
				} else if (event.getSource().equals(this.yesAide)) {
					if (this.aide) {
						this.aide = false;
					} else {
						this.aide = true;
					}
					this.frameConfirm.setVisible(false);
					this.confirm = false;
				}
			} else if (event.getSource().equals(this.ff)) {
				if (this.ff.getText().equals("Revanche !")) {
					this.revanche(1);
				} else {
					this.frameConfirm = new JFrame("Confirmer l'abandon");
					this.yesFF = new JButton("Oui");
					this.no = new JButton("Non");
					JPanel panel = new JPanel();
					panel.add(new JLabel("Conceder la victoire a votre adversaire ?"));
					JPanel panelConfirm = new JPanel();
					this.frameConfirm.setLayout(new GridLayout(2,1));
					this.frameConfirm.add(panel);
					panelConfirm.add(yesFF);
					panelConfirm.add(no);
					this.frameConfirm.add(panelConfirm);
					this.frameConfirm.setBounds(315,350,315,150);
					this.frameConfirm.setResizable(false);
					this.frameConfirm.setAlwaysOnTop(true);
					this.frameConfirm.addWindowListener(this);
					this.frameConfirm.setVisible(true);
					this.confirm  = true;
					this.yesFF.addActionListener(this);
					this.no.addActionListener(this);
				}
			} else if (event.getSource().equals(this.restart) || event.getSource().equals(this.boutonReset)) {
				if (this.endingScreen.getTitle().equals("")) {
				} else {
					this.endingScreen.setVisible(false);
				}
				this.frameConfirm = new JFrame("Recommencer");
				this.yesRestart = new JButton("Oui");
				this.noPseudo = new JButton("Non");
				this.no = new JButton("Annuler");
				JPanel panel = new JPanel();
				panel.add(new JLabel("Recreer les joueurs ?"));
				JPanel panelConfirm = new JPanel();
				this.frameConfirm.setLayout(new GridLayout(3,1));
				this.frameConfirm.add(panel);
				panelConfirm.add(yesRestart);
				panelConfirm.add(noPseudo);
				panelConfirm.add(no);
				this.frameConfirm.add(panelConfirm);
				this.frameConfirm.setBounds(315,350,315,150);
				this.frameConfirm.setResizable(false);
				this.frameConfirm.setAlwaysOnTop(true);
				this.frameConfirm.addWindowListener(this);
				this.frameConfirm.setVisible(true);
				this.confirm  = true;
				this.yesRestart.addActionListener(this);
				this.noPseudo.addActionListener(this);
				this.no.addActionListener(this);
			} else if (event.getSource().equals(this.quit) || event.getSource().equals(this.boutonQuit)) {
				if (this.endingScreen.getTitle().equals("Fin de la partie")) {
		        	System.out.println("Merci d'avoir jouer au jeu! ");
					System.exit(0);
				}
				this.endingScreen.setTitle("");
				this.frameConfirm = new JFrame("Quitter le jeu de dames");
				this.yesQuit = new JButton("Oui");
				this.no = new JButton("Non");
				JPanel panel = new JPanel();
				panel.add(new JLabel("Etes-vous sur de vouloir quitter ?"));
				JPanel panelConfirm = new JPanel();
				this.frameConfirm.setLayout(new GridLayout(2,1));
				this.frameConfirm.add(panel);
				panelConfirm.add(yesQuit);
				panelConfirm.add(no);
				this.frameConfirm.add(panelConfirm);
				this.frameConfirm.setBounds(315,350,315,150);
				this.frameConfirm.setResizable(false);
				this.frameConfirm.setAlwaysOnTop(true);
				this.frameConfirm.addWindowListener(this);
				this.frameConfirm.setVisible(true);
				this.confirm  = true;
				this.yesQuit.addActionListener(this);
				this.no.addActionListener(this);
			} else if (event.getSource().equals(this.logo)) {
				this.frameConfirm = new JFrame("Bulle d'aide");
				if (this.joueur1.isBot()) {
					JLayeredPane layer = new JLayeredPane(); 
					layer.setPreferredSize(new Dimension(25,125));
					
					JLabel labelAide = new JLabel("Vous ne pouvez pas activer l'aide dans un match entre deux bots.");
					JPanel panelAide = new JPanel();
					panelAide.add(labelAide);
					panelAide.setBounds(0,10,440,25);
					layer.add(panelAide, Integer.valueOf(1));
					
					BufferedImage picture = ImageIO.read(getClass().getResource("/data/logoHD.png"));
					this.no = new JButton(new ImageIcon(picture));
					JPanel panelConfirm = new JPanel();
					panelConfirm.add(this.no);
					panelConfirm.setBounds(0,35,450,450);
					layer.add(panelConfirm, Integer.valueOf(0));

					this.frameConfirm.add(layer);
					this.frameConfirm.setBounds(200,150,450,485);
					this.frameConfirm.setResizable(false);
					this.frameConfirm.setAlwaysOnTop(true);
					this.frameConfirm.addWindowListener(this);
					this.frameConfirm.setVisible(true);
					this.confirm  = true;
					this.no.addActionListener(this);
				} else {
					this.yesAide = new JButton("Oui");
					this.no = new JButton("Non");
					JPanel panelConfirm = new JPanel();
					this.frameConfirm.setLayout(new GridLayout(2,1));
					if (this.aide) {
						JPanel panel = new JPanel();
						panel.add(new JLabel("Voulez-vous desactiver l'aide ?"));
						this.frameConfirm.add(panel);
					} else {
						JPanel panel = new JPanel();
						panel.add(new JLabel("Voulez-vous activer l'aide ?"));
						this.frameConfirm.add(panel);
					}
					panelConfirm.add(yesAide);
					panelConfirm.add(no);
					this.frameConfirm.add(panelConfirm);
					this.frameConfirm.setBounds(315,350,315,150);
					this.frameConfirm.setResizable(false);
					this.frameConfirm.setAlwaysOnTop(true);
					this.frameConfirm.addWindowListener(this);
					this.frameConfirm.setVisible(true);
					this.confirm  = true;
					this.yesAide.addActionListener(this);
					this.no.addActionListener(this);
				}
			} else if (event.getSource().equals(this.botgo)) {
				this.playbot();
			} else if (event.getSource().equals(this.boutonRevanche)) {
					this.revanche(1);		
			} else if(this.etape==0) {
				this.frameCreation.setVisible(false);	
				if (event.getSource().equals(this.pvp)) {
					this.bot1 = false;
					this.bot2 = false;
				} else if (event.getSource().equals(this.pve)) {
					this.bot1 = false;
					this.bot2 = true;
				} else if (event.getSource().equals(this.botvsbot)) {
					this.bot1 = true;
					this.bot2 = true;
				}
				
				//Creation du plateau
				if (frameExists) {
					this.frameInterface.remove(plateau.getLayer());
				}
				this.plateau = new Plateau(this, this.taille); //Creation du plateau avec toutes les cases
				this.plateau.createPions(); //Creation des pions
				
				//Creation des joueurs
				this.frameCreation = new JFrame("Creation du joueur 1");
				JPanel panelCreation = new JPanel();
				this.saisirTexte = new JTextField();
				JPanel panel = new JPanel();
				this.message = new JLabel("");
				panel.add(this.message);
				this.frameCreation.setLayout(new GridLayout(4,1));
				panelCreation.add(new JLabel("Joueur 1 : Saisissez votre pseudo"));
				this.frameCreation.add(panelCreation);
				this.frameCreation.add(saisirTexte);
				this.frameCreation.add(panel);
				this.frameCreation.setBounds(350,350,250,125);
				this.frameCreation.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				this.frameCreation.setVisible(true);
				this.saisirTexte.addActionListener(this);
				this.etape = 1;
			} else if (this.etape==1) {
				this.saisie = this.saisirTexte.getText();	
				if (this.creerJoueur1(this.saisie)) {
					this.frameCreation.setVisible(false);	
					this.frameCreation = new JFrame("Choix couleur joueur 1");
					this.boutonBlanc = new JButton("Blanc");
					JPanel panel = new JPanel();
					panel.add(new JLabel("Joueur 1 : Choisissez votre couleur"));
					this.boutonNoir = new JButton("Noir");
					JPanel panelCreation = new JPanel();
					this.frameCreation.setLayout(new GridLayout(3,0));
					this.frameCreation.add(panel);
					panelCreation.add(boutonBlanc);
					panelCreation.add(boutonNoir);
					this.frameCreation.add(panelCreation);
					this.frameCreation.setBounds(350,350,250,150);
					this.frameCreation.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					this.frameCreation.setResizable(false);
					this.frameCreation.setVisible(true);
					this.etape = 2;
					this.boutonBlanc.addActionListener(this);
					this.boutonNoir.addActionListener(this);
				}
			} else if (this.etape==2) {
				if (event.getSource().equals(this.boutonBlanc)) {
					this.joueur1 = new Joueur(this, this.saisie, "blanc", bot1, this.plateau);
				} else {
					this.joueur1 = new Joueur(this, this.saisie, "noir", bot1, this.plateau);
				}
				this.frameCreation.setVisible(false);	
				this.frameCreation = new JFrame("Creation du joueur 2");
				JPanel panel = new JPanel();
				panel.add(new JLabel("Joueur 2 : Saisissez votre pseudo"));
				JPanel panel2 = new JPanel();
				this.message = new JLabel("");
				panel2.add(this.message);
				this.saisirTexte = new JTextField();
				this.message.setText("");
				this.frameCreation.setLayout(new GridLayout(4,1));
				this.frameCreation.add(panel);
				this.frameCreation.add(saisirTexte);
				this.frameCreation.add(panel2);
				this.frameCreation.setBounds(350,350,250,125);
				this.frameCreation.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				this.frameCreation.setResizable(false);
				this.frameCreation.setVisible(true);
				this.etape = 3;
				saisirTexte.addActionListener(this);
			} else if (this.etape==3) {
				this.saisie = this.saisirTexte.getText();	
				if (this.creerJoueur2(this.saisie)) {
					this.frameCreation.setVisible(false);
					this.saisie = this.saisirTexte.getText();
					this.play();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void play() {
		try {			
			//Creation et affichage de la fen�tre de jeu, on y ajoute le layer
			if (frameExists) {	
				//si la fen�tre existe deja, ne rien faire
			} else {
				this.frameInterface = new JFrame("Jeu de Dames");
			}
			this.frameInterface.setResizable(false);
			this.etape = 1;
			this.couleur = "blanc";
			this.labelTour = new JLabel("Tour : " + this.nbTour);
			this.labelTour.setFont(new Font("S�rif", Font.BOLD, 12));
			JPanel panelTour = new JPanel();
			panelTour.add(this.labelTour);
			panelTour.setBounds(830,30,400,25);
			this.plateau.getLayer().add(panelTour, Integer.valueOf(1));
			
			this.panelDeroulement = new JPanel();
			if (bot1 || bot2) {
				this.botgo = new JButton("GO !");
				this.botgo.addActionListener(this);
			}
			if (this.aToiDeJouer().isBot()) {
				panelDeroulement.add(this.botgo);
				this.message.setText("Cliquez sur le bouton \"GO !\" pour faire jouer le bot");
			} else {
				this.message.setText("Selectionnez un pion blanc pour jouer");
			}
			
			this.labelDeroulement = new JLabel("Debut de la partie : le joueur " + this.couleur + " commence !"); 
			this.labelDeroulement.setFont(new Font("S�rif", Font.BOLD, 15));
			panelDeroulement.add(this.labelDeroulement);
			panelDeroulement.add(this.message);
			panelDeroulement.setBounds(830,55,400,80);
			this.plateau.getLayer().add(panelDeroulement, Integer.valueOf(1));
				
			BufferedImage buffer1;
			BufferedImage buffer2;
			if (joueur1.getCouleur().equals("blanc")) {
				buffer1 = ImageIO.read(getClass().getResource("/data/iconeBlanc.png"));
				buffer2 = ImageIO.read(getClass().getResource("/data/iconeNoir.png"));
			} else {
				buffer1 = ImageIO.read(getClass().getResource("/data/iconenoir.png"));
				buffer2 = ImageIO.read(getClass().getResource("/data/iconeBlanc.png"));
			}
			JLabel image1 = new JLabel(new ImageIcon(buffer1)); 
			JPanel panelJ1 = new JPanel();
			panelJ1.add(image1);
			panelJ1.setBounds(830,130,190,200);
			panelJ1.setLayout(new GridLayout(5,1));
			plateau.getLayer().add(panelJ1, Integer.valueOf(1));
			panelJ1.add(this.joueur1.getLabelName());
			panelJ1.add(this.joueur1.getLabelNbP());
			panelJ1.add(this.joueur1.getLabelNbD());
			panelJ1.add(this.joueur1.getLabelNbK());
					
			JLabel image2 = new JLabel(new ImageIcon(buffer2)); 
			JPanel panelJ2 = new JPanel();
			panelJ2.add(image2);
			panelJ2.setBounds(1025,130,190,200);
			panelJ2.setLayout(new GridLayout(5,1));
			plateau.getLayer().add(panelJ2, Integer.valueOf(1));
			panelJ2.add(this.joueur2.getLabelName());
			panelJ2.add(this.joueur2.getLabelNbP());
			panelJ2.add(this.joueur2.getLabelNbD());
			panelJ2.add(this.joueur2.getLabelNbK());
				
			this.score = "Score total : " + joueur1.getNbWin() + " - " + joueur2.getNbWin();
			this.labelScore = new JLabel(score); 
			this.labelScore.setFont(new Font("Serif", Font.BOLD, 17));
			JPanel panelScore = new JPanel();
			panelScore.add(labelScore);
			panelScore.setBounds(830,360,400,25);
			this.plateau.getLayer().add(panelScore, Integer.valueOf(1));

			this.labelManche = new JLabel("Manche : " + this.manche);
			JPanel panelManche = new JPanel();
			panelManche.add(labelManche);
			panelManche.setBounds(830,390,400,25);
			this.plateau.getLayer().add(panelManche, Integer.valueOf(1));
			
			this.ff = new JButton("Abandon de " + aToiDeJouer().getName());
			JPanel panelFF = new JPanel();
			panelFF.add(ff);
			panelFF.setBounds(925,500,200,30);
			panelFF.setLayout(new GridLayout(1,1));
			this.plateau.getLayer().add(panelFF, Integer.valueOf(1));

			this.restart = new JButton("Recommencer");
			JPanel panelRestart = new JPanel();
			panelRestart.add(restart);
			panelRestart.setBounds(925,575,200,30);
			panelRestart.setLayout(new GridLayout(1,1));
			this.plateau.getLayer().add(panelRestart, Integer.valueOf(1));
			
			this.quit = new JButton("Quitter");
			JPanel panelQuit = new JPanel();
			panelQuit.add(quit);
			panelQuit.setBounds(925,650,200,30);
			panelQuit.setLayout(new GridLayout(1,1));
			this.plateau.getLayer().add(panelQuit, Integer.valueOf(1));
			
			BufferedImage picture = ImageIO.read(getClass().getResource("/data/logo.png"));
			this.logo = new JButton(new ImageIcon(picture));
			JPanel panelLogo = new JPanel();
			panelLogo.add(this.logo);
			panelLogo.setBounds(1150,752,80,80);
			panelLogo.setLayout(new GridLayout(1,1));
			this.plateau.getLayer().add(panelLogo, Integer.valueOf(1));
			this.repaint();

			this.frameInterface.add(plateau.getLayer());
			this.frameInterface.setSize(new Dimension(1260,875));
			this.frameInterface.setSize(new Dimension(1250,875));
			this.repaint();
			
			if (frameExists) {
				//ne rien faire
			} else {
				frameExists = true;
				this.frameInterface.setVisible(true);
				this.frameInterface.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				this.frameInterface.addWindowListener(this);
			}

			this.ff.addActionListener(this);
			this.restart.addActionListener(this);
			this.quit.addActionListener(this);
			this.logo.addActionListener(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean playbot() throws IOException {
		//debut de la partie pour le joueur blanc
		//while (!victory) {
		if (mange) {
			for (int i=0; i<plateau.getCases().length; i++) {
				if (this.r.tryEat(killer, plateau.getCases(i), plateau)) {
					this.aToiDeJouer().eat(killer, plateau.getCases(i));
					if (this.couleur.equals("blanc")) {
						this.r.getPionMange().setScore(1000);
					} else {
						this.r.getPionMange().setScore(0);
					}
					this.message.setText("Le joueur " + this.aToiDeJouer().getCouleur() + " peut-il manger un autre pion ? ");
					this.repaint();
					return true;
				} else if (i==plateau.getCases().length-1) {
					this.message.setText("Le joueur " + this.aToiDeJouer().getCouleur() + " peut-il manger un autre pion ? Non.");
					this.pionClique = killer;
					this.finDeTour();
					this.chance = 3;
					this.mange = false;
					this.repaint();
					return false;
				}
			}
		} else {
			this.r.botMove(this.aToiDeJouer(), plateau);
			this.repaint();
		}
		return false;
	}
	
	public void displayBot() throws IOException {
		if (bot1 & bot2) {
			//si les deux sont des bots, ne rien faire
		} else {
			if (this.aToiDeJouer().isBot()) { //si le joueur actuel est un bot, mais pas l'autre, rajouter le bouton "GO !"
				if (nbTour==1) {
					this.message.setText("Cliquez sur le bouton \"GO !\" pour faire jouer le bot");
				}
				panelDeroulement.remove(this.labelDeroulement);
				panelDeroulement.remove(this.message);
				panelDeroulement.add(this.botgo);
				panelDeroulement.add(this.labelDeroulement);
				panelDeroulement.add(this.message);
			} else if (lautre().isBot()) { //si le joueur actuel n'est pas un bot, mais l'autre oui, supprimer le bouton "GO !"
				this.panelDeroulement.remove(this.botgo);
			} //si aucun des joueurs n'est un bot, ne rien faire
		}
	}
	
	public boolean creerJoueur1(String nam) {
		if (nam.equals("")) {
			this.message.setText("Un pseudo ne peut pas etre vide");
			return false;
		} else {
			return true;
		}
	}
	
	public boolean creerJoueur2(String nam) {
		if (nam.equals("")) {
			this.message.setText("Un pseudo ne peut pas etre vide");
			return false;
		} else {
			String nambot = nam + " (bot)";
			if (nam.toUpperCase().equals(this.joueur1.getName().toUpperCase()) || nambot.toUpperCase().equals(this.joueur1.getName().toUpperCase())) {
				this.message.setText("Attention : Meme pseudo !");
				return false;
			} else {
				this.joueur2 = new Joueur(this, this.joueur1.getName(), this.joueur1.getCouleur(), nam, bot2, this.plateau);
				return true;
			}
		}
	}
	
	public void repaint() {
		this.plateau.getLayer().repaint();
	}

	public void finDeTour() throws IOException {
		if (this.couleur.equals("blanc")) {
			this.couleur = ("noir");
			this.labelDeroulement.setText("Au tour du joueur " + this.couleur + " de jouer !");
			System.out.println("Au tour du joueur " + this.couleur + " de jouer !");
		} else {
			this.couleur = ("blanc");
			this.labelDeroulement.setText("Au tour du joueur " + this.couleur + " de jouer !");
			System.out.println("Au tour du joueur " + this.couleur + " de jouer !");
			this.nbTour++;
			this.labelTour.setText("Tour : " + this.nbTour);
		}
		this.ff.setText("Abandon de " + this.aToiDeJouer().getName());
		this.displayBot();
		this.souffle = false;
		this.repaint();
		this.etape = 1;
		this.pionDeplace = this.pionClique;
	}
	
	public void signalerKill(Pion killed, Joueur joueurQuiKill) {
		if (joueurQuiKill.equals(joueur1)) {
			joueur2.pionKilled(killed);
			if (joueur2.getNbPions()==0) {
				victory(joueur1);
				this.confirm = false;
			}
		} else {
			joueur1.pionKilled(killed);
			if (joueur1.getNbPions()==0) {
				victory(joueur2);
				this.confirm = false;
			}
		}
	}
	
	public void victory (Joueur winner) {
		victory = true;
		winner.setNbWin(winner.getNbWin()+1);
		this.confirm = true;
		this.labelScore.setText("Score total : " + joueur1.getNbWin() + " - " + joueur2.getNbWin());
		this.ff.setText("Revanche !");
		this.labelDeroulement.setText("Victoire de " + winner.getName() + " !! Felicitations :D");
		this.repaint();
		
		this.endingScreen = new JFrame("Fin de la partie");
		this.boutonRevanche = new JButton("Revanche !");
		this.boutonReset = new JButton("Recommencer");
		this.boutonQuit = new JButton("Quitter");
		JPanel panelEnding = new JPanel();
		JPanel panel = new JPanel();
		panel.add(new JLabel("Victoire de " + winner.getName() + " !! Que voulez-vous faire ?"));
		endingScreen.add(panel);
		panelEnding.add(boutonRevanche);
		panelEnding.add(boutonReset);
		panelEnding.add(boutonQuit);
		panelEnding.setBounds(10,10,340,145);

		endingScreen.addWindowListener(this);
		endingScreen.setLayout(new GridLayout(2,1));
		endingScreen.setResizable(false);
		endingScreen.setAlwaysOnTop(true);
		endingScreen.add(panelEnding);
		endingScreen.setBounds(350,350,360,150);
		endingScreen.setVisible(true);

		this.etape = 4;
		boutonRevanche.addActionListener(this);
		boutonReset.addActionListener(this);
		boutonQuit.addActionListener(this);
	}
	
	public void resetScore() throws IOException {
		this.mange = false;
		this.manche=1;
		this.nbTour=1;
		this.etape=0;
		this.joueur1.setNbWin(0);		
		this.joueur2.setNbWin(0);
	
		//Cr�ation du plateau
		this.frameInterface.remove(plateau.getLayer());
		this.plateau = new Plateau(this, this.taille); //Cr�ation du plateau avec toutes les cases
		this.plateau.createPions(); //Cr�ation des pions
		this.repaint();
	}

	public void revanche(int i) throws IOException {
		if (i==1) { //Confirmer la revanche
			this.endingScreen.setVisible(false);
			this.frameConfirm = new JFrame("Revanche");
			this.yesRevanche = new JButton("Oui");
			this.noChanger = new JButton("Non");
			this.no = new JButton("Annuler");
			JPanel panel = new JPanel();
			panel.add(new JLabel("Echanger les couleurs ?"));
			JPanel panelConfirm = new JPanel();
			this.frameConfirm.setLayout(new GridLayout(3,1));
			this.frameConfirm.add(panel);
			panelConfirm.add(yesRevanche);
			panelConfirm.add(noChanger);
			panelConfirm.add(no);
			this.frameConfirm.add(panelConfirm);
			this.frameConfirm.setBounds(315,350,315,150);
			this.frameConfirm.setResizable(false);
			this.frameConfirm.setAlwaysOnTop(true);
			this.frameConfirm.addWindowListener(this);
			this.frameConfirm.setVisible(true);
			this.confirm  = true;
			this.yesRevanche.addActionListener(this);
			this.noChanger.addActionListener(this);
			this.no.addActionListener(this);
		} else if (i==2) { //Changer les couleurs
			this.confirm = false;
			this.frameConfirm.setVisible(false);
			this.etape=0;
			if (this.joueur1.getCouleur().equals("blanc")) {
				this.joueur1.setCouleur("noir");
				this.joueur2.setCouleur("blanc");
			} else {
				this.joueur1.setCouleur("blanc");
				this.joueur2.setCouleur("noir");
			}
			this.frameInterface.remove(plateau.getLayer());
			this.plateau = new Plateau(this, this.taille); //Cr�ation du plateau avec toutes les cases
			this.plateau.createPions(); //Cr�ation des pions
			this.repaint();
			this.joueur1.reset();
			this.joueur2.reset();
			this.nbTour = 1;
			this.manche++;
			this.mange = false;
			this.play();
		} else { //Ne pas changer les couleurs
			this.confirm = false;
			this.frameConfirm.setVisible(false);
			this.etape=0;
			
			this.frameInterface.remove(plateau.getLayer());
			this.plateau = new Plateau(this, this.taille); //Cr�ation du plateau avec toutes les cases
			this.plateau.createPions(); //Cr�ation des pions
			this.repaint();
			this.joueur1.reset();
			this.joueur2.reset();
			this.nbTour=1;
			this.manche++;
			this.mange = false;
			this.play();
		}
	}
	
	public Reglement getReglement() {
		return this.r;
	}
	
	public Joueur getJoueur1() {
		return this.joueur1;
	}
	
	public Joueur getJoueur2() {
		return this.joueur2;
	}
	
	public Plateau getPlateau() {
		return this.plateau;
	}
	
	public JFrame getFrame() {
		return this.frameInterface;
	}
	
	public JButton getAbandon() {
		return this.ff;
	}
	
	public Joueur aToiDeJouer() {
		if (this.couleur.equals(joueur1.getCouleur())) {
			return joueur1;
		} else {
			return joueur2;
		}
	}
	
	public Joueur lautre() {
		if (this.couleur.equals(joueur1.getCouleur())) {
			return joueur2;
		} else {
			return joueur1;
		}
	}

	public int getEtape() {
		return this.etape;
	}
	
	public Pion getPionClique() {
		return this.pionClique;
	}

	public String getCouleur() {
		return this.couleur;
	}

	public int getTour() {
		return this.nbTour;
	}

	public JLabel getMessage() {
		return message;
	}

	public JLabel getLabelDeroulement() {
		return this.labelDeroulement;
	}

	public JLabel getLabelTour() {
		return labelTour;
	}
	
	public boolean getMange() {
		return this.mange;
	}
	
	public boolean isVictory() {
		return victory;
	}

	public int getChance() {
		return chance;
	}
	
	public Pion getPionDeplace() {
		return this.pionDeplace;
	}
	
	public Case getCaseDevenueVide() {
		return this.caseDevenueVide;
	}
	
	public boolean getAide() {
		return this.aide;
	}

	public boolean getConfirm() {
		return this.confirm;
	}

	public int getNbTour() {
		return this.nbTour;
	}
	
	public void setPionClique(Pion pion) {
		this.pionClique = pion;		
	}
	
	public void setCouleur(String coul) {
		this.couleur = coul;		
	}
	
	public void setEtape(int eta) {
		this.etape = eta;
	}
	
	public void setTour(int i) {
		this.nbTour = i;
	}
	
	public void setMange(boolean b) {
		this.mange = b;
	}

	public void setChance(int i) {
		this.chance = i;
	}
	
	public void setPlateau(Plateau plat) {
		this.plateau = plat;
	}
	
	public void setCaseDevenueVide(Case c) {
		this.caseDevenueVide = c;
	}
	
	public void setKiller(Pion pion) {
		this.killer = pion;
	}

	public void windowOpened(WindowEvent e) {}

	public void windowClosing(WindowEvent e) {
		if (e.getSource().equals(this.frameInterface)) {
			this.repaint();
			this.frameInterface.setSize(new Dimension(1260,875));
			this.frameInterface.setSize(new Dimension(1250,875));
			this.frameConfirm = new JFrame("Quitter le jeu de dames");
			this.yesQuit = new JButton("Oui");
			this.no = new JButton("Non");
			JPanel panel = new JPanel();
			panel.add(new JLabel("Etes-vous sur de vouloir quitter ?"));
			JPanel panelConfirm = new JPanel();
			this.frameConfirm.setLayout(new GridLayout(2,1));
			this.frameConfirm.add(panel);
			panelConfirm.add(yesQuit);
			panelConfirm.add(no);
			this.frameConfirm.add(panelConfirm);
			this.frameConfirm.setBounds(315,350,315,150);
			this.frameConfirm.setResizable(false);
			this.frameConfirm.setAlwaysOnTop(true);
			this.frameConfirm.addWindowListener(this);
			this.frameConfirm.setVisible(true);
			this.confirm  = true;
			this.yesQuit.addActionListener(this);
			this.no.addActionListener(this);
		} else {
			this.confirm = false;
		}
	}

	public void windowClosed(WindowEvent e) {}

	public void windowIconified(WindowEvent e) {}

	public void windowDeiconified(WindowEvent e) {}

	public void windowActivated(WindowEvent e) {}
	
	public void windowDeactivated(WindowEvent e) {}

	public void setSouffle(boolean b) {
		this.souffle = b;
	}

	public boolean getSouffle() {
		return this.souffle;
	}
}