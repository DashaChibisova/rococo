package guru.qa.rococo.api;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class PagedList<T> {
    private int page = 0;
    private List<T> results = new ArrayList<>();
    private int totalResults = 0;
    private int totalPages = 0;
}
