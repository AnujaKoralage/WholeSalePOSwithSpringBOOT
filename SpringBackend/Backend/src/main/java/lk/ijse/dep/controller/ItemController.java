package lk.ijse.dep.controller;

import lk.ijse.dep.dto.ItemDTO;
import lk.ijse.dep.service.custom.ItemBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequestMapping("/api/v1/items")
@RestController
public class ItemController {

    @Autowired
    ItemBO itemBO;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ItemDTO> getAllItems(){
        return itemBO.getAllItems();
    }

    @GetMapping(value = "/{id:\\d{3}}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemDTO> getItem(@PathVariable("id") String code){
        ItemDTO itemDTO=null;
        if (itemBO.isItemExists(code)){
            itemDTO = itemBO.getItemByCode(code);
        }
        return new ResponseEntity<ItemDTO>(itemDTO,(itemDTO !=null) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<Void> saveItem(@RequestBody ItemDTO itemDTO){
        if (itemBO.isItemExists(itemDTO.getCode())){
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
        else {
            itemBO.saveItem(itemDTO);
            return new ResponseEntity<Void>(HttpStatus.CREATED);
        }
    }

    @PutMapping(value = "/{id:\\d{3}}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateItem(@PathVariable("id") String code,@RequestBody ItemDTO itemDTO){
        if (itemBO.isItemExists(code)){
            if (code.isEmpty() || itemDTO.getDescription().isEmpty() || itemDTO.getPrice().isEmpty() || itemDTO.getQty().isEmpty()){
                return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
            }
            else {
                itemDTO.setCode(code);
                itemBO.updateItem(itemDTO);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }
        else {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/{id:\\d{3}}")
    public ResponseEntity<Void> deleteItem(@PathVariable("id") String id){
        if (itemBO.isItemExists(id)){
            itemBO.deleteItem(id);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }

}
