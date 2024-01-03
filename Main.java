package aplicatieGestiuneStocMagazin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.border.Border;
import javax.swing.text.html.HTMLDocument.Iterator;

public class Main {

	private static ArrayList<Produs> produse;

	public static void main(String[] args) {
		
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
                       // el.remove(denumire);
                    }
                }

                JOptionPane.showMessageDialog(null, "Produsul nu a fost gasit.");
            }
        });
		
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            ProductFileHandler.saveProducts(Main.produse);
        }));
		
		frame.setVisible(true);
		
	}
}

/*JPanel redPanel = new JPanel();
redPanel.setBackground(Color.red);
redPanel.setBounds(0, 0, 100, 100);

JPanel greenPanel = new JPanel();
greenPanel.setBackground(Color.green);
greenPanel.setBounds(100, 0, 100, 100);
greenPanel.setLayout(new BorderLayout());

Border border = BorderFactory.createLineBorder(Color.green,5);

JLabel newLabel = new JLabel();
newLabel.setText("HI!");
//newLabel.setBounds(100, 100, 100, 100);

JLabel label = new JLabel();
label.setText("SALUT!");
label.setHorizontalTextPosition(JLabel.CENTER);
label.setVerticalTextPosition(JLabel.TOP);
label.setVerticalAlignment(JLabel.CENTER);
label.setHorizontalAlignment(JLabel.CENTER);
label.setForeground(new Color(0,1,1));
label.setFont(new Font("TNR", Font.PLAIN, 20));
label.setBorder(border);

JFrame frame = new JFrame();
frame.setTitle("Aplicatie de gestiune stoc magazin");
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
frame.setResizable(false);
frame.setSize(420,420);
frame.setVisible(true);
frame.setLayout(null);
frame.add(label);
frame.add(newLabel);
frame.add(redPanel);
frame.add(greenPanel);
redPanel.add(newLabel);
//frame.setLayout(new GridLayout(5,1,0,0));

new MyFrame();


ImageIcon image = new ImageIcon("star-on.png");
frame.setIconImage(image.getImage());
frame.getContentPane().setBackground(new Color(50,94,168));

*/
