package lk.ijse.dep.repositories.DAO.custom;

import lk.ijse.dep.entities.Customer;
import lk.ijse.dep.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemDao extends JpaRepository<Item,String> {
    @Query("select o.qty from Item o where o.code=:#{#code}")
    public int getQtyByCode(@Param("code") String itemcode);
    public Item findItemByCode(String code);
}
