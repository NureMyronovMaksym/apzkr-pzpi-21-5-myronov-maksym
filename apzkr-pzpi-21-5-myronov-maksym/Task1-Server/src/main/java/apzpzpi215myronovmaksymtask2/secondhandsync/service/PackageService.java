package apzpzpi215myronovmaksymtask2.secondhandsync.service;

import apzpzpi215myronovmaksymtask2.secondhandsync.exception.DatabaseException;
import apzpzpi215myronovmaksymtask2.secondhandsync.exception.LocationException;
import apzpzpi215myronovmaksymtask2.secondhandsync.exception.PackageException;
import apzpzpi215myronovmaksymtask2.secondhandsync.model.dto.PackageDTO;
import apzpzpi215myronovmaksymtask2.secondhandsync.model.dto.PackagesDTO;
import apzpzpi215myronovmaksymtask2.secondhandsync.model.entity.Package;
import apzpzpi215myronovmaksymtask2.secondhandsync.repository.LocationRepository;
import apzpzpi215myronovmaksymtask2.secondhandsync.repository.PackageRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PackageService {

    private final PackageRepository packageRepository;
    private final LocationRepository locationRepository;

    public PackageService(PackageRepository packageRepository, LocationRepository locationRepository) {
        this.packageRepository = packageRepository;
        this.locationRepository = locationRepository;
    }

    public void addPackage(PackageDTO packageDTO) throws DatabaseException, LocationException {
        Package aPackage = convertDTOToEntity(packageDTO);

        try {
            packageRepository.save(aPackage);
        } catch (DataAccessException e) {
            throw new DatabaseException(DatabaseException.DatabaseExceptionProfile.DATABASE_ERROR);
        }
    }

    public void updatePackageStatus(Long packageId, PackageDTO packageDTO) throws PackageException, DatabaseException{
        Package existingPackage = packageRepository.findById(packageId).orElseThrow(
                () -> new PackageException(PackageException.PackageExceptionProfile.PACKAGE_NOT_FOUND));

        existingPackage.setStatus(packageDTO.getStatus());

        try {
            packageRepository.save(existingPackage);
        } catch (DataAccessException e) {
            throw new DatabaseException(DatabaseException.DatabaseExceptionProfile.DATABASE_ERROR);
        }
    }

    public PackagesDTO getAllPackages() throws PackageException {
        List<Package> packages;
        try {
            packages = packageRepository.findAll();
        } catch (Exception e) {
            throw new PackageException(PackageException.PackageExceptionProfile.PACKAGES_NOT_FOUND);
        }

        List<PackageDTO> packageDTOs = new ArrayList<>();
        for (Package aPackage: packages) {
            packageDTOs.add(convertEntityToDTO(aPackage));
        }

        PackagesDTO packagesDTO = new PackagesDTO();
        packagesDTO.setPackagesDTO(packageDTOs);
        return packagesDTO;
    }

    public void deletePackage(Long packageId) throws DatabaseException, PackageException {
        Package aPackage;
        try {
            aPackage = packageRepository.findById(packageId).get();
        } catch (Exception e) {
            throw new PackageException(PackageException.PackageExceptionProfile.PACKAGE_NOT_FOUND);
        }

        try {
            packageRepository.delete(aPackage);
        } catch (DataAccessException e) {
            throw new DatabaseException(DatabaseException.DatabaseExceptionProfile.DATABASE_ERROR);
        }
    }

    private PackageDTO convertEntityToDTO(Package aPackage) {
        PackageDTO packageDTO = new PackageDTO();
        packageDTO.setId(aPackage.getId());
        packageDTO.setStatus(aPackage.getStatus());
        packageDTO.setItemsAmount(aPackage.getItemsAmount());
        if (aPackage.getLocation() != null) {
            packageDTO.setLocationId(aPackage.getLocation().getId());
        }
        return packageDTO;
    }

    public Package convertDTOToEntity(PackageDTO packageDTO) throws LocationException {
        Package aPackage = new Package();

        aPackage.setId(packageDTO.getId());
        aPackage.setStatus(packageDTO.getStatus());
        aPackage.setItemsAmount(packageDTO.getItemsAmount());

        try {
            if (packageDTO.getLocationId() != null)
                aPackage.setLocation(locationRepository.findById(packageDTO.getLocationId()).get());
        } catch (Exception e) {
            throw new LocationException(LocationException.LocationExceptionProfile.LOCATION_NOT_FOUND);
        }

        return aPackage;
    }

}
