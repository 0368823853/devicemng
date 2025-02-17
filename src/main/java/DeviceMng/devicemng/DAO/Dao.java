package DeviceMng.devicemng.DAO;

import java.util.List;
import java.util.Optional;

public interface Dao<E, UUID> {
    Optional<E> findById(UUID id);
    List<E> findAll(String searchText);
    E save(E entity);
    void deleteById(UUID id);
}
