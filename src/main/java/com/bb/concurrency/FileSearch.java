package com.bb.concurrency;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * Created by admin on 2017/5/31.
 */
public class FileSearch implements Runnable {


    // 2. 声明一个私有 String 属性来储存搜索开始的时候的文件夹。
    private String initPath;

    // 3. 声明另一个私有 String 属性来储存我们要寻找的文件的扩展名。
    private String end;

    // 4. 声明一个私有 List 属性来储存我们找到的符合条件的文件的路径。
    private List<String> results;

    // 5. 最后，声明一个私有 Phaser 属性来控制任务的不同phaser的同步。
    private Phaser phaser;

    // 6. 实现类的构造函数，初始化类的属性们。它接收初始文件夹的路径，文件的扩展名，和phaser 作为参数。
    public FileSearch(String initPath, String end, Phaser phaser) {
        this.initPath = initPath;
        this.end = end;
        this.phaser = phaser;
        results = new ArrayList<String>();
    }

    // 7. 现在，你必须实现一些要给run() 方法用的辅助方法。第一个是 directoryProcess()
    // 方法。它接收File对象作为参数并处理全部的文件和子文件夹。对于每个文件夹，此方法会递归调用并传递文件夹作为参数。对于每个文件，此方法会调用fileProcess()
    // 方法。
    private void directoryProcess(File file) {

        File list[] = file.listFiles();
        if (list != null) {
            for (int i = 0; i < list.length; i++) {

                if (list[i].isDirectory()) {
                    directoryProcess(list[i]);
                } else {
                    fileProcess(list[i]);
                }
            }
        }
    }

    // 8. 现在，实现 fileProcess() 方法。它接收 File
    // 对象作为参数并检查它的扩展名是否是我们正在查找的。如果是，此方法会把文件的绝对路径写入结果列表内。
    private void fileProcess(File file) {
        if (file.getName().endsWith(end)) {
            results.add(file.getAbsolutePath());
        }
    }


    // 9. 现在，实现 filterResults()
    // 方法。不接收任何参数。它过滤在第一阶段获得的文件列表，并删除修改超过24小时的文件。首先，创建一个新的空list并获得当前时间。
    private void filterResults() {
        List<String> newResults = new ArrayList<String>();
        long actualDate = new Date().getTime();

        // 10. 然后，浏览结果list里的所有元素。对于每个路径，为文件创建File对象 go through all the elements
        // of the results list. For each path in the list of results, create a
        // File object for that file and get the last modified date for it.
        for (int i = 0; i < results.size(); i++) {
            File file = new File(results.get(i));
            long fileDate = file.lastModified();

            // 11. 然后， 对比与真实日期对比，如果相差小于一天，把文件的路径加入到新的结果列表。
            if (actualDate - fileDate < TimeUnit.MILLISECONDS.convert(1,
                    TimeUnit.DAYS)) {
                newResults.add(results.get(i));
            }
        }

        // 12. 最后，把旧的结果改为新的。
        results = newResults;
    }

    // 13. 现在，实现 checkResults() 方法。此方法在第一个和第二个phase的结尾被调用，并检查结果是否为空。此方法不接收任何参数。
    private boolean checkResults() {

        // 14. 首先，检查结果List的大小。如果为0，对象写信息到操控台表明情况，然后调用Phaser对象的
        // arriveAndDeregister() 方法通知此线程已经结束actual phase，并离开phased操作。
        if (results.isEmpty()) {
            System.out.printf( "%s: Phase %d: 0 results.\n", Thread.currentThread().getName(), phaser.getPhase() );
            System.out.printf( "%s: Phase %d: End.\n", Thread.currentThread().getName(), phaser.getPhase() );
            phaser.arriveAndDeregister();
            return false;

            // 15. 另一种情况，如果结果list有元素，那么对象写信息到操控台表明情况，调用 Phaser对象懂得
            // arriveAndAwaitAdvance() 方法并通知 actual phase，它会被阻塞直到phased
            // 操作的全部参与线程结束actual phase。

        } else {
            System.out.printf("%s: Phase %d: %d results.\n", Thread.currentThread().getName(), phaser.getPhase(), results.size());
            phaser.arriveAndAwaitAdvance();
            return true;
        }
    }

    // 16. 最好一个辅助方法是 showInfo() 方法，打印results list 的元素到操控台。
    private void showInfo() {
        for (int i = 0; i < results.size(); i++) {
            File file = new File(results.get(i));
            System.out.printf("%s: %s\n", Thread.currentThread().getName(), file.getAbsolutePath());
        }
        phaser.arriveAndAwaitAdvance();
    }



    @Override
    public void run() {

        phaser.arriveAndAwaitAdvance();
        System.out.printf("%s: Starting.\n", Thread.currentThread().getName());
        File file = new File(initPath);
        if (file.isDirectory()) {
            directoryProcess(file);
        }
        if (!checkResults()) {
            return;
        }
// 21. 使用filterResults() 方法过滤结果list。
        filterResults();

        // 22. 再次使用checkResults() 方法检查是否有结果。如果没有，结束线程的执行并返回keyword。
        if (!checkResults()) {
            return;
        }
        // 23. 使用 showInfo() 方法打印最终的结果list到操控台，撤销线程的登记，并打印信息表明线程的终结。
        showInfo();
        phaser.arriveAndDeregister();
        System.out.printf("%s: Work completed.\n", Thread.currentThread().getName());
    }


}
