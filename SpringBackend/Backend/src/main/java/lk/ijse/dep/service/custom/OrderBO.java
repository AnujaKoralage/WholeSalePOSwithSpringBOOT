package lk.ijse.dep.service.custom;

import javafx.collections.ObservableList;
import lk.ijse.dep.dto.ItemDTO;
import lk.ijse.dep.dto.OrderDTO;
import lk.ijse.dep.dto.OrderDetailsDTO;
import lk.ijse.dep.dto.OrderItemDetails;

import java.util.List;

public interface OrderBO extends SuperBO{

    public String qtyGetByCode(String itemcode);
//    public void insertOrderDetails(OrderDetailsDTO orderDetailsDTO) ;
//    public void insertOrderItems(OrderItemDetails orderDetailsDTO) ;
    public void finalOrderPalcement(OrderDetailsDTO detailsDTO,List<OrderItemDetails> orderItemDTOS);
    public void updateItemQty(String qtyOnHand, String itemcode);
    public List<ItemDTO> allItems() ;
    public ObservableList getAllCustomerId(ObservableList list) ;
    public String getCurrentId();
    public List<OrderDTO> getAllOrders();

    public boolean orderIsExists(String orerID);

}
