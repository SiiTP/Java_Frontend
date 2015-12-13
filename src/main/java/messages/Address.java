package messages;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ivan on 12.12.15.
 */
public class Address {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(0);
    private final int id;

    public Address() {
        this.id = ID_GENERATOR.incrementAndGet();
    }

    @Override
    public int hashCode() {
        return id;
    }
}
