package jeuDeDames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;

public class Clique implements ActionListener {
    
	private Case caseCliquee;
	private JButton bouton;
	private Partie partie;
	
    public Clique(Case c) {
    	this.caseCliquee = c;
    	this.bouton = this.caseCliquee.getBouton();
        this.bouton.addActionListener(this);
        this.partie = this.caseCliquee.getPartie();
    }
    
    public void actionPerformed(ActionEvent e) { //ce qu'il se passe quand on clique sur le bouton
    	try {
    		if (this.partie.getJoueur1().isBot()) {
    			this.partie.getMessage().setText("Vous ne pouvez pas interf�rer dans cette partie entre deux bots.");
    			this.partie.repaint();
    		} else if (this.partie.getJoueur2().isBot() & this.partie.aToiDeJouer().equals(this.partie.getJoueur2())) {
    			this.partie.getMessage().setText("Vous ne pouvez pas jouer � la place du bot.");
    			this.partie.repaint();
    		} else if (this.partie.getConfirm()) {
    			this.partie.getMessage().setText("Vous devez fermer la fen�tre contextuelle pour pouvoir jouer.");
    			this.partie.repaint();
    		} else {
	    		if (this.partie.getEtape()==1) {
	    			if (this.caseCliquee.pionPresent()) {
	    				if (this.caseCliquee.getPion().getCouleur().equals(this.partie.aToiDeJouer().getCouleur())) {
	    					this.partie.setPionClique(this.caseCliquee.getPion());
	    					this.partie.getMessage().setText("O� voulez-vous d�placer le pion " + this.caseCliquee.getPion().getName() + " ?");
	    					if (partie.getAide()) {
	    						this.partie.getReglement().showMove(this.partie.getPionClique(), this.partie.getPlateau());
	    					}
							this.partie.setChance(2);
	    					this.partie.setEtape(2);
	    				} else {
	    					if (this.partie.getNbTour()==1) { //on ne peut pas souffler de pion au tour 1
      	   						this.partie.setChance(2);
	    					}
	    					if (this.partie.getChance()==0 || this.partie.getChance()==3) {
	        					this.partie.setPionClique(this.caseCliquee.getPion());
	        					if (this.partie.getPionClique().equals(this.partie.getPionDeplace()) & this.partie.getChance()==3) {
	        						this.partie.getMessage().setText("Pouvez-vous souffler ce pion ? ");
	        						this.partie.setChance(1);
	            					this.partie.setEtape(2);
	        					} else {
	        						if (this.partie.getPionClique().equals(this.partie.getPionDeplace())) {
	        							this.partie.getMessage().setText("Vous ne pouvez pas souffler ce pion car il vient de se d�placer");
	        							this.partie.setChance(2);
	        						} else if (this.partie.getChance()==0){
	        							this.partie.getMessage().setText("Pouvez-vous souffler ce pion ? ");
	            						this.partie.setChance(1);
	        							this.partie.setEtape(2);
	        						} else {
	          	   						this.partie.getMessage().setText("Vous ne pouvez souffler que le pion qui a mang� pendant le tour pr�c�dent.");
	          	   						this.partie.setChance(2);
	            					}
	    	    				}
	    					} else {
	  	   						this.partie.getMessage().setText("Vous devez s�lectionner un pion de votre couleur.");
	    					}
	    				}
	    			} else {
	    				this.partie.getMessage().setText("Veuillez cliquer sur un pion pour le d�placer.");
	    			}
	    		} else if (this.partie.getEtape()==2) {
	    			if (this.partie.getChance()==1) {
	    				if (this.caseCliquee.pionPresent()==false) {
	    					if (this.partie.getReglement().tryEat(this.partie.getPionClique(), this.caseCliquee, this.partie.getPlateau())) {
	    						if (this.caseCliquee.equals(this.partie.getCaseDevenueVide())) {
	    							this.partie.getMessage().setText("Votre adversaire ne pouvait pas manger ce pion.");
	    						} else {
	    							this.partie.aToiDeJouer().souffler(this.partie.getPionClique());
	    						}
	    						this.partie.setChance(2);
	    						this.partie.setEtape(1);
	    						this.partie.repaint();
	    					} else {
	    						this.partie.getMessage().setText("Pouvez-vous soufflier ce pion ? Non.");
	    						this.partie.setChance(2);
	        					this.partie.setEtape(1);
	    					}
	    				} else {
	    					this.partie.getMessage().setText("Pouvez-vous soufflier ce pion ? Non.");
							this.partie.setChance(2);
	    					this.partie.setEtape(1);
	    				}
	    			} else {
	    				this.partie.getReglement().resetCasesVides(this.partie.getPlateau());
		    			if (this.caseCliquee.pionPresent()==false) {
		    				if (this.partie.aToiDeJouer().move(this.partie.getPionClique(), this.caseCliquee) || this.partie.aToiDeJouer().eat(this.partie.getPionClique(), this.caseCliquee)) {
		    					if (this.partie.getJoueur1().getNbPions()==0 || this.partie.getJoueur2().getNbPions()==0) { 						
		    					} else {
		    						if(this.partie.getMange()) {
		    							if (partie.getAide()) {
		    	    						this.partie.getReglement().showMove(this.partie.getPionClique(), this.partie.getPlateau());
		    	    					}
		    							if (this.partie.getPionClique().isDevenuDame()) {
		    								this.partie.getPionClique().setDevenuDame(false);
		    								this.partie.setMange(false);
		    								this.partie.finDeTour();
		    								this.partie.setChance(0);
		    							} else {
		    								this.partie.getMessage().setText("Pouvez-vous manger un autre pion ? ");
		    								this.partie.setEtape(3);
		    							}
		    						} else {
			    						this.partie.finDeTour();
			        					this.partie.setChance(0);
		    						}
		    					}
		    				} else {
		    					this.partie.getMessage().setText("D�placement annul�. S�lectionnez un pion � d�placer.");
		    	    			this.partie.setEtape(1);
		    				}
		    			} else {
		    				this.partie.getMessage().setText("D�placement annul�. S�lectionnez un pion � d�placer.");
		        			this.partie.setEtape(1);
		    			}
	    			}
	    		} else if (this.partie.getEtape()==3) {
					this.partie.getReglement().resetCasesVides(this.partie.getPlateau());
	    			if (this.caseCliquee.pionPresent()==false) {
	    				if (this.partie.aToiDeJouer().eat(this.partie.getPionClique(), this.caseCliquee)) {		
	    					if (partie.getAide()) {
	    						this.partie.getReglement().showMove(this.partie.getPionClique(), this.partie.getPlateau());
	    					}
	    					if (this.partie.getPionClique().isDevenuDame()) {
								this.partie.getPionClique().setDevenuDame(false);
								this.partie.setMange(false);
								this.partie.finDeTour();
								this.partie.setChance(0);
							} else {
								this.partie.getMessage().setText("Le joueur " + this.partie.aToiDeJouer().getCouleur() + " peut-il manger un autre pion ?");
							}		
	    				} else {
	    					this.partie.getMessage().setText("Le joueur " + this.partie.aToiDeJouer().getCouleur() + " peut-il manger un autre pion ? Non.");
							this.partie.finDeTour();
	    					this.partie.setChance(3);
							this.partie.setMange(false);
	    				}
	    			} else {
    					this.partie.getMessage().setText("Le joueur " + this.partie.aToiDeJouer().getCouleur() + " peut-il manger un autre pion ? Non.");
						this.partie.finDeTour();
						this.partie.setChance(3);
						this.partie.setMange(false);
	    			}
	    		}
    		} 
    	} catch (IOException ex) {
    		ex.printStackTrace();
    	}
    }
}