package web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//import web.model.Roles;
import web.model.User;
import web.service.RolesService;
import web.service.UserService;

import java.util.*;

@RestController
@RequestMapping("admin/users")
public class AdminController {

    private final UserService userService;
    private final RolesService roleService;

    @Autowired
    public AdminController(UserService userService, RolesService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostMapping()
    public ResponseEntity<User> create(@RequestBody User user) {
        userService.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<User>> read() {
        final List<User> clients = userService.allread();
        return clients != null &&  !clients.isEmpty()
                ? new ResponseEntity<>(clients, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }




    @GetMapping(value = "/{id}")
    public ResponseEntity<User> readId(@PathVariable("id") int id) {
        final User user = userService.show(id);
        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody User user) {
        final boolean updated = userService.update(user);
        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        final boolean deleted = userService.delete(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}
