package br.edu.atitus.geopoint.controllers;

import br.edu.atitus.geopoint.dtos.PointRequest;
import br.edu.atitus.geopoint.entities.PointEntity;
import br.edu.atitus.geopoint.services.PointService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/api/points")
@CrossOrigin(origins = "*")
public class PointController {

    @PostMapping
    public ResponseEntity<PointEntity> insertPoint(@RequestBody PointRequest pointRequest) {
        PointEntity point = service.insert(pointRequest);
        return ResponseEntity.status(201).body(point);
    }
    private final PointService service;

    public PointController(PointService service) {
        this.service = service;
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNotFoundException(NoSuchElementException exception) {
        return ResponseEntity.status(404).body(exception.getMessage());
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleRuntimeException(IllegalArgumentException exception) {
        return ResponseEntity.status(400).body(exception.getMessage());
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleJsonException(HttpMessageNotReadableException exception) {
        return ResponseEntity.status(400).body("Dados inválidos ou ausentes. Verifique o JSON enviado.");
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleInvalidIdException(MethodArgumentTypeMismatchException exception) {
        return ResponseEntity.status(400).body("ID inválido. Informe um UUID válido.");
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception exception) {
        return ResponseEntity.status(500).body("Erro interno do servidor.");
    }

    @GetMapping
    public ResponseEntity<List<PointEntity>> findAll() {
        List<PointEntity> response = service.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PointEntity> findById(@PathVariable UUID id) {
        PointEntity response = service.findById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PointEntity> updatePoint(@PathVariable UUID id, @RequestBody PointRequest pointRequest) {
        PointEntity response = service.update(id, pointRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePoint(@PathVariable UUID id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
