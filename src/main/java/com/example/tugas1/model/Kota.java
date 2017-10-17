package com.example.tugas1.model;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Kota {
	private int id;
	@NotEmpty
	private String kode_kota;
	@NotEmpty	
	private String nama_kota;
}
