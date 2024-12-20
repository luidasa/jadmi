package mx.admino.services.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mx.admino.models.CargoConcepto;
import mx.admino.models.entities.Casa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import mx.admino.exceptions.CuotaNotFound;
import mx.admino.models.CuotaEstatus;
import mx.admino.models.entities.Cargo;
import mx.admino.models.entities.Cuota;
import mx.admino.repositories.CuotaRepository;
import mx.admino.services.CargoService;
import mx.admino.services.CasasService;
import mx.admino.services.CuotaService;

@Service
public class CuotaServiceImpl implements CuotaService {

	@Autowired
	CuotaRepository cuotaRepository;
	
	@Autowired
	CargoService cargoService;

	@Autowired
    CasasService casasService;

	
	@Override
	public Page<Cuota> findAll(Pageable pageable) {
		return cuotaRepository.findAll(pageable);
	}

	@Override
	public Cuota save(Cuota cuota) {
		return cuotaRepository.save(cuota);
	}

	@Override
	public Cuota findById(Long id) {
		return cuotaRepository.findById(id).orElseThrow(() -> new CuotaNotFound());
	}

	@Override
	public void deleteById(Long id) {
		cuotaRepository.deleteById(id);
	}

	@Override
	public void schedule(Cuota cuota) {

		casasService.findAll().stream().forEach(c -> {
			List<Cargo> cargos = this.getCargos(cuota, c);
			cargoService.saveAll(cargos);
		});
		cuota.setEstatus(CuotaEstatus.PLANEADO);
		this.save(cuota);
	}

	public List<Cargo> getCargos(Cuota cuota, Casa c) {
		List<Cargo> cargos = new ArrayList<>();
		
		Date fecha = cuota.getFechaInicio();
		while(fecha.before(cuota.getFechaFin())) {
			cargos.add(new Cargo(c, cuota.getImporte(), cuota.getImporteDesocupado(), CargoConcepto.MANTENIMIENTO, fecha));
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

	@Override
	public List<Cuota> findByEstatus(CuotaEstatus estatus) {
		return cuotaRepository.findByEstatus(estatus);
	}
}
