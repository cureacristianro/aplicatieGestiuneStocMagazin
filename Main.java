package aplicatieGestiuneStocMagazin;

import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Statement;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException {
		
		Connection con = null;
	
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/p3", "root", "a6p3sprm");
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		ArrayList<Produs> produse = new ArrayList<>();
		
		JFrame frame = new JFrame();
		frame.setTitle("Aplicatie de gestiune stoc magazin");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(500, 500);
		frame.setLayout(new GridLayout(7,1,0,0));
		
		JButton afisareButon = new JButton("Afisare");
		JButton afisareStocRedusButon = new JButton("Afisare - stocuri reduse");
		JButton cautareDenumireButon = new JButton("Cautare - dupa denumire");
		JButton cautareCategorieButon = new JButton("Cautare - dupa categorie");
        JButton adaugareButon = new JButton("Adaugare");
        JButton modificareButon = new JButton("Modificare");
        JButton stergereButon = new JButton("Stergere");
        
        afisareButon.setFocusable(false);
        afisareStocRedusButon.setFocusable(false);
        cautareDenumireButon.setFocusable(false);
        cautareCategorieButon.setFocusable(false);
        adaugareButon.setFocusable(false);
        modificareButon.setFocusable(false);
        stergereButon.setFocusable(false);
        
        frame.add(afisareButon);
        frame.add(afisareStocRedusButon);
        frame.add(cautareDenumireButon);
        frame.add(cautareCategorieButon);
        frame.add(adaugareButon);
        frame.add(modificareButon);
        frame.add(stergereButon);
		
		ImageIcon image = new ImageIcon("star-on.png");
		frame.setIconImage(image.getImage());
		
		frame.setVisible(true);
		
		afisareButon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StringBuilder toateProdusele = new StringBuilder("Toate produsele:\n");

                for (Produs el : produse) {
                    toateProdusele.append(el.toString()).append("\n");
                }

                if (toateProdusele.length() > 0) {
                    JOptionPane.showMessageDialog(null, toateProdusele.toString());
                } else {
                    JOptionPane.showMessageDialog(null, "Nu există produse în stoc.");
                }
            }
        });

		afisareStocRedusButon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int limita = 10; 
                StringBuilder produseStocRedus = new StringBuilder("Produse cu stoc redus:\n");

                for (Produs el : produse) {
                    if (el.getCantitate() < limita) {
                        produseStocRedus.append(el.toString()).append("\n");
                    }
                }

                if (produseStocRedus.length() > 0) {
                    JOptionPane.showMessageDialog(null, produseStocRedus.toString());
                } else {
                    JOptionPane.showMessageDialog(null, "Nu există produse cu stoc redus.");
                }
            }
        });
		 
		cautareDenumireButon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String denumire = JOptionPane.showInputDialog("Introduceti denumirea produsului:");

                for (Produs el : produse) {
                    if (el.getDenumire().equalsIgnoreCase(denumire)) {
                        JOptionPane.showMessageDialog(null, "Produsul gasit:\n" + el.toString());
                        return;
                    }
                }

                JOptionPane.showMessageDialog(null, "Produsul nu a fost gasit.");
            }
        });
		
		cautareCategorieButon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String categorie = JOptionPane.showInputDialog("Introduceti categoria produsului:");

                for (Produs el : produse) {
                    if (el.getCategorie().equalsIgnoreCase(categorie)) {
                        JOptionPane.showMessageDialog(null, "Produse gasite:\n" + el.toString());
                        return;
                    }
                }

                JOptionPane.showMessageDialog(null, "Produsul nu a fost gasit.");
            }
        });
		
		adaugareButon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String denumire = JOptionPane.showInputDialog("Introduceti denumirea produsului:");
                String categorie = JOptionPane.showInputDialog("Introduceti categoria produsului: ");
                int cantitate = (int) Double.parseDouble(JOptionPane.showInputDialog("Introduceti cantitatea:"));
                double pret = Double.parseDouble(JOptionPane.showInputDialog("Introduceti pretul:"));

                Produs newProduct = new Produs(denumire, categorie, cantitate, pret);
                produse.add(newProduct);
                
                JOptionPane.showMessageDialog(null, "Produsul a fost adaugat:\n" + newProduct.toString());
            }
        });
		
		modificareButon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String denumire = JOptionPane.showInputDialog("Introduceti denumirea produsului pentru modificare:");
                
                String actualizareDenumire = JOptionPane.showInputDialog("Introduceti noua denumire:");
                String actualizareCategorie = JOptionPane.showInputDialog("Introduceti noua categorie: ");
                int actualizareCantitate = (int) Double.parseDouble(JOptionPane.showInputDialog("Introduceti noua cantitate:"));
                double actualizarePret = Double.parseDouble(JOptionPane.showInputDialog("Introduceti noul pret:"));

                for (Produs el : produse) {
                    if (el.getDenumire().equalsIgnoreCase(denumire)) {
                    	el.setDenumire(actualizareDenumire);
                    	el.setCategorie(actualizareCategorie);
                    	el.setCantitate(actualizareCantitate);
                        el.setPret(actualizarePret);
                        JOptionPane.showMessageDialog(null, "Produsul a fost modificat:\n" + el.toString());
                        return;
                    }
                }

                JOptionPane.showMessageDialog(null, "Produsul nu a fost gasit.");
            }
        });
	
		stergereButon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String denumire = JOptionPane.showInputDialog("Introduceti denumirea produsului pentru stergere:");

                for (Produs el : produse) {
                    if (el.getDenumire().equalsIgnoreCase(denumire)) {
                       produse.remove(el);
                       JOptionPane.showMessageDialog(null, "Produsul a fost sters cu succes.");
                       return;
                    }
                }

                JOptionPane.showMessageDialog(null, "Produsul nu a fost gasit.");
            }
        });
		
	}
}