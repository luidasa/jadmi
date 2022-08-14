package mx.admino.services.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import mx.admino.models.entities.Resumen;
import mx.admino.services.IndicadoresService;

@Service
public class IndicadoresServiceImpl implements IndicadoresService {

	@Override
	public List<Resumen> findByPeriod(Date inicio, Date fin) {
		// TODO Auto-generated method stub
		return null;
	}

}
