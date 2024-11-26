package org.iplacex.proyectos.discografia.discos;


import java.util.List;

import org.iplacex.proyectos.discografia.artistas.Artista;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface IDiscoRepository extends MongoRepository <Disco,String> {
    @Query("{ 'idArtista' : ?0 }")
    Artista findArtistasByid (String id) throws Error;

    List<Disco> findDiscosByIdArtista(String idArtista);

}   
