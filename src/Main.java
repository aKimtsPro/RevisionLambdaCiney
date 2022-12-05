import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {

        Personne p = new Personne("luc", 45){
            @Override
            public void vieillir() {
                this.setAge( this.getAge()+1 );
            }
        };

        IVieillir v = new IVieillir() {
            @Override
            public void vieillir() {
                System.out.println("Je vieillis");
            }
        };

        IVieillir v2 = () -> System.out.println("Je vieillis");


        PersonneWithListener pl = new PersonneWithListener("pol", 14);
        pl.setAbonne( (age) -> {
            System.out.println(age);
        } );

        pl.vieillir();


        List<Personne> liste = new ArrayList<>();

        liste.add(new PersonneWithListener("dominique", 15));
        liste.add(new PersonneWithListener("luc", 62));
        liste.add(new PersonneWithListener("marie", 35));

        List<Integer> filteredList = liste.stream()
                .filter( (pers) -> pers.getNom().length() <= 6 )
                .map((pers) -> pers.getAge())
                .toList();

        // syntax

        // parenthese: toujours sauf si 1 param typé implicitement
        // accolades: obligatoire si plusieurs instructions => ; pour séparer obligatoires
        // return: interdit si pas d'accolades, obligatoire si accolades (dans le cas d'une lambda renvoie qqchose)
        // typage de param: optionnel, si 1 param est typé explicitement, tous les params doivent l'être
        Consumer<String> consumer = chaine -> System.out.println(chaine.length());
        BiConsumer<? extends Personne, String> biCons = (PersonneWithListener entier, String chaine) -> System.out.println( chaine + entier);
        Supplier<? extends Throwable> supplier = () -> new RuntimeException("message");


        // STREAM

        Stream<Integer> stream = Stream.of( 1,2,3,4,5 ); // stream fini
//        stream = Stream.generate(() -> (int)(Math.random() * 100) ); // stream infini


        // Attention, on ne peut pas réaliser 2 opérations sur un même objet Stream.
        stream.map( (entier) -> entier+1 );
//        stream.filter( integer -> integer % 2 == 0 ); // throws IllegalStateException

        // Opérations intermédiaires - renvoie un nouveau Stream

        // filter - garde les elements qui correspondent à un Predicate
        generateStream().filter( (entier) -> entier % 2 == 1 )
                .forEach( System.out::println ); // reference à println de System.out
        // au lieu de définir un comportement, on utilise un prédéfini

        // map - transforme chaque element en un autre élément
        generateStream().map( (entier) -> new PersonneWithListener("truc"+entier, entier) )
                .forEach( System.out::println );

        // distinct - garde les éléments uniques
        generateStream().distinct()
                .forEach( System.out::println ); // 1,2,3,4,5

        // limit - ne travaille que sur les n premiers element, ignore le reste
        Stream<Integer> stream2 = Stream.generate(() -> (int)(Math.random() * 100) );
        stream2.limit(100)
                .forEach(System.out::println);

        // skip - ignore les n premiers elements
        generateStream() // 1,2,2,3,4,5
                .distinct() // 1,2,3,4,5
                .limit(1) // 1
                .skip(3) // (/)
                .forEach(System.out::println); // (/)


        // Operation terminale - ne renvoie pas de Stream

        // forEach - applique un consumer sur chaque element restant
        generateStream().filter( (entier) -> entier % 2 == 1 )
                .forEach( System.out::println );

        // toList - rassemble les elements dans une list (immutable)
        List<Integer> l = generateStream().toList();

        // reduce - reduit toutes les valeurs en une valeur de même type
        int value = generateStream().reduce( 0, (acc, next) -> acc += next );

        // min/max - récupère l'élément le plus petit/le plus grand selon un comparateur
        int min = generateStream().min( (entier1, entier2) -> entier1-entier2 )
                .orElse( 0 );

        // AnyMatch / AllMatch / NoneMatch
        boolean unPair = generateStream().anyMatch( (entier) -> entier % 2 == 0 );
        boolean tousPair = generateStream().allMatch( (entier) -> entier % 2 == 0 );
        boolean aucunPair = generateStream().noneMatch( (entier) -> entier % 2 == 0 );

        // findFirst / findAny
        int first = generateStream().findFirst()
                .orElse(-1);


        // Optional<T>
        Optional<String> opt = Optional.ofNullable( "pas null" );
        String contenu = opt.orElseThrow(() -> new RuntimeException("une erreur s'est produite"));

    }

    public static Stream<Integer> generateStream(){
        return Stream.of( 1,2,2,3,4,5 );
    }

}
