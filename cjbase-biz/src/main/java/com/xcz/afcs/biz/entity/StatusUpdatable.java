package com.xcz.afcs.biz.entity;

public abstract class StatusUpdatable extends Updatable {
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    
    protected Integer status;
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }

	@Override
	public String toString() {
		return "StatusUpdatable [status=" + status + ", updateVersion=" + updateVersion + "]";
	}
    
    
}
