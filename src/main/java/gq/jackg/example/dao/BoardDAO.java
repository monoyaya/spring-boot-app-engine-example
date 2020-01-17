package gq.jackg.example.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.stereotype.Repository;

import com.google.appengine.api.datastore.AsyncDatastoreService;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.QueryResultList;

import gq.jackg.example.model.BoardDTO;

@Repository
public class BoardDAO {
	AsyncDatastoreService datastore;
	private static final String BOARD_KIND = "Board";

	public BoardDAO() {
		this.datastore = DatastoreServiceFactory.getAsyncDatastoreService();
	}

	public BoardDTO entityToBoard(Entity entity) {
		return new BoardDTO(entity.getKey().getId(), 
				(String) entity.getProperty(BoardDTO.AUTHOR),
				(String) entity.getProperty(BoardDTO.SUBJECT), 
				(String) entity.getProperty(BoardDTO.CONTENT),
				(String) entity.getProperty(BoardDTO.PASS), 
				(String) entity.getProperty(BoardDTO.DATE));
	}

	public List<BoardDTO> entitiesToBoard(Iterator<Entity> entities) {
		List<BoardDTO> list = new ArrayList<BoardDTO>();

		while (entities.hasNext()) {
			Entity entity = (Entity) entities.next();
			
			list.add(entityToBoard(entity));
		}

		return list;
	}

	public long writeBoard(BoardDTO board) {
		Entity entity = new Entity(BOARD_KIND);

		Key key = null;

		try {
			// 동기식
			key = datastore.put(entity).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		Entity setId = new Entity(key);
		setId.setProperty(BoardDTO.BOARD_ID, key.getId());
		setId.setProperty(BoardDTO.AUTHOR, board.getAuthor());
		setId.setProperty(BoardDTO.SUBJECT, board.getSubject());
		setId.setProperty(BoardDTO.CONTENT, board.getContent());
		setId.setProperty(BoardDTO.PASS, board.getPass());
		setId.setProperty(BoardDTO.DATE, board.getMonthDay());
		
		// 비동기식
		Future<Key> fKey = datastore.put(setId);
		
		try {
			key = fKey.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		return key.getId();
	}

	public long updateBoard(BoardDTO board) {
		Key key = KeyFactory.createKey(BOARD_KIND, board.getBoardId());

		Entity entity = new Entity(key);

		entity.setProperty(BoardDTO.AUTHOR, board.getAuthor());
		entity.setProperty(BoardDTO.SUBJECT, board.getSubject());
		entity.setProperty(BoardDTO.CONTENT, board.getContent());
		entity.setProperty(BoardDTO.PASS, board.getPass());
		entity.setProperty(BoardDTO.DATE, board.getMonthDay());

		try {
			key = datastore.put(entity).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		return key.getId();
	}

	public void deleteBoard(long boardId) {
		Key key = KeyFactory.createKey(BOARD_KIND, boardId);

		datastore.delete(key);
	}

	public BoardDTO readBoard(long boardId) {
		Future<Entity> fEn = datastore.get(KeyFactory.createKey(BOARD_KIND, boardId));
		
		Entity board = null;

		try {
			board = fEn.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		return entityToBoard(board);
	}
	
	public Map<String, Object> getTenArticle(String startCursor) {
		Map<String, Object> map = new HashMap<String, Object>();
		FetchOptions options = FetchOptions.Builder.withLimit(5);
		
		if (startCursor != null && startCursor != "") {
			options.startCursor(Cursor.fromWebSafeString(startCursor));
		}
		
		Query query = new Query(BOARD_KIND).addSort(BoardDTO.DATE, SortDirection.DESCENDING);
		PreparedQuery psq = datastore.prepare(query);
		
		QueryResultList<Entity> qrl = psq.asQueryResultList(options);
		
		List<BoardDTO> list = entitiesToBoard(qrl.iterator());
		Cursor cursor = qrl.getCursor();
		
		if (cursor != null && list.size() == 5) {
			map.put("endCursor", cursor.toWebSafeString());
			map.put("list", list);
		} else {
			map.put("list", list);
		}
		
		return map;
	}
	
	public Map<String, Object> backWord(int offset) {
		final int PAGE_SIZE = 5;
		Map<String, Object> map = new HashMap<String, Object>();
		
		FetchOptions options = FetchOptions.Builder.withLimit(PAGE_SIZE);
		options.offset(PAGE_SIZE * offset);
		
		Query query = new Query(BOARD_KIND).addSort(BoardDTO.DATE, SortDirection.DESCENDING);
		PreparedQuery psq = datastore.prepare(query);
		
		QueryResultList<Entity> qrl = psq.asQueryResultList(options);
		
		List<BoardDTO> list = entitiesToBoard(qrl.iterator());
		
		Cursor cursor = qrl.getCursor();
		
		if(list.size() == 5 && cursor != null) map.put("endCursor", cursor.toWebSafeString());
		
		map.put("list", list);
		
		return map;
	}
}
