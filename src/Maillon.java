public class Maillon<T> {

    Maillon<T> suivant;
    T info;

    Maillon(T info, Maillon<T> suivant){
        this.info = info;
        this.suivant = suivant;
    }
}
