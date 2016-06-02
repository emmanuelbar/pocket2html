package pocket2html;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Article {
	private String title;
	private String url;
	private String image_url;
	private String id_article;
	private String resume;
	public String getResume() {
		return resume;
	}
	public void setResume(String resume) {
		this.resume = resume;
	}
	public String getId_article() {
		return id_article;
	}
	public void setId_article(String id_article) {
		this.id_article = id_article;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void articlesToHtml(ArrayList<Article> articles) throws FileNotFoundException, UnsupportedEncodingException{
		System.out.println("articlesToHtml");
		PrintWriter writer = new PrintWriter("result_pocket.html", "UTF-8");
		//header
		writer.println("<!DOCTYPE html>	<html><head><meta charset=\"UTF-8\"><title>Pocket2html</title></head><body>");

		//boucle sur les articles
		for (Article article : articles) {
			writer.println("<div style=\"width:800px; margin:0 auto;\"><h2><a href=\""+article.getUrl()+"\">"+article.getTitle()+"</a></h2>");
			if (article.getImage_url() != null && !article.getImage_url().isEmpty()){
				writer.println("<img src=\""+article.getImage_url()+"\" width=\"512\" height=\"280\">");
			}
			if (article.getResume() != null && !article.getResume().isEmpty()){
				writer.println("<blockquote>"+article.getResume()+"</blockquote>");
			}
			
			writer.println("<hr></div>");
		}

		//foot
		writer.println("</body></html>");
		writer.close();
	}
}
