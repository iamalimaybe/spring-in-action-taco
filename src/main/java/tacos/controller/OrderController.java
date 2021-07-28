package tacos.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import tacos.data.JpaOrderRepository;
import tacos.data.OrderRepository;
import tacos.domain.TacoOrder;

@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {
	
	private JpaOrderRepository orderRepository;
	
	public OrderController(JpaOrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}
	
	@GetMapping("/current")
	public String orderForm() {
		return "orderForm";
	}
	
	@PostMapping
	public String processOrder(@Valid TacoOrder tacoOrder, Errors errors, SessionStatus sessionStatus) {
		if (errors.hasErrors()) {
			return "orderForm";
		}

		this.orderRepository.save(tacoOrder);
		sessionStatus.setComplete();

		return "redirect:/";
	}
}
