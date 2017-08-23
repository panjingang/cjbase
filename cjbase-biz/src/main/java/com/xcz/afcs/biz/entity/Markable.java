package com.xcz.afcs.biz.entity;
public abstract class Markable extends Pageable {
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    protected String remark;
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Markable [remark=").append(remark).append("]");
        return builder.toString();
    }
    
}