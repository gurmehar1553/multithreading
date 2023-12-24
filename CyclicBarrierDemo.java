import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {
    private static final int NUM_THREADS = 3;
    private static final CyclicBarrier barrier = new CyclicBarrier(NUM_THREADS,new BarrierAction());
    public static void main(String[] args) {
        Thread[] threads = new Thread[NUM_THREADS];
        for (int i=0;i<NUM_THREADS;i++){
            threads[i] = new Thread(new CyclicBarrierExecute());
            threads[i].start();
        }
        for (Thread thread:threads){
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    static class CyclicBarrierExecute extends Thread{
        public void run(){
            System.out.println(Thread.currentThread().getName()+" is waiting at the barrier");
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName()+" has crossed the barrier");
        }
    }
}

/**
 * Action performed
 * at barrier
 */
class BarrierAction extends Thread{
    public void run(){
        System.out.println("All threads have reached the barrier!!");
    }
}