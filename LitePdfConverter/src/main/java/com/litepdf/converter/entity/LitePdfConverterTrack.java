package com.litepdf.converter.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "litepdf_converter_track")
public class LitePdfConverterTrack {

	@Id
	//@GeneratedValue (strategy = GenerationType.IDENTITY)
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "track_id")
	private Integer trackId;
	
	@Column(name = "user_ip")
	private String userIp;
	
	@Column(name = "user_port")
	private String userPort;

	@Column(name = "create_date")
	private Date createDate;

	@Column(name = "convert_request_type")
	private String convertRequestType;

	@Column(name = "is_converted")
	private Integer isConverted;

	/**
	 * @return the trackId
	 */
	public Integer getTrackId() {
		return trackId;
	}

	/**
	 * @param trackId the trackId to set
	 */
	public void setTrackId(Integer trackId) {
		this.trackId = trackId;
	}

	/**
	 * @return the userIp
	 */
	public String getUserIp() {
		return userIp;
	}

	/**
	 * @param userIp the userIp to set
	 */
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	/**
	 * @return the userPort
	 */
	public String getUserPort() {
		return userPort;
	}

	/**
	 * @param userPort the userPort to set
	 */
	public void setUserPort(String userPort) {
		this.userPort = userPort;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the convertRequestType
	 */
	public String getConvertRequestType() {
		return convertRequestType;
	}

	/**
	 * @param convertRequestType the convertRequestType to set
	 */
	public void setConvertRequestType(String convertRequestType) {
		this.convertRequestType = convertRequestType;
	}

	/**
	 * @return the isConverted
	 */
	public Integer getIsConverted() {
		return isConverted;
	}

	/**
	 * @param isConverted the isConverted to set
	 */
	public void setIsConverted(Integer isConverted) {
		this.isConverted = isConverted;
	}
	
	
	
	
}
