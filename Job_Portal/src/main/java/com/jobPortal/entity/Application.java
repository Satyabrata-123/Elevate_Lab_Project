package com.jobPortal.entity;

import java.time.LocalDate;

import ch.qos.logback.core.status.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="applications")
public class Application {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private LocalDate appliedDate;
	
	@ManyToOne
	@JoinColumn(name="job_id")
	private Job job;
	
	@ManyToOne
	@JoinColumn(name="applicant_id")
	private User applicant; 
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	public enum Status{
		PENDING,ACCEPTED,REJECTED
	}
	
}
