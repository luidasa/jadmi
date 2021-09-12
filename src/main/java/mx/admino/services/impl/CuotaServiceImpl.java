package mx.admino.services.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import mx.admino.exceptions.CuotaNotFound;
import mx.admino.models.Cargo;
import mx.admino.models.Condomino;
import mx.admino.models.Cuota;
import mx.admino.models.CuotaStatus;
import mx.admino.repositories.CuotaRepository;
import mx.admino.services.CargoService;
import mx.admino.services.CondominoService;
import mx.admino.services.CuotaService;

@Service
public class CuotaServiceImpl implements CuotaService {

	@Autowired
	CuotaRepository cuotaRepository;
	
	@Autowired
	CargoService cargoService;

	@Autowired
	CondominoService condominoService;

	
	@Override
	public Page<Cuota> findAll(Pageable pageable) {
		return cuotaRepository.findAll(pageable);
	}

	@Override
	public Cuota save(Cuota cuota) {
		return cuotaRepository.save(cuota);
	}

	@Override
	public Cuota findById(String id) {
		return cuotaRepository.findById(id).orElseThrow(() -> new CuotaNotFound());
	}

	@Override
	public void deleteById(String id) {
		cuotaRepository.deleteById(id);
	}

	@Override
	public void schedule(Cuota cuota) {

		condominoService.findAll().stream().forEach(c -> {
			List<Cargo> cargos = this.getCargos(cuota, c);
			cargoService.saveAll(cargos);
		});
		cuota.setStatus(CuotaStatus.PLANEADO);
		this.save(cuota);
	}

	public List<Cargo> getCargos(Cuota cuota, Condomino c) {
		List<Cargo> cargos = new ArrayList<>();
		
		Date fecha = cuota.getFechaFin();
		while(fecha.before(cuota.getFechaFin())) {
			cargos.add(new Cargo(c, cuota.getImporte(), cuota.getNombre(), fecha));
			fecha = addMonths(fecha, 1);
		}
		
		return cargos;
	}

	public Date addMonths(Date date, int numMonths) {
        if (date != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.MONTH, numMonths);
            return c.getTime();
        } else {
            return null;
        }
	}
}
