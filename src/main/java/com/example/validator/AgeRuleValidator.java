package com.example.validator;

import com.example.utils.rulemachine.RuleCheckResult;
import com.example.utils.rulemachine.RuleValidator;
import org.springframework.stereotype.Component;

@Component
public class AgeRuleValidator implements RuleValidator<Integer> {

    @Override
    public RuleCheckResult validate(Integer input) {
        RuleCheckResult result = new RuleCheckResult();

        if (input < 0) {
            result.setCheckInfo("age must bigger than 0");
            result.setPass(false);
            return result;
        }
        result.setPass(true);
        return result;
    }
}
