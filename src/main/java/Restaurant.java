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

    private final Object readyToMakeOrderMonitor = new Object();
    private final Object clientsOrdersMonitor = new Object();
    private final Object clientsReadyOrdersMonitor = new Object();
    private final Object broughtOrdersMonitor = new Object();
    private final Object enterMonitor = new Object();

    public Restaurant() {
    }


    public boolean allOrdersCooked() {
        return this.clientsReadyOrdersTotal >= MAX_ORDERS;
    }

    public boolean allOrdersBrought() {
        return this.clientsBroughtOrdersTotal >= MAX_ORDERS;
    }

    public void enter() {
        synchronized (enterMonitor) {
            Util.sleep(SLEEP_TIME);
            System.out.println(Thread.currentThread().getName() + " вошел в ресторан");
        }
    }

    public void callWaiter() {
        synchronized (this.readyToMakeOrderMonitor) {
            System.out.println(Thread.currentThread().getName() + " готов сделать заказ");
            this.clientsReadyToMakeOrder++;
            this.readyToMakeOrderMonitor.notifyAll();
        }

    }

    public void takeOrder() {
        synchronized (this.readyToMakeOrderMonitor) {
            while (this.clientsReadyToMakeOrder <= 0 && clientsReadyToMakeOrderTotal<MAX_ORDERS) {
                Util.wait(this.readyToMakeOrderMonitor);
            }
            if (clientsReadyToMakeOrderTotal>=MAX_ORDERS){
                return;
            }
            System.out.println(Thread.currentThread().getName() + " взял заказ");
            Util.sleep(SLEEP_TIME);
            this.clientsReadyToMakeOrder--;
            this.clientsReadyToMakeOrderTotal++;
        }

        synchronized (this.clientsOrdersMonitor) {
            System.out.println(Thread.currentThread().getName() + " передал повару");
            this.clientsOrders++;
            this.clientsOrdersMonitor.notifyAll();
        }
    }

    public void cookDish() {
        synchronized (this.clientsOrdersMonitor) {
            while (this.clientsOrders <= 0 && clientsReadyOrdersTotal < MAX_ORDERS) {
                Util.wait(this.clientsOrdersMonitor);
            }
            if (clientsReadyOrdersTotal >= MAX_ORDERS) {
                return;
            }

            System.out.println(Thread.currentThread().getName() + "  взял заказ в работу");
            Util.sleep(SLEEP_TIME);
            this.clientsOrders--;
            this.clientsReadyOrdersTotal++;
        }

        synchronized (this.clientsReadyOrdersMonitor) {
            System.out.println(Thread.currentThread().getName() + " закончил готовить");
            this.clientsReadyOrders++;
            this.clientsReadyOrdersMonitor.notifyAll();
        }
    }

    public void bringOrder() {
        synchronized (this.clientsReadyOrdersMonitor) {
            while (this.clientsReadyOrders <= 0 && clientsBroughtOrdersTotal<MAX_ORDERS) {
                System.out.println(Thread.currentThread().getName() + clientsBroughtOrdersTotal);
                Util.wait(this.clientsReadyOrdersMonitor);
            }
            if (clientsBroughtOrdersTotal>=MAX_ORDERS){
                return;

            }
            System.out.println(Thread.currentThread().getName() + " забрал заказ у повара");
            Util.sleep(SLEEP_TIME);
            this.clientsReadyOrders--;
            this.clientsBroughtOrdersTotal++;
        }

        synchronized (this.broughtOrdersMonitor) {
            System.out.println(Thread.currentThread().getName() + " несет заказ посетителю");
            this.clientsBroughtOrders++;
            this.broughtOrdersMonitor.notifyAll();
        }
    }

    public void eatOrder() {
        synchronized (this.broughtOrdersMonitor) {
            while (this.clientsBroughtOrders <= 0) {
                Util.wait(this.broughtOrdersMonitor);
            }
            System.out.println(Thread.currentThread().getName() + " ест заказ");
            Util.sleep(SLEEP_TIME);
            clientsBroughtOrders--;
        }
    }


}
