package aplicatieGestiuneStocMagazin;

public class Produs {
	
	private String denumire;
	private String categorie;
	private int cantitate;
	private double pret;
	
	public Produs(String denumire, String categorie, int cantitate, double pret) {
		super();
		this.denumire = denumire;
		this.categorie = categorie;
		this.cantitate = cantitate;
		this.pret = pret;
	}

	public String getDenumire() {
		return denumire;
	}

	public void setDenumire(String denumire) {
		this.denumire = denumire;
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public int getCantitate() {
		return cantitate;
	}

	public void setCantitate(int cantitate) {
		this.cantitate = cantitate;
	}

	public double getPret() {
		return pret;
	}

	public void setPret(double pret) {
		this.pret = pret;
	}

	@Override
	public String toString() {
		return "Produs [denumire=" + denumire + ", categorie=" + categorie + ", cantitate=" + cantitate + ", pret="
				+ pret + "]";
	}

}
