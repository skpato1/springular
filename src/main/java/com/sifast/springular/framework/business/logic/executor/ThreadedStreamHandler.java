package com.sifast.springular.framework.business.logic.executor;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;


class ThreadedStreamHandler extends Thread {

    InputStream inputStream;
    String adminPassword;
    OutputStream outputStream;
    PrintWriter printWriter;
    StringBuilder outputBuffer = new StringBuilder();
    private boolean sudoIsRequested = false;


    ThreadedStreamHandler(InputStream inputStream) {
        this.inputStream = inputStream;
    }


    ThreadedStreamHandler(InputStream inputStream, OutputStream outputStream, String adminPassword) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.printWriter = new PrintWriter(outputStream);
        this.adminPassword = adminPassword;
        this.sudoIsRequested = true;
    }

    @Override
    public void run() {

        if (sudoIsRequested) {
            printWriter.println(adminPassword);
            printWriter.flush();
        }

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                outputBuffer.append(line).append("\n");
            }
        } catch (Exception ioe) {
            ioe.printStackTrace();
        }
        // ignore this one
    }

    @SuppressWarnings("unused")
    private void doSleep(long millis) throws InterruptedException {
        Thread.sleep(millis);
    }

    public StringBuilder getOutputBuffer() {
        return outputBuffer;
    }

}
