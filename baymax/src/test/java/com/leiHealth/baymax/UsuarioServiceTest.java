package com.leiHealth.baymax;

import com.leiHealth.baymax.model.Usuario;
import com.leiHealth.baymax.repository.UsuarioRepository;
import com.leiHealth.baymax.service.UsuarioService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuarioEjemplo;

    @BeforeEach
    void setUp() {
        usuarioEjemplo = new Usuario();
        usuarioEjemplo.setId(1L);
        usuarioEjemplo.setRun("12345678-9");
        usuarioEjemplo.setNombre("Juan");
        usuarioEjemplo.setApellido("Pérez");
        usuarioEjemplo.setFechaNacimiento(Date.valueOf("1990-01-01"));
        usuarioEjemplo.setCorreo("juan.perez@email.com");
    }

    @Test
    void findAllShouldReturnListOfUsuarios() {
        List<Usuario> usuarios = Collections.singletonList(usuarioEjemplo);
        when(usuarioRepository.findAll()).thenReturn(usuarios);

        List<Usuario> resultado = usuarioService.findAll();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Juan", resultado.get(0).getNombre());
    }

    @Test
    void findByIdShouldReturnUsuarioWhenFound() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioEjemplo));

        Usuario resultado = usuarioService.findById(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Juan", resultado.getNombre());
    }

    @Test
    void findByIdShouldThrowExceptionWhenNotFound() {
        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            usuarioService.findById(999L);
        });
    }

    @Test
    void saveShouldPersistUsuario() {
        when(usuarioRepository.save(usuarioEjemplo)).thenReturn(usuarioEjemplo);

        Usuario resultado = usuarioService.save(usuarioEjemplo);

        assertNotNull(resultado);
        verify(usuarioRepository, times(1)).save(usuarioEjemplo);
    }

    @Test
    void deleteShouldRemoveUsuario() {
        Long id = 1L;

        doNothing().when(usuarioRepository).deleteById(id);

        usuarioService.delete(id);

        verify(usuarioRepository, times(1)).deleteById(id);
    }
}
