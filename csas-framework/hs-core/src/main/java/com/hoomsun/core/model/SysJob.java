package com.hoomsun.core.model;

public class SysJob {
    private String jobId;

    private String jobName;

    private String jobDesc;

    private String jobParent;

    private String addEmp;

    private String addDate;
    // 增加用于回显
    private String jobParentName;
    
    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId == null ? null : jobId.trim();
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName == null ? null : jobName.trim();
    }

    public String getJobDesc() {
        return jobDesc;
    }

    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc == null ? null : jobDesc.trim();
    }

    public String getJobParent() {
		return jobParent;
	}

	public void setJobParent(String jobParent) {
		this.jobParent = jobParent;
	}

	public String getAddEmp() {
        return addEmp;
    }

    public void setAddEmp(String addEmp) {
        this.addEmp = addEmp == null ? null : addEmp.trim();
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate == null ? null : addDate.trim();
    }

	public String getJobParentName() {
		return jobParentName;
	}

	public void setJobParentName(String jobParentName) {
		this.jobParentName = jobParentName;
	}
    
}