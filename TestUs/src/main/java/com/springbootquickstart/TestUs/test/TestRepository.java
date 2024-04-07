import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.springbootquickstart.TestUs.test.Test;

@Repository
public interface TestRepository extends JpaRepository<Test, Integer> {
    // You can add custom query methods here if needed
}