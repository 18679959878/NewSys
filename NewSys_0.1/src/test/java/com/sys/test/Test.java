package com.sys.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.transaction.Transactional;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import com.sys.dao.AdminMapper;
import com.sys.dao.ClassGradeMapper;
import com.sys.dao.OpenReportMapper;
import com.sys.dao.StudentMapper;
import com.sys.dao.TeacherMapper;
import com.sys.entity.Admin;
import com.sys.entity.ClassGrade;
import com.sys.entity.DateStage;
import com.sys.entity.OpenReport;
import com.sys.entity.Student;
import com.sys.entity.Teacher;
import com.sys.service.AdminService;
import com.sys.utils.DateTimeUtils;
import com.sys.utils.ExcelProcess;
import com.sys.web.DateStageController;

public class Test extends TestBase {

	@Autowired
	private OpenReportMapper openReportMapper;
	@Autowired
	private StudentMapper studentMapper;
	@Autowired
	private TeacherMapper teacherMapper;
	// @Autowired
	// private FinalProjectService finalProjectService;

	@org.junit.Test
	@Transactional
	public void test12() {
		// ArrayList<ClassGrade> list =
		// classGradeMapper.selectClassGradeOfDateStage(ClassGrade.OLD_CLASS,
		// DateStage.FIELD_WORK_TYPE);
		// System.out.println(list.size());
		openReportMapper.saveOrUpdate(new OpenReport("20151943", "1", "1", "1", "1", "1", "1", "1"));
	}

	@org.junit.Test
	@Transactional
	public void test() throws FileNotFoundException, IOException {
		Workbook workbook = new XSSFWorkbook(new FileInputStream(new File("f:/1.xlsx")));
		System.out.println("进入了吗111");
		// 遍历每一个工作表
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			// 获得第i个工作表
			Sheet sheet = workbook.getSheetAt(i);
			// 验证表格模板是否正确
			for (int j = 0; j <= sheet.getLastRowNum(); j++) {// getLastRowNum()获得最大的行数
				// 获取表格行
				Row row = sheet.getRow(j);
				// 保存每行数据的Student对象
				Student student = new Student();
				Teacher teacher = new Teacher();
				if (row != null) {
					row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
					System.out.println(11);
					if (row.getCell(0) != null)
						student.setStuId(row.getCell(0).getStringCellValue().trim());
					if (row.getCell(2) != null) {
						student.setWorksTea(row.getCell(2).getStringCellValue().trim());
						teacher = new Teacher(row.getCell(2).getStringCellValue().trim(),
								row.getCell(2).getStringCellValue().trim(), "", "1234567");
					}
				}
				Student s_student = studentMapper.select(student.getStuId());
				if (s_student != null) {
					s_student.setWorksTea("");
					s_student.setFieldworkTea(teacher.getTeaAccount());
					studentMapper.update(s_student);
				}
				if (teacherMapper.select(teacher.getTeaAccount()) == null) {
					teacherMapper.save(teacher);
				}
			}
		}
	}
}
