package edu.poly.shop.controller.admin;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

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

import edu.poly.shop.domain.Account;
import edu.poly.shop.model.AccountDto;
import edu.poly.shop.service.AccountService;


@Controller
@RequestMapping("/admin/accounts")
public class AccountController {
	@Autowired
	AccountService accountService;

	@GetMapping("add")
	public String add(Model model) {
		model.addAttribute("account", new AccountDto());
		return "admin/accounts/addOrEdit";
	}

	@GetMapping("edit/{userId}")
	public ModelAndView edit(ModelMap model, @PathVariable("userId") Long userId) {
		Optional<Account> opt = accountService.findById(userId);
		AccountDto dto = new AccountDto();
		if (opt.isPresent()) {
			Account entity = opt.get();
			BeanUtils.copyProperties(entity, dto);
			dto.setIsEdit(true);
			model.addAttribute("account", dto);
			return new ModelAndView("admin/accounts/addOrEdit", model);
		}
		model.addAttribute("mesage", "Thể loại không tồn tại");
		return new ModelAndView("forward:/admin/accounts", model);

	}

	@GetMapping("delete/{userId}")
	public ModelAndView delete(ModelMap model, @PathVariable("userId") Long userId) {

		accountService.deleteById(userId);
		model.addAttribute("message", "Xóa thành công");
		return new ModelAndView("redirect:/admin/accounts", model);
	}


	@PostMapping("saveOrUpdate")
	public ModelAndView saveOrUpdate(ModelMap model, @Valid @ModelAttribute("account") AccountDto dto,
			BindingResult result) {
		if (result.hasErrors()) {
			return new ModelAndView("admin/accounts/addOrEdit");
		}
		Account entity = new Account();
		BeanUtils.copyProperties(dto, entity);
		accountService.save(entity);
		model.addAttribute("message", "Thể loại được thêm thành công !!!");
		return new ModelAndView("forward:/admin/accounts", model);
	}

	@RequestMapping("")
	public String list(ModelMap model) {
		List<Account> list = accountService.findAll();
		model.addAttribute("accounts", list);
		return "admin/accounts/list";
	}


}
