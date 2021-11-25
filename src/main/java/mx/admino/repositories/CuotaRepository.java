package mx.admino.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import mx.admino.models.Cuota;
import mx.admino.models.CuotaEstatus;

@Repository
public interface CuotaRepository extends MongoRepository<Cuota, String> {

	List<Cuota> findByEstatus(CuotaEstatus estatus);

}
