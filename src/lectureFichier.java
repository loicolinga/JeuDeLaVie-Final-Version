import java.io.FileNotFoundException;
import java.io.*;

public class lectureFichier {

    private BufferedReader br;

    /**
     * Va chercher le fichier dont la racine est indique en parametre et le
     * place dans le bufferedReader pour un acces plus rapide pour la suite
     * Si le fichier fait plus de 200000 caracteres, renvoi une IOException
     *
     * @param racine le fichier a lire
     */

    public lectureFichier(String racine){
        try {
            File fichier = new File(racine);
            FileReader fr = new FileReader(fichier);
            br = new BufferedReader(fr);

            try{
                br.mark(20000);
            }catch(IOException e){
                System.out.println(e);
            }

        }catch(FileNotFoundException e) {
            System.out.println("Le fichier indique n'existe pas");
        }
    }

    /**
     * Convertit le fichier lu par lectureFichier en LC<Cellule>
     * utilisable pour le calcul de la generation
     *
     * @return LC<Cellule> correspondante
     */

    public LC<Cellule> ConvertionFichier(){
        LC<Cellule> res = new LC();
        int offsetX = 0;
        int offsetY = 0;

        try{
            br.reset();
        }catch(IOException e){
            System.out.println(e.toString());
        }

        try {
            for (String line = br.readLine(); line != null; line = br.readLine()) {

                if(!line.equals("")) {
                    if (line.charAt(0) == '#') {
                        String[] decoupe = line.split(" ");

                        if (decoupe[0].equals("#P")) {
                            offsetX = Integer.parseInt(decoupe[1]);
                            offsetY = Integer.parseInt(decoupe[2]);
                        }
                    } else {
                        for (int i = 0; i < line.length(); i++) {
                            if (line.charAt(i) == '*') {
                                res.ajout(new Cellule(i + offsetX, offsetY));
                            }
                        }
                        offsetY++;
                    }
                }
            }
        }catch (IOException e){
            e.toString();
        }

        return res;
    }
}