package com.example.demo.controller;


import com.example.demo.Util.EgovUtil;
import com.example.demo.Util.GoogleMapsUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
@AllArgsConstructor
public class MainController {
    //@PostMapping ("/{requestID}/{IIN}")

    @GetMapping("/getToken")
    public String getToken(){
        return EgovUtil.getAccessToken();
    }

    @GetMapping("/getFL/{iin}")
    public String getFL(@PathVariable String iin){
        return EgovUtil.getFL(iin, getToken());
    }

    @GetMapping("/getBMG/{iin}")
    public String getNumber(@PathVariable String iin){
        return EgovUtil.getBMG(iin, getToken());
    }

    @GetMapping("/getDocumentStatus/{requestId}/{iin}")
    public String getDocumentStatus(@PathVariable String requestId, @PathVariable String iin){
        return EgovUtil.getDocumentStatus(requestId, iin);
    }

    @GetMapping("/getInfo/{requestID}/{iin}")
    public String getInfo(@PathVariable String requestID, @PathVariable String iin){
        return EgovUtil.getInfo(requestID, iin);
    }

}
