package framework;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Date;

import com.google.common.base.Defaults;

@SuppressWarnings("serial")
public abstract class BaseEntity<T> implements IBaseEntity<T>, Serializable {

    public abstract T getId();

    public abstract void setId(T id);

    // public abstract void validate();

    public abstract Date getDataCriacao();

    public abstract Date getDataAtualizacao();

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !this.getClass().isInstance(obj)) {
            return false;

        }

        if (super.equals(obj)) {
            return true;
        }

        @SuppressWarnings("unchecked")
        BaseEntity<T> other = (BaseEntity<T>) obj;
        return other.getId().equals(this.getId());
    }

    @Override
    public int hashCode() {
        return isTransient() ? super.hashCode() : this.getId().hashCode();
    }

    public boolean isTransient() {
        T defautValue = Defaults.defaultValue(getIdType());

        if (defautValue == null && this.getId() == null) {
            return true;
        }

        if (defautValue == null && this.getId() != null || defautValue != null && this.getId() == null) {
            return false;
        }

        return this.getId().equals(defautValue);
    }

    @SuppressWarnings("unchecked")
    private Class<T> getIdType() {
        return (Class<T>) getGenericType(0);
    }

    @SuppressWarnings("rawtypes")
    private Class getGenericType(Integer index) {
        return ((Class) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[index]);
    }

}