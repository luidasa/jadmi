package mx.admino.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.admino.models.CuotaEstatus;
import mx.admino.models.entities.Cuota;

@Repository
public interface CuotaRepository extends JpaRepository<Cuota, Long> {

	List<Cuota> findByEstatus(CuotaEstatus estatus);

}
