import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(features= "classpath:features",
        glue="StepDefinition",
        plugin = {"pretty","html:build/CucumberReports" },monochrome = true,
        tags = {""}
        )
public class FxApiRunner {
}
