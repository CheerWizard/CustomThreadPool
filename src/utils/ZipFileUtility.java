package utils;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public final class ZipFileUtility {

    private static ZipOutputStream zipOutputStream;
    private final static String defaultZipName = "res/compressed.zip";
    //method , which will format file into .zip format
    public static synchronized void zip(@NotNull File file , String zippedFileName) throws IOException {
        zipOutputStream = zippedFileName == null
                ? new ZipOutputStream(new FileOutputStream(defaultZipName))
                : new ZipOutputStream(new FileOutputStream(zippedFileName));
        //starts single zipping
        singleZipping(file , file.getName());
        //close
        zipOutputStream.close();
    }
    //method , which will format several files into single .zip format
    public static synchronized void zip(@NotNull File[] files , String zippedFileName) throws IOException {
        zipOutputStream = zippedFileName == null
                ? new ZipOutputStream(new FileOutputStream(defaultZipName))
                : new ZipOutputStream(new FileOutputStream(zippedFileName));
        //start multiple zipping
        Arrays.stream(files).forEach(file -> {
            try {
                //starts single zipping
                singleZipping(file , file.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        //close
        zipOutputStream.close();
    }
    //method , which unzip particular .zip file in appropriate destination
    public static synchronized void unzip(File zipFile , File destination) throws IOException {
        final ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFile));
        ZipEntry zipEntry = zipInputStream.getNextEntry();
        while (zipEntry != null) {
            //create new file and out stream
            FileOutputStream fileOutputStream = new FileOutputStream(newFile(destination , zipEntry));
            //read zip and write files from targeted entry
            readZipAndWriteFiles(zipInputStream , fileOutputStream);
            //close out stream
            fileOutputStream.close();
            //move to next zip entry
            zipEntry = zipInputStream.getNextEntry();
        }
        //close
        zipInputStream.closeEntry();
        zipInputStream.close();
    }
    //method , which writes zip file
    private static synchronized void readFilesAndWriteZipFile(@NotNull FileInputStream fileInputStream) throws IOException {
        final byte[] bytes = new byte[1024];
        int length;
        while((length = fileInputStream.read(bytes)) >= 0) zipOutputStream.write(bytes, 0, length);
    }
    //method , which writes files
    private static synchronized void readZipAndWriteFiles(@NotNull ZipInputStream zipInputStream , FileOutputStream fileOutputStream) throws IOException {
        final byte[] bytes = new byte[1024];
        int length;
        while((length = zipInputStream.read(bytes)) >= 0) fileOutputStream.write(bytes, 0, length);
    }
    //method , which zips single file
    private static synchronized void singleZipping(@NotNull File file , String fileName) throws IOException {
        if (file.isHidden()) return;
        if (file.isDirectory()) {
            //check file full name
            if (fileName.endsWith("/")) zipOutputStream.putNextEntry(new ZipEntry(fileName));
            else zipOutputStream.putNextEntry(new ZipEntry(fileName + "/"));
            //close
            zipOutputStream.closeEntry();
            //iterate through list of files , if it exists
            Arrays.stream(Objects.requireNonNull(file.listFiles())).forEach(childFile -> {
                try {
                    singleZipping(childFile , fileName + "/" + childFile.getName());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            return;
        }
        //create in stream for reading
        final FileInputStream fileInputStream = new FileInputStream(file);
        //create zip entry for file
        final ZipEntry zipEntry = new ZipEntry(fileName);
        //put entry to out stream
        zipOutputStream.putNextEntry(zipEntry);
        //write file
        readFilesAndWriteZipFile(fileInputStream);
        //close
        fileInputStream.close();
    }

    private synchronized static File newFile(File destinationDir, @NotNull ZipEntry zipEntry) throws IOException {
        final File destFile = new File(destinationDir, zipEntry.getName());
        if (!destFile.getCanonicalPath().startsWith(destinationDir.getCanonicalPath() + File.separator))
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        return destFile;
    }
}
