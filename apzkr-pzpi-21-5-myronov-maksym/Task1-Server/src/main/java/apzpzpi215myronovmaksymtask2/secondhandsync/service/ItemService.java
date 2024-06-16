package apzpzpi215myronovmaksymtask2.secondhandsync.service;

import apzpzpi215myronovmaksymtask2.secondhandsync.exception.ItemException;
import apzpzpi215myronovmaksymtask2.secondhandsync.exception.PackageException;
import apzpzpi215myronovmaksymtask2.secondhandsync.model.dto.ItemDTO;
import apzpzpi215myronovmaksymtask2.secondhandsync.model.dto.ItemsDTO;
import apzpzpi215myronovmaksymtask2.secondhandsync.model.entity.Item;
import apzpzpi215myronovmaksymtask2.secondhandsync.repository.ItemRepository;
import apzpzpi215myronovmaksymtask2.secondhandsync.repository.PackageRepository;
import com.google.cloud.spring.vision.CloudVisionTemplate;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {
    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private CloudVisionTemplate cloudVisionTemplate;

    private final PackageRepository packageRepository;
    private final ItemRepository itemRepository;
    private List<String> clothingTypes = Arrays.asList("Jeans", "Trousers", "Shirt", "Dress", "Skirt", "Shorts");
    private List<String> colors = Arrays.asList("White", "Blue", "Red", "Green", "Yellow", "Black", "Purple", "Pink", "Orange");
    private List<String> brands = Arrays.asList("Adidas", "Nike", "Puma");

    public ItemService(PackageRepository packageRepository, ItemRepository itemRepository) {
        this.packageRepository = packageRepository;
        this.itemRepository = itemRepository;
    }

    public void addItem(ItemDTO itemDTO) throws ItemException, PackageException {
        Item item = convertDTOToEntity(itemDTO);

        try {
            itemRepository.save(item);
        } catch (Exception e) {
            throw new ItemException(ItemException.ItemExceptionProfile.ITEM_NOT_FOUND);
        }
    }

    public void updateItemSoldStatus(Long itemId) throws ItemException{
        Item item;
        try {
            item = itemRepository.findById(itemId).get();
        } catch (Exception e) {
            throw new ItemException(ItemException.ItemExceptionProfile.ITEM_NOT_FOUND);
        }

        item.setIsSold(!item.getIsSold());
    }

    public ItemsDTO getAllItems() throws ItemException {
        ItemsDTO itemsDTO = new ItemsDTO();
        List<Item> items;

        try {
            items = itemRepository.findAll();
        } catch (Exception e) {
            throw new ItemException(ItemException.ItemExceptionProfile.ITEMS_NOT_FOUND);
        }

        itemsDTO.setItems(convertEntitiesToDTOs(items));

        return itemsDTO;
    }

    private List<ItemDTO> convertEntitiesToDTOs(List<Item> items) {
        List<ItemDTO> itemDTOs = new ArrayList<>();
        for (Item item: items) {
            itemDTOs.add(convertEntityToDTO(item));
        }

        return itemDTOs;
    }

    private ItemDTO convertEntityToDTO(Item item) {
        ItemDTO itemDTO = new ItemDTO();

        itemDTO.setId(item.getId());
        itemDTO.setBrand(item.getBrand());
        itemDTO.setImage(item.getImage());
        itemDTO.setColor(item.getColor());
        itemDTO.setType(item.getType());
        itemDTO.setName(item.getName());
        itemDTO.setIsSold(item.getIsSold());
        itemDTO.setPackageId(item.getAPackage().getId());

        return itemDTO;
    }

    public Item convertDTOToEntity(ItemDTO itemDTO) throws PackageException {
        Item item = new Item();
        mapBasicFields(item, itemDTO);
        mapFieldsFromImageAnalyze(item, itemDTO);

        return item;
    }

    private void mapBasicFields(Item item, ItemDTO itemDTO) throws PackageException {
        item.setId(itemDTO.getId());
        item.setImage(itemDTO.getImage());
        item.setIsSold(itemDTO.getIsSold());

        try {
            if (itemDTO.getPackageId() != null) {
                item.setAPackage(packageRepository.findById(itemDTO.getPackageId()).get());
            }
        } catch (Exception e) {
            throw new PackageException(PackageException.PackageExceptionProfile.PACKAGE_NOT_FOUND);
        }
    }

    private void mapFieldsFromImageAnalyze(Item item, ItemDTO itemDTO) {
        Resource imageResource = this.resourceLoader.getResource("file:" + itemDTO.getImage());
        AnnotateImageResponse response = this.cloudVisionTemplate.analyzeImage(
                imageResource, Feature.Type.LABEL_DETECTION);
        EntityAnnotation type = null;
        EntityAnnotation color = null;
        EntityAnnotation brand = null;
        EntityAnnotation name = null;
        for (EntityAnnotation result : response.getLabelAnnotationsList()) {
            double weightedScore = calculateWeightedScore(result);

            if (clothingTypes.contains(result.getDescription()) && (type == null || weightedScore > calculateWeightedScore(type))) {
                type = result;
            }

            if (colors.contains(result.getDescription()) && (color == null || weightedScore > calculateWeightedScore(color))) {
                color = result;
            }

            if (brands.contains(result.getDescription()) && (brand == null || weightedScore > calculateWeightedScore(brand))) {
                brand = result;
            }

            if (name == null || weightedScore > calculateWeightedScore(name)) {
                name = result;
            }
        }

        item.setColor(color != null ? color.getDescription() : "Undefined");
        item.setType(type != null ? type.getDescription() : "Undefined");
        item.setBrand(brand != null ? brand.getDescription() : "Undefined");
        item.setName(name != null ? name.getDescription() : "Undefined");
    }

    public double calculateWeightedScore(EntityAnnotation result) {
        return (result.getScore() + result.getTopicality()) / 2.0;
    }
}
