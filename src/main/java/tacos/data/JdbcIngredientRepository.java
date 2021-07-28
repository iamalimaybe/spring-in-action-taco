package tacos.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import tacos.domain.Ingredient;

@Repository
public class JdbcIngredientRepository implements IngredientRepository {

	private JdbcTemplate jdbcTemplate;
	
	public JdbcIngredientRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public Iterable<Ingredient> findAll() {
		return jdbcTemplate.query(
					"select id, name, type from Ingredient", 
					this::mapRowToIngredient
				);
	}

	@Override
	public Ingredient findById(String id) {
		return jdbcTemplate.queryForObject(
					"select id, name, type from Ingredient where id = ?", 
					this::mapRowToIngredient, id
				);
	}

	@Override
	public Ingredient save(Ingredient ingredient) {
		jdbcTemplate.update(
					"insert into Ingredient (id, name, type) values (?, ?, ?)",
					ingredient.getId(),
					ingredient.getName(),
					ingredient.getType().toString()
				);
		return ingredient;
	}
	
	private Ingredient mapRowToIngredient(ResultSet row, int rowNum) throws SQLException {
		return new Ingredient(
					row.getString("id"),
					row.getString("name"),
					Ingredient.Type.valueOf(row.getString("type"))
				);
	}
	
}
