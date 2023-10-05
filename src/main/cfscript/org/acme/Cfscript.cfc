/*
  This test component represents a basic cfscript component.
  Others, will represent entities and REST controllers.
*/
@Path(name="/testname")
component displayname="MyComponent" accessors=true {

    property name="first" value="test" type="String";
    property name="last" value="name";

    public any function init() {
        writedump("This is a component");
        text = "This is some text";
        math = 1+5*2;
        innerObject = {obj: "innerobject"};
        str = { test= innerObject, test2: 2 };
        var val = str["test"]["obj"];
        arr = [1,2,3,4,5,"test"];
        var arrVal = arr[0];

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