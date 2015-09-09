package com.srmarlins.eventful_android.operations;

import com.srmarlins.eventful_android.APIConfiguration;
import com.srmarlins.eventful_android.EVDBAPIException;
import com.srmarlins.eventful_android.EVDBRuntimeException;
import com.srmarlins.eventful_android.data.Comment;
import com.srmarlins.eventful_android.data.Demand;
import com.srmarlins.eventful_android.data.Image;
import com.srmarlins.eventful_android.data.Link;
import com.srmarlins.eventful_android.data.SearchResult;
import com.srmarlins.eventful_android.data.Tag;
import com.srmarlins.eventful_android.data.request.DemandSearchRequest;
import com.srmarlins.eventful_android.data.response.GenericResponse;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class DemandOperations extends BaseOperations {
    private static final String DEMANDS_GET = "/demands/get";
    private static final String DEMANDS_NEW = "/demands/new";
    private static final String DEMANDS_MODIFY = "/demands/modify";
    private static final String DEMANDS_WITHDRAW = "/demands/withdraw";
    private static final String DEMANDS_RESTORE = "/demands/restore";
    private static final String DEMANDS_SEARCH = "/demands/search";
    private static final String DEMANDS_TAGS_LIST = "/demands/tags/list";
    private static final String DEMANDS_TAGS_NEW = "/demands/tags/add";
    private static final String DEMANDS_TAGS_DELETE = "/demands/tags/remove";
    private static final String DEMANDS_COMMENTS_NEW = "/demands/comments/new";
    private static final String DEMANDS_COMMENTS_MODIFY = "/demands/comments/modify";
    private static final String DEMANDS_COMMENTS_DELETE = "/demands/comments/delete";
    private static final String DEMANDS_LINKS_NEW = "/demands/links/new";
    private static final String DEMANDS_LINKS_DELETE = "/demands/links/remove";
    private static final String DEMANDS_IMAGES_ADD = "/demands/links/add";
    private static final String DEMANDS_IMAGES_REMOVE = "/demands/links/remove";

    public DemandOperations() {
    }

    public Demand get(String sdid) throws EVDBRuntimeException, EVDBAPIException {
        HashMap params = new HashMap();
        params.put("id", sdid);
        InputStream is = this.serverCommunication.invokeMethod("/demands/get", params);
        Demand v = (Demand) this.unmarshallRequest(Demand.class, is);
        return v;
    }

    public Demand create(Demand d) throws EVDBRuntimeException, EVDBAPIException {
        if (d.getSdid() != null) {
            throw new EVDBRuntimeException("Create called on an demand with an existing SDID");
        } else {
            return this.createOrModify(d);
        }
    }

    public Demand modify(Demand d) throws EVDBRuntimeException, EVDBAPIException {
        if (d.getSdid() == null) {
            throw new EVDBRuntimeException("Modify called on an demand without an SDID");
        } else {
            return this.createOrModify(d);
        }
    }

    public void withdraw(String sdid, String withdrawNote) throws EVDBRuntimeException, EVDBAPIException {
        HashMap params = new HashMap();
        params.put("id", sdid);
        params.put("note", withdrawNote);
        params.put("user", APIConfiguration.getEvdbUser());
        params.put("password", APIConfiguration.getEvdbPassword());
        InputStream is = this.serverCommunication.invokeMethod("/demands/withdraw", params);
        this.unmarshallRequest(GenericResponse.class, is);
    }

    public void restore(String sdid) throws EVDBRuntimeException, EVDBAPIException {
        HashMap params = new HashMap();
        params.put("id", sdid);
        params.put("user", APIConfiguration.getEvdbUser());
        params.put("password", APIConfiguration.getEvdbPassword());
        InputStream is = this.serverCommunication.invokeMethod("/demands/restore", params);
        this.unmarshallRequest(GenericResponse.class, is);
    }

    public void addTags(String sdid, List<Tag> tagList) throws EVDBRuntimeException, EVDBAPIException {
        super.modifyTags("/demands/tags/add", sdid, tagList);
    }

    public void deleteTags(String sdid, List<Tag> tagList) throws EVDBRuntimeException, EVDBAPIException {
        super.modifyTags("/demands/tags/remove", sdid, tagList);
    }

    public List<Tag> getTags(String sdid) throws EVDBRuntimeException, EVDBAPIException {
        Demand e = (Demand) this.listTags(Demand.class, "/demands/tags/list", sdid);
        return e.getTags();
    }

    public void addComment(String sdid, Comment comment) throws EVDBRuntimeException, EVDBAPIException {
        super.addComment("/demands/comments/new", sdid, comment);
    }

    public void modifyComment(Comment comment) throws EVDBRuntimeException, EVDBAPIException {
        super.modifyComment("/demands/comments/modify", comment);
    }

    public void deleteComment(Comment comment) throws EVDBRuntimeException, EVDBAPIException {
        super.deleteComment("/demands/comments/delete", comment);
    }

    public void addLink(String sdid, Link link) throws EVDBRuntimeException, EVDBAPIException {
        super.addLink("/demands/links/new", sdid, link);
    }

    public void deleteLink(Link link) throws EVDBRuntimeException, EVDBAPIException {
        super.deleteLink("/demands/links/remove", link);
    }

    public void addImage(String sdid, Image image) throws EVDBRuntimeException, EVDBAPIException {
        super.addImage("/demands/links/add", sdid, image);
    }

    public void deleteImage(String sdid, Image image) throws EVDBRuntimeException, EVDBAPIException {
        super.deleteImage("/demands/links/remove", sdid, image);
    }

    public SearchResult search(DemandSearchRequest searchRequest) throws EVDBRuntimeException, EVDBAPIException {
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
        InputStream is = this.serverCommunication.invokeMethod("/demands/search", params);
        return (SearchResult) this.unmarshallRequest(SearchResult.class, is);
    }

    private Demand createOrModify(Demand d) throws EVDBRuntimeException, EVDBAPIException {
        HashMap params = new HashMap();
        params.put("performer_id", d.getPerformer().getSpid());
        params.put("location", d.getLocation());
        params.put("description", d.getDescription());
        params.put("user", APIConfiguration.getEvdbUser());
        params.put("password", APIConfiguration.getEvdbPassword());
        params.put("id", d.getSdid());
        String tagString = "";
        if (d.getTags() != null) {
            Tag is;
            for (Iterator method = d.getTags().iterator(); method.hasNext(); tagString = tagString + is.getTitle() + " ") {
                is = (Tag) method.next();
            }

            params.put("tags", tagString);
        }

        String method1 = d.getSdid() == null ? "/demands/new" : "/demands/modify";
        InputStream is1 = this.serverCommunication.invokeMethod(method1, params);
        GenericResponse gr = (GenericResponse) this.unmarshallRequest(GenericResponse.class, is1);
        d.setSdid(gr.getId());
        return d;
    }
}
