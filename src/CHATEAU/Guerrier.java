package CHATEAU;

import SAVANE.Deplacement;

public class Guerrier extends Personnage implements Deplacement, Attaque {

    public Guerrier(String name) {
        super(name);
    }

    @Override
    public void avancer() {
        IO.println("marche activé");
    }

    @Override
    public void special() {
        IO.println("Double Saut");
    }

    @Override
    public void frapper(Personnage cible) {
        IO.println(getName() + " attaque " + cible.getName() + ".");
    }

    @Override
    public void competence(Personnage cible) {
        IO.println(getName() + " utilise une compétences sur " + cible.getName() + ".");
    }

}
