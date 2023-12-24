import java.util.concurrent.Semaphore;

public class SemaphoreDemo {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(2);
        Thread[] threads = new Thread[5];
        for (int i=0;i<5;i++){
            threads[i] = new Thread(new ImplementSemaphore(semaphore));
            threads[i].start();
        }
        for (int i=0;i<5;i++){
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
class ImplementSemaphore extends Thread{
    Semaphore semaphore;
    ImplementSemaphore(Semaphore semaphore){
        this.semaphore = semaphore;
    }
    public void run(){
        try {
            semaphore.acquire();
            System.out.println(Thread.currentThread().getName()+" Semaphore Acquired");
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName()+" Semaphore Released");
            semaphore.release();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}