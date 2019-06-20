package lk.ijse.dep.service.impl;

import lk.ijse.dep.service.custom.CustomerBO;
import lk.ijse.dep.repositories.DAO.custom.CustomerDAO;
import lk.ijse.dep.repositories.DAO.custom.OrderDetailsDAO;
import lk.ijse.dep.dto.CustomerDTO;
import lk.ijse.dep.entities.Customer;
import lk.ijse.dep.entities.OrderDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Transactional
public class CustomerBOImpl implements CustomerBO {

    @Autowired
    private CustomerDAO customerDAO;
    @Autowired
    OrderDetailsDAO od;
    @PersistenceContext
    private EntityManager session;

    public List<CustomerDTO> allCustomers()  {
        return customerDAO.findAll().stream().map(new Function<Customer, CustomerDTO>() {
            @Override
            public CustomerDTO apply(Customer customer) {
                return new CustomerDTO(customer.getId(), customer.getName(), customer.getAddress());
            }
        }).collect(Collectors.toList());


        /*ArrayList<CustomerDTO> customerlist = new ArrayList<>();
        for (Customer customer:customerDAO.findAll()) {
            customerlist.add(new CustomerDTO(customer.getId(),customer.getName(),customer.getAddress()));
        }
        return customerlist;*/
    }

    public void saveCustomer(CustomerDTO customer)  {
        Customer cusentity = new Customer(customer.getId(),customer.getName(),customer.getAddress());

        customerDAO.save(cusentity);
        
    }

    public void deleteCustomer(String id)  {
        customerDAO.deleteById(id);
    }

    public boolean customerExistsinOrder(String id)  {



        /*Stream<OrderDetails> orderDetailsStream = od.findAll().stream().filter(new Predicate<OrderDetails>() {
            @Override
            public boolean test(OrderDetails orderDetails) {
                if (orderDetails.getCusid().equals(id)) {
                    return true;
                }
                return false;
            }
        });*/

        List<OrderDetails> orderDetails = od.findAll();

        for (OrderDetails order:orderDetails) {
            if (order.getCusid().equals(id)){
                return true;
            }
        }
        return false;
    }

    public void updateCustomer(CustomerDTO customer) {
        customerDAO.save(new Customer(customer.getId(), customer.getName(), customer.getAddress()));
    }

    @Override
    public boolean isCustomerExists(String id) {
        return customerDAO.existsById(id);
    }

    @Override
    public CustomerDTO getCustomerbyId(String id) {
        List<Customer> byId = customerDAO.getById();
        return new CustomerDTO(byId.get(0).getId(),byId.get(0).getName(),byId.get(0).getAddress());
    }

}
