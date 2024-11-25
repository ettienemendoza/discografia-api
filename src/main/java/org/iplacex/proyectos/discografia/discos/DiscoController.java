package org.iplacex.proyectos.discografia.discos;

import java.util.List;
import java.util.Optional;

import org.iplacex.proyectos.discografia.artistas.IArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class DiscoController {

    @Autowired
    private IDiscoRepository discoRepo;

    @Autowired
    private IArtistaRepository artistaRepo;

    // Agregar
    @PostMapping(
        value = "/disco",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Disco> HandlePostDiscoRequest(@RequestBody Disco disco) {
        if (!artistaRepo.existsById(disco.getIdArtista())) {
            return new ResponseEntity<>(null,null, HttpStatus.NOT_FOUND);
        }
        Disco temp = discoRepo.insert(disco);
        return new ResponseEntity<>(temp, HttpStatus.CREATED);
    }

    // Obtener
    @GetMapping(
        value = "/discos",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Disco>> HandleGetDiscosRequest() {
        List<Disco> discos = discoRepo.findAll();
        return new ResponseEntity<>(discos, HttpStatus.OK);
    }

    // Obtener id
    @GetMapping(
        value = "/disco/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Disco> HandleGetDiscoRequest(@PathVariable("id") String id) {
        Optional<Disco> temp = discoRepo.findById(id);
        return temp.map(disco -> new ResponseEntity<>(disco, HttpStatus.OK))
                   .orElseGet(() -> new ResponseEntity<>(null,null, HttpStatus.NOT_FOUND));
    }

    // Obtener todo
    @GetMapping(
        value = "/artista/{id}/discos",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Disco>> HandleGetDiscosByArtistaRequest(@PathVariable("id") String idArtista) {
        List<Disco> discos = discoRepo.findDiscosByIdArtista(idArtista);
        if (discos.isEmpty()) {
            return new ResponseEntity<>(null,null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(discos, HttpStatus.OK);
    }
}