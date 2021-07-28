package tacos.data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import tacos.domain.Taco;
import tacos.domain.TacoOrder;

@Repository
public class JdbcOrderRepository implements OrderRepository  {
	
	private SimpleJdbcInsert orderInserter;
	private SimpleJdbcInsert orderTacoInserter;
	private ObjectMapper objectMapper;
	
	public JdbcOrderRepository(JdbcTemplate jdbcTemplate) {
		this.orderInserter = new SimpleJdbcInsert(jdbcTemplate)
				.withTableName("Taco_Order")
				.usingGeneratedKeyColumns("id");
		this.orderTacoInserter = new SimpleJdbcInsert(jdbcTemplate)
				.withTableName("Taco_Order_Tacos");
		this.objectMapper = new ObjectMapper();
	}

	@Override
	public TacoOrder save(TacoOrder order) {
		order.setPlacedAt(new Date());
		long orderId = saveOrderDetails(order);
		order.setId(orderId);
		for (Taco taco : order.getTacos()) {
			saveTacoToOrder(taco, orderId);
		}
		
		return order;
	}
	
	private long saveOrderDetails(TacoOrder order) {
		@SuppressWarnings("unchecked")
		Map<String, Object> values = objectMapper.convertValue(order, Map.class);
		values.put("placedAt", order.getPlacedAt());
		
		long orderId = orderInserter.executeAndReturnKey(values).longValue();
		return orderId;
	}
	
	private void saveTacoToOrder(Taco taco, long orderId) {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("taco", taco.getId());
		values.put("tacoOrder", orderId);
		orderTacoInserter.execute(values);
	}
	
}
