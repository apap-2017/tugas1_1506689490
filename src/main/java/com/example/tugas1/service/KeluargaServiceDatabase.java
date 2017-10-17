package com.example.tugas1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tugas1.dao.KeluargaMapper;
import com.example.tugas1.model.Keluarga;

@Service
public class KeluargaServiceDatabase implements KeluargaService {
	@Autowired 
	KeluargaMapper keluargaMapper;

	@Override
	public Keluarga selectKeluarga(int id_keluarga) {
		// TODO Auto-generated method stub
		return keluargaMapper.selectKeluarga(id_keluarga);
	}

	@Override
	public Keluarga selectKeluargaNkk(String nkk) {
		// TODO Auto-generated method stub
		return keluargaMapper.selectKeluargaNkk(nkk);
	}

	@Override
	public List<Keluarga> selectSimilarNkk(String nkk) {
		// TODO Auto-generated method stub
		return keluargaMapper.selectSimilarNkk(nkk + "%");
	}

	@Override
	public void addKeluarga(Keluarga keluarga) {
		// TODO Auto-generated method stub
		keluargaMapper.addKeluarga(keluarga);
	}

	@Override
	public void updateKeluarga(Keluarga keluarga) {
		// TODO Auto-generated method stub
		keluargaMapper.updateKeluarga(keluarga);
	}

	@Override
	public void updateStatusKeluarga(int id_keluarga) {
		// TODO Auto-generated method stub
		keluargaMapper.updateStatusKeluarga(id_keluarga);
	}
	
	
}
