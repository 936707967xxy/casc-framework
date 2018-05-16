package com.hoomsun.csas.sales.api.model;

public class UserCallVisual {
    private String id;

    private String applyId;

    private Object addData;

    private String oneGraphicData;

    private String twoGraphicData;

    private String threeGraphicData;

    private String fourGraphicData;

    private String fiveGraphicData;

    private String sixGraphicData;

    private String sevenGraphicData;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId == null ? null : applyId.trim();
    }

    public Object getAddData() {
        return addData;
    }

    public void setAddData(Object addData) {
        this.addData = addData;
    }

    public String getOneGraphicData() {
        return oneGraphicData;
    }

    public void setOneGraphicData(String oneGraphicData) {
        this.oneGraphicData = oneGraphicData == null ? null : oneGraphicData.trim();
    }

    public String getTwoGraphicData() {
        return twoGraphicData;
    }

    public void setTwoGraphicData(String twoGraphicData) {
        this.twoGraphicData = twoGraphicData == null ? null : twoGraphicData.trim();
    }

    public String getThreeGraphicData() {
        return threeGraphicData;
    }

    public void setThreeGraphicData(String threeGraphicData) {
        this.threeGraphicData = threeGraphicData == null ? null : threeGraphicData.trim();
    }

    public String getFourGraphicData() {
        return fourGraphicData;
    }

    public void setFourGraphicData(String fourGraphicData) {
        this.fourGraphicData = fourGraphicData == null ? null : fourGraphicData.trim();
    }

    public String getFiveGraphicData() {
        return fiveGraphicData;
    }

    public void setFiveGraphicData(String fiveGraphicData) {
        this.fiveGraphicData = fiveGraphicData == null ? null : fiveGraphicData.trim();
    }

    public String getSixGraphicData() {
        return sixGraphicData;
    }

    public void setSixGraphicData(String sixGraphicData) {
        this.sixGraphicData = sixGraphicData == null ? null : sixGraphicData.trim();
    }

    public String getSevenGraphicData() {
        return sevenGraphicData;
    }

    public void setSevenGraphicData(String sevenGraphicData) {
        this.sevenGraphicData = sevenGraphicData == null ? null : sevenGraphicData.trim();
    }

	@Override
	public String toString() {
		return "UserCallVisual [id=" + id + ", applyId=" + applyId + ", addData=" + addData + ", oneGraphicData=" + oneGraphicData + ", twoGraphicData=" + twoGraphicData + ", threeGraphicData=" + threeGraphicData + ", fourGraphicData="
				+ fourGraphicData + ", fiveGraphicData=" + fiveGraphicData + ", sixGraphicData=" + sixGraphicData + ", sevenGraphicData=" + sevenGraphicData + "]";
	}
    
    
}