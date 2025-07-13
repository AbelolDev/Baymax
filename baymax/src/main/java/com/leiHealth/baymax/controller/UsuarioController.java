package com.leiHealth.baymax.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.leiHealth.baymax.model.Usuario;
import com.leiHealth.baymax.service.UsuarioService;

/**
 * Controlador REST para gestionar operaciones relacionadas con los usuarios.
 * Proporciona endpoints para listar, crear, actualizar, buscar y eliminar usuarios.
 * 
 * URL base: /api/v1/usuarios
 */
@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Obtiene la lista de todos los usuarios.
     * 
     * @return Lista de objetos {@link Usuario} o estado 204 si no hay usuarios.
     */
    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {
        List<Usuario> usuarios = usuarioService.findAll();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    /**
     * Crea un nuevo usuario.
     * 
     * @param usuario El objeto {@link Usuario} a guardar.
     * @return El usuario creado junto con el código HTTP 201 (Created).
     */
    @PostMapping
    public ResponseEntity<Usuario> guardar(@RequestBody Usuario usuario) {
        Usuario usuarioNuevo = usuarioService.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioNuevo);
    }

    /**
     * Busca un usuario por su ID.
     * 
     * @param id El ID del usuario a buscar.
     * @return El usuario encontrado o estado 404 si no existe.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscar(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.findById(id);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Actualiza un usuario existente por su ID.
     * 
     * @param id      El ID del usuario a actualizar.
     * @param usuario Los datos actualizados del usuario.
     * @return El usuario actualizado o estado 404 si no se encuentra.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            Usuario user = usuarioService.findById(id);
            user.setId(id);
            user.setRun(usuario.getRun());
            user.setNombre(usuario.getNombre());
            user.setApellido(usuario.getApellido());
            user.setFechaNacimiento(usuario.getFechaNacimiento());
            user.setCorreo(usuario.getCorreo());

            usuarioService.save(user);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Elimina un usuario por su ID.
     * 
     * @param id El ID del usuario a eliminar.
     * @return Código HTTP 204 si se elimina correctamente o 404 si no se encuentra.
     */
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
