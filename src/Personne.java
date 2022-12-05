public abstract class Personne implements IVieillir {

    private final String nom;
    private int age;

    public Personne(String nom, int age) {
        this.nom = nom;
        this.age = age;
    }

    public String getNom() {
        return nom;
    }

    public int getAge() {
        return age;
    }

    protected void setAge(int age){
        this.age = age;
    }

}
