package validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Validated
@Component
public class ReportTask {
	@Pattern(regexp = "[0-3]") 
	public String createReport(@NotNull @Size(min = 3, max = 20) String name,@NotNull LocalDateTime startDate) {
		return "-1";
	}
}