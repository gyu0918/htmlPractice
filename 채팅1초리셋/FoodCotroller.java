package www.silver.hom;


import java.time.LocalTime;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import  com.fasterxml.jackson.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
public class FoodController {
	
//	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	private  FoodService foodService = new FoodService();
	
	@RequestMapping(value = "/food", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
//		logger.info("Welcome food! The client locale is {}.", locale);
		
		
		return "food";
	}
	
	@RequestMapping(value = "search", method = RequestMethod.POST)
	public String search( HttpServletRequest request) {
		
		System.out.println(foodService.searchFood(request.getParameter("wordtext")));
		request.setAttribute("food_list", foodService.searchFood(request.getParameter("wordtext")));
	
		return "food";
	}
	
	
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createFood( HttpServletRequest request) {
		
		String[] createText = request.getParameterValues("createText");
		foodService.createFood(createText);
	
		return "food";
	}
	
	@RequestMapping(value = "/allMenu", produces = "application/json")
	@ResponseBody
	public List<FoodDTO> getAllMenus() {
	    return foodService.ReadAll();
	}

	
//	@ResponseBody
	@RequestMapping(value = "allMenu", method = RequestMethod.GET, produces = "text/html; charset=UTF-8")
//	public String allSearch(HttpServletRequest request) {
//	    List<FoodDTO> list = foodService.ReadAll();
//
//	    StringBuilder sb = new StringBuilder();
//	    for (FoodDTO food : list) {
//	        sb.append("메뉴 : ").append(food.getName()).append("<br>");
//	        sb.append("가격 : ").append(food.getPrice()).append("<br>");
//	        sb.append("위치 : ").append(food.getLocation()).append("<br>");
//	        sb.append("원산지 : ").append(food.getContry()).append("<hr>");
//	    }
//
//	    return sb.toString(); // HTML 조각을 String으로 반환
//	}
	
	@ResponseBody
	@RequestMapping(value = "allMenu", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public List<FoodDTO> allSearch(HttpServletRequest request) {
	   List<FoodDTO> aa = foodService.ReadAll();

	   ObjectMapper objectMapper = new ObjectMapper();
	   String json;
	   try {
		json = objectMapper.writeValueAsString(foodService.ReadAll());
	} catch (JsonProcessingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   
	  
	return json;  // Jackson이 자동으로 JSON으로 변환
	  
	}
			

	
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String deleteFood(HttpServletRequest request) {
		
		String deleteMenu = request.getParameter("deleteText");
		foodService.deleteFood(deleteMenu);
		
		return "food";
	}
	
	@RequestMapping(value = "update", method = RequestMethod.GET)
	public String word(HttpServletRequest request) {
		
		String[] updateText = request.getParameterValues("updateText");
		foodService.updateFood(updateText);
		
		return "food";
	}
	
	//-----------------------------------------------------------------------------------------------------------------------
	
	
//	static List<String> tlist = new ArrayList<String>();
//
//	
//	@RequestMapping(value = "delete", method = RequestMethod.POST)
//	public String alltrain( HttpServletRequest request) {
//	
//	//초기설정
//		int a = 1;
//		int b = 0;
//		for (int i = 0; i < temp.length; i++) {
//			request.setAttribute(temp[i], tdata[a][b]);
//			if (b == 4) {
//				b = 0;
//				a++;
//			}else {
//				b++;
//			}
//	
//		}
//	
//		return "home";
//	}
//	
//	static HashMap<String, String[]> trainMap = new HashMap<String,String[]>();
//	static String[] trainInfo1 = {"새마을","0910","1010","a1","b1","c1"};
//	static String[] trainInfo2 = {"무궁화","0850","1012","a2","b2","c2"};
//	static String[] trainInfo3 = {"새마을","1000","1520","a3","b3","c3"};
//	static String[] trainInfo4 = {"무궁화","1200","1350","a4","b4","c4"};
//	static String[] trainInfo5 = {"새마을","0945","1300","a5","b5","c5"};
//	
//	@RequestMapping(value = "trainword", method = RequestMethod.POST)
//	public String search( HttpServletRequest request) {
//	
//		//초기설정
//		trainMap.put(tdata[0][0], trainInfo1);
//		trainMap.put(tdata[0][1], trainInfo2);
//		trainMap.put(tdata[0][2], trainInfo3);
//		trainMap.put(tdata[0][3], trainInfo4);
//		trainMap.put(tdata[0][4], trainInfo5);
//		for (int i = 0; i < tdata[0].length; i++) {
//			tlist.add(tdata[0][i]);
//		}
//		
//		String word = request.getParameter("wordtext");
//		
////		System.out.println(word);
//		
//		if (tlist.contains(word)) {
//			for (int i = 0; i < trainMap.get(word).length - 3; i++) {
//				request.setAttribute(trainMap.get(word)[i + 3], trainMap.get(word)[i]);
//			}
//		}
//		
//		
//		return "home";
//	}
//	
//
//	static List<String> tlist2 = new ArrayList<String>();
//	@RequestMapping(value = "search", method = RequestMethod.POST)
//	public String checkBox( HttpServletRequest request) {
//	
//		//초기설정
//		trainMap.put(tdata[0][0], trainInfo1);
//		trainMap.put(tdata[0][1], trainInfo2);
//		trainMap.put(tdata[0][2], trainInfo3);
//		trainMap.put(tdata[0][3], trainInfo4);
//		trainMap.put(tdata[0][4], trainInfo5);
//		for (int i = 0; i < tdata[0].length; i++) {
//			tlist2.add(tdata[0][i]);
//		}
//		
//		
//		//내가 한부분---------------------------------------------------------------
////		String[] index = {"1","2","3","4","5"};
////		List<String> checkList = new ArrayList<String>();
////		
////		for (int i = 0; i < index.length; i++) {
////			checkList.add(request.getParameter(index[i]));
////		}
//		
////		System.out.println(checkList);
//////		System.out.println(checkList.isEmpty());
////		
////		if (!checkList.isEmpty()) {
////			for (int i = 0; i < checkList.size() ; i++) {
////				if (checkList.get(i) != null) {
////					for (int j = 0; j < 3; j++) {
////						request.setAttribute(trainMap.get(checkList.get(i))[j + 3], trainMap.get(checkList.get(i))[j]);
////					}
////				}
////			}
////				
////		}
//		
//		//티쳐의 손길
//		String[] ab = request.getParameterValues("1");
//	
//		if (ab != null) {
//			for (int i = 0; i < ab.length; i++) {
//				for (int j = 0; j < 3; j++) {
//					request.setAttribute(trainMap.get(ab[i])[j + 3], trainMap.get(ab[i])[j]);
//				}
//			}
//		}
//		
//		return "home";
//	}
//	
	
	
	
	
	
}

