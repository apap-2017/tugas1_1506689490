package com.example.tugas1.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.tugas1.model.Penduduk;


@Mapper
public interface PendudukMapper {
	
	@Select("Select * from penduduk where nik = #{nik}")
	Penduduk selectPenduduk(@Param("nik") String nik);
	
	@Select("Select * from penduduk where id = #{id}")
	Penduduk selectPendudukById(@Param("id") int id);
	
	@Select("Select * from penduduk where id_keluarga = #{id_keluarga}")
	List<Penduduk> selectAnggotaKeluarga(@Param("id_keluarga") int id_keluarga);
	
	@Select("Select * from penduduk where nik like #{nik}")
	List<Penduduk> selectSimilarNik(@Param("nik") String nik);
	
	@Select("Select nik, nama, jenis_kelamin, tanggal_lahir from penduduk join keluarga on penduduk.id_keluarga = keluarga.id "
			+ "where id_kelurahan = #{id_kelurahan}")
	@Results(value = {
			@Result(property="nik", column="nik"),
			@Result(property="nama", column="nama"),
			@Result(property="jenis_kelamin", column="jenis_kelamin"),
			@Result(property="tanggal_lahir", column="tanggal_lahir")

	})
	List<Penduduk> selectPendudukByLocation(@Param("id_kelurahan") int id_kelurahan);
	
	@Insert("Insert into penduduk(nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, is_wni, "
			+ " id_keluarga, agama, pekerjaan, status_perkawinan, status_dalam_keluarga, golongan_darah, is_wafat)"
			+ "VALUES(#{nik}, #{nama}, #{tempat_lahir}, #{tanggal_lahir}, #{jenis_kelamin}, #{is_wni}, "
			+ " #{id_keluarga}, #{agama}, #{pekerjaan}, #{status_perkawinan}, #{status_dalam_keluarga}, #{golongan_darah}, #{is_wafat})")
	void addPenduduk(Penduduk penduduk);
	
	
	@Update("update penduduk set "
			+ "nik = #{nik}, nama = #{nama}, tempat_lahir = #{tempat_lahir}, tanggal_lahir = #{tanggal_lahir}, "
			+ "jenis_kelamin = #{jenis_kelamin}, is_wni = #{is_wni}, id_keluarga = #{id_keluarga}, agama = #{agama}, "
			+ "pekerjaan = #{pekerjaan}, status_perkawinan = #{status_perkawinan}, "
			+ "status_dalam_keluarga = #{status_dalam_keluarga}, golongan_darah = #{golongan_darah} "
			+ "where id = #{id}")
	void updatePenduduk(Penduduk penduduk);
	
	@Update("Update penduduk set is_wafat = 1 where nik = #{nik}")
	void updateStatusKematian(@Param("nik") String nik);
	
	
}
