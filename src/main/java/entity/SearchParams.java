package entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;


import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class SearchParams {

    @NonNull
    private String url;
    @NonNull
    private Integer depth;
    @NonNull
    private Long linksCount;
    private Set<String> keyWords;


}
