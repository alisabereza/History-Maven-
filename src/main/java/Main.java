import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        History history = new History();

        history.writeToFile("Cat", 30, 20);
        history.writeToFile("Dog", 25, 15);
        history.writeToFile("Bird", 20, 10);
        history.writeToFile("Fish", 15, 5);

        history.read();

        System.out.println(history.arrows(false));
        System.out.println(history.arrows(false));
        System.out.println(history.arrows(true));
        System.out.println(history.arrows(true));

    }
}
