package com.mherkert.nomnom.domain;

public enum ParseState {
    NEW,
    READING_DESCRIPTION,
    READING_INGREDIENTS,
    READING_INSTRUCTIONS,
    DONE
}
