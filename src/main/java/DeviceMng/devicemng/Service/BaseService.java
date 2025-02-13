package DeviceMng.devicemng.Service;

import java.util.List;

public interface BaseService <T, UUID> {
    List<T> getAll();
    T getById(UUID id);
    T save(T entity);
    void delete(UUID id);
}
