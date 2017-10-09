package manhattan.domain.impl;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;

import manhattan.domain.AbstractEntity;
@Entity
@AttributeOverride(name = "id", column = @Column(name = "CONTACT_ID"))
public class Contact extends AbstractEntity<Contact> {

	private static final long serialVersionUID = -1088831285476167498L;
	
	private String phone;
	private String mobilePhone;
	private String fax;
	private String email;
	private String facebookLink;
	private String instagramLink;
	private String twitterLink;
	private String address;
	
	@Column(name = "PHONE", length = 50)
	public String getPhone() {
		return phone;
	}
	
	@Column(name = "MOBILE_PHONE", length = 50)
	public String getMobilePhone() {
		return mobilePhone;
	}
	
	@Column(name = "FAX", length = 50)
	public String getFax() {
		return fax;
	}
	
	@Column(name = "EMAIL", length = 100)
	public String getEmail() {
		return email;
	}
	
	@Column(name = "FACEBOOK", length = 250)
	public String getFacebookLink() {
		return facebookLink;
	}
	
	@Column(name = "INSTAGRAM", length = 250)
	public String getInstagramLink() {
		return instagramLink;
	}
	
	@Column(name = "TWITTER", length = 250)
	public String getTwitterLink() {
		return twitterLink;
	}
	
	@Column(name = "ADDRESS", length = 250)
	public String getAddress() {
		return address;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	
	public void setFax(String fax) {
		this.fax = fax;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setFacebookLink(String facebookLink) {
		this.facebookLink = facebookLink;
	}
	
	public void setInstagramLink(String instagramLink) {
		this.instagramLink = instagramLink;
	}
	
	public void setTwitterLink(String twitterLink) {
		this.twitterLink = twitterLink;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Override
	public int compareTo(Contact o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public String toString() {
		return "Contact [phone=" + phone + ", mobilePhone=" + mobilePhone + ", fax=" + fax + ", facebookLink="
				+ facebookLink + ", instagramLink=" + instagramLink + ", twitterLink=" + twitterLink + ", address="
				+ address + "]";
	}	
	
}
