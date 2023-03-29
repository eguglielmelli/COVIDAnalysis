package edu.upenn.cit594.logging;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Logger {

    private PrintWriter out;

    private Logger() {

    }

    private static Logger instance = new Logger();

    public static Logger getInstance() {
        return instance;
    }

    public void log(String msg) {
        this.out.println(msg);
        this.out.flush();
    }

    public void setOrChangeDestination(String fileName) {
        if(fileName == null) {
            return;
        }
        if(this.out != null) {
            this.out.close();
        }
        try {
            this.out = new PrintWriter(new FileWriter(fileName, true));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }
}
