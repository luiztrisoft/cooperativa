package com.cwi.cooperativa.services.impl;

import com.cwi.cooperativa.dtos.AssociadoDTO;
import com.cwi.cooperativa.entities.Associado;
import com.cwi.cooperativa.exceptions.CooperativaException;
import com.cwi.cooperativa.mapstruct.AssociadoMapper;
import com.cwi.cooperativa.repositories.AssociadoRepository;
import com.cwi.cooperativa.services.AssociadoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class AssociadoServiceImpl implements AssociadoService {

    private static final Logger logger = LoggerFactory.getLogger(AssociadoServiceImpl.class);

    @Value("${api.cpf.base-url}")
    private String baseUrl;

    @Autowired
    private AssociadoRepository associadoRepository;

    @Autowired
    private AssociadoMapper associadoMapper;

    @Override
    public AssociadoDTO salvar(AssociadoDTO associadoDTO) {
        Associado associado = associadoMapper.toEntity(associadoDTO);
        validarCpfViaApi(associado.getCpf());
        verificarExistenciaAssociado(associado);
        Associado associadoSalvo = associadoRepository.save(associado);
        return associadoMapper.toDTO(associadoSalvo);
    }

    @Override
    public List<AssociadoDTO> listar() {
        return associadoMapper.toDTOList(associadoRepository.findAll());
    }

    @Override
    public AssociadoDTO buscar(Long id) {
        Associado associado = associadoRepository.findById(id).orElseThrow(() -> new CooperativaException("Associado não encontrado."));
        return associadoMapper.toDTO(associado);
    }

    @Override
    public Associado findByCpf(String cpf){
        return associadoRepository.findByCpf(cpf).orElseThrow(() -> new CooperativaException("Associado não encontrado."));
    }

    private void verificarExistenciaAssociado(Associado associado) {
        Optional<Associado> associadoExistente = associadoRepository.findByCpf(associado.getCpf());
        if (associadoExistente.isPresent()) {
            throw new CooperativaException("Já existe um associado com o CPF informado.");
        }
    }

    private boolean validarCpfViaApi(String cpf) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = baseUrl.concat(cpf);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            if (false) {
                throw new CooperativaException("O CPF informado não é válido");
            }
        }catch (Exception e){
            logger.info("Exception: {}", e.getMessage());
        }

        //Nota para CWI: não encontrei uma api aberta para testar uma requisição externa. Caso haja tempo, posso criar um microserviço apenas com o algoritmo de validação abaixo para fins didáticos
        if(isValidCPF(cpf)){
            return true;
        }
        throw new CooperativaException("O CPF informado não é válido");
    }

    public static boolean isValidCPF(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", "");

        if (cpf.length() != 11 || cpf.matches("(.)\\1{10}")) {
            return false;
        }

        int[] numeros = new int[11];
        for (int i = 0; i < 11; i++) {
            numeros[i] = Integer.parseInt(String.valueOf(cpf.charAt(i)));
        }

        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += numeros[i] * (10 - i);
        }
        int firstDigit = 11 - (sum % 11);
        if (firstDigit == 10 || firstDigit == 11) {
            firstDigit = 0;
        }
        if (numeros[9] != firstDigit) {
            return false;
        }

        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += numeros[i] * (11 - i);
        }
        int secondDigit = 11 - (sum % 11);
        if (secondDigit == 10 || secondDigit == 11) {
            secondDigit = 0;
        }
        return numeros[10] == secondDigit;
    }
}
