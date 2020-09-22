package br.edu.insper;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MyNotes
 */
@WebServlet("/MyNotes")
public class MyNotes extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MyNotes() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		PrintWriter out = response.getWriter();

		try {
			DAO dao = new DAO();
			List<Note> notes = dao.getNotes();
			if (!notes.isEmpty()) {

				String jsonToBeParsed = "[";
				for (Note note : notes) {
					jsonToBeParsed = jsonToBeParsed.concat("{".concat("\"title\":\"").concat(note.getTitle())
							.concat("\",\"text\":\"").concat(note.getTextContent()).concat("\",\"id\":")
							.concat(String.valueOf(note.getId())).concat(",\"timestamp\":")
							.concat(String.valueOf(note.getLastModified().getTime())).concat("},"));
				}
				jsonToBeParsed = jsonToBeParsed.substring(0, jsonToBeParsed.length() - 1).concat("]");

				System.out.println(jsonToBeParsed);
				response.setContentType("text/plain; charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				out.write(jsonToBeParsed);
				out.flush();
				out.close();
			}

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			String title = request.getParameter("title");
			String text = request.getParameter("text");

			DAO dao = new DAO();
			Note note = new Note();

			note.setTitle(title);
			note.setTextContent(text);

//			Date now = new Date();
//			Calendar calendar = Calendar.getInstance();
//			calendar.setTime(now);
//			System.out.println(calendar.getTime());
//			note.setLastModified(calendar);

			dao.createNotes(note);
			dao.close();

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			String title = request.getParameter("title");
			String text = request.getParameter("text");
			Integer id = Integer.parseInt(request.getParameter("id"));

			DAO dao = new DAO();
			Note note = new Note();

			note.setTitle(title);
			note.setTextContent(text);
			note.setId(id);

			Timestamp now = new Timestamp(System.currentTimeMillis());
			note.setLastModified(now);

			dao.updateNotes(note);
			dao.close();

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			Integer id = Integer.parseInt(request.getParameter("id"));

			DAO dao = new DAO();
			Note note = new Note();

			note.setId(id);

			dao.deleteNotes(note);
			dao.close();

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
