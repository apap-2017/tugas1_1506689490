package com.example.tugas1.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.tugas1.model.Kecamatan;
import com.example.tugas1.model.Keluarga;
import com.example.tugas1.model.Kelurahan;
import com.example.tugas1.model.Kota;
import com.example.tugas1.model.Penduduk;
import com.example.tugas1.service.KeluargaService;
import com.example.tugas1.service.LokasiService;
import com.example.tugas1.service.PendudukService;

@Controller
public class KeluargaController {
	
	@Autowired
	PendudukService pendudukDAO;
	@Autowired
	KeluargaService keluargaDAO;
	@Autowired
	LokasiService lokasiDAO;
	
	@RequestMapping("/keluarga")
	public String keluarga(@RequestParam(value = "nkk", required = false) String nkk, Model model){
		if(nkk == null){
			return "not-found";
		}
		
		Keluarga keluarga = keluargaDAO.selectKeluargaNkk(nkk);
		if(keluarga == null){
			return "not-found";
		}
		Kelurahan kelurahan = lokasiDAO.selectKelurahan(keluarga.getId_kelurahan());
		Kecamatan kecamatan = lokasiDAO.selectKecamatan(kelurahan.getId_kecamatan());
		Kota kota = lokasiDAO.selectKota(kecamatan.getId_kota());
		
		model.addAttribute("keluarga", keluarga);
		model.addAttribute("kelurahan", kelurahan);
		model.addAttribute("kecamatan", kecamatan);
		model.addAttribute("kota", kota);
				
		return "keluarga";
	}
	
	@RequestMapping("/keluarga/tambah")
	public String formAddKeluarga(Model model){
		model.addAttribute("keluarga", new Keluarga());
		model.addAttribute("isUpdated", false);
		return "form-add-keluarga";
	}
	
	@RequestMapping(value = "/keluarga/tambah", method = RequestMethod.POST)
	public String addKeluarga(@Valid Keluarga keluarga, BindingResult bindingResult, Model model){
		
		
		if(bindingResult.hasErrors()){
			model.addAttribute("keluarga", new Keluarga());
			model.addAttribute("isUpdated", false);
			model.addAttribute("isError", true);
			return "form-add-keluarga";
		}
		
		Kelurahan kelurahan = lokasiDAO.selectKelurahan(keluarga.getId_kelurahan());
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDate localDate = LocalDate.now();
		String date = dtf.format(localDate);
		String[] dateDetail = date.split("/");
		
		
		String nkk = kelurahan.getKode_kelurahan().substring(0,6) + dateDetail[2] + dateDetail[1] + dateDetail[0].substring(2);
		List<Keluarga> similar = keluargaDAO.selectSimilarNkk(nkk);
		String nomor_urut = "0001";
		if(similar.size() > 0){
			nomor_urut = "" + (Integer.parseInt(similar.get(similar.size() - 1).getNomor_kk().substring(12)) + 1);
			for(int i = nomor_urut.length(); i < 4; i++){
				nomor_urut = "0" + nomor_urut;
			}
		}
		
		nkk += nomor_urut;
		keluarga.setNomor_kk(nkk);
		
		
		keluargaDAO.addKeluarga(keluarga);
		model.addAttribute("nkk", nkk);
		model.addAttribute("isUpdated", true);
		return "form-add-keluarga";
	}
	
	@RequestMapping(value={"/keluarga/ubah", "/keluarga/ubah/{nkk}"})
	public String formUpdateKeluarga(@PathVariable Optional<String> nkk ,Model model){
		if(!nkk.isPresent()){
			return "not-found";
		}
		Keluarga keluarga = keluargaDAO.selectKeluargaNkk(nkk.get());
		if(keluarga == null){
			return "not-found";
		}
		model.addAttribute("keluarga", keluarga);
		Kelurahan kelurahan = lokasiDAO.selectKelurahan(keluarga.getId_kelurahan());
		Kecamatan kecamatan = lokasiDAO.selectKecamatan(kelurahan.getId_kecamatan());
		
		model.addAttribute("id_kelurahan", keluarga.getId_kelurahan());
		model.addAttribute("id_kecamatan", kelurahan.getId_kecamatan());
		model.addAttribute("id_kota", kecamatan.getId_kota());
		
		model.addAttribute("isUpdated", false);
		return "form-update-keluarga";
	}
	
