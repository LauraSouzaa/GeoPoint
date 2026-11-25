package br.edu.atitus.geopoint.services;

import br.edu.atitus.geopoint.dtos.PointRequest;
import br.edu.atitus.geopoint.entities.PointEntity;
import br.edu.atitus.geopoint.repositories.PointRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class PointService {
    public PointEntity insert(PointRequest pointRequest) {
        if (pointRequest == null)
            throw new IllegalArgumentException("Dados do ponto não informados.");
        if (pointRequest.name() == null || pointRequest.name().isBlank())
            throw new IllegalArgumentException("O nome é obrigatório e não pode ficar em branco.");
        if (pointRequest.name().trim().length() > 100)
            throw new IllegalArgumentException("O nome deve ter no máximo 100 caracteres.");
        if (pointRequest.description() != null && pointRequest.description().trim().length() > 255)
            throw new IllegalArgumentException("A descrição deve ter no máximo 255 caracteres.");
        if (pointRequest.latitude() == null || pointRequest.latitude().compareTo(BigDecimal.valueOf(-90)) < 0 || pointRequest.latitude().compareTo(BigDecimal.valueOf(90)) > 0)
            throw new IllegalArgumentException("Latitude inválida - Intervalo aceito: -90 a 90");
        if (pointRequest.longitude() == null || pointRequest.longitude().compareTo(BigDecimal.valueOf(-180)) < 0 || pointRequest.longitude().compareTo(BigDecimal.valueOf(180)) > 0)
            throw new IllegalArgumentException("Longitude inválida - Intervalo aceito: -180 a 180");
        if (repository.existsByName(pointRequest.name().trim()))
            throw new IllegalArgumentException("Já existe um ponto com esse nome.");

        PointEntity point = new PointEntity();

        point.setId(UUID.randomUUID());
        point.setName(pointRequest.name().trim());
        point.setDescription(pointRequest.description() == null ? "" : pointRequest.description().trim());
        point.setLatitude(pointRequest.latitude());
        point.setLongitude(pointRequest.longitude());
        return repository.insert(point);
    }
    private final PointRepository repository;
    public PointService(PointRepository repository) {
        this.repository = repository;
    }
    public List<PointEntity> findAll() {
        return repository.findAll();
    }
    public PointEntity findById(UUID id) {
        PointEntity point = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Ponto não localizado com o ID: " + id));
        return point;
    }
    public PointEntity update(UUID id, PointRequest pointRequest) {
        findById(id);

        if (pointRequest == null)
            throw new IllegalArgumentException("Dados do ponto não informados.");
        if (pointRequest.name() == null || pointRequest.name().isBlank())
            throw new IllegalArgumentException("O nome é obrigatório e não pode ficar em branco.");
        if (pointRequest.name().trim().length() > 100)
            throw new IllegalArgumentException("O nome deve ter no máximo 100 caracteres.");
        if (pointRequest.description() != null && pointRequest.description().trim().length() > 255)
            throw new IllegalArgumentException("A descrição deve ter no máximo 255 caracteres.");
        if (pointRequest.latitude() == null || pointRequest.latitude().compareTo(BigDecimal.valueOf(-90)) < 0 || pointRequest.latitude().compareTo(BigDecimal.valueOf(90)) > 0)
            throw new IllegalArgumentException("Latitude inválida - Intervalo aceito: -90 a 90");
        if (pointRequest.longitude() == null || pointRequest.longitude().compareTo(BigDecimal.valueOf(-180)) < 0 || pointRequest.longitude().compareTo(BigDecimal.valueOf(180)) > 0)
            throw new IllegalArgumentException("Longitude inválida - Intervalo aceito: -180 a 180");

        if (repository.existsByNameAndIdNot(pointRequest.name().trim(), id))
            throw new IllegalArgumentException("Já existe um ponto com esse nome.");

        PointEntity point = new PointEntity();

        point.setId(id);
        point.setName(pointRequest.name().trim());
        point.setDescription(pointRequest.description() == null ? "" : pointRequest.description().trim());
        point.setLatitude(pointRequest.latitude());
        point.setLongitude(pointRequest.longitude());

        repository.update(point);
        return point;
    }
    public void deleteById(UUID id) {
        findById(id);
        repository.deleteById(id);
    }
}
