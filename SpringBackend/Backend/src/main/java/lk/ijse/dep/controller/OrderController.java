package lk.ijse.dep.controller;

import lk.ijse.dep.dto.OrderDTO;
import lk.ijse.dep.dto.OrderDetailsDTO;
import lk.ijse.dep.dto.OrderItemDetails;
import lk.ijse.dep.service.custom.OrderBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@CrossOrigin
@RequestMapping("api/v1/orders")
@RestController
public class OrderController {

    @Autowired
    OrderBO orderBO;

//    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Void> getCurrentId(){
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("X-id",orderBO.getCurrentId());
//        httpHeaders.setAccessControlAllowHeaders(Arrays.asList("X-id"));
//        httpHeaders.setAccessControlExposeHeaders(Arrays.asList("X-id"));
//        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
//    }
//api/v1/orders?maxid=true
    @GetMapping(params ="maxid=true", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> currentID(){

        String id = orderBO.getCurrentId();
        System.out.println(id);
        return new ResponseEntity<String>("\""+id+"\"",HttpStatus.OK);
    }

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<OrderDTO> getAllOrders(){
        return orderBO.getAllOrders();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveOrder(@RequestBody OrderDTO orderDTO){
        OrderDetailsDTO detailsDTO = orderDTO.getDetailsDTO();
        List<OrderItemDetails> orderItemDTOS = orderDTO.getOrderItemDTOS();
        System.out.println(detailsDTO.getOrderid());

        if (orderBO.orderIsExists(orderDTO.getDetailsDTO().getOrderid())){
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
        else {
            if (detailsDTO.getCusid().isEmpty() || detailsDTO.getOrderdate().isEmpty() || detailsDTO.getOrderid().isEmpty() || orderItemDTOS.size()==0){
                return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
            }
            else {
                orderBO.finalOrderPalcement(detailsDTO,orderItemDTOS);
                return new ResponseEntity<Void>(HttpStatus.CREATED);
            }
        }
    }

}
