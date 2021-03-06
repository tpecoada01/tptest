/**
 * Ce managedBean permet de g�rer l'appel des m�thodes destin�s au client
 * ainsi que l'affichage des informations qui lui sont destin�s.
 * Il est param�tre en Session pour que les informations du client restent 
 * en param�tre lors de tout l'usage.
 */


package fr.adaming.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import fr.adaming.entities.Client;
import fr.adaming.entities.Commande;
import fr.adaming.entities.LigneCommande;
import fr.adaming.entities.Produit;
import fr.adaming.service.IClientService;


@ManagedBean
@SessionScoped
public class ClientManagedBean implements Serializable {


/**
 * d�claration des attributs du ManagedBean : ------------------
 */
	private static final long serialVersionUID = 1L;
	private Client client;
	private List<Produit> listePanier;
	private Produit produit;
	private Commande commande;
	private boolean rendu1=false;
	private boolean rendu2=true;
	private boolean connexion=false;
	private List<Commande> listeCommande;
	private List<LigneCommande> listeProd;
	
//instanciation des EJB pour les m�thodes
	@EJB
	IClientService clientService;
	
//constructeur vide -----------------------------:
	
	public ClientManagedBean() {
		this.client = new Client();
		this.listePanier = new ArrayList<Produit>();
		this.produit = new Produit ();
		
	}

//getter et setters :
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public List<Produit> getListePanier() {
		return listePanier;
	}

	public void setListePanier(List<Produit> listePanier) {
		this.listePanier = listePanier;
	}
	
	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}
	
	public boolean isRendu1() {
		return rendu1;
	}

	public void setRendu1(boolean rendu1) {
		this.rendu1 = rendu1;
	}

	public boolean isRendu2() {
		return rendu2;
	}

	public void setRendu2(boolean rendu2) {
		this.rendu2 = rendu2;
	}
	

public boolean isConnexion() {
		return connexion;
	}

	public void setConnexion(boolean connexion) {
		this.connexion = connexion;
	}
	
	
	public List<Commande> getListeCommande() {
		return listeCommande;
	}

	public void setListeCommande(List<Commande> listeCommande) {
		this.listeCommande = listeCommande;
	}


public List<LigneCommande> getListeProd() {
		return listeProd;
	}

	public void setListeProd(List<LigneCommande> listeProd) {
		this.listeProd = listeProd;
	}

	
	public Commande getCommande() {
		return commande;
	}

	public void setCommande(Commande commande) {
		this.commande = commande;
	}
//les methodes --------------------------;
	

