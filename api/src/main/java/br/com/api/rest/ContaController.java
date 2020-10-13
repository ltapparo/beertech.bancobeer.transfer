package br.com.api.rest;

import br.com.api.dto.TransacaoDto;
import br.com.api.dto.TransferenciaDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import br.com.api.model.Conta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import br.com.api.service.ContaService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/contas")
public class ContaController {

    @Autowired
    ContaService contaService;

    @GetMapping(produces={MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value="Lista as contas disponíveis.", produces="application/json")
    public List<Conta> listAll(){
        return contaService.listAll();
    }

    @PostMapping(path = "/{hashId}",
            consumes={MediaType.APPLICATION_JSON_VALUE},
            produces={MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value="Insere operação a conta.", produces="application/json")
    public Conta addTransacao(
            @ApiParam(name="hashId", required=true, value="ID hash da conta", example="d333caa8477e0ad6c22c1d594b05fa668eee01652f9297c71b3bfdf9cb98c197")
            @PathVariable String hashId,
            @ApiParam(name="request", required=true, value="Objeto com as reservas a serem criadas/atualizadas")
            @Valid @RequestBody TransacaoDto request
            ){

            return contaService.saveOperacao(request,hashId);

    }

    @GetMapping(path = "/{hashId}/saldos",
            produces={MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value="Retorna.", produces="application/json")
    public Conta getSaldo(@ApiParam(name="hashId", required=true, value="ID hash de conta", example="d333caa8477e0ad6c22c1d594b05fa668eee01652f9297c71b3bfdf9cb98c197") @PathVariable String hashId){
        return contaService.findByHashId(hashId);
    }

  @PostMapping(path = "/{hashId}/transferencia",
      consumes={MediaType.APPLICATION_JSON_VALUE},
      produces={MediaType.APPLICATION_JSON_VALUE})
  @ApiOperation(value="Insere operação a conta.", produces="application/json")
  public Conta addTransacao(
      @ApiParam(name="hashId", required=true, value="ID hash da conta", example="d333caa8477e0ad6c22c1d594b05fa668eee01652f9297c71b3bfdf9cb98c197")
      @PathVariable String hashId,
      @ApiParam(name="request", required=true, value="Objeto com as reservas a serem criadas/atualizadas")
      @Valid @RequestBody TransferenciaDto request
  ){

    return contaService.saveTransferencia(request,hashId);

  }

}
