package thread;

import com.thankjava.toolkit.bean.thread.TaskEntity;
import com.thankjava.toolkit.bean.utils.TimeType;
import com.thankjava.toolkit.core.thread.ThreadTask;
import com.thankjava.toolkit.core.utils.TimeUtil;

import java.io.IOException;
import java.util.Date;

public class ThreadTaskTest {

    public static void main(String[] args) throws InterruptedException, IOException {


        ThreadTask threadTask = new ThreadTask(10);
        TaskEntity task1 = task1(threadTask);
        threadTask.addTaskAtFixedRate(task1);
        threadTask.addTaskWithFixedDelay(task1);

        threadTask.addTaskRunOnce(20, new Runnable() {
            @Override
            public void run() {
                System.out.println("run once begin");
            }
        });

        threadTask.shutdown(false);

//
//		Thread.sleep(3000);
//		System.out.println("线程休息3秒后 加入任务2");
//		threadTask.addTask(task2());
//
//		System.out.println("运行中的任务数量: " + threadTask.getRunningTaskCount());
//
//		Thread.sleep(5000);
//		System.out.println("线程休息5秒后 移除任务1 flag: " + threadTask.removeTaskByTaskId(task1.getTaskId(), false));
//		System.out.println("运行中的任务数量: " + threadTask.getRunningTaskCount());
//
//		Thread.sleep(2000);
//		task1 = task1();
//		threadTask.addTask(task1);
//		System.out.println("线程休息2秒后 加入任务1");
//		System.out.println("运行中的任务数量: " + threadTask.getRunningTaskCount());
//
//		Thread.sleep(5000);
//		threadTask.clearAllTasks(false);
//		System.out.println("线程休息5秒后 移除所有任务");
//		System.out.println("运行中的任务数量: " + threadTask.getRunningTaskCount());
//
//		Thread.sleep(5000);
//		System.out.println("线程休息5秒后停止任务服务");
//		threadTask.shutdown();

        // Runtime runtime = Runtime.getRuntime();
        // Process p = runtime.exec("cd F:");
        // p.waitFor();
        // InputStream is = p.getInputStream();
        // byte[] bts = new byte[is.available()];
        // is.read(bts);
        // is.close();
        // System.out.println(new String(bts, "GBK"));
        //
        // p = runtime.exec("cd ../");
        // p.waitFor();
        // is = p.getInputStream();
        // bts = new byte[is.available()];
        // is.read(bts);
        // is.close();
        // System.out.println(new String(bts, "GBK"));
        //
        //
        // p = runtime.exec("pwd");
        // p.waitFor();
        // is = p.getInputStream();
        // bts = new byte[is.available()];
        // is.read(bts);
        // is.close();
        // System.out.println(new String(bts, "GBK"));


//		ThreadTask threadTask = new ThreadTask(10);
//		threadTask.addTaskRunOnce(new Runnable() {
//			public void run() {
//				for (int i = 0; i < 10; i++) {
//					try {
//						Thread.sleep(1000);
//						System.out.println(i);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		});
    }

    private static TaskEntity task1(final ThreadTask threadTask) {
        return new TaskEntity(5, 1, new Runnable() {
            @Override
            public void run() {
                System.out.println("============================");
                for (int i = 0; i < 5; i++) {
                    try {
                        System.out.println("task 1 running " + i + " TaskCount " + threadTask.getRunningTaskCount() + " " + TimeUtil.formatDate(TimeType.DEFAULT, new Date()));
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (i == 4) {
                        break;
                    }
                }
            }
        });
    }

    private static TaskEntity task2() {
        return new TaskEntity(0, 1, new Runnable() {
            @Override
            public void run() {
                System.out.println("task 2 running");
            }
        });
    }
}
