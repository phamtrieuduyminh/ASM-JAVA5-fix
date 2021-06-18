package edu.poly.shop.controller.admin;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.poly.shop.domain.Customer;
import edu.poly.shop.model.CustomerDto;
import edu.poly.shop.service.CustomerService;

@Controller
@RequestMapping("/admin/customers")
public class CustomerController {
	@Autowired
	CustomerService customerService;

	@GetMapping("add")
	public String add(Model model) {
		model.addAttribute("customer", new CustomerDto());
		return "admin/customers/addOrEdit";
	}

	@GetMapping("edit/{customerId}")
	public ModelAndView edit(ModelMap model, @PathVariable("customerId") Long customerId) {
		Optional<Customer> opt = customerService.findById(customerId);
		CustomerDto dto = new CustomerDto();
		if (opt.isPresent()) {
			Customer entity = opt.get();
			BeanUtils.copyProperties(entity, dto);
			dto.setIsEdit(true);
			model.addAttribute("customer", dto);
			return new ModelAndView("admin/customers/addOrEdit", model);
		}
		model.addAttribute("mesage", "Thể loại không tồn tại");
		return new ModelAndView("forward:/admin/customers", model);

	}

	@GetMapping("delete/{customerId}")
	public ModelAndView delete(ModelMap model, @PathVariable("customerId") Long customerId) {

		customerService.deleteById(customerId);
		model.addAttribute("message", "Xóa thành công");
		return new ModelAndView("redirect:/admin/customers", model);
	}

	@PostMapping("saveOrUpdate")
	public ModelAndView saveOrUpdate(ModelMap model, @Valid @ModelAttribute("customer") CustomerDto dto,
			BindingResult result) {
		if (result.hasErrors()) {
			return new ModelAndView("admin/customers/addOrEdit");
		}
		Customer entity = new Customer();
		BeanUtils.copyProperties(dto, entity);
		customerService.save(entity);
		model.addAttribute("message", "Thể loại được thêm thành công !!!");
		return new ModelAndView("forward:/admin/customers", model);
	}

	@RequestMapping("")
	public String list(ModelMap model,@RequestParam(name = "name", required = false) String name,
			@RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {
		List<Customer> list = customerService.findAll();
		model.addAttribute("customers", list);

		if (StringUtils.hasText(name)) {
			list = customerService.findByNameContaining(name);
		} else {
			list = customerService.findAll();
		}

		model.addAttribute("customers", list);
		
		int curentPage = page.orElse(1);
		int pageSize = size.orElse(5);
		Pageable pageable = PageRequest.of(curentPage - 1, pageSize, Sort.by("name"));
		Page<Customer> resultPage = null;

		if (StringUtils.hasText(name)) {
			resultPage = customerService.findByNameContaining(name, pageable);
			model.addAttribute("name", name);
		} else {
			resultPage = customerService.findAll(pageable);
		}
		int totalPages = resultPage.getTotalPages();
		if (totalPages > 0) {
			int start = Math.max(1, curentPage - 2);
			int end = Math.min(curentPage + 2, totalPages);
			if(totalPages>5) {
				if(end == totalPages) start = end -5;
				else if (start == 1) 
					end = start + 5;	
			}
			List<Integer> pageNumbers = IntStream.range(start, end)
					.boxed()
					.collect(Collectors.toList());
			model.addAttribute("pageNumbers",pageNumbers);
			}
		model.addAttribute("customerPage", resultPage);
		return "admin/customers/list";
	}

	@GetMapping("search")
	public String search(ModelMap model, @RequestParam(name = "name", required = false) String name) {
		List<Customer> list = null;

		if (StringUtils.hasText(name)) {
			list = customerService.findByNameContaining(name);
		} else {
			list = customerService.findAll();
		}

		model.addAttribute("customers", list);

		return "admin/customers/search";
	}
	
	

	
	
	

	@GetMapping("searchpaginated")
	public String searchpaginated(ModelMap model,
			@RequestParam(name = "name", required = false) String name,
			@RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {
		int curentPage = page.orElse(1);
		int pageSize = size.orElse(5);
		Pageable pageable = PageRequest.of(curentPage - 1, pageSize, Sort.by("name"));
		Page<Customer> resultPage = null;

		if (StringUtils.hasText(name)) {
			resultPage = customerService.findByNameContaining(name, pageable);
			model.addAttribute("name", name);
		} else {
			resultPage = customerService.findAll(pageable);
		}
		int totalPages = resultPage.getTotalPages();
		if (totalPages > 0) {
			int start = Math.max(1, curentPage - 2);
			int end = Math.min(curentPage + 2, totalPages);
			if(totalPages>5) {
				if(end == totalPages) start = end -5;
				else if (start == 1) 
					end = start + 5;	
			}
			List<Integer> pageNumbers = IntStream.range(start, end)
					.boxed()
					.collect(Collectors.toList());
			model.addAttribute("pageNumbers",pageNumbers);
			}
		model.addAttribute("customerPage", resultPage);

		return "admin/customers/searchpaginated";
	}

}
