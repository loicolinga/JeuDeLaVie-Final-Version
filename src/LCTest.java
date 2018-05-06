import junit.framework.TestCase;
import org.junit.*;
import java.util.*;

public class LCTest extends TestCase {

    public LCTest(){
        super("LC.class");
    }

    @Test
    public void testAjoutEnTete() {
        LC<Integer> liste = new LC<>();
        liste.ajoutEnTete(5);
        int info = liste.getTete().info;
        assertEquals(5,info);

        //Comparaison avec liste chainee de l'api

        LinkedList<Integer> listeAPI = new LinkedList<>();
        listeAPI.addFirst(5);
        int infoAPI = listeAPI.getFirst();

        assertEquals(true, info == infoAPI);
    }

    @Test
    public void testAjout() {
        LC<Integer> liste = new LC<>();
        liste.ajout(5);
        int info = liste.getTete().info;
        assertEquals(5,info);

        //Comparaison avec liste chainee de l'api

        LinkedList<Integer> listeAPI = new LinkedList<>();
        listeAPI.add(5);
        int infoAPI = listeAPI.getFirst();

        assertEquals(true, info == infoAPI);
    }

    @Test
    public void testGetTete() {
        LC<Integer> liste = new LC<>();
        liste.ajoutEnTete(5);
        int info = liste.getTete().info;
        assertEquals(5,info);

        //Comparaison avec liste chainee de l'api

        LinkedList<Integer> listeAPI = new LinkedList<>();
        listeAPI.addFirst(5);
        int infoAPI = listeAPI.getFirst();

        assertEquals(true, info == infoAPI);
    }

    @Test
    public void testEstListeVide() {
        LC<Integer> liste = new LC<>();
        assertEquals(true,liste.estListeVide());

        liste.ajout(5);
        assertEquals(false,liste.estListeVide());

        //Comparaison avec liste chainee de l'api

        LinkedList<Integer> listeAPI = new LinkedList<>();
        assertEquals(true,listeAPI.isEmpty());

        assertEquals(false,listeAPI.equals(liste));
    }

    @Test
    public void testAppartientListe() {
        LC<Integer> liste = new LC<>();
        assertEquals(false,liste.appartientListe(5));

        liste.ajoutEnTete(5);
        assertEquals(true,liste.appartientListe(5));

        //Comparaison avec liste chainee de l'api

        LinkedList<Integer> listeAPI = new LinkedList<>();
        assertEquals(false,listeAPI.contains(5));

        listeAPI.add(5);
        assertEquals(true,listeAPI.contains(5));
    }

    @Test
    public void testEqual() {
        LC<Integer> liste = new LC<>();
        liste.ajoutEnTete(5);

        LC<Integer> liste2 = new LC<>();
        liste2.ajoutEnTete(4);

        assertEquals(false,liste.equal(liste2));

        LC<Integer> liste3 = new LC<>();
        liste3.ajoutEnTete(5);

        assertEquals(true,liste.equal(liste3));

        //Comparaison avec liste chainee de l'api

        LinkedList<Integer> listeAPI = new LinkedList<>();
        listeAPI.add(5);

        LinkedList<Integer> listeAPI2 = new LinkedList<>();
        listeAPI2.add(4);

        assertEquals(false,listeAPI.equals(listeAPI2));

        LinkedList<Integer> listeAPI3 = new LinkedList<>();
        listeAPI3.add(5);

        assertEquals(true,listeAPI.equals(listeAPI3));
    }

    @Test
    public void testCopie(){
        LC<Integer> liste = new LC<>();
        liste.ajout(5);

        LC<Integer> listeCopie = liste.copie();

        assertEquals(true, liste.equal(listeCopie));
    }

    @Test
    public void testAffichageTableau() {
        LC<Integer> liste = new LC<>();
        assertEquals("LC vide",liste.affichageTableau());

        liste.ajoutEnTete(5);
        assertEquals("",liste.affichageTableau());
    }
}