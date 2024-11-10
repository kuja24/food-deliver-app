package com.food.delivery.fooddelivery.service;

import com.food.delivery.fooddelivery.entity.Menu;
import com.food.delivery.fooddelivery.entity.Restaurant;
import com.food.delivery.fooddelivery.exception.FoodDeliveryException;
import com.food.delivery.fooddelivery.models.MenuDto;
import com.food.delivery.fooddelivery.models.MenuRequest;
import com.food.delivery.fooddelivery.repository.MenuRepository;
import com.food.delivery.fooddelivery.repository.RestaurantRepository;
import com.food.delivery.fooddelivery.util.MenuSpecification;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class MenuService {

    @Autowired
    private final MenuRepository menuRepository;

    private final RestaurantRepository restaurantRepository;

    public MenuService(MenuRepository menuRepository, RestaurantRepository restaurantRepository) {
        this.menuRepository = menuRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public List<MenuDto> getAllMenus()
    {
        List<MenuDto> menuDtos = new ArrayList<>();
        List<Menu> menus = menuRepository.findAll();
        menus.forEach(menu -> {
               menuDtos.add(MenuDto.builder()
                       .item_name(menu.getItem_name())
                               .description(menu.getDescription())
                               .price(menu.getPrice())
                               .is_available(menu.getIs_available())
                               .build());
                              } ) ;
        return menuDtos;
    }

    public Long createMenuItem(MenuRequest menuRequest)
    {
        Restaurant restaurant = restaurantRepository.findById(menuRequest.getRestaurantId())
                    .orElseThrow(() -> new FoodDeliveryException("Restaurant not found", HttpStatus.BAD_REQUEST));
        Menu menu = menuRepository.save(buildMenuEntity(restaurant, menuRequest.getMenuDto()));
        return menu.getMenu_id();
    }

    private Menu buildMenuEntity(Restaurant restaurant, MenuDto menuDto) {
        return Menu.builder()
                .restaurant(restaurant)
                .item_name(menuDto.getItem_name())
                .price(menuDto.getPrice())
                .description(menuDto.getDescription())
                .is_available(menuDto.getIs_available())
                .build();
    }

    public MenuDto updateMenu(Long menu_id, MenuDto menuDto)
    {
        Menu menu = menuRepository.findById(menu_id)
                .orElseThrow(() -> new FoodDeliveryException("Item not found exception", HttpStatus.BAD_REQUEST));
        menu.setItem_name(menu.getItem_name());
        menu.setDescription(menuDto.getDescription());
        menu.setPrice(menuDto.getPrice());
        menu.setIs_available(menuDto.getIs_available());

        Menu updatedMenu = menuRepository.save(menu);
        return convertEntityToDo(updatedMenu);
    }



    public Boolean deleteMenuById(Long menu_id)
    {
        if(menuRepository.existsById(menu_id))
        {
            menuRepository.deleteById(menu_id);
            return true;
        }
        else {
            return false;
        }
    }

    public List<MenuDto> searchMenuItems(String item_name)
    {
        Specification<Menu> specification = MenuSpecification.filterByCriteria(item_name);
        List<Menu> menus = menuRepository.findAll(specification);
        return menus.stream().map(this::convertEntityToDo).toList();

    }

    private MenuDto convertEntityToDo(Menu updatedMenu) {
        return MenuDto.builder()
                .item_name(updatedMenu.getItem_name())
                .description(updatedMenu.getDescription())
                .price(updatedMenu.getPrice())
                .is_available(updatedMenu.getIs_available())
                .build();
    }


}
