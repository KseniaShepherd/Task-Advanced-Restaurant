public class Cook extends Thread {
    private Restaurant restaurant;

    public Cook(String name, Restaurant restaurant) {
        super(name);
        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        System.out.println(getName() + " на работе");
        while (!restaurant.allOrdersCooked()) {
            restaurant.cookDish();
        }
    }
}
