package io;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public final class TransferFactory {
    //creates transfer object
    public static synchronized Transfer createTransfer(FileInputStream fileInputStream ,
                                                       FileOutputStream fileOutputStream ,
                                                       long position ,
                                                       long count) {
        final Transfer transfer = new Transfer();
        transfer.setCount(count);
        transfer.setFileInputStream(fileInputStream);
        transfer.setFileOutputStream(fileOutputStream);
        transfer.setPosition(position);
        return transfer;
    }
}
