package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.BookDTO;
import dto.LoanBookDTO;
import dto.LoanDTO;
import entities.Book;
import entities.User;
import facades.BookFacade;
import facades.FacadeExample;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import utils.EMF_Creator;
import utils.SetupTestUsers;

/**
 * @author lam@cphbusiness.dk
 */
@Path("books")
public class BookResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final ExecutorService ES = Executors.newCachedThreadPool();
    private static final BookFacade FACADE = BookFacade.getBookFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static String cachedResponse;
    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll() {
        return "{\"msg\":\"Hello anonymous\"}";
    }

    //Just to verify if the database is setup
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    public String allUsers() {

        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<User> query = em.createQuery("select u from User u", entities.User.class);
            List<User> users = query.getResultList();
            return "[" + users.size() + "]";
        } finally {
            em.close();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("user")
    @RolesAllowed("user")
    public String getFromUser() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to User: " + thisuser + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("admin")
    @RolesAllowed("admin")
    public String getFromAdmin() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to (admin) User: " + thisuser + "\"}";
    }

    @Path("setupusers")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public void setUpUsers() {
        SetupTestUsers.setUpUsers();
    }

    @Path("allbooks")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllBooks() {
        List<BookDTO> bookList = FACADE.getAllBooks();
        System.out.println("LSOASDLAWLDLAWDL--_____*****");
        return GSON.toJson(bookList);
    }

    @Path("book/{title}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getBookByIsbn(@PathParam("title") String name) throws Exception {
        BookDTO book = FACADE.getBookByTitle(name);

        return GSON.toJson(book);

    }
    
    
    @Path("loan")
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_JSON)
    public Response LoanABook(String book) throws Exception{
        
        LoanBookDTO loanBook = GSON.fromJson(book, LoanBookDTO.class);
        
        LoanDTO loan = FACADE.loanABook(loanBook);
        
        return Response.ok(GSON.toJson(loan)).build();
    }
    
    @Path("add")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addBook(String book) throws Exception{
        
        BookDTO bookDTO = GSON.fromJson(book, BookDTO.class);
        
        BookDTO newBook = FACADE.addBook(bookDTO);
        
        return Response.ok(GSON.toJson(newBook)).build();
    }
    
    @Path("myloans/{userName}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getMyLoans(@PathParam("userName") String userName) throws Exception {

        List<LoanDTO> loanList = FACADE.getAllLoans(userName);
        
        return GSON.toJson(loanList);

    }
    
    
}
