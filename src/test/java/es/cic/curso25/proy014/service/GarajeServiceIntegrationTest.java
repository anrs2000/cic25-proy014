package es.cic.curso25.proy014.service;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import es.cic.curso25.proy014.entity.Coche;
import es.cic.curso25.proy014.entity.Multa;
import es.cic.curso25.proy014.entity.Plaza;
import es.cic.curso25.proy014.repository.CocheRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GarajeServiceIntegrationTest {
    @Autowired
    CocheRepository cocheRepository;

    @Autowired
    GarajeService garajeService;

    Coche coche;
    Multa multa;
    Coche cocheGuardado;
    Plaza plaza;

    @BeforeEach
    void preparacion() {
        cocheRepository.deleteAll();
        coche = new Coche("rojo", 4);
        multa = new Multa();

        cocheGuardado = garajeService.postCoche(coche);
    }

    @Test
    void testGetAllCoches() {

    }

    @Test
    void testMultarCoche() {
        // Coche cocheMultado = garajeService.multarCoche(cocheGuardado.getId(), multa);
        garajeService.multarCoche(cocheGuardado.getId());

        Coche cocheMultado = garajeService.getCoche(cocheGuardado.getId());

        assertEquals(cocheMultado.getMultas().get(0).getFechaMaximaPago(), multa.getFechaMaximaPago());
        assertEquals(cocheMultado.getMultas().get(0).getPrecio(), multa.getPrecio());
    }

    @Test
    void testPutCoche() {
        Coche nuevoCoche = new Coche("azul", 5);

        garajeService.putCoche(cocheGuardado.getId(), nuevoCoche);
        Coche cocheActualizado = garajeService.getCoche(cocheGuardado.getId());

        assertEquals(cocheActualizado.getColor(), nuevoCoche.getColor());
    }
}
