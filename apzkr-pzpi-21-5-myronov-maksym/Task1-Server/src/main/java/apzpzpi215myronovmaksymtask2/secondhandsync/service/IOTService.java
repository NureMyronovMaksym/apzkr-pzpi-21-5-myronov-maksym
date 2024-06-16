package apzpzpi215myronovmaksymtask2.secondhandsync.service;

import apzpzpi215myronovmaksymtask2.secondhandsync.exception.DatabaseException;
import apzpzpi215myronovmaksymtask2.secondhandsync.exception.LocationException;
import apzpzpi215myronovmaksymtask2.secondhandsync.model.dto.ContainerIOTDTO;
import apzpzpi215myronovmaksymtask2.secondhandsync.model.dto.PackageDTO;
import apzpzpi215myronovmaksymtask2.secondhandsync.model.dto.PackageIOTDTO;
import apzpzpi215myronovmaksymtask2.secondhandsync.model.entity.Package;
import apzpzpi215myronovmaksymtask2.secondhandsync.repository.PackageRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class IOTService {

    private final PackageRepository packageRepository;

    public IOTService(PackageRepository packageRepository) {
        this.packageRepository = packageRepository;
    }

    public void processPackageData(PackageIOTDTO packageDTO) throws DatabaseException {
        System.out.println("kak ti?");
        Package aPackage = convertDTOToEntity(packageDTO);

        try {
            packageRepository.save(aPackage);
        } catch (DataAccessException e) {
            throw new DatabaseException(DatabaseException.DatabaseExceptionProfile.DATABASE_ERROR);
        }
    }

    public Package convertDTOToEntity(PackageIOTDTO packageIOTDTO) {
        Package aPackage = new Package();

        aPackage.setVolume(packageIOTDTO.getVolume());
        aPackage.setTemperature(packageIOTDTO.getTemperature());
        System.out.println(packageIOTDTO.getVolume());
        System.out.println(packageIOTDTO.getTemperature());
        return aPackage;
    }
}
