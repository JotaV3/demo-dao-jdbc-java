package db;

public class DbIntegrityExcetion extends RuntimeException {
    public DbIntegrityExcetion(String message) {
        super(message);
    }
}
