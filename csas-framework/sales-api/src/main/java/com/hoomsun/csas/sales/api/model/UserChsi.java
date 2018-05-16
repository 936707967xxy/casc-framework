package com.hoomsun.csas.sales.api.model;
/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月7日 <br>
 * 描述： 学历
 *
 */
public class UserChsi {
	private String chsiId;

	private String addDate;

	private String graduation;  //毕业年份

	private String major;      //专业

	private String status;     // 学籍状态       不在籍(毕业)1    在籍（注册学籍）2  未核对  0

	private String shool;      //院校

	private String maxEdu;     //最高学历       专科1、本科2、硕士研究生3    未核对  0 

	private String applyId;

	private String userName;   //姓名

	private String userSex;    //性别

	private String birthdayTime; //出生日期

	private String nationality; //民族

	private String cardNumber;  //身份证

	private String level;   //(未用到) //学历类别    

	private String qualificationType;  // 学历类别      网络教育1    普通 2   研究生3   未核对  0

	private String learningType; //学习形式                          普通全日制 1、全日制2、网络教育3,业余4、开放教育5    未核对  0

	private String classGrade;   //班机号

	private String studentNumber;  // 学号

	private String jsonTime;       // 入学时间

	private String branchCourts;  // 分院

	public String getChsiId() {
		return chsiId;
	}

	public void setChsiId(String chsiId) {
		this.chsiId = chsiId == null ? null : chsiId.trim();
	}

	public String getAddDate() {
		return addDate;
	}

	public void setAddDate(String addDate) {
		this.addDate = addDate == null ? null : addDate.trim();
	}

	public String getGraduation() {
		return graduation;
	}

	public void setGraduation(String graduation) {
		this.graduation = graduation == null ? null : graduation.trim();
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major == null ? null : major.trim();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	public String getShool() {
		return shool;
	}

	public void setShool(String shool) {
		this.shool = shool == null ? null : shool.trim();
	}

	public String getMaxEdu() {
		return maxEdu;
	}

	public void setMaxEdu(String maxEdu) {
		this.maxEdu = maxEdu == null ? null : maxEdu.trim();
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId == null ? null : applyId.trim();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName == null ? null : userName.trim();
	}

	public String getUserSex() {
		return userSex;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex == null ? null : userSex.trim();
	}

	public String getBirthdayTime() {
		return birthdayTime;
	}

	public void setBirthdayTime(String birthdayTime) {
		this.birthdayTime = birthdayTime == null ? null : birthdayTime.trim();
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality == null ? null : nationality.trim();
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber == null ? null : cardNumber.trim();
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level == null ? null : level.trim();
	}

	public String getQualificationType() {
		return qualificationType;
	}

	public void setQualificationType(String qualificationType) {
		this.qualificationType = qualificationType == null ? null : qualificationType.trim();
	}

	public String getLearningType() {
		return learningType;
	}

	public void setLearningType(String learningType) {
		this.learningType = learningType == null ? null : learningType.trim();
	}

	public String getClassGrade() {
		return classGrade;
	}

	public void setClassGrade(String classGrade) {
		this.classGrade = classGrade == null ? null : classGrade.trim();
	}

	public String getStudentNumber() {
		return studentNumber;
	}

	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber == null ? null : studentNumber.trim();
	}

	public String getJsonTime() {
		return jsonTime;
	}

	public void setJsonTime(String jsonTime) {
		this.jsonTime = jsonTime == null ? null : jsonTime.trim();
	}

	public String getBranchCourts() {
		return branchCourts;
	}

	public void setBranchCourts(String branchCourts) {
		this.branchCourts = branchCourts == null ? null : branchCourts.trim();
	}
}