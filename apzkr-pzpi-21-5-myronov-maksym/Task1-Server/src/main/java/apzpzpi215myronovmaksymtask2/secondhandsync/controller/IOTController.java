package apzpzpi215myronovmaksymtask2.secondhandsync.controller;

import apzpzpi215myronovmaksymtask2.secondhandsync.model.dto.PackageDTO;
import apzpzpi215myronovmaksymtask2.secondhandsync.model.dto.PackageIOTDTO;
import apzpzpi215myronovmaksymtask2.secondhandsync.service.IOTService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/iot-service")
public class IOTController {

    private final IOTService IoTService;

    public IOTController(IOTService IoTService) {
        this.IoTService = IoTService;
    }

    @PostMapping("/package")
    public ResponseEntity<?> receivePackageData(@RequestBody PackageIOTDTO packageIOTDTO) {
        try {
            IoTService.processPackageData(packageIOTDTO);
            return ResponseEntity.ok("Package data processed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error processing package data");
        }
    }
}
