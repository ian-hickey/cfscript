/*
  This test component represents a basic cfscript component.
  Others, will represent entities and REST controllers.
*/
@Path(name="/testname")
component displayname="MyComponent" accessors=true {

    @Inject
    property type="org.acme.model.ProductService" name="ps";
    property name="first" value="test" type="String";
    property name="last" value="name";
    property name="mathResult";
    property name="anotherMathResult";
    property name="stringResult";
    property name="str";
    property name="arr";
    property name="uid";

    @Inject
    property type="EntityManager" name="em" hint="The datasource";

    public void function createProduct(@PathParam("name") string name, string description, integer price, any fruit,
        boolean flag) {
        product = new org.acme.model.Product();
        product.name = name;
        product.description = description;
        em.persist(product);

    }

    public any function init() {
        writedump("This is a component");
        text = "This is some text";
        this.mathResult = 1+5*2;
        anotherMathResult = this.mathResult++;
        this.stringResult = "test" & text;
        conditional = (true && true);
        innerObject = {obj: "innerobject"};
        var val = str["test"]["obj"];
        arr = [1,2,3,4,5,"test"];
        str = { test= innerObject, test2: 2 };
        var arrVal = arr[0];
        uid = "fef8dd96-65ca-11ee-8c99-0242ac120002"; 

    }

    /**
     * Test returning an array
     */
    private array function getNumbers() {
        return [1,2,3,4,5];
    }

    /**
     * Test returning an struct
     */
    public struct function getOtherObjects() {
        return { test= "test3", test5: "test2" };
    }

}