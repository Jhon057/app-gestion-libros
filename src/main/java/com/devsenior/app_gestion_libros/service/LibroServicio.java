package com.devsenior.app_gestion_libros.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.devsenior.app_gestion_libros.model.Libro;

@Service
public class LibroServicio {

    private final List<Libro> libros = new ArrayList<>();

    private final AtomicLong contadorId = new AtomicLong(1);

    public LibroServicio() {
        libros.add(new Libro(1L, "Si no eres el primero eres el último", "Ground", 2026));
        libros.add(new Libro(2L, "Cien Años de Soledad", "Gabriel García Márquez", 1967));
        libros.add(new Libro(3L, "Don Quijote de la Mancha", "Miguel de Cervantes",
                1605));
    }

    public List<Libro> listarLibros() {
        return Collections.unmodifiableList(libros); // copia de la lista proteger los datos internos
    }

    public Optional<Libro> buscarPorTitulo(String titulo) {
        return libros.stream().filter(l -> l.getTitulo().equalsIgnoreCase(titulo)).findFirst();
    }

    public Libro agregarLibro(Libro libro) {
        if (libro.getTitulo() == null || libro.getTitulo().isBlank()) {
            throw new IllegalArgumentException("El título no puede estar vacío");
        }
        libro.setId(contadorId.getAndIncrement());
        libros.add(libro);
        return libro;
    }

    public boolean eliminarLibro(Long id) {
        return libros.removeIf(l -> l.getId().equals(id));
    }

    public Libro actualizarLibro(Long id, Libro libro) {
        Libro buscarLibro = libros.stream().filter(l -> l.getId().equals(id)).findFirst()
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        libro.setTitulo(buscarLibro.getTitulo());
        libro.setAutor(buscarLibro.getAutor());
        libro.setAnioPublicacion(buscarLibro.getAnioPublicacion());

        return libro;

    }

    public Libro buscarPorId(Long id) {
        return libros.stream().filter(l -> l.getId().equals(id)).findFirst().orElseThrow(
                () -> new RuntimeException("libro no encontrado"));
    }
}
