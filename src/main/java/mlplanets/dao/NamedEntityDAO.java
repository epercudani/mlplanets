package mlplanets.dao;

public interface NamedEntityDAO<T> extends DAO<T> {

    T findByName(String name);
}
