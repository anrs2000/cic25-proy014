package es.cic.curso25.proy014.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.cic.curso25.proy014.entity.Coche;
import es.cic.curso25.proy014.entity.Multa;
import es.cic.curso25.proy014.entity.Plaza;
import es.cic.curso25.proy014.exceptions.NotFoundException;
import es.cic.curso25.proy014.repository.CocheRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class GarajeService {

    @Autowired
    CocheRepository cocheRepository;

    public Coche getCoche(Long id) {
        Optional<Coche> coche = cocheRepository.findById(id);
        if (coche.isEmpty()) {
            throw new NotFoundException("No existe ningún coche con el id " + id);
        }
        coche.get().getMultas().size();
        return coche.get();
    }

    public List<Coche> getAllCoches() {
        return cocheRepository.findAll();
    }

    public Coche aparcar(Coche coche, Plaza plaza) {
        coche.setNumPlazaAparcada(plaza.getNumPlaza());
         if(comprobarPlazaCorrecta(coche)){
            this.multarCoche(coche.getId());
        }
        return cocheRepository.save(coche);
    }

    public boolean comprobarPlazaCorrecta(Coche coche) {
        return (coche.getPlaza().getNumPlaza() == coche.getNumPlazaAparcada());
    }

    public Coche multarCoche(Long id) {
        Multa multa = new Multa();
        Coche cocheAMultar = this.getCoche(id);
        cocheAMultar.addMulta(multa);

        // Hibernate.initialize(cocheAMultar.getMultas());
        cocheAMultar.getMultas().size(); // Inicializamos la lista de multas, para evitar el lazyException
        return cocheAMultar;
    }

    public Coche postCoche(Coche coche) {
        return cocheRepository.save(coche);
    }

    public Coche putCoche(Long id, Coche nuevoCoche) {
        Coche cocheAActualizar = this.getCoche(id);
        cocheAActualizar.setColor(nuevoCoche.getColor());
        cocheAActualizar.setMultas(nuevoCoche.getMultas());
        cocheAActualizar.setNumPuertas(nuevoCoche.getNumPuertas());
        cocheAActualizar.setPlaza(nuevoCoche.getPlaza());

        return cocheRepository.save(cocheAActualizar);
    }


}
