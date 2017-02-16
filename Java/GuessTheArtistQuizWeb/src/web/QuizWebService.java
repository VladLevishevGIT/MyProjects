package web;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;

@WebService
@Path("quizWebService")
public class QuizWebService {
	
	@Context
	private HttpServletRequest req;
	
	@Context
	private HttpServletResponse res;
	
	@Path("getSongs")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public void getSongs() throws MalformedURLException, IOException, JSONException{
/*		HttpSession session = req.getSession();
		if (session!=null){*/
			System.out.println("GOOD");
		/*QuizListCreator qc = new QuizListCreator();
		JSONObject songs = qc.JSONsongsCreator(qc.artistRandomChooser());*/
			/*String songs = "SONGS";*/
		/*return songs;
		} else {
			res.sendError(505);
			return null;
		}*/
	}

}
