package io.github.gaomingyuan666.base.model.template;

import java.util.List;

public interface ConsumerTemplate {
    void accept(List<String> lines) throws Exception;
}
