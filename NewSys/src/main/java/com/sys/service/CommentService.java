package com.sys.service;

import com.sys.dto.NullData;
import com.sys.dto.Result;
import com.sys.entity.Comment;

public interface CommentService {
	public Result<NullData> saveOrUpdate(Comment comment);

	public Result<Comment> select(String stuId);
}
