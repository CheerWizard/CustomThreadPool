package io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class Transfer implements Runnable {

    private FileChannel inChannel;
    private FileChannel outChannel;
    private long position, count;

    public Transfer() {
    }

    public Transfer(FileChannel inChannel, FileChannel outChannel, long position, long count) {
        this.inChannel = inChannel;
        this.outChannel = outChannel;
        this.position = position;
        this.count = count;
    }

    public void setFileInputStream(FileInputStream fileInputStream) {
        inChannel = fileInputStream.getChannel();
    }

    public void setFileOutputStream(FileOutputStream fileOutputStream) {
        outChannel = fileOutputStream.getChannel();
    }

    public FileChannel getInChannel() {
        return inChannel;
    }

    public void setInChannel(FileChannel inChannel) {
        this.inChannel = inChannel;
    }

    public FileChannel getOutChannel() {
        return outChannel;
    }

    public void setOutChannel(FileChannel outChannel) {
        this.outChannel = outChannel;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public void run() {
        try {
            inChannel.transferTo(position, count, outChannel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}