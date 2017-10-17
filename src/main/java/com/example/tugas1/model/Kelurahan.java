package com.example.tugas1.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Kelurahan {
	private int id;
	@NotEmpty
	private String kode_kelurahan;
	@NotNull
	private int id_kecamatan;
	@NotEmpty
	private String nama_kelurahan;
	@NotEmpty
	private String kode_pos;
}
