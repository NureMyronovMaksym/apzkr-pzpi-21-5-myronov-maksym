package apzpzpi215myronovmaksymtask2.secondhandsync.controller;

import apzpzpi215myronovmaksymtask2.secondhandsync.exception.DatabaseException;
import apzpzpi215myronovmaksymtask2.secondhandsync.exception.LocationException;
import apzpzpi215myronovmaksymtask2.secondhandsync.exception.PackageException;
import apzpzpi215myronovmaksymtask2.secondhandsync.model.dto.PackageDTO;
import apzpzpi215myronovmaksymtask2.secondhandsync.model.dto.PackagesDTO;
import apzpzpi215myronovmaksymtask2.secondhandsync.service.PackageService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/package-manager")
public class PackageController {

    private final PackageService packageService;

    public PackageController(PackageService packageService) {
        this.packageService = packageService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/setPackage")
    public ResponseEntity<?> setPackage(@RequestBody PackageDTO packageDTO) throws DatabaseException, LocationException {
        packageService.addPackage(packageDTO);
        return ResponseEntity.ok("Package added successfully");
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PutMapping("/{packageId}/setStatus")
    public ResponseEntity<?> updatePackageStatus(@PathVariable Long packageId, @RequestBody PackageDTO packageDTO) throws DatabaseException, PackageException {
        packageService.updatePackageStatus(packageId, packageDTO);
        return ResponseEntity.ok("Package status updated successfully");
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @GetMapping("/getPackages")
    public ResponseEntity<PackagesDTO> getAllPackages() throws PackageException {
        return ResponseEntity.ok(packageService.getAllPackages());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{packageId}")
    public ResponseEntity<?> deletePackage(@PathVariable Long packageId) throws DatabaseException, PackageException {
        packageService.deletePackage(packageId);
        return ResponseEntity.ok("Package deleted successfully");
    }
}
