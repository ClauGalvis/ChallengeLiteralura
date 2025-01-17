package com.aluracursos.ChallengeLiteralura.repositorio;

import com.aluracursos.ChallengeLiteralura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository <Autor,Long> {

    List<Autor> findAll();
    List<Autor> findByFechaDeNacimientoLessThanOrFechaDeMuerteGreaterThanEqual(int fechaInicio, int fechaFin);

}
