package nbc.sma.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchScheduleRequest {

    private LocalDate updatedAt;
    private Long userId;

    @Min(1)
    private Integer page = 1;

    @Min(1) @Max(20)
    private Integer size = 10;

    public void setUpdated_at(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setUser_id(Long userId) {
        this.userId = userId;
    }
}
