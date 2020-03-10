package com.sifast.convertToJava.tn.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.TimeZone;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class TimestampEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@CreatedDate
	@Column(name = "creation_date", insertable = true, updatable = false)
	@JsonFormat(timezone = "GMT+1", pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime creationDate;

	@LastModifiedDate
	@Column(name = "update_date", nullable = true)
	@JsonFormat(timezone = "GMT+1", pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updateDate;

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDateTime updateDate) {
		this.updateDate = updateDate;
	}

	@PrePersist
	void onPrePersist() {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+1"));
		this.setCreationDate(LocalDateTime.now());
		this.setUpdateDate(null);
	}

	@PreUpdate
	void onPreUpdate() {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+1"));
		this.setUpdateDate(LocalDateTime.now());
	}

}
