package com.example.boot01.repository.search;

import com.example.boot01.domain.Board;
import com.example.boot01.domain.QBoard;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {
    public BoardSearchImpl() {
        super(Board.class);
    }

    @Override
    public Page<Board> search1(Pageable pageable) {
        QBoard board = QBoard.board;    // Q 도메인 객체
        JPQLQuery<Board> query = from(board);    // select ... from board
        query.where(board.title.contains("1")); // where title like

        // parsing
        this.getQuerydsl().applyPagination(pageable, query);

        List<Board> list = query.fetch();
        long count = query.fetchCount();
        return null;
    }

    @Override
    public Page<Board> searchAll(String[] types, String keyword, Pageable pageable) {
        QBoard board = QBoard.board;
        JPQLQuery<Board> query = from(board);

        if((types != null && types.length > 0) && keyword != null) { // 검색 조건과 키워드가 있다면
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            for(String type: types) {
                switch (type) { // 검색
                    case "t": // 제목
                        booleanBuilder.or(board.title.contains(keyword));
                        break;
                    case "c": // 내용
                        booleanBuilder.or(board.content.contains(keyword));
                        break;
                    case "w": // 작성자
                        booleanBuilder.or(board.writer.contains(keyword));
                        break;
                }
            }
            query.where(booleanBuilder);
        }

        // bno > 0
        query.where(board.bno.gt(0L));

        // paging
        this.getQuerydsl().applyPagination(pageable, query);

        List<Board> list = query.fetch();

        long count = query.fetchCount();

        return new PageImpl<>(list, pageable, count);
    }
}
