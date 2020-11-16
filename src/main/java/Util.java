public class Util {


    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new IllegalStateException("EXCEPTION!!!");
        }
    }

    public static void wait(Object obj) {
        try {
            obj.wait();
        } catch (InterruptedException e) {
            throw new IllegalStateException("EXCEPTION!!!");
        }
    }

}
