package msolis.program.com.cst338finalproject.NotUsed;

public class Timer extends Thread {

    private int delay;
    private int accumulatedTime;
    private boolean isPaused;
    private boolean isDone;
    private boolean isActive;

    Timer(int delay)
    {
        this.delay = delay;
        accumulatedTime = 0;
        isPaused = false;
        isDone = false;
        isActive = true;
    }
    public void run()
    {
        System.out.println("run() state " + this.getState());

        //this.getState() == Thread.State.RUNNABLE
        while(isActive)
        {
            try
            {
                checkStatus();
                doNothing();
            } catch (InterruptedException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            System.out.println("Timer while loop: ACC " + accumulatedTime);
            accumulatedTime++;
            System.out.println(this.getState());
            if (accumulatedTime >= delay)
            {
                isDone = true;
            }
        }
    }

    synchronized private void doNothing() throws InterruptedException
    {
        sleep(1000);
    }

    synchronized private void checkStatus() throws InterruptedException
    {
        if (isPaused || isDone)
            wait();
    }

    synchronized public void pause()
    {
        this.isPaused = true;
    }

    synchronized public void restart()
    {
        this.isPaused = false;
        notify();
    }

    synchronized public String getAccTime()
    {
        int minutes = accumulatedTime / 60;
        int seconds = accumulatedTime % 60;

        return minutes + ":" + seconds;
    }

    synchronized public void endTimer()
    {
        isActive = false;
    }
}
