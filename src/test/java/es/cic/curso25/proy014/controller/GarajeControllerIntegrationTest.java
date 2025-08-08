package es.cic.curso25.proy014.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import es.cic.curso25.proy014.entity.Coche;
import es.cic.curso25.proy014.entity.Plaza;
import es.cic.curso25.proy014.repository.CocheRepository;
import es.cic.curso25.proy014.repository.PlazaRepository;
import es.cic.curso25.proy014.service.GarajeService;

@SpringBootTest
@AutoConfigureMockMvc
public class GarajeControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CocheRepository cocheRepository;

    @Autowired
    PlazaRepository plazaRepository;

    @Autowired
    GarajeController garajeController;

    Coche cocheGuardado1;
    Coche cocheGuardado2;
    Coche cocheGuardado3;

    Plaza plaza1;
    Plaza plaza2;
    Plaza plaza3;

    @BeforeEach
    void preparacion() {
        cocheRepository.deleteAll();
        plazaRepository.deleteAll();

        Coche coche1 = new Coche("morado", 5);
        Coche coche2 = new Coche("rosa", 7);
        Coche coche3 = new Coche("blanco", 4);

        cocheGuardado1 = garajeController.postCoche(coche1);
        cocheGuardado2 = garajeController.postCoche(coche2);
        cocheGuardado3 = garajeController.postCoche(coche3);

        plaza1 = new Plaza();
        plaza2 = new Plaza();
        plaza3 = new Plaza();

        garajeController.addPlaza(cocheGuardado1.getId(), plaza1);
        garajeController.addPlaza(cocheGuardado2.getId(), plaza2);
        garajeController.addPlaza(cocheGuardado3.getId(), plaza3);

        plaza1 = garajeController.getPlazaDelCoche(cocheGuardado1.getId());
        plaza2 = garajeController.getPlazaDelCoche(cocheGuardado2.getId());
        plaza3 = garajeController.getPlazaDelCoche(cocheGuardado3.getId());
    }

    /*
     * @PutMapping("/aparcar/{idPlaza}/{idCoche}")
     * public Coche aparcar(@PathVariable Long idCoche, @PathVariable Long idPlaza)
     * {
     * LOGGER.info(String.
     * format("PUT /coches/aparcar/%d/%d - aparcar coche en plaza", idPlaza,
     * idCoche));
     * return garajeService.aparcar(idCoche, idPlaza);
     * }
     */
    @Test
    void testAparcar() throws Exception {
        mockMvc.perform(put(String.format("/coches/aparcar/%d/%d", cocheGuardado1.getId(), plaza1.getId()))
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteCoche() throws Exception {

    }

    @Test
    void testGetAllCoches() throws Exception {

    }

    @Test
    void testGetAllMultasDeCoche() throws Exception {

    }

    @Test
    void testGetAllPlazas() throws Exception {

    }

    @Test
    void testGetCoche() throws Exception {

    }

    @Test
    void testPutCoche() throws Exception {

    }

    @Test
    void testAddPlaza() throws Exception {

    }

    @Test
    void testGetPlazaDelCoche() throws Exception {

    }

    @Test
    void testPostCoche() throws Exception {

    }

}
