package com.skshieldus.waiting_reservation_be.domain.menu.controller;

import com.skshieldus.waiting_reservation_be.common.api.Api;
import com.skshieldus.waiting_reservation_be.domain.menu.dto.MenuInfoRequest;
import com.skshieldus.waiting_reservation_be.domain.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuApiController {
    private final MenuService menuService;



    @PostMapping("/owner/{storeId}")
    public Api<String> insertMenu(@RequestBody MenuInfoRequest request, @PathVariable int storeId, @RequestHeader("Authorization") String authorization){
        menuService.insertMenu(request, storeId,authorization);
        return Api.OK("success");
    }
    @PutMapping("/owner/{storeId}/{menuId}")
    public Api<String> putMenu(@RequestBody MenuInfoRequest request,@PathVariable int storeId, @PathVariable int menuId, @RequestHeader("Authorization") String authorization){
        menuService.putMenu(request,storeId,menuId,authorization);
        return Api.OK("success");
    }
    @DeleteMapping("/owner/{storeId}/{menuId}")
    public Api<String> deleteMenu(@PathVariable int storeId, @PathVariable int menuId, @RequestHeader("Authorization") String authorization){
        menuService.deleteMenu(storeId,menuId,authorization);
        return Api.OK("success");
    }

}
