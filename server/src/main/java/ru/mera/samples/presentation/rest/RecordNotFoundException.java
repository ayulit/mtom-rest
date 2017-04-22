package ru.mera.samples.presentation.rest;

/**
 * This exception captures a missing record.
 *
 * @author Ludovic Dewailly
 */
@SuppressWarnings("serial")
public class RecordNotFoundException extends RuntimeException {

    public RecordNotFoundException(String description) {
        super(description);
    }
}
