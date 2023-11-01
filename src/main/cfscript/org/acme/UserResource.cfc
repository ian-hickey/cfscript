 /**
 * This example component represents a basic DMScript REST resource.
 * This is an example REST resource that performs user CRUD.
 */
@Path("")
@Transactional
@RequestScoped
component name="UserResource" hint="This is a REST controller for Users protected with role based JWT authentication" {

    @Inject
    property name="jwt" type="org.eclipse.microprofile.jwt.JsonWebToken" hint="To use a JWT token we need to inject one.";

    @Inject
    property type="Mailer" name="mailer" hint="Mailer is used to send emails either by manually building them or using a template";

    @Inject
    property name="em" type="EntityManager" hint="This is similar to ormGetSession";

    @Inject
    property type="Template" name="forgotPasswordTemplate";

    @GET
    @Produces("application/json")
    @Path("/users")
    @RolesAllowed("admin")
    public Response function getUsers() {
        Log.info("GET All Users sorted by name");
        var users = org.acme.model.User.listAll(Sort.by("email"));
        return Response.ok(users).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/user/{id}")
    @RolesAllowed("admin")
    public Response function getOneUser(@PathParam("id") string id) {
      Log.info("GET a User by id");
      var user = org.acme.model.User.findById(id);
      if (isNull(user)) {
        return Response.status(Response.Status.NOT_FOUND).build();
      }

      return Response.ok(user).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/signup")
    @PermitAll
    public Response function signup(@Valid org.acme.model.User user, @Context SecurityContext ctx) {
      Log.info("Create new user");
      user.persist();
      return Response.status(Response.Status.CREATED).entity(user).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/user/{id}")
    @RolesAllowed("admin")
    public Response function deleteUser(@PathParam("id") string id, @Context SecurityContext ctx) {
      Log.info("Delete user by: " & ctx.getUserPrincipal().getName());
      var user = org.acme.model.User.findById(id);
      if (isNull(user)) {
        return Response.status(Response.Status.NOT_FOUND).build();
      }
      user.delete();
      return Response.noContent().build();
    }

    @GET
    @RolesAllowed({"user", "admin"})
    @Path("/user/me")
    @Produces("text/plain")
    public string function me(@Context SecurityContext securityContext) {
        return securityContext.getUserPrincipal().getName();
    }

    @POST
    @Produces("text/plain")
    @Consumes("application/x-www-form-urlencoded")
    @Path("/forgot-password")
    @PermitAll
    public Response function forgotPassword(@FormParam("email") string email) {
        // find the user by email.
        Log.info("Find user by email");
        var users = em.createNamedQuery("User.findByEmail", org.acme.model.User.class)
                .setParameter("email", email)
                .getResultList();
        var numberOfUsers = users.size();
        // User names must be unique.
        if (numberOfUsers != 1) {
            return Response.ok("If this email exists you will receive a notification shortly.") // Generic message, no specifics
                .build();
        }
        // Check that the password matches
        var user = users.get(0); // get the only user
        // set a uuid as the forgotPasswordToken on the user entity.
        user.forgotPasswordToken = createUUID();
        user.persist();
        // send an email linking to the password-reset endpoint that takes that token.

        return Response.ok("If this email exists you will receive a notification shortly.") // Generic message, no specifics
                .build();
    }

}