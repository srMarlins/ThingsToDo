package com.srmarlins.eventful_android.operations;

import com.srmarlins.eventful_android.APIConfiguration;
import com.srmarlins.eventful_android.EVDBAPIException;
import com.srmarlins.eventful_android.EVDBRuntimeException;
import com.srmarlins.eventful_android.data.Category;
import com.srmarlins.eventful_android.data.Comment;
import com.srmarlins.eventful_android.data.Event;
import com.srmarlins.eventful_android.data.Image;
import com.srmarlins.eventful_android.data.Link;
import com.srmarlins.eventful_android.data.Performer;
import com.srmarlins.eventful_android.data.Property;
import com.srmarlins.eventful_android.data.SearchResult;
import com.srmarlins.eventful_android.data.Tag;
import com.srmarlins.eventful_android.data.request.EventSearchRequest;
import com.srmarlins.eventful_android.data.response.GenericResponse;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class EventOperations extends BaseOperations {
    private static final String EVENTS_GET = "/events/get";
    private static final String EVENTS_NEW = "/events/new";
    private static final String EVENTS_MODIFY = "/events/modify";
    private static final String EVENTS_WITHDRAW = "/events/withdraw";
    private static final String EVENTS_RESTORE = "/events/restore";
    private static final String EVENTS_SEARCH = "/events/search";
    private static final String EVENTS_TAGS_LIST = "/events/tags/list";
    private static final String EVENTS_TAGS_NEW = "/events/tags/new";
    private static final String EVENTS_TAGS_DELETE = "/events/tags/delete";
    private static final String EVENTS_COMMENTS_NEW = "/events/comments/new";
    private static final String EVENTS_COMMENTS_MODIFY = "/events/comments/modify";
    private static final String EVENTS_COMMENTS_DELETE = "/events/comments/delete";
    private static final String EVENTS_LINKS_NEW = "/events/links/new";
    private static final String EVENTS_LINKS_DELETE = "/events/links/delete";
    private static final String EVENTS_IMAGES_ADD = "/events/links/add";
    private static final String EVENTS_IMAGES_REMOVE = "/events/links/remove";
    private static final String EVENTS_PERFORMERS_ADD = "/events/performers/add";
    private static final String EVENTS_PERFORMERS_REMOVE = "/events/performers/remove";
    private static final String EVENTS_PROPERTIES_LIST = "/events/properties/list";
    private static final String EVENTS_PROPERTIES_ADD = "/events/properties/add";
    private static final String EVENTS_PROPERTIES_REMOVE = "/events/properties/remove";
    private static final String EVENTS_CATEGORIES_ADD = "/events/categories/add";
    private static final String EVENTS_CATEGORIES_REMOVE = "/events/categories/remove";

    public EventOperations() {
    }

    public Event get(String seid) throws EVDBRuntimeException, EVDBAPIException {
        HashMap params = new HashMap();
        params.put("id", seid);
        InputStream is = this.serverCommunication.invokeMethod("/events/get", params);
        return (Event) this.unmarshallRequest(Event.class, is);
    }

    public SearchResult search(EventSearchRequest searchRequest) throws EVDBRuntimeException, EVDBAPIException {
        HashMap params = new HashMap();
        params.put("keywords", searchRequest.getKeywords());
        params.put("include", searchRequest.getIncludes());
        params.put("location", searchRequest.getLocation());
        params.put("image_sizes", searchRequest.getImageSizes());
        params.put("date", searchRequest.getDateRange());
        params.put("category", searchRequest.getCategory());
        params.put("within", String.valueOf(searchRequest.getLocationRadius()));
        params.put("change_multi_day_start", String.valueOf(searchRequest.getChangeMultiDayStart()));
        params.put("units", searchRequest.getLocationUnits());
        params.put("sort_order", searchRequest.getSortOrder().toLowerCase());
        params.put("sort_direction", searchRequest.getSortDirection().toString().toLowerCase());
        params.put("page_size", String.valueOf(searchRequest.getPageSize()));
        params.put("page_number", String.valueOf(searchRequest.getPageNumber()));
        params.put("user", APIConfiguration.getEvdbUser());
        params.put("password", APIConfiguration.getEvdbPassword());
        params.put("connect_timeout", Integer.toString(searchRequest.getConnectionTimeOut()));
        params.put("read_timeout", Integer.toString(searchRequest.getReadTimeOut()));
        InputStream is = this.serverCommunication.invokeMethod("/events/search", params);
        return (SearchResult) this.unmarshallRequest(SearchResult.class, is);
    }

    public Event get(Event e) throws EVDBRuntimeException, EVDBAPIException {
        return this.get(e.getSeid());
    }

    public Event create(Event e) throws EVDBRuntimeException, EVDBAPIException {
        if (e.getSeid() != null) {
            throw new EVDBRuntimeException("Create called on an event with an existing SEID");
        } else {
            return this.createOrModify(e);
        }
    }

    public List<Tag> getTags(String seid) throws EVDBRuntimeException, EVDBAPIException {
        Event e = (Event) this.listTags(Event.class, "/events/tags/list", seid);
        return e.getTags();
    }

    public List<Property> getProperties(String seid) throws EVDBRuntimeException, EVDBAPIException {
        Event e = (Event) this.listProperties(Event.class, "/events/properties/list", seid);
        return e.getProperties();
    }

    public void addTags(String seid, List<Tag> tagList) throws EVDBRuntimeException, EVDBAPIException {
        super.modifyTags("/events/tags/new", seid, tagList);
    }

    public void deleteTags(String seid, List<Tag> tagList) throws EVDBRuntimeException, EVDBAPIException {
        super.modifyTags("/events/tags/delete", seid, tagList);
    }

    public void addComment(String seid, Comment comment) throws EVDBRuntimeException, EVDBAPIException {
        super.addComment("/events/comments/new", seid, comment);
    }

    public void modifyComment(Comment comment) throws EVDBRuntimeException, EVDBAPIException {
        super.modifyComment("/events/comments/modify", comment);
    }

    public void addImage(String seid, Image image) throws EVDBRuntimeException, EVDBAPIException {
        super.addImage("/events/links/add", seid, image);
    }

    public void deleteImage(String seid, Image image) throws EVDBRuntimeException, EVDBAPIException {
        super.deleteImage("/events/links/remove", seid, image);
    }

    public void deleteProperty(String seid, Property property) throws EVDBRuntimeException, EVDBAPIException {
        super.deleteProperty("/events/properties/remove", seid, property);
    }

    public int addProperty(String seid, Property property) throws EVDBRuntimeException, EVDBAPIException {
        return super.addProperty("/events/properties/add", seid, property);
    }

    public void addPerformer(String seid, Performer performer) throws EVDBRuntimeException, EVDBAPIException {
        HashMap params = new HashMap();
        params.put("user", APIConfiguration.getEvdbUser());
        params.put("password", APIConfiguration.getEvdbPassword());
        params.put("id", seid);
        params.put("performer_id", String.valueOf(performer.getSpid()));
        InputStream is = this.serverCommunication.invokeMethod("/events/performers/add", params);
        this.unmarshallRequest(GenericResponse.class, is);
    }

    public void deletePerformer(String seid, Performer performer) throws EVDBRuntimeException, EVDBAPIException {
        HashMap params = new HashMap();
        params.put("user", APIConfiguration.getEvdbUser());
        params.put("password", APIConfiguration.getEvdbPassword());
        params.put("id", seid);
        params.put("performer_id", String.valueOf(performer.getSpid()));
        InputStream is = this.serverCommunication.invokeMethod("/events/performers/remove", params);
        this.unmarshallRequest(GenericResponse.class, is);
    }

    public void addCategory(String seid, Category category) throws EVDBRuntimeException, EVDBAPIException {
        HashMap params = new HashMap();
        params.put("user", APIConfiguration.getEvdbUser());
        params.put("password", APIConfiguration.getEvdbPassword());
        params.put("id", seid);
        params.put("category_id", String.valueOf(category.getId()));
        InputStream is = this.serverCommunication.invokeMethod("/events/categories/add", params);
        this.unmarshallRequest(GenericResponse.class, is);
    }

    public void deleteCategory(String seid, Category category) throws EVDBRuntimeException, EVDBAPIException {
        HashMap params = new HashMap();
        params.put("user", APIConfiguration.getEvdbUser());
        params.put("password", APIConfiguration.getEvdbPassword());
        params.put("id", seid);
        params.put("category_id", String.valueOf(category.getId()));
        InputStream is = this.serverCommunication.invokeMethod("/events/categories/remove", params);
        this.unmarshallRequest(GenericResponse.class, is);
    }

    public void deleteComment(Comment comment) throws EVDBRuntimeException, EVDBAPIException {
        super.deleteComment("/events/comments/delete", comment);
    }

    public Event modify(Event e) throws EVDBRuntimeException, EVDBAPIException {
        if (e.getSeid() == null) {
            throw new EVDBRuntimeException("Modify called on an event without an SEID");
        } else {
            return this.createOrModify(e);
        }
    }

    public void addLink(String seid, Link link) throws EVDBRuntimeException, EVDBAPIException {
        super.addLink("/events/links/new", seid, link);
    }

    public void deleteLink(Link link) throws EVDBRuntimeException, EVDBAPIException {
        super.deleteLink("/events/links/delete", link);
    }

    public void withdraw(String seid, String withdrawNote) throws EVDBRuntimeException, EVDBAPIException {
        HashMap params = new HashMap();
        params.put("id", seid);
        params.put("note", withdrawNote);
        params.put("user", APIConfiguration.getEvdbUser());
        params.put("password", APIConfiguration.getEvdbPassword());
        InputStream is = this.serverCommunication.invokeMethod("/events/withdraw", params);
        this.unmarshallRequest(GenericResponse.class, is);
    }

    public void restore(String seid) throws EVDBRuntimeException, EVDBAPIException {
        HashMap params = new HashMap();
        params.put("id", seid);
        params.put("user", APIConfiguration.getEvdbUser());
        params.put("password", APIConfiguration.getEvdbPassword());
        InputStream is = this.serverCommunication.invokeMethod("/events/restore", params);
        this.unmarshallRequest(GenericResponse.class, is);
    }

    private Event createOrModify(Event e) throws EVDBRuntimeException, EVDBAPIException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        HashMap params = new HashMap();
        params.put("title", e.getTitle());
        params.put("start_time", e.getStartTime() == null ? "" : sdf.format(e.getStartTime()));
        params.put("stop_time", e.getStopTime() == null ? "" : sdf.format(e.getStopTime()));
        params.put("tz_olson_path", e.getOlsonPath());
        params.put("all_day", e.isAllDay() ? "1" : "0");
        params.put("description", e.getDescription());
        params.put("privacy", String.valueOf(e.getPrivacy()));
        params.put("price", e.getPrice());
        params.put("free", e.isFree() ? "1" : "0");
        params.put("venue_id", e.getVenue() == null ? "" : e.getVenue().getSvid());
        if (e.getParents() != null && e.getParents().size() > 0) {
            params.put("parent_id", ((Event) e.getParents().get(0)).getSeid());
        }

        params.put("user", APIConfiguration.getEvdbUser());
        params.put("password", APIConfiguration.getEvdbPassword());
        params.put("id", e.getSeid());
        String tagString = "";
        if (e.getTags() != null) {
            Tag is;
            for (Iterator method = e.getTags().iterator(); method.hasNext(); tagString = tagString + is.getTitle() + " ") {
                is = (Tag) method.next();
            }

            params.put("tags", tagString);
        }

        String method1 = e.getSeid() == null ? "/events/new" : "/events/modify";
        InputStream is1 = this.serverCommunication.invokeMethod(method1, params);
        GenericResponse gr = (GenericResponse) this.unmarshallRequest(GenericResponse.class, is1);
        e.setSeid(gr.getId());
        return e;
    }
}

