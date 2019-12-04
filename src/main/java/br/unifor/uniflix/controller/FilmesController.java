package br.unifor.uniflix.controller;

import br.unifor.uniflix.model.Filme;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Path("/filmes")
public class FilmesController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response popularMovies() throws IOException {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
        		.url("https://api.themoviedb.org/3" + "/movie/popular?api_key=7cd483ce6e30df12336dc2ea731b5c71")
                .build();

        Call call = client.newCall(request);

        okhttp3.Response response = call.execute();
        if (response.isSuccessful()) {
            JSONObject jsonResponse = new  JSONObject(response.body().string());
            JSONArray result = jsonResponse.getJSONArray("results");
            List<Filme> filmes = new ArrayList<>();
            for (int i = 0; i < result.length(); ++i) {
                JSONObject movieJson = result.getJSONObject(i);
                Filme filme = new Filme();
                filme.setTitulo(movieJson.getString("title"));
                filme.setSinopse(movieJson.getString("overview"));
                filme.setAdulto(movieJson.getBoolean("adult"));
                filme.setNota(movieJson.getDouble("vote_average"));
                filmes.add(filme);
            }
            return Response.ok(filmes).build();
        }
        return Response.serverError().build();
    }
}
