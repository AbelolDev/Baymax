package com.leiHealth.baymax.controller;

import com.leiHealth.baymax.model.Usuario;
import com.leiHealth.baymax.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/v2/usuarios")
public class UsuarioControllerV2 {

    @Autowired
    private UsuarioService usuarioService;

    // Método para listar usuarios con HATEOAS
    @GetMapping
    public ResponseEntity<List<EntityModel<Usuario>>> listar() {
        List<Usuario> usuarios = usuarioService.findAll();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<Usuario>> usuariosConLinks = usuarios.stream().map(usuario -> {
            EntityModel<Usuario> usuarioModel = EntityModel.of(usuario);
            // Enlaces HATEOAS
            usuarioModel.add(linkTo(UsuarioControllerV2.class).slash(usuario.getId()).withSelfRel());
            usuarioModel.add(linkTo(UsuarioControllerV2.class).slash(usuario.getId()).slash("actualizar").withRel("actualizar"));
            usuarioModel.add(linkTo(UsuarioControllerV2.class).slash(usuario.getId()).slash("eliminar").withRel("eliminar"));
            return usuarioModel;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(usuariosConLinks);
    }

    // Método para crear un nuevo usuario con HATEOAS
    @PostMapping
    public ResponseEntity<EntityModel<Usuario>> guardar(@RequestBody Usuario usuario) {
        Usuario usuarioNuevo = usuarioService.save(usuario);
        EntityModel<Usuario> usuarioModel = EntityModel.of(usuarioNuevo);
        // Enlace a la nueva entidad creada
        usuarioModel.add(linkTo(UsuarioControllerV2.class).slash(usuarioNuevo.getId()).withSelfRel());
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioModel);
    }

    // Método para obtener un usuario por id con HATEOAS
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Usuario>> buscar(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.findById(id);
            EntityModel<Usuario> usuarioModel = EntityModel.of(usuario);
            // Enlaces HATEOAS
            usuarioModel.add(linkTo(UsuarioControllerV2.class).slash(id).withSelfRel());
            usuarioModel.add(linkTo(UsuarioControllerV2.class).slash(id).slash("actualizar").withRel("actualizar"));
            usuarioModel.add(linkTo(UsuarioControllerV2.class).slash(id).slash("eliminar").withRel("eliminar"));
            return ResponseEntity.ok(usuarioModel);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Método para actualizar un usuario con HATEOAS
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Usuario>> actualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            Usuario user = usuarioService.findById(id);
            user.setId(id);
            user.setRun(usuario.getRun());
            user.setNombre(usuario.getNombre());
            user.setApellido(usuario.getApellido());
            user.setFechaNacimiento(usuario.getFechaNacimiento());
            user.setCorreo(usuario.getCorreo());

            usuarioService.save(user);
            EntityModel<Usuario> usuarioModel = EntityModel.of(user);
            // Enlaces HATEOAS
            usuarioModel.add(linkTo(UsuarioControllerV2.class).slash(id).withSelfRel());
            usuarioModel.add(linkTo(UsuarioControllerV2.class).slash(id).slash("eliminar").withRel("eliminar"));
            return ResponseEntity.ok(usuarioModel);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Método para eliminar un usuario con HATEOAS
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            usuarioService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
