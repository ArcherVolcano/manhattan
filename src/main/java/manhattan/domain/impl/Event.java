package manhattan.domain.impl;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import manhattan.domain.AbstractEntity;
@Entity
@Table(name = "EVENT")
@AttributeOverride(name = "id", column = @Column(name="EVENT_ID"))
public class Event extends AbstractEntity<Event>{
	
	private static final long serialVersionUID = 1L;
	
	private String eventName;
	private Date startDate;
	private Date endDate;
	private Band band;
	private Link picture;
	private String eventInfo;
	private String ticketPrice;
	private String catering;
	
	public Event() {
		this.band = new Band();
		this.picture = new Link();
	}
	
	@Column(name = "EVENT_NAME", nullable = false, unique = true)
	public String getEventName() {
		return eventName;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "START_DATE", nullable = false)
	public Date getStartDate() {
		return startDate;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "END_DATE", nullable = false)
	public Date getEndDate() {
		return endDate;
	}
	
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "BAND_ID", nullable = false)
	public Band getBand() {
		return band;
	}
	
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "PICTURE_ID", nullable = false)
	public Link getPicture() {
		return picture;
	}
	
	@Column(name = "EVENT_INFO")
	public String getEventInfo() {
		return eventInfo;
	}
	
	
	@Column(name = "TICKET_PRICE")
	public String getTicketPrice() {
		return ticketPrice;
	}
	
	@Column(name = "CATERING")
	public String getCatering() {
		return catering;
	}
	
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public void setBand(Band band) {
		this.band = band;
	}

	public void setPicture(Link picture) {
		this.picture = picture;
	}
	
	public void setEventInfo(String eventInfo) {
		this.eventInfo = eventInfo;
	}
	
	public void setTicketPrice(String ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	public void setCatering(String catering) {
		this.catering = catering;
	}

	@Override
	public int compareTo(Event o) {
		return this.getStartDate().compareTo(o.getStartDate());
	}

	@Override
	public String toString() {
		return "Event [startDate=" + startDate + ", endDate=" + endDate + ", band=" + band + ", picture=" + picture
				+ ", eventInfo=" + eventInfo + ", ticketPrice=" + ticketPrice + ", catering=" + catering + "]";
	}



}
