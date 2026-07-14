package utils;

import java.util.concurrent.*;

public class Time {
  public ScheduledExecutorService exec;
  public Time(int threads) {
    this.exec = Executors.newScheduledThreadPool(threads);
  }
  public Time() { this(1); }
  public ScheduledFuture<?> setTimeout(Runnable fn, int delay) {
    return this.exec.schedule(fn, delay, TimeUnit.MILLISECONDS);
  }
  public ScheduledFuture<?> setInterval(Runnable fn, int rate) {
    return this.exec.scheduleAtFixedRate(fn, rate, TimeUnit.MILLISECONDS);
  }
  public void clearInterval(ScheduledFuture<?> sched) {
    sched.cancel(true);
  }
  /*private static class TimeObj {
    public TimeObj() {
      ScheduledFuture<?> id;
      clear() { this.id.cancel(true); }
    }
  }
  public static class Delay extends TimeObj {
    public Delay(ScheduledExecutorService e, Runnable fn, int dl) {
      this.id = e.schedule(fn, dl);
    }
  }
  public static class Interval extends TimeObj {
    public Interval(ScheduledExecutorService e, Runnable fn, int rt) {
      this.id = e.scheduleAtFixedRate(fn, rt);
    }*/
}
