public class Client extends Thread {
    private Restaurant restaurant;

    public Client(String name, Restaurant restaurant) {
        super(name);
        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        System.out.println(getName() + " Идет в ресторан");
        restaurant.enter();
        Util.sleep(2000);
        System.out.println(getName() + " придумал что заказать");
        restaurant.callWaiter();
        restaurant.eatOrder();
        System.out.println(getName() + "  вышел из ресторана");
    }
}
