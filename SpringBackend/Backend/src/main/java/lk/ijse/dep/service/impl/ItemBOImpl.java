package lk.ijse.dep.service.impl;

import lk.ijse.dep.entities.Customer;
import lk.ijse.dep.service.custom.ItemBO;
import lk.ijse.dep.repositories.DAO.custom.ItemDao;
import lk.ijse.dep.repositories.DAO.custom.OrderItemsDAO;
import lk.ijse.dep.dto.ItemDTO;
import lk.ijse.dep.entities.Item;
import lk.ijse.dep.entities.OrderItems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Transactional
public class ItemBOImpl implements ItemBO {
    @Autowired
    private ItemDao itemDAO;
    @Autowired
    private OrderItemsDAO orderDetailsDAO;

    @PersistenceContext
private EntityManager session;

    public List<ItemDTO> getAllItems() {

        return itemDAO.findAll().stream().map(new Function<Item, ItemDTO>() {
            @Override
            public ItemDTO apply(Item item) {
                return new ItemDTO(item.getCode(),item.getDescription(),item.getQty(),item.getPrice());
            }
        }).collect(Collectors.toList());

        /*List<Item> items = itemDAO.findAll();
        ArrayList<ItemDTO> list = new ArrayList<>();

        for (Item item:items) {
            list.add(new ItemDTO(item.getCode(),item.getDescription(),item.getQty(),item.getPrice()));
        }

        return list;*/
    }

    @Override
    public ItemDTO getItemByCode(String code) {
        Item itemByCode = itemDAO.findItemByCode(code);
        return new ItemDTO(itemByCode.getCode(),itemByCode.getDescription(),itemByCode.getQty(),itemByCode.getPrice());
    }

    public void saveItem(ItemDTO itemDTO){
        itemDAO.save(new Item(itemDTO.getCode(), itemDTO.getDescription(), itemDTO.getQty(), itemDTO.getPrice()));

    }

    public void deleteItem(String id) {
        itemDAO.deleteById(id);

    }

    public boolean itemExistsinOrder(String id) {
        List<OrderItems> orderItems = orderDetailsDAO.findAll();

        for (OrderItems orderitem:orderItems) {
            if (orderitem.getOrderItemsPK().getItemcode().equals(id)){
                return true;
            }
        }
        return false;
    }

    public void updateItem(ItemDTO item) {
        itemDAO.save(new Item(item.getCode(), item.getDescription(), item.getQty(), item.getPrice()));
    }

    @Override
    public boolean isItemExists(String code) {
        return itemDAO.existsById(code);
    }

}
