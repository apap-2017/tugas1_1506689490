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
public class PendudukController {
	
	@Autowired
	PendudukService pendudukDAO;
	@Autowired
	KeluargaService keluargaDAO;
	@Autowired
	LokasiService lokasiDAO;
	
	@RequestMapping("/")
	public String index(){
		return "index";
	}
	
	@RequestMapping("/penduduk")
	public String penduduk(@RequestParam(value = "nik", required = false) String nik, Model model){
		if(nik == null){
			return "not-found";
		}
		Penduduk penduduk = pendudukDAO.selectPenduduk(nik);
		if(penduduk == null){
			return "not-found";
		}
		
		Keluarga keluarga = keluargaDAO.selectKeluarga(penduduk.getId_keluarga());
		Kelurahan kelurahan = lokasiDAO.selectKelurahan(keluarga.getId_kelurahan());
		Kecamatan kecamatan = lokasiDAO.selectKecamatan(kelurahan.getId_kecamatan());
		Kota kota = lokasiDAO.selectKota(kecamatan.getId_kota());
		
		model.addAttribute("penduduk", penduduk);
		model.addAttribute("keluarga", keluarga);
		model.addAttribute("kelurahan", kelurahan);
		model.addAttribute("kecamatan", kecamatan);
		model.addAttribute("kota", kota);
		model.addAttribute("isChanged", false);
		return "penduduk";
	}
	
	@RequestMapping(value = "/penduduk", method = RequestMethod.POST)
	public String ubahStatus(@RequestParam(value="nik", required = false) String nik, @RequestParam(value="id_keluarga") int id_keluarga, Model model){
		
		if(nik == null){
			return "not-found";
		}
		
		pendudukDAO.updateStatusKematian(nik);
		
		List<Penduduk> anggotaKeluarga = pendudukDAO.selectAnggotaKeluarga(id_keluarga);
		boolean isTidakBerlaku = true;
		for(Penduduk penduduk : anggotaKeluarga){
			if(penduduk.getIs_wafat() == 0){
				isTidakBerlaku = false;
				break;
			}
		}
		
		if(isTidakBerlaku){
			keluargaDAO.updateStatusKeluarga(id_keluarga);
		}
		Penduduk penduduk = pendudukDAO.selectPenduduk(nik);
		Keluarga keluarga = keluargaDAO.selectKeluarga(penduduk.getId_keluarga());
		Kelurahan kelurahan = lokasiDAO.selectKelurahan(keluarga.getId_kelurahan());
		Kecamatan kecamatan = lokasiDAO.selectKecamatan(kelurahan.getId_kecamatan());
		Kota kota = lokasiDAO.selectKota(kecamatan.getId_kota());
		
		model.addAttribute("keluarga", keluarga);
		model.addAttribute("kelurahan", kelurahan);
		model.addAttribute("kecamatan", kecamatan);
		model.addAttribute("kota", kota);
		model.addAttribute("penduduk", penduduk);
		model.addAttribute("isChanged", true);
		return "penduduk";
	}
	
	
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
	
	@RequestMapping("/penduduk/tambah")
	public String formAddPenduduk(Model model){
		model.addAttribute("penduduk", new Penduduk());
		model.addAttribute("isAdded", false);
		return "form-add-penduduk";
	}
	
	@RequestMapping(value = "/penduduk/tambah", method = RequestMethod.POST)
	public String addPenduduk(@Valid Penduduk penduduk, BindingResult bindingResult,
            Model model){
		
		if(bindingResult.hasErrors()){
			model.addAttribute("penduduk", new Penduduk());
			model.addAttribute("isAdded", false);
			model.addAttribute("isError", true);
    		return "form-add-penduduk";
    	}
		
		Keluarga keluarga = keluargaDAO.selectKeluarga(penduduk.getId_keluarga());
		if(keluarga == null){
			model.addAttribute("penduduk", new Penduduk());
			model.addAttribute("isAdded", false);
			model.addAttribute("isError", true);
			return "form-add-penduduk";
		}
		Kelurahan kelurahan = lokasiDAO.selectKelurahan(keluarga.getId_kelurahan());
		Kecamatan kecamatan = lokasiDAO.selectKecamatan(kelurahan.getId_kecamatan());
		
		
		String[] birth = penduduk.getTanggal_lahir().split("-");
		String year = birth[0].substring(2);
		String month = birth[1];
		int tempDay = Integer.parseInt(birth[2]);
		tempDay = penduduk.getJenis_kelamin() == 0 ? tempDay : tempDay + 40;
		String day = tempDay < 10 ? "0" + tempDay : "" + tempDay;

		String nik = kecamatan.getKode_kecamatan().substring(0, kecamatan.getKode_kecamatan().length() - 1);
		nik += day + month + year;
		
		List<Penduduk> similar = pendudukDAO.selectSimilarNik(nik);
		String nomor_urut = "0001";
		if(similar.size() > 0){
			nomor_urut = "" + (Integer.parseInt(similar.get(similar.size() - 1).getNik().substring(12)) + 1);
			for(int i = nomor_urut.length(); i < 4; i++){
				nomor_urut = "0" + nomor_urut;
			}
		}
		
		nik += nomor_urut;
		penduduk.setNik(nik);
		
		pendudukDAO.addPenduduk(penduduk);
		model.addAttribute("nik", nik);
		model.addAttribute("isAdded", true);
		return "form-add-penduduk";
	}
	
