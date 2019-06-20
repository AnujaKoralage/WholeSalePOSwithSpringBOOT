package lk.ijse.dep.service.custom;

import lk.ijse.dep.dto.CustomerDTO;

import java.util.List;

public interface CustomerBO extends SuperBO {

    public List<CustomerDTO> allCustomers();
    public void saveCustomer(CustomerDTO customer);
    public void deleteCustomer(String id);
    public boolean customerExistsinOrder(String id);
    public void updateCustomer(CustomerDTO customer);
    public boolean isCustomerExists(String id);
    public CustomerDTO getCustomerbyId(String id);

}
