package com.sys.dao;

import com.sys.entity.Curriculum;
import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;

public abstract interface CurriculumMapper {
	public abstract void save(Curriculum paramCurriculum);

	public abstract void saveBatch(ArrayList<Curriculum> paramArrayList);

	public abstract ArrayList<Curriculum> selectOfteaAccount(String paramString);

	public abstract ArrayList<Curriculum> selectOfCouName(String paramString);

	public abstract ArrayList<Curriculum> selectOfClassName(String paramString);

	public abstract ArrayList<Curriculum> selectOfStuYear(String paramString);

	public abstract void delete(@Param("teaAccount") String paramString1, @Param("couName") String paramString2,
			@Param("className") String paramString3);

	public abstract Curriculum select(@Param("couName") String paramString1, @Param("className") String paramString2,
			@Param("stuYear") String paramString3);

	public abstract ArrayList<String> selectStuYear();

	public abstract int selectStuYearNums(String paramString);

	public abstract ArrayList<Curriculum> selectPageOfStuYear(@Param("start") int paramInt1,
			@Param("end") int paramInt2, @Param("stuYear") String paramString);

	public abstract ArrayList<String> selectStuYearOfTea(String paramString);

	public abstract int selectStuYearOfTeaAccountNums(@Param("teaAccount") String paramString1,
			@Param("stuYear") String paramString2);

	public abstract ArrayList<Curriculum> selectPageOfStuYearAndTea(@Param("stuYear") String paramString1,
			@Param("teaAccount") String paramString2, @Param("start") int paramInt1, @Param("end") int paramInt2);

	public abstract void update(Curriculum paramCurriculum);
}