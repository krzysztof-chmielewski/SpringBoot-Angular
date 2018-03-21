package com.kchmielewski.sda.java6.angular.player.web;

import com.kchmielewski.sda.java6.angular.player.application.PlayerDto;
import com.kchmielewski.sda.java6.angular.player.application.PlayerService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("players")
@RestController
public class PlayerController {
    private final PlayerService service;

    public PlayerController(PlayerService service) {
        this.service = service;
    }


    @GetMapping("")
    public Iterable<PlayerDto> player() {
        return service.all();
    }

    @PostMapping("")
    public PlayerDto save(@RequestBody PlayerDto player) {
        return service.add(player);
    }

    @GetMapping("{id}")
    public PlayerDto show(@PathVariable Integer id) {
        return service.one(id);
    }

    @PutMapping("{id}")
    public PlayerDto update(@PathVariable Integer id, @RequestBody PlayerDto player) {
//        PlayerDto dto = service.one(id);
        player.setId(id);
        service.add(player);
//        if(player.getName() != null)
//            dto.setName(player.getName());
//        if(player.setSurname(); != null)
//            dto.setAddress(player.getAddress());
//        if(player.getCity() != null)
//            dto.setCity(player.getCity());
//        if(player.getPhone() != null)
//            dto.setPhone(player.getPhone());
//        if(player.getEmail() != null)
//            dto.setEmail(player.getEmail());
//        playerRepository.save(player);

        return player;
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable Integer id) {
        service.remove(id);

        return "";
    }
}
