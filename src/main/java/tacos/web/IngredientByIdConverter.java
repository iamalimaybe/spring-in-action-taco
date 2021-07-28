package tacos.web;

import java.util.Optional;

//import java.util.HashMap;
//import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

//import tacos.data.IngredientRepository;
import tacos.data.JpaIngredientRepository;
import tacos.domain.Ingredient;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {

	private JpaIngredientRepository ingredientRepository;
	
	@Autowired
	public IngredientByIdConverter(JpaIngredientRepository ingredientRepository) {
		this.ingredientRepository = ingredientRepository;
	}
	
	/* WHEN NO JDBC (static data approach)
	 * private Map<String, Ingredient> ingredientMap = new HashMap<>();
	 * 
	 * public IngredientByIdConverter() { ingredientMap.put("FLTO", new
	 * Ingredient("FLTO", "Flour Tortilla", Type.WRAP)); ingredientMap.put("COTO",
	 * new Ingredient("COTO", "Corn Tortilla", Type.WRAP));
	 * ingredientMap.put("GRBF", new Ingredient("GRBF", "Ground Beef",
	 * Type.PROTEIN)); ingredientMap.put("CARN", new Ingredient("CARN", "Carnitas",
	 * Type.PROTEIN)); ingredientMap.put("TMTO", new Ingredient("TMTO",
	 * "Diced Tomatoes", Type.VEGGIES)); ingredientMap.put("LETC", new
	 * Ingredient("LETC", "Lettuce", Type.VEGGIES)); ingredientMap.put("CHED", new
	 * Ingredient("CHED", "Cheddar", Type.CHEESE)); ingredientMap.put("JACK", new
	 * Ingredient("JACK", "Monterrey Jack", Type.CHEESE)); ingredientMap.put("SLSA",
	 * new Ingredient("SLSA", "Salsa", Type.SAUCE)); ingredientMap.put("SRCR", new
	 * Ingredient("SRCR", "Sour Cream", Type.SAUCE)); }
	 */

	public Ingredient convert(String id) {
	    Optional<Ingredient> optionalIngredient = ingredientRepository.findById(id);
		return optionalIngredient.isPresent() ?
			   optionalIngredient.get() : null;
	  }

}
