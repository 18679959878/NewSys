package com.sys.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.activation.DataSource;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mchange.v1.xml.StdErrErrorHandler;
import com.sys.entity.Student;
import com.sys.entity.Teacher;

public class ExcelProcess {
	// 学生表生成路径
	public static final String CREATE_STUDENT_PATH = "F:/download/student";
	// 学生表读取路径
	public static final String READ_STUDENT_PATH = "F:/upLoadExcel/student";
	// 表类型
	public static final String EXCEL_TYPE_STUDENT = "student_e";// 学生表
	public static final String EXCEL_TYPE_TEACHER = "teacher_e";// 教师表
	// 验证行内容
	public static final String[] STUDENT_VALIDATE_ROW = { "学号", "姓名", "班级名", "系统密码*", "邮箱*", "毕业设计指导老师*", "实习指导老师*",
			"专业" };// 学生表\
	public static final String[] TEACHER_VALIDATE_ROW = { "教师账号", "教师姓名", "邮箱*", "密码" };// 教师表

	public static ArrayList<? extends Object> importExcel(File file, String type)
			throws FileNotFoundException, IOException {
		// 获取文件后缀名
		String fileName = file.getName();
		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
		// 工作薄
		Workbook workbook;
		// 只处理xls和 xlsx两种格式的表格
		if (suffix.equals("xls")) {
			// 获取xls格式工作薄
			workbook = new HSSFWorkbook(new FileInputStream(file));
		} else if (suffix.equals("xlsx")) {
			// 获取工作薄
			workbook = new XSSFWorkbook(new FileInputStream(file));
			// 获取表格对象
		} else {
			return null;
		}
		if (type.equals(EXCEL_TYPE_STUDENT)) {
			return importStudentExcel(workbook);
		} else {
			return importTeacherExcel(workbook);
		}
	}

	public static File exportStudentExcel(ArrayList<Student> list, String fileName) {
		// 最终文件
		File file = new File(CREATE_STUDENT_PATH + "/" + fileName + ".xls");
		// 生成工作薄
		Workbook workbook = new HSSFWorkbook();
		// 生成工作表
		Sheet sheet = workbook.createSheet();
		Row row = sheet.createRow(0);
		// 生成第一行内容
		for (int i = 0; i < STUDENT_VALIDATE_ROW.length; i++) {
			row.createCell(i).setCellValue(STUDENT_VALIDATE_ROW[i]);
		}
		// 生成表格数据
		if (list != null || list.size() > 0) {
			for (int i = 1; i <= list.size(); i++) {
				Row dataRow = sheet.createRow(i);
				dataRow.createCell(0).setCellValue(list.get(i - 1).getStuId());
				dataRow.createCell(1).setCellValue(list.get(i - 1).getStuName());
				dataRow.createCell(2).setCellValue(list.get(i - 1).getClassName());
				dataRow.createCell(3).setCellValue(list.get(i - 1).getPassword());
				dataRow.createCell(4).setCellValue(list.get(i - 1).getEmail());
				dataRow.createCell(5).setCellValue(list.get(i - 1).getWorksTea());
				dataRow.createCell(6).setCellValue(list.get(i - 1).getFieldworkTea());
			}
		}
		try {
			// 如果文件存在
			if (file.exists()) {
				file.delete();
			}
			// 生成文件
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			workbook.write(fos);
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return null;
		}
		return file;
	}

	private static ArrayList<Student> importStudentExcel(Workbook workbook) {
		ArrayList<Student> students = new ArrayList<Student>();
		System.out.println("进入了吗111");
		// 遍历每一个工作表
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			// 获得第i个工作表
			Sheet sheet = workbook.getSheetAt(i);
			// 循环从第二行开始的每一行
			Row validateRow = sheet.getRow(1);
			// 验证表格模板是否正确
			if (validateRow.getLastCellNum() < 7) {
				System.out.println(">????");
				return null;
			}
			System.out.println(1);
			for (int j = 0; j < validateRow.getLastCellNum(); j++) {
				System.out.println("进图");
				System.out.println(validateRow.getCell(j).getStringCellValue());
				if (validateRow.getCell(j).getStringCellValue() == null
						|| !validateRow.getCell(j).getStringCellValue().equals(STUDENT_VALIDATE_ROW[j])) {
					System.out.println("吃法");
					return null;
				}
			}
			System.out.println(2);
			for (int j = 2; j <= sheet.getLastRowNum(); j++) {// getLastRowNum()获得最大的行数
				// 获取表格行
				Row row = sheet.getRow(j);
				// 保存每行数据的Student对象
				Student student = new Student();
				System.out.println(3);
				if (row != null) {
					System.out.println("23");
					if (row.getCell(3) != null)
						row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
					System.out.println(24);
					row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
					System.out.println(11);
					if (row.getCell(0) != null)
						student.setStuId(row.getCell(0).getStringCellValue().trim());
					if (row.getCell(1) != null)
						student.setStuName(row.getCell(1).getStringCellValue().trim());
					if (row.getCell(2) != null)
						student.setClassName(row.getCell(2).getStringCellValue().trim());
					if (row.getCell(3) != null)
						student.setPassword(row.getCell(3).getStringCellValue().trim());
					System.out.println(12);
					if (row.getCell(4) != null)
						student.setEmail(row.getCell(4).getStringCellValue().trim());
					if (row.getCell(5) != null)
						student.setWorksTea(row.getCell(5).getStringCellValue().trim());
					if (row.getCell(6) != null)
						student.setFieldworkTea(row.getCell(6).getStringCellValue().trim());
				}
				System.out.println("教您图");
				System.out.println(student.getStuId());
				students.add(student);
			}
		}
		System.out.println("dds" + students.size());
		return students;
	}

	private static ArrayList<Teacher> importTeacherExcel(Workbook workbook) {
		ArrayList<Teacher> teachers = new ArrayList<Teacher>();
		// 遍历每一个工作表
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			// 获得第i个工作表
			Sheet sheet = workbook.getSheetAt(i);
			// 循环从第二行开始的每一行
			Row validateRow = sheet.getRow(1);
			// 验证表格模板是否正确
			if (validateRow.getLastCellNum() < 4) {// 验证表格列数是否正确
				System.out.println("错误");
				return null;
			}
			for (int j = 0; j < validateRow.getLastCellNum(); j++) {// 验证列名是否正确
				if (validateRow.getCell(j).getStringCellValue() == null
						|| !validateRow.getCell(j).getStringCellValue().equals(TEACHER_VALIDATE_ROW[j])) {
					System.out.println("cuo");
					return null;
				}
			}
			for (int j = 2; j <= sheet.getLastRowNum(); j++) {// getLastRowNum()获得最大的行数
				// 获取表格行
				Row row = sheet.getRow(j);
				// 保存每行数据的Teacher对象
				Teacher teacher = new Teacher();
				if (row != null) {
					row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
					row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
					if (row.getCell(0) != null)
						teacher.setTeaAccount(row.getCell(0).getStringCellValue().trim());
					if (row.getCell(1) != null)
						teacher.setTeaName(row.getCell(1).getStringCellValue().trim());
					if (row.getCell(2) != null)
						teacher.setEmail(row.getCell(2).getStringCellValue().trim());
					if (row.getCell(3) != null)
						teacher.setPassword(row.getCell(3).getStringCellValue().trim());
				}
				teachers.add(teacher);
			}
		}
		System.out.println(teachers.size());
		return teachers;
	}
	
	
}
