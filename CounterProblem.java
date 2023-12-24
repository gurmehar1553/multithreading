public class CounterProblem extends Thread {
    public static int cnt = 0;
    public void run(){
        cnt++;
    }
    public static void main(String[] args) throws InterruptedException {
        int numThreads = 6;
        for(int i=0;i<numThreads;i++){
            CounterProblem c = new CounterProblem();
            c.start();
            c.join();
        }
        System.out.println(cnt);
    }
}
