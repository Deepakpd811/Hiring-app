package com.bridgelab.hiringapp.exception;


import lombok.Getter;

@Getter
public class CandidateNotFoundException extends RuntimeException {

    private final String path;
    private final Long id;

    public CandidateNotFoundException(Long id, String path) {
        super("Candidate with id" + id + " does not exist");
        this.id = id;
        this.path = path;
    }


}
