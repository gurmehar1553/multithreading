public class DemoThread {
    public static void main(String[] args) throws InterruptedException {
        MyThread t = new MyThread();
        MyThread t2 = new MyThread();
        t2.setPriority(10);
        t.start();
        t2.start();
        System.out.println(t.getPriority());
        System.out.println(t2.getPriority());
    }
}
class MyThread extends Thread{
    MyThread(){
        System.out.println(Thread.currentThread());
    }
    public void run(){
        System.out.println(getName());
        System.out.println("Thread is running");
    }
}