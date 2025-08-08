package es.cic.curso25.proy014.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.cic.curso25.proy014.entity.Coche;
import es.cic.curso25.proy014.entity.Multa;
import es.cic.curso25.proy014.entity.Plaza;
import es.cic.curso25.proy014.service.GarajeService;

@RestController
@RequestMapping("/coches")
public class GarajeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GarajeController.class);

    @Autowired
    GarajeService garajeService;

    @GetMapping("/{id}")
    public Coche getCoche(@PathVariable Long id) {
        LOGGER.info(String.format("GET /coches/%d - obtener un coche por id", id));
        return garajeService.getCoche(id);
    }

    @GetMapping("/{idCoche}/plaza")
    public Plaza getPlazaDelCoche(@PathVariable Long idCoche) {
        LOGGER.info(String.format("\"GET /coches/%d/plaza - obtener una plaza por el id del coche", idCoche));
        return garajeService.getPlazaDelCoche(idCoche);
    }

    @GetMapping
    public List<Coche> getAllCoches() {
        LOGGER.info("GET /coches - obtener todos los coches");
        return garajeService.getAllCoches();
    }

    @GetMapping("/plazas")
    public List<Plaza> getAllPlazas() {
        LOGGER.info("GET /coches/plazas - obtener todas las plazas");
        return garajeService.getAllPlazas();
    }

    @GetMapping("/{id}/multas")
    public List<Multa> getAllMultasDeCoche(@PathVariable("id") Long idCoche) {
        LOGGER.info(String.format("GET /coches/%d/multas - obtener todas las multas de un coche", idCoche));
        return garajeService.getAllMultasDeCoche(idCoche);
    }

    @PutMapping("/aparcar/{idPlaza}/{idCoche}")
    public Coche aparcar(@PathVariable Long idCoche, @PathVariable Long idPlaza) {
        LOGGER.info(String.format("PUT /coches/aparcar/%d/%d - aparcar coche en plaza", idPlaza, idCoche));
        return garajeService.aparcar(idCoche, idPlaza);
    }

    @PostMapping
    public Coche postCoche(@RequestBody Coche coche) {
        return garajeService.postCoche(coche);
    }

    @PutMapping("/{id}")
    public Coche putCoche(@PathVariable Long id, @RequestBody Coche nuevoCoche) {
        LOGGER.info(String.format("PUT /coches/%d - actualizar coche por id", id));
        return garajeService.putCoche(id, nuevoCoche);
    }

    @PutMapping("/{id}")
    public Coche addPlaza(@PathVariable("id") Long idCoche, @RequestBody Plaza plaza) {
        garajeService.calcularNumPlaza(plaza);
        return garajeService.addPlazaCoche(idCoche, plaza);
    }

    @DeleteMapping("/{id}")
    public void deleteCoche(@PathVariable Long id) {
        LOGGER.info(String.format("DELETE /coches/%d - eliminar coche por id", id));
        garajeService.deleteCoche(id);
    }
}