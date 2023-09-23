/*
  This test component represents a basic cfscript component.
  Others, will represent entities and REST controllers.
*/
@testable(name="testname")
component displayname="MyComponent" accessors="true" {
    property name="first" value="test" type="String";
    property name="last" value="name";

    public any function init() {
        writedump("This is a component");
        text = "This is some text";
        math = 1+5*2;
        str = { test= "test", test2: 2 };
        arr = [1,2,3,4,5,"test"];
    }

    @test(name="1")
    private any function getNumbers() {
        return [1,2,3,4,5];
    }

    @test("skip")
    public struct function getOtherObjects() {
        writeoutput("testing");
        return { test= "test3", test5: "test2" };
    }

}