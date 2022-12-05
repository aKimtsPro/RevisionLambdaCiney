import java.util.function.Consumer;

public class PersonneWithListener extends Personne{

    Consumer<Integer> abonne;

    public PersonneWithListener(String nom, int age) {
        super(nom, age);
    }

    public void setAbonne(Consumer<Integer> abonne) {
        this.abonne = abonne;
    }

    @Override
    public void vieillir() {
        setAge( getAge() + 1 );
        if( abonne != null )
            abonne.accept( getAge() );
    }
}
