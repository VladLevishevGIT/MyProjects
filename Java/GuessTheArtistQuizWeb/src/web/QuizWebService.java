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
import core.JavaRoundBean;
import core.QuizListCreator;

@WebService
@Path("quizWebService")
public class QuizWebService {
	
	@Context
	private HttpServletRequest req;
	
	@Context
	private HttpServletResponse res;
	
	@Path("getFirstSong")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JavaRoundBean getFirstSong() throws MalformedURLException, IOException, JSONException{
		HttpSession session = req.getSession(true);
		if (!session.equals(null)){
			QuizListCreator qc = new QuizListCreator();
			JavaRoundBean round = qc.RoundCreator(qc.artistRandomChooser());
			session.setAttribute("round", round);
			session.setAttribute("songsCounter", 0);
			JavaRoundBean returnedRound = new JavaRoundBean();
			returnedRound.setFirstSong(round.getFirstSong());
			return returnedRound;
		} else {
			res.sendError(505);
		}
		return null;
	}
	
	@Path("getOneMoreSong")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JavaRoundBean getSecondSong() throws MalformedURLException, IOException, JSONException{
		HttpSession session = req.getSession(false);
		if (!session.equals(null)){
			Integer counter = (Integer)session.getAttribute("songsCounter");
			if(counter == 0){
				JavaRoundBean returnedRound = (JavaRoundBean)session.getAttribute("round");
				returnedRound.setThirdSong(null);
				session.removeAttribute("songsCounter");
				session.setAttribute("songsCounter", 1);
				return returnedRound;
				} else {
				JavaRoundBean returnedRound = (JavaRoundBean)session.getAttribute("round");
				return returnedRound;
			}
			
		} else {
			res.sendError(505);
		}
		return null;
	}

}
