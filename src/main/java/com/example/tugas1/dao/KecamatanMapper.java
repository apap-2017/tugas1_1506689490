package com.example.tugas1.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.tugas1.model.Kecamatan;

@Mapper
public interface KecamatanMapper {
	@Select("Select * from kecamatan where id = #{id_kecamatan}")
	Kecamatan selectKecamatan(@Param("id_kecamatan") int id_kecamatan);
	
	@Select("Select * from kecamatan where id_kota = #{id_kota}")
	List<Kecamatan> selectKecamatanByKota(@Param("id_kota") int id_kota);
}
