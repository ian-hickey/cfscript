@Test("This is an annotation")
component name="Cfscript" {
    property name="myprop" value="test";

    function init() {
        writedump("This is a component");
        sayHi = () => writeoutput("Hi");
        five = () => 4+1;
        text = "This is some text";
    }

    @Path("/path")
    function getObjects() {

    }
}