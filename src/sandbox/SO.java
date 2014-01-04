package sandbox;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class SO {
    public String test;
    public String[] list = new String[] {test};

    public static void main(String[] args) {
        new SO();
    }

    public SO() {
        go();
    }

    BlockingQueue<String> qq = new LinkedBlockingQueue<String>();
    String wheee = new String("wheee");

    class Producer implements Runnable {
        public void run() {
            try {
                //while (true) {
                    synchronized (qq) {
                    	System.out.println("at the top");
                        while (qq.size() > 0)
                        {
                        	System.out.println("waitblock");
                            wheee.wait();
                            System.out.println("   after waitblock");
                        }

                        System.out.println("Adding new");
                        qq.put("Value 1");
                        qq.put("Value 2");
                        qq.put("Value 3");
                        qq.put("Value 4");
                    }
                //}
            } catch (InterruptedException ex) {}
        }
    }

    class Consumer implements Runnable {
        public void run() {
            try {
                while(true) {
                	synchronized(qq){
                		System.err.println("Taking " + qq.take()+". "+String.valueOf(qq.size())+" left");
                		wheee.notify();
                	}
                	Thread.sleep(1000);
                }
            } catch (InterruptedException ex) {}
        }
    }

    public void go() {
        Producer p = new Producer();
        Consumer c = new Consumer();

        new Thread(p).start();
        new Thread(c).start();
    }
}