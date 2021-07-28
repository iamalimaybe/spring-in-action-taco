package tacos.data;

import org.springframework.data.repository.CrudRepository;

import tacos.domain.Ingredient;

public interface JpaIngredientRepository extends CrudRepository<Ingredient, java.lang.String> {

}
