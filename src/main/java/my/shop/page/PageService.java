package my.shop.page;

import my.shop.item.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

public class PageService {

    public static <T> Page<T> makePage(List<T> list, int pageNum, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNum - 1, 5);
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), list.size());

        Page<T> page = new PageImpl<>(list.subList(start, end), pageRequest, list.size());
        return page;
    }

}
