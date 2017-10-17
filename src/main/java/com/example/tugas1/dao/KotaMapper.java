package com.example.tugas1.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.tugas1.model.Kota;

@Mapper
public interface KotaMapper {
	@Select("Select * from kota where id = #{id_kota}")
	Kota selectKota(@Param("id_kota") int id_kota);
	
	@Select("Select * from kota")
	List<Kota> selectAllKota();
}
