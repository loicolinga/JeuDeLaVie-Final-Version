import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FilenameFilter;

public class HTML {
    String WorkingDirectory = System.getProperty("user.dir");
    //fichier html dans lequel on a ecrit
    File f = new File(WorkingDirectory+"\\TypeEvolution.html");

    public String[] getFiles(){
        //dossier lif
        File folder = new File(WorkingDirectory +"\\LIF");
        //on prend tous les fichiers lif du dossiers
        File [] files = folder.listFiles(new FilenameFilter() {
            public boolean accept(File folder, String name) {
                return name.endsWith(".LIF");
            }
        });
        String [] lif = new String[files.length];
        for (int i = 0 ; i < lif.length-1; i++) {
            //on recupere leur chemin dans un tableau
            lif[i] =  files[i].getAbsolutePath();
        }
        return lif;
    }
    public void setHTML (int max, Generation g, int [] CHG, int [] CBD){
        String html =  "<!DOCTYPE html><html lang=\"fr\"><head>\n<meta charset=\"utf-8\">\n<link rel=\"stylesheet\" href=\"style.css\">\n<title>page vide</title></head><body><h3>Pour " + max + " generations voici le type d'evolution de tous les jeux contenus : </h3>";
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(f));
            String [] fileslist = getFiles();
            for (int i = 0; i < fileslist.length -1; i++){
                //separateurs \\\\ a cause du escape, donc \\\\ equivaut à \\ en realite
                String[] part = fileslist[i].split("\\\\");
                //la derniere partie du tableau est forcement le nom du fichier que l'on veut
                String filename = part[part.length-1];
                lectureFichier LIF = new lectureFichier(fileslist[i]);
                LC<Cellule> liste = LIF.ConvertionFichier();
                String evol = g.detectionEvolution(liste, max, CHG, CBD);
                String [] splt = evol.split(",");
                html = html + "<p>nom fichier : " + filename + " type evolution : "  + splt[0] + " </p><br>";
            }
            html = html + "</body></html>";
            br.write(html);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

