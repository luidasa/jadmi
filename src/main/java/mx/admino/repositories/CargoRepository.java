package mx.admino.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import mx.admino.models.Cargo;
import mx.admino.models.CargoEstatus;

@Repository
public interface CargoRepository extends MongoRepository<Cargo, String> {

	Page<Cargo> findByCondomino_Id(String id, Pageable pageable);

	List<Cargo> findByCondomino_IdAndFechaVencimientoBetween(String cid, Date fechaCorte, Date fechaVencimiento);

	List<Cargo> findByFechaVencimientoBetween(Date fechaCorte, Date fechaFinal);

	List<Cargo> findByFechaVencimientoBeforeAndEstatus(Date fechaCorte, CargoEstatus estatus);

}
