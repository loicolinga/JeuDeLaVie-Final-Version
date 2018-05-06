public class LigneAfficheur {

    public String contenu;
    public int ligne;

    public LigneAfficheur(String c, int d){
        contenu = c;
        ligne = d;
    }

    /**
     * Ajoute un * au coontenu de la ligne pour representer une cellule vivante
     */

    public void ajoutVivant(){
        contenu += " * ";
    }

    /**
     * Ajoute trois espaces au ctenu de la ligne pour signifier une case vide
     */

    public void ajoutVide(){
        contenu += "   ";
    }

    /**
     * Ajoute trois espace au debut de la ligne pour decaler l'ensemble de la ligne d'une unite vers la droite
     */

    public void decalage(){
        contenu = "   " + contenu;
    }

    /**
     * Renvoi le contenu de la ligne precede du numero de ligne
     *
     * @return le contenu
     */

    public String toString() {
        String res = "";

        if (ligne >= 0)
            res += "+" + ligne + "|";
        else
            res += ligne + "|";

        res += contenu;

        return res;
    }
}
