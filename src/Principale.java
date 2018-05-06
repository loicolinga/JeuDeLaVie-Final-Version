import java.awt.*;
import java.io.*;
import javax.swing.Timer;
import java.awt.event.*;
import java.util.Scanner;

public class Principale {


    private static LC<Cellule> liste;

    public static void main(String[] Args){
        Scanner sc = new Scanner(System.in);
        //Enregistre le path du dossier courant dans une variable
        String WorkingDirectory = System.getProperty("user.dir");
        if (Args[0].contains("-w")){
            try {
                int [] CHG = new int[0];
                int [] CBD = new int[0];
                Generation g = new Generation();
              HTML h = new HTML();
              //ecrit dans le HTML
              h.setHTML(Integer.parseInt(Args[1]), g, CHG, CBD);


            }
            catch (NumberFormatException e){
                //si l'utilisateur a entré autre chose que X,Y (X et Y etant des int), une exception sera attrapée
                System.err.println(e.getMessage().replaceFirst(".*For input string: ", "Ceci n'est pas un nombre"));
            }

            //indique le fichier html à ouvrir
            File htmlFile = new File(WorkingDirectory +"\\TypeEvolution.html");
            try {

                //ouvre le html dans le navigateur par defaut du PC
                Desktop.getDesktop().browse(htmlFile.toURI());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            //contains à la place de equals au cas ou l'utilisateur ajouterait un espace par inadvertance
            if (Args[0].contains("-name")){
                //Affiche les noms du groupe
                System.out.println("ROBLOU Antoine");
                System.out.println("OLINGA-MEDJO Loic");
                System.out.println("ZHANG Ling");
            }
            else{
                if (Args[0].contains("-h")){
                    //Affiche les options du programme
                    System.out.println("Tapez '-name' apres 'java -JeuDeLaVie.jar' pour afficher les noms et prenoms des etudiants du groupe");
                    System.out.println("Tapez '-h' apres 'java -JeuDeLaVie.jar' pour afficher les options du programme");
                    System.out.println("Tapez '-s d 'nom du fichier'.lif apres 'java -JeuDeLaVie.jar' pour executer une simulation du jeu d'une duree d (celui que vous avez passe en parametre) affichant les configurations du jeu avec le numero de la generation");
                    System.out.println("Tapez '-c max 'nom du fichier'.lif apres 'java -JeuDeLaVie.jar' pour visualiser le type d'evolution du jeu avec ses caracteristiques (taille de la queue, periode et deplacement), max represente la duree maximale de simulation pour deduire les resultats du calcul");
                    System.out.println("Tapez '-w max dossier' apres 'java -JeuDeLaVie.jar' pour voir le type d'evolution de tous les jeux contenus dans le dossier passe en parametre et voir les resultats dans un fichier html");
                }
                else {
                    if (Args[0].contains("-s" )|| Args[0].contains("-c")){
                        //simulation jeu de la vie ou calcul type evolution du fichier
                        //Args[1] etant le nombre max de generation que l'utilisateur veut simuler
                        int d = Integer.parseInt(Args[1]);
                        //va chercher le fichier lif au bon endroit (Args[2] etant le fichier lif entré en parametre
                        lectureFichier LIF = new lectureFichier(WorkingDirectory +"\\LIF\\"+ Args[2]);
                        Generation g = new Generation();
                        LC<Cellule> liste = LIF.ConvertionFichier();
                        Principale p = new Principale();
                        String option = "";
                        while ((!option.contains("normal")&&!option.contains("fini"))){
                            System.out.println("Tapez 'normal' pour une simulation dans le monde normal et 'fini' pour une simulation dans le monde fini avec frontieres");
                            option = sc.nextLine();
                            //au cas ou l'utilisateur ecrirait l'option en majuscule
                            option = option.toLowerCase();
                        }
                        if (option.contains("normal")){
                            //si l'utilisateur a tapé normal, on simule dans le monde normal, c'est à dire le monde infini. De ce fait les limites CHG et CBD ne seront pas prises en compte
                            //voir la condition dans deroulementJeu (en dessous du main)
                            int [] CHG = new int[0];
                            int [] CBD = new int[0];
                            if (Args[0].contains("-s")){
                                p.deroulementJeu(liste, g, d, CHG, CBD);
                            }
                            else {
                                System.out.println(g.detectionEvolution(liste, d, CHG, CBD));
                            }
                        }
                        else {
                            if (option.contains("fini")){
                                String min = "";
                                String max = "";
                                while (!min.contains(",")){
                                    System.out.println("Coordonnees minimum du monde fini (entrez les cordonnees de la maniere suivante : X,Y)");
                                    min = sc.nextLine();
                                }
                                while (!max.contains(",")){
                                    System.out.println("Coordonnees maximum du monde fini (entrez les cordonnees de la maniere suivante : X,Y)");
                                    max = sc.nextLine();
                                }
                                //on decoupe min et max pour pouvoir transformer les coordonees en int et les rentrer dans CHG (pour les coordonnees min) et CHD (pour les coordonnees max)
                                String [] part1 = min.split(",");
                                String [] part2 = max.split(",");
                                try {
                                    int []CHG = {Integer.parseInt(part1[0]), Integer.parseInt(part1[1])};
                                    int []CBD = {Integer.parseInt(part2[0]), Integer.parseInt(part2[1])};
                                    if (Args[0].contains("-s")){
                                        p.deroulementJeu(liste, g, d, CHG, CBD);
                                    }
                                    else {
                                        System.out.println(g.detectionEvolution(liste, d, CHG, CBD));
                                    }
                                }
                                catch (NumberFormatException e){
                                    //si l'utilisateur a entré autre chose que X,Y (X et Y etant des int), une exception sera attrapée
                                    System.err.println(e.getMessage().replaceFirst(".*For input string: ", "Ceci n'est pas un nombre"));
                                }


                            }
                        }
                    }
                    else {
                        if (Args[0].contains("-w")){
                            //calcule type d'evolution d'un jeu
                        }
                        else {
                            System.out.println("Veuillez retapez l'option choisie correctement");
                        }
                    }
                }
            }

        }
    }
    



    public static void deroulementJeu(LC<Cellule> l, Generation g, final int LimiteGeneration, int [] CHG, int [] CBD){

        //Sert a modifier la valeur de liste dans la classe anonyme actionPerformed

        liste = l;

        //Erreur de compilation si on met l'actionListener directement lors de la declaration
        Timer t = new Timer(5,null);
        t.addActionListener(new ActionListener() {
            int nbGeneration = 0;
            public void actionPerformed(ActionEvent e) {
                System.out.println(liste.affichageTableau());
                if (CHG.length == 0 && CBD.length == 0 ){
                    liste = g.NextGen(liste);
                }
                else {
                    liste = g.NextGen(l, CHG, CBD);
                }

                System.out.println(l.affichageTableau());

                System.out.println(liste.affichageTableau());




                nbGeneration++;

                if(nbGeneration == LimiteGeneration) {
                    System.out.println("Appuyez sur entree pour continuer");
                    t.stop();
                }
            }
        });
        t.start();

        try {
            System.in.read();
        }
        catch (IOException e){}

        t.stop();
    }
}

