package lk.ijse.dep.repositories.DAO.custom;

import lk.ijse.dep.entities.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailsDAO extends JpaRepository<OrderDetails,String> {
     public OrderDetails getTopOrderByOrderByOrderidDesc();

}
