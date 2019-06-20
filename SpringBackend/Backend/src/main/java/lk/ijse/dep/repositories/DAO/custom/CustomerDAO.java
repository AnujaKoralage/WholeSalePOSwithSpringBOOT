package lk.ijse.dep.repositories.DAO.custom;

import lk.ijse.dep.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerDAO extends JpaRepository<Customer,String> {
    @Query("SELECT c FROM Customer c")
    public List<Customer> getById();

//    public Customer findCustomerByOrderByAddressDesc();
//
//    public List<Customer> findCustomersByNameLike(String name);
//
//    @Query("SELECT c FROM Customer c WHERE c.name LIKE ?1")
//    public List<Customer> allCustomers(String name);
//
//    public List<Customer> findCustomersByNameLikeAndAddressLikeOrderByAddressDesc(String name,String address);
//
//    @Query("SELECT c from Customer c WHERE c.name LIKE :#{'%'+#name} AND c.address LIKE :#{'%'+#address} ORDER BY c.id DESC")
//    public List<Customer> all1Customers(@Param("name") String name, @Param("address") String address);

}
