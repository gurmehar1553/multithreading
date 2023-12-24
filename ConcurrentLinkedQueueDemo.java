import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedQueueDemo {
    public static void main(String[] args) {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        Producer producerThread1 = new Producer("Producer1",queue);
        Producer producerThread2 = new Producer("Producer2",queue);
        producerThread1.start();
        producerThread2.start();

        Consumer consumerThread1 = new Consumer("Consumer1",queue);
        Producer consumerThread2 = new Producer("Consumer2",queue);
        consumerThread1.start();
        consumerThread2.start();
    }
    static class Producer extends Thread{
        private String producerName;
        private ConcurrentLinkedQueue<String> queue;
        int counter=0;

        public Producer(String producerName, ConcurrentLinkedQueue<String> queue) {
            this.producerName = producerName;
            this.queue = queue;
        }
        public void run(){
            while (true){
                String item = producerName+" item-"+counter;
                queue.add(item);
                counter++;
                System.out.println("Produced item-"+counter);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    static class Consumer extends Thread{
        private String consumerName;
        private ConcurrentLinkedQueue<String> queue;
        public Consumer(String consumerName,ConcurrentLinkedQueue<String> queue){
            this.consumerName = consumerName;
            this.queue = queue;
        }
        public void run(){
            while (true){
                String item = queue.poll();
                if (item != null){
                    System.out.println(consumerName+" Consumed "+item);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
