package lk.ijse.dep.service.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.dep.dto.OrderDTO;
import lk.ijse.dep.dto.OrderItemDetails;
import lk.ijse.dep.entities.OrderDetails;
import lk.ijse.dep.service.custom.OrderBO;
import lk.ijse.dep.repositories.DAO.custom.CustomerDAO;
import lk.ijse.dep.repositories.DAO.custom.ItemDao;
import lk.ijse.dep.repositories.DAO.custom.OrderDetailsDAO;
import lk.ijse.dep.repositories.DAO.custom.OrderItemsDAO;
import lk.ijse.dep.dto.ItemDTO;
import lk.ijse.dep.dto.OrderDetailsDTO;
import lk.ijse.dep.entities.Customer;
import lk.ijse.dep.entities.Item;
import lk.ijse.dep.entities.OrderItems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Transactional
public class OrderBOImpl implements OrderBO {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    OrderDetailsDAO orderDetailsDAO;
    @Autowired
    CustomerDAO customerDAO;
    @Autowired
    OrderItemsDAO dao;
    @Autowired
    ItemDao itemDAO;

public String qtyGetByCode(String itemcode){

    return String.valueOf(itemDAO.getQtyByCode(itemcode));
}

    public void finalOrderPalcement(OrderDetailsDTO detailsDTO,List<OrderItemDetails> orderItemDTOS){
        this.insertOrderDetails(detailsDTO);
        for (int i=0;i<orderItemDTOS.size();i++){
            this.insertOrderItems(orderItemDTOS.get(i));
        }
    }

    private void insertOrderDetails(OrderDetailsDTO orderDetailsDTO) {

        orderDetailsDAO.save(new OrderDetails(orderDetailsDTO.getOrderid(),new Customer(orderDetailsDTO.getCusid(),"",""),orderDetailsDTO.getOrderdate()));

    }

    private void insertOrderItems(OrderItemDetails orderDetailsDTO) {
        dao.save(new OrderItems(orderDetailsDTO.getOrderid(),orderDetailsDTO.getItemcode(),orderDetailsDTO.getQty()));
        itemDAO.save(new Item(orderDetailsDTO.getItemcode(),itemDAO.getOne(orderDetailsDTO.getItemcode()).getDescription(),String.valueOf(Integer.parseInt(itemDAO.getOne(orderDetailsDTO.getItemcode()).getQty())-Integer.parseInt(orderDetailsDTO.getQty())),itemDAO.getOne(orderDetailsDTO.getItemcode()).getPrice()));
    }

    public void updateItemQty(String qtyOnHand, String itemcode){
            itemDAO.getOne(itemcode).setQty(qtyOnHand);
    }

    public List<ItemDTO> allItems() {


        return itemDAO.findAll().stream().map(new Function<Item, ItemDTO>() {
            @Override
            public ItemDTO apply(Item item) {
                return new ItemDTO(item.getCode(),item.getDescription(),item.getQty(),item.getPrice());
            }
        }).collect(Collectors.toList());

        /*List<Item> all = dao.findAll();

        List<ItemDTO> list  = new ArrayList<>();
        for (Item item:all) {
            list.add(new ItemDTO(item.getCode(),item.getDescription(),item.getQty(),item.getPrice()));
        }

        return list;*/
    }

    public ObservableList getAllCustomerId(ObservableList list) {

        List<Customer> byId = customerDAO.getById();
        List<String> list1 = null;

        for (Customer customer:byId) {
            list1.add(customer.getId());
        }
        ObservableList<String> strings = FXCollections.observableArrayList(list1);

        return strings;
    }

    @Override
    public String getCurrentId() {
        String id = String.valueOf(Integer.parseInt(orderDetailsDAO.getTopOrderByOrderByOrderidDesc().getOrderid())+1);
        return "00"+id;
    }

    @Override
    public boolean orderIsExists(String orerID) {
        return orderDetailsDAO.existsById(orerID);
    }

    public List<OrderDTO> getAllOrders(){
        List<OrderDetails> all = orderDetailsDAO.findAll();
        List<OrderDTO> orderDTOS = new ArrayList<>();

        for (int i=0; i<all.size();i++){
            List<OrderItems> orderItems = dao.findOrderItems(all.get(i).getOrderid());
            List<OrderItemDetails> orderItemDetails = new ArrayList<>();
            for (int j=0;orderItems.size()>j;j++){
                orderItemDetails.add(new OrderItemDetails(orderItems.get(j).getOrderItemsPK().getOrderid(),orderItems.get(j).getItem().getCode(),orderItems.get(j).getQty()));
            }
            orderDTOS.add(new OrderDTO(new OrderDetailsDTO(all.get(i).getOrderid(),all.get(i).getCusid().getId(),all.get(i).getOrderdate()),orderItemDetails));
        }
        return orderDTOS;
    }


}
