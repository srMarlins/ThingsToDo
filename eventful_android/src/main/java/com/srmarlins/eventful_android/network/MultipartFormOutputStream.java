package com.srmarlins.eventful_android.network;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

class MultipartFormOutputStream {
    private static final String NEWLINE = "\r\n";
    private static final String PREFIX = "--";
    private DataOutputStream out = null;
    private String boundary = null;

    MultipartFormOutputStream(OutputStream os, String boundary) {
        if (os == null) {
            throw new IllegalArgumentException("Output stream is required.");
        } else if (boundary != null && boundary.length() != 0) {
            this.out = new DataOutputStream(os);
            this.boundary = boundary;
        } else {
            throw new IllegalArgumentException("Boundary stream is required.");
        }
    }

    public void writeField(String name, boolean value) throws IOException {
        this.writeField(name, (new Boolean(value)).toString());
    }

    public void writeField(String name, double value) throws IOException {
        this.writeField(name, Double.toString(value));
    }

    public void writeField(String name, float value) throws IOException {
        this.writeField(name, Float.toString(value));
    }

    public void writeField(String name, long value) throws IOException {
        this.writeField(name, Long.toString(value));
    }

    public void writeField(String name, int value) throws IOException {
        this.writeField(name, Integer.toString(value));
    }

    public void writeField(String name, short value) throws IOException {
        this.writeField(name, Short.toString(value));
    }

    public void writeField(String name, char value) throws IOException {
        this.writeField(name, (new Character(value)).toString());
    }

    public void writeField(String name, String value) throws IOException {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        } else {
            if (value == null) {
                value = "";
            }

            this.out.writeBytes("--");
            this.out.writeBytes(this.boundary);
            this.out.writeBytes("\r\n");
            this.out.writeBytes("Content-Disposition: form-data; name=\"" + name + "\"");
            this.out.writeBytes("\r\n");
            this.out.writeBytes("\r\n");
            this.out.writeBytes(value);
            this.out.writeBytes("\r\n");
            this.out.flush();
        }
    }

    public void writeFile(String name, String mimeType, File file) throws IOException {
        if (file == null) {
            throw new IllegalArgumentException("File cannot be null.");
        } else if (!file.exists()) {
            throw new IllegalArgumentException("File does not exist.");
        } else if (file.isDirectory()) {
            throw new IllegalArgumentException("File cannot be a directory.");
        } else {
            this.writeFile(name, mimeType, file.getCanonicalPath(), (InputStream) (new FileInputStream(file)));
        }
    }

    public void writeFile(String name, String mimeType, String fileName, InputStream is) throws IOException {
        if (is == null) {
            throw new IllegalArgumentException("Input stream cannot be null.");
        } else if (fileName != null && fileName.length() != 0) {
            this.out.writeBytes("--");
            this.out.writeBytes(this.boundary);
            this.out.writeBytes("\r\n");
            this.out.writeBytes("Content-Disposition: form-data; name=\"" + name + "\"; filename=\"" + fileName + "\"");
            this.out.writeBytes("\r\n");
            if (mimeType != null) {
                this.out.writeBytes("Content-Type: " + mimeType);
                this.out.writeBytes("\r\n");
            }

            this.out.writeBytes("\r\n");
            byte[] data = new byte[1024];
            boolean r = false;

            int r1;
            while ((r1 = is.read(data, 0, data.length)) != -1) {
                this.out.write(data, 0, r1);
            }

            try {
                is.close();
            } catch (Exception var8) {
                ;
            }

            this.out.writeBytes("\r\n");
            this.out.flush();
        } else {
            throw new IllegalArgumentException("File name cannot be null or empty.");
        }
    }

    void writeFile(String name, String mimeType, String fileName, byte[] data) throws IOException {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        } else if (fileName != null && fileName.length() != 0) {
            this.out.writeBytes("--");
            this.out.writeBytes(this.boundary);
            this.out.writeBytes("\r\n");
            this.out.writeBytes("Content-Disposition: form-data; name=\"" + name + "\"; filename=\"" + fileName + "\"");
            this.out.writeBytes("\r\n");
            if (mimeType != null) {
                this.out.writeBytes("Content-Type: " + mimeType);
                this.out.writeBytes("\r\n");
            }

            this.out.writeBytes("\r\n");
            this.out.write(data, 0, data.length);
            this.out.writeBytes("\r\n");
            this.out.flush();
        } else {
            throw new IllegalArgumentException("File name cannot be null or empty.");
        }
    }

    public void flush() {
    }

    public void close() throws IOException {
        this.out.writeBytes("--");
        this.out.writeBytes(this.boundary);
        this.out.writeBytes("--");
        this.out.writeBytes("\r\n");
        this.out.flush();
        this.out.close();
    }

    public String getBoundary() {
        return this.boundary;
    }

    public static URLConnection createConnection(URL url) throws IOException {
        URLConnection urlConn = url.openConnection();
        if (urlConn instanceof HttpURLConnection) {
            HttpURLConnection httpConn = (HttpURLConnection) urlConn;
            httpConn.setRequestMethod("POST");
        }

        urlConn.setDoInput(true);
        urlConn.setDoOutput(true);
        urlConn.setUseCaches(false);
        urlConn.setDefaultUseCaches(false);
        return urlConn;
    }

    public static String createBoundary() {
        return "--------------------" + Long.toString(System.currentTimeMillis(), 16);
    }

    public static String getContentType(String boundary) {
        return "multipart/form-data; boundary=" + boundary;
    }
}

