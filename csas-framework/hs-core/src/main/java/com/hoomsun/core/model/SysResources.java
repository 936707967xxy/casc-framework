package com.hoomsun.core.model;

public class SysResources {
	public static final String TYPE_MENU = "MENU";
	public static final String TYPE_ACTION = "ACTION";
	
    private String resId;

    private String resName;

    private String resIcon;

    private Integer resLevel;

    private Integer resSort;

    private String resType;

    private String resUrl;

    private String resValue;

    private String resParent;

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId == null ? null : resId.trim();
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName == null ? null : resName.trim();
    }

    public String getResIcon() {
        return resIcon;
    }

    public void setResIcon(String resIcon) {
        this.resIcon = resIcon == null ? null : resIcon.trim();
    }

    public Integer getResLevel() {
        return resLevel;
    }

    public void setResLevel(Integer resLevel) {
        this.resLevel = resLevel;
    }

    public Integer getResSort() {
        return resSort;
    }

    public void setResSort(Integer resSort) {
        this.resSort = resSort;
    }

    public String getResType() {
        return resType;
    }

    public void setResType(String resType) {
        this.resType = resType == null ? null : resType.trim();
    }

    public String getResUrl() {
        return resUrl;
    }

    public void setResUrl(String resUrl) {
        this.resUrl = resUrl == null ? null : resUrl.trim();
    }

    public String getResValue() {
        return resValue;
    }

    public void setResValue(String resValue) {
        this.resValue = resValue == null ? null : resValue.trim();
    }

    public String getResParent() {
        return resParent;
    }

    public void setResParent(String resParent) {
        this.resParent = resParent == null ? null : resParent.trim();
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((resId == null) ? 0 : resId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SysResources other = (SysResources) obj;
		if (resId == null) {
			if (other.resId != null)
				return false;
		} else if (!resId.equals(other.resId))
			return false;
		return true;
	}
    
}