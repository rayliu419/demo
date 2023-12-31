package com.example.utils.rulemachine.validators;

import com.example.utils.rulemachine.RuleCheckResult;
import com.example.utils.rulemachine.RuleValidator;
import org.springframework.stereotype.Component;

@Component
public class NameRuleValidator implements RuleValidator<String> {

    @Override
    public RuleCheckResult validate(String input) {
        RuleCheckResult result = new RuleCheckResult();
        if (input.isBlank()) {
            result.setCheckInfo("name is empty");
            result.setPass(false);
            return result;
        }
        result.setPass(true);
        return result;
    }


}
