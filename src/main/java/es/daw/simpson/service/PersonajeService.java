package es.daw.simpson.service;

import es.daw.simpson.model.Personaje;
import es.daw.simpson.repository.PersonajeRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// El servicio sí se conecta al repositorio!!!
public class PersonajeService {
    // En spring no usaremos new. Inyectaremos el respository con @Autowired
    private final PersonajeRepository personajeRepository = new PersonajeRepository();

    public List<Personaje> buscar(String lugar,
                                  Integer edadMax,
                                  String ordenarPor, // pendiente
                                  boolean descendente, // pendiente
                                  Integer limite) {

        return personajeRepository.findAll().stream()
                .filter( p -> lugar == null || lugar.isBlank() || p.lugar().equalsIgnoreCase(lugar))
                .filter(p -> p.edad() <= edadMax)
                .sorted((p1, p2) -> p1.nombre().compareTo(p2.nombre())) // estamos ordenando solo por nombre ascendente
                .limit(limite)
                .toList();

    }

    public List<String> lugaresDisponibles() {
        return personajeRepository.findAll().stream()
                //.map(p -> p.lugar())
                .map(Personaje::lugar)
                .distinct()
                .sorted()
                .toList();

//        List<String> lugares = new ArrayList<>();
//        List<Personaje> personajes = personajeRepository.findAll();
//
//        for (Personaje p : personajes) {
//            if (!lugares.contains(p.lugar())) {
//                lugares.add(p.lugar());
//            }
//        }
//
//        return lugares;
    }
}
