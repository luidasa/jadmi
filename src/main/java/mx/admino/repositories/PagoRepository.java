package mx.admino.repositories;

import java.util.Date;
import java.util.List;

import mx.admino.models.entities.Casa;
import mx.admino.models.entities.Condominio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.admino.models.PagoEstatus;
import mx.admino.models.entities.Pago;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {

	List<Pago> findByCasaAndFechaPagadoBetween(Casa casa, Date fechaInicial, Date fechaFinal);

    Page<Pago> findByCasa(Casa casa, Pageable pageable);

    List<Pago> findByCasa(Casa casa);

    Page<Pago> findByCondominio(Condominio condominio, Pageable pageable);

    List<Pago> findByFechaPagadoGreaterThanAndLessThanAndEstatus(Date fechaInicio, Date fechaFinal, PagoEstatus estatus);

    List<Pago> findByFechaPagadoGreaterThanAndLessThan(Date fechaInicio, Date fechaFinal);

    List<Pago> findByFechaPagadoGraterThanAndEstatus(Date fechaFinal, PagoEstatus estatus);
}
