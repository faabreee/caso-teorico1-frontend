package edu.cibertec.pe.caso_teorico1_frontend.controller;

import edu.cibertec.pe.caso_teorico1_frontend.dto.RueditasRequestDTO;
import edu.cibertec.pe.caso_teorico1_frontend.dto.RueditasResponseDTO;
import edu.cibertec.pe.caso_teorico1_frontend.viewmodel.RueditasModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/rueditas")
public class RueditasController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/buscar")
    public String buscar(Model model) {
        RueditasModel rueditasModel = new RueditasModel("00", "", "");
        model.addAttribute("rueditasModel", rueditasModel);
        return "buscar";
    };


    @PostMapping("/validar")
    public String validar(@RequestParam("placa") String placa,
                          Model model) {

        if (    placa.equals("") ||
                !placa.matches("[a-zA-Z0-9\\-]+") ||
                placa.trim().length() != 8 )
        {

            RueditasModel rueditasModel = new RueditasModel("01", "Debe ingresar una placa correcta ", "");
            model.addAttribute("rueditasModel", rueditasModel);
            return "buscar";
        }

        try{
            RueditasRequestDTO rueditasRequestDTO = new RueditasRequestDTO(placa);
            RueditasResponseDTO rueditasResponseDTO = restTemplate.postForObject("http://localhost:8082/validacion/vehiculo", rueditasRequestDTO, RueditasResponseDTO.class);

            if (!rueditasResponseDTO.id().equals("0")) {

                model.addAttribute("rueditasResponseDTO", rueditasResponseDTO);
                return "detalle";

            } else {
                RueditasModel rueditasModel = new RueditasModel("00", "Vehiculo " + rueditasRequestDTO.placa() + " no encontrado", rueditasRequestDTO.placa());
                model.addAttribute("rueditasModel", rueditasModel);
                return "buscar";
            }
        }
        catch (Exception e){

            RueditasModel rueditasModel = new RueditasModel("99", "Surgió un error en la autenticación", "");
            model.addAttribute("rueditasModel", rueditasModel);
            return "buscar";
        }
    };
}
