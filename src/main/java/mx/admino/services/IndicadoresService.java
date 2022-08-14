package mx.admino.services;

import java.util.Date;
import java.util.List;

import mx.admino.models.entities.Resumen;

public interface IndicadoresService {

	List<Resumen> findByPeriod(Date inicio, Date fin);

}
