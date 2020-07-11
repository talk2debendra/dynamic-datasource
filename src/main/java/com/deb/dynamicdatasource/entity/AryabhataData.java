package com.deb.dynamicdatasource.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="aryabhat_data")
@Table(name = "aryabhat_data")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AryabhataData {

	@Id
	@GeneratedValue
	private Long id;
	
	private String ftpPath;
	
}
