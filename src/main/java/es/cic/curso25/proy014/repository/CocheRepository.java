package es.cic.curso25.proy014.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.cic.curso25.proy014.entity.Coche;

public interface CocheRepository extends JpaRepository<Coche, Long> {

}
