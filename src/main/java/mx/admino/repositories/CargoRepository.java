package mx.admino.repositories;

import java.util.Date;
import java.util.List;

import mx.admino.models.entities.Casa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import mx.admino.models.CargoEstatus;
import mx.admino.models.entities.Cargo;

@Repository
public interface CargoRepository extends MongoRepository<Cargo, String> {

	List<Cargo> findByFechaVencimientoBeforeAndEstatus(Date fechaCorte, CargoEstatus estatus);

    Page<Cargo> findByCasa(Casa casa, Pageable pageable);
}
