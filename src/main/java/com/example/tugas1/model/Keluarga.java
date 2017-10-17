package com.example.tugas1.model;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Keluarga {
	private int id;

	private String nomor_kk;
	@NotEmpty
	private String alamat;
	@NotEmpty
	private String rt;
	@NotEmpty
	private String rw;
	@NotNull
	private int id_kelurahan;
	
	private int is_tidak_berlaku;
	private List<Penduduk> anggotaKeluarga;

	public String convertStatusKK() {
		return this.is_tidak_berlaku == 0 ? "Tidak valid" : "valid";
	}
}
