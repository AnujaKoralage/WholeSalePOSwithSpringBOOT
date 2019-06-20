package lk.ijse.dep.dto;

import java.util.List;

public class OrderDTO {

    private OrderDetailsDTO detailsDTO;
    private List<OrderItemDetails> orderItemDTOS;

    public OrderDTO() {
    }

    public OrderDTO(OrderDetailsDTO detailsDTO, List<OrderItemDetails> orderItemDTOS) {
        this.detailsDTO = detailsDTO;
        this.orderItemDTOS = orderItemDTOS;
    }

    public OrderDetailsDTO getDetailsDTO() {
        return detailsDTO;
    }

    public void setDetailsDTO(OrderDetailsDTO detailsDTO) {
        this.detailsDTO = detailsDTO;
    }

    public List<OrderItemDetails> getOrderItemDTOS() {
        return orderItemDTOS;
    }

    public void setOrderItemDTOS(List<OrderItemDetails> orderItemDTOS) {
        this.orderItemDTOS = orderItemDTOS;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "detailsDTO=" + detailsDTO +
                ", orderItemDTOS=" + orderItemDTOS +
                '}';
    }
}
