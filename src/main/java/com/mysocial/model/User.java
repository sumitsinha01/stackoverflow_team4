package com.mysocial.model;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

/**
 * User of a particular site
 * @author maruthir
 *
 */
@Entity("users")
@Indexes(
    @Index(value = "userName", fields = @Field("userName"))
)
public class User {
	@Id
    private ObjectId id;
    private String userName;
    private String first;
    private String last;
    private String email;
    private ObjectId siteId;
    private String password;
    private String companyId;
    private String companyName;
    private List<ObjectId> departmentIds;
    private List<String> deptNames;
    private String subdomain;
    private Boolean isCompany;
    
	public List<ObjectId> getDepartmentIds() {
		if(departmentIds==null)
			departmentIds = new ArrayList<ObjectId>();
		return departmentIds;
	}
	public void setDepartmentIds(List<ObjectId> departmentIds) {
		this.departmentIds = departmentIds;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFirst() {
		return first;
	}
	public void setFirst(String first) {
		this.first = first;
	}
	public String getLast() {
		return last;
	}
	public void setLast(String last) {
		this.last = last;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public ObjectId getSiteId() {
		return siteId;
	}
	public void setSiteId(ObjectId siteId) {
		this.siteId = siteId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getSubdomain() {
		return subdomain;
	}
	public void setSubdomain(String subdomain) {
		this.subdomain = subdomain;
	}
	public Boolean getIsCompany() {
		return isCompany;
	}
	public void setIsCompany(Boolean isCompany) {
		this.isCompany = isCompany;
	}
	public List<String> getDeptNames() {
		return deptNames;
	}
	public void setDeptNames(List<String> deptNames) {
		this.deptNames = deptNames;
	}

    
}
