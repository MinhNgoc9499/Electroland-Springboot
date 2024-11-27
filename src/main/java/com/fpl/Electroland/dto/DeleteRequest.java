package com.fpl.Electroland.dto;

import java.util.List;

public class DeleteRequest {
    private List<Long> ids;

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
}
