package com.sys.dao;

import com.sys.entity.Comment;
import com.sys.entity.Report;

public interface CommentMapper {
	public Comment select(String stuId);

	public void saveOrUpdate(Comment comment);
}
