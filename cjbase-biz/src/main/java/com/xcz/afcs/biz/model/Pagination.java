package com.xcz.afcs.biz.model;

import java.io.Serializable;

public class Pagination implements Serializable {
    
    public static final int DEFAULT_PAGE_SIZE = 10;
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 页面大小，一页的条目数
     */
    private int pageSize = DEFAULT_PAGE_SIZE;
    
    /**
     * 总条目数量
     */
    private int totalItemNum;
    
    /**
     * 总页数，从1开始，没有条目也有1页
     */
    private int totalPageNum;
    
    /**
     * 页码，从1开始，没有条目也有1页
     */
    private Integer pageNo;
    
    /**
     * 开始的条目位置，从1开始
     */
    private Integer startIndex;
    
    /**
     * 结束的条目位置，从1开始
     */
    private int endIndex;
    
    /**
     * 该页的项目数
     */
    private int curPageItemNum;
    
    /**
     * 计算分页，<br />
     * 如果设置了页码，按页码分，<br />
     * 如果没有设置页码，设置了开始条目位置，则先根据开始条目位置计算页码再分页，<br />
     * 如果两个都没设置，则按第一页算
     * 
     * @return
     */
    public boolean calc() {
        if (!checkPageSize()) {
            return false;
        }
        
        if (!checkTotalItemNum()) {
            return false;
        }
        
        // 计算总页数
        totalPageNum = totalItemNum / pageSize;
        if (totalPageNum * pageSize < totalItemNum) {
            totalPageNum++;
        }
        
        if (null != pageNo) {
            calcByPageNo();
        } else if (null != startIndex) {
            calcByStartIndex();
        } else {
            pageNo = 1;
            calcByPageNo();
        }
        
        return true;
    }
    
    private void calcByPageNo() {
        // 页码小于等于0，取第一页
        if (pageNo <= 0) {
            pageNo = 1;
        }

        // 超过总页数，取最后一页
        /*
        if (pageNo > totalPageNum) {
            pageNo = totalPageNum;
        }*/

        // 重新根据页码计算开始条目（到前一页为止的总条目数再加1）
        startIndex = (pageNo - 1) * pageSize + 1;
        // 计算结束的条目位置
        calcEndIndex();
    }
    
    private void calcByStartIndex() {
        // 开始的条目位置小于等于0，取第一条（checkTotalItemNum已经校验了总条目数不为0）
        if (startIndex <= 0) {
            startIndex = 1;
        }
        // 超过总条目数，取最后一条
        if (startIndex > totalItemNum) {
            startIndex = totalItemNum;
        }
        
        // 计算页码
        pageNo = startIndex / pageSize;
        // 开始的条目位置正好等于这么多页的条目数，则其实是前一页的页尾
        if (pageNo * pageSize == startIndex) {
            pageNo--;
        }
        // 如果是第0页，则调整为第一页
        if (pageNo == 0) {
            pageNo = 1;
        }
        
        // 重新根据页码计算开始条目（到前一页为止的总条目数再加1）
        startIndex = (pageNo - 1) * pageSize + 1;
        // 计算结束的条目位置
        calcEndIndex();
    }
    
    private void calcEndIndex() {
        // 从开始条目位置完后偏移页面大小减1
        endIndex = startIndex + (pageSize - 1);
        // 如果结束的条目位置超过总条目数，调整为总条目数
        if (endIndex > totalItemNum) {
            endIndex = totalItemNum;
        }
    }
    
    private boolean checkPageSize() {
        // page_size小于0，表示不分页，取所有
        // page_size等于0，没结果
        // page_size大于0，正常按page_size分页
        if (pageSize == 0) {
            pageNo = 1;
            startIndex = 0;
            endIndex = 0;
            totalItemNum = 0;
            curPageItemNum = 0;
            totalPageNum = 1;
            return false;
        }
        if (pageSize < 0) {
            pageSize = totalItemNum;
            pageNo = 1;
            startIndex = 1;
            endIndex = pageSize;
            curPageItemNum = pageSize;
            totalPageNum = 1;
            return false;
        }
        return true;
    }
    
    private boolean checkTotalItemNum() {
        if (totalItemNum <= 0) {
            totalItemNum = 0;
            pageNo = 1;
            startIndex = 0;
            endIndex = 0;
            curPageItemNum = 0;
            totalPageNum = 1;
            return false;
        }
        return true;
    }
    
    /**
     * 获取页面大小，一页的条目数
     * 
     * @return
     */
    public int getPageSize() {
        return pageSize;
    }
    
    /**
     * 设置页面大小，一页的条目数
     * 
     * @param pageSize
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    
    /**
     * 总条目数量
     * 
     * @return
     */
    public int getTotalItemNum() {
        return totalItemNum;
    }
    
    /**
     * 设置总条目数量
     * 
     * @param totalItemNum
     */
    public void setTotalItemNum(int totalItemNum) {
        this.totalItemNum = totalItemNum;
    }
    
    /**
     * 获取页码，从1开始，没有条目也有1页
     * 
     * @return
     */
    public Integer getPageNo() {
        return pageNo;
    }
    
    /**
     * 设置页码，从1开始，没有条目也有1页
     * 
     * @param pageNo
     */
    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }
    
    /**
     * 获取开始的条目位置，从1开始
     * 
     * @return
     */
    public Integer getStartIndex() {
        return startIndex;
    }
    
    /**
     * 设置开始的条目位置，从1开始
     * 
     * @param startIndex
     */
    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }
    
    /**
     * 获取总页数，从1开始，没有条目也有1页
     * 
     * @return
     */
    public int getTotalPageNum() {
        return totalPageNum;
    }
    
    /**
     * 设置总页数，从1开始，没有条目也有1页<br />
     * <strong>该方法只作为自动注入调用，业务代码不应该调用该方法</strong>
     * @param totalPageNum
     */
    public void setTotalPageNum(int totalPageNum) {
        this.totalPageNum = totalPageNum;
    }
    
    /**
     * 获取结束的条目位置，从1开始
     * 
     * @return
     */
    public int getEndIndex() {
        return endIndex;
    }
    
    /**
     * 设置结束的条目位置，从1开始<br />
     * <strong>该方法只作为自动注入调用，业务代码不应该调用该方法</strong>
     * @param endIndex
     */
    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }
    
    /**
     * 获取该页项目数
     * 
     * @return
     */
    public int getCurPageItemNum() {
        return curPageItemNum;
    }
    
    /**
     * 设置该页项目数<br />
     * <strong>该方法只作为自动注入调用，业务代码不应该调用该方法</strong>
     * @param curPageItemNum
     */
    public void setCurPageItemNum(int curPageItemNum) {
        this.curPageItemNum = curPageItemNum;
    }
    
    public Integer getOffset() {
        return startIndex > 0 ? startIndex - 1 : 0;
    }

	@Override
	public String toString() {
		return "Pagination [pageSize=" + pageSize + ", totalItemNum=" + totalItemNum + ", totalPageNum=" + totalPageNum
				+ ", pageNo=" + pageNo + ", startIndex=" + startIndex + ", endIndex=" + endIndex + ", curPageItemNum="
				+ curPageItemNum + "]";
	}

}
