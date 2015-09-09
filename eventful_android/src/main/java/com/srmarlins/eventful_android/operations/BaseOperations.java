package com.srmarlins.eventful_android.operations;

import com.srmarlins.eventful_android.APIConfiguration;
import com.srmarlins.eventful_android.EVDBAPIException;
import com.srmarlins.eventful_android.EVDBRuntimeException;
import com.srmarlins.eventful_android.data.Comment;
import com.srmarlins.eventful_android.data.Image;
import com.srmarlins.eventful_android.data.Link;
import com.srmarlins.eventful_android.data.Property;
import com.srmarlins.eventful_android.data.Tag;
import com.srmarlins.eventful_android.data.response.GenericErrorResponse;
import com.srmarlins.eventful_android.data.response.GenericResponse;
import com.srmarlins.eventful_android.network.ServerCommunication;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public abstract class BaseOperations {
    protected ServerCommunication serverCommunication = new ServerCommunication();

    public BaseOperations() {
    }


    protected Object listTags(Class clazz, String method, String id) throws EVDBRuntimeException, EVDBAPIException {
        HashMap params = new HashMap();
        params.put("id", id);
        InputStream is = this.serverCommunication.invokeMethod(method, params);
        return this.unmarshallRequest(clazz, is);
    }

    protected Object listProperties(Class clazz, String method, String id) throws EVDBRuntimeException, EVDBAPIException {
        HashMap params = new HashMap();
        params.put("id", id);
        InputStream is = this.serverCommunication.invokeMethod(method, params);
        return this.unmarshallRequest(clazz, is);
    }

    protected void modifyTags(String method, String id, List<Tag> tagList) throws EVDBRuntimeException, EVDBAPIException {
        HashMap params = new HashMap();
        params.put("user", APIConfiguration.getEvdbUser());
        params.put("password", APIConfiguration.getEvdbPassword());
        params.put("id", id);
        String tagString = "";

        Tag t;
        for (Iterator is = tagList.iterator(); is.hasNext(); tagString = tagString + t.getTitle() + " ") {
            t = (Tag) is.next();
        }

        params.put("tags", tagString);
        InputStream is1 = this.serverCommunication.invokeMethod(method, params);
        this.unmarshallRequest(GenericResponse.class, is1);
    }

    protected int addProperty(String method, String id, Property property) throws EVDBRuntimeException, EVDBAPIException {
        HashMap params = new HashMap();
        params.put("user", APIConfiguration.getEvdbUser());
        params.put("password", APIConfiguration.getEvdbPassword());
        params.put("id", id);
        params.put("name", property.getName());
        params.put("value", property.getValue());
        InputStream is = this.serverCommunication.invokeMethod(method, params);
        GenericResponse go = (GenericResponse) this.unmarshallRequest(GenericResponse.class, is);
        property.setId(go.getPropertyId());
        return go.getPropertyId();
    }

    protected void deleteProperty(String method, String id, Property property) throws EVDBRuntimeException, EVDBAPIException {
        HashMap params = new HashMap();
        params.put("user", APIConfiguration.getEvdbUser());
        params.put("password", APIConfiguration.getEvdbPassword());
        params.put("id", id);
        params.put("name", property.getName());
        params.put("property_id", String.valueOf(property.getId()));
        InputStream is = this.serverCommunication.invokeMethod(method, params);
        this.unmarshallRequest(GenericResponse.class, is);
    }

    protected void addComment(String method, String id, Comment comment) throws EVDBRuntimeException, EVDBAPIException {
        HashMap params = new HashMap();
        params.put("user", APIConfiguration.getEvdbUser());
        params.put("password", APIConfiguration.getEvdbPassword());
        params.put("id", id);
        params.put("comment", comment.getText());
        InputStream is = this.serverCommunication.invokeMethod(method, params);
        this.unmarshallRequest(GenericResponse.class, is);
    }

    protected void modifyComment(String method, Comment comment) throws EVDBRuntimeException, EVDBAPIException {
        HashMap params = new HashMap();
        params.put("user", APIConfiguration.getEvdbUser());
        params.put("password", APIConfiguration.getEvdbPassword());
        params.put("comment_id", String.valueOf(comment.getId()));
        params.put("comment", comment.getText());
        InputStream is = this.serverCommunication.invokeMethod(method, params);
        this.unmarshallRequest(GenericResponse.class, is);
    }

    protected void deleteComment(String method, Comment comment) throws EVDBRuntimeException, EVDBAPIException {
        HashMap params = new HashMap();
        params.put("user", APIConfiguration.getEvdbUser());
        params.put("password", APIConfiguration.getEvdbPassword());
        params.put("comment_id", String.valueOf(comment.getId()));
        InputStream is = this.serverCommunication.invokeMethod(method, params);
        this.unmarshallRequest(GenericResponse.class, is);
    }

    protected void deleteImage(String method, String id, Image image) throws EVDBRuntimeException, EVDBAPIException {
        HashMap params = new HashMap();
        params.put("user", APIConfiguration.getEvdbUser());
        params.put("password", APIConfiguration.getEvdbPassword());
        params.put("id", id);
        params.put("image_id", String.valueOf(image.getId()));
        InputStream is = this.serverCommunication.invokeMethod(method, params);
        this.unmarshallRequest(GenericResponse.class, is);
    }

    protected void addImage(String method, String id, Image image) throws EVDBRuntimeException, EVDBAPIException {
        HashMap params = new HashMap();
        params.put("user", APIConfiguration.getEvdbUser());
        params.put("password", APIConfiguration.getEvdbPassword());
        params.put("id", id);
        params.put("image_id", String.valueOf(image.getId()));
        InputStream is = this.serverCommunication.invokeMethod(method, params);
        this.unmarshallRequest(GenericResponse.class, is);
    }

    protected void addLink(String method, String id, Link link) throws EVDBRuntimeException, EVDBAPIException {
        HashMap params = new HashMap();
        params.put("user", APIConfiguration.getEvdbUser());
        params.put("password", APIConfiguration.getEvdbPassword());
        params.put("id", id);
        params.put("link", link.getUrl());
        params.put("description", link.getDescription());
        params.put("link_type_id", String.valueOf(link.getType()));
        InputStream is = this.serverCommunication.invokeMethod(method, params);
        this.unmarshallRequest(GenericResponse.class, is);
    }

    protected void deleteLink(String method, Link link) throws EVDBRuntimeException, EVDBAPIException {
        HashMap params = new HashMap();
        params.put("user", APIConfiguration.getEvdbUser());
        params.put("password", APIConfiguration.getEvdbPassword());
        params.put("link_id", String.valueOf(link.getId()));
        InputStream is = this.serverCommunication.invokeMethod(method, params);
        this.unmarshallRequest(GenericResponse.class, is);
    }

    protected Object unmarshallRequest(Class clazz, InputStream is) throws EVDBAPIException {
        Object o = null;
        try {
            Serializer serializer = new Persister(APIConfiguration.getRegistryMatcher());
            o = serializer.read(clazz, is, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (o instanceof GenericErrorResponse) {
            throw new EVDBAPIException((GenericErrorResponse) o);
        } else {
            if (clazz.getCanonicalName().equals(GenericResponse.class.getCanonicalName())) {
                GenericResponse gr = (GenericResponse) o;
                if (!gr.getStatus().equals("ok")) {
                    throw new EVDBAPIException("Command failed: " + gr.getMessage());
                }
            }

            return o;
        }
    }
}

