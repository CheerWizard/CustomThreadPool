import io.TransferFactory;
import pools.CustomThreadPool;
import pools.ICustomThreadPool;
import utils.FileUtility;
import utils.ZipFileUtility;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public final class Launcher {

    public static void main(String[] args) {
//        threadPoolTest();
//        singleCopyTest();
//        multiple2SingleCopyTest();
//        singleZipTest();
//        singleUnzipTest();
//        multipleZipTest();
    }
    //test copying single file
    private static void singleCopyTest() {
        try {
            FileUtility.copy(new File("res/original.txt") , new File("res/copy.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    //test custom thread pool
    private static void threadPoolTest() {
        //create queue size - 3
        //number of threads - 4
        ICustomThreadPool threadPool = new CustomThreadPool(6,6);
        //prepare files
        final List<File> files = new LinkedList<>();
        for (int i = 1; i < 7; i++) files.add(new File("res/original_" + i + ".txt"));
        //execute thread pool for each file
        AtomicLong file_size = new AtomicLong(0);
        files.forEach(file -> {
            try {
                threadPool.submitTask(TransferFactory.createTransfer(
                        new FileInputStream(file) ,
                        new FileOutputStream(file) ,
                        file_size.get(),
                        file_size.get() + file.length()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            file_size.addAndGet(file.length());
        });
    }
    //test multiple in single copying
    private static void multiple2SingleCopyTest() {
        final List<File> files = new LinkedList<>();
        for (int i = 1; i < 7; i++) files.add(new File("res/original_" + i + ".txt"));
        FileUtility.copy(files.toArray(new File[]{}) , new File("res/copy.txt"));
    }
    //test single zipping
    private static void singleZipTest() {
        try {
            ZipFileUtility.zip(new File("res/original.txt") , null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //test multiple zipping
    private static void multipleZipTest() {
        final List<File> files = new LinkedList<>();
        for (int i = 1; i < 7; i++) files.add(new File("res/original_" + i + ".txt"));
        try {
            ZipFileUtility.zip(files.toArray(new File[]{}) , null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //test single unzipping
    private static void singleUnzipTest() {
        try {
            ZipFileUtility.unzip(new File("res/compressed.zip") , new File("res/"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
