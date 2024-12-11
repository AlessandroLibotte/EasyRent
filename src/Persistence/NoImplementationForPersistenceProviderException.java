package Persistence;

public class NoImplementationForPersistenceProviderException extends RuntimeException {

    public NoImplementationForPersistenceProviderException(PersistenceProvider provider, Exception e) {
        super("cannot instantiate factory for provider "+ provider.getName(), e);
    }

}
