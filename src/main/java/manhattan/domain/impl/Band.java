package manhattan.domain.impl;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import manhattan.domain.AbstractEntity;

@Entity
@Table(name = "BAND")
@AttributeOverride(name = "id", column = @Column(name = "BAND_ID"))
public class Band extends AbstractEntity<Band> {

	private static final long serialVersionUID = 3700557193500224813L;
	
	private String bandName;
	private List<String> bandMembers;
	private Link picture;
	private boolean standartBand;
	private String bandInfo;
	
	public Band() {
		this.picture = new Link();
		this.bandMembers = new ArrayList<>();
	}
	
	@Column(name = "BAND_NAME", length = 100, nullable = false, unique = true)
	public String getBandName() {
		return bandName;
	}
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "PICTURE_ID", nullable = false)
	public Link getPicture() {
		return picture;
	}
	
	@Column(name = "STANDART_BAND")
	public boolean isStandartBand() {
		return standartBand;
	}
	
	@Column(name = "BAND_INFO", length = 1000)
	public String getBandInfo() {
		return bandInfo;
	}
	
	@ElementCollection(fetch = FetchType.EAGER)
//	@CollectionTable(name = "BAND_MEMBER_LIST", joinColumns = @JoinColumn(name="BAND_ID"))
	public List<String> getBandMembers() {
		return bandMembers;
	}
	
	public void setBandName(String bandName) {
		this.bandName = bandName;
	}
	
	public void setBandMembers(List<String> bandMembers) {
		this.bandMembers = bandMembers;
	}
	
	public void setPicture(Link picture) {
		this.picture = picture;
	}
	
	public void setStandartBand(boolean standartBand) {
		this.standartBand = standartBand;
	}
	
	public void setBandInfo(String bandInfo) {
		this.bandInfo = bandInfo;
	}

	@Override
	public int compareTo(Band o) {
		return this.bandName.compareTo(o.bandName);
	}

	@Override
	public String toString() {
		return "Band [bandName=" + bandName + ", bandMembers=" + bandMembers + ", picture=" + picture
				+ ", standartBand=" + standartBand + ", bandInfo=" + bandInfo + "]";
	}
		
}
