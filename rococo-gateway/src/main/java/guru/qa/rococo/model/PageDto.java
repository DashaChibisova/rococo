package guru.qa.rococo.model;

public record PageDto(
        int pageNumber,
        int pageSize,
        SortDao sort,
        int offset,
        boolean paged,
        boolean unpaged
) {
}
