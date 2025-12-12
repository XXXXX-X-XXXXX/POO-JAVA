import CHATEAU.Archer;
import CHATEAU.Guerrier;
import CHATEAU.Mage;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() {

    Guerrier g1 = new Guerrier("Arthur");
    Archer a1 = new Archer("Charles");
    Mage m1 = new Mage("Merlin");

    IO.println("=======================");
    g1.avancer();
    g1.special();
    g1.frapper(m1);
    g1.competence(a1);
    IO.println("=======================");


}
