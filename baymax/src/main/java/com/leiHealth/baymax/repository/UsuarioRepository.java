package com.leiHealth.baymax.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leiHealth.baymax.model.Usuario;

/*
CRUD (Create, Read, Update, Delete):
o save(S entity): Guarda una entidad.
o findById(ID id): Encuentra una entidad por su ID.
o existsById(ID id): Verifica si una entidad con un ID dado existe.
o findAll(): Encuentra todas las entidades.
o findAllById(Iterable<ID> ids): Encuentra todas las entidades por sus IDs.
o count(): Cuenta todas las entidades.
o deleteById(ID id): Borra una entidad por su ID.
o delete(S entity): Borra una entidad.
o deleteAll(): Borra todas las entidades.
Paginación y Ordenación:
• findAll(Pageable pageable): Encuentra todas las entidades con paginación.
• findAll(Sort sort): Encuentra todas las entidades con ordenación.
*/

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findByApellido(String apellido);

    Usuario findByCorreo(String correo);

    List<Usuario> findByNombreAndApellido(String nombre, String apellido);
}
