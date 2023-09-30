/*
  This test component represents a basic cfscript REST component.
  This is an example REST service that greet the user. This is a test
*/
@Path("/hello")
component {

    @Inject
    property type="Mailer" name="mailer";

    public function init() { }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response function greet() {
        var name="-ian";
        mailer.send(new Mail().addTo("me" & name & "@ionatomics.org"));
        return Response.ok("Greeting from Dark Matter!").build();
    }
}