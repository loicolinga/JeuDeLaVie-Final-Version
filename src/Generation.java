import java.lang.Math;

public class Generation {

    private LC<LC<Cellule>> genPreced = new LC<>();
    private Cellule[] firstGenMoveCell;
    private UsefulData firstGendata;

    /**
     * Compte le nombre de cellule vivante autours de la cellule c
     * Compte aussi les cellules en diagonales
     *
     * @param l la liste chainee
     * @param c la cellule a traite
     * @return le nombre de voisin
     */

    public void ComptageVoisins(LC<Cellule> l, Cellule c){
        int res = 0;

        Maillon m = l.getTete();
        Cellule info = (Cellule)m.info;

        while(m!= null){
            if(m.info instanceof Cellule) {
                if(c.getColonne() - info.getColonne() == 0){
                    if((Math.abs(c.getLigne() - info.getLigne())) == 1) {
                        res++;
                    }
                }
                else{
                    if(Math.abs(c.getColonne() - info.getColonne()) == 1){

                        int dY = Math.abs(c.getLigne() - info.getLigne());

                        if(dY == 1 || dY == 0){
                            res++;
                        }
                    }
                }
            }
            m = m.suivant;

            if(m != null)
                info = (Cellule)m.info;
        }

        c.setNbVoisin(res);
    }

    /**
     * Renvoi l'ensemble des cellules et de leurs voisins
     *
     * @param l la liste a analyser
     * @return l'ensemble des cellules existantes et de leurs voisins
     */

    public LC Neighbours (LC<Cellule> l){
        LC<Cellule> n = new LC<>();
        Maillon m = l.getTete();

        while (m != null){
            Cellule cell = (Cellule)m.info;

            for(int i = -1; i <= 1; i++){
                for(int j = -1; j <= 1; j++){
                    Cellule neighbour = new Cellule(cell.getColonne() + i, cell.getLigne() + j);
                    ComptageVoisins(l, neighbour);

                    if (i == 0 && j == 0) continue;

                    if(n.estListeVide() || !n.appartientListe(neighbour))
                        n.ajout(neighbour);
                }
            }
            m = m.suivant;
        }
        return n;
    }

    /**
     * Calcul la liste chainee correspondant a la generation suivante
     *
     * @param l la liste chainee de la generation actuelle
     * @return la liste chainee correspondant a la generation suivante
     */

    public LC NextGen (LC<Cellule> l){
        LC<Cellule> res = new LC();
        LC<Cellule> lEtVoisin = Neighbours(l);
        Maillon m = lEtVoisin.getTete();

        while(m!= null){
            Cellule info = (Cellule)m.info;
            int nbVoisin = info.getNbVoisin();

            if(nbVoisin == 2 && l.appartientListe(info))
                res.ajout(info);

            if(nbVoisin == 3)
                res.ajout(info);

            m = m.suivant;
        }

        return res;
    }

    /**
     * Variante pour un monde avec limite
     * Compte le nombre de cellule vivante autours de la cellule c
     * Compte aussi les cellules en diagonales
     *
     * @param CHG les coordonnées au format [x,y] du coin haut gauche de la limite
     * @param CBD les coordonnées au format [x,y] du coin bas droit de la limite
     * @param l la liste chainee
     * @param c la cellule a traite
     * @return le nombre de voisin
     */

    public void ComptageVoisins(LC<Cellule> l, Cellule c, int[] CHG, int[] CBD){
        int res = 0;

        Maillon m = l.getTete();
        Cellule info = (Cellule)m.info;

        while(m!= null){
            if(m.info instanceof Cellule) {
                if(info.determineDansLimite(CHG,CBD)) {
                    if (c.getColonne() - info.getColonne() == 0) {
                        if ((Math.abs(c.getLigne() - info.getLigne())) == 1) {
                            res++;
                        }
                    } else {
                        if (Math.abs(c.getColonne() - info.getColonne()) == 1) {

                            int dY = Math.abs(c.getLigne() - info.getLigne());

                            if (dY == 1 || dY == 0) {
                                res++;
                            }
                        }
                    }
                }
            }
            m = m.suivant;

            if(m != null)
                info = (Cellule)m.info;
        }

        c.setNbVoisin(res);
    }

    /**
     * Variante pour un monde avec limite
     * Renvoi l'ensemble des cellules et de leurs voisins dans une liste chainee avec leurs nombres de voisins mis a jour
     *
     * @param CHG les coordonnées au format [x,y] du coin haut gauche de la limite
     * @param CBD les coordonnées au format [x,y] du coin bas droit de la limite
     * @param l la liste a analyser
     * @return l'ensemble des cellules existantes et de leurs voisins
     */

    public LC Neighbours (LC l, int[] CHG, int[] CBD){
        LC<Cellule> n = new LC<>();
        Maillon m = l.getTete();

        while (m != null){
            if(m.info instanceof Cellule) {
                Cellule cell = (Cellule) m.info;

                if(cell.determineDansLimite(CHG,CBD)) {
                    for (int i = -1; i <= 1; i++) {
                        for (int j = -1; j <= 1; j++) {
                            Cellule neighbour = new Cellule(cell.getColonne() + i, cell.getLigne() + j);
                            ComptageVoisins(l, neighbour, CHG, CBD);

                            if (i == 0 && j == 0) continue;

                            if (n.estListeVide() || !n.appartientListe(neighbour))
                                n.ajout(neighbour);
                        }
                    }
                }
                m = m.suivant;
            }
        }
        return n;
    }

