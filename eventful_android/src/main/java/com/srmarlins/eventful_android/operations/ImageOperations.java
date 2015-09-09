package com.srmarlins.eventful_android.operations;

import com.srmarlins.eventful_android.APIConfiguration;
import com.srmarlins.eventful_android.EVDBAPIException;
import com.srmarlins.eventful_android.EVDBRuntimeException;
import com.srmarlins.eventful_android.data.response.GenericResponse;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;

public class ImageOperations extends BaseOperations {
    private static final String IMAGES_REMOVE = "/images/remove";
    private static final String IMAGES_NEW = "/images/new";

    public ImageOperations() {
    }

    public String uploadImageFromURL(String url, String caption) throws EVDBRuntimeException, EVDBAPIException {
        HashMap params = new HashMap();
        params.put("image_url", url);
        params.put("caption", caption);
        params.put("user", APIConfiguration.getEvdbUser());
        params.put("password", APIConfiguration.getEvdbPassword());
        InputStream is = this.serverCommunication.invokeMethod("/images/new", params);
        GenericResponse gr = (GenericResponse) this.unmarshallRequest(GenericResponse.class, is);
        return gr.getId();
    }

    public String uploadImageFromLocalFile(File file, String caption) throws EVDBRuntimeException, EVDBAPIException {
        HashMap params = new HashMap();
        params.put("caption", caption);
        params.put("user", APIConfiguration.getEvdbUser());
        params.put("password", APIConfiguration.getEvdbPassword());
        params.put("file_path", file.toString());
        InputStream is = this.serverCommunication.invokeMethod("/images/new", params);
        GenericResponse gr = (GenericResponse) this.unmarshallRequest(GenericResponse.class, is);
        return gr.getId();
    }
}

