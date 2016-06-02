package pocket2html;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainPocket2html1 {

	static ArrayList<Article> getArticles() throws IOException{
		ArrayList<Article> articles = new ArrayList<Article>();
		try {
			//chargement des properties
			Properties properties = new Properties();
			properties.load(new FileInputStream("pocket.properties"));
			String consumerKey=properties.getProperty("consumerKey");
			String accessToken=properties.getProperty("accessToken");

			//préparation de la variable since
			Calendar weekAgo = Calendar.getInstance();
			weekAgo.add(Calendar.DAY_OF_YEAR, -7);			
			long since = weekAgo.getTimeInMillis() / 1000L ;

			//http get
			String myurl = "https://getpocket.com/v3/get?consumer_key="+consumerKey+"&access_token="+accessToken+"&detailType=complete&since="+since+"&sort=oldest";
			URL url = new URL(myurl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.connect();
			InputStream inputStream = connection.getInputStream();
			String result = InputStreamOperations.inputStreamToString(inputStream);

			// on récupère le json complet
			JSONObject jsonObject = new JSONObject(result);



			//			On récupère la list des id de chaque article
			JSONArray array = jsonObject.getJSONObject("list").names();
			for (int i = 0; i < array.length(); i++) {
				String id_article = array.getString(i);
				// que les articles ayant un status différent de 2
				if (jsonObject.getJSONObject("list").getJSONObject(id_article).getString("status").matches("[^2]")){

					System.out.println("Objet Json : ");
					System.out.println(jsonObject.getJSONObject("list").getJSONObject(id_article).toString());
					Article article = new Article();
					//id de l'article
					System.out.println("id article: "+id_article);
					article.setId_article(id_article);
					//titre de l'article
					if (jsonObject.getJSONObject("list").getJSONObject(id_article).has("resolved_title")) {
						String titre_article= jsonObject.getJSONObject("list").getJSONObject(id_article).getString("resolved_title");
						System.out.println("Titre de l'article: "+titre_article);
						article.setTitle(titre_article);
					}

					//Url de l'article
					if (jsonObject.getJSONObject("list").getJSONObject(id_article).has("given_url")){
						String url_article=jsonObject.getJSONObject("list").getJSONObject(id_article).getString("given_url");
						System.out.println("Url de l'article: "+url_article);
						article.setUrl(url_article);
					}
					//image de l'article
					if ( jsonObject.getJSONObject("list").getJSONObject(id_article).has("image")) {
						String image_url = jsonObject.getJSONObject("list").getJSONObject(id_article).getJSONObject("image").getString("src");
						System.out.println("Url image : "+image_url);
						article.setImage_url(image_url);
					}

					//résumé de l'article
					if (jsonObject.getJSONObject("list").getJSONObject(id_article).has("excerpt")) {
						String resume_article = jsonObject.getJSONObject("list").getJSONObject(id_article).getString("excerpt");
						System.out.println("Résumé: "+resume_article);
						article.setResume(resume_article);
					}
					articles.add(article);
					System.out.println("*********************************");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return articles;

	}
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		ArrayList<Article> articles = getArticles();
		Article article = new Article();
		article.articlesToHtml(articles);

	}

}
