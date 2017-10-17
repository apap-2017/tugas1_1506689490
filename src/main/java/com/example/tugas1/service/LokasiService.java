package com.example.tugas1.service;

import java.util.List;

import com.example.tugas1.model.Kecamatan;
import com.example.tugas1.model.Kelurahan;
import com.example.tugas1.model.Kota;

public interface LokasiService {
	Kelurahan selectKelurahan(int id_kelurahan);
	Kecamatan selectKecamatan(int id_kecamatan);
	Kota selectKota(int id_kota);
	List<Kota> selectAllKota();
	List<Kecamatan> selectKecamatanByKota(int id_kota);
	List<Kelurahan> selectKelurahanByKecamatan(int id_kecamatan);
}
