package tacos.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import tacos.data.JpaIngredientRepository;
import tacos.data.JpaTacoRepository;
import tacos.domain.Ingredient;
import tacos.domain.Taco;
import tacos.domain.TacoOrder;
import tacos.domain.Ingredient.Type;

@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {

	private final JpaIngredientRepository ingredientRepository;
	private JpaTacoRepository tacoRepository;
	
	@Autowired
	public DesignTacoController(JpaIngredientRepository ingredientRepository, JpaTacoRepository tacoRepository) {
		this.ingredientRepository = ingredientRepository;
		this.tacoRepository = tacoRepository;
	}
	
	@ModelAttribute(name = "taco")
	public Taco taco() {
		return new Taco();
	}
	
	@ModelAttribute(name = "tacoOrder")
	public TacoOrder tacoOrder() {
		return new TacoOrder();
	}
	
	@ModelAttribute
	public void addIngredientsToTaco(Model model) {
		List<Ingredient> ingredients = new ArrayList<>();
		ingredientRepository.findAll().forEach(i -> ingredients.add(i));
		
		Type[] types = Ingredient.Type.values();
		for (Type type: types) {
			model.addAttribute(type.toString().toLowerCase(),
					filterByType(ingredients, type));
		}
	}
	
	
	@GetMapping
	public String showDesignForm() {
		return "design";
	}
	
	
	private List<Ingredient> filterByType(
			List<Ingredient> ingredients, Type type) {
		return ingredients
				.stream()
				.filter(x -> x.getType().equals(type))
				.collect(Collectors.toList());
	}
	
	@PostMapping
	public String processTaco(@Valid Taco design, Errors result, @ModelAttribute TacoOrder tacoOrder) {
		
		if (result.hasErrors()) {
			return "design";
		}
		
		Taco saved = tacoRepository.save(design);
		tacoOrder.addTaco(saved);
		
		return "redirect:orders/current";
	}
	
}
