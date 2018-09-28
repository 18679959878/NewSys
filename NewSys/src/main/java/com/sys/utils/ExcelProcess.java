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
	// ѧ��������·��
	public static final String CREATE_STUDENT_PATH = "F:/download/student";
	// ѧ�����ȡ·��
	public static final String READ_STUDENT_PATH = "F:/upLoadExcel/student";
	// ������
	public static final String EXCEL_TYPE_STUDENT = "student_e";// ѧ����
	public static final String EXCEL_TYPE_TEACHER = "teacher_e";// ��ʦ��
	// ��֤������
	public static final String[] STUDENT_VALIDATE_ROW = { "ѧ��", "����", "�༶��", "ϵͳ����*", "����*", "��ҵ���ָ����ʦ*", "ʵϰָ����ʦ*",
			"רҵ" };// ѧ����\
	public static final String[] TEACHER_VALIDATE_ROW = { "��ʦ�˺�", "��ʦ����", "����*", "����" };// ��ʦ��

	public static ArrayList<? extends Object> importExcel(File file, String type)
			throws FileNotFoundException, IOException {
		// ��ȡ�ļ���׺��
		String fileName = file.getName();
		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
		// ������
		Workbook workbook;
		// ֻ����xls�� xlsx���ָ�ʽ�ı��
		if (suffix.equals("xls")) {
			// ��ȡxls��ʽ������
			workbook = new HSSFWorkbook(new FileInputStream(file));
		} else if (suffix.equals("xlsx")) {
			// ��ȡ������
			workbook = new XSSFWorkbook(new FileInputStream(file));
			// ��ȡ������
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
		// �����ļ�
		File file = new File(CREATE_STUDENT_PATH + "/" + fileName + ".xls");
		// ���ɹ�����
		Workbook workbook = new HSSFWorkbook();
		// ���ɹ�����
		Sheet sheet = workbook.createSheet();
		Row row = sheet.createRow(0);
		// ���ɵ�һ������
		for (int i = 0; i < STUDENT_VALIDATE_ROW.length; i++) {
			row.createCell(i).setCellValue(STUDENT_VALIDATE_ROW[i]);
		}
		// ���ɱ������
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
			// ����ļ�����
			if (file.exists()) {
				file.delete();
			}
			// �����ļ�
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
		System.out.println("��������111");
		// ����ÿһ��������
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			// ��õ�i��������
			Sheet sheet = workbook.getSheetAt(i);
			// ѭ���ӵڶ��п�ʼ��ÿһ��
			Row validateRow = sheet.getRow(1);
			// ��֤���ģ���Ƿ���ȷ
			if (validateRow.getLastCellNum() < 7) {
				System.out.println(">????");
				return null;
			}
			System.out.println(1);
			for (int j = 0; j < validateRow.getLastCellNum(); j++) {
				System.out.println("��ͼ");
				System.out.println(validateRow.getCell(j).getStringCellValue());
				if (validateRow.getCell(j).getStringCellValue() == null
						|| !validateRow.getCell(j).getStringCellValue().equals(STUDENT_VALIDATE_ROW[j])) {
					System.out.println("�Է�");
					return null;
				}
			}
			System.out.println(2);
			for (int j = 2; j <= sheet.getLastRowNum(); j++) {// getLastRowNum()�����������
				// ��ȡ�����
				Row row = sheet.getRow(j);
				// ����ÿ�����ݵ�Student����
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
				System.out.println("����ͼ");
				System.out.println(student.getStuId());
				students.add(student);
			}
		}
		System.out.println("dds" + students.size());
		return students;
	}

	private static ArrayList<Teacher> importTeacherExcel(Workbook workbook) {
		ArrayList<Teacher> teachers = new ArrayList<Teacher>();
		// ����ÿһ��������
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			// ��õ�i��������
			Sheet sheet = workbook.getSheetAt(i);
			// ѭ���ӵڶ��п�ʼ��ÿһ��
			Row validateRow = sheet.getRow(1);
			// ��֤���ģ���Ƿ���ȷ
			if (validateRow.getLastCellNum() < 4) {// ��֤��������Ƿ���ȷ
				System.out.println("����");
				return null;
			}
			for (int j = 0; j < validateRow.getLastCellNum(); j++) {// ��֤�����Ƿ���ȷ
				if (validateRow.getCell(j).getStringCellValue() == null
						|| !validateRow.getCell(j).getStringCellValue().equals(TEACHER_VALIDATE_ROW[j])) {
					System.out.println("cuo");
					return null;
				}
			}
			for (int j = 2; j <= sheet.getLastRowNum(); j++) {// getLastRowNum()�����������
				// ��ȡ�����
				Row row = sheet.getRow(j);
				// ����ÿ�����ݵ�Teacher����
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
