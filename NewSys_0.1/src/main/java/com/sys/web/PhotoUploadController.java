package com.sys.web;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sys.dto.AdPictureURL;
import com.sys.entity.Student;
import com.sys.utils.Contants;

@Controller
@RequestMapping("/photo")
public class PhotoUploadController {
	@Autowired
	private HttpSession session;

	@CrossOrigin
	@ResponseBody
	@RequestMapping("/wangEditorUpload")
	public AdPictureURL upload(@RequestParam("photo") List<MultipartFile> list, String stageName) {
		Student student = (Student) session.getAttribute("student");
		Integer errno = 0;
		String message = "";
		List<String> urls = new ArrayList<>();
		AdPictureURL returnAd = new AdPictureURL();

		if (list.size() == 0) {
			errno = 1;
		}
		for (MultipartFile file : list) {
			if (file != null) {
				String fileName = UUID.randomUUID().toString();
				File stu_file = new File(Contants.ROOT_PATH + "/" + student.getStuId());
				if (!stu_file.exists()) {
					stu_file.mkdir();
				}
				File newFile = new File(Contants.ROOT_PATH + "/" + student.getStuId() + "/" + fileName + ".jpg");

				try {
					file.transferTo(newFile);
				} catch (IllegalStateException e) {
					errno = 1;
					e.printStackTrace();
				} catch (IOException e) {
					errno = 1;
					e.printStackTrace();
				}
				// String databaseUrl = "http://localhost:8080/photo/student/" +
				// student.getStuId() + "/" + fileName
				// + ".jpg";
				String databaseUrl = "https://www.gy1217.club/photo/student/" + student.getStuId() + "/" + fileName
						+ ".jpg";
				urls.add(databaseUrl);

			}
		}
		returnAd.setData(urls);
		returnAd.setErrno(errno);
		returnAd.setMessage(message);
		return returnAd;
	}

}
