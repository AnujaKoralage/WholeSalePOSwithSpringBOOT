package lk.ijse.dep.controller;

import lk.ijse.dep.dto.CustomerDTO;
import lk.ijse.dep.service.custom.CustomerBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequestMapping("api/v1/customers")
@RestController
public class CustomerController {

    @Autowired
    private CustomerBO customerBO;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CustomerDTO> getAllCustomers() {
        return customerBO.allCustomers();
    }

    @GetMapping(value = "/{id:\\d{3}}")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable("id") String id) {
        CustomerDTO dto = null;
        if (customerBO.isCustomerExists(id)){
            dto=customerBO.getCustomerbyId(id);
        }
        return new ResponseEntity<CustomerDTO>(dto,(dto!=null)? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<Void> saveCustomer(@RequestBody CustomerDTO customer){
        if (!customerBO.isCustomerExists(customer.getId())){
            if (customer.getId().trim().isEmpty() || customer.getName().trim().isEmpty() || customer.getAddress().trim().isEmpty()){
                return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
            }
            else {
                customerBO.saveCustomer(customer);
                return new ResponseEntity<Void>(HttpStatus.CREATED);
            }
        }else {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/{id:\\d{3}}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateCustomer(@PathVariable("id") String id,@RequestBody CustomerDTO customerDTO){
        if (customerBO.isCustomerExists(id)){
            if (customerDTO.getName().trim().isEmpty() || customerDTO.getAddress().trim().isEmpty())
                return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
            else{
                customerDTO.setId(id);
                customerBO.updateCustomer(customerDTO);
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }
        }
        else
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/{id:\\d{3}}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("id") String id){
        if (customerBO.isCustomerExists(id)){
            customerBO.deleteCustomer(id);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }
}
