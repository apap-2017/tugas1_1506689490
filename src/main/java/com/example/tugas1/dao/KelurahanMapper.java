package com.example.tugas1.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.tugas1.model.Kelurahan;

@Mapper
public interface KelurahanMapper {

	@Select("Select * from kelurahan where id = #{id_kelurahan}")
	Kelurahan selectKelurahan(@Param("id_kelurahan") int id_kelurahan);
	
	@Select("Select * from kelurahan where id_kecamatan = #{id_kecamatan}")
	List<Kelurahan> selectKelurahanByKecamatan(@Param("id_kecamatan") int id_kecamatan);
}