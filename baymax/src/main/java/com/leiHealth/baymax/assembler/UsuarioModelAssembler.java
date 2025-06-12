package com.leiHealth.baymax.assembler;

import com.leiHealth.baymax.model.Usuario;
import com.leiHealth.baymax.controller.UsuarioController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import org.springframework.lang.NonNull;

public class UsuarioModelAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {

    @Override
    @NonNull
    public EntityModel<Usuario> toModel(@NonNull Usuario usuario) {
        // Verificamos que el usuario no sea nulo
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no puede ser nulo");
        }
        
        return EntityModel.of(usuario,
                linkTo(UsuarioController.class).slash(usuario.getId()).withSelfRel(),
                linkTo(UsuarioController.class).withRel("usuarios"));
    }
}
