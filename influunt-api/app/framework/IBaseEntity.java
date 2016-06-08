package framework;

import java.util.Date;

public interface IBaseEntity<T> {

	public T getId();

	public void setId(T id);
	
//	public void validate();
	
	public Date getDataCriacao();
	
	public Date getDataAtualizacao();
	
}
