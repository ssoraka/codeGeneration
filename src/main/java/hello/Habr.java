
package hello;

import java.io.Serializable;
import ru.dima.file.SimpleGenerator;


/**
 * The <code>hello.Habr</code> implementing the Serializable interface
 * 
 */
@Annotation
public class Habr<T >
    extends SimpleGenerator
    implements Serializable
{

    private static String message = "Hello Habr!";

    public static void helloHabr() {
        System.out.println(message);
    }

}
