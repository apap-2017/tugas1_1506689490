package com.example.tugas1.service;

import java.util.List;

import com.example.tugas1.model.Penduduk;

public interface PendudukService {
	
	Penduduk selectPenduduk(String nik);
	List<Penduduk> selectSimilarNik(String nik);
	void addPenduduk(Penduduk penduduk);
	void updatePenduduk(Penduduk penduduk);
	void updateStatusKematian(String nik);
	Penduduk selectPendudukById(int id);
	List<Penduduk> selectPendudukBylocation(int id_kelurahan);
	List<Penduduk> selectAnggotaKeluarga(int id_keluarga);
}
