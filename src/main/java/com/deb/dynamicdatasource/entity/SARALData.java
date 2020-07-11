package com.deb.dynamicdatasource.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="saral_data")
@Table(name = "saral_data")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SARALData {

	@Id
	@GeneratedValue
	private Long id;
	
	private String ftpPath;
	
}
