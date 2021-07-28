package tacos.data;

import org.springframework.data.repository.CrudRepository;

import tacos.domain.TacoOrder;

public interface JpaOrderRepository extends CrudRepository<TacoOrder, Long> {

}
