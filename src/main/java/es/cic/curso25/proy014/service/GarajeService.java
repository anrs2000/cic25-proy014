package es.cic.curso25.proy014.service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.cic.curso25.proy014.entity.Coche;
import es.cic.curso25.proy014.entity.Multa;
import es.cic.curso25.proy014.entity.Plaza;
import es.cic.curso25.proy014.exceptions.NotFoundException;
import es.cic.curso25.proy014.repository.CocheRepository;
import es.cic.curso25.proy014.repository.MultaRepository;
import es.cic.curso25.proy014.repository.PlazaRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class GarajeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GarajeService.class);

    @Autowired
    CocheRepository cocheRepository;

    @Autowired
    PlazaRepository plazaRepository;

    @Autowired
    MultaRepository multaRepository;

    public Coche getCoche(Long id) {
        LOGGER.info(String.format("Buscando coche con id %d", id));
        Optional<Coche> coche = cocheRepository.findById(id);
        if (coche.isEmpty()) {
            throw new NotFoundException("No existe ningún coche con el id " + id);
        }
        coche.get().getMultas().size();
        return coche.get();
    }

    public Plaza getPlaza(Long id) {
        LOGGER.info(String.format("Buscando plaza con id %d", id));
        Optional<Plaza> plaza = plazaRepository.findById(id);
        if (plaza.isEmpty()) {
            throw new NotFoundException("No existe ninguna plaza con el id " + id);
        }
        return plaza.get();
    }

    public Plaza getPlazaDelCoche(Long idCoche){
        LOGGER.info(String.format("Buscando plaza del coche con id %d", idCoche));
        Coche coche = this.getCoche(idCoche);
        return coche.getPlaza();
    }

    public Plaza getPlazaPorNum(int numPlaza) {
        LOGGER.info(String.format("Buscando plaza con numPlaza %d", numPlaza));
        boolean plazaEncontrada = false;
        Plaza plazaBuscada = new Plaza();
        Iterator<Plaza> iterador = this.getAllPlazas().iterator();
        while (iterador.hasNext() && !plazaEncontrada) {
            Plaza plazaActual = iterador.next();
            if (plazaActual.getNumPlaza() == numPlaza) {
                plazaBuscada = plazaActual;
                plazaEncontrada = true;
            }
        }
        if (!plazaEncontrada) {
            throw new NotFoundException("No se ha encontrado ninguna plaza con el numPlaza " + numPlaza);
        }
        return plazaBuscada;
    }

    public List<Coche> getAllCoches() {
        LOGGER.info("Obteniendo lista completa de coches");
        return cocheRepository.findAll();
    }

    public List<Plaza> getAllPlazas() {
        LOGGER.info("Obteniendo todas las plazas");
        return plazaRepository.findAll();
    }

    public List<Multa> getAllMultasDeCoche(Long idCoche) {
        LOGGER.info(String.format("Obteniendo multas del coche con id %d", idCoche));
        Coche cocheEncontrado = this.getCoche(idCoche);
        return cocheEncontrado.getMultas();
    }

    public Coche aparcar(Long idCoche, Long idPlaza) {
        Plaza plaza = this.getPlaza(idPlaza);
        LOGGER.info(String.format("Aparcando coche id %d en plaza numPlaza %d", idCoche, plaza.getNumPlaza()));
        Coche coche = this.getCoche(idCoche);
        coche.setNumPlazaAparcada(plaza.getNumPlaza());
        if (!comprobarPlazaCorrecta(idCoche)) {
            coche = this.multarCoche(coche.getId());
        }
        return cocheRepository.save(coche);
    }

    public boolean comprobarPlazaCorrecta(Long id) {
        LOGGER.info(String.format("Comprobando plaza correcta para coche id %d", id));
        Coche coche = this.getCoche(id);
        return (coche.getPlaza().getNumPlaza() == coche.getNumPlazaAparcada());
    }

    public Coche multarCoche(Long id) {
        LOGGER.info(String.format("Multando coche con id %d", id));
        Multa multa = new Multa();
        Coche cocheAMultar = this.getCoche(id);
        cocheAMultar.addMulta(multa);
        cocheAMultar.getMultas().size();
        return cocheRepository.save(cocheAMultar);
    }

    public Coche postCoche(Coche coche) {
        LOGGER.info(String.format("Guardando nuevo coche: %s", coche));
        return cocheRepository.save(coche);
    }

    public Coche putCoche(Long id, Coche nuevoCoche) {
        LOGGER.info(String.format("Actualizando coche id %d con nuevos datos %s", id, nuevoCoche));
        Coche cocheAActualizar = this.getCoche(id);
        cocheAActualizar.setColor(nuevoCoche.getColor());
        cocheAActualizar.getMultas().clear();
        for (Multa multa : nuevoCoche.getMultas()) {
            cocheAActualizar.addMulta(multa);
        }
        cocheAActualizar.setNumPuertas(nuevoCoche.getNumPuertas());
        cocheAActualizar.setPlaza(nuevoCoche.getPlaza());
        return cocheRepository.save(cocheAActualizar);
    }

    public Coche addPlazaCoche(Long idCoche, Plaza plaza){
        Coche coche = this.getCoche(idCoche);
        coche.addPlaza(plaza);

        cocheRepository.save(coche);
        return this.getCoche(idCoche);
    }

    public Plaza calcularNumPlaza(Plaza nuevaPlaza) {
        int max = plazaRepository.findMaxNumPlaza();
        LOGGER.info(String.format("Calculando numPlaza para nueva plaza, max actual es %d", max));
        nuevaPlaza.setNumPlaza(max + 1);
        return nuevaPlaza;
    }

    public void deleteCoche(Long id){
        cocheRepository.deleteById(id);
    }

}