package apzpzpi215myronovmaksymtask2.secondhandsync.controller;

import apzpzpi215myronovmaksymtask2.secondhandsync.exception.ItemException;
import apzpzpi215myronovmaksymtask2.secondhandsync.exception.PackageException;
import apzpzpi215myronovmaksymtask2.secondhandsync.model.dto.ItemDTO;
import apzpzpi215myronovmaksymtask2.secondhandsync.model.dto.ItemsDTO;
import apzpzpi215myronovmaksymtask2.secondhandsync.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/item-manager")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping("/setItem")
    public ResponseEntity<?> addItem(@RequestBody ItemDTO itemDTO) throws PackageException, ItemException {
        itemService.addItem(itemDTO);
        return ResponseEntity.ok("Item added successfully");
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @GetMapping("/getItems")
    public ItemsDTO getAllItems() throws ItemException {
        return itemService.getAllItems();
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PutMapping("/{itemId}/setSold")
    public ResponseEntity<?> updateItemSoldStatus(@PathVariable Long itemId) throws ItemException {
        itemService.updateItemSoldStatus(itemId);
        return ResponseEntity.ok("Item sold status updated successfully");
    }
}
