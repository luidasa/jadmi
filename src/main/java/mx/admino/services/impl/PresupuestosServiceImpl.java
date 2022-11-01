package mx.admino.services.impl;

import mx.admino.models.entities.Condominio;
import mx.admino.models.entities.Presupuesto;
import mx.admino.repositories.PresupuestosRepository;
import mx.admino.services.PresupuestosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PresupuestosServiceImpl implements PresupuestosService {

    @Autowired
    PresupuestosRepository presupuestosRepository;

    @Override
    public Page<Presupuesto> findByCondominio(Condominio condominio, Pageable pageable) {
        return presupuestosRepository.findByCondominio(condominio, pageable);
    }
}
