package com.example.tugas1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tugas1.dao.KecamatanMapper;
import com.example.tugas1.dao.KelurahanMapper;
import com.example.tugas1.dao.KotaMapper;
import com.example.tugas1.model.Kecamatan;
import com.example.tugas1.model.Kelurahan;
import com.example.tugas1.model.Kota;

@Service
public class LokasiServiceDatabase implements LokasiService {
	@Autowired
	KelurahanMapper kelurahanMapper;
	@Autowired
	KecamatanMapper kecamatanMapper;
	@Autowired
	KotaMapper kotaMapper;
	@Override
	public Kelurahan selectKelurahan(int id_kelurahan) {
		// TODO Auto-generated method stub
		return kelurahanMapper.selectKelurahan(id_kelurahan);
	}
	@Override
	public Kecamatan selectKecamatan(int id_kecamatan) {
		// TODO Auto-generated method stub
		return kecamatanMapper.selectKecamatan(id_kecamatan);
	}
	@Override
	public Kota selectKota(int id_kota) {
		// TODO Auto-generated method stub
		return kotaMapper.selectKota(id_kota);
	}
	@Override
	public List<Kota> selectAllKota() {
		// TODO Auto-generated method stub
		return kotaMapper.selectAllKota();
	}
	@Override
	public List<Kecamatan> selectKecamatanByKota(int id_kota) {
		// TODO Auto-generated method stub
		return kecamatanMapper.selectKecamatanByKota(id_kota);
	}
	@Override
	public List<Kelurahan> selectKelurahanByKecamatan(int id_kecamatan) {
		// TODO Auto-generated method stub
		return kelurahanMapper.selectKelurahanByKecamatan(id_kecamatan);
	}
	
}