/**
 * la methode pour ajouter le produit dans le panier : 
 * 1 - r�cup�ration des informations compl�te du produit via son ID
 * 2 - ajout de la quantit�e voulue
 * 3 - calcul du nouveau prix de l'ensemble 
 * 4 - ajout du produit � la liste Panier (gard�e sur l'ensemble de la session et enregistr�e � la commande

 */
	public void ajoutPanier(){
	System.out.println("la quantit�e r�cup�r�e =");
	System.out.println(produit.getQuantite());
	System.out.println("L'id produit r�cup�r� =");
	System.out.println(produit.getIdProduit());
	int quantite = produit.getQuantite();
	Produit produitadd = clientService.getProduitByIdService(produit.getIdProduit());
	produitadd.setQuantite(quantite);
	double nouveauprix = produitadd.getPrix() * quantite;
	produitadd.setPrix(nouveauprix);
	this.listePanier.add(produitadd);
	this.rendu1=true;
	this.rendu2=false;
	this.produit.setQuantite(1);
	}
	
	/**
	 * la methode pour supprimer un produit du panier :
	 * Si elle le panier est enti�rement vid�, reinistialise les boolean utilis�s pour l'affichage
	 */
	public void supprimerProduit(){
		this.listePanier.remove(produit);
		if(listePanier.size()==0){
			this.rendu1=false;
			this.rendu2=true;
		}
		
	}
	
	/**
	 * Cette m�thode permet de vider int�gralement le panier de la session
	 */
	public void supprimerPanier(){
		this.listePanier.clear();
		this.rendu1=false;
		this.rendu2=true;
	}
	

	/**
	 * Cette m�thode est utilis�e pour la validation de la commande. 
	 * Elle se d�roule en plusieurs �tapes : 
	 * - Si le client c'est d�j� identifi� dans l'espace, il est detect� via la session -> le client est directement dirig� vers la page finale
	 * - Si l'utilisateur n'est pas identifi�, on l'am�ne vers une page proposant de cr�er un compte ou de s'identifier
	 * @client analyse le client contenu dans la session
	 * @return confirmationcommande si le client est d�j� identifi� 
	 * @identification si celui ci n'est pas identifi�
	 */
	
	public String validerCommande(){
		/**
		 * Il ya deux v�rification :
		 * - on v�rifie s'il y a d�j� un nom et un mail dans la session 
		 * - on v�rifie ensuite s'ils existent d�j� dans la bdd
		 * 
		 */
				if (this.client.getNomClient() != null) {
					this.client = clientService.isExistService(client);
					if(this.client!=null){
					return "confirmationcommande";
					}
					else {
						return "identification";
					}
				} else {
					return "identification";
				}
	}
	
	/**
	 *  @param client correspond aux informations transmise par le client (nom, mail, adresse et tel) qui sont inscrite en abse de donn�e
	 * @return quand le client est enregistr�, renvois vers la page de confirmation de commande 
	 */
	
	public String enregistrementClient(){
		clientService.enregistrementClientService(client);
		return "confirmationcommande";
	}
	
	
	/**
	 * 
	 * C'est la methode utilis�e pour l'envois de la commande.
	 * Pour �viter tout soucis, on reverifie ici que le client est bien enregistr� en session de mani�re � �viter une commande li� � un 
	 * client nul.
	 * On verifie �galement que le panier n'est pas vide.
	 * A la fin de la commande, le panier est vid� et le client redirig� vers une page de confirmation
	 * @param client est le client actuel de la session (null si non connect�, correspondant � un client connu si identifi�)
	 * @param listePanier est la liste de tous les produits enregistr�s, avec quantit� et prix total
	 */
	public String confirmerCommande(){
		if (this.client.getNomClient() != null) {
			this.client = clientService.isExistService(client);
			if(this.client!=null && this.listePanier.size()>0){
				clientService.enregistrementCommandeService(client, listePanier);
				this.listePanier.clear();
				this.rendu1=false;
				this.rendu2=true;
				return "commandeconfirme";
			}
			return "commandeechec";
			}
		return "commandeechec";
	}
	
	/**
	 * 
	 * Cette m�thode permet l'identification d'un Client existant au moment de passer commande
	 * Elle v�rifie que celui-ci existe bien, sinon elle recharge la page en affichant un message d'erreur
	 * @param client correspond aux informations transmise par le client (nom et mail); qui sont verifi�es
	 */
	public String identificationClient(){
		Client cr= clientService.isExistService(client);
		if(cr!=null){
			this.connexion=false;
			return "confirmationcommande";
		}else{
		this.connexion=true;
		return "connexionclient";
		}
	}
	
	/**
	 * Cette m�thode permet la navigation vers l'espace client, qui permet a un client existant de voir un aper�u de ses commandes.
	 *  La m�thode verifie si le client existe, sinon elle le renvois vers la page de connexion. 
	 *  Elle charge �galement la liste des commandes effectu�es.
	 *  @param client correspond aux informations transmise par le client (nom et mail); qui sont verifi�es
	 *  @return espaceclient : si le client est reconnu
	 *  @return identificationclient : si le client n'est pas reconnu
	 */
	public String espaceClient(){
		Client cr= clientService.isExistService(client);
		if(cr!=null){
			this.listeCommande=clientService.getCommandeByClientService(client);
			this.connexion=false;
			return "espaceclient";
		}else{
		this.connexion=true;
		return "identificationclient";
		}
	}
	
	/**
	 * Permet d'afficher le d�tail d'une commande pass�e par un client
	 * @param commande : une commande pass�e. L'utilisateur choisis celle qui l'interesse dans son espace client.
	 * @return apr�s avoir charger le d�tail, retourne vers la page detailcommande.xhtml
	 */
	public String detailPanier(){
		listeProd = clientService.getPanierByCommandeService(commande);
		return "detailcommande";
	}
}
