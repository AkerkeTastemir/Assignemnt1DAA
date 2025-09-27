package me.akerke.assignment.metrics;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public final class CSVWriter implements AutoCloseable {

    private final PrintWriter pw;

    public CSVWriter(String path) throws IOException {
        File file = new File(path);
        System.out.println(file.getAbsolutePath());

        this.pw = new PrintWriter(new FileWriter(file, true));

        if (file.exists() && file.length() == 0) {
            headers();
        }
    }

    private void headers() {
        pw.println("Algorithm,Time,Depth,Comparisons");
    }

    public void write(String algo, long timeNs, int depth, long comps) {
        pw.printf("%s,%d,%d,%d,%d\n", algo, timeNs, depth, comps);
    }

    public void close(){ pw.close(); }

}