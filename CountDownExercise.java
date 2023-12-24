import java.util.concurrent.CountDownLatch;

public class CountDownExercise {
    private final static int NUM_THREADS = 3;
    private static CountDownLatch startLatch = new CountDownLatch(1);
    private static CountDownLatch finishLatch = new CountDownLatch(NUM_THREADS);

    public static void main(String[] args) {
        Thread[] threads = new Thread[NUM_THREADS];
        for (int i=0;i<NUM_THREADS;i++){
            threads[i] = new Thread(new CountDownDemo());
            threads[i].start();
        }
        startLatch.countDown();
        try {
            finishLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("All threads have finished work");
    }
    static class CountDownDemo extends Thread{
        public void run(){
            try {
                startLatch.await();
                System.out.println(Thread.currentThread().getName()+" has finished work.");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            finally {
                finishLatch.countDown();
            }
        }
    }
}
