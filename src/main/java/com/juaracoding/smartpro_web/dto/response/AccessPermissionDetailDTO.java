package com.juaracoding.smartpro_web.dto.response;

public class AccessPermissionDetailDTO {
    private Long id;

    private Boolean allowRead;

    private Boolean allowCreate;

    private Boolean allowUpdate;

    private Boolean allowDelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getAllowRead() {
        return allowRead;
    }

    public void setAllowRead(Boolean allowRead) {
        this.allowRead = allowRead;
    }

    public Boolean getAllowCreate() {
        return allowCreate;
    }

    public void setAllowCreate(Boolean allowCreate) {
        this.allowCreate = allowCreate;
    }

    public Boolean getAllowUpdate() {
        return allowUpdate;
    }

    public void setAllowUpdate(Boolean allowUpdate) {
        this.allowUpdate = allowUpdate;
    }

    public Boolean getAllowDelete() {
        return allowDelete;
    }

    public void setAllowDelete(Boolean allowDelete) {
        this.allowDelete = allowDelete;
    }
}
