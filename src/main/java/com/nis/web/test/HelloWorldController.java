package com.nis.web.test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;








import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.nis.domain.SysFunctionMenu;
import com.nis.web.controller.BaseController;
import com.nis.web.service.UserService;


@SessionAttributes(value="userObject")
@RequestMapping("/helloworld")
@Controller
public class HelloWorldController extends BaseController{
	
	
	@RequestMapping("/hello")
	public String hello(){
		return "success";
	}
	
	@RequestMapping("/testJson")
	@ResponseBody
	public List<SysFunctionMenu> testJson(){
		return userService.getTopTreeListByUserId(5l);
	}
	
	/**
	 * requestMapping 参数有value(url),method(request method),params,headers
	 * @return
	 */
	@RequestMapping(value="/testMethod",method=RequestMethod.POST)
	public String testMethod(){
		return "success";
	}
	
	/**
	 * params 参数为数组，包含username属性并且age参数的值不为10
	 * @return
	 */
	@RequestMapping(value="/testParams",params={"username","age!=10"},headers={"Accept-Language=en-US"})
	public String testParams() {
		return "success";
	}
	
	/**
	 * 使用通配符映射请求参数
	 * ?一个字符 
	 * *多个字符
	 * **多级目录
	 * @return
	 */
	@RequestMapping("/testAntPath/*/test")
	public String testAntPath() {
		return "success";
	}
	
	@RequestMapping("/testPathVariable/{id}")
	public String testPathVariable(@PathVariable("id") int id){
		System.out.println(id);
		return "success";
	}
	
	/**
	 * @RequestParam 参数value代表url参数名，required：是否必须传入，defaultValue,为空时默认值为。
	 * 
	 * @param username
	 * @param age
	 * @return
	 */
	@RequestMapping(value="/testRequestParam")
	public String testRequestParam(@RequestParam(value="username",required=true) String username,@RequestParam(required=false,defaultValue="0") int age){
		System.out.println(username+":"+age);
		return "success";
	}
	
	
	
	
	
	/**
	 * 通过modelandview 传递数据与视图
	 * @param map
	 * @return
	 */
	@RequestMapping("/testModelAndView")
	public ModelAndView testModelAndView(){
		String viewName = "success";
		ModelAndView modelAndView = new ModelAndView(viewName);
		
		modelAndView.addObject("time", new Date());
		
		return modelAndView;
	}
	
	/**
	 * 通过自动注解map对象传递数据
	 * @param map
	 * @return
	 */
	@RequestMapping("/testMap")
	public String  testMap(Map<String,Object> map){
		map.put("names", Arrays.asList("A","B","C"));
		return "success";
	}
	
	
	
	

}
