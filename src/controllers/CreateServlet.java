package controllers;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Task;
import utils.DBUtil;

/**
 * Servlet implementation class CreateServlet
 */
@WebServlet("/create")
public class CreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateServlet() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) { //CSRF対策のチェック
            EntityManager em = DBUtil.createEntityManager();
            em.getTransaction().begin();

            Task m = new Task();

            String content = request.getParameter("content"); //フォームから入力された内容をセット
            m.setContent(content);

            Timestamp currentTime = new Timestamp(System.currentTimeMillis()); //現在日時
            m.setCreated_at(currentTime);
            m.setUpdated_at(currentTime);

            em.persist(m); //データベースへの保存
            em.getTransaction().commit();
            em.close();

            response.sendRedirect(request.getContextPath() + "/index"); //indexページへ遷移


        }
    }

}
