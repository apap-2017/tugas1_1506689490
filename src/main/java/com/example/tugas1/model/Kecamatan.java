package com.example.tugas1.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Kecamatan {
	private int id;
	@NotEmpty
	private String kode_kecamatan;
	@NotNull
	private int id_kota;
	@NotEmpty
	private String nama_kecamatan;
}
