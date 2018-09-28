package com.sys.service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sys.dao.CommentMapper;
import com.sys.dto.NullData;
import com.sys.dto.Result;
import com.sys.entity.Comment;
import com.sys.entity.Student;

@Service
public class CommentServiceImp implements CommentService {
	@Autowired
	private CommentMapper commentMapper;
	@Autowired(required = false)
	private HttpSession session;

	@Override
	@Transactional
	public Result<NullData> saveOrUpdate(Comment comment) {
		try {
			commentMapper.saveOrUpdate(comment);
			return new Result<NullData>(new NullData());
		} catch (Exception e) {
			return new Result<NullData>("ÃÌº” ß∞‹");
		}
	}

	@Override
	public Result<Comment> select(String stuId) {
		try {
			if (stuId == null || stuId.equals("")) {
				stuId = ((Student) session.getAttribute("student")).getStuId();
			}
			Comment comment = commentMapper.select(stuId);
			return new Result<Comment>(comment);
		} catch (Exception e) {
			return new Result<Comment>("≤È—Ø ß∞‹");
		}
	}

}
