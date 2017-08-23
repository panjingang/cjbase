package com.xcz.afcs.biz.entity;

public abstract class Updatable extends Markable {
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    
    protected Long updateVersion;
    
    public Long getUpdateVersion() {
        return updateVersion;
    }
    
    public void setUpdateVersion(Long updateVersion) {
        this.updateVersion = updateVersion;
    }

	@Override
	public String toString() {
		return "Updatable [updateVersion=" + updateVersion + "]";
	}
 
}
