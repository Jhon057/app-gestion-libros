package com.devsenior.app_gestion_libros.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsenior.app_gestion_libros.model.Libro;
import com.devsenior.app_gestion_libros.service.LibroServicio;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    private final LibroServicio libroServicio;

    public LibroController(LibroServicio libroServicio) {
        this.libroServicio = libroServicio;
    }

    @GetMapping
    public List<Libro> listarTodos() {
        return libroServicio.listarLibros();
    }

    @GetMapping("/buscar")
    public ResponseEntity<Libro> buscarPorTitulo(@RequestParam String titulo) {
        return libroServicio.buscarPorTitulo(titulo).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Libro> agregarLibro(@RequestBody Libro libro) {
        try {
            Libro nuevo = libroServicio.agregarLibro(libro);
            return ResponseEntity.status(201).body(nuevo);
        } catch (IllegalArgumentException e) {
            e.getMessage();
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPorId(@PathVariable Long id) {
        boolean eliminado = libroServicio.eliminarLibro(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Libro> actualizarLibro(
            @PathVariable Long id,
            @RequestBody Libro libro) {

        try {
            Libro actualizado = libroServicio.actualizarLibro(id, libro);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            e.getMessage();
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Libro> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(libroServicio.buscarPorId(id));
    }

}
