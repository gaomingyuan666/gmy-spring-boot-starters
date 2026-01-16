package io.github.gaomingyuan666.base.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gaomingyuan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdRequest {
    @NotNull
    private Integer id;
}
