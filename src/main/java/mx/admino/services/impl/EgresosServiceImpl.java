package mx.admino.services.impl;

import mx.admino.models.entities.Condominio;
import mx.admino.models.entities.Egreso;
import mx.admino.repositories.GastosRepository;
import mx.admino.services.EgresosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EgresosServiceImpl implements EgresosService {

    @Autowired
    GastosRepository gastosRepository;

    @Override
    public Page<Egreso> findByCondominio(Condominio condominio, Pageable pageable) {

        return gastosRepository.findByCondominio(condominio, pageable);
    }

    @Override
    public Egreso crear(Egreso egreso) {

        /// TODO. cuando se comience a registrar el saldo en la caja o en cuenta de banco vamos a tener que afectar esa caja de forma transaccional.
        return gastosRepository.save(egreso);
    }
}
