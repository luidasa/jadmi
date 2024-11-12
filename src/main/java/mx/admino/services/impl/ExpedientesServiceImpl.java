package mx.admino.services.impl;

import mx.admino.exceptions.ArchivoNotFound;
import mx.admino.models.entities.Archivo;
import mx.admino.models.entities.Casa;
import mx.admino.repositories.ExpedientesRepository;
import mx.admino.services.ExpedientesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ExpedientesServiceImpl implements ExpedientesService {

    @Autowired
    ExpedientesRepository expedientesRepository;

    @Override
    public Page<Archivo> findByCasa(Casa casa, Pageable pageable) {
        return expedientesRepository.findByCasa(casa, pageable);
    }

    @Override
    public Archivo insert(Archivo archivo) {
        return expedientesRepository.save(archivo);
    }

    @Override
    public Archivo findById(Long aid) {
        return expedientesRepository.findById(aid).orElseThrow(() -> new ArchivoNotFound());
    }

    @Override
    public void deleteById(Long aid) {
        expedientesRepository.deleteById(aid);
    }
}
