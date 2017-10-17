package com.example.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.tugas1.model.Kecamatan;
import com.example.tugas1.model.Kelurahan;
import com.example.tugas1.model.Kota;
import com.example.tugas1.service.LokasiService;

@Controller
public class LokasiController {

	@Autowired
	LokasiService lokasiDAO;
	
	@RequestMapping(value="/kota", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<Kota> kota(){
		List<Kota> kota = lokasiDAO.selectAllKota();
		return kota;
	}
	
	@RequestMapping(value="/kecamatan", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<Kecamatan> kecamatan(@RequestParam("id_kota") int id_kota){
		List<Kecamatan> kecamatan = lokasiDAO.selectKecamatanByKota(id_kota);
		return kecamatan;
	}
	
	@RequestMapping(value="/kelurahan", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<Kelurahan> kelurahan(@RequestParam("id_kecamatan") int id_kecamatan){
		List<Kelurahan> kelurahan = lokasiDAO.selectKelurahanByKecamatan(id_kecamatan);
		return kelurahan;
	}


}
