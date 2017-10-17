package com.example.tugas1.model;


import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Penduduk {
	private int id;
	
	private String nik;
	@NotEmpty
	private String nama;
	@NotEmpty
	private String tempat_lahir;
	@NotEmpty
	private String tanggal_lahir;
	@NotNull
	private int jenis_kelamin;
	@NotNull
	private int is_wni;
	@NotNull
	private int id_keluarga;
	@NotEmpty
	private String agama;
	@NotEmpty
	private String status_perkawinan;
	@NotEmpty
	private String status_dalam_keluarga;
	@NotEmpty
	private String golongan_darah;
	@NotNull
	private int is_wafat;
	@NotEmpty
	private String pekerjaan;
	
	private Keluarga keluarga;
	private Kelurahan kelurahan;
	private Kecamatan kecamatan;
	private Kota kota;
	
	public String convertJenisKelamin(){
		return this.jenis_kelamin == 0 ? "Pria" : "Wanita";
	}
	
	public String convertWni(){
		return this.is_wni == 0 ? "WNA" : "WNI";
	}
	
	public String convertWafat(){
		return this. is_wafat == 0 ? "hidup" : "wafat";
	}
	
	
	
}
