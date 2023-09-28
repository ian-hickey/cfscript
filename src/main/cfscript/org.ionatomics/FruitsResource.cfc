/*
  This test component represents a basic cfscript component.
  This is an example REST service that gets and adds fruits.
*/
@Path("/fruits")
component {

    property name="fruits" type="Array";

    public function init() {
        arrayAppend(fruits, new Fruit("Apple", "Winter fruit"));
        arrayAppend(fruits, new Fruit("Pineapple", "Tropical fruit"));
    }

    @GET
    public Response function list() {
        Response.ok(fruits).build();
    }

    @POST
    public Response function add(any fruit) {
        arrayAppend(fruits, fruit);
        return Response.ok(fruits).build();
    }
}