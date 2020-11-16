import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Restaurant restaurant = new Restaurant();

        List<Thread> threads = Arrays.asList(
                new Cook("Повар", restaurant),

                new Waiter("Официант1", restaurant),
                new Waiter("Официант2", restaurant),
                new Waiter("Официант3", restaurant),

                new Client("Посетитель1", restaurant),
                new Client("Посетитель2", restaurant),
                new Client("Посетитель3", restaurant),
                new Client("Посетитель4", restaurant),
                new Client("Посетитель5", restaurant)
        );

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }
    }

}
