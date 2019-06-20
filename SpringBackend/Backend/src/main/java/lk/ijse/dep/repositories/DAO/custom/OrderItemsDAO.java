package lk.ijse.dep.repositories.DAO.custom;

import lk.ijse.dep.entities.OrderItems;
import lk.ijse.dep.entities.OrderItemsPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemsDAO extends JpaRepository<OrderItems,OrderItemsPK> {
    @Query(value = "select * from orderitems where orderid=:#{#orderid}", nativeQuery = true)
    public List<OrderItems> findOrderItems(@Param("orderid") String itemid);

}
