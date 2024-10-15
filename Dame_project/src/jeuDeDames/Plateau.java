package jeuDeDames;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class Plateau {

	private Case A1, A3, A5, A7, A9, B2, B4, B6, B8, B10, C1, C3, C5, C7, C9, D2, D4, D6, D8, D10, E1, E3, E5, E7, E9, F2, F4, F6, F8, F10, G1, G3, G5, G7, G9, H2, H4, H6, H8, H10, I1, I3, I5, I7, I9, J2, J4, J6, J8, J10; //Cr�ation de toutes les cases du plateau	
	private Case[] cases, casesReverse;
	private Pion PB1, PB2, PB3, PB4, PB5, PB6, PB7, PB8, PB9, PB10, PB11, PB12, PB13, PB14, PB15, PB16, PB17, PB18, PB19, PB20, PN1, PN2, PN3, PN4, PN5, PN6, PN7, PN8, PN9, PN10, PN11, PN12, PN13, PN14, PN15, PN16, PN17, PN18, PN19, PN20;
	private Pion[] pionsBlancs, pionsNoirs;
	private JLayeredPane layer;
	private Partie partie;
	private int taille, nbCases;
	
	public Plateau (Partie part, int tail) throws IOException {
		this.partie = part;
		this.taille = tail;
		this.layer = new JLayeredPane(); 
		this.layer.setPreferredSize(new Dimension(50,50));
		String plateauPath="/data/plateauVide" + this.taille + ".png";
		BufferedImage plateauPicture = ImageIO.read(getClass().getResourceAsStream(plateauPath));
		JLabel plateauLabel = new JLabel(new ImageIcon(plateauPicture)); 
		JPanel plateauPanel = new JPanel();
		plateauPanel.add(plateauLabel);
		plateauPanel.setBounds(10,10,820,840);
		this.layer.add(plateauPanel,Integer.valueOf(0));
		this.partie.setPlateau(this);
		this.A1 = this.createCase("A", 1);
		this.A3 = this.createCase("A", 3);
		this.A5 = this.createCase("A", 5);
		this.A7 = this.createCase("A", 7);
		this.B2 = this.createCase("B", 2);
		this.B4 = this.createCase("B", 4);
		this.B6 = this.createCase("B", 6);
		this.B8 = this.createCase("B", 8);
		this.C1 = this.createCase("C", 1);
		this.C3 = this.createCase("C", 3);
		this.C5 = this.createCase("C", 5);
		this.C7 = this.createCase("C", 7);
		this.D2 = this.createCase("D", 2);
		this.D4 = this.createCase("D", 4);
		this.D6 = this.createCase("D", 6);
		this.D8 = this.createCase("D", 8);
		this.E1 = this.createCase("E", 1);
		this.E3 = this.createCase("E", 3);
		this.E5 = this.createCase("E", 5);
		this.E7 = this.createCase("E", 7);
		this.F2 = this.createCase("F", 2);
		this.F4 = this.createCase("F", 4);
		this.F6 = this.createCase("F", 6);
		this.F8 = this.createCase("F", 8);
		this.G1 = this.createCase("G", 1);
		this.G3 = this.createCase("G", 3);
		this.G5 = this.createCase("G", 5);
		this.G7 = this.createCase("G", 7);
		this.H2 = this.createCase("H", 2);
		this.H4 = this.createCase("H", 4);
		this.H6 = this.createCase("H", 6);
		this.H8 = this.createCase("H", 8);
		if (this.taille==8) {
			this.nbCases = 32;
		} else if (this.taille==10) {
			this.nbCases = 50;
			this.A9 = this.createCase("A", 9);
			this.B10 = this.createCase("B", 10);
			this.C9 = this.createCase("C", 9);
			this.D10 = this.createCase("D", 10);
			this.E9 = this.createCase("E", 9);
			this.F10 = this.createCase("F", 10);
			this.G9 = this.createCase("G", 9);
			this.H10 = this.createCase("H", 10);
			this.I1 = this.createCase("I", 1);
			this.I3 = this.createCase("I", 3);
			this.I5 = this.createCase("I", 5);
			this.I7 = this.createCase("I", 7);
			this.I9 = this.createCase("I", 9);
			this.J2 = this.createCase("J", 2);
			this.J4 = this.createCase("J", 4);
			this.J6 = this.createCase("J", 6);
			this.J8 = this.createCase("J", 8);
			this.J10 = this.createCase("J", 10);
		}
		this.setCases();
		this.setCasesReverse();
		System.out.println("Le plateau de " + this.nbCases + " cases vient d'�tre cr��");
	}
	
	private Case createCase(String abcisse, int ordonnee) throws IOException {
		Case c = null;
		c = new Case(abcisse, ordonnee, this.partie);
		JPanel panel = c.createImage("caseVide");
		this.layer.add(panel,Integer.valueOf(1));
		return c;
	}

	public void createPions () throws IOException {
		if (this.taille==8) {
			this.PB1 = new Pion("PB1", "blanc", this.A1, this.partie);
			this.PB2 = new Pion("PB2", "blanc", this.C1, this.partie);
			this.PB3 = new Pion("PB3", "blanc", this.E1, this.partie);
			this.PB4 = new Pion("PB4", "blanc", this.G1, this.partie);
			this.PB5 = new Pion("PB5", "blanc", this.B2, this.partie);
			this.PB6 = new Pion("PB6", "blanc", this.D2, this.partie);
			this.PB7 = new Pion("PB7", "blanc", this.F2, this.partie);
			this.PB8 = new Pion("PB8", "blanc", this.H2, this.partie);
			this.PB9 = new Pion("PB9", "blanc", this.A3, this.partie);
			this.PB10 = new Pion("PB10", "blanc", this.C3, this.partie);
			this.PB11 = new Pion("PB11", "blanc", this.E3, this.partie);
			this.PB12 = new Pion("PB12", "blanc", this.G3, this.partie);
			this.PN1 = new Pion("PN1", "noir", this.B8, this.partie);
			this.PN1 = new Pion("PN2", "noir", this.D8, this.partie);
			this.PN2 = new Pion("PN3", "noir", this.F8, this.partie);
			this.PN3 = new Pion("PN4", "noir", this.H8, this.partie);
			this.PN4 = new Pion("PN5", "noir", this.A7, this.partie);
			this.PN5 = new Pion("PN6", "noir", this.C7, this.partie);
			this.PN6 = new Pion("PN7", "noir", this.E7, this.partie);
			this.PN7 = new Pion("PN8", "noir", this.G7, this.partie);
			this.PN8 = new Pion("PN9", "noir", this.B6, this.partie);
			this.PN9 = new Pion("PN10", "noir", this.D6, this.partie);
			this.PN10 = new Pion("PN11", "noir", this.F6, this.partie);
			this.PN11 = new Pion("PN12", "noir", this.H6, this.partie);
		} else {
			this.PB1 = new Pion("PB1", "blanc", this.A1, this.partie);
			this.PB2 = new Pion("PB2", "blanc", this.C1, this.partie);
			this.PB3 = new Pion("PB3", "blanc", this.E1, this.partie);
			this.PB4 = new Pion("PB4", "blanc", this.G1, this.partie);
			this.PB5 = new Pion("PB5", "blanc", this.I1, this.partie);
			this.PB6 = new Pion("PB6", "blanc", this.B2, this.partie);
			this.PB7 = new Pion("PB7", "blanc", this.D2, this.partie);
			this.PB8 = new Pion("PB8", "blanc", this.F2, this.partie);
			this.PB9 = new Pion("PB9", "blanc", this.H2, this.partie);
			this.PB10 = new Pion("PB10", "blanc", this.J2, this.partie);
			this.PB11 = new Pion("PB11", "blanc", this.A3, this.partie);
			this.PB12 = new Pion("PB12", "blanc", this.C3, this.partie);
			this.PB13 = new Pion("PB13", "blanc", this.E3, this.partie);
			this.PB14 = new Pion("PB14", "blanc", this.G3, this.partie);
			this.PB15 = new Pion("PB15", "blanc", this.I3, this.partie);
			this.PB16 = new Pion("PB16", "blanc", this.B4, this.partie);
			this.PB17 = new Pion("PB17", "blanc", this.D4, this.partie);
			this.PB18 = new Pion("PB18", "blanc", this.F4, this.partie);
			this.PB19 = new Pion("PB19", "blanc", this.H4, this.partie);
			this.PB20 = new Pion("PB20", "blanc", this.J4, this.partie);
			
			this.PN1 = new Pion("PN1", "noir", this.B10, this.partie);
			this.PN2 = new Pion("PN2", "noir", this.D10, this.partie);
			this.PN3 = new Pion("PN3", "noir", this.F10, this.partie);
			this.PN4 = new Pion("PN4", "noir", this.H10, this.partie);
			this.PN5 = new Pion("PN5", "noir", this.J10, this.partie);
			this.PN6 = new Pion("PN6", "noir", this.A9, this.partie);
			this.PN7 = new Pion("PN7", "noir", this.C9, this.partie);
			this.PN8 = new Pion("PN8", "noir", this.E9, this.partie);
			this.PN9 = new Pion("PN9", "noir", this.G9, this.partie);
			this.PN10 = new Pion("PN10", "noir", this.I9, this.partie);
			this.PN11 = new Pion("PN11", "noir", this.B8, this.partie);
			this.PN12 = new Pion("PN12", "noir", this.D8, this.partie);
			this.PN13 = new Pion("PN13", "noir", this.F8, this.partie);
			this.PN14 = new Pion("PN14", "noir", this.H8, this.partie);
			this.PN15 = new Pion("PN15", "noir", this.J8, this.partie);
			this.PN16 = new Pion("PN16", "noir", this.A7, this.partie);
			this.PN17 = new Pion("PN17", "noir", this.C7, this.partie);
			this.PN18 = new Pion("PN18", "noir", this.E7, this.partie);
			this.PN19 = new Pion("PN19", "noir", this.G7, this.partie);
			this.PN20 = new Pion("PN20", "noir", this.I7, this.partie);
		}
		this.setPionsBlancs();
		this.setPionsNoirs();
	}
	
	public Case getCaseA1() {
		return this.A1;
	}
	
	public Case getCaseA3() {
		return this.A3;
	}
	
	public Case getCaseA5() {
		return this.A5;
	}
	
	public Case getCaseA7() {
		return this.A7;
	}
	
	public Case getCaseA9() {
		return this.A9;
	}
	
	public Case getCaseB2() {
		return this.B2;
	}
	
	public Case getCaseB4() {
		return this.B4;
	}
	
	public Case getCaseB6() {
		return this.B6;
	}
	
	public Case getCaseB8() {
		return this.B8;
	}
	
	public Case getCaseB10() {
		return this.B10;
	}
	
	public Case getCaseC1() {
		return this.C1;
	}
	
	public Case getCaseC3() {
		return this.C3;
	}
	
	public Case getCaseC5() {
		return this.C5;
	}
	
	public Case getCaseC7() {
		return this.C7;
	}
	
	public Case getCaseC9() {
		return this.C9;
	}
	
	public Case getCaseD2() {
		return this.D2;
	}
	
	public Case getCaseD4() {
		return this.D4;
	}
	
	public Case getCaseD6() {
		return this.D6;
	}
	
	public Case getCaseD8() {
		return this.D8;
	}
	
	public Case getCaseD10() {
		return this.D10;
	}
	
	public Case getCaseE1() {
		return this.E1;
	}
	
	public Case getCaseE3() {
		return this.E3;
	}
	
	public Case getCaseE5() {
		return this.E5;
	}
	
	public Case getCaseE7() {
		return this.E7;
	}
	
	public Case getCaseE9() {
		return this.E9;
	}
	
	public Case getCaseF2() {
		return this.F2;
	}
	
	public Case getCaseF4() {
		return this.F4;
	}
	
	public Case getCaseF6() {
		return this.F6;
	}
	
	public Case getCaseF8() {
		return this.F8;
	}
	
	public Case getCaseF10() {
		return this.F10;
	}
	
	public Case getCaseG1() {
		return this.G1;
	}
	
	public Case getCaseG3() {
		return this.G3;
	}
	
	public Case getCaseG5() {
		return this.G5;
	}
	
	public Case getCaseG7() {
		return this.G7;
	}
	
	public Case getCaseG9() {
		return this.G9;
	}
	
	public Case getCaseH2() {
		return this.H2;
	}
	
	public Case getCaseH4() {
		return this.H4;
	}
	
	public Case getCaseH6() {
		return this.H6;
	}
	
	public Case getCaseH8() {
		return this.H8;
	}
	
	public Case getCaseH10() {
		return this.H10;
	}
	
	public Case getCaseI1() {
		return this.I1;
	}
	
	public Case getCaseI3() {
		return this.I3;
	}
	
	public Case getCaseI5() {
		return this.I5;
	}
	
	public Case getCaseI7() {
		return this.I7;
	}
	
	public Case getCaseI9() {
		return this.I9;
	}
	
	public Case getCaseJ2() {
		return this.J2;
	}
	
	public Case getCaseJ4() {
		return this.J4;
	}
	
	public Case getCaseJ6() {
		return this.J6;
	}
	
	public Case getCaseJ8() {
		return this.J8;
	}
	
	public Case getCaseJ10() {
		return this.J10;
	}
	
	public Case getCase(String position) {
		if (position.equals(A1.getPosition())) {
			return A1;
		} else if (position.equals(A3.getPosition())) {
			return A3;
		} else if (position.equals(A5.getPosition())) {
			return A5;
		} else if (position.equals(A7.getPosition())) {
			return A7;
		} else if (position.equals(B2.getPosition())) {
			return B2;
		} else if (position.equals(B4.getPosition())) {
			return B4;
		} else if (position.equals(B6.getPosition())) {
			return B6;
		} else if (position.equals(B8.getPosition())) {
			return B8;
		} else if (position.equals(C1.getPosition())) {
			return C1;
		} else if (position.equals(C3.getPosition())) {
			return C3;
		} else if (position.equals(C5.getPosition())) {
			return C5;
		} else if (position.equals(C7.getPosition())) {
			return C7;
		} else if (position.equals(D2.getPosition())) {
			return D2;
		} else if (position.equals(D4.getPosition())) {
			return D4;
		} else if (position.equals(D6.getPosition())) {
			return D6;
		} else if (position.equals(D8.getPosition())) {
			return D8;
		} else if (position.equals(E1.getPosition())) {
			return E1;
		} else if (position.equals(E3.getPosition())) {
			return E3;
		} else if (position.equals(E5.getPosition())) {
			return E5;
		} else if (position.equals(E7.getPosition())) {
			return E7;
		} else if (position.equals(F2.getPosition())) {
			return F2;
		} else if (position.equals(F4.getPosition())) {
			return F4;
		} else if (position.equals(F6.getPosition())) {
			return F6;
		} else if (position.equals(F8.getPosition())) {
			return F8;
		} else if (position.equals(G1.getPosition())) {
			return G1;
		} else if (position.equals(G3.getPosition())) {
			return G3;
		} else if (position.equals(G5.getPosition())) {
			return G5;
		} else if (position.equals(G7.getPosition())) {
			return G7;
		} else if (position.equals(H2.getPosition())) {
			return H2;
		} else if (position.equals(H4.getPosition())) {
			return H4;
		} else if (position.equals(H6.getPosition())) {
			return H6;
		} else if (position.equals(H8.getPosition())) {
			return H8;
		}
		if (this.taille==10) {
			if (position.equals(A9.getPosition())) {
				return A9;
			} else if (position.equals(B10.getPosition())) {
				return B10;
			} else if (position.equals(C9.getPosition())) {
				return C9;
			} else if (position.equals(D10.getPosition())) {
				return D10;
			} else if (position.equals(E9.getPosition())) {
				return E9;
			} else if (position.equals(F10.getPosition())) {
				return F10;
			} else if (position.equals(G9.getPosition())) {
				return G9;
			} else if (position.equals(H10.getPosition())) {
				return H10;
			} else if (position.equals(I1.getPosition())) {
				return I1;
			} else if (position.equals(I3.getPosition())) {
				return I3;
			} else if (position.equals(I5.getPosition())) {
				return I5;
			} else if (position.equals(I7.getPosition())) {
				return I7;
			} else if (position.equals(I9.getPosition())) {
				return I9;
			} else if (position.equals(J2.getPosition())) {
				return J2;
			} else if (position.equals(J4.getPosition())) {
				return J4;
			} else if (position.equals(J6.getPosition())) {
				return J6;
			} else if (position.equals(J8.getPosition())) {
				return J8;
			} else {
				return J10;
			}
		} else {
			return H8;
		}
	}
	
	public JLayeredPane getLayer() {
		return this.layer;
	}

	public int getTaille() {
		return taille;
	}

	public Case[] getCases() {
		return cases;
	}
	
	public Case getCases(int i) {
		return cases[i];
	}
	
	public Case getCasesReverse(int i) {
		return casesReverse[i];
	}

	public void setCases() {
		if (this.taille==8) {
			this.cases = new Case[32];
			cases[0] = A1;
			cases[1] = A3;
			cases[2] = A5;
			cases[3] = A7;
			cases[4] = B2;
			cases[5] = B4;
			cases[6] = B6;
			cases[7] = B8;
			cases[8] = C1;
			cases[9] = C3;
			cases[10] = C5;
			cases[11] = C7;
			cases[12] = D2;
			cases[13] = D4;
			cases[14] = D6;
			cases[15] = D8;
			cases[16] = E1;
			cases[17] = E3;
			cases[18] = E5;
			cases[19] = E7;
			cases[20] = F2;
			cases[21] = F4;
			cases[22] = F6;
			cases[23] = F8;
			cases[24] = G1;
			cases[25] = G3;
			cases[26] = G5;
			cases[27] = G7;
			cases[28] = H2;
			cases[29] = H4;
			cases[30] = H6;
			cases[31] = H8;
		} else if (this.taille==10) {
			this.cases = new Case[50];
			cases[0] = A1;
			cases[1] = A3;
			cases[2] = A5;
			cases[3] = A7;
			cases[4] = A9;
			cases[5] = B2;
			cases[6] = B4;
			cases[7] = B6;
			cases[8] = B8;
			cases[9] = B10;
			cases[10] = C1;
			cases[11] = C3;
			cases[12] = C5;
			cases[13] = C7;
			cases[14] = C9;
			cases[15] = D2;
			cases[16] = D4;
			cases[17] = D6;
			cases[18] = D8;
			cases[19] = D10;
			cases[20] = E1;
			cases[21] = E3;
			cases[22] = E5;
			cases[23] = E7;
			cases[24] = E9;
			cases[25] = F2;
			cases[26] = F4;
			cases[27] = F6;
			cases[28] = F8;
			cases[29] = F10;
			cases[30] = G1;
			cases[31] = G3;
			cases[32] = G5;
			cases[33] = G7;
			cases[34] = G9;
			cases[35] = H2;
			cases[36] = H4;
			cases[37] = H6;
			cases[38] = H8;
			cases[39] = H10;
			cases[40] = I1;
			cases[41] = I3;
			cases[42] = I5;
			cases[43] = I7;
			cases[44] = I9;
			cases[45] = J2;
			cases[46] = J4;
			cases[47] = J6;
			cases[48] = J8;
			cases[49] = J10;
		}
	}
	
	public void setCasesReverse() {
		if (this.taille==8) {
			this.casesReverse = new Case[32];
			casesReverse[31] = A1;
			casesReverse[30] = A3;
			casesReverse[29] = A5;
			casesReverse[28] = A7;
			casesReverse[27] = B2;
			casesReverse[26] = B4;
			casesReverse[25] = B6;
			casesReverse[24] = B8;
			casesReverse[23] = C1;
			casesReverse[22] = C3;
			casesReverse[21] = C5;
			casesReverse[20] = C7;
			casesReverse[19] = D2;
			casesReverse[18] = D4;
			casesReverse[17] = D6;
			casesReverse[16] = D8;
			casesReverse[15] = E1;
			casesReverse[14] = E3;
			casesReverse[13] = E5;
			casesReverse[12] = E7;
			casesReverse[11] = F2;
			casesReverse[10] = F4;
			casesReverse[9] = F6;
			casesReverse[8] = F8;
			casesReverse[7] = G1;
			casesReverse[6] = G3;
			casesReverse[5] = G5;
			casesReverse[4] = G7;
			casesReverse[3] = H2;
			casesReverse[2] = H4;
			casesReverse[1] = H6;
			casesReverse[0] = H8;
		} else if (this.taille==10) {
			this.casesReverse = new Case[50];
			casesReverse[49] = A1;
			casesReverse[48] = A3;
			casesReverse[47] = A5;
			casesReverse[46] = A7;
			casesReverse[45] = A9;
			casesReverse[44] = B2;
			casesReverse[43] = B4;
			casesReverse[42] = B6;
			casesReverse[41] = B8;
			casesReverse[40] = B10;
			casesReverse[39] = C1;
			casesReverse[38] = C3;
			casesReverse[37] = C5;
			casesReverse[36] = C7;
			casesReverse[35] = C9;
			casesReverse[34] = D2;
			casesReverse[33] = D4;
			casesReverse[32] = D6;
			casesReverse[31] = D8;
			casesReverse[30] = D10;
			casesReverse[29] = E1;
			casesReverse[28] = E3;
			casesReverse[27] = E5;
			casesReverse[26] = E7;
			casesReverse[25] = E9;
			casesReverse[24] = F2;
			casesReverse[23] = F4;
			casesReverse[22] = F6;
			casesReverse[21] = F8;
			casesReverse[20] = F10;
			casesReverse[19] = G1;
			casesReverse[18] = G3;
			casesReverse[17] = G5;
			casesReverse[16] = G7;
			casesReverse[15] = G9;
			casesReverse[14] = H2;
			casesReverse[13] = H4;
			casesReverse[12] = H6;
			casesReverse[11] = H8;
			casesReverse[10] = H10;
			casesReverse[9] = I1;
			casesReverse[8] = I3;
			casesReverse[7] = I5;
			casesReverse[6] = I7;
			casesReverse[5] = I9;
			casesReverse[4] = J2;
			casesReverse[3] = J4;
			casesReverse[2] = J6;
			casesReverse[1] = J8;
			casesReverse[0] = J10;
		}
	}
	
	public Pion getPionsBlancs(int i) {
		return this.pionsBlancs[i];
	}
	
	public void setPionsBlancs() {
		if (this.taille==8) {
			this.pionsBlancs = new Pion[12];
			pionsBlancs[0] = PB1;
			pionsBlancs[1] = PB2;
			pionsBlancs[2] = PB3;
			pionsBlancs[3] = PB4;
			pionsBlancs[4] = PB5;
			pionsBlancs[5] = PB6;
			pionsBlancs[6] = PB7;
			pionsBlancs[7] = PB8;
			pionsBlancs[8] = PB9;
			pionsBlancs[9] = PB10;
			pionsBlancs[10] = PB11;
			pionsBlancs[11] = PB12;
		} else if (this.taille==10) {
			this.pionsBlancs = new Pion[20];
			pionsBlancs[0] = PB1;
			pionsBlancs[1] = PB2;
			pionsBlancs[2] = PB3;
			pionsBlancs[3] = PB4;
			pionsBlancs[4] = PB5;
			pionsBlancs[5] = PB6;
			pionsBlancs[6] = PB7;
			pionsBlancs[7] = PB8;
			pionsBlancs[8] = PB9;
			pionsBlancs[9] = PB10;
			pionsBlancs[10] = PB11;
			pionsBlancs[11] = PB12;
			pionsBlancs[12] = PB13;
			pionsBlancs[13] = PB14;
			pionsBlancs[14] = PB15;
			pionsBlancs[15] = PB16;
			pionsBlancs[16] = PB17;
			pionsBlancs[17] = PB18;
			pionsBlancs[18] = PB19;
			pionsBlancs[19] = PB20;
		}
	}
	
	public Pion getPionsNoirs(int i) {
		return this.pionsNoirs[i];
	}
	
	public void setPionsNoirs() {
		if (this.taille==8) {
			this.pionsBlancs = new Pion[12];
			pionsNoirs[0] = PN1;
			pionsNoirs[1] = PN2;
			pionsNoirs[2] = PN3;
			pionsNoirs[3] = PN4;
			pionsNoirs[4] = PN5;
			pionsNoirs[5] = PN6;
			pionsNoirs[6] = PN7;
			pionsNoirs[7] = PN8;
			pionsNoirs[8] = PN9;
			pionsNoirs[9] = PN10;
			pionsNoirs[10] = PN11;
			pionsNoirs[11] = PN12;
		} else if (this.taille==10) {
			this.pionsNoirs = new Pion[20];
			pionsNoirs[0] = PN1;
			pionsNoirs[1] = PN2;
			pionsNoirs[2] = PN3;
			pionsNoirs[3] = PN4;
			pionsNoirs[4] = PN5;
			pionsNoirs[5] = PN6;
			pionsNoirs[6] = PN7;
			pionsNoirs[7] = PN8;
			pionsNoirs[8] = PN9;
			pionsNoirs[9] = PN10;
			pionsNoirs[10] = PN11;
			pionsNoirs[11] = PN12;
			pionsNoirs[12] = PN13;
			pionsNoirs[13] = PN14;
			pionsNoirs[14] = PN15;
			pionsNoirs[15] = PN16;
			pionsNoirs[16] = PN17;
			pionsNoirs[17] = PN18;
			pionsNoirs[18] = PN19;
			pionsNoirs[19] = PN20;
		}
	}

	public Pion getScoreBlanc(int score) {
		for (int i=0; i<pionsBlancs.length; i++) {
			if (pionsBlancs[i].getScore()==score) {
				return pionsBlancs[i];
			}
		}
		return null;
	}
	
	public Pion getScoreNoir(int score) {
		for (int i=0; i<pionsNoirs.length; i++) {
			if (pionsNoirs[i].getScore()==score) {
				return pionsNoirs[i];
			}
		}
		return null;
	}
}
