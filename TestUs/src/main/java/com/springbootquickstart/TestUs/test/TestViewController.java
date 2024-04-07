import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import com.springbootquickstart.TestUs.test.Test;
import com.springbootquickstart.TestUs.test.TestService;

@Controller
public class TestViewController {

    @Autowired
    private TestService testService;

    @GetMapping("/teacher/view-tests")
    public String viewTests(Model model) {
        // Fetch tests from the database
        List<Test> tests = testService.getAllTests();
        // Add tests to the model
        model.addAttribute("tests", tests);
        // Return the view template
        return "view-tests";
    }
}