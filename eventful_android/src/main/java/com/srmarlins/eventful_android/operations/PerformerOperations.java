package com.srmarlins.eventful_android.operations;

import com.srmarlins.eventful_android.APIConfiguration;
import com.srmarlins.eventful_android.EVDBAPIException;
import com.srmarlins.eventful_android.EVDBRuntimeException;
import com.srmarlins.eventful_android.data.Comment;
import com.srmarlins.eventful_android.data.Image;
import com.srmarlins.eventful_android.data.Link;
import com.srmarlins.eventful_android.data.Performer;
import com.srmarlins.eventful_android.data.Property;
import com.srmarlins.eventful_android.data.SearchResult;
import com.srmarlins.eventful_android.data.Tag;
import com.srmarlins.eventful_android.data.request.PerformerSearchRequest;
import com.srmarlins.eventful_android.data.response.GenericResponse;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class PerformerOperations extends BaseOperations {
    private static final String PERFORMERS_GET = "/performers/get";
    private static final String PERFORMERS_NEW = "/performers/new";
    private static final String PERFORMERS_MODIFY = "/performers/modify";
    private static final String PERFORMERS_WITHDRAW = "/performers/withdraw";
    private static final String PERFORMERS_RESTORE = "/performers/restore";
    private static final String PERFORMERS_SEARCH = "/performers/search";
    private static final String PERFORMERS_TAGS_LIST = "/performers/tags/list";
    private static final String PERFORMERS_TAGS_NEW = "/performers/tags/add";
    private static final String PERFORMERS_TAGS_DELETE = "/performers/tags/remove";
    private static final String PERFORMERS_COMMENTS_NEW = "/performers/comments/new";
    private static final String PERFORMERS_COMMENTS_MODIFY = "/performers/comments/modify";
    private static final String PERFORMERS_COMMENTS_DELETE = "/performers/comments/delete";
    private static final String PERFORMERS_LINKS_NEW = "/performers/links/add";
    private static final String PERFORMERS_LINKS_DELETE = "/performers/links/remove";
    private static final String PERFORMERS_IMAGES_ADD = "/performers/links/add";
    private static final String PERFORMERS_IMAGES_REMOVE = "/performers/links/remove";
    private static final String PERFORMERS_PROPERTIES_ADD = "/performers/properties/add";
    private static final String PERFORMERS_PROPERTIES_REMOVE = "/performers/properties/remove";
    private static final String PERFORMERS_PROPERTIES_LIST = "/performers/properties/list";

    public PerformerOperations() {
    }

    public Performer get(String spid) throws EVDBRuntimeException, EVDBAPIException {
        HashMap params = new HashMap();
        params.put("id", spid);
        InputStream is = this.serverCommunication.invokeMethod("/performers/get", params);
        return (Performer) this.unmarshallRequest(Performer.class, is);
    }

    public Performer create(Performer p) throws EVDBRuntimeException, EVDBAPIException {
        if (p.getSpid() != null) {
            throw new EVDBRuntimeException("Create called on an performer with an existing SPID");
        } else {
            return this.createOrModify(p);
        }
    }

    public Performer modify(Performer p) throws EVDBRuntimeException, EVDBAPIException {
        if (p.getSpid() == null) {
            throw new EVDBRuntimeException("Modify called on an performer without an SPID");
        } else {
            return this.createOrModify(p);
        }
    }

    public void withdraw(String spid, String withdrawNote) throws EVDBRuntimeException, EVDBAPIException {
        HashMap params = new HashMap();
        params.put("id", spid);
        params.put("note", withdrawNote);
        params.put("user", APIConfiguration.getEvdbUser());
        params.put("password", APIConfiguration.getEvdbPassword());
        InputStream is = this.serverCommunication.invokeMethod("/performers/withdraw", params);
        this.unmarshallRequest(GenericResponse.class, is);
    }

    public void restore(String spid) throws EVDBRuntimeException, EVDBAPIException {
        HashMap params = new HashMap();
        params.put("id", spid);
        params.put("user", APIConfiguration.getEvdbUser());
        params.put("password", APIConfiguration.getEvdbPassword());
        InputStream is = this.serverCommunication.invokeMethod("/performers/restore", params);
        this.unmarshallRequest(GenericResponse.class, is);
    }

    public void addTags(String spid, List<Tag> tagList) throws EVDBRuntimeException, EVDBAPIException {
        super.modifyTags("/performers/tags/add", spid, tagList);
    }

    public void deleteTags(String spid, List<Tag> tagList) throws EVDBRuntimeException, EVDBAPIException {
        super.modifyTags("/performers/tags/remove", spid, tagList);
    }

    public List<Tag> getTags(String spid) throws EVDBRuntimeException, EVDBAPIException {
        Performer e = (Performer) this.listTags(Performer.class, "/performers/tags/list", spid);
        return e.getTags();
    }

    public void addComment(String spid, Comment comment) throws EVDBRuntimeException, EVDBAPIException {
        super.addComment("/performers/comments/new", spid, comment);
    }

    public void modifyComment(Comment comment) throws EVDBRuntimeException, EVDBAPIException {
        super.modifyComment("/performers/comments/modify", comment);
    }

    public void deleteComment(Comment comment) throws EVDBRuntimeException, EVDBAPIException {
        super.deleteComment("/performers/comments/delete", comment);
    }

    public void addLink(String spid, Link link) throws EVDBRuntimeException, EVDBAPIException {
        super.addLink("/performers/links/add", spid, link);
    }

    public void deleteLink(String spid, Link link) throws EVDBRuntimeException, EVDBAPIException {
        HashMap params = new HashMap();
        params.put("user", APIConfiguration.getEvdbUser());
        params.put("password", APIConfiguration.getEvdbPassword());
        params.put("link_id", String.valueOf(link.getId()));
        params.put("id", spid);
        InputStream is = this.serverCommunication.invokeMethod("/performers/links/remove", params);
        this.unmarshallRequest(GenericResponse.class, is);
    }

    public void addImage(String spid, Image image) throws EVDBRuntimeException, EVDBAPIException {
        super.addImage("/performers/links/add", spid, image);
    }

    public void deleteImage(String spid, Image image) throws EVDBRuntimeException, EVDBAPIException {
        super.deleteImage("/performers/links/remove", spid, image);
    }

    public void deleteProperty(String spid, Property property) throws EVDBRuntimeException, EVDBAPIException {
        super.deleteProperty("/performers/properties/remove", spid, property);
    }

    public int addProperty(String spid, Property property) throws EVDBRuntimeException, EVDBAPIException {
        return super.addProperty("/performers/properties/add", spid, property);
    }

    public List<Property> getProperties(String spid) throws EVDBRuntimeException, EVDBAPIException {
        Performer p = (Performer) this.listProperties(Performer.class, "/performers/properties/list", spid);
        return p.getProperties();
    }

    public SearchResult search(PerformerSearchRequest searchRequest) throws EVDBRuntimeException, EVDBAPIException {
        HashMap params = new HashMap();
        params.put("keywords", searchRequest.getKeywords());
        params.put("location", searchRequest.getLocation());
        params.put("within", String.valueOf(searchRequest.getLocationRadius()));
        params.put("units", searchRequest.getLocationUnits());
        params.put("sort_order", searchRequest.getSortOrder().toLowerCase());
        params.put("sort_direction", searchRequest.getSortDirection().toString().toLowerCase());
        params.put("page_size", String.valueOf(searchRequest.getPageSize()));
        params.put("page_number", String.valueOf(searchRequest.getPageNumber()));
        params.put("user", APIConfiguration.getEvdbUser());
        params.put("password", APIConfiguration.getEvdbPassword());
        InputStream is = this.serverCommunication.invokeMethod("/performers/search", params);
        return (SearchResult) this.unmarshallRequest(SearchResult.class, is);
    }

    private Performer createOrModify(Performer p) throws EVDBRuntimeException, EVDBAPIException {
        HashMap params = new HashMap();
        params.put("name", p.getName());
        params.put("short_bio", p.getShortBio());
        params.put("long_bio", p.getLongBio());
        params.put("user", APIConfiguration.getEvdbUser());
        params.put("password", APIConfiguration.getEvdbPassword());
        params.put("id", p.getSpid());
        String tagString = "";
        if (p.getTags() != null) {
            Tag is;
            for (Iterator method = p.getTags().iterator(); method.hasNext(); tagString = tagString + is.getTitle() + " ") {
                is = (Tag) method.next();
            }

            params.put("tags", tagString);
        }

        String method1 = p.getSpid() == null ? "/performers/new" : "/performers/modify";
        InputStream is1 = this.serverCommunication.invokeMethod(method1, params);
        GenericResponse gr = (GenericResponse) this.unmarshallRequest(GenericResponse.class, is1);
        p.setSpid(gr.getId());
        return p;
    }
}

