package edu.poly.shop.controller.home;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.poly.shop.domain.Account;
import edu.poly.shop.domain.Category;
import edu.poly.shop.domain.Product;
import edu.poly.shop.model.CategoryDto;
import edu.poly.shop.service.CategoryService;
import edu.poly.shop.service.ProductService;
import edu.poly.shop.service.StorageService;


@Controller
public class HomeController {

	@Autowired
	private ProductService productService;
	@Autowired
	StorageService storageService;
	@Autowired
	CategoryService categoryService;
	
	
	@ModelAttribute("categories")
	public List<CategoryDto> getCategories() {
		return categoryService.findAll().stream().map(item -> {
			CategoryDto dto = new CategoryDto();
			BeanUtils.copyProperties(item, dto);
			return dto;
		}).collect(Collectors.toList());
	}
	
	
	
	@GetMapping("")
	public String add(Model model) {
		List<Product> list = productService.findAll();
		model.addAttribute("products", list);
		return "user/index";
	}
	
	


	@GetMapping("/user/site/login")
	public String doGetLogin(ModelMap model) {
		model.addAttribute("user", new Account());
		return "/user/site/login";
	}


	

	
	@GetMapping("user/site/registration")
	public String registration() {
		return "/user/site/registration";
	}
}
