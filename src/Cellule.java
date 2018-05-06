public class Cellule {
    private int colonne;
    private int ligne;
    private int nbVoisin;

    public Cellule(int colonne, int ligne){
        this.colonne = colonne;
        this.ligne = ligne;
        nbVoisin = 0;

    }

    /**
     * Renvoi la colonne de la cellule
     *
     * @return la colonne de la cellule
     */

    public int getColonne() {
        return colonne;
    }

    /**
     * Renvoi la ligne de la cellule
     *
     * @return la ligne de la cellule
     */

    public int getLigne() {
        return ligne;
    }

    /**
     * Renvoi le nombre de voisin de la cellule
     * N'effectue pas une mise a jour du nombre de voisin
     *
     * @return le nombre de voisin
     */

    public int getNbVoisin() {
        return nbVoisin;
    }

    /**
     * Affecte le nombre de voisin de la cellule a nbVoisin
     *
     * @param nbVoisin le nombre de voisin de la cellule a affecter
     */

    public void setNbVoisin(int nbVoisin) {
        this.nbVoisin = nbVoisin;
    }

    /**
     * Determine si la cellule actuelle a les meme coorodnnees que la cellule passee en argument
     *
     * @param obj la cellule a comparer
     * @return vrai si egal, faux sinon
     */

	@Override
    public boolean equals(Object obj) {
        return  colonne == ((Cellule)obj).getColonne() && ligne == ((Cellule)obj).getLigne();
    }
	
    /**
     * Determine si la cellule est situe en dehors des limites du plateau
     * La limite a une forme rectangulaire
     * Tolere que la cellule soit sur place sur la limite
     *
     * @param coinHautGauche les coordonnées au format [x,y] du coin haut gauche de la limite
     * @param coinBasDroit les coordonnées au format [x,y] du coin bas droit de la limite
     * @return vrai si la cellule est dans le plateau, faux sinon
     * @throws IllegalArgumentException
     */

    public boolean determineDansLimite(int[] coinHautGauche, int[] coinBasDroit) throws IllegalArgumentException{

        if(coinHautGauche[0] > coinBasDroit[0] || coinHautGauche[1] > coinBasDroit[1])
            throw new IllegalArgumentException("La valeur de coinHautGauche ou coinBasDroit est incoherente");

        return colonne >= coinHautGauche[0] && ligne >= coinHautGauche[1] && colonne <= coinBasDroit[0] && ligne <= coinBasDroit[1];
    }

    /**
     * Renvoi les coordonnees de la cellule au format (colonne, ligne)
     *
     * @return les coordonnees de la cellule
     */

    public String toString(){
        return "(" + colonne + "," + ligne + ")";
    }
}