    /**
     * Variante pour un monde avec limite
     * Calcul la liste chainee correspondant a la generation suivante
     *
     * @param CHG les coordonnées au format [x,y] du coin haut gauche de la limite
     * @param CBD les coordonnées au format [x,y] du coin bas droit de la limite
     * @param l la liste chainee de la generation actuelle
     * @return la liste chainee correspondant a la generation suivante
     */

    public LC NextGen (LC l, int[] CHG, int[] CBD){
        LC<Cellule> res = new LC();
        LC<Cellule> lEtVoisin = Neighbours(l,CHG,CBD);

        Maillon m = lEtVoisin.getTete();

        while(m!= null){
            Cellule info = (Cellule)m.info;
            int nbVoisin = info.getNbVoisin();

            if(nbVoisin == 2 && l.appartientListe(info))
                res.ajout(info);

            if(nbVoisin == 3)
                res.ajout(info);

            m = m.suivant;
        }

        return res;
    }

    /**
     * Detecte les evolutions
     *
     * @param l la LC de la generation actuelle
     * @return le string correspondant au resultat
     */

    public String detectionEvolution(LC<Cellule> l, int LimiteGeneration, int[] CHG, int[] CBD) {

        LC nextGeneration = new LC();
        nextGeneration = l.copie();

        if (CHG.length == 0 && CBD.length == 0 ){
            nextGeneration = NextGen(nextGeneration);
        }
        else {
            nextGeneration = NextGen(nextGeneration, CHG, CBD);
        }


        LC CurrentGeneration = new LC();
        LC NextGenerationForCompare = new LC();
        CurrentGeneration = l.copie();
        NextGenerationForCompare = nextGeneration.copie();

        int limite = 0;
        //le type mort
        if (l.estListeVide())
            return "Mort";
        //le type stable
        if (CurrentGeneration.equal(NextGenerationForCompare)) {
            Maillon<Cellule> elt = NextGenerationForCompare.getTete();
            int queue = 0;
            while (elt != null) {
                queue++;
                elt = elt.suivant;
            }
            return "Stable" + " " + ", Cellules vivantes :" + queue;
        }
        while ((!CurrentGeneration.equal(NextGenerationForCompare)) && (limite < LimiteGeneration)) {
            if (CHG.length == 0 && CBD.length == 0 ){
                CurrentGeneration = NextGen(CurrentGeneration);
                NextGenerationForCompare = NextGen(NextGenerationForCompare);
            }
            else {
                CurrentGeneration = NextGen(CurrentGeneration);
                NextGenerationForCompare = NextGen(NextGenerationForCompare, CHG, CBD);
            }
            if (CurrentGeneration.equal(NextGenerationForCompare)) {
                Maillon<Cellule> elt = NextGenerationForCompare.getTete();
                int queue = 0;
                while (elt != null) {
                    queue++;
                    elt = elt.suivant;
                }
                return "Stable" + " " + ", Cellules vivantes :" + queue;
            }
            limite++;
        }

        //le type oscillateur
        LC CurrentGenforOcsillateur = l.copie();

        LC<Cellule>[] GenerationSave = new LC[10000];

        for (int i = 0; i < LimiteGeneration; i++) {
            GenerationSave[i] = CurrentGenforOcsillateur.copie();
            if (CHG.length == 0 && CBD.length == 0 ){
                CurrentGenforOcsillateur = NextGen(CurrentGenforOcsillateur);
            }
            else {
                CurrentGenforOcsillateur = NextGen(CurrentGenforOcsillateur, CHG, CBD);
            }
        }
        LC CurrentGenForSpaceship = l.copie();
        Maillon maillon = CurrentGenForSpaceship.getTete();
        int length = 0;
        while (maillon != null) {
            length++;
            maillon = maillon.suivant;
        }
        // firstGenMoveCell = new Cellule[length];
        // maillon = CurrentGenForSpaceship.getTete();
        // int i = 0;
        // while (maillon!=null){
        // firstGenMoveCell[i] = (Cellule) maillon.info;
        // ++i;
        // maillon = maillon.suivant;
        // }
        //firstGendata = LifeUtil.getUsefulData(CurrentGenForSpaceship);



        for (int j = 0; j < LimiteGeneration; j++) {
            for (int temps = 1; temps < LimiteGeneration - j; temps++) {
                if (GenerationSave[j].equal(GenerationSave[j + temps])) {
                    Maillon<Cellule> elt = GenerationSave[j + temps].getTete();
                    int queue = 0;
                    while (elt != null) {
                        queue++;
                        elt = elt.suivant;
                    }
                    return "Oscillateur" + " " + ", periode:" + temps + " " + ", Cellules vivantes :" + queue;
                }
            }

            //le type vaisseau
            if (j == 0) {
                firstGendata = LifeUtil.getUsefulData(GenerationSave[j]);
                firstGenMoveCell = LifeUtil.move(GenerationSave[j], firstGendata);
            }
            else {
                UsefulData data = LifeUtil.getUsefulData(GenerationSave[j]);
                Cellule[] movedCells = LifeUtil.move(GenerationSave[j], data);
                boolean equals = LifeUtil.equal(firstGenMoveCell, movedCells);
                if (equals) {
                    int A;
                    int B;
                    A = firstGendata.getAddX() - data.getAddX();
                    B = firstGendata.getAddY() - data.getAddY();
                    int queue= data.getLCLength();
                    return "Vaisseau"+" ,"+"Cellule vivant:"+queue+" ,"+"deplacement:"+"ligne:"+A+"colonne:"+B+" ,"+"periode:"+j;
                }
            }
        }

        // spaceship

        return "Pas d'evolution particuliere";
    }
}
