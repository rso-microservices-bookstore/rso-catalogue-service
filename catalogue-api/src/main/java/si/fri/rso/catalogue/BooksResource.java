/*
 *  Copyright (c) 2014-2017 Kumuluz and/or its affiliates
 *  and other contributors as indicated by the @author tags and
 *  the contributor list.
 *
 *  Licensed under the MIT License (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  https://opensource.org/licenses/MIT
 *
 *  The software is provided "AS IS", WITHOUT WARRANTY OF ANY KIND, express or
 *  implied, including but not limited to the warranties of merchantability,
 *  fitness for a particular purpose and noninfringement. in no event shall the
 *  authors or copyright holders be liable for any claim, damages or other
 *  liability, whether in an action of contract, tort or otherwise, arising from,
 *  out of or in connection with the software or the use or other dealings in the
 *  software. See the License for the specific language governing permissions and
 *  limitations under the License.
*/
package si.fri.rso.catalogue;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import si.fri.rso.catalogue.cdi.configuration.ConfigProperties;
import si.fri.rso.models.Book;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Future;

import static org.asynchttpclient.Dsl.*;

//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

@Path("/books")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BooksResource {


    @PersistenceContext
    private EntityManager em;

    @Inject
    private ConfigProperties properties;

    /**
     * <p>Queries the database and returns a list of all books.</p>
     *
     * @return Response object containing the retrieved list of books from the database.
     */
    @GET
    public Response getBooks() {
        em.getEntityManagerFactory().getCache().evictAll();
        TypedQuery<Book> query = em.createNamedQuery("Book.findAll", Book.class);

        List<Book> books = query.getResultList();

        return Response.ok(books).build();
    }

    /**
     * <p>Queries the database and returns a specific book based on the given id.</p>
     *
     * @param id The id of the wanted book.
     * @return Response object containing the requested book, or empty with the NOT_FOUND status.
     */
    @GET
    @Path("/{id}")
    public Response getBook(@PathParam("id") Integer id) {
        em.getEntityManagerFactory().getCache().evictAll();
        Book b = em.find(Book.class, id);

        if (b == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(b).build();
    }

    /**
     * <p>Inserts the provided book into the database.</p>
     *
     * @param b The book object which will be created.
     * @return Response object containing the created book.
     */
    @POST
    public Response createBook(Book b) {

        b.setId(null);

        em.getTransaction().begin();

        em.persist(b);

        em.getTransaction().commit();

        return Response.status(Response.Status.CREATED).entity(b).build();
    }

    @POST
    @Path("/{id}")
    public Response createBookByGoodReadsId(@PathParam("id") String id) throws IOException {
        AsyncHttpClient client = new DefaultAsyncHttpClient();
         client.prepare("GET", "https://goodreads-books.p.rapidapi.com/books/"+ id)
                .setHeader("x-rapidapi-host", "goodreads-books.p.rapidapi.com")
                .setHeader("x-rapidapi-key", properties.getExternalApiKey())
                .execute()
                .toCompletableFuture()
                .thenAccept(jsonString -> {
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode actualObj = null;
                    try {
                        actualObj = mapper.readTree(jsonString.getResponseBody());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // When
                    JsonNode jsonNode1 = actualObj.get("title");
                    JsonNode jsonNode2 = actualObj.get("author").get("name");
                    JsonNode jsonNode3 = actualObj.get("description");

                    Book b = new Book();
                    b.setTitle(jsonNode1.asText());
                    b.setAuthor(jsonNode2.asText());
                    b.setDescription(jsonNode3.asText());

                    b.setId(null);

                    em.getTransaction().begin();

                    em.persist(b);

                    em.getTransaction().commit();

                })
                .join();

        client.close();
        return Response.status(Response.Status.CREATED).entity("Book added to our database").build();

    }




}
