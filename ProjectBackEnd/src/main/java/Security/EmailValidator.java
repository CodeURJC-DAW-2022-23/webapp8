package Security;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;
import java.util.regex.*;

@Service
public class EmailValidator implements Predicate<String> {
    private String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    @Override
    public boolean test(String s) {
        return Pattern.compile(regexPattern).matcher(s) .matches();
    }

}
