package systemagenda.com.dev.service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import systemagenda.com.dev.entity.Cliente;
import systemagenda.com.dev.entity.Sessao;
import systemagenda.com.dev.entity.Tratamento;
import systemagenda.com.dev.repository.ClienteRepository;
import systemagenda.com.dev.repository.SessaoRepository;
import systemagenda.com.dev.repository.TratamentoRepository;

@Service
public class RelatorioService {

    private final ClienteRepository clienteRepository;
    private final TratamentoRepository tratamentoRepository;
    private final SessaoRepository sessaoRepository;

    public RelatorioService(ClienteRepository clienteRepository,
                            TratamentoRepository tratamentoRepository,
                            SessaoRepository sessaoRepository) {
        this.clienteRepository = clienteRepository;
        this.tratamentoRepository = tratamentoRepository;
        this.sessaoRepository = sessaoRepository;
    }

    @Transactional(readOnly = true)
    public byte[] gerarRelatorioTratamentosPorCliente(UUID clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        List<Tratamento> tratamentos = tratamentoRepository.findByClienteOrderByAreaTratamentoAsc(cliente);

        JRBeanCollectionDataSource dsTratamentos = new JRBeanCollectionDataSource(tratamentos);

        try (InputStream jasperStream = getClass()
                .getResourceAsStream("/relatorios/tratamentos.jasper")) {

            if (jasperStream == null) {
                throw new IllegalStateException("Arquivo relatorios/tratamentos.jasper não encontrado no classpath");
            }

            Map<String, Object> params = new HashMap<>();
            params.put("CLIENTE_NOME", cliente.getNome());
            params.put("CLIENTE_ID", cliente.getId().toString());

            InputStream subJasper = getClass().getResourceAsStream("/relatorios/sessao_sub.jasper");
            if (subJasper == null) {
                throw new IllegalStateException("Arquivo relatorios/sessao_sub.jasper não encontrado no classpath");
            }
            params.put("SUBREPORT_INPUT_STREAM", subJasper);

            List<Sessao> sessoesDoCliente = sessaoRepository
                    .findAll()
                    .stream()
                    .filter(s -> s.getTratamento().getCliente().getId().equals(clienteId))
                    .toList();
            JRBeanCollectionDataSource dsSessoes = new JRBeanCollectionDataSource(sessoesDoCliente);
            params.put("SUBREPORT_DATA_SOURCE", dsSessoes);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperStream, params, dsTratamentos);

            return JasperExportManager.exportReportToPdf(jasperPrint);

        } catch (JRException e) {
            throw new RuntimeException("Erro ao gerar relatório Jasper", e);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar arquivos de relatório", e);
        }
    }
}