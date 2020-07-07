package entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.util.concurrent.ConcurrentHashMap;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Statistics {

    @NonNull
    private String link;
    private ConcurrentHashMap<String, Long> statistics;
}
