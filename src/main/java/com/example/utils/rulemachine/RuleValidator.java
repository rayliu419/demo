package com.example.utils.rulemachine;

public interface RuleValidator<T> {

    RuleCheckResult validate(T input);
}
