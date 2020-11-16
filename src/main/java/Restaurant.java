public class Restaurant {
    private static final int MAX_ORDERS = 5;
    private static final int SLEEP_TIME = 2000;

    private int clientsReadyToMakeOrder;
    private int clientsReadyToMakeOrderTotal;
    private int clientsOrders;
    private int clientsReadyOrders;
    private int clientsReadyOrdersTotal;
    private int clientsBroughtOrders;
    private int clientsBroughtOrdersTotal;

    private final Object monitor1 = new Object();
    private final Object monitor2 = new Object();
    private final Object monitor3 = new Object();
    private final Object monitor4 = new Object();
    private final Object monitor5 = new Object();

    public Restaurant() {
    }


    public boolean allOrdersCooked() {
        return this.clientsReadyOrdersTotal >= MAX_ORDERS;
    }

    public boolean allOrdersBrought() {
        return this.clientsBroughtOrdersTotal >= MAX_ORDERS;
    }

    public void enter() {
        synchronized (monitor5) {
            Util.sleep(SLEEP_TIME);
            System.out.println(Thread.currentThread().getName() + " вошел в ресторан");
        }
    }

    public void callWaiter() {
        synchronized (this.monitor1) {
            System.out.println(Thread.currentThread().getName() + " готов сделать заказ");
            this.clientsReadyToMakeOrder++;
            this.monitor1.notifyAll();
        }

    }

    public void takeOrder() {
        synchronized (this.monitor1) {
            while (this.clientsReadyToMakeOrder <= 0 && clientsReadyToMakeOrderTotal<MAX_ORDERS) {
                Util.wait(this.monitor1);
            }
            if (clientsReadyToMakeOrderTotal>=MAX_ORDERS){
                return;
            }
            System.out.println(Thread.currentThread().getName() + " взял заказ");
            Util.sleep(SLEEP_TIME);
            this.clientsReadyToMakeOrder--;
            this.clientsReadyToMakeOrderTotal++;
        }

        synchronized (this.monitor2) {
            System.out.println(Thread.currentThread().getName() + " передал повару");
            this.clientsOrders++;
            this.monitor2.notifyAll();
        }
    }

    public void cookDish() {
        synchronized (this.monitor2) {
            while (this.clientsOrders <= 0 && clientsReadyOrdersTotal < MAX_ORDERS) {
                Util.wait(this.monitor2);
            }
            if (clientsReadyOrdersTotal >= MAX_ORDERS) {
                return;
            }

            System.out.println(Thread.currentThread().getName() + "  взял заказ в работу");
            Util.sleep(SLEEP_TIME);
            this.clientsOrders--;
            this.clientsReadyOrdersTotal++;
        }

        synchronized (this.monitor3) {
            System.out.println(Thread.currentThread().getName() + " закончил готовить");
            this.clientsReadyOrders++;
            this.monitor3.notifyAll();
        }
    }

    public void bringOrder() {
        synchronized (this.monitor3) {
            while (this.clientsReadyOrders <= 0 && clientsBroughtOrdersTotal<MAX_ORDERS) {
                System.out.println(Thread.currentThread().getName() + clientsBroughtOrdersTotal);
                Util.wait(this.monitor3);
            }
            if (clientsBroughtOrdersTotal>=MAX_ORDERS){
                return;

            }
            System.out.println(Thread.currentThread().getName() + " забрал заказ у повара");
            Util.sleep(SLEEP_TIME);
            this.clientsReadyOrders--;
            this.clientsBroughtOrdersTotal++;
        }

        synchronized (this.monitor4) {
            System.out.println(Thread.currentThread().getName() + " несет заказ посетителю");
            this.clientsBroughtOrders++;
            this.monitor4.notifyAll();
        }
    }

    public void eatOrder() {
        synchronized (this.monitor4) {
            while (this.clientsBroughtOrders <= 0) {
                Util.wait(this.monitor4);
            }
            System.out.println(Thread.currentThread().getName() + " ест заказ");
            Util.sleep(SLEEP_TIME);
            clientsBroughtOrders--;
        }
    }


}
