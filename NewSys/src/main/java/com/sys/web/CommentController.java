package com.sys.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sys.dto.NullData;
import com.sys.dto.Result;
import com.sys.entity.Comment;
import com.sys.service.CommentService;

@Controller
@RequestMapping("/comment")
public class CommentController {
	@Autowired
	private CommentService commentService;
	
	
	@RequestMapping(value = "/sop", method = RequestMethod.POST, produces = { "application/json; charset=utf-8" })
	@ResponseBody
	public Result<NullData> saveOrUpdate(String stuId, String stuName, String className, String commentText) {
		Comment comment = commentService.select(stuId).getData();
		comment.setStuId(stuId);
		comment.setStuName(stuName);
		comment.setClassName(className);
		comment.setComment(commentText);
		return commentService.saveOrUpdate(comment);
	}
	
	@RequestMapping(value = "/sel", method = RequestMethod.GET, produces = { "application/json; charset=utf-8" })
	@ResponseBody
	public Result<Comment> select(String stuId) {
		return commentService.select(stuId);
	}
}