	@RequestMapping(value = "/keluarga/ubah/{nkk}", method = RequestMethod.POST)
	public String updateKeluarga(@Valid Keluarga keluarga, BindingResult bindingResult, Model model){
		String oldNkk = keluarga.getNomor_kk();
		if(bindingResult.hasErrors()){
			Keluarga temp = keluargaDAO.selectKeluargaNkk(keluarga.getNomor_kk());
			model.addAttribute("keluarga", temp);
			Kelurahan kelurahan = lokasiDAO.selectKelurahan(keluarga.getId_kelurahan());
			Kecamatan kecamatan = lokasiDAO.selectKecamatan(kelurahan.getId_kecamatan());
			
			model.addAttribute("id_kelurahan", keluarga.getId_kelurahan());
			model.addAttribute("id_kecamatan", kelurahan.getId_kecamatan());
			model.addAttribute("id_kota", kecamatan.getId_kota());
			model.addAttribute("isError", true);
			model.addAttribute("isUpdated", false);
			return "form-update-keluarga";
		}
		
		Kelurahan kelurahan = lokasiDAO.selectKelurahan(keluarga.getId_kelurahan());
		Kecamatan kecamatan = lokasiDAO.selectKecamatan(kelurahan.getId_kecamatan());
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		
		String date = keluarga.getNomor_kk().substring(6,12);
		
		String nkk = kelurahan.getKode_kelurahan().substring(0,6);
		if(!nkk.equals(keluarga.getNomor_kk().substring(0, 6))){
			
			List<Keluarga> similar = keluargaDAO.selectSimilarNkk(nkk);
			String nomor_urut = "0001";
			if(similar.size() > 0){
				nomor_urut = "" + (Integer.parseInt(similar.get(similar.size() - 1).getNomor_kk().substring(12)) + 1);
				for(int i = nomor_urut.length(); i < 4; i++){
					nomor_urut = "0" + nomor_urut;
				}
			}
			
			nkk += date + nomor_urut;
			
			keluarga.setNomor_kk(nkk);
			List<Penduduk> anggotaKeluarga = pendudukDAO.selectAnggotaKeluarga(keluarga.getId());
			
			
			for(Penduduk penduduk : anggotaKeluarga){
				String[] birth = penduduk.getTanggal_lahir().split("-");
				String year = birth[0].substring(2);
				String month = birth[1];
				int tempDay = Integer.parseInt(birth[2]);
				tempDay = penduduk.getJenis_kelamin() == 0 ? tempDay : tempDay + 40;
				String day = tempDay < 10 ? "0" + tempDay : "" + tempDay;
				String nik = kecamatan.getKode_kecamatan().substring(0, kecamatan.getKode_kecamatan().length() - 1);
				nik += day + month + year;
				if(!nik.equals(penduduk.getNik().substring(0, 12))){
					List<Penduduk> similar_penduduk = pendudukDAO.selectSimilarNik(nik);
					String nomor_urut_penduduk = "0001";
					if(similar_penduduk.size() > 0){
						nomor_urut_penduduk = "" + (Integer.parseInt(similar_penduduk.get(similar_penduduk.size() - 1).getNik().substring(12)) + 1);
						for(int i = nomor_urut_penduduk.length(); i < 4; i++){
							nomor_urut_penduduk = "0" + nomor_urut_penduduk;
						}
					}		
					nik += nomor_urut_penduduk;
					penduduk.setNik(nik);
					pendudukDAO.updatePenduduk(penduduk);
				}
			}
		}
		
		keluargaDAO.updateKeluarga(keluarga);
		model.addAttribute("keluarga", keluarga);
		
		model.addAttribute("id_kelurahan", keluarga.getId_kelurahan());
		model.addAttribute("id_kecamatan", kelurahan.getId_kecamatan());
		model.addAttribute("id_kota", kecamatan.getId_kota());
		model.addAttribute("nkk", oldNkk);
		model.addAttribute("isUpdated", true);
		model.addAttribute("isError", false);
		return "form-update-keluarga";
	}
}
