import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockExercise {
    private static final int NUM_READS = 3;
    private static final int NUM_WRITES = 2;

    public static void main(String[] args) {
        ReadWriteLock lock = new ReentrantReadWriteLock();
        SharedResource sharedResource = new SharedResource();
        for (int i=0;i<NUM_READS;i++){
            Thread readerThread = new Thread(new Reader(lock,sharedResource));
            readerThread.start();
        }

        for (int i=0;i<NUM_WRITES;i++){
            Thread writerThread = new Thread(new Writer(lock,sharedResource));
            writerThread.start();
        }

    }
    static class Reader extends Thread{
        ReadWriteLock lock;
        SharedResource sharedResource;
        public Reader(ReadWriteLock lock,SharedResource sharedResource){
            this.lock = lock;
            this.sharedResource = sharedResource;
        }
        public void run(){
            while (true){
                lock.readLock().lock();
                System.out.println(sharedResource.getData());
                lock.readLock().unlock();
                // Delay between 2 consecutive read threads
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    static class Writer extends Thread{
        ReadWriteLock lock;
        SharedResource sharedResource;
        int count=0;
        public Writer(ReadWriteLock lock, SharedResource sharedResource) {
            this.lock = lock;
            this.sharedResource = sharedResource;
        }
        public void run(){
            while (true){
                lock.writeLock().lock();
                sharedResource.setData(Thread.currentThread().getName()+" writes "+count++);
                // Delay between 2 consecutive write threads
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    static class SharedResource{
        String data;
        String getData(){
            return data;
        }
        void setData(String data){
            this.data = data;
        }
    }
}