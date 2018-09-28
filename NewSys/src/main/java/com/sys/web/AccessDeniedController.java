package com.sys.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sys.dto.NullData;
import com.sys.dto.Result;
@Controller
public class AccessDeniedController {

	@RequestMapping(value = "/lje", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Result<NullData> land() {
		return new Result<NullData>("��Ǹ��δ��½��û��Ȩ�޽��в���");
	}
	
	@RequestMapping(value = "/dje", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Result<NullData> date() {
		return new Result<NullData>("��Ǹ����ǰ�׶λ�δ���ţ�û��Ȩ�޽��в���");
	}
}
