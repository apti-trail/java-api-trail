package br.com.fiap.application.service;

import br.com.fiap.domain.model.Modulo;
import br.com.fiap.domain.repository.ModuloRepository;
import br.com.fiap.domain.service.ModuloService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ModuloServiceImpl implements ModuloService {

    @Inject
    ModuloRepository moduloRepository;

    @Override
    public Modulo salvarModulo(Modulo modulo) {
        return moduloRepository.salvarModulo(modulo);
    }

    @Override
    public void excluir(Modulo modulo) {
        moduloRepository.excluir(modulo);
    }

    @Override
    public List<Modulo> listarTodos() {
        return moduloRepository.listarTodos();
    }

    @Override
    public Optional<Modulo> buscarPorId(Long id) {
        return moduloRepository.buscarPorId(id);
    }

    @Override
    public List<Modulo> buscarPorTrilha(Long trilhaId) {
        return listarTodos().stream()
                .filter(modulo -> modulo.getTrilha().getIdTrilha().equals(trilhaId))
                .toList();
    }

    @Override
    public void marcarComoConcluido(Long moduloId) {
        Optional<Modulo> moduloOpt = buscarPorId(moduloId);
        if (moduloOpt.isPresent()) {
            Modulo modulo = moduloOpt.get();
            modulo.setConcluido(true);
            modulo.setDataConclusao(LocalDate.now());
            salvarModulo(modulo);
        }
    }

    @Override
    public void marcarComoNaoConcluido(Long moduloId) {
        Optional<Modulo> moduloOpt = buscarPorId(moduloId);
        if (moduloOpt.isPresent()) {
            Modulo modulo = moduloOpt.get();
            modulo.setConcluido(false);
            modulo.setDataConclusao(null);
            salvarModulo(modulo);
        }
    }
}
