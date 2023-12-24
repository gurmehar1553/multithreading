import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapDemo {
    public static void main(String[] args) {
        ConcurrentHashMap<String,Integer> map = new ConcurrentHashMap<>();
        Writer writerThread1 = new Writer(1,"Thread1",map);
        Writer writerThread2 = new Writer(2,"Thread2",map);
        writerThread1.start();
        writerThread2.start();

        Reader readerThread1 = new Reader(map,"Thread1");
        Reader readerThread2 = new Reader(map,"Thread2");
        readerThread1.start();
        readerThread2.start();
    }
    static class Writer extends Thread{
        private ConcurrentHashMap<String,Integer> map;
        private String threadName;
        private int value;
        public Writer(int value,String threadName,ConcurrentHashMap<String,Integer> map){
            this.value = value;
            this.threadName = threadName;
            this.map = map;
        }
        public void run(){
            for (int i=0;i<5;i++){
                map.put(threadName,value);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    static class Reader extends Thread{
        private ConcurrentHashMap<String,Integer> map;
        private String threadName;
        public Reader(ConcurrentHashMap<String,Integer> map,String threadName) {
            this.map = map;
            this.threadName = threadName;
        }
        public void run(){
            for (int i=0;i<5;i++){
                Integer value = map.get(threadName);
                System.out.println(threadName +" has value: "+value);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}