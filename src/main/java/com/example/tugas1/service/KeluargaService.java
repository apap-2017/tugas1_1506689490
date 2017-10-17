package com.example.tugas1.service;

import java.util.List;

import com.example.tugas1.model.Keluarga;

public interface KeluargaService {
	Keluarga selectKeluarga(int id_keluarga);
	Keluarga selectKeluargaNkk(String nkk);
	List<Keluarga> selectSimilarNkk(String nkk);
	void addKeluarga(Keluarga keluarga);
	void updateKeluarga(Keluarga keluarga);
	void updateStatusKeluarga(int id_keluarga);
}
