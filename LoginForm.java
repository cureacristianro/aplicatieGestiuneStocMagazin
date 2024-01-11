package aplicatieGestiuneStocMagazin;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class LoginForm {
	 public static void main(String[] args) {
	        String username = JOptionPane.showInputDialog("Introduceti username:");
	        String password = JOptionPane.showInputDialog("Introduceti parola:");

	        // Verificați aici dacă credențialele sunt corecte (de ex., cu o bază de date sau altă logică de autentificare)
	        if (username != null && password != null && username.equals("utilizator") && password.equals("parola")) {
	            // Deschideți aplicația principală dacă autentificarea este reușită
	            SwingUtilities.invokeLater(() -> {
	                new Main();
	            });
	        } else {
	            JOptionPane.showMessageDialog(null, "Autentificare esuata. Inchidere aplicatie.");
	        }
	    }
}