	@RequestMapping(value={"/penduduk/ubah", "/penduduk/ubah/{nik}"})
	public String formUpdatePenduduk(@PathVariable Optional<String> nik, Model model){
		if(!nik.isPresent()){
			return "not-found";
		}
		Penduduk penduduk = pendudukDAO.selectPenduduk(nik.get());
		if(penduduk == null){
			return "not-found";
		}
		model.addAttribute("penduduk", penduduk);
		model.addAttribute("isUpdated", false);
		return "form-update-penduduk";
	}
	
	@RequestMapping(value="/penduduk/ubah/{nik}", method = RequestMethod.POST)
	public String updatePenduduk(@Valid Penduduk penduduk, BindingResult bindingResult, Model model){
		
		if(bindingResult.hasErrors()){
			model.addAttribute("penduduk", penduduk);
			model.addAttribute("isUpdated", false);
			model.addAttribute("isError", true);
    		return "form-update-penduduk";
		}
		
		Keluarga keluarga = keluargaDAO.selectKeluarga(penduduk.getId_keluarga());
		if(keluarga == null){
			model.addAttribute("penduduk", penduduk);
			model.addAttribute("isAdded", false);
			model.addAttribute("isError", true);
			return "form-update-penduduk";
		}
		Kelurahan kelurahan = lokasiDAO.selectKelurahan(keluarga.getId_kelurahan());
		Kecamatan kecamatan = lokasiDAO.selectKecamatan(kelurahan.getId_kecamatan());
		 
		String[] birth = penduduk.getTanggal_lahir().split("-");
		String year = birth[0].substring(2);
		String month = birth[1];
		int tempDay = Integer.parseInt(birth[2]);
		tempDay = penduduk.getJenis_kelamin() == 0 ? tempDay : tempDay + 40;
		String day = tempDay < 10 ? "0" + tempDay : "" + tempDay;
		
		String nik = kecamatan.getKode_kecamatan().substring(0, kecamatan.getKode_kecamatan().length() - 1);
		nik += day + month + year;
		
		
		if(!nik.equals(penduduk.getNik().substring(0, 12))){
			List<Penduduk> similar = pendudukDAO.selectSimilarNik(nik);
			String nomor_urut = "0001";
			if(similar.size() > 0){
				nomor_urut = "" + (Integer.parseInt(similar.get(similar.size() - 1).getNik().substring(12)) + 1);
				for(int i = nomor_urut.length(); i < 4; i++){
					nomor_urut = "0" + nomor_urut;
				}
			}
			
			nik += nomor_urut;
			penduduk.setNik(nik);
		}
		
		
		
		pendudukDAO.updatePenduduk(penduduk);
		model.addAttribute("nik", penduduk.getNik());
		model.addAttribute("isUpdated", true);
		return "form-update-penduduk";
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
	public String updateKeluarga(Keluarga keluarga, Model model){
		
		Kelurahan kelurahan = lokasiDAO.selectKelurahan(keluarga.getId_kelurahan());
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDate localDate = LocalDate.now();
		String date = dtf.format(localDate);
		String[] dateDetail = date.split("/");
		
		
		String nkk = kelurahan.getKode_kelurahan().substring(0,6) + dateDetail[2] + dateDetail[1] + dateDetail[0].substring(2);
		if(!nkk.equals(keluarga.getNomor_kk())){

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
		}
		
		keluargaDAO.updateKeluarga(keluarga);
		model.addAttribute("nkk", nkk);
		model.addAttribute("isUpdated", true);
		return "form-update-keluarga";
	}
	
	@RequestMapping("/penduduk/cari")
	public String cariPenduduk(@RequestParam(value = "kota", required = false) String id_kota, 
			@RequestParam(value = "kecamatan", required = false) String id_kecamatan,
			@RequestParam(value = "kelurahan", required = false) String id_kelurahan, Model model){
		
		
		model.addAttribute("kota", lokasiDAO.selectAllKota());
		if(id_kota != null && id_kecamatan != null && id_kelurahan != null){
			List<Penduduk> list_penduduk = pendudukDAO.selectPendudukBylocation(Integer.parseInt(id_kelurahan));
			Kelurahan kelurahan = lokasiDAO.selectKelurahan(Integer.parseInt(id_kelurahan));
			Kecamatan kecamatan = lokasiDAO.selectKecamatan(kelurahan.getId_kecamatan());
			Kota kota = lokasiDAO.selectKota(kecamatan.getId_kota());
			
			model.addAttribute("list_penduduk", list_penduduk);
			model.addAttribute("kelurahan", kelurahan);
			model.addAttribute("kecamatan", kecamatan);
			model.addAttribute("kota", kota);
			model.addAttribute("isResult", false);
			return "list-penduduk";
		}
		else if(id_kota != null){
			int temp_kota = Integer.parseInt(id_kota);
			model.addAttribute("kecamatan", lokasiDAO.selectKecamatanByKota(temp_kota));
			model.addAttribute("selected_id_kota", temp_kota);
			model.addAttribute("isKecamatan", true);
			if(id_kecamatan != null){
				int temp_kecamatan = Integer.parseInt(id_kecamatan);
				model.addAttribute("kelurahan", lokasiDAO.selectKelurahanByKecamatan(temp_kecamatan));
				model.addAttribute("selected_id_kota", temp_kota);
				model.addAttribute("selected_id_kecamatan", temp_kecamatan);
				model.addAttribute("isKelurahan", true);
			}
			
		}
		model.addAttribute("isResult", true);
		return "list-penduduk";
	}
	
}
