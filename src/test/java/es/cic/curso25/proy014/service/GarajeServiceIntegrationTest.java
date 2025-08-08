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
import es.cic.curso25.proy014.repository.PlazaRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GarajeServiceIntegrationTest {
    @Autowired
    CocheRepository cocheRepository;

    @Autowired
    PlazaRepository plazaRepository;

    @Autowired
    GarajeService garajeService;

    Coche coche1;
    Coche cocheGuardado;
    Plaza plaza1;
    Plaza otraPlaza;

    @BeforeEach
    void preparacion() {
        cocheRepository.deleteAll();
        plazaRepository.deleteAll();

        plaza1 = new Plaza();

        garajeService.calcularNumPlaza(plaza1);

        coche1 = new Coche("rojo", 4);

        coche1.addPlaza(plaza1);

        cocheGuardado = garajeService.postCoche(coche1);

        // Después de haber guardado la primera plaza en BD (al guardar el coche al que
        // va asociada), podemos asignar numero de plaza a la segunda (si no, se le
        // asignaría el mismo)
        otraPlaza = new Plaza();
        garajeService.calcularNumPlaza(otraPlaza);
    }

    @Test
    void testCalcularNumPlaza() {
        assertEquals(1, plaza1.getNumPlaza());
        assertEquals(2, otraPlaza.getNumPlaza());
    }

    @Test
    void testGetAllCoches() {

    }

    /*
     * public Coche aparcar(Coche coche, Plaza plaza) {
     * coche.setNumPlazaAparcada(plaza.getNumPlaza());
     * if (comprobarPlazaCorrecta(coche)) {
     * this.multarCoche(coche.getId());
     * }
     * return cocheRepository.save(coche);
     * }
     */

    @Test
    void testAparcar() {
        int numMultasEsperado = cocheGuardado.getMultas().size() + 1;
        garajeService.aparcar(cocheGuardado, otraPlaza);

        //Traemos los datos actualizados del coche desde la BD
        Coche cocheActualizado = garajeService.getCoche(cocheGuardado.getId());
        assertEquals(numMultasEsperado, cocheActualizado.getMultas().size());

        Plaza plazaDelCoche = cocheActualizado.getPlaza();
        garajeService.aparcar(cocheActualizado, plazaDelCoche);

        //Comprobamos que en caso de que aparque en su plaza, el número de multas no se incrementa
        assertEquals(numMultasEsperado, cocheActualizado.getMultas().size());
    }

    @Test
    void testMultarCoche() {
        int numMultasInicial = cocheGuardado.getMultas().size();

        garajeService.multarCoche(cocheGuardado.getId());

        Coche cocheMultado = garajeService.getCoche(cocheGuardado.getId());

        assertEquals(numMultasInicial + 1, cocheMultado.getMultas().size());
    }

    @Test
    void testPutCoche() {
        Coche nuevoCoche = new Coche("azul", 5);

        garajeService.putCoche(cocheGuardado.getId(), nuevoCoche);
        Coche cocheActualizado = garajeService.getCoche(cocheGuardado.getId());

        assertEquals(cocheActualizado.getColor(), nuevoCoche.getColor());
    }
}
