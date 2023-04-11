package dev.evgeni.personsapi.web.dto;

import java.util.List;
import org.springframework.data.domain.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class PersonApiPage<T> {
  List<T> content;
  CustomPageable pageable;

  public PersonApiPage(Page<T> page) {
    this.content = page.getContent();
    this.pageable = new CustomPageable(page.getPageable().getPageNumber(),
        page.getPageable().getPageSize(), page.getTotalElements());
  }

  @Data
  public class CustomPageable {
    private int pageNumber;
    private int pageSize;
    private long totalElements;

    public CustomPageable(int pageNumber, int pageSize, long totalElements) {

      this.pageNumber = pageNumber;
      this.pageSize = pageSize;
      this.totalElements = totalElements;
    }
  }
}
