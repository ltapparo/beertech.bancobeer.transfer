package rest;

import Dto.TransacaoDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import model.Conta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import service.ContaService;

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

    @PostMapping(path = "/{idConta}",
            consumes={MediaType.APPLICATION_JSON_VALUE},
            produces={MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value="Insere operação a conta.", produces="application/json")

    public void addTransacao(
            @ApiParam(name="idConta", required=true, value="ID de conta", example="1")
            @PathVariable String idConta,
            @ApiParam(name="request", required=true, value="Objeto com as reservas a serem criadas/atualizadas")
            @RequestBody TransacaoDto request
            ){

            contaService.save(request,idConta);

    }


    @GetMapping(path = "/{idConta}/saldos",
            consumes={MediaType.APPLICATION_JSON_VALUE},
            produces={MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value="Retorna.", produces="application/json")
    public void getSaldo(
            @ApiParam(name="idConta", required=true, value="ID de conta", example="1")
            @PathVariable String idConta
    ){

        contaService.getSaldo(idConta);

    }

}
