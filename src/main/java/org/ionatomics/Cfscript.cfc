/*
  This test component represents a basic cfscript component.
  Others, will represent entities and REST controllers.
*/
component name="Cfscript" {
    property name="first" value="test" type="String";
    property name="last" value="test" type="String";
    public any function init() {
        writedump("This is a component");
        sayHi = () => writeoutput("Hi");
        five = () => 4+1;
        text = "This is some text";
    }
    function getObjects() {
    }
}