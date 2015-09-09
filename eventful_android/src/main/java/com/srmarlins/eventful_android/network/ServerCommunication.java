package com.srmarlins.eventful_android.network;

import com.srmarlins.eventful_android.APIConfiguration;
import com.srmarlins.eventful_android.EVDBAPIException;
import com.srmarlins.eventful_android.EVDBRuntimeException;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class ServerCommunication {
    private Integer read_timeout = Integer.valueOf(0);
    private Integer connect_timeout = Integer.valueOf(0);

    public ServerCommunication() {
        System.setProperty("java.net.useSystemProxies", "true");
    }

    public InputStream invokeMethod(String methodPath, Map<String, String> parameters) throws EVDBRuntimeException, EVDBAPIException {
        String key = APIConfiguration.getApiKey();
        boolean isGet = false;
        boolean isForm = false;
        if (((Map) parameters).get("read_timeout") != null) {
            this.read_timeout = Integer.valueOf(Integer.parseInt((String) ((Map) parameters).get("read_timeout")));
            ((Map) parameters).remove("read_timeout");
        }

        if (((Map) parameters).get("connect_timeout") != null) {
            this.connect_timeout = Integer.valueOf(Integer.parseInt((String) ((Map) parameters).get("connect_timeout")));
            ((Map) parameters).remove("connect_timeout");
        }

        if (key != null && key.length() != 0) {
            if (parameters == null) {
                parameters = new HashMap();
            }

            ((Map) parameters).put("app_key", key);
            String urlParameters = this.constructURLString((Map) parameters);
            String baseUrlString = APIConfiguration.getBaseURL() + methodPath;
            String urlToRequest = baseUrlString;
            if (urlParameters.length() < 900) {
                urlToRequest = baseUrlString + "?" + urlParameters;
                isGet = true;
            }

            if (((Map) parameters).containsKey("file_path")) {
                isGet = false;
                isForm = true;
            }

            InputStream in = null;

            try {
                URL e = new URL(urlToRequest);
                HttpURLConnection con = (HttpURLConnection) e.openConnection();
                if (isGet) {
                    con.setRequestMethod("GET");
                    con.setDoOutput(false);
                    con.setDoInput(true);
                    if (this.connect_timeout.intValue() > 0) {
                        con.setConnectTimeout(this.connect_timeout.intValue());
                    }

                    if (this.read_timeout.intValue() > 0) {
                        con.setReadTimeout(this.read_timeout.intValue());
                    }

                    con.connect();
                    in = con.getInputStream();
                } else if (isForm) {
                    con.setRequestMethod("POST");
                    con.setDoOutput(true);
                    con.setDoInput(true);
                    con.setUseCaches(false);
                    con.setDefaultUseCaches(false);
                    String boundary = MultipartFormOutputStream.createBoundary();
                    con.setRequestProperty("Accept", "*/*");
                    con.setRequestProperty("Content-Type", MultipartFormOutputStream.getContentType(boundary));
                    con.setRequestProperty("Connection", "Keep-Alive");
                    con.setRequestProperty("Cache-Control", "no-cache");
                    con.connect();
                    MultipartFormOutputStream mpout = new MultipartFormOutputStream(con.getOutputStream(), boundary);
                    Iterator mimeType = ((Map) parameters).entrySet().iterator();

                    while (mimeType.hasNext()) {
                        Entry fullFilePath = (Entry) mimeType.next();
                        mpout.writeField((String) fullFilePath.getKey(), (String) fullFilePath.getValue());
                    }

                    String mimeType1 = "image/jpg";
                    String fullFilePath1 = (String) ((Map) parameters).get("file_path");
                    if (fullFilePath1.endsWith("gif")) {
                        mimeType1 = "image/gif";
                    } else if (fullFilePath1.endsWith("png")) {
                        mimeType1 = "image/png";
                    } else if (fullFilePath1.endsWith("tiff")) {
                        mimeType1 = "image/tiff";
                    }

                    mpout.writeFile("image_file", mimeType1, new File((String) ((Map) parameters).get("file_path")));
                    mpout.flush();
                    mpout.close();
                    in = con.getInputStream();
                } else {
                    con.setRequestMethod("POST");
                    con.setDoOutput(true);
                    con.setDoInput(true);
                    con.connect();
                    OutputStream out = con.getOutputStream();
                    byte[] buff = urlParameters.getBytes("UTF8");
                    out.write(buff);
                    out.flush();
                    out.close();
                    in = con.getInputStream();
                }

                return in;
            } catch (Exception var18) {
                throw new EVDBRuntimeException("URL or network communication error: " + var18.getMessage(), var18);
            }
        } else {
            throw new EVDBRuntimeException("No API Key specified");
        }
    }

    private String constructURLString(Map<String, String> parameters) {
        String url = "";
        boolean first = true;
        Iterator i$ = parameters.entrySet().iterator();

        while (i$.hasNext()) {
            Entry entry = (Entry) i$.next();

            try {
                if (entry.getValue() != null && entry.getKey() != null && ((String) entry.getValue()).length() != 0) {
                    if (first) {
                        first = false;
                    } else {
                        url = url + "&";
                    }

                    url = url + URLEncoder.encode((String) entry.getKey(), "UTF-8") + "=" + URLEncoder.encode((String) entry.getValue(), "UTF-8");
                }
            } catch (UnsupportedEncodingException var7) {
                var7.printStackTrace();
                throw new Error("Unsupported Encoding Exception", var7);
            }
        }

        return url;
    }
}

