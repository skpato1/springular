package com.sifast.springular.framework.business.logic.common;


import java.io.IOException;
import java.util.Objects;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.databind.ObjectMapper;



public class CustomMatcher extends TypeSafeMatcher<HttpClientErrorException> {

    private String foundError;

    private HttpStatus foundStatusCode;

    private final String expectedError;

    private final HttpStatus expectedStatusCode;

    public static CustomMatcher hasError(String expectedError, HttpStatus expectedStatusCode) {
        return new CustomMatcher(expectedError, expectedStatusCode);
    }

    private CustomMatcher(String expectedError, HttpStatus expectedStatusCode) {
        this.expectedError = expectedError;
        this.expectedStatusCode = expectedStatusCode;
    }

    @Override
    protected boolean matchesSafely(final HttpClientErrorException exception) {
        foundStatusCode = exception.getStatusCode();
        ObjectMapper mapper = new ObjectMapper();
        try {
            HttpErrorResponse error = mapper.readValue(exception.getResponseBodyAsString(), HttpErrorResponse.class);
            foundError = error.getMessage();

        } catch (IOException e) {
            foundError = null;
        }
        if (foundError != null) {
            return foundError.equals(expectedError) && foundStatusCode.equals(expectedStatusCode);
        } else {
            return foundStatusCode.equals(expectedStatusCode);
        }
    }

    @Override
    public void describeTo(Description description) {
        if (!Objects.equals(expectedError, foundError)) {
            description.appendText("found Error ").appendValue(foundError).appendText(" was not found instead of ").appendValue(expectedError);
        }
        if (!expectedStatusCode.equals(foundStatusCode)) {
            description.appendText("found Status ").appendValue(foundStatusCode).appendText(" was not found instead of ").appendValue(expectedStatusCode);
        }
    }

}

