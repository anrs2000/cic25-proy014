package es.cic.curso25.proy014.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.cic.curso25.proy014.entity.Plaza;

public interface PlazaRepository extends JpaRepository<Plaza, Long> {
    @Query("SELECT COALESCE(MAX(p.numPlaza), 0) FROM Plaza p")
    int findMaxNumPlaza();
}
