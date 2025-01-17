package com.aluracursos.ChallengeLiteralura.repositorio;

import com.aluracursos.ChallengeLiteralura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibroRepository extends JpaRepository <Libro, Long> {

    boolean existsByTitulo(String titulo);
    Libro findByTituloContainsIgnoreCase(String titulo);
    List<Libro> findByIdiomas(String idioma);


}