/**
 * ManagedBean de navigation qui permet l'appel et l'affichage de :
 * - consulter la liste des cat�gories
 * - consulter la liste des produits selon la cat�gorie 
 * - rechercher un produit via mot cl�s 
 */

package fr.adaming.managedBean;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;


import fr.adaming.entities.Admin;
import fr.adaming.entities.Categorie;
import fr.adaming.entities.Client;
import fr.adaming.entities.Produit;
import fr.adaming.service.IAdminService;
import fr.adaming.service.IClientService;

/** 
 * 
 * D�claration du ManagedBean en ViewScopped : 
 * R�sultat hors session, uniquement � l'affichage de la page pour ces r�sultats
 *
 */
@ManagedBean
@ViewScoped
public class NavigationManagedBean implements Serializable{



/**
 * D�claration des attributs :
 * serialisation
 * rendu permet l'affichage au rafraichissement de la page et appel de la methode via l'attribut rendered
 */
	private static final long serialVersionUID = 1L;
	private Client client;
	private Admin admin;
	private boolean rendu;
	private List<Categorie> listeCategorie;
	private List<Produit> listeProduit;
	private Categorie categorie;
	private Produit produit;
	private String saisie;
	private String message;


/**
 * Instation pour le lien avec le service :
 */
	@EJB
	IClientService clientService;
	
	@EJB
	IAdminService adminService;
	
/**
 * les constructeurs :	
 */
public NavigationManagedBean() {
	this.client = new Client();
	this.admin = new Admin();
	this.produit = new Produit();
	this.categorie = new Categorie();
}

/**
 * D�claration des getters et setters :
 */


	public boolean isRendu() {
		return rendu;
	}

	public List<Categorie> getListeCategorie() {
	return listeCategorie;
}

public void setListeCategorie(List<Categorie> listeCategorie) {
	this.listeCategorie = listeCategorie;
}

	public Client getClient() {
	return client;
}

public void setClient(Client client) {
	this.client = client;
}

	public void setRendu(boolean rendu) {
		this.rendu = rendu;
	}
	
	
public List<Produit> getListeProduit() {
		return listeProduit;
	}

	public void setListeProduit(List<Produit> listeProduit) {
		this.listeProduit = listeProduit;
	}
	
	
public String getSaisie() {
		return saisie;
	}

	public void setSaisie(String saisie) {
		this.saisie = saisie;
	}
	
	
	


public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	
	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * D�claration des m�thodes :
	 */
	

	/**
	 * m�thode de l'espace utilisateur
	 */

	/**
	 * la m�thode pour obtenir la liste des produits par cat�gorie :
	 * La deuxi�me utilise une m�thode de navigation � partir de la page d'afficage des diff�rentes cat�gories
	 */
	public void listePro(){
		this.listeProduit = clientService.getProduitsByCategorieService(categorie);
		this.rendu = true;	
	}
	
	
	
	/**la methode pour obtenir la liste des produits par mot cl�s :
	 * Via ajax, les r�sultats sont rafraichis en direct � la saisie.
	 * 
	 */
	public void listeMot(){
		
		this.listeProduit = clientService.getProduitsByMotService(saisie);
		this.rendu=true;
		
	}
	
	
	
	
	/**
	 * m�thode qui affiche un message de confirmation lors de l'ajout d'un produit au panier :
	 */
	
	  public void panierMessage() {
	        FacesContext context = FacesContext.getCurrentInstance();
	         
	        context.addMessage(null, new FacesMessage("Le produit a bien �t� ajout� au panier !"));
	  
	    }

	
/**
 * m�thodes de l'espace admin
 */
	
	/**
	 * la m�thode pour obtenir un produit par id:
	 */

	public void getProduitById(){
		this.produit = adminService.rechercherProduitParIdService(produit.getIdProduit());
		this.rendu = true;
	}
	
	

	
}
