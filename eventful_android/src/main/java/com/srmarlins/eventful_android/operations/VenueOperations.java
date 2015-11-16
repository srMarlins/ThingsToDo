package com.srmarlins.eventful_android.operations;

import com.srmarlins.eventful_android.APIConfiguration;
import com.srmarlins.eventful_android.EVDBAPIException;
import com.srmarlins.eventful_android.EVDBRuntimeException;
import com.srmarlins.eventful_android.data.Comment;
import com.srmarlins.eventful_android.data.Image;
import com.srmarlins.eventful_android.data.Link;
import com.srmarlins.eventful_android.data.Property;
import com.srmarlins.eventful_android.data.SearchResult;
import com.srmarlins.eventful_android.data.Tag;
import com.srmarlins.eventful_android.data.Venue;
import com.srmarlins.eventful_android.data.request.VenueSearchRequest;
import com.srmarlins.eventful_android.data.response.GenericResponse;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class VenueOperations extends BaseOperations {
    private static final String VENUES_GET = "/venues/get";
    private static final String VENUES_NEW = "/venues/new";
    private static final String VENUES_MODIFY = "/venues/modify";
    private static final String VENUES_WITHDRAW = "/venues/withdraw";
    private static final String VENUES_RESTORE = "/venues/restore";
    private static final String VENUES_SEARCH = "/venues/search";
    private static final String VENUES_TAGS_LIST = "/venues/tags/list";
    private static final String VENUES_TAGS_NEW = "/venues/tags/new";
    private static final String VENUES_TAGS_DELETE = "/venues/tags/delete";
    private static final String VENUES_COMMENTS_NEW = "/venues/comments/new";
    private static final String VENUES_COMMENTS_MODIFY = "/venues/comments/modify";
    private static final String VENUES_COMMENTS_DELETE = "/venues/comments/delete";
    private static final String VENUES_LINKS_NEW = "/venues/links/new";
    private static final String VENUES_LINKS_DELETE = "/venues/links/delete";
    private static final String VENUES_IMAGES_ADD = "/venues/links/add";
    private static final String VENUES_IMAGES_REMOVE = "/venues/links/remove";
    private static final String VENUES_PROPERTIES_ADD = "/venues/properties/add";
    private static final String VENUES_PROPERTIES_REMOVE = "/venues/properties/remove";

    public VenueOperations() {
    }

    public Venue get(String svid) throws EVDBRuntimeException, EVDBAPIException {
        HashMap params = new HashMap();
        params.put("id", svid);
        InputStream is = this.serverCommunication.invokeMethod("/venues/get", params);
        return (Venue) this.unmarshallRequest(Venue.class, is);
    }

    public Venue create(Venue v) throws EVDBRuntimeException, EVDBAPIException {
        if (v.getSvid() != null) {
            throw new EVDBRuntimeException("Create called on an venue with an existing SVID");
        } else {
            return this.createOrModify(v);
        }
    }

    public Venue modify(Venue v) throws EVDBRuntimeException, EVDBAPIException {
        if (v.getSvid() == null) {
            throw new EVDBRuntimeException("Modify called on an venue without an SVID");
        } else {
            return this.createOrModify(v);
        }
    }

    public void withdraw(String svid, String withdrawNote) throws EVDBRuntimeException, EVDBAPIException {
        HashMap params = new HashMap();
        params.put("id", svid);
        params.put("note", withdrawNote);
        params.put("user", APIConfiguration.getEvdbUser());
        params.put("password", APIConfiguration.getEvdbPassword());
        InputStream is = this.serverCommunication.invokeMethod("/venues/withdraw", params);
        this.unmarshallRequest(GenericResponse.class, is);
    }

    public void restore(String svid) throws EVDBRuntimeException, EVDBAPIException {
        HashMap params = new HashMap();
        params.put("id", svid);
        params.put("user", APIConfiguration.getEvdbUser());
        params.put("password", APIConfiguration.getEvdbPassword());
        InputStream is = this.serverCommunication.invokeMethod("/venues/restore", params);
        this.unmarshallRequest(GenericResponse.class, is);
    }

    public void addTags(String svid, List<Tag> tagList) throws EVDBRuntimeException, EVDBAPIException {
        super.modifyTags("/venues/tags/new", svid, tagList);
    }

    public void deleteTags(String svid, List<Tag> tagList) throws EVDBRuntimeException, EVDBAPIException {
        super.modifyTags("/venues/tags/delete", svid, tagList);
    }

    public List<Tag> getTags(String svid) throws EVDBRuntimeException, EVDBAPIException {
        Venue e = (Venue) this.listTags(Venue.class, "/venues/tags/list", svid);
        return e.getTags();
    }

    public void addComment(String svid, Comment comment) throws EVDBRuntimeException, EVDBAPIException {
        super.addComment("/venues/comments/new", svid, comment);
    }

    public void modifyComment(Comment comment) throws EVDBRuntimeException, EVDBAPIException {
        super.modifyComment("/venues/comments/modify", comment);
    }

    public void deleteComment(Comment comment) throws EVDBRuntimeException, EVDBAPIException {
        super.deleteComment("/venues/comments/delete", comment);
    }

    public void addLink(String svid, Link link) throws EVDBRuntimeException, EVDBAPIException {
        super.addLink("/venues/links/new", svid, link);
    }

    public void deleteLink(Link link) throws EVDBRuntimeException, EVDBAPIException {
        super.deleteLink("/venues/links/delete", link);
    }

    public void addImage(String svid, Image image) throws EVDBRuntimeException, EVDBAPIException {
        super.addImage("/venues/links/add", svid, image);
    }

    public void deleteImage(String svid, Image image) throws EVDBRuntimeException, EVDBAPIException {
        super.deleteImage("/venues/links/remove", svid, image);
    }

    public void deleteProperty(String svid, Property property) throws EVDBRuntimeException, EVDBAPIException {
        super.deleteProperty("/venues/properties/remove", svid, property);
    }

    public int addProperty(String svid, Property property) throws EVDBRuntimeException, EVDBAPIException {
        return super.addProperty("/venues/properties/add", svid, property);
    }

    public SearchResult search(VenueSearchRequest searchRequest) throws EVDBRuntimeException, EVDBAPIException {
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
        InputStream is = this.serverCommunication.invokeMethod("/venues/search", params);
        return (SearchResult) this.unmarshallRequest(SearchResult.class, is);
    }

    private Venue createOrModify(Venue v) throws EVDBRuntimeException, EVDBAPIException {
        HashMap params = new HashMap();
        params.put("name", v.getName());
        params.put("address", v.getAddress());
        params.put("city", v.getCity());
        params.put("region", v.getRegion());
        params.put("postal_code", v.getPostalCode());
        params.put("description", v.getDescription());
        params.put("country", v.getCountry());
        params.put("privacy", String.valueOf(v.getPrivacy()));
        params.put("venue_type", v.getType());
        params.put("url", v.getUrl());
        params.put("url_type", v.getUrlType());
        if (v.getParents() != null && v.getParents().size() > 0) {
            params.put("parent_id", ((Venue) v.getParents().get(0)).getSvid());
        }

        params.put("user", APIConfiguration.getEvdbUser());
        params.put("password", APIConfiguration.getEvdbPassword());
        params.put("id", v.getSvid());
        String tagString = "";
        if (v.getTags() != null) {
            Tag is;
            for (Iterator method = v.getTags().iterator(); method.hasNext(); tagString = tagString + is.getTitle() + " ") {
                is = (Tag) method.next();
            }

            params.put("tags", tagString);
        }

        String method1 = v.getSvid() == null ? "/venues/new" : "/venues/modify";
        InputStream is1 = this.serverCommunication.invokeMethod(method1, params);
        GenericResponse gr = (GenericResponse) this.unmarshallRequest(GenericResponse.class, is1);
        v.setSvid(gr.getId());
        return v;
    }
}

