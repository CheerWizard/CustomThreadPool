package utils;

import io.Transfer;
import io.TransferFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public final class FileUtility {
    //makes copy of single file
    public static synchronized void copy(final File original , final File destination) throws FileNotFoundException {
        //ask for transfer object and starts it's execution
        System.out.println("Start copying file...");
        final Transfer transfer = TransferFactory.createTransfer(new FileInputStream(original) ,
                new FileOutputStream(destination) ,
                0 ,
                original.length());
        ThreadFactory.createThread(transfer , "CopyThread").start();
        System.out.println("End copying file.");
    }
    //makes copy of several files in single file
    public static synchronized void copy(final File[] originals , final File destination) {
        System.out.println("Start copying files...");
        AtomicInteger i = new AtomicInteger();
        Arrays.stream(originals).forEach(original -> {
            try {
                final Transfer transfer = TransferFactory.createTransfer(new FileInputStream(original) ,
                        new FileOutputStream(destination) ,
                        0 ,
                        original.length());
                ThreadFactory.createThread(transfer , "Thread - " + i.incrementAndGet()).start();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        System.out.println("End copying files...");
    }
}
