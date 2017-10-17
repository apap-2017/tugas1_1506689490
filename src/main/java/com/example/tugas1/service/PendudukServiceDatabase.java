package com.example.tugas1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tugas1.dao.PendudukMapper;
import com.example.tugas1.model.Penduduk;

@Service
public class PendudukServiceDatabase implements PendudukService {
	
	@Autowired
	PendudukMapper pendudukMapper;
	
	@Override
	public Penduduk selectPenduduk(String nik) {
		
		return pendudukMapper.selectPenduduk(nik);
	}

	@Override
	public List<Penduduk> selectSimilarNik(String nik) {
		// TODO Auto-generated method stub
		return pendudukMapper.selectSimilarNik(nik + "%");
	}

	@Override
	public void addPenduduk(Penduduk penduduk) {
		// TODO Auto-generated method stub
		pendudukMapper.addPenduduk(penduduk);
	}

	@Override
	public void updatePenduduk(Penduduk penduduk) {
		// TODO Auto-generated method stub
		pendudukMapper.updatePenduduk(penduduk);
	}

	@Override
	public Penduduk selectPendudukById(int id) {
		// TODO Auto-generated method stub
		return pendudukMapper.selectPendudukById(id);
	}

	@Override
	public List<Penduduk> selectPendudukBylocation(int id_kelurahan) {
		// TODO Auto-generated method stub
		return pendudukMapper.selectPendudukByLocation(id_kelurahan);
	}

	@Override
	public void updateStatusKematian(String nik) {
		// TODO Auto-generated method stub
		pendudukMapper.updateStatusKematian(nik);
		System.out.println("HAHA");
	}

	@Override
	public List<Penduduk> selectAnggotaKeluarga(int id_keluarga) {
		// TODO Auto-generated method stub
		return pendudukMapper.selectAnggotaKeluarga(id_keluarga);
	}

}
