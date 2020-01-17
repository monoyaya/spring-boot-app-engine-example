package gq.jackg.example.web.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gq.jackg.example.dao.BoardDAO;
import gq.jackg.example.model.BoardDTO;

@Service
public class BoardService{
	@Autowired BoardDAO dao;
	
	public BoardDTO getArticle(String boardId) {
		if (boardId == null) {
			return new BoardDTO();
		}
		
		return dao.readBoard(Long.parseLong(boardId));
	}
	
	public BoardDTO writeArticle(BoardDTO board) {
		board = setTime(board);
		
		long boardId = dao.writeBoard(board);
		
		return dao.readBoard(boardId);
	}
	
	public BoardDTO updateArticle(BoardDTO board) {
		long boardId = dao.updateBoard(board);
		
		return dao.readBoard(boardId);
	}
	
	public void deleteArticle(Long boardId) {
		dao.deleteBoard(boardId);
	}
	
	public Map<String, Object> getTen(String startCusor) {
		return dao.getTenArticle(startCusor);
	}
	
	public Map<String, Object> backWord(int offset) {
		return dao.backWord(offset);
	}
	
	private BoardDTO setTime(BoardDTO board) {
		String nowToString = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		board.setMonthDay(nowToString);
		return board;
	}
}
