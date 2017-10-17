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

import com.example.tugas1.model.Keluarga;

@Mapper
public interface KeluargaMapper {

	@Select("Select * from keluarga where id = #{id_keluarga}")
	Keluarga selectKeluarga(@Param("id_keluarga") int id_keluarga);
	
	@Select("Select * from keluarga where nomor_kk = #{nkk}")
	@Results(value = {
			@Result(property="id", column="id"),
			@Result(property="nomor_kk", column="nomor_kk"),
			@Result(property="alamat", column="alamat"),
			@Result(property="rt", column="rt"),
			@Result(property="rw", column="rw"),
			@Result(property="anggotaKeluarga", column="id",
			javaType = List.class,
			many=@Many(select="com.example.tugas1.dao.PendudukMapper.selectAnggotaKeluarga"))
	})
	Keluarga selectKeluargaNkk(@Param("nkk") String nkk);
	
	@Select("Select nomor_kk from keluarga where nomor_kk like #{nkk}")
	List<Keluarga> selectSimilarNkk(@Param("nkk") String nkk);
	
	@Insert("insert into keluarga(nomor_kk, alamat, rt, rw, id_kelurahan, is_tidak_berlaku) "
			+ "VALUES(#{nomor_kk}, #{alamat}, #{rt}, #{rw}, #{id_kelurahan}, #{is_tidak_berlaku})")
	void addKeluarga(Keluarga keluarga);
	
	@Update("Update keluarga set "
			+ "nomor_kk = #{nomor_kk}, alamat = #{alamat}, rt = #{rt}, rw = #{rw}, id_kelurahan = #{id_kelurahan} "
			+ "where id = #{id}")
	void updateKeluarga(Keluarga keluarga);
	
	@Update("Update keluarga set is_tidak_berlaku = 1 where id = #{id_keluarga}")
	void updateStatusKeluarga(@Param(value = "id_keluarga") int id_keluarga);
	
	
}
