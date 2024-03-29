package com.example.boot01.service;

import com.example.boot01.dto.BoardDTO;
import com.example.boot01.dto.PageRequestDTO;
import com.example.boot01.dto.PageResponseDTO;

public interface BoardService {
    Long register(BoardDTO boardDTO);
    BoardDTO readOne(Long bno);
    void modify(BoardDTO boardDTO);
    void remove(Long bno);
    PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO);
}
