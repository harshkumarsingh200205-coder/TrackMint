package com.trackmint.exception;

public class TrackMintException extends RuntimeException {
    public TrackMintException(String message) {
        super(message);
    }

    public TrackMintException(String message, Throwable cause) {
        super(message, cause);
    }
}
