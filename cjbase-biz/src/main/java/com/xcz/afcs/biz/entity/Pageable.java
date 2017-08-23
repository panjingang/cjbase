package com.xcz.afcs.biz.entity;

import com.xcz.afcs.biz.model.Pagination;
import com.xcz.afcs.biz.model.SortMarker;

import java.util.LinkedList;
import java.util.List;

public class Pageable extends AfbpEntityBase {
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    
    protected Pagination page;

    protected List<SortMarker> sortMarkers;
    
    public Pageable pagination(Pagination page) {
        setPage(page);
        return this;
    }

    public Pagination getPage() {
        return page;
    }
    
    public void setPage(Pagination page) {
        this.page = page;
    }
    
    public Pageable addSortMarker(SortMarker sortMarker) {
        if (null == sortMarkers) {
            sortMarkers = new LinkedList<SortMarker>();
        }
        sortMarkers.add(sortMarker);
        return this;
    }
    
    public Pageable addSortMarker(SortMarker... sortMarkersToAdd) {
        if (null != sortMarkersToAdd && 0 < sortMarkersToAdd.length) {
            if (null == sortMarkers) {
                sortMarkers = new LinkedList<SortMarker>();
            }
            for (SortMarker sortMarker : sortMarkersToAdd) {
                sortMarkers.add(sortMarker);
            }
        }
        return this;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Pageable [page=").append(page).append("]");
        return builder.toString();
    }
    
}
