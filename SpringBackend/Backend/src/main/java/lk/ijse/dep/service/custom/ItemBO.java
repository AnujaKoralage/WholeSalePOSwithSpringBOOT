package lk.ijse.dep.service.custom;

import lk.ijse.dep.dto.ItemDTO;

import java.util.List;

public interface ItemBO extends SuperBO{

    public List<ItemDTO> getAllItems() ;
    public ItemDTO getItemByCode(String code);
    public void saveItem(ItemDTO itemDTO) ;
    public void deleteItem(String id) ;
    public boolean itemExistsinOrder(String id) ;
    public void updateItem(ItemDTO item) ;
    public boolean isItemExists(String code);

}
