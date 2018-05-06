
public class LC<T> {

    private Maillon<T> tete;

    LC(){
        tete = null;
    }

    /**
     * Ajoute l'element v en tete de la liste chainee si ce dernier n'appartient pas deja a la liste
     *
     * @param v l'element a ajouter
     */

    public void ajoutEnTete(T v){
        if(!appartientListe(v)) tete = new Maillon<T>(v, tete);
    }

    /**
     * Rajoute un nouveau maillon a la liste chainee
     * Si v est une cellule, ce dernier sera place en fonction de ses coordonnees afin que
     * la liste chainee soit trie par ordre croissant en donnant la priorite a la ligne des cellules
     * puis leur colonne.
     * Si v n'est pas une cellule, il sera rajouter en tete de la liste chainee.
     *
     * @param v l'element a ajouter
     */

    public void ajout(T v){

        if(v instanceof Cellule) {
            Maillon m = tete;
            Maillon pre = null;
            Cellule c = (Cellule) v;
            Maillon ma = new Maillon(c, null);

            if (m == null)
                tete = ma;

            while (m != null) {
                Cellule info = (Cellule) m.info;

                if (c.getLigne() < info.getLigne() || (c.getLigne() == info.getLigne() && c.getColonne() < info.getColonne())) {
                    if (pre == null) {
                        tete = ma;
                        ma.suivant = m;
                    } else {
                        pre.suivant = ma;
                        ma.suivant = m;
                    }
                    break;
                }

                pre = m;
                m = m.suivant;

                if (m == null)
                    pre.suivant = ma;
            }
        }else{
            ajoutEnTete(v);
        }
    }

    /**
     * Renvoi le maillon de tete de la LC
     *
     * @return le maillon de tete
     */

    public Maillon<T> getTete(){
        return tete;
    }

    /**
     * Renseigne si la liste est vide
     *
     * @return vrai si la liste est vide
     */

    public boolean estListeVide(){
        return (this.tete == null);
    }

    /**
     * Retourne si l'element x appartient a la liste chainee
     *
     * @param x l'element a tester
     * @return vrai si l'element appartient a la LC
     */

    public boolean appartientListe(T x){
        if(!estListeVide()) {
            Maillon<T> m = tete;

            while (m != null) {

                if (x instanceof Cellule && m.info instanceof Cellule) {
                    Cellule info = (Cellule) m.info;

                    if ((info.getColonne() == ((Cellule) x).getColonne() && info.getLigne() == ((Cellule) x).getLigne())) return true;
                } else {
                    if (m.info == x) return true;
                }

                m = m.suivant;
            }
        }

        return false;
    }

    /**
     * Verifie si deux LC sont egales
     *
     * @param l la LC a comparer
     * @return vrai si les LC sont egales
     */

    public boolean equal(LC<T> l){
        Maillon<T> m = tete;
        Maillon<T> p = l.tete;

        while(m != null && p != null){
            T infoM = m.info;
            T infoP = p.info;

            if(infoM instanceof Cellule && infoP instanceof Cellule){
                Cellule iM = (Cellule)infoM;
                Cellule iP = (Cellule)infoP;

                if(!iM.equals(iP)) return false;
            }else{
                if(!infoM.equals(infoP)) return false;
            }

            m = m.suivant;
            p = p.suivant;
        }

        return true;
    }

    /**
     * Renvoi une copie de la liste chainee
     *
     * @return LC<Cellule> la copie
     */

    public LC<T> copie(){
        LC<T> res = new LC();

        Maillon m = tete;

        while(m != null){
            res.ajout((T)m.info);

            m = m.suivant;
        }

        return res;
    }

    /**
     * Affiche la liste chainee sous la forme d'un tableau
     * Parcours la liste chainee une seule fois
     *
     * @return le tableau
     */

    public String affichageTableau(){
        Maillon m = tete;
        Maillon pre = null;
        String res = "";
        String echelle = "";
        LC<LigneAfficheur> ensLigne = new LC<>();

        if(estListeVide()) return "LC vide";

        if(m != null && m.info instanceof Cellule) {
            Cellule info = (Cellule) m.info;
            int minColonne = info.getColonne();
            int maxColonne = info.getColonne();
            LigneAfficheur act = new LigneAfficheur("",info.getLigne());

            while (m != null) {
                info = (Cellule)m.info;

                if(info.getColonne() < minColonne){
                    //Ajoute le decalage a toute les lignes existantes
                    Maillon t = ensLigne.tete;

                    for(int i = 0; i < Math.abs(minColonne - info.getColonne()); i++)
                        act.decalage();

                    while(t != null){
                        for(int i = 0; i < Math.abs(minColonne - info.getColonne()); i++)
                            ((LigneAfficheur) t.info).decalage();

                        t = t.suivant;
                    }

                    minColonne = info.getColonne();
                }

                if(info.getColonne() > maxColonne){
                    maxColonne = info.getColonne();
                }

                //Pour le premier point ou quand le point actuel est sur la meme ligne que le precedent
                if(pre == null || info.getLigne() == ((Cellule)pre.info).getLigne()){
                    if(pre != null){
                        for(int i = 0; i < Math.abs(info.getColonne() - ((Cellule)pre.info).getColonne()) - 1; i++)
                            act.ajoutVide();
                    }
                    act.ajoutVivant();
                }else{
                    ensLigne.ajoutEnTete(act);
                    act = new LigneAfficheur("", info.getLigne());
                    for(int i = 0; i < Math.abs(minColonne - info.getColonne()); i++)
                        act.ajoutVide();
                    act.ajoutVivant();
                }

                pre = m;
                m = m.suivant;
            }

            if(!ensLigne.appartientListe(act))
                ensLigne.ajoutEnTete(act);

            //Formation de l'echelle
            for(int i = minColonne; i <= maxColonne; i++){
                if(i >= 0) echelle += "|+" + i;
                else echelle += "|" + i;
            }
            echelle = "_/" + echelle;

            //Affichage des resultats
            Maillon t = ensLigne.tete;
            Maillon preT = null;

            while(t != null){
                String temp = t.info + "";

                if(preT != null) {

                    int decalage = Math.abs(((LigneAfficheur) t.info).ligne - ((LigneAfficheur) preT.info).ligne);

                    if(decalage > 1){

                        temp += "\n";

                        for (int i = 1; i < decalage; i++) {
                            if (i > 0) temp += "+" + (i + ((LigneAfficheur) t.info).ligne) + "|";
                            else temp += (i+((LigneAfficheur) t.info).ligne) + "|";
                        }
                    }
                }

                res = temp + "\n" + res;

                preT = t;
                t = t.suivant;
            }

            res = echelle + "\n" + res;
        }

        return res;
    }
}