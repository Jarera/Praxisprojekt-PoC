import java.util.Iterator;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.util.FileManager;

public class Main{
	public static void main(String args[]){
		sparqlTest();
	}
	
	static void sparqlTest(){
		FileManager.get().addLocatorClassLoader(Main.class.getClassLoader());
		Model model = FileManager.get().loadModel("./beitragsbeschreibung.ttl");
		
		String queryString = 
				"PREFIX dbp: <http://de.dbpedia.org/resource/> " +
				"PREFIX duden: <https://www.duden.de/rechtschreibung/> " +
				
				"SELECT ?gewalt WHERE { " +
				"duden:Mord ?in dbp:Berlin . " +
				"duden:Mann ?gewalt duden:Tochter . " +
				"dbp:Polizei duden:untersuchen duden:Mord . }";
		
		// Für lokale .ttl Datei
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		
		ResultSet results = qexec.execSelect();
		while(results.hasNext()){
			QuerySolution soln = results.nextSolution();
			System.out.println(soln);
		}
		qexec.close();
		
		
		/* Für Online SPARQL Endpoint
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.sparqlService("http://de.dbpedia.org/sparql", query);

		ResultSet results = qexec.execSelect();
		ResultSetFormatter.out(System.out, results, query);
		
		qexec.close();
		*/
	}
}