package aplicatieGestiuneStocMagazin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Main {

	static ArrayList<Produs> produse = LucruCuBazaDeDate.obtineListaProduse();

	static Boolean loggedUserIsAdmin = false;

	public static void main(String[] args) throws ClassNotFoundException {

		boolean credentialeCorecte = LoginForm();

		if (!credentialeCorecte)
			return;

		JFrame frame = new JFrame();
		frame.setTitle("Aplicatie de gestiune stoc magazin");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(500, 500);
		frame.setLayout(new GridLayout(7, 1, 0, 0));

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
				boolean cautareCuSucces = false;
				StringBuilder rezultat = new StringBuilder();
				String denumire = JOptionPane.showInputDialog("Introduceti denumirea produsului:");

				for (Produs el : produse) {
					if (el.getDenumire().equalsIgnoreCase(denumire)) {
						if (!cautareCuSucces) {
							cautareCuSucces = true;
							rezultat.append("Produsul gasit:\n");
						}
						rezultat.append(el.toString() + "\n");
					}
				}
				if (!cautareCuSucces)
					rezultat.append("Produsul nu a fost gasit.");
				JOptionPane.showMessageDialog(null, rezultat);
			}
		});

		cautareCategorieButon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean cautareCuSucces = false;
				StringBuilder rezultat = new StringBuilder();
				String categorie = JOptionPane.showInputDialog("Introduceti categoria produsului:");

				for (Produs el : produse) {
					if (el.getCategorie().equalsIgnoreCase(categorie)) {
						if (!cautareCuSucces) {
							cautareCuSucces = true;
							rezultat.append("Produsul gasit:\n");
						}
						rezultat.append(el.toString() + "\n");
					}
				}
				if (!cautareCuSucces)
					rezultat.append("Produsul nu a fost gasit.");
				JOptionPane.showMessageDialog(null, rezultat);
			}
		});

		adaugareButon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!Main.loggedUserIsAdmin) {
					JOptionPane.showMessageDialog(null, "Adaugarea este permisa doar utilizatorilor admin!");
					return;
				}
				String denumire = JOptionPane.showInputDialog("Introduceti denumirea produsului:");
				String categorie = JOptionPane.showInputDialog("Introduceti categoria produsului: ");
				int cantitate = (int) Double.parseDouble(JOptionPane.showInputDialog("Introduceti cantitatea:"));
				double pret = Double.parseDouble(JOptionPane.showInputDialog("Introduceti pretul:"));

				Produs newProduct = new Produs(denumire, categorie, cantitate, pret);
				LucruCuBazaDeDate.adaugaProdus(newProduct);
				produse = LucruCuBazaDeDate.obtineListaProduse();
				// produse.add(newProduct);
				JOptionPane.showMessageDialog(null, "Produsul a fost adaugat:\n" + newProduct.toString());
			}
		});

		modificareButon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!Main.loggedUserIsAdmin) {
					JOptionPane.showMessageDialog(null, "Modificarea este permisa doar utilizatorilor admin!");
					return;
				}
				String denumire = JOptionPane.showInputDialog("Introduceti denumirea produsului pentru modificare:");

				String actualizareDenumire = JOptionPane.showInputDialog("Introduceti noua denumire:");
				String actualizareCategorie = JOptionPane.showInputDialog("Introduceti noua categorie: ");
				int actualizareCantitate = (int) Double
						.parseDouble(JOptionPane.showInputDialog("Introduceti noua cantitate:"));
				double actualizarePret = Double.parseDouble(JOptionPane.showInputDialog("Introduceti noul pret:"));

				Produs produsNou = new Produs(actualizareDenumire, actualizareCategorie, actualizareCantitate,
						actualizarePret);

				for (Produs el : produse) {
					if (el.getDenumire().equalsIgnoreCase(denumire)) {
						LucruCuBazaDeDate.modificaProdus(denumire, produsNou);
						produse = LucruCuBazaDeDate.obtineListaProduse();
						JOptionPane.showMessageDialog(null, "Produsul a fost modificat:\n" + produsNou.toString());
						return;
					}
				}

				JOptionPane.showMessageDialog(null, "Produsul nu a fost gasit.");
			}
		});

		stergereButon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!Main.loggedUserIsAdmin) {
					JOptionPane.showMessageDialog(null, "Stergerea este permisa doar utilizatorilor admin!");
					return;
				}
				String denumire = JOptionPane.showInputDialog("Introduceti denumirea produsului pentru stergere:");

				for (Produs el : produse) {
					if (el.getDenumire().equalsIgnoreCase(denumire)) {
						LucruCuBazaDeDate.stergeProdus(el);
						// produse.remove(el);
						produse = LucruCuBazaDeDate.obtineListaProduse();
						JOptionPane.showMessageDialog(null, "Produsul a fost sters cu succes.");
						return;
					}
				}

				JOptionPane.showMessageDialog(null, "Produsul nu a fost gasit.");
			}
		});

	}

	private static boolean LoginForm() {
		String utilizator = JOptionPane.showInputDialog("Introduceti numele de utilizator:");
		String parola = JOptionPane.showInputDialog("Introduceti parola:");
		Boolean credentialeCorecte = false;
		// Verific credențialele
		ArrayList<User> listaUtilizatori = LucruCuBazaDeDate.obtineListaUtilizatori();
		for (User user : listaUtilizatori) {
			if (user.getUsername().equals(utilizator) && user.getPassword().equals(parola)) {
				credentialeCorecte = true;
				if (user.getIsAdmin())
					Main.loggedUserIsAdmin = true;
				JOptionPane.showMessageDialog(null, "Autentificare reusita!");
				break;
			}
		}
		if (!credentialeCorecte)
			JOptionPane.showMessageDialog(null, "Autentificare esuata!");
		return credentialeCorecte;
	}
}
