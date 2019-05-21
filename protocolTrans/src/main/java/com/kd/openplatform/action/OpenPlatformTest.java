package com.kd.openplatform.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/test")
@Controller
public class OpenPlatformTest {

	private static final Log log = LogFactory.getLog(OpenPlatformTest.class);


	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String baseAction(HttpServletRequest request) {
		log.info("=======================");
		String argsStr = request.getParameter("args");
//		log.info("用户传过来的参数--OpenPlatformBaseController --argsStr==== " + argsStr);

		return "success";
	}
}
