import java.util.LinkedList;
import java.util.Queue;

public class ProducerConsumer {
    final static int BUFFER_SIZE = 5;
    static Queue<Integer> buffer = new LinkedList<>();

    public static void main(String[] args) {
        Producer producerThread = new Producer();
        Consumer consumerThread = new Consumer();
        producerThread.start();
        consumerThread.start();
    }

    static class Producer extends Thread{
        public void run(){
            int val = 0;
            while (true){
                synchronized (buffer){
                    while (buffer.size() == BUFFER_SIZE){
                        try {
                            buffer.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    System.out.println("Producer produced : "+val);
                    val++;
                    buffer.add(val);
                    buffer.notify();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    static class Consumer extends Thread{
        public void run(){
            while (true){
                synchronized (buffer){
                    while (buffer.isEmpty()){
                        try {
                            buffer.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    System.out.println("Consumer consumed : "+buffer.poll());
                    buffer.notify();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}

