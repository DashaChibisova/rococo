package guru.qa.rococo.jupiter.model;

public record PageDto(
        int pageNumber,
        int pageSize,
        SortDto sort,
        int offset,
        boolean paged,
        boolean unpaged
) {
}
